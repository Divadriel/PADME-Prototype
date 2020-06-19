package fr.limsi.Model.Utils;

import fr.limsi.Model.Exercise;
import fr.limsi.Model.Session;
import javafx.scene.control.Spinner;
import javafx.stage.FileChooser;

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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Utils {

    public static String arrayListToString(ArrayList<?> arrayList){
        String result = "";
        if (arrayList.size()<1){
            result += "ArrayList is empty.\n";
        }
        else{
            for (Object o : arrayList) {
                result += o.toString() + "\n";
            }
        }
        return result;
    }

    public static int calculateIndex(int percentile, int sampleSize){
        return (int) Math.floor((percentile * (sampleSize + 1)) / 100);
    }

    // could be initialized in the past to have more or less unique and long IDs
    public static long calculateUniqueID(){
        return System.currentTimeMillis(); // 13 digits
    }

    public static double ceilToNInteger(double number, int N){ return (Math.floor(number / N) + 1) * N; }

    public static void commitSpinnerValueOnLostFocus(Spinner<?> spinner) {
        spinner.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue){
                spinner.increment(0);
            }
        }));
    }

    // upperTolerance and lowerTolerance are numbers between 0 and 1 (percentages)
    public static int considerCorrection(int precision, double lowerTolerance, double upperTolerance,
                                         int currSteps, int prevSteps, int steps){
        int upperLimit = steps + (int)(Math.floor(steps * upperTolerance));
        int lowerLimit = steps - (int)(Math.floor(steps * lowerTolerance));

        ArrayList<Integer> drawList = new ArrayList<>(precision);
        Random random = new Random();
        IntStream partOne;
        IntStream partTwo;

        // pseudo random array list with weights
        if(currSteps >= prevSteps){
            partOne = random.ints((long)(Math.floor(precision * 0.69)), steps, upperLimit);
            partTwo = random.ints((long)(Math.floor(precision * 0.31)), lowerLimit, steps);
        }
        else {
            partOne = random.ints((long)(Math.floor(precision * 0.44)), steps, upperLimit);
            partTwo = random.ints((long)(Math.floor(precision * 0.56)), lowerLimit, steps);
        }
        drawList.addAll(partOne.boxed().collect(Collectors.toList()));
        drawList.addAll(partTwo.boxed().collect(Collectors.toList()));

        // pseudo random choice of one element in the array list
        int index = (int)(Math.random() * drawList.size());

        return drawList.get(index);
    }

    public static Exercise findExercise(long exID, ArrayList<Exercise> exerciseArrayList){
        for(Exercise exercise : exerciseArrayList){
            if(exercise.getExerciseID() == exID){
                return exercise;
            }
        }
        return null;
    }

    public static Session findSession(long sessionID, ArrayList<Session> sessionArrayList){
        for (Session session : sessionArrayList){
            if(session.getSessionID() == sessionID){
                return session;
            }
        }
        return null;
    }

    public static int generateNextStepsObjective(ArrayList<Integer> stepsRecord, int percentile){

        int index = calculateIndex(percentile, stepsRecord.size());
        ArrayList<Integer> stepsRecordSorted = new ArrayList<>(stepsRecord);
        stepsRecordSorted.sort(Comparator.naturalOrder());

        return stepsRecordSorted.get(index - 1);
    }

    public static ArrayList<Integer> generateRandomStepsRecord(int days){
        ArrayList<Integer> result = new ArrayList<>(days);
        for (int i = 0; i < days; i++){
            result.add((int) Math.floor((Math.random() * 10000) + 1));
        }
        return result;
    }

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

    public static Exercise getRandomExercise(ArrayList<Exercise> arrayList){
        return arrayList.get(new Random().nextInt(arrayList.size()));
    }

    public static Session getRandomSession(ArrayList<Session> arrayList){
        return arrayList.get(new Random().nextInt(arrayList.size()));
    }

    public static String getUserSaveFilePath(long userID){
        // save timestamp formatter
        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC+02:00"));
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // for JSON filename
        return "D:\\Users\\"+System.getProperty("user.name")+"\\Documents\\PADMEH_data\\"+userID+"_"+now.format(formatterDate)+".json";
    }

    public static String loadInitJSONFile() throws IOException {
        return new String(Files.readAllBytes(Paths.get(Strings.PATH_TO_INIT_JSON)));
    }

    public static String nthPercentileAlgorithmDisplay(int wantedPercentile, ArrayList<Integer> stepsRecord,
                                                       int dayCount, boolean correction, boolean verbose){
        String result = "";

        int nextObj = generateNextStepsObjective(stepsRecord, wantedPercentile); // returns the next objective (steps nb)
        nextObj = (int) ceilToNInteger(nextObj, 100); // correction to closest upper N = 100 steps
        ArrayList<Integer> temp = new ArrayList<>(stepsRecord);// temp list to show ordered elements
        temp.sort(Comparator.naturalOrder());

        // removes first index and add next objective depending on whether correction is true or false
        stepsRecord.remove(0);

        int realNextObj;
        if(correction){
            realNextObj = considerCorrection(
                    10000,
                    0.2, // 0.2 means lower limit can be 20% lower than expected next objective
                    0.2, // 0.2 means upper limit can be 20% higher than expected next objective
                    stepsRecord.get(stepsRecord.size() - 1), // current day steps
                    stepsRecord.get(stepsRecord.size() - 2), // previous day steps
                    nextObj // expected next objective
            );
        }
        else{
            realNextObj = nextObj;
        }

        stepsRecord.add(realNextObj);

        // if verbose is true, we add more info in result String

        result += "Wanted percentile: " + wantedPercentile + " ; moving days: " + stepsRecord.size() + "\n";
        if(verbose){
            result += "Steps record sorted:\n";
            result += arrayListToString(temp) + "\n";
        }
        result += "Day counter: " + dayCount + "\n";
        result += "Next objective: " + nextObj + "\n";
        if(correction){
            result += "Correction is active.\n";
        }
        else{
            result += "Correction is not active.\n";
        }
        result += "Simulator decided to walk " + stepsRecord.get(stepsRecord.size() - 1) + " steps tomorrow.\n";
        if(verbose){
            result += "Updated steps record:\n";
            result += arrayListToString(stepsRecord);
        }
        result += "\n";
        return result;
    }

}
