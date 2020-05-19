package fr.limsi.Model;

import fr.limsi.Model.Utils.Utils;

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
        session += "UserFeedback \t" + userFeedback + "\n";
        session += "Exercise List \n";
        for (int i = 0; i < exerciseList.size(); i++){
            session += "\t";
            session += exerciseList.get(i).toString();
        }
        session += "\n";
        return session;
    }
/*
    @Override
    public String toString() {
        String toString = "User: " + this.userAdaptationRules.getUser().getFirstName() + "\nSession " + sessionId
                + ", number of exercises: " + exerciseNb + "\nExercises: \n";
        for(int i = 0; i < this.exerciseList.size(); i++){
            toString += this.exerciseList[i].toString() + "\n";
        }
        return toString;
    }
 */
}
