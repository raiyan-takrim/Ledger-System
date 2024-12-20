import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;
import java.util.Scanner;

public class UserActions {
    public static String email;
    private static String name, password, confirmPassword;
    static Scanner input = new Scanner(System.in);

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
                    break;
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

    public static boolean setSavingsForCurrentUser(String status, double percentage, double savedBalance) {
        int userID = getUserID();
        if (userID == -1) {
            System.out.println("User does not exist.");
            return false;
        }
        setSavings(userID, status, percentage, savedBalance);
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
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
    public static String getUserName(){
        Connection conn = DB.connect();
        try{
            PreparedStatement preSt = conn.prepareStatement(Query.getUserName);
            preSt.setString(1, email);
            ResultSet result = preSt.executeQuery();
            if(result.next()){
                return result.getString(1);
            }
        }catch(SQLException e){
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

    public static boolean credit(double amount, String description, LocalDate date) {
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
            PreparedStatement creditStmt = conn.prepareStatement(Query.makeTransaction);
            creditStmt.setDouble(1, userID);
            creditStmt.setString(2, type);
            creditStmt.setDouble(3, amount);
            creditStmt.setString(4, description);
            creditStmt.setDate(5, Date.valueOf(date));
            creditStmt.executeUpdate();

            conn.commit();
            updateAccountBalance(userID, amount, type, date);
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

            // Debit amount from user's balance
            PreparedStatement debitStmt = conn.prepareStatement(Query.makeTransaction);
            debitStmt.setDouble(1, userID);
            debitStmt.setString(2, type);
            debitStmt.setDouble(3, amount);
            debitStmt.setString(4, description);
            debitStmt.setDate(5, Date.valueOf(date));
            debitStmt.executeUpdate();

            conn.commit();
            amount = getAccountBalance() - amount;
            updateAccountBalance(userID, amount, type, date);
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

    private static void updateAccountBalance(int userID, double amount, String type, LocalDate date) {
        Connection conn = DB.connect();
        try {
            PreparedStatement preSt = conn.prepareStatement(Query.accountBalance);
            preSt.setInt(1, userID);
            preSt.setDouble(2, amount);
            preSt.setDate(3, Date.valueOf(date));
            preSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static double getAccountBalance() {
        int userID = getUserID();
        Connection conn = DB.connect();
        try {
            PreparedStatement preSt = conn.prepareStatement(Query.getAccountBalance);
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

}
