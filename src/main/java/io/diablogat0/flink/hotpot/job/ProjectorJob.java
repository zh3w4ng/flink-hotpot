package io.diablogato.flink.hotpot.job;

import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import io.diablogato.flink.hotpot.source.M2kKafkaSource;
import io.diablogato.flink.hotpot.sink.HotpotKafkaSink;
import io.diablogato.flink.hotpot.schema.M2kEvent;
import io.diablogato.flink.hotpot.schema.HotpotEvent;
import io.diablogato.flink.hotpot.job.filterfunc.FilterByDomain;
import io.diablogato.flink.hotpot.job.mapfunc.M2kEventToHotpotEvent;
import io.diablogato.flink.hotpot.job.mapfunc.StringToM2kEvent;

public class ProjectorJob {
    public static void main(String[] args) throws Exception {
        String inputTopic = "m2k_mongo_domains";
        String outputTopic = "flink_hotpot_events";
        String consumerGroup = "prismo_hotpot_projector";

        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        KafkaSource<String> m2kEventSource = M2kKafkaSource.createRemote(inputTopic, consumerGroup);
        DataStream<String> m2kEventStream = environment
            .fromSource(m2kEventSource,
                       WatermarkStrategy.noWatermarks(),
                       "prismo_hotpot")
            .name("prismo_hotpot").uid("prismo_hotpot");

        KafkaSink<HotpotEvent> hotpotEventSink = HotpotKafkaSink.createLocal(outputTopic);
        m2kEventStream
            .map(new StringToM2kEvent())
            .filter(new FilterByDomain())
            .map(new M2kEventToHotpotEvent())
            .sinkTo(hotpotEventSink);

        environment.execute();
    }


}
