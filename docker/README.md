
## Initial Kafka Setup with Docker

### run kafka with docker-compose

> docker-compose -f kafka-single-node.yml up -d

check the containers (kafka-broker, zookeeper)
> docker ps

To shut down and remove the setup, execute this command in the same directory
> docker-compose -f kafka-single-node.yml down

### Logging into the Kafka Broker Container
> docker exec -it kafka-broker /bin/bash

> cd /opt/bitnami/kafka/bin

### Create topics


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


###Check the created topics and their deteils

        ./kafka-topics.sh \
            --zookeeper zookeeper:2181 \
            --list

        ./kafka-topics.sh \
            --zookeeper zookeeper:2181 \
            --describe

###Publishing Messages to Topics

        ./kafka-console-producer.sh \
            --bootstrap-server localhost:29092 \
            --topic kafka.learning.tweets

+ Write Message

> this is my first tweet!
> this is my second tweet!


###Consuming Messages from Topics

+ Open another terminal tab

        ./kafka-console-consumer.sh \
            --bootstrap-server localhost:29092 \
            --topic kafka.learning.tweets \
            --from-beginning


Deleting Topics

        ./kafka-topics.sh \
            --zookeeper zookeeper:2181 \
            --delete \
            --topic kafka.learning.alerts


## Kafka Configuration Check

#### open Zookeeper setup
docker exec -it zookeeper /bin/bash
cd /opt/bitnami/zookeeper/bin
./zkCli.sh

#### check Zookeeper components

ls /
ls /brokers
ls /brokers/topics
ls /brokers/ids

#### check Broker details
get /brokers/ids/<BROKER-ID>

ex.
get /brokers/ids/1001

#### check Topic details
get /brokers/topics/<TOPIC-ID>

ex.
get /brokers/topics/kafka.learning.tweets

escape the detail window
quit

#### check Server.properties details
> docker exec -it kafka-broker /bin/bash

cat /opt/bitnami/kafka/config/server.properties

#### check Consumers & Logs

cd /bitnami/kafka/data
ls
cd kafka.learning.tweets-0
cat 00000000000000000000.log


## Kafka Partition & Groups

###Create a Topic with multiple partitions

> cd /opt/bitnami/kafka/bin

        ./kafka-topics.sh \
            --zookeeper zookeeper:2181 \
            --create \
            --topic kafka.learning.orders \
            --partitions 3 \
            --replication-factor 1

###Check topic partitioning

        ./kafka-topics.sh \
            --zookeeper zookeeper:2181 \
            --topic kafka.learning.orders \
            --describe

### Publishing Messages to Topics with keys

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
cd /bitnami/kafka/data
ls kafka.learning.orders*
cat kafka.learning.orders-0/00000000000000000000.log
cat kafka.learning.orders-1/00000000000000000000.log
cat kafka.learning.orders-2/00000000000000000000.log

### Check consuming partition data

> docker exec -it kafka-broker /bin/bash
> cd /opt/bitnami/kafka/bin


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

BILD


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