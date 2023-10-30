package pdc.project.part2;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TransactionTest {
    private Transaction transaction;

    @Before
    public void setUp() {
        transaction = new Transaction("Deposit", 100.0);
    }

    @Test
    public void testGetAmount() {
        assertEquals(100.0, transaction.getAmount(), 0.0);
    }
}
