from kafka import KafkaConsumer
from kafka import KafkaProducer
import datetime

def consumer_kafka(topic):
    consumer = KafkaConsumer(topic)

    for msg in consumer:
        timestamp = msg.timestamp
        your_dt = datetime.datetime.fromtimestamp(int(timestamp)/1000)
        time = your_dt.strftime("%Y-%m-%d %H:%M:%S")
        print("time: ", time)
        print("timestamp", timestamp)
        print("msg: ", msg.value.decode("utf-8"))
        #print(consumer.metrics())

def producer_kafka(host, topic):
    producer = KafkaProducer(bootstrap_servers=host)
    for i in range(10):
        producer.send(topic, b'data: %d' % i)


if __name__ == '__main__':
    topic = 'topicYY'
    host = 'localhost:9092'
    #consumer_kafka(topic)
    #producer_kafka(host, topic)