package expense_tracker;
import java.sql.*;  
public class Conn{
    Connection c;
    Statement s;
    public Conn(){  
        try{  
            Class.forName("com.mysql.cj.jdbc.Driver");  
            c =DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/expense_tracker","root","Hamzalfs@7086"); 
            s =c.createStatement();  
        }catch(Exception e){ 
            System.out.println(e);
        }  
    }  
}  
