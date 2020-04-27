import org.javatuples.Septet;
import java.lang.Exception;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

public class BDInsertion {

    Connection connection;

    public static void main(String args[]) throws Exception {
        new FileCatching().GetAmountOfFiles();
    }

    public BDInsertion(Connection connection) throws Exception {
        this.connection = connection;
    }

    public void CreateSeptet(int milk, int coffee, int sugar, int water, int id, String time, String date) throws Exception {
        
        Septet<Integer, Integer, Integer, Integer, Integer, String, String> septet = Septet.with(milk, coffee, sugar, water, id, time, date);
        new ValueInsert(septet, connection).insert();
    }

    public void Convert(Data data) throws Exception {
        
        int coffee = 8,
            milk = 0,
            sugar = data.getSugar() * 6;,
            id = data.getId();

        String date = data.getDate(),
               time = data.getTime();
        
        if (data.getMilk())
            milk += 100;

        switch (data.getType()) {
            case "cappuccino":
                int cappuccinoVolume = data.getVolume();
                int cappuccinoWater = (int) ((int) cappuccinoVolume * 0.3);
                milk += (int) ((int) cappuccinoVolume * 0.7);
                CreateSeptet(milk, coffee, sugar, cappuccinoWater, id, time, date);
                break;

            case "espresso":
                CreateSeptet(milk, coffee, sugar, data.getVolume(), id, time, date);
                break;
            
            case "milkCoffee":
                int milkCoffeeVolume = data.getVolume();
                int milkCoffeeWater = (int) ((int) milkCoffeeVolume * 0.5);
                milk += milkCoffeeWater;
                CreateSeptet(milk, coffee, sugar, milkCoffeeWater, id, time, date);
                break;
            
            case "latteMacchiato":
                int latteMacchiatoVolume = data.getVolume();
                int latteMacchiatoWater = (int) ((int) latteMacchiatoVolume * 0.3);
                milk += (int) ((int) latteMacchiatoVolume * 0.7);
                CreateSeptet(milk, coffee, sugar, latteMacchiatoWater, id, time, date);

            case "cream cafe":
                CreateSeptet(milk, coffee, sugar, data.getVolume(), id, time, date);
                break;
        }

    }

}