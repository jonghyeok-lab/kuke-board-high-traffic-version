package kuke.board.article;

import kuke.board.article.service.response.ArticlePageResponse;
import kuke.board.article.service.response.ArticleResponse;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

public class ArticleApiTest {
    RestClient client = RestClient.create("http://localhost:9000");

    @Test
    void readAll() {
        ArticlePageResponse response = client.get()
                .uri("/v1/articles?boardId=1&limit=30&page=50000&movablePageSize=10")
                .retrieve()
                .body(ArticlePageResponse.class);

        System.out.println("response.getCount() = " + response.getCount());
        for (ArticleResponse article : response.getArticles()) {
            System.out.println("article.getArticleId() = " + article.getArticleId());
        }
    }


}
