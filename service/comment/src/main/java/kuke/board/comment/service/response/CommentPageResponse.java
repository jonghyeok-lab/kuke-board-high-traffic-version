package kuke.board.comment.service.response;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class CommentPageResponse {
    private List<CommentResponse> comments;
    private Long count;

    public static CommentPageResponse of(final List<CommentResponse> comments, Long count) {
        CommentPageResponse commentPageResponse = new CommentPageResponse();
        commentPageResponse.comments = comments;
        commentPageResponse.count = count;
        return commentPageResponse;
    }
}
