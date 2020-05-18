package fr.limsi.Model;

import fr.limsi.Model.Utils.Utils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class Programme {

    private ArrayList<Exercise> exerciseArrayList;
    private ArrayList<Session> sessionArrayList;
    private UserModel user;
    private AdaptationRules adaptationRules;
    private JSONObject jsonMETAObject;

    public Programme() throws IOException{

        // config components
        jsonMETAObject = new JSONObject(); // jsonObject representing all data for user : userModel, exercises, sessions, etc. hence the name
        user = new UserModel(jsonMETAObject);

        // load init data from init.json -- User John Doe + sample exercises and session
        initProgramme();

        adaptationRules = new AdaptationRules(user, sessionArrayList.get(0));

    }

    private void initProgramme() throws IOException {
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
                    user,
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

    public JSONObject getJsonMETAObject() {
        return jsonMETAObject;
    }

    public void setJsonMETAObject(JSONObject jsonMETAObject) {
        this.jsonMETAObject = jsonMETAObject;
    }
}
