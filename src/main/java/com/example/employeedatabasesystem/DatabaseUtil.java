package com.example.employeedatabasesystem;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {

    private static final String URL = "jdbc:postgresql://localhost:5432/employee_db";
    private static final String USER = "postgres";  // Replace with your PostgreSQL username
    private static final String PASSWORD = "123456";  // Replace with your PostgreSQL password

    // Get connection to the database
    public static Connection getConnection() throws SQLException {
        try {
            // Register the PostgreSQL driver explicitly (just in case)
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("PostgreSQL driver not found.");
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Close resources (Connection, Statement, ResultSet)
    public static void closeResources(AutoCloseable... resources) {
        for (AutoCloseable resource : resources) {
            try {
                if (resource != null) {
                    resource.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}