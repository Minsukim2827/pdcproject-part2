package pdc.project.part2;
import javax.swing.JOptionPane;
public class StudentAccount extends BankAccount {
    private final double OVERDRAFT_FEE = 10.00;
    private static final double INTEREST_RATE = 0.005;

    public StudentAccount(double accountBalance) {
        super("Student Account", accountBalance, INTEREST_RATE);

    }
    public StudentAccount(String accountType,double accountBalance, double interestRate) {
        super(accountType, accountBalance, interestRate);
    }
            public StudentAccount(int accountId, String accountType,double accountBalance, double interestRate) {
        super(accountId, accountType, accountBalance, interestRate);
    }

    @Override
    public void calculateYearlyInterest() {
        double yearlyInterest = getAccountBalance() * getInterestRate();
        setYearlyInterest(yearlyInterest);
    }

    @Override
    public void withdrawMoney(double amount) {
        if (amount > getAccountBalance()) {
            JOptionPane.showMessageDialog(null, "Balance going into negative, overdraft fee of $10 applies");
            System.out.println(String.format("Insufficient balance. Withdrawing %.2f with an overdraft fee of %.2f", amount, this.OVERDRAFT_FEE));
            double resultingBalance = getAccountBalance() - amount - OVERDRAFT_FEE;
            setAccountBalance(resultingBalance);
            System.out.println("Your balance is now: " + getAccountBalance());
        } else {
            super.withdrawMoney(amount);
        }
        calculateYearlyInterest();
    }
}