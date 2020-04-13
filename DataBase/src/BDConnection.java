import java.sql.Connection;
import java.sql.DriverManager;
import java.io.*;
import java.util.Properties;



public class BDConnection {

    public BDConnection() {
        try {
            String url = "jdbc:mysql://178.206.224.58:3306/coffeeBreaker?serverTimezone=Europe/Moscow";
            String username = "coffeeBreaker";
            String password = "coffeeBreaker";
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
