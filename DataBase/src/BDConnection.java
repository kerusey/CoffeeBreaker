import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import java.util.Properties;




public class BDConnection {

    public BDConnection() {
        try {
            FileInputStream fis;
            Properties property = new Properties();
            fis = new FileInputStream("src/main/resources/application.properties");
            property.load(fis);
            String url = property.getProperty("db.host");;
            String username = property.getProperty("db.login");;
            String password = property.getProperty("db.password");
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)) {

                System.out.println("Connection to coffeeBreaker BD has been completed!");
                try {
                   new FileCatching(connection).GetAmountOfFiles();

                } catch (Exception ignore) {
                    System.out.println("error");

                }
            }
        } catch (Exception ex) {
            System.out.println("Connection failed...");

            System.out.println(ex);
        }

    }


}
