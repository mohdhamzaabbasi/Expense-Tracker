 package expense_tracker;
import expense_tracker.ExpensesIncomesTracker;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener{

	private JPanel panel;
	private JTextField textField;
	private JPasswordField passwordField;
        private JButton b1,b2,b3,b4;


	public Login() {
            
	setBackground(new Color(255, 255, 204));	
        setBounds(550, 250, 700, 400);
		
        panel = new JPanel();
	panel.setBackground(Color.WHITE);
	setContentPane(panel);
	panel.setLayout(null);
        JLabel l65 = new JLabel("Expense Tracker");
        l65.setBounds(30, 7, 200, 24);
        l65.setFont(new Font("Arial",Font.BOLD,25));
	panel.add(l65);
	JLabel l1 = new JLabel("Username : ");
	l1.setBounds(124, 89, 95, 24);
	panel.add(l1);

	JLabel l2 = new JLabel("Password : ");
	l2.setBounds(124, 124, 95, 24);
	panel.add(l2);

	textField = new JTextField();
	textField.setBounds(210, 93, 157, 20);
	panel.add(textField);
	
	passwordField = new JPasswordField();
	passwordField.setBounds(210, 128, 157, 20);
	panel.add(passwordField);

	JLabel l3 = new JLabel("");
	l3.setBounds(377, 79, 46, 34);
	panel.add(l3);

	JLabel l4 = new JLabel("");
	l4.setBounds(377, 124, 46, 34);
	panel.add(l3);

        ImageIcon c1 = new ImageIcon(ClassLoader.getSystemResource("expense_tracker/icons/login.png"));
        Image i1 = c1.getImage().getScaledInstance(150, 150,Image.SCALE_DEFAULT);
        ImageIcon i2 = new ImageIcon(i1);
        
        
        JLabel l6 = new JLabel(i2);
        l6.setBounds(480, 70, 150, 150);
        add(l6);
        
        
	b1 = new JButton("Login");
	b1.addActionListener(this);
                
	b1.setForeground(new Color(46, 139, 87));
	b1.setBackground(new Color(176, 224, 230));
	b1.setBounds(149, 181, 113, 25);
	panel.add(b1);
        
        b2 = new JButton("Sign Up");
	b2.addActionListener(this);
                
	b2.setForeground(new Color(46, 139, 87));
	b2.setBackground(new Color(176, 224, 230));
	b2.setBounds(280, 181, 113, 25);
	panel.add(b2);
        
        b3 = new JButton("Update Password");
	b3.addActionListener(this);
                
	b3.setForeground(new Color(46, 139, 87));
	b3.setBackground(new Color(176, 224, 230));
	b3.setBounds(280, 215, 113, 25);
	panel.add(b3);
        
        b4 = new JButton("Delete Account");
	b4.addActionListener(this);
                
	b4.setForeground(new Color(46, 139, 87));
	b4.setBackground(new Color(176, 224, 230));
	b4.setBounds(149, 215, 113, 25);
	panel.add(b4);
	
	

        JPanel panel2 = new JPanel();
        panel2.setBackground(new Color(255, 255, 204));
        panel2.setBounds(24, 40, 434, 263);
        panel.add(panel2);
	}
        
        public void actionPerformed(ActionEvent ae){
            if(ae.getSource() == b1){
                Boolean status = false;
		try {
                    Conn con = new Conn();
                    String sql = "select * from user where username=? and password=?";
                    PreparedStatement st = con.c.prepareStatement(sql);

                    st.setString(1, textField.getText());
                    st.setString(2, passwordField.getText());

                    ResultSet rs = st.executeQuery();
                    if (rs.next()) {
                        this.setVisible(false);
                        new ExpensesIncomesTracker(textField.getText()).setVisible(true);
                    } else
			JOptionPane.showMessageDialog(null, "Invalid Login or Password!");
                       
		} catch (Exception e2) {
                    e2.printStackTrace();
		}
            }
            if(ae.getSource() == b2){
                this.setVisible(false);
		Signup su = new Signup();
		su.setVisible(true);
            }
            if(ae.getSource() == b3){
                this.setVisible(false);
		UpdatePass su = new UpdatePass();
		su.setVisible(true);
            }
            if(ae.getSource() == b4){
                this.setVisible(false);
                DeleteAcc su = new DeleteAcc();
		su.setVisible(true);
            }
        }
        
  	public static void main(String[] args) {
                new Login().setVisible(true);
	}

}