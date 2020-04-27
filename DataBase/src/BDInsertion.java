import org.javatuples.Septet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;


public class BDInsertion {


    Connection connection;

    public BDInsertion(Connection connection) throws NoSuchMethodException, IOException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        this.connection = connection;
    }

    public void CreateSeptet(int milk, int coffee, int sugar, int water, int id, String time, String date) throws SQLException, NoSuchMethodException, IOException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
    Septet<Integer, Integer, Integer, Integer, Integer, String, String> septet = Septet.with(milk, coffee, sugar, water, id, time, date);
    new ValueInsert(septet, connection).insert();
}

public void Convert(Data data) throws SQLException, NoSuchMethodException, IOException, InstantiationException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
    int coffee = 8;
    int sugar = 6 * data.getSugar();
    String date = data.getDate();
    String time = data.getTime();
    int id = data.getId();
    int milk_ = 0;
    if (data.getMilk())
        milk_ += 100;
    switch (data.getType()) {
        case "cappuccino":
            int cappuccinoVolume = data.getVolume();
            int cappuccinoWater = (int) ((int) cappuccinoVolume * 0.3);
            milk_ += (int) ((int) cappuccinoVolume * 0.7);
            CreateSeptet(milk_, coffee, sugar, cappuccinoWater, id, time, date);
            break;
        case "espresso":
            CreateSeptet(milk_, coffee, sugar, data.getVolume(), id, time, date);
            break;
        case "milkCoffee":
            int milkCoffeeVolume = data.getVolume();
            int milkCoffeeWater = (int) ((int) milkCoffeeVolume * 0.5);
            milk_ += milkCoffeeWater;
            CreateSeptet(milk_, coffee, sugar, milkCoffeeWater, id, time, date);
            break;
        case "latteMacchiato":
            int latteMacchiatoVolume = data.getVolume();
            int latteMacchiatoWater = (int) ((int) latteMacchiatoVolume * 0.3);
            milk_ += (int) ((int) latteMacchiatoVolume * 0.7);
            CreateSeptet(milk_, coffee, sugar, latteMacchiatoWater, id, time, date);

        case "cream cafe":
            CreateSeptet(milk_, coffee, sugar, data.getVolume(), id, time, date);
            break;
    }

}

    public static void main(String args[]) throws IOException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        new FileCatching().GetAmountOfFiles();

    }
}