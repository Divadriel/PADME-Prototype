package fr.limsi.Model.Utils;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import javafx.stage.FileChooser;
import org.json.JSONArray;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

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

    public static double setDurationToClosestUpperNMinutes(double duration, int N){
        if (duration % N == 0){
            return duration;
        }
        else{
            return (Math.floor(duration / N) + 1) * N;
        }
    }

    public static long calculateUniqueID(){
        return System.currentTimeMillis(); // 13 digits
    }

    public static String arrayListToString(ArrayList<?> arrayList){
        String result = "";
        for(int i = 0; i < arrayList.size(); i++){
            result += arrayList.get(i).toString() + "\n";
        }
        return result;
    }

    public static String getUserSaveFilePath(String userFirstName){
        // save timestamp formatter
        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC+02:00"));
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // for JSON filename
        return "D:\\Users\\"+System.getProperty("user.name")+"\\Documents\\PADMEH_data\\"+userFirstName+"_"+now.format(formatterDate)+".json";
    }

    public static int calculatePercentile(int percentile, int sampleSize){
        return (int) Math.floor((percentile * (sampleSize + 1)) / 100);
    }

    public static ArrayList<Integer> generateRandomStepsRecord(int days){
        ArrayList<Integer> result = new ArrayList<Integer>(days);
        for (int i = 0; i < days; i++){
            result.add((int) Math.floor((Math.random() * 10000) + 1));
        }
        return result;
    }

    public static int generateNextStepsObjective(ArrayList<Integer> stepsRecord){

        int percentile = calculatePercentile(60, stepsRecord.size());
        ArrayList<Integer> stepsRecordSorted = new ArrayList<Integer>();
        for(int val : stepsRecord){
            stepsRecordSorted.add(val);
        }
        stepsRecordSorted.sort(Comparator.naturalOrder());

        return stepsRecordSorted.get(percentile - 1);
    }

    public static ArrayList<Integer> updateStepsRecord(ArrayList<Integer> stepsRecord, int nextObj){
        stepsRecord.remove(0);
        stepsRecord.add(nextObj);
        return stepsRecord;
    }
}
