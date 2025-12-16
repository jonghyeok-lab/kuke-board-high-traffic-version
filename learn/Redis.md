# Redis

## Redis-cluster
- Redis 수평 확장
- 샤딩 지원
  - 확장성을 위해 논리적 샤드인 Slot을 지원
    - 16,384개의 Slot
    - Slot은 물리적 샤드에 균등하게 분산된다.
  - shard 선택 방식
    - key 의 hash 값으로 slot(Logical Shard)을 구하고, slot 으로 shard(Physical shard)를 선택
      - slot = hash_function(key)
      - shard = select_shard(slot)
- 서버가 추가되면, 자동으로 데이터가 분산
- 데이터 복제 기능을 제공 -> 고가용성

## Redis 의 디스크 백업
- AOF (Append Only File)
  - 수행된 명령어를 로그 파일에 기록하고, 데이터 복구를 위해 로그를 재실행
- RDB
  - snapshot을 만든다.
  - 저장된 데이터를 주기적으로 파일에 저장

- RDB 백업
  - 조회수를 Redis 에 저장할 경우, 약간의 데이터 유실은 허용한다는 관점 -> 실시간으로 모든 데이터를 백업하지 않음
  - 시간 단위 백업
    - 배치 or 스케줄링으로 N분마다 RDB에 백업
    - 백업 전 장애 시, 데이터 유실
  - 개수 단위 백업
    - N개 단위로 RDB에 백업
    - 조회 시점에 간단히 처리 가능.
    - 백업 전 장애 시, 데이터 유실
    - 개수 단위가 안채워지면 데이터 유실 가능성 있음
  - 두 방법을 조합할 수도 있다.