package com.highspot.prismo.hotpot.job.mapfunc;

import org.apache.flink.api.common.functions.MapFunction;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.joda.time.DateTime;
import com.highspot.prismo.hotpot.schema.M2kEvent;

public class StringToM2kEvent implements MapFunction<String, M2kEvent> {
    @Override
    public M2kEvent map(String jsonString) throws JsonProcessingException {
        System.out.println(jsonString);
        System.out.println("*********");
        M2kEvent e = new M2kEvent();
        e.setId("");
        if (jsonString == null) return e;
        JsonNode node = new ObjectMapper().readTree(jsonString);
        M2kEvent event = new ObjectMapper().treeToValue(node, M2kEvent.class);
        System.out.println(event.getId());
        DateTime dt = new DateTime(event.getCreatedAt().get$date());
        System.out.println(dt.getDayOfMonth());
        System.out.println("#########");
        return event;
    }
}
