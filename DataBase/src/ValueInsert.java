
import org.javatuples.Sextet;
import java.sql.Connection;
import java.sql.*;


public class ValueInsert {

     Object sextet;
    Connection connection;

    public ValueInsert(Sextet<Integer, Integer, Integer, Integer, String, String> sextet, Connection connection) throws SQLException {
        this.sextet = sextet;
        this.connection = connection;
        int milk = sextet.getValue0();
        int sugar = sextet.getValue1();
        int water = sextet.getValue2();
        int id = sextet.getValue3();
        String mtime = sextet.getValue4();
        String mdate = sextet.getValue5();
        mdate = mdate + " " + mtime;
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO `CoffeeBreakerDataTable`(coffeeID, water,  sugar, milk, date ) VALUE ('" + id + "','" + water + "','" + sugar + "', '"+ milk +"' , '" + mdate + "')");
        System.out.println("Inserted records into the table...");

    }
}

