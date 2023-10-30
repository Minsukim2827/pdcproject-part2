package pdc.project.part2;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class DisplayDetailsScreenTest {
    private DisplayDetailsScreen screen;
    private Customer customer;

    @Before
    public void setUp() {
        BankAccount account = new SavingsAccount(1000.0);
        customer = new Customer("John Doe", "123 Main St", "555-555-5555");
        customer.setBankAccount(account);
        screen = new DisplayDetailsScreen(customer);
    }

    @Test
    public void testGetDetails() {
        String details = screen.getDetails();
        assertTrue(details.contains("Name: John Doe"));
        assertTrue(details.contains("Address: 123 Main St"));
        assertTrue(details.contains("Phone Number: 555-555-5555"));
        assertTrue(details.contains("Account Type: Savings Account"));
        assertTrue(details.contains("Balance: 1000.0"));
        assertTrue(details.contains("Interest Rate: 0.01"));
    }
}
