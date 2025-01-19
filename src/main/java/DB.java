import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    public static Connection connect() {
        String url = "jdbc:postgresql://ep-cool-frog-a1i885rb.ap-southeast-1.aws.neon.tech:5432/verceldb";
        String user = "default";
        String password = "BGy1iEgA8OWu";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            // System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (conn != null) {
            return conn;
        } else {
            return null;

        }
    }
}
