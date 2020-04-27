import org.javatuples.Septet;
import java.sql.Connection;
import java.sql.*;

public class ValueInsert {

    Object septet;
    Connection connection;
    Statement statement;

    public ValueInsert(Septet<Integer, Integer, Integer, Integer, Integer, String, String> septet, Connection connection) {
        this.septet = septet;
        this.connection = connection;
    }

    public void insert () throws SQLException {

        statement = connection.createStatement();
        String sql = "INSERT INTO CoffeeBreakerDataTable " + " VALUES (septet)";
        
        statement.executeUpdate(sql);
        System.out.println("Inserted records into the table...");
     // new JavaBDPrint(connection).print();

    }

}