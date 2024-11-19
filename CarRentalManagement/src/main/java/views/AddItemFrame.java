package views;

import dbconnect.dbconnect;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddItemFrame extends JFrame {
    private JTextField titleField, authorField, isbnField, publishedYearField; // Updated field names
    private JButton submitButton, cancelButton;

    public AddItemFrame() {
        setTitle("Add New Item"); // Updated title of the frame
        setSize(600, 400); // Adjust as needed
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame

        // Set modern layout and gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                Color color1 = new Color(221, 24, 24); // Neon red
                Color color2 = new Color(51, 51, 51); // Dark gray
                GradientPaint gp = new GradientPaint(0, 0, color1, width, height, color2); // Left to right gradient
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        };
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding
        add(mainPanel); // Add the main panel to the frame

        // Font for labels and buttons
        Font modernFont = new Font("Segoe UI", Font.PLAIN, 16);

        // Title Label and Field
        JLabel titleLabel = new JLabel("Car name:");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(modernFont);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(titleLabel, gbc);

        titleField = createStyledTextField();
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(titleField, gbc);

        // Author Label and Field
        JLabel authorLabel = new JLabel("Seater:");
        authorLabel.setForeground(Color.WHITE);
        authorLabel.setFont(modernFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(authorLabel, gbc);

        authorField = createStyledTextField();
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(authorField, gbc);

        // ISBN Label and Field
        JLabel isbnLabel = new JLabel("Car No:");
        isbnLabel.setForeground(Color.WHITE);
        isbnLabel.setFont(modernFont);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(isbnLabel, gbc);

        isbnField = createStyledTextField();
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(isbnField, gbc);

        // Published Year Label and Field
        JLabel publishedYearLabel = new JLabel("Fuel Type:");
        publishedYearLabel.setForeground(Color.WHITE);
        publishedYearLabel.setFont(modernFont);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(publishedYearLabel, gbc);

        publishedYearField = createStyledTextField();
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(publishedYearField, gbc);

        // Submit Button
        submitButton = createStyledButton("Add Car");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(submitButton, gbc);

        // Cancel Button
        cancelButton = createStyledButton("Cancel");
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(cancelButton, gbc);

        // Action listeners
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBookToDatabase(); // Updated method call
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the frame
            }
        });

        setVisible(true);
    }

    // Helper method to create styled text fields
    private JTextField createStyledTextField() {
        JTextField textField = new JTextField(15);
        textField.setBackground(Color.DARK_GRAY); // Dark background
        textField.setForeground(Color.RED); // Neon red text
        textField.setBorder(BorderFactory.createLineBorder(Color.RED, 2)); // Red border
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 16)); // Modern font
        return textField;
    }

    // Helper method to create styled buttons
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.WHITE); // Solid white background
        button.setForeground(Color.RED); // Neon red text
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setFocusPainted(false); // Remove button focus border
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Rounded button edges
        button.setBorder(new RoundedBorder(20)); // Set rounded border

        return button;
    }

    // Method to add book data to the database
    private void addBookToDatabase() { 
        String title = titleField.getText(); 
        String author = authorField.getText(); // Updated variable name
        String isbn = isbnField.getText();
        String publishedYear = publishedYearField.getText(); // Updated variable name

        if (title.isEmpty() || author.isEmpty() || isbn.isEmpty() || publishedYear.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }

        Connection conn = dbconnect.connect();
        if (conn != null) {
            // Update the SQL statement to target the Books table
            String sql = "INSERT INTO Books (title, author, isbn, published_year) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, title);
                pstmt.setString(2, author);
                pstmt.setString(3, isbn);
                pstmt.setString(4, publishedYear);

                int rowsInserted = pstmt.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Car added successfully!"); 
                    // Clear the form fields after successful insertion
                    titleField.setText(""); 
                    authorField.setText("");
                    isbnField.setText("");
                    publishedYearField.setText("");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error adding Car: " + ex.getMessage()); 
            }
        }
    }

    // Custom border class for rounded buttons
    class RoundedBorder implements Border {
        private int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(radius + 1, radius + 1, radius + 2, radius);
        }

        public boolean isBorderOpaque() {
            return true;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }

    // Main method to run the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AddItemFrame().setVisible(true);
        });
    }
}
