package eu.balev.java2days.kafka.producer;

import static eu.balev.java2days.kafka.Constants.BROKER_LIST;
import static eu.balev.java2days.kafka.Constants.TOPIC_TEMPERATURE;

import eu.balev.java2days.kafka.TemperatureSensor;
import java.util.Properties;
import java.util.stream.Stream;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.DoubleSerializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.slf4j.LoggerFactory;

/**
 * Demonstrates how Kafka message are produced with the standard API.
 * We will generate some messages in the temperature topic.
 */
public class SampleKafkaProducer {

  private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SampleKafkaProducer.class);

  public static void main(String[] args) {

    // 1. Setting up Kafka properties
    Properties properties = new Properties();

    properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BROKER_LIST);
    properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
    properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, DoubleSerializer.class.getName());

    // 2. Create producer
    KafkaProducer<Long, Double> producer = new KafkaProducer<>(properties);

    // 3. Send temperature records
    Stream<Double> temperatureRecords = Stream.generate(new TemperatureSensor()).limit(10);

    temperatureRecords.forEach(d ->
        {

          long currentTime = System.currentTimeMillis();

          ProducerRecord<Long, Double> record = new ProducerRecord<>(TOPIC_TEMPERATURE,
              currentTime,
              d);

          producer.send(record);

          LOGGER.info("Sent {} degrees at {}.", d, currentTime);

          try {
            Thread.sleep(100);
          } catch (InterruptedException e) {
            Thread.interrupted();
            LOGGER.error("I won't cooperate!");
          }
        }
    );
    producer.close();
  }
}
