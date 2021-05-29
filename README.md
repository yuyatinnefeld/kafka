<h1 align="center">Kafka Data Streaming </h1> <br>
<h2>🐍 Table of Contents 🐍</h2>

- [About](#about)
- [Benefit](#benefits)
- [Use cases](#use-case)
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
- Asynchronous Messaging
- Realtime Stream Processing
- Logging & Monitoring
- Event Sourcing
- Realtime Analytics

## Components
- Publishers (Producers) push message to Kafka
- Consumers (Subscribers) listen and receive messages and provide offset management
- Broker (Kafka Cluster/Instance) receives messages and store replicated within the cluster. Broker handles all requests from clients (produce, consume, and metadata)
- Zookeeper Keeps the state of the cluster (brokers, topics, users).
- Topics are category/feed name to which records are stored and published
- Messages (Events) is unit of Data (Row, record, Map, Blob)
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