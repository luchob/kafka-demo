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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The purpose of this example is to show that if the topic has
 * multiple partitions only certain partitions are assigned to consumers in a given consumer group.
 */
public class MultipleKafkaConsumers {

  public static void main(String[] args) {

    Properties config = new Properties();

    config.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BROKER_LIST);
    config.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
        LongDeserializer.class.getName());
    config.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
        DoubleDeserializer.class.getName());
    config.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "multiple-kafka-consumer");
    config.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

    Thread t1 = new Thread(new ConsumerLoop(config,1));
    Thread t2 = new Thread(new ConsumerLoop(config,2));
    Thread t3 = new Thread(new ConsumerLoop(config,3));

    t1.start();
    t2.start();
    t3.start();
  }

}

class ConsumerLoop implements Runnable {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerLoop.class);

  private final Properties config;
  private final int consumerID;

  ConsumerLoop(Properties config, int consumerID) {
    this.config = config;
    this.consumerID = consumerID;
  }

  @Override
  public void run() {
    // create the consumer
    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(config);
    // subscribe
    consumer.subscribe(Collections.singleton(TOPIC_TEMPERATURE));


    // WARNING: It is very important to notice that this example has no clean shut down!
    while(true) {

      ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
      for (ConsumerRecord<String, String> record : records) {
        LOGGER.info("Consumer {}. Record P/O {}/{} - Key {}/Value {}",
            consumerID,
            record.partition(),
            record.offset(),
            record.key(),
            record.value());
      }
    }
  }
}
