package fr.limsi.Model.Utils;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import javafx.stage.FileChooser;
import org.json.JSONArray;
import sun.plugin.javascript.navig.Array;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.stream.IntStream;

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

    public static int calculateIndex(int percentile, int sampleSize){
        return (int) Math.floor((percentile * (sampleSize + 1)) / 100);
    }

    public static ArrayList<Integer> generateRandomStepsRecord(int days){
        ArrayList<Integer> result = new ArrayList<Integer>(days);
        for (int i = 0; i < days; i++){
            result.add((int) Math.floor((Math.random() * 10000) + 1));
        }
        return result;
    }

    public static int generateNextStepsObjective(ArrayList<Integer> stepsRecord, int percentile){

        int index = calculateIndex(percentile, stepsRecord.size());
        ArrayList<Integer> stepsRecordSorted = new ArrayList<Integer>();
        for(int val : stepsRecord){
            stepsRecordSorted.add(val);
        }
        stepsRecordSorted.sort(Comparator.naturalOrder());

        return stepsRecordSorted.get(index - 1);
    }

    public static String nthPercentileAlgorithmDisplay(int wantedPercentile, ArrayList<Integer> stepsRecord, int dayCount, boolean verbose){
        String result = "";

        int nextObj = generateNextStepsObjective(stepsRecord, wantedPercentile); // returns the next objective (steps nb)
        ArrayList<Integer> temp = new ArrayList<>(stepsRecord);// temp list to show ordered elements
        temp.sort(Comparator.naturalOrder());
        // removes first index and add next objective
        stepsRecord.remove(0);
        Random random = new Random();
        IntStream genInts = random.ints(1,(int)(Math.floor(0.9*nextObj)), (int)(Math.floor(1.1*nextObj)));
        stepsRecord.add(genInts.sum());

        // if verbose is true, we add more info in result String

        result += "Wanted percentile: " + wantedPercentile + " ; moving days: " + stepsRecord.size() + "\n";
        if(verbose){

            result += "Steps record sorted:\n";
            result += arrayListToString(temp) + "\n";
        }
        result += "Day counter: " + dayCount + "\n";
        result += "Next objective: " + nextObj + "\n";
        result += "Simulator decided to walk " + stepsRecord.get(stepsRecord.size() -1) + " steps tomorrow.\n";
        if(verbose){
            result += "Updated steps record:\n";
            result += arrayListToString(stepsRecord);
        }
        result += "\n";
        return result;
    }
}
