package kuke.board.like.repository;

import jakarta.persistence.LockModeType;
import kuke.board.like.entity.ArticleLikeCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ArticleLikeCountRepository extends JpaRepository<ArticleLikeCount, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<ArticleLikeCount> findLockByArticleId(Long articleId);

    @Query(
            value = "update article_like_count set like_count = like_count + 1 where articleId = :articleId",
            nativeQuery = true
    )
    @Modifying
    int increase(Long articleId);

    @Query(
            value = "update article_like_count set like_count = like_count - 1 where articleId = :articleId",
            nativeQuery = true
    )
    @Modifying
    int decrease(Long articleId);
}
