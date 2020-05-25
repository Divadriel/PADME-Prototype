package fr.limsi.Model;

import fr.limsi.Model.Utils.Utils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class UserModel {

    // userID
    private long userID;

    // static user model
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int age;
    private int weight;
    private int height;
    private String gender;

    // for each, range from 1 to 100
    private int promotion;
    private int prevention;
    private int chronicRegulatoryFocus; // assessment needs to be done --> which?
    private int physicalActivityLevel;
    private int motivationLevel;

    // dynamic user model
    private int startedExercises = 0;
    private int completedExercises = 0;
    private int startedSessions = 0;
    private int completedSessions = 0;
    private int totalMinutesActivity = 0;
    private double feedbackMean = 0;
    private double minutesActivityPerExerciseMean = 0;
    private double minutesActivityPerSessionMean = 0;
    private double kmTravelled = 0;
    private double kmTravelledPerExercise = 0;
    private double kmTravelledPerSession = 0;

    // other
    private FileWriter file;

    // jsonMETAObject is all data for a single user : user records, exercises records, sessions records, etc...
    private JSONObject jsonMETAObject;
    // jsonUserArray is the collection of user records (no exercise nor session, just user)
    private JSONArray jsonUserArray;

    public UserModel(){
        this.userID = Utils.calculateUniqueID();
        this.jsonMETAObject = new JSONObject();
        this.jsonUserArray = new JSONArray();
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
                ", Regulatory Focus: '" + chronicRegulatoryFocus + '\'' +
                ", Physical Activity Level: '" + physicalActivityLevel + '\'' +
                ", Motivation Level: '" + motivationLevel + '\'' +
                '.';
    }

    public String displayStaticProfile(){
        String profile = "";
        profile += "\n\t\tStatic profile\n\n";
        profile += "User ID \t" + userID + "\n";
        profile += "Name \t" + firstName + "\n";
        profile += "Age \t" + age + "\n";
        profile += "Gender \t" + gender + "\n";
        profile += "Motivation \t" + motivationLevel + "\n";
        profile += "Physical Activity \t" + physicalActivityLevel + "\n";
        profile += "Promotion \t" + promotion + "\n";
        profile += "Prevention \t" + prevention + "\n";
        profile += "Chronic focus \t" + chronicRegulatoryFocus + "\n";

        return profile;
    }

    public String displayDynamicProfile(){
        String profile = "";
        profile += "\t\tDynamic profile\n\n";
        profile += "Started exercises \t" + startedExercises + "\n";
        profile += "Completed exercises \t" + completedExercises + "\n";
        profile += "Started sessions \t" + startedSessions + "\n";
        profile += "Completed sessions \t" + completedSessions + "\n";
        profile += "Total minutes of activity \t" + totalMinutesActivity + "\n";
        profile += "Mean of minutes per exercise \t" + minutesActivityPerExerciseMean + "\n";
        profile += "Mean of minutes per session \t" + minutesActivityPerSessionMean + "\n";
        profile += "KM travelled \t" + kmTravelled + "\n";
        profile += "KM travelled per exercise \t" + kmTravelledPerExercise + "\n";
        profile += "KM travelled per session \t" + kmTravelledPerSession + "\n";
        profile += "Feedback mean \t" + feedbackMean + "\n";
        return profile;
    }

    public boolean saveUserModelToJSON() throws IOException {
        JSONObject jsonUserObject = new JSONObject();
        boolean result;

        // save static profile
        jsonUserObject.put("firstName", firstName);
        jsonUserObject.put("userID", userID);
        jsonUserObject.put("age", age);
        jsonUserObject.put("gender", gender);
        jsonUserObject.put("promotion", promotion);
        jsonUserObject.put("prevention", prevention);
        jsonUserObject.put("chronicRegulatoryFocus", chronicRegulatoryFocus);
        jsonUserObject.put("physicalActivityLevel", physicalActivityLevel);
        jsonUserObject.put("motivationLevel", motivationLevel);

        // save dynamic profile
        jsonUserObject.put("startedExercises", startedExercises);
        jsonUserObject.put("completedExercises", completedExercises);
        jsonUserObject.put("startedSessions", startedSessions);
        jsonUserObject.put("completedSessions", completedSessions);
        jsonUserObject.put("totalMinutesActivity", totalMinutesActivity);
        jsonUserObject.put("minutesActivityPerExerciseMean", minutesActivityPerExerciseMean);
        jsonUserObject.put("minutesActivityPerSessionMean", minutesActivityPerSessionMean);
        jsonUserObject.put("kmTravelled", kmTravelled);
        jsonUserObject.put("kmTravelledPerExercise", kmTravelledPerExercise);
        jsonUserObject.put("kmTravelledPerSession", kmTravelledPerSession);
        jsonUserObject.put("feedbackMean", feedbackMean);

        // save timestamp formatter
        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC+02:00"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss"); // for JSON file data
        // save timestamp to then retrieve last update of user profile, while saving all updates of user profile
        jsonUserObject.put("saveTimestamp", now.format(formatter));

        // bundle jsonUser to jsonUserArray
        jsonUserArray.put(jsonUserObject);

        // put to jsonMETAObject with class name as key
        jsonMETAObject.put(this.getClass().getSimpleName(), jsonUserArray);

        // save object to a daily file (all changes from a same day on a same file)
        try {
            file = new FileWriter(Utils.getUserSaveFilePath(userID), false);
            file.write(jsonMETAObject.toString(2));
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try{
                file.flush();
                file.close();
                result = true;
            } catch (IOException e) {
                e.printStackTrace();
                result = false;
            }
        }
        return result;
    }

    public void parseUserModelFromString(String content){
        // parse String to JSONObject
        JSONObject temp = new JSONObject(content);

        // get only user records from "UserModel" as key
        jsonUserArray = null; // reset current array of user records (1st line)
        jsonUserArray = temp.getJSONArray(this.getClass().getSimpleName()); // to load those from the file (2nd line)
        // load only last index
        JSONObject jsonObject = jsonUserArray.getJSONObject((jsonUserArray.length() -1));

        // update jsonMETAObject
        jsonMETAObject = null; // reset whole object
        jsonMETAObject = temp;
//        jsonMETAObject.put(this.getClass().getSimpleName(), jsonUserArray);

        userID = jsonObject.getLong("userID");
        // load static profile
        firstName = jsonObject.getString("firstName");
        age = jsonObject.getInt("age");
        gender = jsonObject.getString("gender");
        promotion = jsonObject.getInt("promotion");
        prevention = jsonObject.getInt("prevention");
        //chronicRegulatoryFocus = jsonObject.getInt("chronicRegulatoryFocus");
        computeChronicRegulatoryFocus();
        physicalActivityLevel = jsonObject.getInt("physicalActivityLevel");
        motivationLevel = jsonObject.getInt("motivationLevel");

        // load dynamic profile
        startedExercises = jsonObject.getInt("startedExercises");
        completedExercises = jsonObject.getInt("completedExercises");
        startedSessions = jsonObject.getInt("startedSessions");
        completedSessions = jsonObject.getInt("completedSessions");
        totalMinutesActivity = jsonObject.getInt("totalMinutesActivity");
        minutesActivityPerExerciseMean = jsonObject.getInt("minutesActivityPerExerciseMean");
        minutesActivityPerSessionMean = jsonObject.getInt("minutesActivityPerSessionMean");
        kmTravelled = jsonObject.getDouble("kmTravelled");
        kmTravelledPerExercise = jsonObject.getDouble("kmTravelledPerExercise");
        kmTravelledPerSession = jsonObject.getDouble("kmTravelledPerSession");
        feedbackMean = jsonObject.getDouble("feedbackMean");
    }

    public void loadUserModelFromJSON() throws IOException{
        String content = Utils.getJSONContentFromFile();
        parseUserModelFromString(content);
    }

    public long getUserID() { return userID; }

    public void setUserID(long userID){ this.userID = userID; }

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getChronicRegulatoryFocus() {
        return chronicRegulatoryFocus;
    }

    public void setChronicRegulatoryFocus(int chronicRegulatoryFocus) {
        this.chronicRegulatoryFocus = chronicRegulatoryFocus;
    }

    public void computeChronicRegulatoryFocus(){
        this.chronicRegulatoryFocus = promotion - prevention;
    }

    public int getPhysicalActivityLevel() {
        return physicalActivityLevel;
    }

    public void setPhysicalActivityLevel(int physicalActivityLevel) {
        if (physicalActivityLevel > 100){
            this.physicalActivityLevel = 100;
        }
        else{
            this.physicalActivityLevel = physicalActivityLevel;
        }
    }

    public int getMotivationLevel() {
        return motivationLevel;
    }

    public void setMotivationLevel(int motivationLevel) {
        if(motivationLevel > 100){
            this.motivationLevel = 100;
        }
        else{
            this.motivationLevel = motivationLevel;
        }
    }

    public int getPromotion() {
        return promotion;
    }

    public void setPromotion(int promotion) {
        this.promotion = promotion;
    }

    public int getPrevention() {
        return prevention;
    }

    public void setPrevention(int prevention) {
        this.prevention = prevention;
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

    public JSONObject getJsonMETAObject() { return jsonMETAObject; }

}
