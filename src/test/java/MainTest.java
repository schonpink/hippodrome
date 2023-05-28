import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class MainTest {

    @Disabled
    @Test
    @Timeout(value = 22)
    public void testMainDuration() throws Exception {
        Main.main(null);
    }


}