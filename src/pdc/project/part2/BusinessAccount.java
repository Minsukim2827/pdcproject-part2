package pdc.project.part2;

public class BusinessAccount extends BankAccount {
    private static final double INTEREST_RATE = 0.03;

    public BusinessAccount(double accountBalance) {
        super("Business Account", accountBalance, INTEREST_RATE);
    }

    public BusinessAccount(String accountType,double accountBalance, double interestRate) {
        super(accountType, accountBalance, interestRate);
    }
    
    @Override
    public void calculateYearlyInterest() {
        double yearlyInterest = getAccountBalance() * getInterestRate();
        setYearlyInterest(yearlyInterest);
    }


}

