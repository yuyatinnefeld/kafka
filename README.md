<h1 align="center">Kafka Data Streaming </h1> <br>
<h2>🐍 Table of Contents 🐍</h2>

- [About](#about)
- [Benefit](#benefits)
- [Components](#components)
- [Info](#info)
- [Setup](#setup)

## About
...

## Benefits
...

## Components
...

## Info
- https://kafka.apache.org
- https://dzone.com/articles/hands-on-apache-kafka-with-scala
- https://medium.com/@Ankitthakur/apache-kafka-installation-on-mac-using-homebrew-a367cdefd273
- https://pypi.org/project/kafka-python/
- https://www.confluent.io/what-is-apache-kafka


## Setup
### Docker
### Java
### Scala
### Python


## Example Code

### 1. start zookeeper-server
```bash
zookeeper-server-start /usr/local/etc/kafka/zookeeper.properties
```
### 2. start kafka-server
```bash
kafka-server-start /usr/local/etc/kafka/server.properties
```
### 3. create kafka topic (my example => topicYY)
```bash
kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic topicYY
```
### 4. initialize Producer console:
```bash
kafka-console-producer --broker-list localhost:9092 --topic topicYY
>hallo
>konnichiwa
>domo
>hallo
>you
```
### 5. initialize consumer console:

```bash
kafka-console-consumer --bootstrap-server localhost:9092 --topic topicYY --from-beginning
hallo
>konnichiwa
>domo
>hallo
>you
```

