import java.sql.Connection;
import java.sql.DriverManager;

public class BDConnection {

    public BDConnection() {
        try{
            String url = "your URL";
            String username = "your  username";
            String password = "your password";
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection c = DriverManager.getConnection(url, username, password)){
            System.out.println("Connection to coffeeBreaker BD has been completed!");
            }
        }
        catch(Exception ex) {
            System.out.println("Connection failed...");
            System.out.println(ex);
        }
    }

}
