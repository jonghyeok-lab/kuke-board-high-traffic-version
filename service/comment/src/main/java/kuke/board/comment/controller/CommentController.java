package kuke.board.comment.controller;

import kuke.board.comment.service.CommentService;
import kuke.board.comment.service.request.CommentCreateRequest;
import kuke.board.comment.service.response.CommentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/v1/comments/{commentId}")
    public CommentResponse read(@PathVariable Long commentId) {
        return commentService.read(commentId);
    }

//    @GetMapping("/v1/comments")
//    public CommentPageResponse readAll(
//            @RequestParam Long articleId,
//            @RequestParam Long page,
//            @RequestParam Long limit
//    ) {
//        return commentService.readAll(articleId, page, limit);
//    }

//    @GetMapping("/v1/comments/infinite-scroll")
//    public List<CommentResponse> readAllInfiniteScroll(
//            @RequestParam Long articleId,
//            @RequestParam Long limit,
//            @RequestParam(required = false) Long lastCommentId
//    ) {
//        return commentService.readAllInfiniteScroll(articleId, limit, lastCommentId);
//    }

    @PostMapping("/v1/comments")
    public CommentResponse create(@RequestBody CommentCreateRequest request) {
        return commentService.create(request);
    }

    @DeleteMapping("/v1/comments/{commentId}")
    public void delete(@PathVariable Long commentId) {
        commentService.delete(commentId);
    }
}
