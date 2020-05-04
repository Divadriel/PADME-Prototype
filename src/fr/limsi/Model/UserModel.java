package fr.limsi.Model;

import fr.limsi.Model.Utils.Strings;
import javafx.stage.FileChooser;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class UserModel {

    // ID and user count
  //  private static int userCount = 0;
//    private static int USER_ID;

    // static user model
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private int age;
    private int weight;
    private int height;
    private String gender;

    // à considérer en double par la suite --> dimension continue
    private String regulatoryFocus;
    private String physicalActivityLevel;
    private String motivationLevel;

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

    public UserModel(String firstName, String lastName, int age, int weight, int height, String gender,
                     String regulatoryFocus, String physicalActivityLevel, String motivationLevel){
     //   userCount++;
       // USER_ID = userCount;
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

    public UserModel(){
    //    userCount++;
     //   USER_ID = userCount;
    }

    public UserModel(String firstName){

    //    userCount++;
    //    USER_ID = userCount;
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

    public String displayStaticProfile(){
        String profile = "";
        profile += "\n\t\tStatic profile\n\n";
        profile += "Name \t" + firstName + "\n";
        profile += "Age \t" + age + "\n";
        profile += "Gender \t" + gender + "\n";
        profile += "Motivation \t" + motivationLevel + "\n";
        profile += "Physical Activity \t" + physicalActivityLevel + "\n";
        profile += "Chronic focus \t" + regulatoryFocus + "\n";

        return profile;
    }

    public String displayDynamicProfile(){
        String profile = "";
        profile += "\n\t\tDynamic profile\n\n";
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

    public boolean saveUserModelToJSON(){
        JSONObject jsonUser = new JSONObject();

        // save static profile
    //    jsonUser.put("userID", USER_ID);
        jsonUser.put("firstName", firstName);
        //jsonUser.put("lastName", lastName);
      //  jsonUser.put("email", email);
        jsonUser.put("age", age);
       // jsonUser.put("weight", weight);
     //   jsonUser.put("height", height);
        jsonUser.put("gender", gender);
        jsonUser.put("regulatoryFocus", regulatoryFocus);
        jsonUser.put("physicalActivityLevel", physicalActivityLevel);
        jsonUser.put("motivationLevel", motivationLevel);

        // save dynamic profile
        jsonUser.put("startedExercises", startedExercises);
        jsonUser.put("completedExercises", completedExercises);
        jsonUser.put("startedSessions", startedSessions);
        jsonUser.put("completedSessions", completedSessions);
        jsonUser.put("totalMinutesActivity", totalMinutesActivity);
        jsonUser.put("minutesActivityPerExerciseMean", minutesActivityPerExerciseMean);
        jsonUser.put("minutesActivityPerSessionMean", minutesActivityPerSessionMean);
        jsonUser.put("kmTravelled", kmTravelled);
        jsonUser.put("kmTravelledPerExercise", kmTravelledPerExercise);
        jsonUser.put("kmTravelledPerSession", kmTravelledPerSession);
        jsonUser.put("feedbackMean", feedbackMean);

        // save timestamp formatter
        LocalDateTime now = LocalDateTime.now(ZoneId.of("UTC+02:00"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        String saveTimestamp = now.format(formatter);
        // save timestamp to then retrieve last update of user profile, while saving all updates of user profile
        jsonUser.put("saveTimestamp", saveTimestamp);

        try {
            //file = new FileWriter("/Users/reida/Documents/PADMEH_data/"+firstName+age+".json", true);
            file = new FileWriter("/Users/"+System.getProperty("user.name")+"/Documents/PADMEH_data/"+firstName+"_"+saveTimestamp+".json", false);
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

    public void loadUserModelFromJSON() throws IOException{
        // get file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose User Profile JSON");
        //fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
        File file = fileChooser.showOpenDialog(null);
        String path = file.getAbsolutePath();
        // extract content to String
        String content = new String(Files.readAllBytes(Paths.get(path)));

        // parse String to JSON Object
        JSONObject jsonObject = new JSONObject(content);
        // load static profile
        firstName = jsonObject.getString("firstName");
        age = jsonObject.getInt("age");
        gender = jsonObject.getString("gender");
        regulatoryFocus = jsonObject.getString("regulatoryFocus");
        physicalActivityLevel = jsonObject.getString("physicalActivityLevel");
        motivationLevel = jsonObject.getString("motivationLevel");

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
