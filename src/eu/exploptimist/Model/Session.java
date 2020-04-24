package eu.exploptimist.Model;

import java.util.List;

public class Session {

    private List<Exercise> exerciseList;
    //private int exerciseNb;
    private int sessionId;
    private AdaptationRules userAdaptationRules;
    private int userFeedback;
/*
    public Session(int sessionId, int exerciseNb, Exercise exerciseList[], UserModel user){
        this.exerciseList = exerciseList;
        this.sessionId = sessionId;
        this.exerciseNb = exerciseNb;
        this.userAdaptationRules = new AdaptationRules(user);
        computeAdaptationRules();
    }
    public Session(int sessionId, int exerciseNb, UserModel user){
        this.sessionId = sessionId;
        this.exerciseNb = exerciseNb;
        this.exerciseList = new Exercise[this.exerciseNb];
        this.userAdaptationRules = new AdaptationRules(user);
        computeAdaptationRules();
    }

    public Session(int sessionId, Exercise exerciseList[], UserModel user){
        this.sessionId = sessionId;
        this.exerciseList = exerciseList;
        this.exerciseNb = exerciseList.length;
        this.userAdaptationRules = new AdaptationRules(user);
        computeAdaptationRules();
    }
*/
    public Session(List<Exercise> exerciseList, UserModel user, int userFeedback){
      //  this.exerciseNb = exerciseNb;
        this.exerciseList = exerciseList;
        this.userFeedback = userFeedback;
        // use user to init adapt rules
    }

    public void computeAdaptationRules(){
        float distancePAMultiplier = userAdaptationRules.distancePAMultiplier();
        float lengthPAMultiplier = userAdaptationRules.lengthPAMultiplier();
        for(Exercise exercise : this.exerciseList){
            if(exercise.getLength() != 0){
                exercise.setLength((int)(exercise.getLength() * lengthPAMultiplier));
            }
            if(exercise.getDistance() != 0){
                exercise.setDistance((int)(exercise.getDistance() * distancePAMultiplier));
            }
        }
    }

    public List<Exercise> getExerciseList() {
        return exerciseList;
    }

    public void setExerciseList(List<Exercise> exerciseList) {
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

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getUserFeedback() { return userFeedback; }

    public void setUserFeedback(int userFeedback){ this.userFeedback = userFeedback; }

    @Override
    public String toString(){
        return "Session toString to be completed, there are " + exerciseList.size() + " exercises in the session.\n";
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
