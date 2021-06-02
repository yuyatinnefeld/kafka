
#Steps

#Info
- https://github.com/twitter/hbc

## create Twitter Producer App

## modify the TwitterAPI Config
```bash
cd /../path/conf/app.config
```

consumerKey=xxxxxxxxxxxxxxx
consumerSecret=xxxxxxxxxxxxxx
accessToken=xxxxxxxxxxxxxx-xxxxxxxxxxxxxx
accessTokenSecret=xxxxxxxxxxxxxx

## create topic "twitter_tweets"

```bash
kafka-topics --zookeeper localhost:2181 --create --topic twitter_tweets --partitions 6 --replication-factor 1
```

## run the consumer "twitter_tweets"

```bash
kafka-console-consumer --bootstrap-server localhost:9092 --topic twitter_tweets
```

## run the Twitter Producer App