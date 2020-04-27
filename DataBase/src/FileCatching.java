import java.io.File;
import java.lang.Exception;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class FileCatching {

    String catalog__ = "C:\\Users\\khash\\IdeaProjects\\NewCoffeeBreaker\\src\\main\\java\\GovnoNahuy\\";
    File catalog = new File(catalog__);

    public void GetAmountOfFiles() throws Exception {
    
        Connection connection = new BDConnection().getCon();

        if (!catalog.isDirectory()) 
            System.out.println("isDirectory error");
            
        for (File item : Objects.requireNonNull(catalog.listFiles())) {
            System.out.println(item.getName() + (item.isDirectory() ? " \t folder" : "\t file"));
            String  filename = item.getName();
            Data data = new  JsonParser(filename, catalog__).Parse();
            new BDInsertion(connection).Convert(data);
        }

    }
}
