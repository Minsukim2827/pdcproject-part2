package pdc.project.part2;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class StudentAccountTest {
    private StudentAccount account;

    @Before
    public void setUp() {
        account = new StudentAccount(1, "Student Account", 100.00, 0.005);
    }

    @Test
    public void testWithdrawMoney_SufficientBalance() {
        account.withdrawMoney(50.00);
        assertEquals(50.00, account.getAccountBalance(), 0.01);
    }

    @Test
    public void testWithdrawMoney_InsufficientBalance() {
        account.withdrawMoney(150.00);
        assertEquals(-60.00, account.getAccountBalance(), 0.01);
    }

    @Test
    public void testWithdrawMoney_ExactBalance() {
        account.withdrawMoney(100.00);
        assertEquals(0.00, account.getAccountBalance(), 0.01);
    }
}
