package eu.exploptimist.Model;

public class Exercise {

    private String name;
    private int length; // in minutes
    private float distance; // in km
    private String description;

    public Exercise(String name, int length, float distance, String description){
        this.name = name;
        this.length = length;
        this.distance = distance;
        this.description = description;
    }
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

    @Override
    public String toString() {
        if(this.length == 0){
            return "Exercise \'" + name + "\', during " + distance + " km.";
        }
        else if (this.distance == 0f){
            return "Exercise \'" + name + "\', during " + length + " minutes.";
        }
        else{
            return "Exercise \'" + name + "\', during " + length + " minutes or " + distance + " km.";
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
}
