package expense_tracker;

import com.formdev.flatlaf.FlatDarkLaf;
import expense_tracker.Conn;
import javax.swing.*;
import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class ExpensesIncomesTracker extends JFrame {
    
    private final ExpenseIncomeTableModel tableModel;
    private final JTable table;
    private final JTextField dateField;
    private final JTextField descriptionField;
    private final JTextField amountField;
    private final JComboBox<String> typeCombobox;
    private final JButton addButton;
    private final JButton outButton;
    private final JLabel balanceLabel;
    private double balance;
    private String user;
    public ExpensesIncomesTracker(String username){
        this.user=username;
        try{
            UIManager.setLookAndFeel(new FlatDarkLaf());
        }
        catch(Exception ex){
            System.err.println("Failed to Set FlatDarkLaf LookAndFeel");
        }
        UIManager.put("TextField.foreground", Color.WHITE);
        UIManager.put("TextField.background", Color.DARK_GRAY);
        UIManager.put("TextField.caretForeground", Color.RED);
        UIManager.put("ComboBox.foreground", Color.YELLOW);
        UIManager.put("ComboBox.selectionForeground", Color.WHITE);
        UIManager.put("ComboBox.selectionBackground", Color.BLACK);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.background", Color.ORANGE);
        UIManager.put("Label.foreground", Color.WHITE);
        Font customFont = new Font("Arial", Font.PLAIN, 18);
        UIManager.put("Label.font", customFont);
        UIManager.put("TextField.font", customFont);
        UIManager.put("ComboBox.font", customFont);
        UIManager.put("Button.font", customFont);
        balance = 0.0;
        tableModel = new ExpenseIncomeTableModel();
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        dateField = new JTextField(10);
        descriptionField = new JTextField(20);
        amountField = new JTextField(10);
        typeCombobox = new JComboBox<>(new String[]{"Expense","Income"});
        addButton = new JButton("Add");
        outButton = new JButton("Logout");
        addButton.addActionListener(e -> addEntry());
        outButton.addActionListener(e -> logout());
        balanceLabel = new JLabel("Balance: $" + balance);
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Date"));
        inputPanel.add(dateField);
        
        inputPanel.add(new JLabel("Description"));
        inputPanel.add(descriptionField);
        
        inputPanel.add(new JLabel("Amount"));
        inputPanel.add(amountField);
        
        inputPanel.add(new JLabel("Type"));
        inputPanel.add(typeCombobox);
        
        inputPanel.add(addButton);
        inputPanel.add(outButton);
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(balanceLabel);
        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        setTitle("Expanses And Incomes Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        try {
                    Conn con = new Conn();
                    String sql = "select date,des,amount,typer from transactions where username=?";
                    PreparedStatement st = con.c.prepareStatement(sql);

                    st.setString(1, user);

                    ResultSet rs = st.executeQuery();
                    while (rs.next()) {
                        String date=rs.getString(1);
                        String des=rs.getString(2);
                        String amount=rs.getString(3);
                        String type=rs.getString(4);
                        addEntry(date,des,amount,type);
                        
                    }
                       
		} catch (Exception e2) {
                    e2.printStackTrace();
		}
        
    }
    private void addEntry(String date,String description,String amount,String type)
    {
        double x=Double.parseDouble(amount);
        String dis="Income";
        if(type.equals("expense"))
        {
            dis="Expense";
            x=Double.parseDouble(amount)*-1;
        }
        ExpenseIncomeEntry entry = new ExpenseIncomeEntry(date, description, Double.parseDouble(amount), dis);
        tableModel.addEntry(entry);
        balance += x;
        balanceLabel.setText("Balance: $"+balance);
        clearInputFields();
    }
    private void addEntry()
    {
        String date = dateField.getText();
        String description = descriptionField.getText();
        String amountStr = amountField.getText();
        String type = (String)typeCombobox.getSelectedItem();
        type=type.toLowerCase();
        int typ=0;
        double amount;
        if(amountStr.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Enter the Amount", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try
        {
            amount = Double.parseDouble(amountStr);
        }
        catch(NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(this, "Invalid Amount Format", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(type.equals("expense"))
        {
            amount *= -1;
            typ=1;
        }
        
        try {
                    Conn con = new Conn();
                    String sql = "insert into transactions (date,des,amount,typer,username) value(?,?,?,?,?)";
                    PreparedStatement st = con.c.prepareStatement(sql);
                    st.setString(1,date);
                    st.setString(2,description);
                    st.setString(3,amountStr);
                    st.setString(4,type);
                    st.setString(5,user);

                    st.executeUpdate();
        }catch (Exception e2) {
                    e2.printStackTrace();
		}
        
        ExpenseIncomeEntry entry = new ExpenseIncomeEntry(date, description, amount, type);
        tableModel.addEntry(entry);
        balance += amount;
        try {
                    Conn con = new Conn();
                    String sql = "update current_balance set balance=? where username=?";
                    PreparedStatement st = con.c.prepareStatement(sql);
                    st.setString(1,String.valueOf(balance));
                    st.setString(2,user);

                    st.executeUpdate();
        }catch (Exception e2) {
                    e2.printStackTrace();
		}   
        balanceLabel.setText("Balance: $"+balance);
        clearInputFields();
    }
    private void logout()
    {
        this.setVisible(false);
        new Login().setVisible(true);
    }
    private void clearInputFields()
    {
        dateField.setText("");
        descriptionField.setText("");
        amountField.setText("");
        typeCombobox.setSelectedIndex(0);
    }
    
}