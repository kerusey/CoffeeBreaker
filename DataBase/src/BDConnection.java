import java.sql.Connection;
import java.sql.DriverManager;



public class BDConnection {

    public BDConnection() {
        try{
            String url = "your database URL"; // watchout it might be a timezone error (use ?Timezone: )
            String username = "your username";
            String password = "your username";
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection connection = DriverManager.getConnection(url, username, password)){

                System.out.println("Connection to coffeeBreaker BD has been completed!");
               try {
                   new JavaBDPrint(connection).print();// printing Data Base values
                   new ValueInsert(connection).insert(); // inserts values 
               }catch (Exception ignore) {
                   System.out.println("proeb");// was used to catch printing troubles. 

               }
            }
        }
        catch(Exception ex){
            System.out.println("Connection failed..."); // could occur if you didn t import Driver as a library. 

            System.out.println(ex);
        }

    }


}
