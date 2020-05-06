import org.javatuples.Sextet;
import java.lang.Exception;
import java.sql.Connection;


public class BDInsertion {

    Connection connection;

    public static void main(String args[]) throws Exception {
        new FileCatching().GetAmountOfFiles();
    }

    public BDInsertion(Connection connection) throws Exception {
        this.connection = connection;
    }
//    sextet <A, B, C, D, E>    milk sugar water id mtime mdate
    public void CreateSextet(int milk,  int sugar, int water, int id, String time, String date) throws Exception {

        Sextet<Integer, Integer, Integer,  Integer, String, String> sextet = Sextet.with(milk,  sugar, water, id, time, date);
        new ValueInsert(sextet, connection);
    }

    public void Convert(Data data) throws Exception {

               int milk = 0,
                sugar = data.getSugar() * 6;
        int id = data.getId();

        String date = data.getDate(),
                time = data.getTime();

        if (data.getMilk())
            milk += 100;

        switch (data.getType()) {
            case "cappuccino":
                int cappuccinoVolume = data.getVolume();
                int cappuccinoWater = (int) ((int) cappuccinoVolume * 0.3);
                milk += (int) ((int) cappuccinoVolume * 0.7);
                CreateSextet(milk,  sugar, cappuccinoWater, id, time, date);
                break;

            case "espresso": CreateSextet( milk,  sugar, data.getVolume(), id, time, date);
                break;

            case "milkCoffee":
                int milkCoffeeVolume = data.getVolume();
                int milkCoffeeWater = (int) ((int) milkCoffeeVolume * 0.5);
                milk += milkCoffeeWater;
                CreateSextet(milk,  sugar, milkCoffeeWater, id, time, date);
                break;

            case "latteMacchiato":
                int latteMacchiatoVolume = data.getVolume();
                int latteMacchiatoWater = (int) ((int) latteMacchiatoVolume * 0.3);
                milk += (int) ((int) latteMacchiatoVolume * 0.7);
                CreateSextet(milk,  sugar, latteMacchiatoWater, id, time, date);

            case "cream cafe":
                CreateSextet(milk, sugar, data.getVolume(), id, time, date);
                break;
        }

    }

}