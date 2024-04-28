import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import io.diablogato.flink.hotpot.schema.M2kEvent;
import io.diablogato.flink.hotpot.job.filterfunc.FilterByDomain;

public class FilterByDomainSpec {
    @Test
    public void testFilter() {
        M2kEvent m = new M2kEvent()
            .withId("hs60818.com");
        assertEquals(false, new FilterByDomain().filter(m));
    }
}
