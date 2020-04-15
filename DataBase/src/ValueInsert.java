import org.javatuples.Septet;

import java.sql.Connection;
        import java.sql.*;


public class ValueInsert {

    Object pair;
    Connection connection;
    Statement statement;

    public ValueInsert(Connection connection, Septet<String, String, String, Boolean, Integer, Integer, Integer> pair) { this.connection = connection;  this.pair= pair;}


    public void insert () throws SQLException {
        statement = connection.createStatement();
        String sql = "INSERT INTO num " + " VALUES (pair)"; // нужно  поменять расположения переменных в кортеже + законверить json переменные в нужные написав дляф этого метод
        statement.executeUpdate(sql);
        System.out.println("Inserted records into the table...");
        new JavaBDPrint(connection).print();

    }


}
