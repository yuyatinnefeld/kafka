# Kafka Connect and Stream API
![GitHub Logo](/images/kafka_api.png)

- can be used a part data pipeline for data import (ex. DB, JDBC, Couchbase, SAP HANA, Blockchain, Cassandra, DynamoDB, IoT, MongoDB, MQTT, RethinkDB, Salesforce, SQS)
- can be used a part data pipeline for data storing (ex. S3, ElasticSearch, HDFS, JDBC, SAP HANA, DocumentDB, Cassandra, DynamoDB, HBase, MongoDB, Redis, Twitter, etc.)

## Info
- https://docs.confluent.io/platform/current/connect/references/restapi.html
- https://www.confluent.io/hub/jcustenborder/kafka-connect-twitter
- https://github.com/jcustenborder/kafka-connect-twitter


## Use case

- Produce API
    - Case: Source => Kafka
- Consumer, Producer API
    - Case: Kafka => Kafka
- Consumer API
    - Case: Kafka => Sink
- Consumer API
    - Case: Kafka => App
    

## Setup
### create new maven project

### open the github release page
https://github.com/jcustenborder/kafka-connect-twitter/releases

### download zip file
ex. kafka-connect-twitter-0.2.26.tar.gz

### create the connectors folder
```bash
mkdir connectors
mkdir connectors/kafka-connect-twitter
```

### extract the file and copy all jar files in connectors/kafka-connect-twitter

### copy & paste connect-standalone
```bash
connect-standalone

> USAGE: /usr/local/Cellar/kafka/2.8.0/libexec/bin/connect-standalone.sh [-daemon] connect-standalone.properties
```

```bash
cd /usr/local/Cellar/kafka/2.8.0/libexec/
cd config
cp connect-standalone.properties /...your_project_path/kafka-connector-api
```


Edit connect-standalone.properties (plugin.path=connectors)
```bash
....

# Examples: 
# plugin.path=/usr/local/share/java,/usr/local/share/kafka/plugins,/opt/connectors,
#plugin.path=connectors 
```

create twitter properties
```bash
touch twitter.properties
```
Twitter Properties:
```bash
name=TwitterSourceDemo
tasks.max=1
connector.class=com.github.jcustenborder.kafka.connect.twitter.TwitterSourceConnector

# Set these required values
twitter.oauth.accessTokenSecret=xxxxxxxxx
process.deletes=false
filter.keywords=bitcoin
kafka.status.topic=twitter_status_connect
kafka.delete.topic=twitter_deletes_connect
twitter.oauth.consumerSecret=xxxxxxxxx
twitter.oauth.accessToken=xxxxxxxxx-xxxxxxxxx
twitter.oauth.consumerKey=xxxxxxxxx
```

```bash
docker-compose -f kafka-single-node.yml down
```

### 
```bash
zookeeper-server-start /usr/local/etc/kafka/zookeeper.properties
kafka-server-start /usr/local/etc/kafka/server.properties
```
```bash
kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 3 --topic twitter_tweets
kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 3 --topic twitter_status_connect
kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 3 --topic twitter_deletes_connect
kafka-topics --zookeeper localhost:2181 --list
```

```bash
kafka-console-consumer --bootstrap-server localhost:9092 --topic twitter_status_connect --from-beginning
```



```bash
cd /...your_project_path.../kafka-connector-api
```

```bash
connect-standalone connect-standalone.properties twitter.properties
```