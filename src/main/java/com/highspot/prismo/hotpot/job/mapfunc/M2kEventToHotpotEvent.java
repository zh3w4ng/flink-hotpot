package com.highspot.prismo.hotpot.job.mapfunc;

import org.apache.flink.api.common.functions.MapFunction;
import com.highspot.prismo.hotpot.schema.M2kEvent;
import com.highspot.prismo.hotpot.schema.HotpotEvent;

public class M2kEventToHotpotEvent implements MapFunction<String, HotpotEvent> {
    @Override
    public HotpotEvent map(String in){
        System.out.println(in);
        return new HotpotEvent(1, "placeholder");
    }
}
