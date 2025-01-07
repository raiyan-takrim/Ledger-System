import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class TransactionMethods {

    private static char decision;
    private static double percentage, amount;
    private static String description;
    static Scanner input = new Scanner(System.in);

    public static void savings() {
        System.out.println("== Savings ==");
        System.out.print("Are you sure you want to activate it? (Y/N) : ");
        decision = input.next().charAt(0);
        if (decision == 'Y') {
            System.out.print("\nPlease enter the percentage you wish to deduct from the next debit: ");
            percentage = input.nextDouble();
            input.nextLine(); // Consume the newline character
            System.out.println("Savings settings added successfully!!!");
        }
        System.out.println("Savings Settings not added");

    }

    public static void debit() {
        System.out.println("== Debit ==");
        while (true) {
            System.out.print("Enter Amount: ");
            amount = input.nextDouble();
            input.nextLine();
            if (amount <= 0) {
                System.out.println("Invalid amount. Please enter a positive amount.");
            } else
                break;
        }
        while (true) {
            System.out.print("Enter description: ");
            description = input.nextLine();
            if (description.length() > 100) {
                System.out.println("Description is too long. Please enter a description with at most 100 characters.");
            } else {
                break;
            }

        }
        LocalDate date = getDateFromUser();
        boolean result = UserActions.debit(amount, description, date);
        if (result) {
            System.out.println("Debit Successfully Recorded!!!");
        } else {
            System.out.println("Something went wrong!!!");
        }

    }

    public static void credit() {
        System.out.println("== Credit ==");
        double balance = UserActions.getAccountBalance();
        while (true) {
            System.out.print("Enter Amount: ");
            amount = input.nextDouble();
            if (balance < amount) {
                System.out.println("Insufficient balance. Please enter an amount less than or equal to your balance.");
            } else if (amount <= 0) {
                System.out.println("Invalid amount. Please enter a positive amount.");
            } else
                break;
        }
        input.nextLine(); // Consume the newline character
        while (true) {
            System.out.print("Enter description: ");
            description = input.nextLine();
            if (description.length() > 100) {
                System.out.println("Description is too long. Please enter a description with at most 100 characters.");
            } else {
                break;
            }

        }
        LocalDate date = getDateFromUser();
        boolean result = UserActions.credit(amount, description, date);
        if (result) {
            System.out.println("Credit Successfully Recorded!!!");
        } else {
            System.out.println("Something went wrong!!!");

        }
    }

    private static LocalDate getDateFromUser() {
        LocalDate date = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (date == null) {
            System.out.print("Enter date (yyyy-MM-dd): ");
            String dateString = input.nextLine();
            try {
                date = LocalDate.parse(dateString, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter the date in yyyy-MM-dd format.");
            }
        }
        //System.out.println("Date: " + date);
        return date;
    }

    public static void history(){
        System.out.println("== Transaction History ==");

        List<Transaction> transactions = UserActions.getTransactionHistory();
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.printf("%-15s%-20s%-15s%-15s%-15s\n", "Date", "Description", "Debit", "Credit", "Balance");
            for (Transaction transaction : transactions) {
                if(transaction.getType().equals("debit")){
                    System.out.printf("%-15s%-20s%-15s%-15s%-15s\n", transaction.getDate(), transaction.getDescription(), transaction.getAmount(), "0.0", transaction.getUpdatedAmount());
                }else{
                    System.out.printf("%-15s%-20s%-15s%-15s%-15s\n", transaction.getDate(), transaction.getDescription(), "0.0", transaction.getAmount(), transaction.getUpdatedAmount());
                }
            }

            try(FileWriter csvWriter = new FileWriter("transaction_history.csv")) {
                csvWriter.append("Date,Description,Debit,Credit,Balance\n");
                for (Transaction transaction : transactions) {
                    if(transaction.getType().equals("debit")){
                        csvWriter.append(transaction.getDate() + "," + transaction.getDescription() + "," + transaction.getAmount() + ",0.0," + transaction.getUpdatedAmount() + "\n");
                    }else{
                        csvWriter.append(transaction.getDate() + "," + transaction.getDescription() + ",0.0," + transaction.getAmount() + "," + transaction.getUpdatedAmount() + "\n");
                    }
                }
                csvWriter.flush();
                System.out.println("Transaction history has been saved to transaction_history.csv");
            } catch (Exception e) {
                System.out.println("An error occurred while writing to the file.");
            }
        }
    }

    public static void depositInterestPredictor() {
        double accBalance = UserActions.getAccountBalance();
        int choice;
        double interest;
        System.out.printf("%-22s %-20s\n", "Bank", "Interest Rate(%)");
        System.out.println("-------------------------------------------------");
        System.out.printf("%-22s %-20s%n", "1.RHB", "2.6");
        System.out.printf("%-22s %-20s%n", "2.Maybank", "2.5");
        System.out.printf("%-22s %-20s%n", "3.Hong Leong", "2.3");
        System.out.printf("%-22s %-20s%n", "4.Alliance", "2.85");
        System.out.printf("%-22s %-20s%n", "5.AmBank", "2.55");
        System.out.printf("%-22s %-20s%n", "6.Standard Chartered", "2.65");

        System.out.print("Input desired bank : ");
        choice = input.nextInt();

        switch (choice) {
            case 1: {
                interest = (accBalance * 2.6) / 12;
                System.out.printf("Monthly interest : RM%.2f%n", interest);
                break;
            }
            case 2: {
                interest = (accBalance * 2.5) / 12;
                System.out.printf("Monthly interest : RM%.2f%n", interest);
                break;
            }
            case 3: {
                interest = (accBalance * 2.3) / 12;
                System.out.printf("Monthly interest : RM%.2f%n", interest);
                break;
            }
            case 4: {
                interest = (accBalance * 2.85) / 12;
                System.out.printf("Monthly interest : RM%.2f%n", interest);
                break;
            }
            case 5: {
                interest = (accBalance * 2.55) / 12;
                System.out.printf("Monthly interest : RM%.2f%n", interest);
                break;
            }
            case 6: {
                interest = (accBalance * 2.65) / 12;
                System.out.printf("Monthly interest : RM%.2f%n", interest);
                break;
            }
            default: {
                System.out.println("Invalid input.");
                break;
            }
        }
    }
}