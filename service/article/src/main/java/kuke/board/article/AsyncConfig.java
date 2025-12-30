package kuke.board.article;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class AsyncConfig {

    @Bean(name = "taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("async-");

        // 우아한 종료 설정
        executor.setWaitForTasksToCompleteOnShutdown(true); // 큐 작업 완료 대기
        executor.setAwaitTerminationSeconds(25); // 최대 대기 시간 (graceful timeout보다 짧게)

        executor.initialize();
        return executor;
    }
}
