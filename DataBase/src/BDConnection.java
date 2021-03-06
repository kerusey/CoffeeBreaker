import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class BDConnection {


    public Connection getCon() throws Exception {

        FileInputStream fis;
        Properties property = new Properties();
        fis = new FileInputStream("src/main/resources/application.properties");
        property.load(fis);
        String url = property.getProperty("db.host");
        String username = property.getProperty("db.login");
        String password = property.getProperty("db.password");
        Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
        return DriverManager.getConnection(url, username, password);

    }
}

