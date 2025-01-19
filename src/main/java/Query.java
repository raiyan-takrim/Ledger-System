public class Query {
    static String getUserID = "SELECT user_id FROM users WHERE email = ?";
    static String addUser = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
    static String userExists = "SELECT COUNT(*) FROM users WHERE email = ?";
    static String getPassword = "SELECT password from users WHERE email = ?";
    static String makeTransaction = "INSERT INTO transactions (user_id, transaction_type, amount, description, date, updated_amount) VALUES (?, ?, ?, ?, ?, ?)";
//    static String savings = "INSERT INTO savings (user_id, status, percentage) VALUES (?, ?, ?)";
//    static String getSaving = "SELECT * FROM savings WHERE user_id = ?";
    static String loans = "INSERT INTO loans (user_id, principal_amount, interest_rate, repayment_period, outstanding_balance, status, created_at, monthly_installment, last_payment_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    static String getLoan = "SELECT * FROM loans WHERE user_id = ?";
//    static String bank = "INSERT INTO banks (bank_name, interest_rate) VALUES (?, ?)";
    static String account_balance = "INSERT INTO account_balance (user_id, balance) VALUES (?, ?)";
    static String update_account_balance = "UPDATE account_balance SET balance = ? WHERE user_id = ?";
    static String get_account_balance = "SELECT balance FROM account_balance WHERE user_id = ?";
    static String getUserName = "SELECT name FROM users WHERE email = ?";
    static String setSavings = "INSERT INTO savings (user_id, status, percentage, saved_balance) VALUES (?, ?, ?, ?)";
    static String getSaved = "SELECT saved_balance FROM savings WHERE user_id = ?";
    static String get_savings_status = "SELECT status FROM savings WHERE user_id = ?";
    static String update_savings_status = "UPDATE savings SET status = ?, percentage = ? WHERE user_id = ?";
}