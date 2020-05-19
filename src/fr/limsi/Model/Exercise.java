package fr.limsi.Model;

import fr.limsi.Model.Utils.Utils;
import org.json.JSONObject;

public class Exercise {

    private final long exerciseID;
    private String name;
    private double duration; // in minutes
    private double distance; // in km
    private String description;
    private double completed; // percents of completion - range 0-100

    public Exercise(String name, double duration, double distance){
        this.exerciseID = Utils.calculateUniqueID();
        this.name = name;
        this.duration = Utils.setDurationToClosestUpperNMinutes(duration,5);
        this.distance = distance;
        this.completed = 0;
    }

    public JSONObject saveExerciseToJSONObject (){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("duration", duration);
        jsonObject.put("distance", distance);
        jsonObject.put("completed", completed);
        return jsonObject;
    }

    @Override
    public String toString() {
        String exercise = "";
        exercise += "\tExercise ID \t" + exerciseID;
        exercise += "\n\tName \t" + name;
        exercise += "\n\tDuration \t" + duration;
        exercise += "\n\tDistance \t" + distance;
        exercise += "\n\tCompleted \t" + completed;
        exercise += "\n";
        return exercise;
    }

    public long getExerciseID() { return exerciseID; }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getCompleted() { return completed; }

    public void setCompleted(double completed) { this.completed = completed; }
}
