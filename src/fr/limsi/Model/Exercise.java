package fr.limsi.Model;

public class Exercise {

    private String name;
    private double duration; // in minutes
    private double distance; // in km
    private String description;
    private double completed; // percents of completion - range 0-100

    public Exercise(String name, double duration, double distance){
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
        else if (this.distance == 0){
            return "Exercise '" + name + "', for " + duration + " minutes.\n";
        }
        else{
            return "Exercise '" + name + "', for " + duration + " minutes or " + distance + " km.\n";
        }
    }

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
