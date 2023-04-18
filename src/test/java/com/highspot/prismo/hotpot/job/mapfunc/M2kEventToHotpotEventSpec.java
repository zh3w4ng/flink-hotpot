import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.joda.time.DateTime;
import com.highspot.prismo.hotpot.schema.Timestamp;
import com.highspot.prismo.hotpot.schema.M2kEvent;
import com.highspot.prismo.hotpot.schema.HotpotEvent;
import com.highspot.prismo.hotpot.job.mapfunc.M2kEventToHotpotEvent;

public class M2kEventToHotpotEventSpec {
    @Test
    public void testMap() {
        M2kEvent m = new M2kEvent()
            .withId("12345")
            .withCreatedAt(new Timestamp().with$date("2023-02-21T19:34:49.296Z"))
            .withUpdatedAt(new Timestamp().with$date("2023-04-14T04:03:00.859Z"));
        HotpotEvent h = new M2kEventToHotpotEvent().map(m);
        assertEquals(new DateTime("2023-02-21T19:34:49.296Z"), new DateTime(h.getCreatedAt()));
        assertEquals(new DateTime("2023-04-14T04:03:00.859Z"), new DateTime(h.getUpdatedAt()));
    }
}
