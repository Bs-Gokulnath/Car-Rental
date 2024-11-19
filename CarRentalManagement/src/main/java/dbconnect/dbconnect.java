package dbconnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbconnect {
    public static Connection connect() {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC"); // Load SQLite JDBC Driver
            connection = DriverManager.getConnection("jdbc:sqlite:F:\\Car Rental Manage\\-inventory-management-system-java\\InventoryManagement\\database\\library.db"); 
        } catch (ClassNotFoundException e) {
            System.out.println("SQLite JDBC driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database.");
            e.printStackTrace();
        }
        return connection; // Returns null if connection fails
    }
}
