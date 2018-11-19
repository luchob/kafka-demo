Commands demonstrated during the talk.

Delete the demo topic if exists.

```
kafka-topics.sh \
    --delete \
    --zookeeper localhost:2181 \
    --topic demo
```

Create the demo topic with 3 partitions, replication factor of 2.

```
kafka-topics.sh \
    --create \
    --zookeeper localhost:2181 \
    --replication-factor 2 \
    --partitions 3 \
    --topic demo
```

Open Kafka console consumer for topic demo.

```
kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --topic demo
```

Open Kafka console producer for topic demo.

```
kafka-console-producer.sh \
    --topic demo \
    --broker-list localhost:9092
```

Describe the sample Kafka consumer group.

```
kafka-consumer-groups.sh \
      --bootstrap-server localhost:9092 \
      --describe \
      --group sample-kafka-consumer
```

Describe the multiple Kafka consumer group.

```
kafka-consumer-groups.sh \
      --bootstrap-server localhost:9092 \
      --describe \
      --group multiple-kafka-consumer
```