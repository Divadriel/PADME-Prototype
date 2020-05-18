package fr.limsi.Model;

import fr.limsi.Model.Utils.Utils;

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
        this.duration = duration;
        this.distance = distance;
        this.completed = 0;
    }

    @Override
    public String toString() {
        String exercise = "";
        exercise += "Exercise ID \t" + exerciseID;
        exercise += "\nName \t" + name;
        exercise += "\nDuration \t" + duration;
        exercise += "\nDistance \t" + distance;
        exercise += "\nCompleted \t" + completed;

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
