package io.diablogato.flink.hotpot.sink;

import com.amazonaws.services.schemaregistry.utils.AWSSchemaRegistryConstants;
import com.amazonaws.services.schemaregistry.utils.AvroRecordType;
import java.util.*;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.aws.config.AWSConfigConstants;
import org.apache.flink.formats.avro.glue.schema.registry.GlueSchemaRegistryAvroSerializationSchema;
import io.diablogato.flink.hotpot.schema.HotpotEvent;

public class HotpotKafkaSink {
    public static KafkaSink<HotpotEvent> createLocal(String topic) {
        return KafkaSink.<HotpotEvent>builder()
            .setBootstrapServers("localhost:29092")
            .setRecordSerializer(kafkaRecordSerializationSchema(topic))
            .build();
    }

    private static KafkaRecordSerializationSchema<HotpotEvent> kafkaRecordSerializationSchema(String topic) {
        return KafkaRecordSerializationSchema
            .builder()
            .setTopic(topic)
            .setValueSerializationSchema(glueAvroSerializationSchema(topic))
            .build();
    }

    private static SerializationSchema<HotpotEvent> glueAvroSerializationSchema(String topic) {
        return GlueSchemaRegistryAvroSerializationSchema
            .forSpecific(HotpotEvent.class, topic, glueConfigs());
    }

    private static Map<String, Object> glueConfigs() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AWSConfigConstants.AWS_PROFILE_NAME, "prismo_latest_dev");
        configs.put(AWSConfigConstants.AWS_ROLE_SESSION_NAME, "PowerUserAccess");
        configs.put(AWSSchemaRegistryConstants.AWS_REGION, "us-east-1");
        configs.put(AWSSchemaRegistryConstants.SCHEMA_AUTO_REGISTRATION_SETTING, true);
        configs.put(AWSSchemaRegistryConstants.AVRO_RECORD_TYPE, AvroRecordType.SPECIFIC_RECORD.getName());
        configs.put(AWSSchemaRegistryConstants.REGISTRY_NAME, "com.highspot.mldata.schemas");

        return configs;
    }
}
