package views;

import dbconnect.dbconnect;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UpdateItemFrame extends JFrame {
    private JTextField titleField, authorField, isbnField, publishedYearField;
    private JButton submitButton, cancelButton;
    private String itemIsbn; // ISBN of the item to update

    public UpdateItemFrame(String itemIsbn) {
        this.itemIsbn = itemIsbn;

        setTitle("Update Book"); // Updated title
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set up main panel with gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                Color color1 = new Color(221, 24, 24); // Neon red
                Color color2 = new Color(51, 51, 51); // Dark gray
                GradientPaint gp = new GradientPaint(0, 0, color1, width, height, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        };
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        add(mainPanel);

        // Font for labels and buttons
        Font modernFont = new Font("Segoe UI", Font.PLAIN, 16);

        // Title Label and Field
        JLabel titleLabel = new JLabel("Title:");
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
        JLabel authorLabel = new JLabel("Author:");
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
        JLabel isbnLabel = new JLabel("ISBN:");
        isbnLabel.setForeground(Color.WHITE);
        isbnLabel.setFont(modernFont);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(isbnLabel, gbc);

        isbnField = createStyledTextField();
        isbnField.setText(itemIsbn); // Display the ISBN
        isbnField.setEditable(false); // Make ISBN uneditable
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(isbnField, gbc);

        // Published Year Label and Field
        JLabel publishedYearLabel = new JLabel("Published Year:");
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

        // Load existing book data
        loadItemData(itemIsbn);

        // Submit Button
        submitButton = createStyledButton("Update Book"); // Updated button text
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
                updateItemInDatabase();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    // Load book data from the database
    private void loadItemData(String isbn) {
        Connection conn = dbconnect.connect();
        if (conn != null) {
            String sql = "SELECT title, author, published_year FROM Books WHERE isbn = ?"; // Updated table name
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, isbn);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    titleField.setText(rs.getString("title"));
                    authorField.setText(rs.getString("author"));
                    publishedYearField.setText(rs.getString("published_year"));
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error loading book: " + ex.getMessage());
            }
        }
    }

    // Update book data in the database
    private void updateItemInDatabase() {
        String title = titleField.getText();
        String author = authorField.getText();
        String publishedYear = publishedYearField.getText();

        if (title.isEmpty() || author.isEmpty() || publishedYear.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }

        Connection conn = dbconnect.connect();
        if (conn != null) {
            String sql = "UPDATE Books SET title = ?, author = ?, published_year = ? WHERE isbn = ?"; // Updated table name
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, title);
                pstmt.setString(2, author);
                pstmt.setString(3, publishedYear);
                pstmt.setString(4, itemIsbn);

                int rowsUpdated = pstmt.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "Book updated successfully!"); // Updated message
                    dispose(); // Close the frame after update
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error updating book: " + ex.getMessage()); // Updated message
            }
        }
    }

    // Helper method to create styled text fields
    private JTextField createStyledTextField() {
        JTextField textField = new JTextField(15);
        textField.setBackground(Color.DARK_GRAY);
        textField.setForeground(Color.RED);
        textField.setBorder(BorderFactory.createLineBorder(Color.RED, 2));
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        return textField;
    }

    // Helper method to create styled buttons
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.RED);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Rounded button edges
        button.setBorder(new RoundedBorder(20));
        return button;
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

    // Main method to run the application for testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new UpdateItemFrame("12345").setVisible(true); // Test with an example ISBN
        });
    }
}
