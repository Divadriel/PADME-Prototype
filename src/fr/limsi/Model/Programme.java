package fr.limsi.Model;

import fr.limsi.Model.Utils.Colors;
import fr.limsi.Model.Utils.Strings;
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
    private Session currentSession;
    private Exercise currentExercise;

    // system related
    private JSONObject messages;
    private JSONObject colors;

    public Programme() throws IOException{

        // config components
        user = new UserModel();
        messages = loadNeutralMessages();
        colors = loadNeutralColors();

        // load init data from init.json -- User John Doe + sample exercises and session
        initProgramme();
        //adaptationRules = new AdaptationRules(user, sessionArrayList.get(0));
    }

    private JSONObject loadNeutralMessages(){
        JSONObject messages = new JSONObject();
        messages.put("MESS_PROFILE_CREATED", Strings.NEUT_MESS_PROFILE_CREATED);
        messages.put("MESS_EX_BEG", Strings.NEUT_MESS_EX_BEG);
        messages.put("MESS_EX_MID1", Strings.NEUT_MESS_EX_MID1);
        messages.put("MESS_EX_MID2", Strings.NEUT_MESS_EX_MID2);
        messages.put("MESS_EX_END", Strings.NEUT_MESS_EX_END);
        messages.put("MESS_SESSION_END", Strings.NEUT_MESS_SESSION_END);
        return messages;
    }

    private JSONObject loadNeutralColors(){
        JSONObject colors = new JSONObject();
        colors.put("COL_BRIGHTER", Colors.NEUT_COL_BRIGHTER);
        colors.put("COL_DARK", Colors.NEUT_COL_DARK);
        colors.put("COL_DARKER", Colors.NEUT_COL_DARKER);
        colors.put("COL_DARKEST", Colors.NEUT_COL_DARKEST);
        colors.put("COL_MAIN", Colors.NEUT_COL_MAIN);
        return colors;
    }

    private void initProgramme() throws IOException {
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
        sessionArrayList = new ArrayList<>();
        setSessionArrayListFromJSONObject(object, sessionArrayList);
    }

    public boolean loadContentFromJSONFile(String contentType) {

        String content = "";

        try{
            content = Utils.getJSONContentFromFile();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        // parse String to JSONObject
        JSONObject temp = new JSONObject(content);

        switch (contentType){
            case "Exercise":
                // load to exoAL: extract json array from content string and then use setExoALFromJsonArray
                JSONArray jsonExerciseArray = temp.getJSONArray(Exercise.class.getSimpleName()); // json array of exercises
                // update exerciseAL with ex from file
                setExerciseArrayListFromJSONArray(jsonExerciseArray, exerciseArrayList);

                // update jsonMETAObject
                user.getJsonMETAObject().remove(Exercise.class.getSimpleName()); // remove array corresponding to "Exercise" key
                JSONArray jsonArray = new JSONArray();
                for (Exercise exercise : exerciseArrayList) {
                    jsonArray.put(exercise.saveExerciseToJSONObject());
                }
                user.getJsonMETAObject().put(Exercise.class.getSimpleName(), jsonArray); // put "Exercise" key with JSONArray as value
                break;
            case "Session":
                // load to SessionAL: extract json array from content string and then use setSessionALFromJsonArray
                setSessionArrayListFromJSONObject(temp, sessionArrayList);

                // update META -- same procedure as above for Exercise
                user.getJsonMETAObject().remove(Session.class.getSimpleName());
                JSONArray jsonArray1 = new JSONArray();
                for (Session session : sessionArrayList) {
                    jsonArray1.put(session.saveSessionToJSONObject());
                }
                user.getJsonMETAObject().put(Session.class.getSimpleName(), jsonArray1);
                break;
            default:
                return false;
        }
        return true;
    }

    private void setExerciseArrayListFromJSONArray(JSONArray jsonExerciseArray, ArrayList<Exercise> arrayList){
        for (int i = 0; i < jsonExerciseArray.length(); i++){
            JSONObject jsonObject = jsonExerciseArray.getJSONObject(i);
            Exercise exercise = new Exercise(jsonObject);
            arrayList.add(exercise);
        }
    }

    private void setSessionArrayListFromJSONObject(JSONObject jsonObject, ArrayList<Session> arrayList){
        JSONArray jsonSessionArray = jsonObject.getJSONArray(Session.class.getSimpleName()); // session json array
        JSONArray jsonExerciseArray = jsonObject.getJSONArray(Exercise.class.getSimpleName()); // exercise json array
        int i;
        int j;
        int k;
        for (j = 0; j < jsonSessionArray.length(); j++){
            JSONObject jsonObject2 = jsonSessionArray.getJSONObject(j); // session json object
            JSONArray temp = jsonObject2.getJSONArray("ExerciseIDList"); // exercise ID json array
            ArrayList<Exercise> exercises = new ArrayList<>(); // reset exercises ArrayList for each new Session json object

            for(k = 0; k < temp.length(); k++){ // temp -> each ID must be matched with exId in exArray
                i = 0; // reset jsonExerciseArray index i for new research at index k in temp
                long tempID = temp.getLong(k); // exercise ID at index k

                while(i < jsonExerciseArray.length()){

                    if(tempID == jsonExerciseArray.getJSONObject(i).getLong("exerciseID")){
                        exercises.add(new Exercise(jsonExerciseArray.getJSONObject(i)));
                        break; // index i in jsonExerciseArray is equal to index k in exerciseID list from jsonSessionArray
                    }
                    else {
                        i++;
                    }
                }
            } // end of for loop = exercises ArrayList is full

            // create a new session and add it to the final list
            Session session = new Session(
                    jsonObject2.getLong("sessionID"),
                    exercises,
                    user.getUserID(),
                    jsonObject2.getInt("userFeedback")
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
        setSessionArrayListFromJSONObject(user.getJsonMETAObject(), sessionArrayList);
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

    public void saveSessionArrayListToJSON(long userID) throws IOException {
        // get file
        String path = Utils.getUserSaveFilePath(userID);
        // update Session ArrayList from JSON
        JSONObject object = new JSONObject(new String(Files.readAllBytes(Paths.get(path)))); // whole object

        object.remove(Session.class.getSimpleName()); // remove array corresponding to "Session" key
        JSONArray jsonArray = new JSONArray();
        for (Session session : sessionArrayList) {
            jsonArray.put(session.saveSessionToJSONObject());
        }
        object.put(Session.class.getSimpleName(), jsonArray); // put "Session" key with JSONArray as value
        // update user jsonMETAObject with SessionList
        user.getJsonMETAObject().remove(Session.class.getSimpleName());
        user.getJsonMETAObject().put(Session.class.getSimpleName(), jsonArray);

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

    public UserModel getUserByID(long userID){

        if(user.getUserID() == userID){
            return user;
        }
        return null;
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

    public Session getCurrentSession(){ return currentSession; }

    public void setCurrentSession(Session currentSession){ this.currentSession = currentSession; }

    public Exercise getCurrentExercise(){ return currentExercise; }

    public void setCurrentExercise(Exercise currentExercise){ this.currentExercise = currentExercise; }

    public JSONObject getMessages() {
        return messages;
    }

    public void setMessages(JSONObject messages) {
        this.messages = messages;
    }

    public void setMessagesFromUserModel(){
        this.messages = AdaptationRules.loadMotivationalMessages(this.user);
    }

    public JSONObject getColors() {
        return colors;
    }

    public void setColors(JSONObject colors) {
        this.colors = colors;
    }

    public void setColorsFromUserModel(){
        this.colors = AdaptationRules.loadColors(this.user);
    }
}
