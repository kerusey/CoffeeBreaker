import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Insert {
    Connection connection;
    int id, milk, water, coffee, sugar;
    String mdate, mtime;

    public void ValueInsert(Connection connection, int id, int milk, int water, int coffee, int sugar, String mdate, String mtime) throws SQLException {
        this.connection = connection;
        this.id = id;
        this.milk = milk;
        this.water = water;
        this.coffee = coffee;
        this.sugar = sugar;
        this.mdate = mdate;
        this.mtime= mtime;
        Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO `CoffeeBreakerDataTable`(id, milk, coffee, sugar, water,  mtime, mdate) VALUE ('" + id + "','" + milk + "','" + coffee + "','" + sugar + "','" + water + "','" + mtime + "', '" + mdate + "')");
    }
}
