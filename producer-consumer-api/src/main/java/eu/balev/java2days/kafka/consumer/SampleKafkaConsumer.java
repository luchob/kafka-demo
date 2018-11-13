package eu.balev.java2days.kafka.consumer;

import static eu.balev.java2days.kafka.common.Constants.BROKER_LIST;
import static eu.balev.java2days.kafka.common.Constants.TOPIC_TEMPERATURE;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.DoubleDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.LoggerFactory;

/**
 * The class demonstrates the creation of a Kafka consumer by using
 * the <a href="https://kafka.apache.org/documentation.html#consumerapi">Kafka Consumer API</a>.
 */
public class SampleKafkaConsumer {

  private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SampleKafkaConsumer.class);

  public static void main(String[] args) {

    Properties config = new Properties();

    // 1. Create the important configuration
    config.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BROKER_LIST);
    config.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
        StringDeserializer.class.getName());
    config.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
        DoubleDeserializer.class.getName());
    config.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "sample-kafka-consumer");
    config.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

    // 2. Create the consumer itself.
    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(config);
    // 3. Subscribe to a topic
    consumer.subscribe(Collections.singleton(TOPIC_TEMPERATURE));


    // WARNING: It is very important to notice that this example has no clean shut down!
    while(true) {
      // 4. Poll the records and write them down.
      ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
      for (ConsumerRecord<String, String> record : records) {
        LOGGER.info("Record P/O {}/{} - Key {}/Value {}",
            record.partition(),
            record.offset(),
            record.key(),
            record.value());
      }
    }
  }
}
