import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Donor {
    String name, bloodGroup, contact;

    Donor(String name, String bloodGroup, String contact) {
        this.name = name;
        this.bloodGroup = bloodGroup;
        this.contact = contact;
    }
}

public class BloodBankSystem extends JFrame {

    private ArrayList donorList = new ArrayList();
    private JTextField tfName, tfContact, tfSearch;
    private JComboBox cbBloodGroup;
    private JTable donorTable;
    private DefaultTableModel tableModel;

    public BloodBankSystem() {
        setTitle("Blood Bank Management System");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // --- Top Panel Title ---
        JLabel title = new JLabel("Blood Bank Management System", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(Color.RED);
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        // --- Form Panel (Left) ---
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(8, 1, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        formPanel.setBackground(new Color(255, 240, 240));

        tfName = new JTextField();
        tfContact = new JTextField();
        cbBloodGroup = new JComboBox(new String[]{"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"});
        tfSearch = new JTextField();

        JButton btnAdd = new JButton("Add Donor");
        JButton btnSearch = new JButton("Search by Group");
        JButton btnDelete = new JButton("Delete Selected");
        JButton btnShowAll = new JButton("Show All");

        formPanel.add(new JLabel("Name:"));
        formPanel.add(tfName);
        formPanel.add(new JLabel("Blood Group:"));
        formPanel.add(cbBloodGroup);
        formPanel.add(new JLabel("Contact:"));
        formPanel.add(tfContact);
        formPanel.add(btnAdd);
        formPanel.add(btnSearch);

        add(formPanel, BorderLayout.WEST);

        // --- Table Panel (Center) ---
        tableModel = new DefaultTableModel(new String[]{"Name", "Blood Group", "Contact"}, 0);
        donorTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(donorTable);

        add(scrollPane, BorderLayout.CENTER);

        // --- Bottom Panel ---
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(255, 240, 240));
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.add(new JLabel("Search Blood Group:"));
        bottomPanel.add(tfSearch);
        bottomPanel.add(btnShowAll);
        bottomPanel.add(btnDelete);

        add(bottomPanel, BorderLayout.SOUTH);

        // --- Event Listeners ---

        btnAdd.addActionListener(e -&gt; addDonor());
        btnSearch.addActionListener(e -&gt; searchByBloodGroup());
        btnDelete.addActionListener(e -&gt; deleteSelected());
        btnShowAll.addActionListener(e -&gt; loadTable());

        // Sample data
        donorList.add(new Donor("John Doe", "A+", "9876543210"));
        donorList.add(new Donor("Jane Smith", "O-", "9123456789"));
        loadTable();
    }

    private void addDonor() {
        String name = tfName.getText().trim();
        String contact = tfContact.getText().trim();
        String blood = cbBloodGroup.getSelectedItem().toString();

        if (name.isEmpty() || contact.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter all details.");
            return;
        }

        donorList.add(new Donor(name, blood, contact));
        loadTable();
        tfName.setText("");
        tfContact.setText("");
    }

    private void searchByBloodGroup() {
        String bg = tfSearch.getText().trim().toUpperCase();
        if (bg.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter blood group to search.");
            return;
        }

        tableModel.setRowCount(0);
        for (Donor d : donorList) {
            if (d.bloodGroup.equalsIgnoreCase(bg)) {
                tableModel.addRow(new Object[]{d.name, d.bloodGroup, d.contact});
            }
        }
    }

    private void deleteSelected() {
        int row = donorTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a row to delete.");
            return;
        }
        String name = tableModel.getValueAt(row, 0).toString();
        donorList.removeIf(d -&gt; d.name.equals(name));
        loadTable();
    }

    private void loadTable() {
        tableModel.setRowCount(0);
        for (Donor d : donorList) {
            tableModel.addRow(new Object[]{d.name, d.bloodGroup, d.contact});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -&gt; new BloodBankSystem().setVisible(true));
    }
}


