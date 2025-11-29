package kuke.board.article.service.response;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class ArticlePageResponse {
    private List<ArticleResponse> articles;
    private Long count;

    public static ArticlePageResponse from(List<ArticleResponse> articles, Long count) {
        ArticlePageResponse articlePageResponse = new ArticlePageResponse();
        articlePageResponse.articles = articles;
        articlePageResponse.count = count;
        return articlePageResponse;
    }
}
