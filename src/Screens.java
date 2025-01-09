import java.util.Scanner;

public class Screens {
    public static void LoginRegScreen() {
        Scanner input = new Scanner(System.in);

        System.out.println("== Ledger System ==");
        while (true) {
            int choice;
            System.out.println("Login or Register: ");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.print("> ");
            choice = input.nextInt();
            if (choice == 1) {
                if (UserActions.login()) {
                    System.out.println("Login Successful!!!");
                    UserProfile();
                    break;
                }
            } else if (choice == 2) {
                if (UserActions.register()) {
                    System.out.println("\nRegistration Successful!!!");
                    System.out.println("Please login to continue.\n");
                }
            } else
                System.out.println("Invalid Input!\n");
        }
        input.close();
    }

    
    // public static void UserProfile() {
    //     if (UserActions.getSavingsStatus()) {
    //         UserActions.transferSavings();
    //     }
    //     Scanner input = new Scanner(System.in);
    //     String userName = UserActions.getUserName();
    //     System.out.println("\n== Welcome, " + userName + " ==");
    //     System.out.print("Balance: ");
    //     double balance = UserActions.getAccountBalance();
    //     System.out.println(balance);
    //     System.out.print("Savings: ");
    //     double savings = UserActions.getSavedBalance();
    //     System.out.println(savings);
    //     System.out.print("Loan: ");
    //     double loan = UserActions.getOutstandingBalance();
    //     System.out.println(loan);

    //     System.out.println("\n== Transaction ==");
    //     System.out.println("1.Debit");
    //     System.out.println("2.Credit");
    //     System.out.println("3.History");
    //     System.out.println("4.Savings");
    //     System.out.println("5.Credit Loan");
    //     System.out.println("6.Deposit Interest Predictor");
    //     System.out.println("7.Logout");
    //     System.out.print(">");
    //     int choice = input.nextInt();

    //     switch (choice) {
    //         case 1:
    //             TransactionMethods.debit();
    //             UserProfile();
    //             break;
    //         case 2:
    //             if (UserActions.delayedRepay()) {
    //                 System.out.println("You have an outstanding loan. Please pay it off first.");
    //                 UserProfile();
    //             }
    //             TransactionMethods.credit();
    //             UserProfile();
    //             break;
    //         case 3:
    //             TransactionMethods.history();
    //             UserProfile();
    //             break;
    //         case 4:
    //             TransactionMethods.savings();
    //             UserProfile();
    //             break;
    //         case 5:
    //             TransactionMethods.creditLoan();
    //             UserProfile();
    //             break;
    //         case 6:
    //             TransactionMethods.depositInterestPredictor();
    //             UserProfile();
    //             break;
    //         case 7:
    //             System.out.println("Thank you for using Ledger System");
    //             LoginRegScreen();
    //             break;
    //         default:
    //             System.out.println("Invalid choice. Please try again.");
    //             UserProfile();
    //             break;
    //     }
    //     input.close();
    // }

    public static void UserProfile() {
        if (UserActions.getSavingsStatus()) {
            UserActions.transferSavings();
        }
        Scanner input = new Scanner(System.in);
        String userName = UserActions.getUserName();
        System.out.println("\n== Welcome, " + userName + " ==");
        System.out.print("Balance: ");
        double balance = UserActions.getAccountBalance();
        System.out.println(balance);
        System.out.print("Savings: ");
        double savings = UserActions.getSavedBalance();
        System.out.println(savings);
        System.out.print("Loan: ");
        double loan = UserActions.getOutstandingBalance();
        System.out.println(loan);

        int overdueInstallments = UserActions.getOverdueInstallments();
        if (overdueInstallments > 0) {
            System.out.println("You have " + overdueInstallments
                    + " overdue installment(s). Please pay the overdue installments to proceed with transactions.");
        }

        System.out.println("\n== Transaction ==");
        System.out.println("1.Debit");
        System.out.println("2.Credit");
        System.out.println("3.History");
        System.out.println("4.Savings");
        System.out.println("5.Credit Loan");
        System.out.println("6.Deposit Interest Predictor");
        System.out.println("7.Logout");
        System.out.print(">");
        int choice = input.nextInt();

        switch (choice) {
            case 1:
                if (overdueInstallments > 0) {
                    System.out.println("You have " + overdueInstallments
                            + " overdue installment(s). Please pay the overdue installments to proceed with transactions.");
                    UserProfile();
                }
                TransactionMethods.debit();
                UserProfile();
                break;
            case 2:
                if (overdueInstallments > 0) {
                    System.out.println("You have " + overdueInstallments
                            + " overdue installment(s). Please pay the overdue installments to proceed with transactions.");
                    UserProfile();
                }
                TransactionMethods.credit();
                UserProfile();
                break;
            case 3:
                TransactionMethods.history();
                UserProfile();
                break;
            case 4:
                TransactionMethods.savings();
                UserProfile();
                break;
            case 5:
                TransactionMethods.creditLoan();
                UserProfile();
                break;
            case 6:
                TransactionMethods.depositInterestPredictor();
                UserProfile();
                break;
            case 7:
                System.out.println("Thank you for using Ledger System");
                LoginRegScreen();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
                UserProfile();
                break;
        }
        input.close();
    }
}