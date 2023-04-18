package com.highspot.prismo.hotpot.job;

import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import com.highspot.prismo.hotpot.source.M2kKafkaSource;
import com.highspot.prismo.hotpot.sink.HotpotKafkaSink;
import com.highspot.prismo.hotpot.schema.M2kEvent;
import com.highspot.prismo.hotpot.schema.HotpotEvent;
import com.highspot.prismo.hotpot.job.mapfunc.M2kEventToHotpotEvent;
import com.highspot.prismo.hotpot.job.mapfunc.StringToM2kEvent;

public class ProjectorJob {
    public static void main(String[] args) throws Exception {
        String inputTopic = "m2k_mongo_domains";
        String outputTopic = "prismo_hotpot_domains";
        String consumerGroup = "prismo_hotpot_projector";

        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();
        KafkaSource<String> m2kEventSource = M2kKafkaSource.createRemote(inputTopic, consumerGroup);
        DataStream<String> m2kEventStream = environment
            .fromSource(m2kEventSource,
                       WatermarkStrategy.noWatermarks(),
                       "prismo_hotpot")
            .name("prismo_hotpot").uid("prismo_hotpot");

        KafkaSink<HotpotEvent> hotpotEventSink = HotpotKafkaSink.createLocal(outputTopic);
        // m2kEventStream.map(new StringToM2kEvent()).map(new M2kEventToHotpotEvent()).print();
        m2kEventStream.map(new StringToM2kEvent()).map(new M2kEventToHotpotEvent()).sinkTo(hotpotEventSink);

        environment.execute();
    }


}
