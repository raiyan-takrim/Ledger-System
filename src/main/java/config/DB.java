package config;
import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DB {
    // I am using HikariCP for connection pooling. It is a high-performance JDBC connection pool library. It provides a simple and efficient way to manage database connections in Java applications. The HikariDataSource class is used to create a connection pool, and the connect() method retrieves a connection from the pool. The close() method is used to close the connection pool when it is no longer needed.

    private static HikariDataSource dataSource;
    static {
        // Load database connection details from .env file using Dotenv
        Dotenv dotenv = Dotenv.load();
        String url = dotenv.get("DB_URL");
        String user = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");

        // Building the HikariConfig with the database connection details and pool settings
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(password);
        config.setMaximumPoolSize(10); // Max connections in pool
        config.setMinimumIdle(5); // Min idle connections
        config.setConnectionTimeout(30000); // 30 seconds
        config.setIdleTimeout(600000); // 10 minutes
        config.setMaxLifetime(1800000); // 30 minutes

        // Initialize the HikariDataSource with the configuration
        dataSource = new HikariDataSource(config);
    }
    
    public static Connection connect() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            System.err.println("Failed to get connection from pool: " + e.getMessage());
            return null;
        }
    }
    public static void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}
