import java.sql.Connection;

public class RandomInsert {
    public static void main(String args[]) throws Exception {
        Connection connection = new DbConnection().getCon();
        int days = 1;
        while (days != 31) {
            for (int i = 0; i < 100; i++) {
                setValues(days, connection);
            }
            days++;
        }
    }


    public static void setValues(int days, Connection connection) throws Exception {

        int coffee = 8;
        String mtime;
        int hours = (int) ((Math.random() * (24 - 0)) + 0);
        int minutes = (int) ((Math.random() * (60 - 0)) + 0);
        String hrs = Integer.toString(hours);
        if (hours < 10) {
            hrs = "0" + hrs;
        }
        mtime = hrs;

        String mins = Integer.toString(minutes);
        if (minutes < 10) {
            mins = "0" + mins;
        }
        mtime = mtime + ":" + mins;
        int sugar = (int) ((Math.random() * 5 - 0) + 0);
        int water = (int) ((Math.random() * 4 - 2) + 2);
        while (water == 0) {
            water = (int) ((Math.random() * 4 - 2) + 2);
        }
        water *= 100;
        int milk = (int) ((Math.random() * 3 - 0) + 0) * 100;
        int id = (int) ((Math.random() * 9 - 1) + 1);
        while (id == 0) {
            id = (int) ((Math.random() * 9 - 1) + 1);
        }
        String ds = Integer.toString(days);
        if (days < 10) {
            ds = "0" + ds;
        }
        String mdate = ds + ".01.2020";
        new Insert().ValueInsert(connection, id, milk, water, coffee, sugar, mdate, mtime);
    }


}
