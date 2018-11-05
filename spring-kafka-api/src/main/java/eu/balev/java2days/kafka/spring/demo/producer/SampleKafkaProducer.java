package eu.balev.java2days.kafka.spring.demo.producer;

import eu.balev.java2days.kafka.spring.demo.common.Constants;
import eu.balev.java2days.kafka.spring.demo.common.TemperatureSensor;
import java.util.UUID;
import java.util.stream.Stream;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.spi.LoggerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import sun.util.locale.provider.LocaleServiceProviderPool.LocalizedObjectGetter;

@Component
public class SampleKafkaProducer implements CommandLineRunner {

  private static final Logger LOGGER = LoggerFactory.getLogger(SampleKafkaProducer.class);

  @Autowired
  private KafkaTemplate<String, Double> kafkaTemplate;


  @Override
  public void run(String... args) throws Exception {

    Stream<Double> temperatureRecords = Stream.generate(new TemperatureSensor()).limit(10);

    temperatureRecords.forEach((Double d) -> {

      ProducerRecord<String, Double> record = new ProducerRecord<>(
          Constants.TOPIC_TEMPERATURE, UUID
          .randomUUID().toString(),
          d);
      kafkaTemplate.send(record).addCallback(
          (t) -> {
            ProducerRecord<String, Double> sentRecord  = t.getProducerRecord();
            LOGGER.info("TOPIC {}, PARTITION {}",
                sentRecord.topic(),
                sentRecord.partition());
          },
          (e) -> {
            LOGGER.error("Could not send message to Kafka. The error is {}", e);
          }
      );
    });
  }
}
