package pdc.project.part2;

public class SavingsAccount extends BankAccount {

    private final double MINIMUM_BALANCE = 500.0;
    private static final double INTEREST_RATE = 0.01;

    public SavingsAccount(String accountType, double accountBalance, double interestRate) {
        super("Savings Account", accountBalance, INTEREST_RATE);
        if (accountBalance < MINIMUM_BALANCE) {
            System.out.println("Initial balance is less than minimum balance requirement for Savings Account. Please try again");
        }
    }


    @Override
    public void calculateYearlyInterest() {
        double yearlyInterest = getAccountBalance() * getInterestRate();
        setYearlyInterest(yearlyInterest);
    }

    // override the withdrawMoney method to account for the minimum balance needed for the savings account
    @Override
    public void withdrawMoney(double amount) {
        if (getAccountBalance() - amount < MINIMUM_BALANCE) {
            System.out.println("Cannot process the transaction. Savings account must maintain a minimum balance of " + MINIMUM_BALANCE +"\n");
        } else {
            System.out.println("$" + amount+ " has been withdrawn.\n");
            super.withdrawMoney(amount);
        }
        calculateYearlyInterest();
    }

}






