package fr.limsi.Model;

import fr.limsi.Model.Utils.Utils;

import java.util.ArrayList;

public class Session {

    private final long sessionID;
    private ArrayList<Exercise> exerciseList;
    //private int exerciseNb;
    private AdaptationRules userAdaptationRules;
    private int userFeedback;

    public Session(ArrayList<Exercise> exerciseList, UserModel user, int userFeedback){
      //  this.exerciseNb = exerciseNb;
        this.sessionID = Utils.calculateUniqueID();
        this.exerciseList = exerciseList;
        this.userFeedback = userFeedback;
        // use user to init adapt rules
    }
/*
    public void computeAdaptationRules(){
        float distancePAMultiplier = userAdaptationRules.distancePAMultiplier();
        float lengthPAMultiplier = userAdaptationRules.lengthPAMultiplier();
        for(Exercise exercise : this.exerciseList){
            if(exercise.getDuration() != 0){
                exercise.setDuration((int)(exercise.getDuration() * lengthPAMultiplier));
            }
            if(exercise.getDistance() != 0){
                exercise.setDistance((int)(exercise.getDistance() * distancePAMultiplier));
            }
        }
    }

 */

    public ArrayList<Exercise> getExerciseList() {
        return exerciseList;
    }

    public void setExerciseList(ArrayList<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }
/*
    public int getExerciseNb() {
        return exerciseNb;
    }

    public void setExerciseNb(int exerciseNb) {
        this.exerciseNb = exerciseNb;
    }

 */

    public long getSessionID() {
        return sessionID;
    }

    public int getUserFeedback() { return userFeedback; }

    public void setUserFeedback(int userFeedback){ this.userFeedback = userFeedback; }

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
