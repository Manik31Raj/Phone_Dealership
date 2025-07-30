import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

class DealershipUI extends JFrame {
    private Inventory inventory;
    private JTable table;
    private DefaultTableModel tableModel;

    public DealershipUI() {
        inventory = new Inventory();
        setTitle("Phone Dealership");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Table
        String[] columns = {"ID", "Brand", "Model", "Price", "Quantity"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Buttons
        JButton addBtn = new JButton("Phone Stock Entry");
        JButton sellBtn = new JButton("Sell Phone");
        JButton refreshBtn = new JButton("Refresh Inventory");

        addBtn.addActionListener(e -> openAddDialog());
        sellBtn.addActionListener(e -> openSellDialog());
        refreshBtn.addActionListener(e -> loadInventory());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBtn);
        buttonPanel.add(sellBtn);
        buttonPanel.add(refreshBtn);

        // Layout
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        loadInventory();
        setVisible(true);
    }

    private void loadInventory() {
        tableModel.setRowCount(0); // clear table
        List<Phone> phones = inventory.getAllPhones();
        for (Phone p : phones) {
            tableModel.addRow(new Object[]{p.getId(), p.getBrand(), p.getModel(), p.getPrice(), p.getQuantity()});
        }
    }

    private void openAddDialog() {
        JTextField brandField = new JTextField();
        JTextField modelField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField quantityField = new JTextField();

        Object[] message = {
                "Brand:", brandField,
                "Model:", modelField,
                "Price:", priceField,
                "Quantity:", quantityField,
        };

        int option = JOptionPane.showConfirmDialog(this, message, "Add Phone", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String brand = brandField.getText();
                String model = modelField.getText();
                double price = Double.parseDouble(priceField.getText());
                int qty = Integer.parseInt(quantityField.getText());

                inventory.addPhone(new Phone(brand, model, price, qty));
                loadInventory();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input.");
            }
        }
    }

    private void openSellDialog() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a phone from the table.");
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0);
        String brand = (String) tableModel.getValueAt(row, 1);
        String model = (String) tableModel.getValueAt(row, 2);
        int stock = (int) tableModel.getValueAt(row, 4);

        String qtyStr = JOptionPane.showInputDialog(this, "Enter quantity to sell for " + brand + " " + model + ":");
        if (qtyStr != null) {
            try {
                int qty = Integer.parseInt(qtyStr);
                if (qty > 0) {
                    if (qty <= stock) {
                        inventory.sellPhone(id, qty);
                        loadInventory();
                    } else {
                        JOptionPane.showMessageDialog(this, "Not enough stock.");
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid quantity.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DealershipUI());
    }
}
