package eu.exploptimist.Model;

public class Session {

    private Exercise exerciseList[];
    private int exerciseNb;
    private int sessionId;
    private AdaptationRules userAdaptationRules;
    private int userFeedback;

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

    public Exercise[] getExerciseList() {
        return exerciseList;
    }

    public void setExerciseList(Exercise[] exerciseList) {
        this.exerciseList = exerciseList;
    }

    public void initExerciseListLength(int length){
        this.exerciseList = new Exercise[length];
    }

    public void addToExerciseList(Exercise exercise){
        Exercise temp[] = new Exercise[this.exerciseList.length - 1]; // size of current list
        temp = this.exerciseList; // we save the current list
        initExerciseListLength(this.exerciseList.length); // we create a new list with +1 index (old list is deleted in the process)
        this.exerciseList = temp; // we give back temp to list
        this.exerciseList[this.exerciseList.length - 1] = exercise; // we add the new exercise to the new list, at last new index
    }

    public int getExerciseNb() {
        return exerciseNb;
    }

    public void setExerciseNb(int exerciseNb) {
        this.exerciseNb = exerciseNb;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public int getUserFeedback() { return userFeedback; };

    public void setUserFeedback(int userFeedback){ this.userFeedback = userFeedback; }

    @Override
    public String toString() {
        String toString = "User: " + this.userAdaptationRules.getUser().getFirstName() + "\nSession " + sessionId
                + ", number of exercises: " + exerciseNb + "\nExercises: \n";
        for(int i = 0; i < this.exerciseList.length; i++){
            toString += this.exerciseList[i].toString() + "\n";
        }
        return toString;
    }
}
