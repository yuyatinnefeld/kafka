# Kafka Setup
In this project you can configure kafka with homebrew or with docker.

- Setup with Single Node Docker ([Link](#Docker))
- Setup with kafka ([Link](#Homebrew))
- Setup with Multiple Nodes Docker ([Link](#Docker2))

# Docker

## kafka Initial Setup

### kafka docker run
```bash
docker-compose -f kafka-single-node.yml up -d
```

check the containers (kafka-broker, zookeeper)
```bash
docker ps
```
### kafka docker shut down

execute this command in the same directory
```bash
docker-compose -f kafka-single-node.yml down
```

### logging into the kafka broker container
```bash
docker exec -it kafka-broker /bin/bash
cd /opt/bitnami/kafka/bin
```

### create topics
        ./kafka-topics.sh \
            --zookeeper zookeeper:2181 \
            --create \
            --topic kafka.learning.tweets \
            --partitions 1 \
            --replication-factor 1

        ./kafka-topics.sh \
            --zookeeper zookeeper:2181 \
            --create \
            --topic kafka.learning.alerts \
            --partitions 1 \
            --replication-factor 1

        ./kafka-topics.sh \
            --zookeeper zookeeper:2181 \
            --create \
            --topic twitter_status_connect \
            --partitions 3 \
            --replication-factor 1

### check the created topics and their details

        ./kafka-topics.sh \
            --zookeeper zookeeper:2181 \
            --list

        ./kafka-topics.sh \
            --zookeeper zookeeper:2181 \
            --describe

### publishing messages to topic

        ./kafka-console-producer.sh \
            --bootstrap-server localhost:29092 \
            --topic kafka.learning.tweets

+ Write Message
```bash
> this is my first tweet!
> this is my second tweet!
```

### consuming Messages from Topics

+ Open another terminal tab


        ./kafka-console-consumer.sh \
            --bootstrap-server localhost:29092 \
            --topic kafka.learning.tweets \
            --from-beginning


+ Deleting Topics


        ./kafka-topics.sh \
            --zookeeper zookeeper:2181 \
            --delete \
            --topic kafka.learning.alerts

## Check Component Setups

### zookeeper setup
```bash
docker exec -it zookeeper /bin/bash
cd /opt/bitnami/zookeeper/bin
./zkCli.sh
```

### zookeeper components
```bash
ls /
ls /brokers
ls /brokers/topics
ls /brokers/ids
```

### broker details
```bash
get /brokers/ids/<BROKER-ID>

ex.
get /brokers/ids/1001
```

### topic details
```bash
get /brokers/topics/<TOPIC-ID>

ex.
get /brokers/topics/kafka.learning.tweets
```

escape the zookeeper window
```bash
> quit
```

### server.properties details
```bash
docker exec -it kafka-broker /bin/bash
cat /opt/bitnami/kafka/config/server.properties
```

### consumers & logs
```bash
cd /bitnami/kafka/data
ls
cd kafka.learning.tweets-0
cat 00000000000000000000.log
```

## Kafka Partition & Groups

### create a topic with multiple partitions

```bash
cd /opt/bitnami/kafka/bin
```

        ./kafka-topics.sh \
            --zookeeper zookeeper:2181 \
            --create \
            --topic kafka.learning.orders \
            --partitions 3 \
            --replication-factor 1

### check topic partitioning

        ./kafka-topics.sh \
            --zookeeper zookeeper:2181 \
            --topic kafka.learning.orders \
            --describe

### publishing messages to topics with keys

        ./kafka-console-producer.sh \
            --bootstrap-server localhost:29092 \
            --property "parse.key=true" \
            --property "key.separator=:" \
            --topic kafka.learning.orders

```bash
>1001:"macbook, 1000.00"
>1002:"keyboard,30.00"
```

### Check the data in the 3 partition logs
```bash
cd /bitnami/kafka/data
ls kafka.learning.orders*
cat kafka.learning.orders-0/00000000000000000000.log
cat kafka.learning.orders-1/00000000000000000000.log
cat kafka.learning.orders-2/00000000000000000000.log
```

### Check consuming partition data
```bash
docker exec -it kafka-broker /bin/bash
cd /opt/bitnami/kafka/bin
```


Tab1: Producer (Publishing Messages to Topics with keys)

        ./kafka-console-producer.sh \
            --bootstrap-server localhost:29092 \
            --property "parse.key=true" \
            --property "key.separator=:" \
            --topic kafka.learning.orders

Tab2: Consumer1 (with Consumer Group)

        ./kafka-console-consumer.sh \
            --bootstrap-server localhost:29092 \
            --topic kafka.learning.orders \
            --group test-consumer-group \
            --property print.key=true \
            --property key.separator=" = " \
            --from-beginning

Tab3: Consumer2 (with Consumer Group)

        ./kafka-console-consumer.sh \
            --bootstrap-server localhost:29092 \
            --topic kafka.learning.orders \
            --group test-consumer-group \
            --property print.key=true \
            --property key.separator=" = " \
            --from-beginning

![GitHub Logo](/images/topic-group.png)


Tab4: Check current status of offsets

        ./kafka-consumer-groups.sh \
            --bootstrap-server localhost:29092 \
            --describe \
            --all-groups


Result:
```bash

GROUP               TOPIC                 PARTITION  CURRENT-OFFSET  LOG-END-OFFSET  LAG             CONSUMER-ID                                                         HOST            CLIENT-ID
test-consumer-group kafka.learning.orders 0          3               3               0               consumer-test-consumer-group-1-0c62d42f-53d3-4ae4-a0f4-c9d8a5ccc81a /172.18.0.3     consumer-test-consumer-group-1
test-consumer-group kafka.learning.orders 1          5               5               0               consumer-test-consumer-group-1-0c62d42f-53d3-4ae4-a0f4-c9d8a5ccc81a /172.18.0.3     consumer-test-consumer-group-1
test-consumer-group kafka.learning.orders 2          3               3               0               consumer-test-consumer-group-1-66f616e7-6ced-45c9-b69b-4b2d677e1db1 /172.18.0.3     consumer-test-consumer-group-1
```


## Homebrew

### install kafka
```bash
brew install kafka
```

### start zookeeper-server
```bash
zookeeper-server-start /usr/local/etc/kafka/zookeeper.properties
```
### start kafka-server
```bash
kafka-server-start /usr/local/etc/kafka/server.properties
```
### create kafka topic 
```bash
kafka-topics --create --zookeeper <HOST:PORT> --replication-factor <1-N> --partitions <1-N> --topic <TOPIC_NAME>
```
* topic name is NOT allowed with "-" for example. topic-YY, My-Topic
* replication-factor is less than available BROKER number

example: host:port = localhost:2181, replication-factor = 1, partition = 1 topic_name = topicYY

```bash
kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 3 --topic topicYY
```

```bash
kafka-topics --zookeeper localhost:2181 --topic --list
```

```bash
kafka-topics --zookeeper localhost:2181 --topic topicYY --describe
```

### delete kafka topic
```bash
kafka-topics --zookeeper <HOST:PORT> --topic <TOPIC_NAME> --delete
```


### initialize Producer:
```bash
kafka-console-producer --broker-list <HOST:PORT> --topic <TOPIC_NAME> [--producer-property acks=<ACKS-TYPE>]
```


```bash
kafka-console-producer --broker-list localhost:9092 --topic topicYY
>hallo
>konnichiwa
>domo
>hallo
>you
```
### initialize Consumer:
```bash
kafka-console-consumer --bootstrap-server <HOST:PORT> --topic <TOPIC_NAME> [--from-beginning] [--group <GROUP-NAME>]
```
--from-beginning => read message from beginning. without this only reading Realtime data.


```bash
kafka-console-consumer --bootstrap-server localhost:9092 --topic topicYY --from-beginning
hallo
>konnichiwa
>domo
>hallo
>you
```

# Docker2

## kafka Initial Setup for Cluster
```bash
docker-compose -f kafka-cluster.yml up -d
```

## Open Kafka Cluster Manager Site "Kafdrop"
localhost:9000


### kafka docker shut down

execute this command in the same directory
```bash
docker-compose -f kafka-cluster.yml down
```

### create a topic by Kafdrop

![GitHub Logo](/images/create_topic.png)

create topics
![GitHub Logo](/images/create_topic2.png)
![GitHub Logo](/images/create_topic3.png)

### Check the Kafdrop UI
step1
- run com.multiplenodes.simple.KafkaProducer
- open > http://localhost:9000/topic/kafka.learning.orders 
- Consumers > Group ID = kafka-java-consumer
- Combined Lag = 0

![GitHub Logo](/images/groupid.png)

step2
- run com.multiplenodes.simple.KafkaProducer
- open > http://localhost:9000/topic/kafka.learning.orders
- Consumers > Group ID = kafka-java-consumer
- Combined Lag = 20

step3
- restart com.multiplenodes.simple.KafkaSimpleConsumer
- open > http://localhost:9000/topic/kafka.learning.orders
- Consumers > Group ID = kafka-java-consumer
- Combined Lag = 0

### Recover Simulation
step1
- run com.multiplenodes.simple.KafkaProducer
- open > http://localhost:9000/topic/kafka.learning.orders

![GitHub Logo](/images/recover1.png)

Stop the kafka broker3
```bash
docker container stop kafka-broker3
```

leader broker switched
![GitHub Logo](/images/recover2.png)

Restart the kafka broker3
```bash
docker container start kafka-broker3
```
leader broker 1003 reactivated
![GitHub Logo](/images/recover3.png)

