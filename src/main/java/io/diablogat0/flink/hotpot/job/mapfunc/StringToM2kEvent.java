package io.diablogato.flink.hotpot.job.mapfunc;

import org.apache.flink.api.common.functions.MapFunction;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.diablogato.flink.hotpot.schema.M2kEvent;
import io.diablogato.flink.hotpot.schema.Timestamp;

public class StringToM2kEvent implements MapFunction<String, M2kEvent> {
    private ObjectMapper om = new ObjectMapper();

    @Override
    public M2kEvent map(String jsonString) throws JsonProcessingException {
        if (jsonString == null) return defaultM2kEvent();
        JsonNode node = om.readTree(jsonString);
        M2kEvent event = om.treeToValue(node, M2kEvent.class);
        return event;
    }

    private M2kEvent defaultM2kEvent() {
        return new M2kEvent()
                .withId("")
                .withCreatedAt(new Timestamp().with$date("1970-01-01T00:00:00.000Z"))
                .withUpdatedAt(new Timestamp().with$date("1970-01-01T00:00:00.000Z"));
    }
}
