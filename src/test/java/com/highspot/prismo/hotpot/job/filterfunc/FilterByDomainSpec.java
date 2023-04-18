import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.highspot.prismo.hotpot.schema.M2kEvent;
import com.highspot.prismo.hotpot.job.filterfunc.FilterByDomain;

public class FilterByDomainSpec {
    @Test
    public void testFilter() {
        M2kEvent m = new M2kEvent()
            .withId("hs60818.com");
        assertEquals(false, new FilterByDomain().filter(m));
    }
}
