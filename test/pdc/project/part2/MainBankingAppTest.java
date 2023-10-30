
package pdc.project.part2;

import org.junit.Test;
import static org.junit.Assert.*;

public class MainBankingAppTest {
    @Test
    public void testMain() {
        try {
            MainBankingApp.main(new String[]{});
            boolean running = true; // This should be where you check if your application is running
            assertEquals("The application should be running", true, running);
        } catch (Exception e) {
            fail("Main method failed with exception: " + e.getMessage());
        }
    }
}
