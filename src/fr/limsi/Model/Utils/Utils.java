package fr.limsi.Model.Utils;

import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils {

    public static String getJSONContentFromFile() throws IOException {
        // get file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose JSON file");
        //fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
        File file = fileChooser.showOpenDialog(null);
        String path = file.getAbsolutePath();
        // extract content to String and return it
        return new String(Files.readAllBytes(Paths.get(path)));
    }
}
