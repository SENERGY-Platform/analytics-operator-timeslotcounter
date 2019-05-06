import org.infai.seits.sepl.operators.Message;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TimeslotCounterTest {

    @Test
    public void run() throws Exception{
        TimeslotCounter tsc = new TimeslotCounter();
        List<Message> messages = TestMessageProvider.getTestMesssagesSet();
        for (Message m : messages) {
            tsc.config(m);
            tsc.run(m);
            m.addInput("timeCounts");
            String timeCounts = m.getInput("timeCounts").getString();
            if (m.getMessageString().split("\"compare\":\"")[1].split("\"")[0].equals(timeCounts)) {
                Assert.fail("Test did not yield correct results. Expected:\n"
                        + m.getMessageString().split("\"compare\": \"")[1].split("\"")[0]
                        + "\nbut got:\n"
                        + timeCounts);
            }
        }
    }
}
