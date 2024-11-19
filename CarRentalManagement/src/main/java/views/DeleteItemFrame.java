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

public class DeleteItemFrame extends JFrame {
    private JTextField isbnField; // Field for ISBN input
    private JButton deleteButton, cancelButton;

    public DeleteItemFrame() {
        setTitle("Delete Car"); // Title of the frame
        setSize(400, 200); // Size of the frame
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame

        // Set up the main panel with gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                Color color1 = new Color(221, 24, 24); // Neon red
                Color color2 = new Color(51, 51, 51); // Dark gray
                GradientPaint gp = new GradientPaint(0, 0, color1, width, height, color2); // Gradient
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

        // ISBN Label and Field
        JLabel isbnLabel = new JLabel("ISBN:");
        isbnLabel.setForeground(Color.WHITE);
        isbnLabel.setFont(modernFont);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(isbnLabel, gbc);

        isbnField = createStyledTextField(); // Create the styled text field
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(isbnField, gbc);

        // Delete Button
        deleteButton = createStyledButton("Delete Car");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(deleteButton, gbc);

        // Cancel Button
        cancelButton = createStyledButton("Cancel");
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(cancelButton, gbc);

        // Action listeners
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteBookFromDatabase(); // Call method to delete book
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the frame
            }
        });

        setVisible(true); // Make the frame visible
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

    // Method to delete book data from the database
    private void deleteBookFromDatabase() {
        String isbn = isbnField.getText(); // Get the ISBN from the input field

        if (isbn.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ISBN field is required.");
            return; // Exit if ISBN is empty
        }

        Connection conn = dbconnect.connect();
        if (conn != null) {
            // SQL statement to delete the book from the Books table
            String sql = "DELETE FROM Books WHERE isbn = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, isbn); // Set the ISBN parameter

                int rowsDeleted = pstmt.executeUpdate(); // Execute the update
                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(this, "Car deleted successfully!");
                    isbnField.setText(""); // Clear the ISBN field after deletion
                } else {
                    JOptionPane.showMessageDialog(this, "No Item found with the given CarNo.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error deleting Car: " + ex.getMessage());
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
            new DeleteItemFrame().setVisible(true); // Launch the delete item frame
        });
    }
}
