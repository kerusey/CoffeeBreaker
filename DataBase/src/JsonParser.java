import com.google.gson.Gson;
import org.javatuples.Septet;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;

public class JsonParser {

    /*
        "date": "2020-03-30",

        "milk": false,
        "shugar": 0,
        "strenght": 4,
        "time": " 22:45:15",
        "type": "espresso",
        "volume": 2

         */
    public String catalog;
    public String FileName;
    public Connection connection;

    public JsonParser(String FileName, String catalog, Connection connection) {
        this.FileName = FileName;
        this.catalog = catalog;
        this.connection = connection;
    }
    public void Parse () throws FileNotFoundException {
        Gson gson = new Gson();
        String FinalPath= catalog+FileName;
        Data data = gson.fromJson(new FileReader(FinalPath), Data.class);

    }


    class Data {
        private String date;
        private String time;
        private String type;
        private boolean milk;
        private int sugar;
        private int volume ;
        private int strength;

        public Data(String date, String time, String type, int sugar, boolean milk, int strength, int volume) {
            this.date = date;
            this.time = time;
            this.type = type;
            this.sugar = sugar;
            this.milk = milk;
            this.strength = strength;
            this.volume = volume;
        }


        public void Converter(){
            Septet<String, String, String, Boolean, Integer, Integer, Integer> pair = Septet.with(date, time, type, milk, sugar, strength, volume);
           new ValueInsert(connection, pair);
        }

    }





}


