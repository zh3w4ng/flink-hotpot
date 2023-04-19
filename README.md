# prismo-hotpot
![hotpot](https://user-images.githubusercontent.com/110857308/232117492-d046a5b9-4284-4e05-8345-9701fa09ccbc.jpg)

## Flow Chart
![Projector Flink Job](https://user-images.githubusercontent.com/110857308/233148674-45ba9023-6490-4106-8eee-c1354e7cf5dd.png)

## How to run

### Unit Testing
- `gradle test`

### Simulate with Latest M2k Event
- get the credentials for M2k Kafka and derive a file named `M2kKafkaSource.properties` from `M2kKafkaSource.properties.template`.
- connect to Latest VPN for m2k.
- `gradle run`
- `kafka-console-consumer --bootstrap-server localhost:29092 --topic prismo_hotpot_domains` and you will see the serialized avro events.
- go to AWS Glue Schema Registry and you can find the up-to-date definition for schema `prismo_hotpot_domains`
