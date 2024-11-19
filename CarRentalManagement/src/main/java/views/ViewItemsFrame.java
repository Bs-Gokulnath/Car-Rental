package views;

import dbconnect.dbconnect;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ViewItemsFrame extends JFrame {

    private JTable booksTable;
    private DefaultTableModel tableModel;

    public ViewItemsFrame() {
        setTitle("View Car");
        setSize(600, 400); // Adjust the window size as needed
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame
        
        // Set layout and background color
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK); // Black background
        
        // Table model to hold book data
        String[] columns = {"ID", "Car Name", "Seater", "Car No", "Fuel Type"};
        tableModel = new DefaultTableModel(columns, 0);
        booksTable = new JTable(tableModel);
        booksTable.setRowHeight(25); // Increase row height
        booksTable.setFont(new Font("Segoe UI", Font.PLAIN, 16)); // Set custom font
        booksTable.setBackground(Color.DARK_GRAY); 
        Color neonPink = new Color(255, 20, 147); // Bright neon pink
        booksTable.setForeground(neonPink); 
        booksTable.setSelectionBackground(neonPink); // Selection background color
        booksTable.setSelectionForeground(Color.BLACK); // Selection text color

        // Set header font and colors
        booksTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16)); // Custom header font
        booksTable.getTableHeader().setBackground(Color.BLACK); // Header background color
        booksTable.getTableHeader().setForeground(neonPink); // Header text color
        booksTable.getTableHeader().setBorder(BorderFactory.createLineBorder(Color.PINK)); // Header border

        // Adding table to scroll pane for better UI
        JScrollPane scrollPane = new JScrollPane(booksTable);
        add(scrollPane, BorderLayout.CENTER);

        // Fetch book data from database
        loadBooksFromDatabase();

        // Display the frame
        setVisible(true);
    }

    // Method to load book data from the database
    private void loadBooksFromDatabase() {
        Connection conn = dbconnect.connect();
        if (conn != null) {
            String query = "SELECT book_id, title, author, isbn, published_year FROM Books";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    // Get data from the current row
                    int id = rs.getInt("book_id");
                    String title = rs.getString("title");
                    String author = rs.getString("author");
                    String isbn = rs.getString("isbn");
                    String publishedYear = rs.getString("published_year");

                    // Add the row to the table model
                    Object[] row = {id, title, author, isbn, publishedYear};
                    tableModel.addRow(row);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error fetching Car data.");
            }
        }
    }
}
