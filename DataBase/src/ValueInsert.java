import org.javatuples.Septet;
import java.sql.Connection;
import java.sql.*;


public class ValueInsert {

    Object septet;
    Connection connection;

    public ValueInsert(Septet<Integer, Integer, Integer, Integer, Integer, String, String> septet, Connection connection) throws SQLException {
        this.septet = septet;
        this.connection = connection;
        int milk = septet.getValue0();
        int coffee = septet.getValue1();
        int sugar = septet.getValue2();
        int water = septet.getValue3();
        int id = septet.getValue4();
        String mtime = septet.getValue5();
        String mdate = septet.getValue6();
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO `CoffeeBreakerDataTable`(id, milk, coffee, sugar, water,  mtime, mdate) VALUE ('" + id + "','" + milk + "','" + coffee + "','" + sugar + "','" + water + "','" + mtime + "', '" + mdate + "')");
        System.out.println("Inserted records into the table...");

    }
}

