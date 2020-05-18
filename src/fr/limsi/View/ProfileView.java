package fr.limsi.View;

import fr.limsi.Model.UserModel;
import fr.limsi.Model.Utils.Utils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class ProfileView extends Parent {

    private UserModel user;
    private TraceView traceView;
    private TextArea dynamicProfileDisplay;

    public ProfileView(UserModel usr, TraceView trcView){

        user = usr;
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
        TextField nameField = new TextField("");
        nameField.setPrefWidth(120);

        // row 1, cell 0
        Label age = new Label("Age");
        // row 1, cell 1
        Spinner<Integer> ageSpinner = new Spinner<>(1, 120, 25);
        ageSpinner.setEditable(true);

        // row 2, cell 0
        Label sex = new Label("Gender");
        // row 2, cell 1
        HBox sexBox = new HBox();
        sexBox.setSpacing(5);

        ToggleGroup sexGroup = new ToggleGroup();
        RadioButton maleRB = new RadioButton("Male");
        maleRB.setToggleGroup(sexGroup);
        maleRB.setFocusTraversable(false);

        RadioButton femaleRB = new RadioButton("Female");
        femaleRB.setToggleGroup(sexGroup);
        femaleRB.setFocusTraversable(false);

        sexGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle oldValue, Toggle newValue) {

                if(newValue.equals(maleRB)){
                    user.setGender("M");
                }
                else{
                    user.setGender("F");
                }
            }
        });
            // adding to HBox
        sexBox.getChildren().addAll(maleRB, femaleRB);

        // row 3, cell 0
        Label motivationLabel = new Label("Motivation");
        // row 3, cell 1
        Spinner<Integer> motivationSpinner = new Spinner<>(1, 100, 50);
        motivationSpinner.setEditable(true);

        // row 4, cell 0
        Label PALabel = new Label("Physical Activity");
        PALabel.setPadding(new Insets(5));
        // row 4, cell 1
        Spinner<Integer> PASpinner = new Spinner<>(1, 100, 50);
        PASpinner.setEditable(true);

        // row 5, cell 0
        Label RFLabel = new Label("Chronic focus");
        RFLabel.setPadding(new Insets(5));
        // row 5, cell 1
        Spinner<Integer> RFSpinner = new Spinner<>(1, 100, 50);
        RFSpinner.setEditable(true);

        // row 6, cell 0
        Button displayProfile = new Button("Display Profile");
        displayProfile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                traceView.getMainDisplay().appendText(user.displayStaticProfile());
                dynamicProfileDisplay.appendText(user.displayDynamicProfile());
            }
        });
        // row 6, cell 1
        GridPane saveLoadPane = new GridPane();
        saveLoadPane.setPadding(new Insets(5));
        saveLoadPane.setVgap(5);
        saveLoadPane.setHgap(5);
        Button saveButton = new Button("Save");
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                user.setFirstName(nameField.getText());
                user.setAge(ageSpinner.getValue());
                user.setMotivationLevel(motivationSpinner.getValue());
                user.setPhysicalActivityLevel(PASpinner.getValue());
                user.setRegulatoryFocus(RFSpinner.getValue());
                traceView.getMainDisplay().appendText("Saved!\n");
                if(user.saveUserModelToJSON()){
                    traceView.getMainDisplay().appendText("Save to JSON file complete\n");
                }
                else {
                    traceView.getMainDisplay().appendText("Error while saving to JSON\n");
                }
            }
        });

        Button loadButton = new Button("Load");
        loadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    // load a json file to user model
                    user.loadUserModelFromJSON();
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
                    RFSpinner.getValueFactory().setValue(user.getRegulatoryFocus());

                    traceView.getMainDisplay().appendText("Successfully loaded profile: "+user.getFirstName()+"\n");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

            // add to gridPane
        saveLoadPane.addRow(0, saveButton, loadButton);

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
        resetDynamicProfile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dynamicProfileDisplay.setText("");
            }
        });

        // adding to gridpanes
        staticProfilePane.add(staticName, 0, 0, 2, 1);
        staticProfilePane.addColumn(0, name, age, sex, motivationLabel, PALabel, RFLabel, displayProfile);
        staticProfilePane.addColumn(1, nameField, ageSpinner, sexBox, motivationSpinner, PASpinner, RFSpinner, saveLoadPane);
        dynamicProfilePane.add(dynamicName, 0, 0);
        dynamicProfilePane.add(dynamicProfileDisplay, 0, 1);
        dynamicProfilePane.add(resetDynamicProfile, 0, 2);

        content.add(staticProfilePane, 0, 0);
        content.add(dynamicProfilePane, 1, 0);

        profilePane.setContent(content);
        this.getChildren().add(profilePane);

    }

    public TextArea getDynamicProfileDisplay() {
        return dynamicProfileDisplay;
    }

    public void setDynamicProfileDisplay(TextArea dynamicProfileDisplay) {
        this.dynamicProfileDisplay = dynamicProfileDisplay;
    }
}
