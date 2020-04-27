import java.io.FileInputStream;
import java.lang.Exception;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class BDConnection {

    Connection connection;

    public Connection getCon() throws Exception {
        FileInputStream fis;
        Properties property = new Properties();
        fis = new FileInputStream("src/main/resources/application.properties");
        property.load(fis);
        String url = property.getProperty("db.host");
        String username = property.getProperty("db.login");
        String password = property.getProperty("db.password");
        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        try (Connection connection1 = DriverManager.getConnection(url, username, password)) {
            this.connection = connection1;
            System.out.println("Connection to coffeeBreaker BD has been completed!");

        } catch (Exception ex) {
            System.out.println("Connection failed...");
            System.out.println(ex);
        }
        return connection;

    }
}
