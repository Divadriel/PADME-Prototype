package fr.limsi.Model;

import fr.limsi.Model.Utils.Utils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Session {

    private final long sessionID;
    private ArrayList<Exercise> exerciseList;
    private int userFeedback;
    private long userID;

    public Session(ArrayList<Exercise> exerciseList, long userID, int userFeedback){
        this.sessionID = Utils.calculateUniqueID();
        this.exerciseList = exerciseList;
        this.userFeedback = userFeedback;
        this.userID = userID;
    }

    public Session(){

        this.sessionID = Utils.calculateUniqueID();
        this.exerciseList = new ArrayList<>();
    }

    public Session(long sessionID, ArrayList<Exercise> exerciseList, long userID, int userFeedback){
        this.sessionID = sessionID;
        this.exerciseList = exerciseList;
        this.userFeedback = userFeedback;
        this.userID = userID;
    }

    public JSONObject saveSessionToJSONObject(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sessionID", sessionID);
        jsonObject.put("userFeedback", userFeedback);
        jsonObject.put("userID", userID);
//        jsonObject.put("ExerciseList", new JSONArray(exerciseList));
        jsonObject.put("ExerciseIDList", getExerciseIDArrayList());
        return jsonObject;
    }

    public JSONArray getExerciseIDArrayList(){
        JSONArray jsonArray = new JSONArray();
        for (Exercise exercise : exerciseList){
            jsonArray.put(exercise.getExerciseID());
        }
        return jsonArray;
    }

    public ArrayList<Exercise> getExerciseList() {
        return exerciseList;
    }

    public void setExerciseList(ArrayList<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }

    public long getSessionID() {
        return sessionID;
    }

    public int getUserFeedback() { return userFeedback; }

    public void setUserFeedback(int userFeedback){ this.userFeedback = userFeedback; }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    @Override
    public String toString(){
        String session = "";
        session += "Session ID \t" + sessionID + "\n";
        session += "User ID \t" + userID + "\n";
        session += "UserFeedback \t" + userFeedback + "\n";
        session += "Exercise List \n";
        for (Exercise exercise : exerciseList) {
            session += exercise.toString();
        }
        session += "\n";
        return session;
    }
}
