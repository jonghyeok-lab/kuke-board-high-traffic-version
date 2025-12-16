# Kafka Cluster

## Kafka
- 분산 이벤트 스트리밍 플랫폼
- 대규모 데이터를 실시간으로 처리하기 위해 사용
- 고성능, 확장성, 내구성, 가용성

## pub/sub 패턴
  - Producer -> Message Broker -> Consumer
    - Producer 의 처리량이 MessageBroker 나 Consumer의 처리량이 넘어가면
    - 리소스 장애 및 데이터 유실 가능.
  - Producer -> Message Broker <- Consumer
    - Consumer 는 자신의 처리량에 맞게 Message Broker 에서 데이터를 가져온다.(구독한다)
    - 이를 pub/sub 패턴이라 함.

## Kafka는 어떻게 데이터를 구분할까?
- Producer는 topic 단위로 이벤트를 생산 및 전송
- Consumer는 topic 단위로 이벤트를 구독 및 소비
- topic: Kafka에서 생산 및 소비되는 데이터를 논리적으로 구분하는 단위

- Message Broker 를 3대로 구성한 Cluster를 구축하면 topic 을 병렬로 처리할 수 있다.
  - topic 은 partition 단위로 물리적으로 분산될 수 있다.
    - topic1과 2의 partition = 3? 각 topic은 각 3개의 partition으로 분산해 데이터를 처리.
      - 각 topic 의 partition은 여러 Broker 에 균등하게 분산 처리 가능.
      - Producer 와 consumer 는 topic 의 partition 단위로 데이터를 생산/처리
    - 순서 보장이 필요한 경우? -> 동일한 partition 으로 보내준다.

## Cluster에서 Broker 1대가 장애 상황
- replication factor=3 설정을 통해, 각 partition의 데이터가 3개로 복제된다.
- 각 복제본은 Kafka에서 여러 Broker 간에 균등하게 분산해준다. (leader1, follower1, follower2)
  - Producer가 이 과정을 기다려야 할까?
    - Producer 의 acks 설정
      - acks = 0 : Broker에 데이터 전달되었는지 확인 X (데이터 유실 가능성 있음)
      - acks = 1 : leader에 전달되면 성공, follower 전달 안되면 장애 시 유실 가능성 있음.
      - acks = all : leader와 모든 follower(min.insync.replicas 만큼)에 데이터 기록되면 성공.
        - min.insync.replicas: 데이터 전송 성공으로 간주하려면 최소 몇개의 ISR이 있어야 하는지 설정
          - ISR(In-Sync Replicas): leader의 데이터가 복제본으로 동기화되어 있는 follower들을 의미

## Kafka의 데이터 저장 방법
- 순서가 보장된 데이터 로그를 각 topic의 partition 단위로 Broker의 디스크에 저장
  - 각 데이터는 고유한 offset을 가짐. Consumer는 offset기반으로 데이터를 읽어감.
- Consumer1과 Consumer2가 같은 데이터(같은 topic의 같은 partition)를 읽어야 한다면?
  - offset이 Consumer 간 따로 관리되야 한다. -> Consumer Group 등장
  - Offset은 Consumer Group 단위로 관리된다.
- Zookeeper 가 Kafka의 메타데이터(offset, partition, broker..)를 관리하고, 고가용성을 위해 여러 대를 연결해 클러스터 구조를 이룰 수 있다.(당연 복잡성 증가)
  - Kafka 2.8부터 Kafka Broker 자체적으로 관리할수 있게 됨
  - KRaft 모드로 Zookeeper를 의존성 제거.
