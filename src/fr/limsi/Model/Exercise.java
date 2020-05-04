package fr.limsi.Model;

public class Exercise {

    private String name;
    private int length; // in minutes
    private float distance; // in km
    private String description;
    private double completed; // percents of completion - range 0-100

    public Exercise(String name, int length, float distance){
        this.name = name;
        this.length = length;
        this.distance = distance;
        this.completed = 0;
    }
    /*
    public Exercise(String name, int length){
        this.name = name;
        this.length = length;
        this.distance = 0f;
        this.description = toString();
    }
    public Exercise(String name, float distance){
        this.name = name;
        this.distance = distance;
        this.length = 0;
        this.description = toString();
    }

     */

    @Override
    public String toString() {
        if(this.length == 0){
            return "Exercise '" + name + "', for " + distance + " km.\n";
        }
        else if (this.distance == 0f){
            return "Exercise '" + name + "', for " + length + " minutes.\n";
        }
        else{
            return "Exercise '" + name + "', for " + length + " minutes or " + distance + " km.\n";
        }
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
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
