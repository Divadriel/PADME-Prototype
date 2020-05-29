package fr.limsi.Model;

import fr.limsi.Model.Utils.Utils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Programme {

    // user related
    private ArrayList<Exercise> exerciseArrayList;
    private ArrayList<Session> sessionArrayList;
    private UserModel user;
    private AdaptationRules adaptationRules;

    public Programme() throws IOException{

        // config components
        user = new UserModel();

        // load init data from init.json -- User John Doe + sample exercises and session
        initProgramme();
        adaptationRules = new AdaptationRules(user, sessionArrayList.get(0));
    }

    public void initProgramme() throws IOException {
        // load init.json
        String initJSON = Utils.loadInitJSONFile();
        // parse UserModel
        user.parseUserModelFromString(initJSON);
        // parse Exercise ArrayList
        JSONObject object = new JSONObject(initJSON);
        JSONArray jsonExerciseArray = object.getJSONArray(Exercise.class.getSimpleName());
        exerciseArrayList = new ArrayList<>();
        setExerciseArrayListFromJSONArray(jsonExerciseArray, exerciseArrayList);

        // parse Session ArrayList
        JSONArray jsonSessionArray = object.getJSONArray(Session.class.getSimpleName());
        sessionArrayList = new ArrayList<>();
        setSessionArrayListFromJSONArray(jsonSessionArray, sessionArrayList);
    }
    private void setExerciseArrayListFromJSONArray(JSONArray jsonExerciseArray, ArrayList<Exercise> arrayList){
        for (int i = 0; i < jsonExerciseArray.length(); i++){
            JSONObject jsonObject = jsonExerciseArray.getJSONObject(i);
            Exercise exercise = new Exercise(
                    jsonObject.getString("name"),
                    jsonObject.getDouble("duration"),
                    jsonObject.getDouble("distance"),
                    jsonObject.getDouble("completed"),
                    jsonObject.getLong("exerciseID")
            );
            arrayList.add(exercise);
        }
    }

    private void setSessionArrayListFromJSONArray(JSONArray jsonSessionArray, ArrayList<Session> arrayList){
        for (int j = 0; j < jsonSessionArray.length(); j++){
            JSONObject jsonObject = jsonSessionArray.getJSONObject(j);
            JSONArray temp = jsonObject.getJSONArray("ExerciseList");
            ArrayList<Exercise> exercises = new ArrayList<>();
            setExerciseArrayListFromJSONArray(temp, exercises);
            Session session = new Session(
                    exercises,
                    user.getUserID(),
                    jsonObject.getInt("userFeedback")
            );
            arrayList.add(session);
        }
    }

    public ArrayList<Exercise> getExerciseArrayList() {
        return exerciseArrayList;
    }

    public void updateExerciseArrayList(){
        JSONArray jsonArray = user.getJsonMETAObject().getJSONArray(Exercise.class.getSimpleName());
        setExerciseArrayListFromJSONArray(jsonArray, exerciseArrayList);
    }

    public void updateSessionArrayList(){
        JSONArray jsonArray = user.getJsonMETAObject().getJSONArray(Session.class.getSimpleName());
        setSessionArrayListFromJSONArray(jsonArray, sessionArrayList);
    }

    public void setExerciseArrayList(ArrayList<Exercise> exerciseArrayList) {
        this.exerciseArrayList = exerciseArrayList;
    }

    public void resetExerciseArrayList(){
        this.exerciseArrayList = null;
        exerciseArrayList = new ArrayList<>();
    }

    public void resetSessionArrayList(){
        this.sessionArrayList = null;
        sessionArrayList = new ArrayList<>();
    }

    public void saveExerciseArrayListToJSON(long userID) throws IOException {

        // get file
        String path = Utils.getUserSaveFilePath(userID);
        // update Exercise ArrayList from JSON
        JSONObject object = new JSONObject(new String(Files.readAllBytes(Paths.get(path)))); // whole object
        object.remove(Exercise.class.getSimpleName()); // remove array corresponding to "Exercise" key
        JSONArray jsonArray = new JSONArray();
        for (Exercise exercise : exerciseArrayList) {
            jsonArray.put(exercise.saveExerciseToJSONObject());
        }
        object.put(Exercise.class.getSimpleName(), jsonArray); // put "Exercise" key with JSONArray as value
        // update user jsonMETAObject with ExerciseList
        user.getJsonMETAObject().remove(Exercise.class.getSimpleName());
        user.getJsonMETAObject().put(Exercise.class.getSimpleName(), jsonArray);

        // save object again to file -- rewriting it completely (append false)
        FileWriter file = null;
        try {
            file = new FileWriter(path, false);
            file.write(object.toString(2));
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try{
                assert file != null;
                file.flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void initNewJsonFile(long userID){
        FileWriter file = null;
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonObject.put(UserModel.class.getSimpleName(), jsonArray);
        jsonObject.put(Exercise.class.getSimpleName(), jsonArray);
        jsonObject.put(Session.class.getSimpleName(), jsonArray);
        try {
            file = new FileWriter(Utils.getUserSaveFilePath(userID), false);
            file.write(jsonObject.toString(2));
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try{
                assert file != null;
                file.flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Session> getSessionArrayList() {
        return sessionArrayList;
    }

    public void setSessionArrayList(ArrayList<Session> sessionArrayList) {
        this.sessionArrayList = sessionArrayList;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public AdaptationRules getAdaptationRules() {
        return adaptationRules;
    }

    public void setAdaptationRules(AdaptationRules adaptationRules) {
        this.adaptationRules = adaptationRules;
    }
}
