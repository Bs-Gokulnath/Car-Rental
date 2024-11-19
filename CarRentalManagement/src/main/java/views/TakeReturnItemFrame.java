package views;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class TakeReturnItemFrame extends JFrame {

    public TakeReturnItemFrame() {
        setTitle("Take or Return Inventory");
        setSize(500, 400); // Adjusted size
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set layout and custom gradient background
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
        add(mainPanel); // Add the main panel to the frame

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around components
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;

        // Create a modern font
        Font labelFont = new Font("Segoe UI", Font.BOLD, 16);

        // Labels and fields for Book ID and User ID
        JLabel bookIDLabel = new JLabel("Inventory ID:");
        bookIDLabel.setFont(labelFont);
        bookIDLabel.setForeground(Color.WHITE); // White text for contrast

        JTextField bookIDField = new JTextField(15);
        styleTextField(bookIDField);

        JLabel userIDLabel = new JLabel("User ID:");
        userIDLabel.setFont(labelFont);
        userIDLabel.setForeground(Color.WHITE); // White text for contrast

        JTextField userIDField = new JTextField(15);
        styleTextField(userIDField);

        // Radio buttons for selecting either 'Take Inventory' or 'Return Inventory'
        JRadioButton takeBook = createTransparentRadioButton("Take Inventory");
        JRadioButton returnBook = createTransparentRadioButton("Return Inventory");

        ButtonGroup group = new ButtonGroup();
        group.add(takeBook);
        group.add(returnBook);

        // Arrange components using GridBagConstraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(bookIDLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(bookIDField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(userIDLabel, gbc);

        gbc.gridx = 1;
        mainPanel.add(userIDField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(takeBook, gbc);

        gbc.gridx = 1;
        mainPanel.add(returnBook, gbc);

        // Submit button with rounded corners
        JButton submitButton = createRoundedButton("Submit");
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2; // Makes the button span two columns
        mainPanel.add(submitButton, gbc);

        setVisible(true);
    }

    private void styleTextField(JTextField textField) {
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setForeground(Color.BLACK);
        textField.setBackground(Color.WHITE);
        textField.setBorder(BorderFactory.createLineBorder(Color.RED, 2)); // Red border
        textField.setOpaque(true); // Make sure the background is opaque
    }

    private JRadioButton createTransparentRadioButton(String text) {
        JRadioButton radioButton = new JRadioButton(text);
        radioButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        radioButton.setForeground(Color.WHITE); // White text for contrast
        radioButton.setOpaque(false); // Set to transparent
        radioButton.setFocusPainted(false); // Remove focus outline
        radioButton.setContentAreaFilled(false); // Remove default button area fill
        return radioButton;
    }

    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(Color.WHITE); // Solid white background
        button.setForeground(Color.RED); // Neon red text
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Hand cursor for better UX
        button.setOpaque(true);
        button.setBorder(new RoundedBorder(20)); // Set rounded border

        // Adding hover effect for the button
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.RED); // Change background to red on hover
                button.setForeground(Color.WHITE); // Change text to white on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.WHITE); // Revert to original background
                button.setForeground(Color.RED); // Revert to original text color
            }
        });

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TakeReturnItemFrame::new);
    }
}
