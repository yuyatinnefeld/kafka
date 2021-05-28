import java.util
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer._
import java.util.Properties
import scala.collection.JavaConverters._
import org.apache.log4j._


object SimpleKafka extends App{
  Logger.getLogger("org").setLevel(Level.ERROR)

  def PropsSetup(host: String): Properties ={
    val props = new Properties()
    props.put("bootstrap.servers", host)
    return props
  }

  def ConsumerKafka(props: Properties, topic: String) = {
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
    props.put("auto.offset.reset", "latest")
    props.put("group.id", "consumer-group")
    val consumer: KafkaConsumer[String, String] = new KafkaConsumer[String, String](props)
    consumer.subscribe(util.Arrays.asList(topic))
    while (true) {
      val record = consumer.poll(1000).asScala
      for (data <- record.iterator) {
        println("kafka data coming: " + data.value())
      }
    }
  }

  def ProducerKafka(props: Properties, topic: String) = {
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    val producer = new KafkaProducer[String, String](props)
    //val record = new ProducerRecord[String, String](topic, "key", "value")
    for(i <- 1 to 10){
      println("send")
      val generated_data = i.toString
      val record = new ProducerRecord[String, String](topic, "key", generated_data)
      producer.send(record)
    }
    producer.close()
  }
  val props = PropsSetup("localhost:9092")

  ConsumerKafka(props, "topicYY")

  //ProducerKafka(props,"topicYY")

}
