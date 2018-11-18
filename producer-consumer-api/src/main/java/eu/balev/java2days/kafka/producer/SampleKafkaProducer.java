package eu.balev.java2days.kafka.producer;

import static eu.balev.java2days.kafka.common.Constants.BROKER_LIST;
import static eu.balev.java2days.kafka.common.Constants.TOPIC_TEMPERATURE;

import eu.balev.java2days.kafka.common.TemperatureSensor;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.DoubleSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.LoggerFactory;

/**
 * Demonstrates how Kafka message are produced with the standard
 * <a href="https://kafka.apache.org/documentation.html#producerapi">Kafka Producer API</a>.
 * We will generate some messages in the temperature topic.
 */
public class SampleKafkaProducer {

  private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SampleKafkaProducer.class);

  public static void main(String[] args) {

    // 1. Setting up Kafka properties
    Properties config = new Properties();

    config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BROKER_LIST);
    config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, DoubleSerializer.class.getName());

    // 2. Create producer
    KafkaProducer<String, Double> producer = new KafkaProducer<>(config);

    // a fake temperature sensors :-)
    Stream<Double> temperatureRecords = Stream.generate(new TemperatureSensor()).limit(10);

    // 3. Send temperature records
    temperatureRecords.forEach((Double d) ->
        {
          ProducerRecord<String, Double> record = new ProducerRecord<>(TOPIC_TEMPERATURE,
              UUID.randomUUID().toString(),
              d);

          try {
            RecordMetadata producedRecord = producer.send(record).get();
            LOGGER.info("PARTITION {} OFFSET {}. {} degrees sent.",
                producedRecord.partition(),
                producedRecord.offset(),
                d);
          } catch (InterruptedException e) {
            Thread.interrupted();
            LOGGER.error("I won't cooperate!");
          } catch (ExecutionException e) {
            LOGGER.error(e.getMessage(), e);
          }
        }
    );
    // 4. Shutdown cleanly
    producer.close();
  }
}
