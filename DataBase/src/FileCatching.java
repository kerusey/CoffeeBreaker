import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.Objects;

public class FileCatching {
   Connection connection;

   public FileCatching (Connection connection){this.connection = connection; }
   File catalog = new File("C:\\Users\\khash\\IdeaProjects\\NewCoffeeBreaker\\src\\main\\java\\GovnoNahuy");
    String catalog__ = "C:\\Users\\khash\\IdeaProjects\\NewCoffeeBreaker\\src\\main\\java\\GovnoNahuy\\";
    public void GetAmountOfFiles() throws FileNotFoundException {
        if (!catalog.isDirectory()) {
            return;
        }
        for (File item : Objects.requireNonNull(catalog.listFiles())) {
            System.out.println(item.getName() + (item.isDirectory() ? " \t folder" : "\t file"));
          String  filename = item.getName();
            new JsonParser(filename, catalog__, connection).Parse();
          //  new JsonParser(filename, catalog__).readJSON();
        }
    }
}





