package pdc.project.part2;
import java.time.format.DateTimeFormatter;
import java.time.*;

public class Transaction {

    // declare constants
    private final String transactionType;
    private double amount;
    private final String date;


    // create transaction constructor use local date to get current time
    public Transaction(String transactionType, double amount) {
        this.transactionType = transactionType;
        this.amount = amount;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.date =LocalDateTime.now().format(formatter);

    }
        public Transaction(String transactionType, double amount, String date) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.date = date;
   
    }


    public double getAmount() {
        return amount;
    }
    public String getDate(){
        return date;
    }
    public String getTransactionType() {
        return transactionType;
    }
        public String toStringFormatted() {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
   
        return String.format("%s,%f,%s,", transactionType, amount, date);
    }

    // toString method to print transaction contents
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        return String.format("------- Transaction -------\n- Transaction Type: %s\n- " +
                "Amount: $%.2f\n- Date: %10s\n", transactionType, amount, date);
    }
}
