package com.highspot.prismo.hotpot.job.mapfunc;

import org.apache.flink.api.common.functions.MapFunction;
import org.joda.time.DateTime;
import com.highspot.prismo.hotpot.schema.M2kEvent;
import com.highspot.prismo.hotpot.schema.HotpotEvent;

public class M2kEventToHotpotEvent implements MapFunction<M2kEvent, HotpotEvent> {
    @Override
    public HotpotEvent map(M2kEvent in){
        Long createdAt = new DateTime(in.getCreatedAt().get$date()).getMillis();
        long updatedAt = new DateTime(in.getUpdatedAt().get$date()).getMillis();
        return new HotpotEvent(in.getId(), createdAt, updatedAt);
    }
}
