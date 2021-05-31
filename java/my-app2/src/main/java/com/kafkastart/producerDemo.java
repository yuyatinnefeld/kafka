package com.kafkastart;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class producerDemo {
    public static void main(String[] args) {
        String bootstrapServers = "127.0.0.1:9092";

        // create Producer Properties
        Properties kafkaProps = new Properties();
        kafkaProps.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        kafkaProps.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        kafkaProps.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // create the producer
        KafkaProducer <String, String> producer = new KafkaProducer<String, String>(kafkaProps);

        // create a producer record
        ProducerRecord<String, String> record = new ProducerRecord<String, String>("topicYY", "hello world!");

        // send data - this message is asynchronous
        System.out.println("Sending Message : "+ record.toString());
        producer.send(record);

        // flush data
        producer.flush();

        // flush and close producer
        producer.close();
    }
}
