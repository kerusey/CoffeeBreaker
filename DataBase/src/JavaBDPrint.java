import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JavaBDPrint {

    Connection connection;
    String sql;
    ResultSet rs;
    Statement statement;

    public JavaBDPrint(Connection connection) {
        this.connection = connection;
    }

    public void print() throws SQLException {

        statement = connection.createStatement();

        sql = "SELECT water, milk, sugar, coffee, mtime, mdate FROM CoffeeBreakerDataTable";
        rs = statement.executeQuery(sql);

        while (rs.next()){
            // variables from dataBase
            int water = rs.getInt("water"),
                milk = rs.getInt("milk"),
                sugar = rs.getInt("sugar"),
                coffee = rs.getInt("coffee");

            String time = rs.getString("mtime"),
                   date = rs.getString("mdate");

            // printf itself
            System.out.println("water value " + water + "ml  ");
            System.out.println("milk value " + milk + "ml  ");
            System.out.println("sugar value " + sugar + "mg  ");
            System.out.println("coffee value " + coffee + "mg");
            System.out.println("DATE: " + date + "  To be certain at: " +  time);
            System.out.println("\n\n");
        }
        
        rs.close();
    }
}
