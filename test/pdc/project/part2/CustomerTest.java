package pdc.project.part2;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CustomerTest {
    private Customer customer;

    @Before
    public void setUp() {
        customer = new Customer("John Doe", "123 Street", "1234567890");
    }

    @Test
    public void testGetCustomerName() {
        assertEquals("John Doe", customer.getCustomerName());
    }
}
