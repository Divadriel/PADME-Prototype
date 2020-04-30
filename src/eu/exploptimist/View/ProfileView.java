package eu.exploptimist.View;

import eu.exploptimist.Model.UserModel;
import eu.exploptimist.Model.Utils.Strings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ProfileView extends Parent {

    private UserModel user;
    private DisplayActions displayActions;
    private TextArea dynamicProfileDisplay;

    public ProfileView(UserModel usr, DisplayActions dspAct){

        user = usr;
        displayActions = dspAct;

        // creation and config of titled pane
        TitledPane profilePane = new TitledPane();
        profilePane.setText("Profile");
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
        //maleRB.setSelected(true);
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
        VBox motivBox = new VBox();
        //motivBox.setSpacing(10);
        ToggleGroup motivationGroup = new ToggleGroup();
        RadioButton amotivation = new RadioButton(Strings.AMOTIVATION);
        amotivation.setFocusTraversable(false);
        amotivation.setToggleGroup(motivationGroup);
        RadioButton controlledMotiv = new RadioButton(Strings.CONTROLLED_MOTIV);
        controlledMotiv.setFocusTraversable(false);
        controlledMotiv.setToggleGroup(motivationGroup);
        //controlledMotiv.setSelected(true);
        RadioButton autonomousMotiv = new RadioButton(Strings.AUTONOMOUS_MOTIV);
        autonomousMotiv.setFocusTraversable(false);
        autonomousMotiv.setToggleGroup(motivationGroup);

            // listener to motivationGroup
        motivationGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle oldValue, Toggle newValue) {
                if(newValue.equals(amotivation)){
                    user.setMotivationLevel(Strings.AMOTIVATION);
                }
                else if(newValue.equals(controlledMotiv)){
                    user.setMotivationLevel(Strings.CONTROLLED_MOTIV);
                }
                else {
                    user.setMotivationLevel(Strings.AUTONOMOUS_MOTIV);
                }
            }
        });
            // adding button to VBox
        motivBox.getChildren().addAll(amotivation, controlledMotiv, autonomousMotiv);

        // row 4, cell 0
        Label activLabel = new Label("Physical Activity");
        activLabel.setPadding(new Insets(5));
        // row 4, cell 1
        VBox activBox = new VBox();
        //activBox.setSpacing(10);
        ToggleGroup activGroup = new ToggleGroup();
        RadioButton notActive = new RadioButton(Strings.PA_NOT_ACTIVE);
        notActive.setToggleGroup(activGroup);
        notActive.setFocusTraversable(false);
        RadioButton active = new RadioButton(Strings.PA_ACTIVE);
        active.setToggleGroup(activGroup);
        active.setFocusTraversable(false);
       // active.setSelected(true);
        RadioButton veryActive = new RadioButton(Strings.PA_VERY_ACTIVE);
        veryActive.setToggleGroup(activGroup);
        veryActive.setFocusTraversable(false);

            // listener to activGroup
        activGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle oldValue, Toggle newValue) {

                if(newValue.equals(notActive)){
                    user.setPhysicalActivityLevel(Strings.PA_NOT_ACTIVE);
                }
                else if(newValue.equals(active)){
                    user.setPhysicalActivityLevel(Strings.PA_ACTIVE);
                }
                else{
                    user.setPhysicalActivityLevel(Strings.PA_VERY_ACTIVE);
                }

            }
        });
            // adding to VBox
        activBox.getChildren().addAll(notActive, active, veryActive);

        // row 5, cell 0
        Label focusLabel = new Label("Chronic focus");
        focusLabel.setPadding(new Insets(5));
        // row 5, cell 1
        VBox focusBox = new VBox();
        //focusBox.setSpacing(10);
        ToggleGroup focusGroup = new ToggleGroup();
        RadioButton promotion = new RadioButton(Strings.PROMOTION);
        promotion.setToggleGroup(focusGroup);
        promotion.setFocusTraversable(false);
        //promotion.setSelected(true);
        RadioButton prevention = new RadioButton(Strings.PREVENTION);
        prevention.setToggleGroup(focusGroup);
        prevention.setFocusTraversable(false);

            // listener to focusGroup
        focusGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle oldValue, Toggle newValue) {
                if(newValue.equals(promotion)){
                    user.setRegulatoryFocus(Strings.PROMOTION);
                }
                else{
                    user.setRegulatoryFocus(Strings.PREVENTION);
                }
            }
        });
            // adding to VBox
        focusBox.getChildren().addAll(promotion, prevention);

        // row 6, cell 0
        Button displayProfile = new Button("Display Profile");
        displayProfile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                displayActions.getMainDisplay().appendText(user.displayProfile());
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
                displayActions.getMainDisplay().appendText("Saved!\n");
                if(user.saveUserModelToJSON()){
                    displayActions.getMainDisplay().appendText("Save to JSON file complete\n");
                }
                else {
                    displayActions.getMainDisplay().appendText("Error while saving to JSON\n");
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
                    switch (user.getMotivationLevel()){
                        case Strings.AMOTIVATION:
                            amotivation.setSelected(true);
                            break;
                        case Strings.AUTONOMOUS_MOTIV:
                            autonomousMotiv.setSelected(true);
                            break;
                        case Strings.CONTROLLED_MOTIV:
                            controlledMotiv.setSelected(true);
                            break;
                    }
                    switch (user.getPhysicalActivityLevel()){
                        case Strings.PA_NOT_ACTIVE:
                            notActive.setSelected(true);
                            break;
                        case Strings.PA_ACTIVE:
                            active.setSelected(true);
                            break;
                        case Strings.PA_VERY_ACTIVE:
                            veryActive.setSelected(true);
                            break;
                    }
                    if(user.getRegulatoryFocus().equals(Strings.PROMOTION)){
                        promotion.setSelected(true);
                    }
                    else {
                        prevention.setSelected(true);
                    }
                    displayActions.getMainDisplay().appendText("Successfully loaded profile: "+user.getFirstName()+"\n");

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
        dynamicProfileDisplay.appendText(Strings.APP_TITLE + Strings.APP_VERSION + "\n");
        dynamicProfileDisplay.appendText("Dynamic Profile \n");
        dynamicProfileDisplay.setPrefRowCount(15);
        dynamicProfileDisplay.setPrefColumnCount(15);

        // row 1: reset button
        Button resetDynamicProfile = new Button("Reset");
        resetDynamicProfile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dynamicProfileDisplay.setText(Strings.APP_TITLE + Strings.APP_VERSION + "\nDynamic Profile \n");
            }
        });

        // adding to gridpanes
        staticProfilePane.addColumn(0, name, age, sex, motivationLabel, activLabel, focusLabel, displayProfile);
        staticProfilePane.addColumn(1, nameField, ageSpinner, sexBox, motivBox, activBox, focusBox, saveLoadPane);
        dynamicProfilePane.add(dynamicProfileDisplay, 0, 0);
        dynamicProfilePane.add(resetDynamicProfile, 0, 1);

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
