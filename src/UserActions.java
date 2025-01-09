import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserActions {
    public static String email;
    public static String name, password, confirmPassword;
    static Scanner input = new Scanner(System.in);

    /* ==========USER DETAILS================= */

    public static String getUserName() {
        Connection conn = DB.connect();
        try {
            PreparedStatement preSt = conn.prepareStatement(Query.getUserName);
            preSt.setString(1, email);
            ResultSet result = preSt.executeQuery();
            if (result.next()) {
                return result.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getUserID() {
        Connection conn = DB.connect();
        try {
            PreparedStatement preSt = conn.prepareStatement(Query.getUserID);
            preSt.setString(1, email);
            ResultSet result = preSt.executeQuery();
            if (result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /* ==========LOGIN AND REGISTRATION================= */
    public static boolean login() {
        boolean isValid = false;
        while (!isValid) {
            System.out.println("== Please enter your email and password ==");
            System.out.print("Email: ");
            email = input.nextLine();
            System.out.print("Password: ");
            password = input.nextLine();
            isValid = CheckCredential(email, password);
        }
        return true;
    }

    private static boolean CheckCredential(String email, String password) {
        boolean exists = isUserExists(email);
        if (exists) {
            try {
                Connection conn = DB.connect();
                PreparedStatement preSt = conn.prepareStatement(Query.getPassword);
                preSt.setString(1, email);
                ResultSet result = preSt.executeQuery();
                if (result.next()) {
                    if (result.getString(1).equals(password)) {
                        return true;
                    } else
                        System.out.println("Wrong Credential!\n");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else
            System.out.println("Sorry, user not exists.\n");
        return false;
    }

    private static boolean isUserExists(String userEmail) {
        Connection conn = DB.connect();
        PreparedStatement preSt;
        try {
            preSt = conn.prepareStatement(Query.userExists);
            preSt.setString(1, userEmail);
            ResultSet result = preSt.executeQuery();
            if (result.next()) {
                return result.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean register() {
        while (true) {
            boolean isValid;
            System.out.println("== Please fill in the form ==");

            while (true) {
                System.out.print("Name: ");
                name = input.nextLine();
                isValid = Validation.name(name);
                if (!isValid) {
                    System.out.println("Name should be only alphanumeric\n");
                    continue;
                }
                break;
            }

            while (true) {
                System.out.print("Email: ");
                email = input.nextLine();
                isValid = Validation.email(email);
                if (!isValid) {
                    System.out.println("Invalid Email\n");
                    continue;
                }
                isValid = isUserExists(email);
                if (isValid) {
                    System.out.println("User already exists!\n");
                    continue;
                }
                break;
            }

            while (true) {
                System.out.print("Password: ");
                password = input.nextLine();
                isValid = Validation.password(password);
                if (!isValid) {
                    System.out.println(
                            "Password should contains:\n1. At least 6 character\n2. At least one letter, one special character and one number\n");
                    continue;
                }
                break;
            }

            while (true) {
                System.out.print("Confirm Password: ");
                confirmPassword = input.nextLine();
                if (!password.equals(confirmPassword)) {
                    System.out.println("Password not matched!\n");
                    continue;
                }
                break;
            }

            boolean isSuccessfull = UserActions.setUser(name, email, password);
            if (isSuccessfull) {
                break;
            } else {
                System.out.println("Someting went wrong!\n");
            }
        }
        return true;
    }

    private static boolean setUser(String name, String email, String password) {
        Connection conn = DB.connect();
        try {
            PreparedStatement preSt = conn.prepareStatement(Query.addUser);
            preSt.setString(1, name);
            preSt.setString(2, email);
            preSt.setString(3, password);
            preSt.executeUpdate();
            setSavings(getUserID(), "inactive", 0.0, 0.0);
            setAccountBalance(getUserID(), 0.0);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    /*=========Savings============= */

    private static void setSavings(int userID, String status, double percentage, double savedBalance) {
        Connection conn = DB.connect();
        try {
            PreparedStatement preSt = conn.prepareStatement(Query.setSavings);
            preSt.setInt(1, userID);
            preSt.setString(2, status);
            preSt.setDouble(3, percentage);
            preSt.setDouble(4, savedBalance);
            preSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean getSavingsStatus() {
        int userID = getUserID();
        Connection conn = DB.connect();
        try {
            PreparedStatement preSt = conn.prepareStatement(Query.get_savings_status);
            preSt.setInt(1, userID);
            ResultSet result = preSt.executeQuery();
            if (result.next()) {
                return result.getString(1).equals("active");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void setSavingsStatusAndPercentage(String status, double percentage) {
        int userID = getUserID();
        Connection conn = DB.connect();
        try {
            PreparedStatement preSt = conn.prepareStatement(Query.update_savings_status);
            preSt.setString(1, status);
            preSt.setDouble(2, percentage);
            preSt.setInt(3, userID);
            preSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setSavingsStatus(String status) {
        int userID = getUserID();
        Connection conn = DB.connect();
        try {
            PreparedStatement preSt = conn.prepareStatement("UPDATE savings SET status = ? WHERE user_id = ?");
            preSt.setString(1, status);
            preSt.setInt(2, userID);
            preSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static double getSavedBalance() {
        int userID = getUserID();
        Connection conn = DB.connect();
        try {
            PreparedStatement preSt = conn.prepareStatement(Query.getSaved);
            preSt.setInt(1, userID);
            ResultSet result = preSt.executeQuery();
            if (result.next()) {
                return result.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void updateSavedAmount(int userID, double amount) {
        Connection conn = DB.connect();
        try {
            PreparedStatement preSt = conn.prepareStatement("UPDATE savings SET saved_balance = ? WHERE user_id = ?");
            preSt.setDouble(1, amount);
            preSt.setInt(2, userID);
            preSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static double getCurrentSavingsPercentage(int userID) {
        double percentage = -1; // Default value indicating no savings activated
        Connection conn = DB.connect();
        try {
            PreparedStatement preSt = conn.prepareStatement("SELECT percentage FROM savings WHERE user_id = ?");
            preSt.setInt(1, userID);
            ResultSet result = preSt.executeQuery();
            if (result.next()) {
                percentage = result.getDouble("percentage");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close(); // Close the connection
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return percentage;
    }

    public static void updateSavingsPercentage(int userID, double percentage) {
        Connection conn = DB.connect();
        try {
            PreparedStatement preSt = conn.prepareStatement("UPDATE savings SET percentage = ? WHERE user_id = ?");
            preSt.setDouble(1, percentage);
            preSt.setInt(2, userID);
            preSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close(); // Close the connection
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static boolean setSavingsForCurrentUser(String status, double percentage, double savedBalance) {
        int userID = getUserID();
        if (userID == -1) {
            System.out.println("User does not exist.");
            return false;
        }
        setSavings(userID, status, percentage, savedBalance);
        return true;
    }

    public static void transferSavings(){
        LocalDate date = LocalDate.now();
        int dayOfMonth = 31;
        int daysInMonth = date.lengthOfMonth();
        if (dayOfMonth == daysInMonth) {
            double savedBalance = getSavedBalance();
            if (savedBalance > 0) {
                setSavingsStatus("inactive");
                debit(savedBalance, "Savings transfer", date);
                setSavingsStatus("active");
                updateSavedAmount(getUserID(), 0.0);
            }
        }
    }
    
    /*==========TRANSACTIONS================= */
    public static boolean debit(double amount, String description, LocalDate date) {
        String type = "debit";
        int userID = getUserID();
        if (userID == -1) {
            System.out.println("User does not exist.");
            return false;
        }

        Connection conn = DB.connect();
        try {
            conn.setAutoCommit(false);
            double updatedAmount = getAccountBalance() + amount;
            // Debit amount from user's balance
            PreparedStatement debitStmt = conn.prepareStatement(Query.makeTransaction);
            debitStmt.setDouble(1, userID);
            debitStmt.setString(2, type);
            debitStmt.setDouble(3, amount);
            debitStmt.setString(4, description);
            debitStmt.setDate(5, Date.valueOf(date));
            debitStmt.setDouble(6, updatedAmount);
            debitStmt.executeUpdate();

            conn.commit();
            updateAccountBalance(userID, updatedAmount);

            // checking and Updating savings balance
            if (getSavingsStatus()) {
                double savingsPercentage = getCurrentSavingsPercentage(userID);
                double savedAmount = (amount * savingsPercentage) / 100;
                double currentSavedAmount = getSavedBalance();
                credit(savedAmount, "Added to savings", date);
                updateSavedAmount(userID, currentSavedAmount + savedAmount);
            }
            return true;

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static boolean credit(double amount, String description, LocalDate date) {
        int overdueInstallments = getOverdueInstallments();
        if (overdueInstallments > 0) {
            System.out.println("You have " + overdueInstallments
                    + " overdue installment(s). Please pay the overdue installments to proceed with transactions.");
            return false;
        }

        final String type = "credit";
        int userID = getUserID();
        if (userID == -1) {
            System.out.println("User does not exist.");
            return false;
        }

        Connection conn = DB.connect();
        try {
            conn.setAutoCommit(false);

            // Credit amount to user's balance
            double updatedAmount = getAccountBalance() - amount;
            PreparedStatement creditStmt = conn.prepareStatement(Query.makeTransaction);
            creditStmt.setDouble(1, userID);
            creditStmt.setString(2, type);
            creditStmt.setDouble(3, amount);
            creditStmt.setString(4, description);
            creditStmt.setDate(5, Date.valueOf(date));
            creditStmt.setDouble(6, updatedAmount);
            creditStmt.executeUpdate();

            conn.commit();
            updateAccountBalance(userID, updatedAmount);
            return true;
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void updateAccountBalance(int userID, double amount) {
        Connection conn = DB.connect();
        try {
            PreparedStatement preSt = conn.prepareStatement(Query.update_account_balance);
            preSt.setDouble(1, amount);
            preSt.setInt(2, userID);
            // preSt.setDate(3, Date.valueOf(date));
            preSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static double getAccountBalance() {
        int userID = getUserID();
        Connection conn = DB.connect();
        try {
            PreparedStatement preSt = conn.prepareStatement(Query.get_account_balance);
            preSt.setInt(1, userID);
            ResultSet result = preSt.executeQuery();
            if (result.next()) {
                return result.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    private static void setAccountBalance(int userID, double balance) {
        Connection conn = DB.connect();
        try {
            PreparedStatement preSt = conn.prepareStatement(Query.account_balance);
            preSt.setInt(1, userID);
            preSt.setDouble(2, balance);
            preSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /*==========TRANSACTION HISTORY================= */
    public static List<Transaction> getTransactionHistory() {
        int userId = getUserID();
        List<Transaction> transactions = new ArrayList<>();
        Connection conn = DB.connect();
        try {
            PreparedStatement preSt = conn.prepareStatement("SELECT * FROM transactions WHERE user_id = ?");
            preSt.setInt(1, userId);
            ResultSet result = preSt.executeQuery();
            while (result.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransactionID(result.getInt("transaction_id"));
                transaction.setUserId(result.getInt("user_id"));
                transaction.setType(result.getString("transaction_type"));
                transaction.setAmount(result.getDouble("amount"));
                transaction.setDate(result.getDate("date"));
                transaction.setDescription(result.getString("description"));
                transaction.setUpdatedAmount(result.getDouble("updated_amount"));
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return transactions;
    }

    /*==========LOANS================= */
    public static boolean applyLoan(double principalAmount, double interestRate, int repaymentPeriod) {
        int userID = getUserID();
        if (userID == -1) {
            System.out.println("User does not exist.");
            return false;
        }

        double totalRepayment = principalAmount + (principalAmount * interestRate / 100);
        double monthlyInstallment = totalRepayment / repaymentPeriod;

        Connection conn = DB.connect();
        try {
            PreparedStatement preSt = conn.prepareStatement(Query.loans);
            preSt.setInt(1, userID);
            preSt.setDouble(2, principalAmount);
            preSt.setDouble(3, interestRate);
            preSt.setInt(4, repaymentPeriod);
            preSt.setDouble(5, totalRepayment);
            preSt.setString(6, "active");
            preSt.setDate(7, Date.valueOf(LocalDate.now()));
            preSt.setDouble(8, monthlyInstallment);
            preSt.setDate(9, Date.valueOf(LocalDate.now()));
            preSt.executeUpdate();

            debit(principalAmount, "Loan disbursement", LocalDate.now());
            
            System.out.println("Loan applied successfully!");
            
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean repayLoan(double amount) {
        int userID = getUserID();
        if (userID == -1) {
            System.out.println("User does not exist.");
            return false;
        }

        Connection conn = DB.connect();
        try {
            PreparedStatement preSt = conn.prepareStatement(Query.getLoan);
            preSt.setInt(1, userID);
            ResultSet result = preSt.executeQuery();

            if (result.next()) {
                double outstandingBalance = result.getDouble("outstanding_balance");
                if (amount > getAccountBalance()) {
                    System.out.println("Insufficient funds to repay loan.");
                    return false;
                }

                double newOutstandingBalance = outstandingBalance - amount;
                PreparedStatement updateStmt = conn.prepareStatement(
                        "UPDATE loans SET outstanding_balance = ?, last_payment_date = ? WHERE user_id = ?");
                updateStmt.setDouble(1, newOutstandingBalance);
                updateStmt.setDate(2, Date.valueOf(LocalDate.now()));
                updateStmt.setInt(3, userID);
                updateStmt.executeUpdate();

                if (newOutstandingBalance == 0) {
                    PreparedStatement statusStmt = conn.prepareStatement("DELETE FROM loans WHERE user_id = ?");
                    statusStmt.setInt(1, userID);
                    statusStmt.executeUpdate();
                }
                credit(amount, "Loan Payment", LocalDate.now());
                System.out.println("Loan repayment successful!");
                return true;
            } else {
                System.out.println("No active loan found.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static double getOutstandingBalance() {
        int userID = getUserID();
        if (userID == -1) {
            System.out.println("User does not exist.");
            return 0;
        }

        Connection conn = DB.connect();
        try {
            PreparedStatement preSt = conn.prepareStatement(Query.getLoan);
            preSt.setInt(1, userID);
            ResultSet result = preSt.executeQuery();
            if (result.next()) {
                double outstandingBalance = result.getDouble("outstanding_balance");
                return outstandingBalance;
            } else {
                //System.out.println("No active loan found.");
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static double getMonthlyInstallment() {
        int userID = getUserID();
        if (userID == -1) {
            System.out.println("User does not exist.");
            return 0;
        }

        Connection conn = DB.connect();
        try {
            PreparedStatement preSt = conn.prepareStatement(Query.getLoan);
            preSt.setInt(1, userID);
            ResultSet result = preSt.executeQuery();
            if (result.next()) {
                double monthlyInstallment = result.getDouble("monthly_installment");
                return monthlyInstallment;
            } else {
                //System.out.println("No active loan found.");
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static double getLoanBalance() {
        int userID = getUserID();
        if (userID == -1) {
            System.out.println("User does not exist.");
            return 0;
        }

        Connection conn = DB.connect();
        try {
            PreparedStatement preSt = conn.prepareStatement(Query.getLoan);
            preSt.setInt(1, userID);
            ResultSet result = preSt.executeQuery();
            if (result.next()) {
                double principalAmount = result.getDouble("principal_amount");
                return principalAmount;                
            } else {
                //System.out.println("No active loan found.");
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();   
        }
        return 0;
    }

    public static boolean isLoanActive() {
        int userID = getUserID();
        if (userID == -1) {
            System.out.println("User does not exist.");
            return false;
        }

        Connection conn = DB.connect();
        try {
            PreparedStatement preSt = conn.prepareStatement(Query.getLoan);
            preSt.setInt(1, userID);
            ResultSet result = preSt.executeQuery();
            if (result.next()) {
                String status = result.getString("status");
                return status.equals("active");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static boolean delayedRepay(){
        int userID = getUserID();
        if (userID == -1) {
            System.out.println("User does not exist.");
            return false;
        }

        Connection conn = DB.connect();
       try {
            PreparedStatement preSt = conn.prepareStatement(Query.getLoan);
            preSt.setInt(1, userID);
            ResultSet result = preSt.executeQuery();
            if (result.next()) {
                boolean delayed = result.getBoolean("delayed_repayment");
                return delayed;                
            } else {
                System.out.println("No active loan found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();   
        }
        return false;
    }

    public static int getOverdueInstallments() {
        int userID = getUserID();
        if (userID == -1) {
            System.out.println("User does not exist.");
            return 0;
        }

        Connection conn = DB.connect();
        try {
            PreparedStatement preSt = conn.prepareStatement(Query.getLoan);
            preSt.setInt(1, userID);
            ResultSet result = preSt.executeQuery();
            if (result.next()) {
                LocalDate lastPaymentDate = result.getDate("last_payment_date").toLocalDate();
                LocalDate currentDate = LocalDate.now();
                int overdueMonths = (int) java.time.temporal.ChronoUnit.MONTHS.between(lastPaymentDate, currentDate);
                return overdueMonths;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}