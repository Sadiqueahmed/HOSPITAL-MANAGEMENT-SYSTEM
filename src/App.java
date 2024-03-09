import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class App {
    public static void main(String[] args) {
        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "root");
            Statement stmt=con.createStatement();
            ResultSet rs =stmt.executeQuery ("SELECT * FROM patients;") ; // replace <query> with the actual query
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
            con.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
}