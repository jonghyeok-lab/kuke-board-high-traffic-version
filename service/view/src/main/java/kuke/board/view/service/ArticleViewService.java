package kuke.board.view.service;

import kuke.board.view.repository.ArticleViewCountRepository;
import kuke.board.view.repository.ArticleViewDistributeLockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class ArticleViewService {
    private final ArticleViewCountRepository articleViewCountRepository;
    private final ArticleViewCountBackupProcessor articleViewCountBackupProcessor;
    private final ArticleViewDistributeLockRepository articleViewDistributeLockRepository;

    private static final int BACKUP_BATCH_SIZE = 100;
    private static final Duration TTL = Duration.ofMinutes(10);

    public Long increase(Long articleId, Long userId) {
        if (!articleViewDistributeLockRepository.lock(articleId, userId, TTL)) {
            return count(articleId);
        }
        Long count = articleViewCountRepository.increase(articleId);
        if (count % BACKUP_BATCH_SIZE == 0) {
            articleViewCountBackupProcessor.backup(articleId, count);
        }
        return count;
    }

    public Long count(Long articleId) {
        return articleViewCountRepository.read(articleId);
    }
}
