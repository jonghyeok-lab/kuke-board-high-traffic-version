package kuke.board.common.outboxmessagerelay;

import jakarta.persistence.*;
import kuke.board.common.event.EventType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Table(name = "outbox")
@Getter
@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Outbox {
    @Id
    private Long outboxId;

    @Enumerated(EnumType.STRING)
    private EventType eventType;
    private String payload;
    private Long shardKey; // 비즈니스 로직 테이블이랑 아웃박스 테이블이 같은 샤드에서 처리되어야 아웃박스트랜잭션
    private LocalDateTime createdAt;

    public static Outbox create(Long outboxId, EventType eventType, String payload, Long shardKey) {
        Outbox outbox = new Outbox();
        outbox.outboxId = outboxId;
        outbox.eventType = eventType;
        outbox.payload = payload;
        outbox.shardKey = shardKey;
        outbox.createdAt = LocalDateTime.now();
        return outbox;
    }

}
