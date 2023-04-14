package com.highspot.prismo.hotpot.source;

import java.io.InputStream;
import java.io.IOException;
import java.util.*;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.apache.flink.formats.json.JsonDeserializationSchema;
import com.highspot.prismo.hotpot.schema.M2kEvent;

public class M2kKafkaSource {
    public static KafkaSource<M2kEvent> createLocal(String topic, String consumerGroup) {
        return KafkaSource.<M2kEvent>builder()
            .setBootstrapServers("localhost:29092")
            .setTopics(Arrays.asList(topic))
            .setGroupId(consumerGroup)
            .setProperties(properties())
            .setDeserializer(kafkaRecordDeserializationSchema())
            .build();
    }

    private static KafkaRecordDeserializationSchema<M2kEvent> kafkaRecordDeserializationSchema() {
        return KafkaRecordDeserializationSchema
            .valueOnly(new JsonDeserializationSchema<M2kEvent>(M2kEvent.class));
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
