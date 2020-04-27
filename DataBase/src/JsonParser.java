import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;


public class JsonParser {
    public String catalog;
    public String FileName;

    public JsonParser(String FileName, String catalog) {
        this.FileName = FileName;
        this.catalog = catalog;
    }

    public Data Parse() throws FileNotFoundException {
        Gson gson = new Gson();
        String FinalPath = catalog + FileName;
        Data data = gson.fromJson(new FileReader(FinalPath), Data.class);
        return data;
    }
}




// water ... milk .... sugar ... coffee ... time... date







