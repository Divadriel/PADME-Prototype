package fr.limsi.Model;

public class Exercise {

    private String name;
    private int duration; // in minutes
    private float distance; // in km
    private String description;
    private double completed; // percents of completion - range 0-100

    public Exercise(String name, int duration, float distance){
        this.name = name;
        this.duration = duration;
        this.distance = distance;
        this.completed = 0;
    }

    @Override
    public String toString() {
        if(this.duration == 0){
            return "Exercise '" + name + "', for " + distance + " km.\n";
        }
        else if (this.distance == 0f){
            return "Exercise '" + name + "', for " + duration + " minutes.\n";
        }
        else{
            return "Exercise '" + name + "', for " + duration + " minutes or " + distance + " km.\n";
        }
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public double getCompleted() { return completed; }

    public void setCompleted(double completed) { this.completed = completed; }
}
