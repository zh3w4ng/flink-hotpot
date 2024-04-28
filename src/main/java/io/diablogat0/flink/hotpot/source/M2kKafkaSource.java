package io.diablogato.flink.hotpot.source;

import java.io.InputStream;
import java.io.IOException;
import java.util.*;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.apache.flink.formats.json.JsonDeserializationSchema;
import io.diablogato.flink.hotpot.schema.M2kEvent;

public class M2kKafkaSource {
    public static KafkaSource<String> createRemote(String topic, String consumerGroup) {
        return KafkaSource.<String>builder()
            .setBootstrapServers("b-1.latest0-su0-kafka3.ubriia.c8.kafka.us-east-1.amazonaws.com:9096")
            .setTopics(Arrays.asList(topic))
            .setGroupId(consumerGroup)
            .setProperties(properties())
            .setDeserializer(kafkaRecordDeserializationSchema())
            .build();
    }

    private static KafkaRecordDeserializationSchema<String> kafkaRecordDeserializationSchema() {
        return KafkaRecordDeserializationSchema
            .valueOnly(StringDeserializer.class);
            // .valueOnly(new JsonDeserializationSchema<M2kEvent>(M2kEvent.class));
    }

    private static Properties properties() {
        Properties prop = new Properties();
        try {
            InputStream input = M2kKafkaSource.class
                .getClassLoader().getResourceAsStream("M2kKafkaSource.properties");
            prop.load(input);
        } catch(IOException e) {
            return new Properties();
        }

        return prop;
    }
}
