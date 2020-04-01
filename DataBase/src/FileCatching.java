import java.io.File;


public class FileCatching {
    File catalog = new File("full path of json's catalog");

    public void GetAmountOfFiles() {
        if (catalog.isDirectory()) {

            for (File item : catalog.listFiles()) {

                if (item.isDirectory()) {

                    System.out.println(item.getName() + "  \t folder");
                } else {

                    System.out.println(item.getName() + "\t file");
                }
            }


        }
    }
}



