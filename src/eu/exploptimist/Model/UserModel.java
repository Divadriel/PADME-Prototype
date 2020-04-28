package eu.exploptimist.Model;

import eu.exploptimist.Model.Utils.Strings;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class UserModel {

    // ID and user count
    private static int userCount = 0;
    private static int USER_ID;

    // static user model
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int age;
    private int weight;
    private int height;
    private char gender;

    // à considérer en double par la suite --> dimension continue
    private String regulatoryFocus;
    private String physicalActivityLevel;
    private String motivationLevel;

    // dynamic user model
    private int startedExercises;
    private int completedExercises;
    private int startedSessions;
    private int completedSessions;
    private int totalMinutesActivity;
    private double feedbackMean;
    private double minutesActivityPerExerciseMean;
    private double minutesActivityPerSessionMean;
    private double kmTravelled;
    private double kmTravelledPerExercise;
    private double kmTravelledPerSession;

    // other
    private FileWriter file;

    private String checkRegFocus(String regulatoryFocus){
        switch(regulatoryFocus){
            case "promotion":
            case "Promotion":
                return Strings.PROMOTION;
            case "prevention":
            case "Prevention":
                return Strings.PREVENTION;
            default:
                return Strings.UNKNOWN;
        }
    }

    public UserModel(String firstName, String lastName, int age, int weight, int height, char gender,
                     String regulatoryFocus, String physicalActivityLevel, String motivationLevel){
        userCount++;
        USER_ID = userCount;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.gender = gender;
        this.regulatoryFocus = checkRegFocus(regulatoryFocus);
        this.physicalActivityLevel = physicalActivityLevel;
        this.motivationLevel = motivationLevel;
    }
    public UserModel(String firstName){

        userCount++;
        USER_ID = userCount;
        this.firstName = firstName;
    }

    @Override
    public String toString() {
        return "User Details: " + '\n' +
                "First Name: '" + firstName + '\'' +
                ", Last Name: '" + lastName + '\'' +
                ", Age: " + age +
                ", Weight: " + weight +
                ", Height: " + height +
                ", Gender: " + gender +
                ", Regulatory Focus: '" + regulatoryFocus + '\'' +
                ", Physical Activity Level: '" + physicalActivityLevel + '\'' +
                ", Motivation Level: '" + motivationLevel + '\'' +
                '.';
    }

    public String displayProfile(){
        String profile = "";
        profile += "Name \t" + firstName + "\n";
        profile += "Age \t" + age + "\n";
        profile += "Gender \t" + gender + "\n";
        profile += "Motivation \t" + motivationLevel + "\n";
        profile += "Physical Activity \t" + physicalActivityLevel + "\n";
        profile += "Chronic focus \t" + regulatoryFocus + "\n";

        return profile;
    }

    public boolean saveUserModelToJSON(){
        JSONObject jsonUser = new JSONObject();
        jsonUser.put("userID", USER_ID);
        jsonUser.put("firstName", firstName);
        jsonUser.put("lastName", lastName);
        jsonUser.put("email", email);
        jsonUser.put("password", password);
        jsonUser.put("age", age);
        jsonUser.put("weight", weight);
        jsonUser.put("height", height);
        jsonUser.put("gender", gender);
        jsonUser.put("regulatoryFocus", regulatoryFocus);
        jsonUser.put("physicalActivityLevel", physicalActivityLevel);
        jsonUser.put("motivationLevel", motivationLevel);

        try {
            file = new FileWriter("D:\\Users\\reida\\Documents\\PADMEH_data\\"+firstName+lastName+USER_ID+".json", true);
            file.write(jsonUser.toString(2));
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try{
                file.flush();
                file.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getRegulatoryFocus() {
        return regulatoryFocus;
    }

    public void setRegulatoryFocus(String regulatoryFocus) {
        this.regulatoryFocus = regulatoryFocus;
    }

    public String getPhysicalActivityLevel() {
        return physicalActivityLevel;
    }

    public void setPhysicalActivityLevel(String physicalActivityLevel) {
        this.physicalActivityLevel = physicalActivityLevel;
    }

    public String getMotivationLevel() {
        return motivationLevel;
    }

    public void setMotivationLevel(String motivationLevel) {
        this.motivationLevel = motivationLevel;
    }

    public int getStartedExercises() {
        return startedExercises;
    }

    public void setStartedExercises(int startedExercises) {
        this.startedExercises = startedExercises;
    }

    public int getCompletedExercises() {
        return completedExercises;
    }

    public void setCompletedExercises(int completedExercises) {
        this.completedExercises = completedExercises;
    }

    public int getStartedSessions() {
        return startedSessions;
    }

    public void setStartedSessions(int startedSessions) {
        this.startedSessions = startedSessions;
    }

    public int getCompletedSessions() {
        return completedSessions;
    }

    public void setCompletedSessions(int completedSessions) {
        this.completedSessions = completedSessions;
    }

    public int getTotalMinutesActivity() {
        return totalMinutesActivity;
    }

    public void setTotalMinutesActivity(int totalMinutesActivity) {
        this.totalMinutesActivity = totalMinutesActivity;
    }

    public double getFeedbackMean() {
        return feedbackMean;
    }

    public void setFeedbackMean(double feedbackMean) {
        this.feedbackMean = feedbackMean;
    }

    public double getMinutesActivityPerExerciseMean() {
        return minutesActivityPerExerciseMean;
    }

    public void setMinutesActivityPerExerciseMean(double minutesActivityPerExerciseMean) {
        this.minutesActivityPerExerciseMean = minutesActivityPerExerciseMean;
    }

    public double getMinutesActivityPerSessionMean() {
        return minutesActivityPerSessionMean;
    }

    public void setMinutesActivityPerSessionMean(double minutesActivityPerSessionMean) {
        this.minutesActivityPerSessionMean = minutesActivityPerSessionMean;
    }

    public double getKmTravelled() {
        return kmTravelled;
    }

    public void setKmTravelled(double kmTravelled) {
        this.kmTravelled = kmTravelled;
    }

    public double getKmTravelledPerExercise() {
        return kmTravelledPerExercise;
    }

    public void setKmTravelledPerExercise(double kmTravelledPerExercise) {
        this.kmTravelledPerExercise = kmTravelledPerExercise;
    }

    public double getKmTravelledPerSession() {
        return kmTravelledPerSession;
    }

    public void setKmTravelledPerSession(double kmTravelledPerSession) {
        this.kmTravelledPerSession = kmTravelledPerSession;
    }

    public FileWriter getFile(){ return file; }

}
