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
        exerciseArrayList = new ArrayList<Exercise>();
        setExerciseArrayListFromJSONArray(jsonExerciseArray, exerciseArrayList);

        // parse Session ArrayList
        JSONArray jsonSessionArray = object.getJSONArray(Session.class.getSimpleName());
        sessionArrayList = new ArrayList<Session>();
        for (int j = 0; j < jsonSessionArray.length(); j++){
            JSONObject jsonObject = jsonSessionArray.getJSONObject(j);
            JSONArray temp = jsonObject.getJSONArray("ExerciseList");
            ArrayList<Exercise> exercises = new ArrayList<Exercise>();
            setExerciseArrayListFromJSONArray(temp, exercises);
            Session session = new Session(
                    exercises,
                    user.getUserID(),
                    jsonObject.getInt("userFeedback")
            );
            sessionArrayList.add(session);
        }
    }
    private void setExerciseArrayListFromJSONArray(JSONArray jsonExerciseArray, ArrayList<Exercise> arrayList){
        for (int i = 0; i < jsonExerciseArray.length(); i++){
            JSONObject jsonObject = jsonExerciseArray.getJSONObject(i);
            Exercise exercise = new Exercise(
                    jsonObject.getString("name"),
                    jsonObject.getDouble("duration"),
                    jsonObject.getDouble("distance")
            );
            arrayList.add(exercise);
        }
    }

    public ArrayList<Exercise> getExerciseArrayList() {
        return exerciseArrayList;
    }

    public void setExerciseArrayList(ArrayList<Exercise> exerciseArrayList) {
        this.exerciseArrayList = exerciseArrayList;
    }

    public void resetExerciseArrayList(){
        this.exerciseArrayList = null;
        exerciseArrayList = new ArrayList<Exercise>();
    }

    public boolean saveExerciseArrayListToJSON(String path) throws IOException {

        // update Exercise ArrayList from JSON
        JSONObject object = new JSONObject(new String(Files.readAllBytes(Paths.get(path)))); // whole object
        object.remove(Exercise.class.getSimpleName()); // remove array corresponding to "Exercise" key
        JSONArray jsonArray = new JSONArray();
        for(int i = 0; i < exerciseArrayList.size(); i++){
            jsonArray.put(exerciseArrayList.get(i).saveExerciseToJSONObject());
        }
        object.put(Exercise.class.getSimpleName(), jsonArray); // put "Exercise" key with arrayList / JSONArray as value
        // update user jsonMETAObject with ExerciseList
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
                file.flush();
                file.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
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
