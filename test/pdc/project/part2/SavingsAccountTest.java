package pdc.project.part2;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class SavingsAccountTest {
    private SavingsAccount account;

    @Before
    public void setUp() {
        account = new SavingsAccount(1000.0);
    }

    @Test
    public void testWithdrawMoney() {
        double initialBalance = account.getAccountBalance();
        account.withdrawMoney(400.0); // This should work
        assertEquals(initialBalance - 400.0, account.getAccountBalance(), 0.01);

        account.withdrawMoney(600.0); // This should fail
        // The balance should not change because the withdrawal should fail
        assertEquals(initialBalance - 400.0, account.getAccountBalance(), 0.01);
    }
}
