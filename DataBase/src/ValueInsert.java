import java.sql.Connection;
import java.sql.*;


public class ValueInsert {
    Connection connection;
    Statement statement;

    public ValueInsert(Connection connection) { this.connection = connection; }
    public void insert () throws SQLException {
      statement = connection.createStatement();
      String sql = "INSERT INTO num " + " VALUES (100, 100, 100, 100, '00:00', '12.09.2003' )"; // values will be changed to variables when the deal with .json starts
      statement.executeUpdate(sql);
        System.out.println("Inserted records into the table...");
        new JavaBDPrint(connection).print();

    }


}
