package eu.balev.java2days.kafka.streaming;

import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.LoggerFactory;


/**
 * A sample streaming app which gets the data in the temperature topic and streams
 * it directly to a similar topic containing the temperatures in fahrenheit.
 *
 * To run this app the output topic should exist i.e. it should be created beforehand.
 *
 * <pre>
      kafka-console-consumer.sh \
        --bootstrap-server localhost:9092 \
        --topic temperature-fahrenheit \
        --from-beginning \
        --value-deserializer org.apache.kafka.common.serialization.DoubleDeserializer
 * </pre>
 */
public class KafkaStreamingApp {

  private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(KafkaStreamingApp.class);

  //topic names
  private static final String INPUT_TOPIC = "temperature";
  private static final String OUTPUT_TOPIC = "temperature-fahrenheit";
  private static final String BROKER_LIST = "localhost:9092";

  public static void main(String[] args) {
    Properties config = new Properties();

    config.put(StreamsConfig.APPLICATION_ID_CONFIG, "temperature-converter-app");
    config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BROKER_LIST);
    config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
    config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.Double().getClass());

    // initialize
    StreamsBuilder builder = new StreamsBuilder();
    KStream<String, Double> wordCountInput = builder.stream(INPUT_TOPIC);

    //Map values
    KStream<String, Double> wordCounts = wordCountInput.mapValues(d -> (d * 9)/5 + 32);

    wordCounts.to(OUTPUT_TOPIC);

    KafkaStreams streams = new KafkaStreams(builder.build(), config);
    streams.start();

    Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
  }
}
