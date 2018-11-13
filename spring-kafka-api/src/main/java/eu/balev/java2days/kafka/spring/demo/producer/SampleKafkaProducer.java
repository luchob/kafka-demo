package eu.balev.java2days.kafka.spring.demo.producer;

import eu.balev.java2days.kafka.spring.demo.common.Constants;
import eu.balev.java2days.kafka.spring.demo.common.TemperatureSensor;
import java.util.UUID;
import java.util.stream.Stream;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Demonstrates the usage of a Kafka Template - the Spring way to produce messages.
 */
@Component
public class SampleKafkaProducer implements CommandLineRunner {

  private static final Logger LOGGER = LoggerFactory.getLogger(SampleKafkaProducer.class);

  @Autowired
  private KafkaTemplate<String, Double> kafkaTemplate;


  @Override
  public void run(String... args) {

    Stream<Double> temperatureRecords = Stream.generate(new TemperatureSensor()).limit(10);

    temperatureRecords.forEach((Double d) -> {

      // Create a producer record, no spring wrapper here.
      ProducerRecord<String, Double> record = new ProducerRecord<>(
          Constants.TOPIC_TEMPERATURE, UUID
          .randomUUID().toString(),
          d);

      // Send the record
      kafkaTemplate.send(record).addCallback(
          (res) -> {
            RecordMetadata sentRecordMeta  = res.getRecordMetadata();
            LOGGER.info("TOPIC {}, PARTITION {}",
                sentRecordMeta.topic(),
                sentRecordMeta.partition());
          },
          (e) -> LOGGER.error("Could not send message to Kafka. The error is {}", e)
      );
    });
  }
}
