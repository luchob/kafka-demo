package eu.balev.java2days.kafka.spring.demo.consumer;

import static eu.balev.java2days.kafka.spring.demo.common.Constants.TOPIC_TEMPERATURE;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Demonstrates the consumption of messages with a listener annotation.
 * For the complete configuration see the application.yaml.
 */
@Component
public class SampleKafkaConsumer {

  private static final Logger LOGGER = LoggerFactory.getLogger(SampleKafkaConsumer.class);

  @KafkaListener(
      topics = TOPIC_TEMPERATURE,
      id = "spring-temperature-consumer"
  )
  public void onRecord(final ConsumerRecord<String, Double> messageRecord) {

    LOGGER.info("Received a Kafka message! Topic/Part/Offset = {}/{}/{}. Value is {}.",
        messageRecord.topic(),
        messageRecord.partition(),
        messageRecord.offset(),
        messageRecord.value());
  }

}
