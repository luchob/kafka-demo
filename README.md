# Welcome

This project is small back-up for my talk at [Java2Days conference](https://2018.java2days.com/) in Sofia, November 2018.
The goal of the talk is to:
+ give the attendees some fundamental Kafka knowledge.
+ show a very minimalistic examples with the Kafka API-s.
+ demonstrate some real world examples. 

# Contents

The project consists of:

+ A demo usage of the standard Kafka JAVA [Producer](https://kafka.apache.org/documentation/#producerapi) and [Consumer](https://kafka.apache.org/documentation/#consumerapi) API-s.
+ A demo of super simplified usage of the [Kafka Streams](https://kafka.apache.org/documentation/#streamsapi) API.
+ A demo of [Sping Kafka](https://spring.io/projects/spring-kafka).

Prerequisites if you want to use the project:

+ A running Kafka installation (details below).
+ Knowledge of Kafka theory.
+ Reasonable IDE - IntelliJ is certainly recommended!

The classes have decent documentation and casual browsing should be enough to grasp the ideas within.

# Kafka installation

If you want to install Kafka and make more deeper experiment maybe you would like a native Kafka installation.
If you just want a quick and dirty start and you have docker installed then the docker installation might be the way to go.

## Docker

If you have a running docker installation this is the quickest way to get started.
The current project contains a docker compose file which will get you started.
Navigate to the project root and execute (the docker deamon should be running).

`docker-compose -f docker/docker-compose.yml up`

If everything goes OK then you should be able to successfully execute the `SampleKafkaProducer`.
Then you will be able to consume that with `SampleKafkaConsumer`.

## Native

Installing Kafka natively without some ad-hoc configurations is easy. Just follow [this](https://kafka.apache.org/quickstart) 
quick start guide.

# Common Kafka commands

This is a cheat sheet with some Kafka console tools commands. Just for reference.
Assumes a local Kafka installation with a broker at port 9092 and zookeeper at port 2181.

## Create a topic

Creates a topic `demo` with replication factor of 2 and 3 partitions.

```
kafka-topics.sh \
    --create \
    --zookeeper localhost:2181 \
    --replication-factor 2 \
    --partitions 3 \
    --topic demo
```

## Produce to a topic

Produce to topic `demo`.

```
kafka-console-producer.sh \
    --topic demo \
    --broker-list localhost:9092
```

## Consume from a topic with double values

Consume from topic `temperature-fahrenheit`.

```
kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --topic temperature-fahrenheit \
  --value-deserializer org.apache.kafka.common.serialization.DoubleDeserializer
```

## Delete a topic

Delete the topic `demo`.

```
kafka-topics.sh \
    --delete \
    --zookeeper localhost:2181 \
    --topic demo
```

## Describe consumer group

Describes the `multiple-kafka-consumer` group (topic, partions, lags, etc)

```
kafka-consumer-groups.sh \
      --bootstrap-server localhost:9092 \
      --describe \
      --group multiple-kafka-consumer
```


# Contributing

The idea of this talk is to be useful for any attendee.

Reasonable suggestions are welcomed and appreciated via [pull requests](https://github.com/luchob/kafka-demo/pulls).

If you have some question or remark (before or after the talk) or notice issues do not hesitate to [open an issue](https://github.com/luchob/kafka-demo/issues).
