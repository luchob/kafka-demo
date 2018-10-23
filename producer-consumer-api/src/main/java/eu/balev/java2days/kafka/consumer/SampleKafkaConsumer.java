package eu.balev.java2days.kafka.consumer;

import static eu.balev.java2days.kafka.Constants.BROKER_LIST;
import static eu.balev.java2days.kafka.Constants.TOPIC_TEMPERATURE;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.DoubleDeserializer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.slf4j.LoggerFactory;

public class SampleKafkaConsumer {

  private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SampleKafkaConsumer.class);

  public static void main(String[] args) {

    Properties config = new Properties();

    config.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BROKER_LIST);
    config.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
        LongDeserializer.class.getName());
    config.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
        DoubleDeserializer.class.getName());
    config.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "sample-kafka-consumer");
    config.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

    // create the consumer
    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(config);
    // subscribe
    consumer.subscribe(Collections.singleton(TOPIC_TEMPERATURE));


    // WARNING: It is very important to notice that this example has no clean shut down!
    while(true) {

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
