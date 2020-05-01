import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JsonParser {

    public String catalog;
    public String fileName;

    public JsonParser(String fileName, String catalog) {
        this.fileName = fileName;
        this.catalog = catalog;
    }

    public Data Parse() throws IOException {
        String FinalPath = catalog + fileName;
        Gson gson = new Gson();
        FileReader fileReader = new FileReader(FinalPath);
        Data data = gson.fromJson(fileReader, Data.class);
        fileReader.close();
        return data;
    }
}








