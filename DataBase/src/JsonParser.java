import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class JsonParser {

    public String catalog;
    public String fileName;

    public JsonParser(String fileName, String catalog) {
        this.fileName = fileName;
        this.catalog = catalog;
    }

    public Data Parse() throws FileNotFoundException {
        return new Gson().fromJson(new FileReader(catalog + fileName), Data.class);
    }
}

// water ... milk .... sugar ... coffee ... time... date






