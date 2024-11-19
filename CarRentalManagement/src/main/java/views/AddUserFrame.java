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

public class AddUserFrame extends JFrame {
    
    private JTextField userNameField, emailField;
    private JPasswordField passwordField;
    private JButton submitButton, cancelButton;

    public AddUserFrame() {
        setTitle("Add New User");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300); // Adjust the size as per your need
        setLocationRelativeTo(null); // Center the frame

        // Set modern layout and background
        setLayout(new GridBagLayout());
        getContentPane().setBackground(Color.BLACK); // Black background
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding for elements

        // Modern font for labels and buttons
        Font modernFont = new Font("Roboto", Font.PLAIN, 16); // Or use "Montserrat"

        // Neon red color
        Color neonRed = new Color(255, 0, 102); // Vivid neon red

        // User Name
        JLabel userNameLabel = new JLabel("Name:");
        userNameLabel.setForeground(neonRed); // Neon red text
        userNameLabel.setFont(modernFont);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(userNameLabel, gbc);

        userNameField = createStyledTextField(neonRed);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(userNameField, gbc);

        // Email (Gmail)
        JLabel emailLabel = new JLabel("Email :");
        emailLabel.setForeground(neonRed); // Neon red text
        emailLabel.setFont(modernFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(emailLabel, gbc);

        emailField = createStyledTextField(neonRed);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(emailField, gbc);

        // Password
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(neonRed); // Neon red text
        passwordLabel.setFont(modernFont);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        add(passwordLabel, gbc);

        passwordField = createStyledPasswordField(neonRed);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(passwordField, gbc);

        // Submit Button
        submitButton = createStyledButton("Submit", neonRed);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        add(submitButton, gbc);

        // Cancel Button
        cancelButton = createStyledButton("Cancel", neonRed);
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(cancelButton, gbc);

        // Action Listeners
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = userNameField.getText();
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                if (userName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(AddUserFrame.this, "All fields must be filled out!");
                } else {
                    // Add user to the database
                    addUserToDatabase(userName, email, password);

                    // Clear the form
                    userNameField.setText("");
                    emailField.setText("");
                    passwordField.setText("");

                    JOptionPane.showMessageDialog(AddUserFrame.this, "User added successfully!");
                }
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
    private JTextField createStyledTextField(Color neonRed) {
        JTextField textField = new JTextField(15);
        textField.setBackground(Color.DARK_GRAY); // Dark background
        textField.setForeground(neonRed); // Neon red text
        textField.setBorder(BorderFactory.createLineBorder(neonRed, 2)); // Neon red border
        textField.setFont(new Font("Roboto", Font.PLAIN, 16)); // Modern font
        return textField;
    }

    // Helper method to create styled password fields
    private JPasswordField createStyledPasswordField(Color neonRed) {
        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setBackground(Color.DARK_GRAY); // Dark background
        passwordField.setForeground(neonRed); // Neon red text
        passwordField.setBorder(BorderFactory.createLineBorder(neonRed, 2)); // Neon red border
        passwordField.setFont(new Font("Roboto", Font.PLAIN, 16)); // Modern font
        return passwordField;
    }

    // Helper method to create styled buttons
    private JButton createStyledButton(String text, Color neonRed) {
        JButton button = new JButton(text);
        button.setBackground(neonRed); // Neon red background
        button.setForeground(Color.BLACK); // Black text
        button.setFont(new Font("Roboto", Font.BOLD, 16));
        button.setFocusPainted(false); // Remove button focus border
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Rounded button edges
        button.setBorder(new RoundedBorder(10));

        return button;
    }

    // Method to insert user data into the database
    private void addUserToDatabase(String userName, String email, String password) {
        String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";

        try (Connection conn = dbconnect.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userName);
            pstmt.setString(2, email);
            pstmt.setString(3, password);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error adding user to the database: " + e.getMessage());
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
}
