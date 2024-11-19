package views;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame {

    private JLabel title;
    private JLabel itemsSection;
    private JLabel employeesSection;
    private JButton addItemBtn, viewItemsBtn, updateItemBtn, deleteItemBtn;

    public HomePage() {
        // Set frame properties
        setTitle("Car Rental System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setSize(600, 400); // Initial size
        setLocationRelativeTo(null); // Center the window

        // Main panel using GridBagLayout for flexibility
        JPanel mainPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                Color color1 = new Color(230, 132, 174);
                Color color2 = new Color(121, 203, 202);
                Color color3 = new Color(119, 161, 211);
                GradientPaint gp = new GradientPaint(width, 0, color1, 0, 0, color3);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        };

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        title = new JLabel("Car Rental System", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 10, 20, 10);
        mainPanel.add(title, gbc);

        // Inventory Section
        itemsSection = new JLabel("Car Rental", SwingConstants.CENTER);
        itemsSection.setFont(new Font("Segoe UI", Font.BOLD, 20));
        itemsSection.setForeground(Color.WHITE);
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        mainPanel.add(itemsSection, gbc);

        // Inventory Section Buttons
        addItemBtn = new JButton("Add New Car");
        viewItemsBtn = new JButton("View Available Cars");
        updateItemBtn = new JButton("Update Cars Garage"); // New update item button
        deleteItemBtn = new JButton("Remove Car"); // New delete button

        styleButton(addItemBtn, new Color(0, 153, 204));
        styleButton(viewItemsBtn, new Color(255, 127, 80));
        styleButton(updateItemBtn, new Color(154, 205, 50)); // Light green for update button
        styleButton(deleteItemBtn, new Color(255, 99, 71)); // Tomato color for delete button

        gbc.gridy = 2; // Set y position for buttons
        gbc.gridwidth = 1; // Set grid width to 1 for buttons
        gbc.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(addItemBtn, gbc); // Add the "Add New Inventory" button

        gbc.gridx = 1; // Move to the next column
        mainPanel.add(viewItemsBtn, gbc); // Add the "View Available Inventory" button

        gbc.gridx = 0; // Move back to the first column
        gbc.gridy = 3; // Move to the next row
        mainPanel.add(updateItemBtn, gbc); // Add the "Update Inventory Item" button

        gbc.gridx = 1; // Move to the next column for delete button
        mainPanel.add(deleteItemBtn, gbc); // Add the "Delete Inventory Item" button

        add(mainPanel, BorderLayout.CENTER);

        // Action listeners for buttons
        addItemBtn.addActionListener(e -> new AddItemFrame());
        viewItemsBtn.addActionListener(e -> new ViewItemsFrame());

        // Action listener for update item button
        updateItemBtn.addActionListener(e -> {
            String sku = JOptionPane.showInputDialog("Enter CarNo of item to update:");
            if (sku != null && !sku.trim().isEmpty()) {
                new UpdateItemFrame(sku); // Pass SKU to open the correct item update form
            } else {
                JOptionPane.showMessageDialog(null, "CarNo is required to update an item.");
            }
        });

        // Action listener for delete item button
        deleteItemBtn.addActionListener(e -> {
            String sku = JOptionPane.showInputDialog("Enter CarNo of item to delete:");
            if (sku != null && !sku.trim().isEmpty()) {
                // Call the method to delete the item (you would need to implement this)
                deleteItem(sku);
            } else {
                JOptionPane.showMessageDialog(null, "CarNo is required to delete an item.");
            }
        });

        adjustComponentSizes();
    }

    // Method to delete an item based on SKU
    private void deleteItem(String sku) {
        // Logic for deleting the item from the database
        JOptionPane.showMessageDialog(this, "Item with CarNo " + sku + " deleted successfully!"); // Placeholder for successful deletion message
    }

    // Adjust button sizes dynamically
    private void adjustComponentSizes() {
        int width = getWidth();
        int height = getHeight();
        title.setFont(new Font("Segoe UI", Font.BOLD, width / 25));
        itemsSection.setFont(new Font("Segoe UI", Font.BOLD, width / 35));

        Dimension buttonSize = new Dimension(width / 3, height / 10);
        addItemBtn.setPreferredSize(buttonSize);
        viewItemsBtn.setPreferredSize(buttonSize);
        updateItemBtn.setPreferredSize(buttonSize);
        deleteItemBtn.setPreferredSize(buttonSize); // Set size for delete button

        revalidate();
        repaint();
    }

    // Method to style the buttons
    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBorder(new RoundedBorder(20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setContentAreaFilled(false);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter());
                button.repaint();
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
                button.repaint();
            }
        });
    }

    public static class RoundedBorder implements Border {
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
        SwingUtilities.invokeLater(() -> {
            HomePage homePage = new HomePage();
            homePage.setVisible(true);
        });
    }
}
