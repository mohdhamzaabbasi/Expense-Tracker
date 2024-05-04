 package expense_tracker;
import expense_tracker.ExpensesIncomesTracker;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class UpdatePass extends JFrame implements ActionListener{

	private JPanel panel;
	private JTextField textField;
	private JPasswordField oldpasswordField;
        private JPasswordField newpasswordField;
        private JButton b1,b2,b3,b4;


	public UpdatePass() {
            
	setBackground(new Color(255, 255, 204));	
        setBounds(550, 250, 700, 400);
		
        panel = new JPanel();
	panel.setBackground(Color.WHITE);
	setContentPane(panel);
	panel.setLayout(null);
        JLabel l65 = new JLabel("Update Password");
        l65.setBounds(30, 7, 220, 35);
        l65.setFont(new Font("Arial",Font.BOLD,25));
	panel.add(l65);
	JLabel l1 = new JLabel("Username : ");
	l1.setBounds(124, 89, 95, 24);
	panel.add(l1);

	JLabel l2 = new JLabel("Old Password : ");
	l2.setBounds(124, 124, 95, 24);
	panel.add(l2);
        
        JLabel l45 = new JLabel("New Password: ");
	l45.setBounds(124, 159, 95, 24);
	panel.add(l45);
        
        
	textField = new JTextField();
	textField.setBounds(210, 93, 157, 20);
	panel.add(textField);
	
	oldpasswordField = new JPasswordField();
	oldpasswordField.setBounds(210, 128, 157, 20);
	panel.add(oldpasswordField);
        
        newpasswordField = new JPasswordField();
	newpasswordField.setBounds(210, 163, 157, 20);
	panel.add(newpasswordField);

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
        
        
	b1 = new JButton("Change!");
	b1.addActionListener(this);
                
	b1.setForeground(new Color(46, 139, 87));
	b1.setBackground(new Color(176, 224, 230));
	b1.setBounds(149, 200, 113, 25);
	panel.add(b1);
        
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
                    st.setString(2, oldpasswordField.getText());

                    ResultSet rs = st.executeQuery();
                    if (rs.next()) {
                        try {
                            Conn cons = new Conn();
                            String sqls = "update user set password=? where username=?;";
                            PreparedStatement rst = cons.c.prepareStatement(sqls);
                            rst.setString(1, newpasswordField.getText());
                        rst.setString(2, textField.getText());
                    
                            rst.executeUpdate();
          
                       
        		} catch (Exception e2) {
                        e2.printStackTrace();
                        }
                        this.setVisible(false);
                        new Login().setVisible(true);
                    } else
			JOptionPane.showMessageDialog(null, "Invalid Old Password!");
                       
		} catch (Exception e2) {
                    e2.printStackTrace();
		}
            }
            if(ae.getSource() == b2){
                this.setVisible(false);
		Signup su = new Signup();
		su.setVisible(true);
            }
        }

}