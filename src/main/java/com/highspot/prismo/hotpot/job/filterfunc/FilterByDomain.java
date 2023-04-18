package com.highspot.prismo.hotpot.job.filterfunc;

import java.util.List;
import java.util.Arrays;
import org.apache.flink.api.common.functions.FilterFunction;
import com.highspot.prismo.hotpot.schema.M2kEvent;

public class FilterByDomain implements FilterFunction<M2kEvent> {
    @Override
    public boolean filter(M2kEvent e) {
        List<String> blacklist = Arrays.asList("hs60818.com");
        return !blacklist.contains(e.getId());
    }

}
