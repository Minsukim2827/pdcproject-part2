package pdc.project.part2;
import java.util.*;
/**
 *
 * @author Minsu
 */
public interface BankService {
    void displayAccounts();
    void addNewAccount();
    void deleteAccount();
    Customer getCustomerById(int id);
    HashMap<Integer, Customer> getCustomers();
}