package kuke.board.comment.service;

import kuke.board.comment.entity.Comment;
import kuke.board.comment.repository.CommentRepository;
import kuke.board.comment.service.request.CommentCreateRequest;
import kuke.board.comment.service.response.CommentPageResponse;
import kuke.board.comment.service.response.CommentResponse;
import kuke.board.common.event.EventType;
import kuke.board.common.event.payload.ArticleDeletedEventPayload;
import kuke.board.common.event.payload.CommentCreatedEventPayload;
import kuke.board.common.outboxmessagerelay.OutboxEventPublisher;
import kuke.board.common.snowflake.Snowflake;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.function.Predicate.not;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final Snowflake snowflake = new Snowflake();
    private final CommentRepository commentRepository;
    private final OutboxEventPublisher outboxEventPublisher;

    @Transactional
    public CommentResponse create(CommentCreateRequest request) {
        Optional<Comment> parent = findParent(request);
        Comment comment = commentRepository.save(
                Comment.create(
                        snowflake.nextId(),
                        request.getContent(),
                        parent.map(Comment::getCommentId).orElse(null),
                        request.getArticleId(),
                        request.getWriterId()
                )
        );

        outboxEventPublisher.publish(
                EventType.COMMENT_CREATED,
                CommentCreatedEventPayload.builder()
                        .commentId(comment.getCommentId())
                        .articleId(comment.getArticleId())
                        .content(comment.getContent())
                        .writerId(comment.getWriterId())
                        .createdAt(comment.getCreatedAt())
                        .deleted(comment.getDeleted())
//                        .boardArticleCount(count(comment.getBoardId()))
                        .build(),
                comment.getArticleId()
        );
        return CommentResponse.from(comment);
    }

    private Optional<Comment> findParent(CommentCreateRequest request) {
        Long parentCommentId = request.getParentCommentId();
        if (parentCommentId == null) {
            return Optional.empty();
        }
        return commentRepository.findById(parentCommentId)
                .filter(not(Comment::getDeleted))
                .filter(Comment::isRoot);
    }

    public CommentResponse read(Long commentId) {
        return CommentResponse.from(
                commentRepository.findById(commentId).orElseThrow()
        );
    }

    @Transactional
    public void delete(Long commentId) {
        commentRepository.findById(commentId)
                .filter(not(Comment::getDeleted))
                .ifPresent(comment -> {
                    if (hasChildren(comment)) {
                        comment.delete();
                    } else {
                        delete(comment);
                    }
                });
    }

    private boolean hasChildren(Comment comment) {
        return commentRepository.countBy(comment.getArticleId(), comment.getCommentId(), 2L) == 2;
    }

    private void delete(Comment comment) {
        commentRepository.delete(comment);
        if (!comment.isRoot()) {
            commentRepository.findById(comment.getParentCommentId())
                    .filter(Comment::getDeleted)
                    .filter(not(this::hasChildren))
                    .ifPresent(this::delete);
        }
    }

    public CommentPageResponse readAll(Long articleId, Long page, Long limit) {
        return CommentPageResponse.of(
                commentRepository.findAll(articleId, limit, (page - 1) * limit).stream()
                        .map(CommentResponse::from)
                        .toList(),
                commentRepository.countBy(articleId, PageCalculator.calculatePageLimit(page, limit, 10L))
        );
    }

    public List<CommentResponse> readAllInfiniteScroll(
            Long articleId,
            Long lastParentCommentId,
            Long lastCommentId,
            Long limit
    ) {
        if (lastParentCommentId == null || lastCommentId == null) {
            return commentRepository.findAllInfiniteScroll(articleId, limit).stream()
                    .map(CommentResponse::from)
                    .toList();
        }
        return commentRepository.findAllInfiniteScroll(articleId, lastParentCommentId, lastCommentId, limit).stream()
                .map(CommentResponse::from)
                .toList();
    }

}
