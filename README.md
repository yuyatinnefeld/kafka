<h1 align="center">Kafka Data Streaming </h1> <br>
<h2>🐍 Table of Contents 🐍</h2>

- [About](#about)
- [Benefit](#benefit)
- [Info](#info)
- [Kafka](#kafka)
- [Scala](#scala)
- [Python](#python)
- [Functions](#functions)

<h2>⚡ About ⚡ </h2>

## Benefit
Scala
Python

## Info
- https://kafka.apache.org
- https://dzone.com/articles/hands-on-apache-kafka-with-scala
- https://medium.com/@Ankitthakur/apache-kafka-installation-on-mac-using-homebrew-a367cdefd273
- https://pypi.org/project/kafka-python/

## Kafka
### 0. install kafka
```bash
brew install kafka
```
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

## Scala
### 0. create project structure
```bash
- myapp
    - project
        - build.properties
    - src
        - main
            - scala
                - Main.scala
    - build.sbt
```

### 1. add env setup in built.sbt

```scala
name := "kafka-sbt"

version := "0.1"

scalaVersion := "2.12.10"

libraryDependencies ++= Seq(
  "org.apache.kafka" % "kafka-clients" % "2.7.0",
  "org.apache.spark" %% "spark-core" % "3.0.0"
)

name := "kafka-scala"
organization := "yuyatinnefeld-scala"
version := "1.0"
```

### 2. build the project

### 3. run the project

```bash
sbt
run 
```
## Python

### 0. activate venv
```bash
python -m venv venv
source ./venv/bin/activate (Mac) or venv\Scripts\activate (Windows)
```

### 1. install the packages

```bash
pip install kafka-python
```

### 2. run the main.py

```bash
python python/main.py
```
