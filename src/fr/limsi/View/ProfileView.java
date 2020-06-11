package fr.limsi.View;

import fr.limsi.Model.Programme;
import fr.limsi.Model.UserModel;
import fr.limsi.Model.Utils.Utils;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import org.json.JSONException;

import java.io.IOException;

public class ProfileView extends Parent {

    private TraceView traceView;
    // fields
    private TextArea dynamicProfileDisplay;
    private TextField nameField;
    private Spinner<Integer> ageSpinner;
    private RadioButton maleRB;
    private RadioButton femaleRB;
    private Spinner<Integer> motivationSpinner;
    private Spinner<Integer> PASpinner;
    private Spinner<Integer> promotionSpinner;
    private Spinner<Integer> preventionSpinner;

    public ProfileView(Programme programme, TraceView trcView){

        traceView = trcView;

        // creation and config of titled pane
        TitledPane profilePane = new TitledPane();
        profilePane.setText("User Profile");
        profilePane.setCollapsible(false);
        profilePane.setAnimated(false);

        // content placed in gridpane
        GridPane content = new GridPane();
        content.setHgap(5);
        content.setVgap(5);
        content.setPadding(new Insets(5));

        // main col 0: static profile
        GridPane staticProfilePane = new GridPane();
        staticProfilePane.setHgap(10);
        staticProfilePane.setVgap(10);
        staticProfilePane.setPadding(new Insets(5));

        // row 00: column titles
        Label staticName = new Label("\t\tStatic Profile");
        Label dynamicName = new Label("\t\tDynamic Profile");
        // row 0, cell 0
        Label name = new Label("Name");
        // row 0, cell 1
        nameField = new TextField("");
        nameField.setPrefWidth(120);

        // row 1, cell 0
        Label age = new Label("Age");
        // row 1, cell 1
        ageSpinner = new Spinner<>(1, 120, 25);
        ageSpinner.setEditable(true);
        Utils.commitSpinnerValueOnLostFocus(ageSpinner);

        // row 2, cell 0
        Label sex = new Label("Gender");
        // row 2, cell 1
        HBox sexBox = new HBox();
        sexBox.setSpacing(5);

        ToggleGroup sexGroup = new ToggleGroup();
        maleRB = new RadioButton("Male");
        maleRB.setToggleGroup(sexGroup);
        maleRB.setFocusTraversable(false);

        femaleRB = new RadioButton("Female");
        femaleRB.setToggleGroup(sexGroup);
        femaleRB.setFocusTraversable(false);

        sexGroup.selectedToggleProperty().addListener((observableValue, oldValue, newValue) -> {

            if(newValue.equals(maleRB)){
                programme.getUser().setGender("M");
            }
            else{
                programme.getUser().setGender("F");
            }
        });
            // adding to HBox
        sexBox.getChildren().addAll(maleRB, femaleRB);

        // row 3, cell 0
        Label motivationLabel = new Label("Motivation");
        // row 3, cell 1
        motivationSpinner = new Spinner<>(0, 100, 50);
        motivationSpinner.setEditable(true);
        Utils.commitSpinnerValueOnLostFocus(motivationSpinner);

        // row 4, cell 0
        Label PALabel = new Label("Physical Activity");
        PALabel.setPadding(new Insets(5));
        // row 4, cell 1
        PASpinner = new Spinner<>(0, 100, 50);
        PASpinner.setEditable(true);
        Utils.commitSpinnerValueOnLostFocus(PASpinner);

        // row 5, cell 0
        Label proLabel = new Label("Promotion");
        proLabel.setPadding(new Insets(5));
        // row 5, cell 1
        promotionSpinner = new Spinner<>(0,100,50);
        promotionSpinner.setEditable(true);
        Utils.commitSpinnerValueOnLostFocus(promotionSpinner);

        // row 6, cell 0
        Label preLabel = new Label("Prevention");
        preLabel.setPadding(new Insets(5));
        // row 6, cell 1
        preventionSpinner = new Spinner<>(0, 100, 50);
        preventionSpinner.setEditable(true);
        Utils.commitSpinnerValueOnLostFocus(preventionSpinner);

        // row 7, cell 0
        Button displayProfile = new Button("Display Profile");
        displayProfile.setOnAction(event -> {
            traceView.getMainDisplay().appendText(programme.getUser().displayStaticProfile());
            dynamicProfileDisplay.setText(programme.getUser().displayDynamicProfile());
        });
        // row 7, cell 1
        GridPane saveLoadPane = new GridPane();
        saveLoadPane.setPadding(new Insets(5));
        saveLoadPane.setVgap(5);
        saveLoadPane.setHgap(5);
        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> {
            programme.getUser().setFirstName(nameField.getText());
            programme.getUser().setAge(ageSpinner.getValue());
            programme.getUser().setMotivationLevel(motivationSpinner.getValue());
            programme.getUser().setPhysicalActivityLevel(PASpinner.getValue());
            programme.getUser().setPromotion(promotionSpinner.getValue());
            programme.getUser().setPrevention(preventionSpinner.getValue());
            traceView.getMainDisplay().appendText("Updated UserModel " + programme.getUser().getFirstName() + ".\n");
            try {
                if(programme.getUser().saveUserModelToJSON()){
                    traceView.getMainDisplay().appendText("Save to JSON file complete\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
                traceView.getMainDisplay().appendText("Error while saving to JSON\n");
            }
        });

        Button loadButton = new Button("Load");
        loadButton.setOnAction(event -> {
            try {
                // load a json file to user model
                programme.getUser().loadUserModelFromJSON();
            } catch (IOException e) {
                e.printStackTrace();
                traceView.getMainDisplay().appendText("Error loading profile.\n");
                return;
            }
            updateUserTextFields(programme.getUser());
            try {
                // once user profile is loaded, load exercises
                programme.updateExerciseArrayList();
            }
            catch (JSONException e){
                e.printStackTrace();
                traceView.getMainDisplay().appendText("Error loading exercises.\n");
                return;
            }
            traceView.getMainDisplay().appendText("Successfully loaded exercises.\n");
            try {
                // once user profile is loaded, load sessions
                programme.updateSessionArrayList();
            }
            catch (JSONException e){
                e.printStackTrace();
                traceView.getMainDisplay().appendText("Error loading sessions.\n");
                return;
            }
            traceView.getMainDisplay().appendText("Successfully loaded sessions.\n");
        });

        Button newButton = new Button("New");
        newButton.setOnAction(event -> {
            // reset user to start from scratch a new one
            programme.setUser(null);
            programme.setUser(new UserModel());
            programme.initNewJsonFile(programme.getUser().getUserID());
            clearUserTextFields();
        });

            // add to gridPane
        saveLoadPane.addRow(0, saveButton, loadButton, newButton);

        // main col 1: dynamic profile
        // row 0: display of dynamic profile
        GridPane dynamicProfilePane = new GridPane();
        dynamicProfilePane.setHgap(10);
        dynamicProfilePane.setVgap(10);
        dynamicProfilePane.setPadding(new Insets(5));
        dynamicProfileDisplay = new TextArea();
        dynamicProfileDisplay.setEditable(false);
        dynamicProfileDisplay.setPrefRowCount(15);
        dynamicProfileDisplay.setPrefColumnCount(20);

        // row 1: reset button
        Button resetDynamicProfile = new Button("Reset");
        resetDynamicProfile.setOnAction(event -> dynamicProfileDisplay.setText(""));

        // adding to gridpanes
        staticProfilePane.add(staticName, 0, 0, 2, 1);
        staticProfilePane.addColumn(0, name, age, sex, motivationLabel, PALabel, proLabel, preLabel, displayProfile);
        staticProfilePane.addColumn(1, nameField, ageSpinner, sexBox, motivationSpinner, PASpinner, promotionSpinner, preventionSpinner, saveLoadPane);
        dynamicProfilePane.add(dynamicName, 0, 0);
        dynamicProfilePane.add(dynamicProfileDisplay, 0, 1);
        dynamicProfilePane.add(resetDynamicProfile, 0, 2);

        content.add(staticProfilePane, 0, 0);
        content.add(dynamicProfilePane, 1, 0);

        profilePane.setContent(content);
        this.getChildren().add(profilePane);

    }

    public void updateUserTextFields(UserModel user){
        // display updated info from user model to fields
        nameField.setText(user.getFirstName());
        ageSpinner.getValueFactory().setValue(user.getAge());
        if(user.getGender().equals("M")){
            maleRB.setSelected(true);
        }
        else {
            femaleRB.setSelected(true);
        }
        motivationSpinner.getValueFactory().setValue(user.getMotivationLevel());
        PASpinner.getValueFactory().setValue(user.getPhysicalActivityLevel());
        promotionSpinner.getValueFactory().setValue(user.getPromotion());
        preventionSpinner.getValueFactory().setValue(user.getPrevention());

        traceView.getMainDisplay().appendText("Successfully loaded profile: "+user.getFirstName()+"\n");
    }

    private void clearUserTextFields(){
        nameField.setText("");
        ageSpinner.getValueFactory().setValue(20);
        motivationSpinner.getValueFactory().setValue(0);
        PASpinner.getValueFactory().setValue(0);
        promotionSpinner.getValueFactory().setValue(0);
        preventionSpinner.getValueFactory().setValue(0);
        traceView.getMainDisplay().setText("Cleared all fields.\n");
    }
}
