package fr.limsi.Model.Utils;

import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils {

    public static String getJSONContentFromFile() throws IOException {
        // get file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose JSON file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
        File file = fileChooser.showOpenDialog(null);
        String path = file.getAbsolutePath();
        // extract content to String and return it
        return new String(Files.readAllBytes(Paths.get(path)));
    }

    public static String loadInitJSONFile() throws IOException {
        //FileReader file = new FileReader(Strings.PATH_TO_INIT_JSON);
        return new String(Files.readAllBytes(Paths.get(Strings.PATH_TO_INIT_JSON)));
    }

    public static double setDurationToClosestUpperFiveMinutes(double duration){
        if(duration % 5 == 0){
            return duration;
        }
        else{
            return (Math.floor(duration / 5) + 1) * 5;
        }
    }

    public static long calculateUniqueID(){
        //return (long) (10000000000.0 * ((System.currentTimeMillis() / 10000000000.0) - (Math.floor(System.currentTimeMillis() / 10000000000.0)))); // 10 digits
        return System.currentTimeMillis(); // 13 digits
    }
}
