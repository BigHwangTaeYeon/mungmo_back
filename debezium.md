[최종-connector-설정](#최종-connector-설정)
[mysql-설정](#mysql-설정)
[kafka](#kafka)
[debezium](#debezium)
[avro](#avro)
[connect-설정](#connect-설정)
[source-connect](#source-connect)
[sink-connect](#sink-connect)

환경 java 17, mysql 8.3
kafka_2.13-3.7.0
connect : http://packages.confluent.io/archive/7.5/confluent-community-7.5.0.tar.gz
connector : https://d1i4a15mxbxib1.cloudfront.net/api/plugins/confluentinc/kafka-connect-jdbc/versions/10.6.4/confluentinc-kafka-connect-jdbc-10.6.4.zip

### 최종 connector 설정
```json
{
    "name": "mysql-debezium-connector_oauth_login",
    "config": {
        "connector.class": "io.debezium.connector.mysql.MySqlConnector",
        "database.hostname": "localhost",
        "database.port": "3306",
        "database.user": "mungmo",
        "database.password": "1234",
        "database.server.id": "1",
        "database.server.name": "mungmomember_server",
        "database.include.list": "mungmomember",
        "table.include.list": "mungmomember.oauth_login",
        "database.history.kafka.bootstrap.servers": "localhost:9092",
        "database.history.kafka.topic": "oauth_login_history",
        "schema.history.internal": "io.debezium.storage.file.history.FileSchemaHistory",
        "schema.history.internal.file.filename": "/Users/bighwang/Documents/workspace/MungMo/kafka_2.13-3.7.0/connect/storage/schemahistory.dat",
        "topic.prefix": "test123",
        "include.schema.changes": "true",
        "auto.leader.rebalance.enable": "true",
        "topic.creation.default.replication.factor": 3,
        "topic.creation.default.partitions": 30,

        "transforms": "unwrap",
        "transforms.unwrap.type": "io.debezium.transforms.ExtractNewRecordState",

        "primary.key.mode": "record_key",
        "primary.key.fields": "member_id",

        "snapshot.mode": "initial",
        "key.converter": "io.confluent.connect.avro.AvroConverter",
        "key.converter.schema.registry.url": "http://localhost:8081",
        "value.converter": "io.confluent.connect.avro.AvroConverter",
        "value.converter.schema.registry.url": "http://localhost:8081",
        "key.converter.schemas.enable": "true",
        "value.converter.schemas.enable": "true"
    }
}
```
```json
{
    "name": "mysql-sink-connector_oauth_login",
    "config": {
        "connector.class": "io.confluent.connect.jdbc.JdbcSinkConnector",
        "tasks.max": "1",
        "topics": "test123.mungmomember.oauth_login",
        "connection.url": "jdbc:mysql://localhost:3306/mungmoboard",
        "connection.user": "mungmo",
        "connection.password": "1234",
        "auto.create": "true",
        "auto.evolve": "true",
        "insert.mode": "upsert",
        "pk.mode": "record_key",
        "pk.fields": "member_id",
        "table.name.format": "oauth_login",
        "delete.enabled": "true",
        "key.converter": "io.confluent.connect.avro.AvroConverter",
        "key.converter.schema.registry.url": "http://localhost:8081",
        "key.converter.schemas.enable": "true",
        "value.converter": "io.confluent.connect.avro.AvroConverter",
        "value.converter.schema.registry.url": "http://localhost:8081",
        "value.converter.schemas.enable": "true",

        "transforms": "dropFields,convertCreateDate,convertRecentDate",
        "transforms.dropFields.type": "org.apache.kafka.connect.transforms.ReplaceField$Value",
        "transforms.dropFields.blacklist": "op,ts_ns,transaction,ts_us,before,after,source.ts_ms,source.transaction,source.ts_ns,source",

        "transforms.convertCreateDate.type": "org.apache.kafka.connect.transforms.TimestampConverter$Value",
        "transforms.convertCreateDate.field": "create_date",
        "transforms.convertCreateDate.target.type": "Timestamp",
        "transforms.convertCreateDate.format": "yyyy-MM-dd HH:mm:ss.SSSSSS",
        "transforms.convertCreateDate.unix.precision": "microseconds",

        "transforms.convertRecentDate.type": "org.apache.kafka.connect.transforms.TimestampConverter$Value",
        "transforms.convertRecentDate.field": "recent_date",
        "transforms.convertRecentDate.target.type": "Timestamp",
        "transforms.convertRecentDate.format": "yyyy-MM-dd HH:mm:ss.SSSSSS",
        "transforms.convertRecentDate.unix.precision": "microseconds"
    }
}
```

### mysql 설정
log_bin -> 활성화
binlog(binary log) -> row-level
    binary log는 데이터의 변경사항을 저장하며 복제, 복구를 목적으로 사용합니다.
    대표적으로 replication 구성 시 binlog가 사용됩니다.
    binary log와 별개로 transaction log와 redo log도 존재합니다.

my.cnf 설정 정리

    [mysqld]
    server-id = 1
    log_bin = mysql-bin
    binlog_row_image = FULL
    
    # Statement-based replication 설정
    # binlog_format=STATEMENT
    
    # 또는 Row-based replication 설정
    binlog_format=ROW
    
    # 또는 Mixed replication 설정
    # binlog_format=MIXED

mysql> show grants for mungmo@localhost;
+------------------------------------------------------------------------------------------------------------+
| Grants for mungmo@localhost                                                                                |
+------------------------------------------------------------------------------------------------------------+
| GRANT SELECT, RELOAD, SHOW DATABASES, REPLICATION SLAVE, REPLICATION CLIENT ON *.* TO `mungmo`@`localhost` |
| GRANT ALL PRIVILEGES ON `mungmoboard`.* TO `mungmo`@`localhost`                                            |
| GRANT ALL PRIVILEGES ON `mungmochat`.* TO `mungmo`@`localhost`                                             |
| GRANT ALL PRIVILEGES ON `mungmomember`.* TO `mungmo`@`localhost`                                           |
+------------------------------------------------------------------------------------------------------------+
4 rows in set (0.00 sec)

### kafka
    환경
kafka_2.13-3.7.0
connect : curl -O http://packages.confluent.io/archive/7.5/confluent-community-7.5.0.tar.gz
connector : curl -O https://d1i4a15mxbxib1.cloudfront.net/api/plugins/confluentinc/kafka-connect-jdbc/versions/10.6.4/confluentinc-kafka-connect-jdbc-10.6.4.zip

    실행
$ bin/zookeeper-server-start.sh -daemon config/zookeeper.properties
$ bin/kafka-server-start.sh -daemon config/server.properties
$ bin/connect-distributed -daemon ./etc/kafka/connect-distributed.properties

Zookeeper: 2181
Kafka Broker: 9092
Kafka Connect REST API: 8083

bin/kafka-server-stop.sh && bin/kafka-server-start.sh -daemon config/server.properties

$ bin/zookeeper-server-start.sh -daemon config/zookeeper.properties
$ bin/kafka-server-start.sh -daemon config/server.properties

Zookeeper 의 기본 포트는 2181이고 Kafka 의 기본 포트는 9092
$ lsof -i :9092     (pid 확인)
$ kill -9 1523

bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --from-beginning --topic test

$ bin/kafka-topics.sh --bootstrap-server localhost:9092 --list
$ bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --topic test --partitions 1
$ bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --topic topicname --partitions 1 --replication-factor 1

$ bin/kafka-topics.sh --delete --topic HereTopicName --bootstrap-server localhost:9092
topic 삭제를 하여면 먼저 생성한 connector 부터 삭제해야한다.
커넥터 삭제 : DELETE http://localhost:8083/connectors/my-sink-connect-member
토픽 삭제 : bin/kafka-topics.sh --delete --topic my_topic_test001 --bootstrap-server localhost:9092
확인 : bin/kafka-topics.sh --bootstrap-server localhost:9092 --list

Producer 등록
$ bin/kafka-console-producer.sh --broker-list localhost:9092 --topic HereTopicName
$ bin/kafka-console-producer.sh --broker-list localhost:9092 --topic topicname

Consumer 등록
$ bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic HereTopicName --from-beginning
$ bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic topicname --from-beginning

connect
$ bin/connect-distributed -daemon ./etc/kafka/connect-distributed.properties
$ lsof -i :8083

mariadb-java-client-2.7.2.jar 파일을 kafka connect → share/java/kafka로 복사함

connect-distributed.properties 파일은 Apache Kafka 의 Connect 분산 모드에서 사용되는 설정 파일입니다.
```properties
plugin.path=/usr/share/java
```
위 경로를 jdbc.jar 파일 경로로 변경해야한다.
```properties
plugin.path=/Users/userName/Documents/workspace/MungMo/confluentinc-kafka-connect-jdbc-10.6.4/lib/
```

1. 토픽 생성
```json
   {
       "name" : "test-connect-mungmo-member004",
       "config" : {
           "connector.class" : "io.confluent.connect.jdbc.JdbcSourceConnector",
           "connection.url":"jdbc:mysql://localhost:3306/mungmoMember",
           "connection.user":"mungmo",
           "connection.password":"1234",
           "mode": "incrementing",
           "incrementing.column.name" : "member_id",
           "table.whitelist":"mungmoMember.oauth_login",
           "topic.prefix" : "service_",
           "tasks.max" : "1"
       }
   }
```
kafka 토픽에 저장될 이름 형식 지정 위 같은경우 whitelist를 뒤에 붙여 example_topic_users에 데이터가 들어감
tasks.max : 커넥터에 대한 작업자 수

참고로 table.whitelist 에 dot 로 해당_데이터배이스.테이블_명 으로 명시해주어야 한다.

2. sink connect 생성
```json
   {
       "name":"my-sink-connect-member003",
       "config":{
           "connector.class":"io.confluent.connect.jdbc.JdbcSinkConnector",
           "connection.url":"jdbc:mysql://localhost:3306/mungmoBoard",
           "connection.user":"mungmo",
           "connection.password":"1234",
           "auto.create":"true",
           "auto.evolve":"true",
           "delete.enabled":"false",
           "tasks.max":"1",
           "topics":"service_oauth_login"
       }
   }
```

auto.create : 데이터를 넣을 테이블이 누락되었을 경우 자동 테이블 생성 여부
auto.evolve : 특정 데이터의 열이 누락된 경우 대상 테이블에 ALTER 구문을 날려 자동으로 테이블 구조를 바꾸는지 여부 (하지만 데이터 타입 변경, 컬럼 제거, 키본 키 제약 조건 추가등은 시도되지 않는다)
delete.enabled : 삭제 모드 여부

```text
/**
* CDC 를 통한 테이블 생성 시 문제.
* 1. jpa 로 복제 테이블을 생성하면 컨슈머에서 insert 를 하지 않음
* 2. 일반적으로 개발 단계에서 jpa ddl-auto 설정을 update 로 하고 토픽을 통해 복제 테이블을 생성한 후, create 해줘야함.
* 3. 이렇게 생성하면 테이블이 내가 설계한대로 생성, 수정되지않고 뒤죽박죽이 되어 디테일 설정을 해줘야한다.
*
* 해결
* 1. 복제 테이블인 service_oauth_login의 PRIMARY KEY (member_id) 설정과 unique(town_id) 설정.
* 2. 컨슈머가 가지고 있는 메시지를 통해 복제 테이블의 데이터를 생성하여 엔티티에 설정한 alter foreign key 가 문제가 되는데,
* 		insert 된 데이터를 연관관계의 테이블에도 참조 무결성이 위배되지 않게 데이터를 입력해줘야한다.
* 		foreign key 를 가지고 있는 테이블은 자식 테이블이 되기에 부모테이블의 참조값을 무조건 가지고 있어야한다. 그러므로 not null
* 		INSERT INTO service_town  values(2,'seoul',false, '2024-07-06 03:59:13');
*/
```

3. table 또는 데이터 생성 수정 삭제
   mungMo 데이터베이스에서 dml 에 의해 mungmoMember 에 데이터 복제

connect mode 로 incrementing 를 사용한 결과, 수정을 감지못하는 상황을 확인하였다.
kafka connect mode
bulk : 데이터를 폴링할 때 마다 전체 테이블을 복사
incrementing : 특정 컬럼의 중가분만 감지되며, 기존 행의 수정과 삭제는 감지되지 않음
timestamp : timestamp형 컬럼일 경우, 새 행과 수정된 행을 감지함

벌크는 모든 데이터 삭제, 삽입이 오버헤드로 느껴지고, 타임스탬프는 모든 테이블에 컬럼을 추가하고, 수정 시 같이 변경해줘야한다는 것이 부담이 되었다.

cdc 도구로 Debezium 를 사용하기로 하였다.

### debezium
흐름
소스 커넥터(Debezium) :소스 데이터베이스에서 변경 사항을 캡처하고 이러한 변경 사항을 Kafka 토픽으로 스트리밍.
카프카 :캡처된 변경 사항을 저장하고 전달.
싱크 커넥터 :Kafka 토픽의 변경 사항을 사용하여 대상 데이터베이스에 기록.


설치(mysql 기준)
    wget https://repo1.maven.org/maven2/io/debezium/debezium-connector-mysql/1.9.6.Final/debezium-connector-mysql-1.9.6.Final-plugin.tar.gz
    tar -xzf debezium-connector-mysql-1.9.6.Final-plugin.tar.gz -C ~/kafka/connectors

***io.debezium.connector.mysql.MySqlConnector class 를 찾지 못하는 문제로인해 share/java/kafka 안에 connect 관련 소스를 모두 넣어줬다.***

Kafka Connect 실행 스크립트 (kafka/connect-distributed.properties)
```properties
    bootstrap.servers=localhost:9092
    group.id=connect-cluster
    key.converter=org.apache.kafka.connect.json.JsonConverter
    value.converter=org.apache.kafka.connect.json.JsonConverter
    key.converter.schemas.enable=true
    value.converter.schemas.enable=true
    offset.storage.topic=connect-offsets
    offset.storage.replication.factor=1
    config.storage.topic=connect-configs
    config.storage.replication.factor=1
    status.storage.topic=connect-status
    status.storage.replication.factor=1
    offset.flush.interval.ms=10000
    plugin.path=/Users/~/kafka_2.13-3.7.0/connect
```

```text
key.converter, value.converter
    데이터를 카프카에 저장할 때 혹은 카프카에서 데이터를 가져올 때 변환하는 데에 사용
    JsonConverter, StringConverter, ByteArrayConverter를 기본으로 제공
    만약 사용하고 싶지 않다면 key.converter.schemas.enable=false or value.converter.schemas.enable=false로 설정
offset.storage.file.filename
    단일 모드 커넥터는 로컬 파일에 오프셋 정보를 저장하며, 이 정보는 소스 커넥터 또는 싱크 커넥터가 데이터 처리 시점을 저장하기 위해 사용
    해당 정보는 다른 사용자나 시스템이 접근하지 않도록 주의해야 함
offset.flush.interval.ms
    태스크가 처리 완료한 오프셋을 커밋하는 주기
plugin.path 
    debezium 압축 해제 위치
```

http://localhost:8081/subjects/test005.mungmoMember.oauth_login-key/versions/latest

bin/zookeeper-server-start.sh -daemon config/zookeeper.properties
bin/kafka-server-start.sh -daemon config/server-1.properties
bin/kafka-server-start.sh -daemon config/server-2.properties
bin/kafka-server-start.sh -daemon config/server-3.properties

bin/zookeeper-server-stop.sh -daemon config/zookeeper.properties
bin/kafka-server-stop.sh -daemon config/server-1.properties
bin/kafka-server-stop.sh -daemon config/server-2.properties
bin/kafka-server-stop.sh -daemon config/server-3.properties

bin/kafka-topics.sh --bootstrap-server localhost:9092 --list

bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --from-beginning --topic oauth_login_history

bin/kafka-topics.sh --describe --bootstrap-server localhost:9092 --topic oauth_login_history

bin/kafka-topics.sh --create --topic maxwell_test --bootstrap-server localhost:9092 --partitions 3 --replication-factor 3

bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --partitions 3 --replication-factor 3 --topic chat_massage
bin/kafka-topics.sh --create --topic chat_notification --bootstrap-server localhost:9092 --partitions 3 --replication-factor 3

bin/kafka-topics.sh --delete --topic connect-configs --bootstrap-server localhost:9092
bin/kafka-topics.sh --delete --topic connect-offsets --bootstrap-server localhost:9092
bin/kafka-topics.sh --delete --topic connect-status --bootstrap-server localhost:9092

bin/kafka-topics.sh --delete --topic __consumer_offsets --bootstrap-server localhost:9092
bin/kafka-topics.sh --delete --topic _dek_registry_keys --bootstrap-server localhost:9092
bin/kafka-topics.sh --delete --topic _schema_encoders --bootstrap-server localhost:9092
bin/kafka-topics.sh --delete --topic _schemas --bootstrap-server localhost:9092

bin/kafka-topics.sh --delete --bootstrap-server localhost:9092 --topic test119
bin/kafka-topics.sh --delete --bootstrap-server localhost:9092 --topic test119.mungmoMember.oauth_login

connector 실행 : bin/connect-distributed ./etc/kafka/connect-distributed.properties
registry  실행 : bin/schema-registry-start ./etc/schema-registry/schema-registry.properties
registry consumer message : bin/kafka-avro-console-consumer --bootstrap-server localhost:9092 --topic test005.mungmoMember.oauth_login --from-beginning --property schema.registry.url=http://localhost:8081 --property print.key=true

##### source connect

curl -i -X POST -H "Accept:application/json" -H "Content-Type:application/json" localhost:8083/connectors/ -d
```json
{
    "name": "mysql-debezium-connector_oauth_login",
    "config": {
        "connector.class": "io.debezium.connector.mysql.MySqlConnector",
        "database.hostname": "localhost",
        "database.port": "3306",
        "database.user": "mungmo",
        "database.password": "1234",
        "database.server.id": "184054",
        "database.server.name": "mungmoMember_server",
        "database.include.list": "mungmoMember",
        "table.include.list": "mungmoMember.oauth_login",
        "database.history.kafka.bootstrap.servers": "localhost:9092",
        "database.history.kafka.topic": "oauth_login_history",
        "schema.history.internal": "io.debezium.storage.file.history.FileSchemaHistory",
        "schema.history.internal.file.filename": "//Users/bighwang/Documents/workspace/MungMo/kafka_2.13-3.7.0/connect/storage/schemahistory.dat",
        "topic.prefix": "test",
        "include.schema.changes": "true",
        "transforms": "unwrap",
        "transforms.unwrap.type": "io.debezium.transforms.ExtractNewRecordState",
        "transforms.unwrap.drop.tombstones": "false"
    }
}
```
database.server.id: MySQL 복제 서버 ID. 다른 복제 서버와 고유해야 합니다.
database.server.name: Kafka 토픽 이름의 접두사로 사용됩니다.
database.history.kafka.topic: 스키마 변경 내역을 저장할 Kafka 토픽 이름.
transforms 및 transforms.unwrap.type: Debezium의 ExtractNewRecordState 변환을 사용하여 캡처된 데이터에서 새로운 레코드 상태를 추출.

서비스마다 sql 서버를 분리하여 사용할 경우, server.id 의 고유 식별자로 분리시켜야하지만.
    나는 하나의 sql 로 데이터베이스를 분리해서 사용하기에 통일해도 좋다.
대신 server.name 으로 구분이 가능하다.
database.history.kafka.topic 은 자동으로 생성되며, 수동으로도 가능하다.


- Failed to find any class that implements Connector and which name matches io.debezium.connector.mysql.MySqlConnector 에러
io.debezium.connector.mysql.MySqlConnector class 를 찾지 못하는 문제로인해 share/java/kafka 안에 connect 관련 소스를 모두 넣어줬다.

- Error configuring an instance of KafkaSchemaHistory;
"schema.history.internal": "io.debezium.storage.file.history.FileSchemaHistory",
"schema.history.internal.file.filename": "//Users/bighwang/Documents/workspace/MungMo/kafka_2.13-3.7.0/connect/storage/schemahistory.dat",
schema.history.internal.kafka.topic : include.schema.changes 속성 설정에 따른 스키마 변경사항(DDL)을 기록하는 것과 별도로
Debezium connector에서 별도로 스키마 변경 히스토리를 기록하는 토픽을 지정하는 속성 입니다.
참조 : https://debezium.io/documentation/reference/stable/development/engine.html

##### sink connect

http://localhost:8083/connectors POST
```json
{
    "name": "mysql-sink-connector_oauth_login",
    "config": {
        "connector.class": "io.confluent.connect.jdbc.JdbcSinkConnector",
        "tasks.max": "1",
        "topics": "service_oauth_login",
        "connection.url": "jdbc:mysql://localhost:3306/target_database",
        "connection.user": "mungmo",
        "connection.password": "1234",
        "auto.create": "true",
        "auto.evolve": "true",
        "insert.mode": "insert",
        "table.name.format": "oauth_login",
        "pk.mode": "none",
        "key.converter": "org.apache.kafka.connect.storage.StringConverter",
        "value.converter": "org.apache.kafka.connect.json.JsonConverter",
        "value.converter.schemas.enable": "false"
    }
}
```
auto.create: 테이블이 존재하지 않을 경우 자동으로 생성 (true).
auto.evolve: 스키마 변경 시 테이블을 자동으로 수정 (true).
insert.mode: 데이터 삽입 모드 (insert).
pk.mode: 기본 키 모드 (none).

insert.mode 종류
해당 테이블의 히스토리를 보고싶다 : insert
최신 데이터를 보고싶다 : upsert
ㅁ upsert
    "pk.mode": "record_key",
    "delete.enabled": "true",
    "insert.mode": "upsert",
ㅁ insert
"insert.mode": "insert",

- Access denied; you need (at least one of) the SUPER, REPLICATION CLIENT privilege(s) for this operation
GRANT RELOAD, REPLICATION SLAVE, REPLICATION CLIENT ON *.* TO `user`@`localhost`
이 GRANT RELOAD, REPLICATION SLAVE, REPLICATION CLIENT 세가지 권한이 필요했다.
  GRANT REPLICATION SLAVE ON *.* TO 'mungmo'@'localhost'
  SHOW GRANTS FOR 'mungmo'@'localhost';
  FLUSH PRIVILEGES;

- The deleted record handling configs "drop.tombstones" and "delete.handling.mode" have been deprecated, please use "delete.tombstone.handling.mode" instead.
삭제된 기록 처리 구성 "drop.tombstones" 및 "delete.handling.mode"는 더 이상 사용되지 않습니다. 대신 "delete.tombstone.handling.mode"를 사용하십시오.
"transforms.unwrap.delete.tombstone.handling.mode": "false",

- Caused by: io.debezium.DebeziumException: Encountered change event for table mungmomember.oauth_login whose schema isn't known to this connector
이 커넥터에 스키마가 알려지지 않은 mungmomember.oauth_login 테이블에 대한 변경 이벤트가 발생했습니다.
"table.include.list": "oauth_login"
처음에는 mungmoMember.oauth_login 으로 했다.

##### Avro

- java.lang.ClassCastException: class java.util.HashMap cannot be cast to class org.apache.kafka.connect.data.Struct
참조 : https://docs.confluent.io/platform/current/schema-registry/connect.html
    1. brew install confluent-platform
    2. 스키마 레지스트리 시작 localhost:8081: $ ./bin/schema-registry-start ./etc/schema-registry/schema-registry.properties

```json
{
    "name": "mysql-debezium-connector_oauth_login",
    "config": {
        "connector.class": "io.debezium.connector.mysql.MySqlConnector",
        "database.hostname": "localhost",
        "database.port": "3306",
        "database.user": "mungmo",
        "database.password": "1234",
        "database.server.id": "184054",
        "database.server.name": "mungmoMember_server",
        "database.include.list": "mungmoMember",
        "table.include.list": "mungmoMember.oauth_login",
        "database.history.kafka.bootstrap.servers": "localhost:9092",
        "database.history.kafka.topic": "oauth_login_history",
        "schema.history.internal": "io.debezium.storage.file.history.FileSchemaHistory",
        "schema.history.internal.file.filename": "/Users/bighwang/Documents/workspace/MungMo/kafka_2.13-3.7.0/connect/storage/schemahistory.dat",
        "topic.prefix": "test005",
        "include.schema.changes": "true",

        "primary.key.mode": "record_key",
        "primary.key.fields": "member_id",

        "snapshot.mode": "initial",
        "key.converter": "io.confluent.connect.avro.AvroConverter",
        "key.converter.schema.registry.url": "http://localhost:8081",
        "value.converter": "io.confluent.connect.avro.AvroConverter",
        "value.converter.schema.registry.url": "http://localhost:8081",
        "key.converter.schemas.enable": "true",
        "value.converter.schemas.enable": "true"
    }
}
```

```json
{
    "name": "mysql-sink-connector_oauth_login",
    "config": {
        "connector.class": "io.confluent.connect.jdbc.JdbcSinkConnector",
        "tasks.max": "1",
        "topics": "test005.mungmoMember.oauth_login",
        "connection.url": "jdbc:mysql://localhost:3306/mungmoBoard",
        "connection.user": "mungmo",
        "connection.password": "1234",
        "auto.create": "true",
        "auto.evolve": "true",
        "insert.mode": "upsert",
        "pk.mode": "record_key",
        "pk.fields": "member_id",
        "table.name.format": "oauth_login",
        "delete.enabled": "true",
        "key.converter": "io.confluent.connect.avro.AvroConverter",
        "key.converter.schema.registry.url": "http://localhost:8081",
        "value.converter": "io.confluent.connect.avro.AvroConverter",
        "value.converter.schema.registry.url": "http://localhost:8081",
        "key.converter.schemas.enable": "true",
        "value.converter.schemas.enable": "true"
    }
}
```

Caused by: io.debezium.DebeziumException: Encountered change event for table mungmomember.oauth_login whose schema isn't known to this connector
- GRANT SELECT, RELOAD, SHOW DATABASES, REPLICATION SLAVE, REPLICATION CLIENT ON *.* TO 'mungmo'@'%';
- [mysqld]
  server-id=1
  log_bin=mysql-bin
  binlog_format=ROW
- "topic.creation.default.replication.factor": 1,
  "topic.creation.default.partitions": 1,


```json
{
    "name": "mysql-debezium-connector_oauth_login",
    "config": {
        "connector.class": "io.debezium.connector.mysql.MySqlConnector",
        "database.hostname": "localhost",
        "database.port": "3306",
        "database.user": "mungmo",
        "database.password": "1234",
        "database.server.id": "184054",
        "database.server.name": "mungmoMember_server",
        "database.include.list": "mungmoMember",
        "table.include.list": "mungmoMember.oauth_login",
        "database.history.kafka.bootstrap.servers": "localhost:9092",
        "database.history.kafka.topic": "oauth_login_history",
        "schema.history.internal": "io.debezium.storage.file.history.FileSchemaHistory",
        "schema.history.internal.file.filename": "/Users/bighwang/Documents/workspace/MungMo/kafka_2.13-3.7.0/connect/storage/schemahistory.dat",
        "topic.prefix": "test100",
        "include.schema.changes": "true",

        "primary.key.mode": "record_key",
        "primary.key.fields": "member_id",

        "auto.leader.rebalance.enable": "true",

        "transforms": "unwrap",
        "transforms.unwrap.type": "io.debezium.transforms.ExtractNewRecordState",

        "topic.creation.default.replication.factor": 1,
        "topic.creation.default.partitions": 1,

        "snapshot.mode": "initial",
        "key.converter": "io.confluent.connect.avro.AvroConverter",
        "key.converter.schema.registry.url": "http://localhost:8081",
        "value.converter": "io.confluent.connect.avro.AvroConverter",
        "value.converter.schema.registry.url": "http://localhost:8081",
        "key.converter.schemas.enable": "true",
        "value.converter.schemas.enable": "true"
    }
}
```
```json
{
    "name": "mysql-sink-connector_oauth_login",
    "config": {
        "connector.class": "io.confluent.connect.jdbc.JdbcSinkConnector",
        "tasks.max": "1",
        "topics": "test100.mungmoMember.oauth_login",
        "connection.url": "jdbc:mysql://localhost:3306/mungmoBoard",
        "connection.user": "mungmo",
        "connection.password": "1234",
        "auto.create": "true",
        "auto.evolve": "true",
        "insert.mode": "upsert",
        "pk.mode": "record_key",
        "pk.fields": "member_id",
        "table.name.format": "oauth_login",
        "delete.enabled": "true",
        "key.converter": "io.confluent.connect.avro.AvroConverter",
        "key.converter.schema.registry.url": "http://localhost:8081",
        "key.converter.schemas.enable": "true",
        "value.converter": "io.confluent.connect.avro.AvroConverter",
        "value.converter.schema.registry.url": "http://localhost:8081",
        "value.converter.schemas.enable": "true",

        "transforms": "dropFields,convertCreateDate,convertRecentDate",
        "transforms.dropFields.type": "org.apache.kafka.connect.transforms.ReplaceField$Value",
        "transforms.dropFields.blacklist": "op,ts_ns,transaction,ts_us,before,after,source.ts_ms,source.transaction,source.ts_ns,source",

        "transforms.convertCreateDate.type": "org.apache.kafka.connect.transforms.TimestampConverter$Value",
        "transforms.convertCreateDate.field": "create_date",
        "transforms.convertCreateDate.target.type": "Timestamp",
        "transforms.convertCreateDate.format": "yyyy-MM-dd HH:mm:ss.SSSSSS",
        "transforms.convertCreateDate.unix.precision": "microseconds",

        "transforms.convertRecentDate.type": "org.apache.kafka.connect.transforms.TimestampConverter$Value",
        "transforms.convertRecentDate.field": "recent_date",
        "transforms.convertRecentDate.target.type": "Timestamp",
        "transforms.convertRecentDate.format": "yyyy-MM-dd HH:mm:ss.SSSSSS",
        "transforms.convertRecentDate.unix.precision": "microseconds"
    }
}
```

"database.history.kafka.topic": "oauth_login_history" 
- server.properties auto.create.topics.enable=true 추가

io.debezium.DebeziumException: Encountered change event for table mungmomember.oauth_login whose schema isn't known to this connector

### connect 설정

localhost:8083/connectors
```json
{
    "name": "mysql-debezium-connector_oauth_login",
    "config": {
        "connector.class": "io.debezium.connector.mysql.MySqlConnector",
        "database.hostname": "localhost",
        "database.port": "3306",
        "database.user": "mungmo",
        "database.password": "1234",
        "database.server.id": "1",
        "database.server.name": "mungmoMember_server",
        "database.include.list": "mungmoMember",
        "table.include.list": "mungmoMember.oauth_login",
        "database.history.kafka.bootstrap.servers": "localhost:9092",
        "database.history.kafka.topic": "oauth_login_history",
        "schema.history.internal": "io.debezium.storage.file.history.FileSchemaHistory",
        "schema.history.internal.file.filename": "/Users/bighwang/Documents/workspace/MungMo/kafka_2.13-3.7.0/connect/storage/schemahistory.dat",
        "topic.prefix": "test112",
        "include.schema.changes": "true",

        "primary.key.mode": "record_key",
        "primary.key.fields": "member_id",

        "auto.leader.rebalance.enable": "true",

        "transforms": "unwrap",
        "transforms.unwrap.type": "io.debezium.transforms.ExtractNewRecordState",

        "topic.creation.default.replication.factor": 1,
        "topic.creation.default.partitions": 1,

        "snapshot.mode": "initial",
        "key.converter": "io.confluent.connect.avro.AvroConverter",
        "key.converter.schema.registry.url": "http://localhost:8081",
        "value.converter": "io.confluent.connect.avro.AvroConverter",
        "value.converter.schema.registry.url": "http://localhost:8081",
        "key.converter.schemas.enable": "true",
        "value.converter.schemas.enable": "true"
    }
}
```

localhost:8083/connectors
```json
{
    "name": "mysql-sink-connector_oauth_login",
    "config": {
        "connector.class": "io.confluent.connect.jdbc.JdbcSinkConnector",
        "tasks.max": "1",
        "topics": "test108.mungmoMember.oauth_login",
        "connection.url": "jdbc:mysql://localhost:3306/mungmoBoard",
        "connection.user": "mungmo",
        "connection.password": "1234",
        "auto.create": "true",
        "auto.evolve": "true",
        "insert.mode": "upsert",
        "pk.mode": "record_key",
        "pk.fields": "member_id",
        "table.name.format": "oauth_login",
        "delete.enabled": "true",
        "key.converter": "io.confluent.connect.avro.AvroConverter",
        "key.converter.schema.registry.url": "http://localhost:8081",
        "key.converter.schemas.enable": "true",
        "value.converter": "io.confluent.connect.avro.AvroConverter",
        "value.converter.schema.registry.url": "http://localhost:8081",
        "value.converter.schemas.enable": "true",

        "transforms": "dropFields,convertCreateDate,convertRecentDate",
        "transforms.dropFields.type": "org.apache.kafka.connect.transforms.ReplaceField$Value",
        "transforms.dropFields.blacklist": "op,ts_ns,transaction,ts_us,before,after,source.ts_ms,source.transaction,source.ts_ns,source",

        "transforms.convertCreateDate.type": "org.apache.kafka.connect.transforms.TimestampConverter$Value",
        "transforms.convertCreateDate.field": "create_date",
        "transforms.convertCreateDate.target.type": "Timestamp",
        "transforms.convertCreateDate.format": "yyyy-MM-dd HH:mm:ss.SSSSSS",
        "transforms.convertCreateDate.unix.precision": "microseconds",

        "transforms.convertRecentDate.type": "org.apache.kafka.connect.transforms.TimestampConverter$Value",
        "transforms.convertRecentDate.field": "recent_date",
        "transforms.convertRecentDate.target.type": "Timestamp",
        "transforms.convertRecentDate.format": "yyyy-MM-dd HH:mm:ss.SSSSSS",
        "transforms.convertRecentDate.unix.precision": "microseconds"
    }
}
```
