import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.highspot.prismo.hotpot.schema.M2kEvent;
import com.highspot.prismo.hotpot.job.mapfunc.StringToM2kEvent;
import com.highspot.prismo.hotpot.test.util.FixtureLoader;

public class StringToM2kEventTest {
    @Test
    public void testMap() throws JsonProcessingException {
        String jsonString = FixtureLoader.load("mongo_domains_fixture.json");
        M2kEvent e = new StringToM2kEvent().map(jsonString);
        assertEquals("2023-02-21T19:34:49.296Z", e.getCreatedAt().get$date());
        assertEquals("2023-04-14T04:03:00.859Z", e.getUpdatedAt().get$date());
    }
}
