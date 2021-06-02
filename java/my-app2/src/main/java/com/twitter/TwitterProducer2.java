package com.twitter;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class TwitterProducer2 {
    Logger logger = LoggerFactory.getLogger(TwitterProducer2.class.getName());
    List<String> terms = Lists.newArrayList("kafka","bitcoin");


    public TwitterProducer2(){}

    public static void main(String[] args) {
        TwitterProducer2 tw = new TwitterProducer2();
        tw.run();

    }

    public static Map <String, String> getTwitterAPIConfig(){

        Properties prop = new Properties();
        String fileName = "/Users/yuyatinnefeld/desktop/projects/kafka/conf/app.config";
        InputStream is = null;

        try {
            is = new FileInputStream(fileName);
        } catch (FileNotFoundException ex) {
            System.out.println("FileNotFoundException");
        }
        try {
            prop.load(is);
        } catch (IOException ex) {
            System.out.println("IOException");
        }

        Map<String, String> twitterAPIConfig = new HashMap<>();

        twitterAPIConfig.put("consumerKey", prop.getProperty("consumerKey"));
        twitterAPIConfig.put("consumerSecret", prop.getProperty("consumerSecret"));
        twitterAPIConfig.put("token", prop.getProperty("accessToken"));
        twitterAPIConfig.put("secret", prop.getProperty("accessTokenSecret"));

        return twitterAPIConfig;

    }

    public void run(){

        logger.info("Setup");

        /** Set up your blocking queues: Be sure to size these properly based on expected TPS of your stream */
        BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(1000);


        // create a twitter client
        Client client = createTwitterClient(msgQueue);
        // Attempts to establish a connection.
        client.connect();


        // create kafka producer
        KafkaProducer<String, String> producer = createKafkaProducer();

        // add a shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("stopping application...");
            logger.info("shutting down client from twitter...");
            client.stop();
            logger.info("closing producer....");
            producer.close();
            logger.info("done!");
        }));

        // loop to send tweets to kafka

        // on a different thread, or multiple different threads....
        while (!client.isDone()) {
            String msg = null;
            try {
                msg = msgQueue.poll(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
                client.stop();
            }
            if (msg != null){
                logger.info(msg);
                producer.send(new ProducerRecord<>("twitter_tweets", null, msg), new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                        if (e != null){
                            logger.error("Something bad happened", e);
                        }
                    }
                });


            }
        }

        logger.info("End of application");

    }




    public Client createTwitterClient(BlockingQueue<String> msgQueue){

        /** Declare the host you want to connect to, the endpoint, and authentication (basic auth or oauth) */
        Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
        StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();

        hosebirdEndpoint.trackTerms(terms);

        // These secrets should be read from a config file

        Map<String, String> api = getTwitterAPIConfig();
        Authentication hosebirdAuth = new OAuth1(api.get("consumerKey"), api.get("consumerSecret"), api.get("token"), api.get("secret"));

        ClientBuilder builder = new ClientBuilder()
                .name("Hosebird-Client-01")                              // optional: mainly for the logs
                .hosts(hosebirdHosts)
                .authentication(hosebirdAuth)
                .endpoint(hosebirdEndpoint)
                .processor(new StringDelimitedProcessor(msgQueue));


        Client hosebirdClient = builder.build();
        return hosebirdClient;

    }

    public static KafkaProducer<String, String> createKafkaProducer(){

        String bootstrapServers = "127.0.0.1:9092";

        // create Producer Properties
        Properties kafkaProps = new Properties();
        kafkaProps.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        kafkaProps.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        kafkaProps.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // create safe producer
        kafkaProps.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");
        kafkaProps.setProperty(ProducerConfig.ACKS_CONFIG, "all"); //acks = -1
        kafkaProps.setProperty(ProducerConfig.RETRIES_CONFIG, Integer.toString(Integer.MAX_VALUE));
        kafkaProps.setProperty(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "5");

        // create the producer
        KafkaProducer <String, String> producer = new KafkaProducer<String, String>(kafkaProps);

        return producer;

    }


}
