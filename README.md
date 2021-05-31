<h1 align="center">Kafka Data Streaming </h1> <br>
<h2> Table of Contents </h2>

- [About](#about)
- [Benefit](#benefits)
- [Use-cases](#use-case)
- [Components](#components)
- [Setup](#setup)
- [Info](#info)

## About
Kafka is events/message streaming (Pub/Sub) platform and one of the crucial part of Big Data solution.

![GitHub Logo](/images/kafka-concept.png)

## Benefits
- Decoupling of publishers and subscribers (easy management & change)
- Scaling (N+1)
- Low Latency and high throughput
- Fault Tolerance
- Back-pressure handling
- Reliability
- provide Streaming & Batching

## Use-cases
![GitHub Logo](/images/bigdata.png)
- Asynchronous Messaging
- Realtime Stream Processing
- Logging & Monitoring
- Event Sourcing
- Realtime Analytics

## Core Components
- Publishers (Producers) push message to Kafka
- Consumers (Subscribers) listen and receive messages and provide offset management
- Broker (Kafka Cluster/Instance) receives messages and store replicated within the cluster. Broker handles all requests from clients (produce, consume, and metadata)
- Zookeeper Keeps the state of the cluster (brokers, topics, users)
- Topics are category/feed name to which records are stored and published. similar to a table in DB
- Partitions are if topics are split
- Offset is an incremental id of each message within a partition / id to track message consumption by consumer and partition
- Messages (Events) is unit of Data (Row, record, Map, Blob). Default size limit 1 MB
- Logs (Physical files) are used for storing data and managed by Broker. Logs are multiple files and pruned periodically

## Setup
### [Kafka](https://github.com/yuyatinnefeld/kafka/tree/master/kafka)
### [Java](https://github.com/yuyatinnefeld/kafka/tree/master/java)
### [Scala](https://github.com/yuyatinnefeld/kafka/tree/master/scala)
### [Python](https://github.com/yuyatinnefeld/kafka/tree/master/python)

## Info
- https://kafka.apache.org
- https://dzone.com/articles/hands-on-apache-kafka-with-scala
- https://medium.com/@Ankitthakur/apache-kafka-installation-on-mac-using-homebrew-a367cdefd273
- https://pypi.org/project/kafka-python/
- https://www.confluent.io/what-is-apache-kafka

## Core Concept
![GitHub Logo](/images/kafka_map.png)

## Kafka Cluster
- is the group of Brokers working together
- Each broker has an ID
- share work by partition
- sync through Zookeeper

### Cluster Controller
![GitHub Logo](/images/controller.png)
- One broker becomes the controller
- a controller is also a broker
- first broker to register in Zookeeper => Controller
- Controller can monitor other brokers, partition leader election, replicate management 

## Broker
### Broker Discovery
![GitHub Logo](/images/broker_discovery.png)
- Every Kafka broker is also called a "bootstrap server"
- You only need to connect to one broker than you will be connected to the entire cluster
- Each broker knows about all brokers, topics and partitions (metadata)

### Broker Guarantees
- With a replication factor of N, producer and consumers can tolerate up to N-1 brokers being down
- This is why a replication factor of 3 is a good idea:
    - Allows for one broker to be taken down for maintenance
    - Allows for another broker to be taken down unexpectedly
    
### Replication
![GitHub Logo](/images/replication.png)
- provides resiliency against individual broker failures
- maintains multiple copies of partition logs
- Each partition has multiple replicas (replication-factor)
- only one broker can be leader replica and other follower replicas / ISR (In-sync replica)
- leader broker manages write, reads and storage
- Replication distribution:
    - Controller distributes replicas to brokers
    - Leader replicas of partitions are distributed across leader brokers
    - Follower replicas are distributed to the brokers which are not leaders for the same partition
    
### Leader Broker / Partition Leaders
- Broker owning the leader replica is the partition leader
- Controller assigns partition leaders during topic creation
- All read/writes go to the partition leader
- Partition Leader Resiliency
    - if the partition leader goes down than the controller notified of lost broker
    - Controller chooses new partition leader
    - New leaders are notified by controller (take over read/writes function)

## Zookeeper
![GitHub Logo](/images/zookeeper.png)
- ZK manages brokers (keeps a list of them)
- ZK helps in performing leader election for partitions
- ZK sends notifications to Kafka in case of changes (e.g. new topic, broker dies, broker comes up, etc.)
- Kafka can't work without Zookeeper
- ZK by design operates with an odd number of servers (3,5,7)
- ZK has a leader (handle writes) the rest of the servers are followers (handle reads)
- ZK does NOT store consumers offers with Kafka > v0.10

## Producers
- embedded within the client (as a library)
- New producers are created => Producers contact the bootstrap server, get info on other brokers, get info on topics, partitions and partition leaders
- keep track metadata of changes
- client send messages with send() method => Producer serialize the messages and choose a partition.
- Producer publishing options:
    - Synchronous Mode
    - Asynchronous Mode with No-Check
    - Asynchronous Mode with Callback

### Acknowledgments
- a client may need to know if the message has been received and saved by the broker
- Once the messages are processed, consumer will send an acknowledgement to the Kafka broker.
- Once Kafka receives an acknowledgement, it changes the offset to the new value and updates it in the Zookeeper
- Acknowledgment Values
- ACKs = 0 (Producer won't wait for acknowledgment. possible data loss)
- ACKs = 1 (default / Producer waits for leader acknowledgment. limited data loss)
- ACKs = all (all in-sync replicas should receive the leader acknowledgment. no data loss)

### Producer parameters
- BUFFER.MEMORY = memory in bytes
- COMPRESSION.TYPE = None, gzip, snappy, lz4 or zstd
- BATCH.SIZE = size in bytes
- LINGER.MS = time in milliseconds

## Consumers
- Consumers are typically part of consumer groups
- Each consumer group has a group coordinator
- Consumers read data of consumer groups

### Consumer Offset
![GitHub Logo](/images/offset-steps.png)
- Consumer Offset allows processing to continue from where it last left off if the stream application is turned off or if there is an unexpected failure.
- it means if a consumer DIES, it will be able to read back from where the consumer stopped to read thanks to the committed consumer offsets.
- there 3 delivery semantics: At most once, At least once, Exactly once
- At most once => offsets are committed as soon as the message is received. if the processing goes wrong the message will be lost.
- At least once (usually preferred) => offsets are committed after the message is processed. if the processing goes wrong, the message will be read again but this can be processed duplicated. That's why idempotent processing is recommended
- Exactly once => can be achieved for Kafka through the Kafka Streams API

### Group Coordinator
- is a kafka broker in the cluster
- keeps track of active consumers
- receives heartbeats form consumers
- triggers are rebalanced if heartbeats stop




### Security
- Client authentication (Producers, Consumers, Brokers, ZooKeeper) using SSL/SASL
- Authorization of read/write operation by topic and group
- In-flight encryption using SSL
- Storage encryption - encrypted disks
- public API not recommended => trusted private network