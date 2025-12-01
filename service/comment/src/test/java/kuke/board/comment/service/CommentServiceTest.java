package kuke.board.comment.service;

import kuke.board.comment.entity.Comment;
import kuke.board.comment.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {
    @InjectMocks // @Mock 으로 생성된 객체들을 자동으로 주입받는 테스트 대상 객체
    CommentService commentService;
    @Mock // Spring 컨텍스트 로드 X 하지 않고 테스트 대상 객체에 주입됨.
    CommentRepository commentRepository;

    @Test
    void deleteShouldMarkDeletedIfHasChildren() {
        Long articleId = 1L;
        Long commentId = 2L;

        // Comment 에 자식이 있으면 딜리티드 마크 표시만
        Comment comment = createComment(articleId, commentId);
        if (!comment.isRoot()) {

        }

    }

    private Comment createComment(Long articleId, Long commentId) {
        Comment comment = mock(Comment.class);
        given(comment.getArticleId()).willReturn(articleId);
        given(comment.getCommentId()).willReturn(commentId);
        return comment;
    }

//    private
}