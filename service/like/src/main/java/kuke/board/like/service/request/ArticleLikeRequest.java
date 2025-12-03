package kuke.board.like.service.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ArticleLikeRequest {
    private Long articleId;
    private Long userId;
}
