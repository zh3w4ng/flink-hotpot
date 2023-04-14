package com.highspot.prismo.hotpot.job.mapfunc;

import org.apache.flink.api.common.functions.MapFunction;
import com.highspot.prismo.hotpot.schema.M2kEvent;
import com.highspot.prismo.hotpot.schema.HotpotEvent;

public class M2kEventToHotpotEvent implements MapFunction<M2kEvent, HotpotEvent> {
    @Override
    public HotpotEvent map(M2kEvent e){
        return new HotpotEvent(1, "placeholder");
    }
}
