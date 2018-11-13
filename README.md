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

# Contributing

The idea of this talk is to be useful for any attendee.

Reasonable suggestions are welcomed and appreciated via [pull requests](https://github.com/luchob/kafka-demo/pulls).

If you have some question or remark (before or after the talk) or notice issues do not hesitate to [open an issue](https://github.com/luchob/kafka-demo/issues).
