package fr.limsi.Model.Utils;

import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

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
        return System.currentTimeMillis(); // 13 digits, in 2020
    }

    public static String arrayListToString(ArrayList<?> arrayList){
        String result = "";
        for(int i = 0; i < arrayList.size(); i++){
            result += arrayList.get(i).toString() + "\n";
        }
        return result;
    }
}
