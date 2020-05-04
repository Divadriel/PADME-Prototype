package fr.limsi.View;

import fr.limsi.Model.UserModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

public class SessionConfigView extends Parent {

    private UserModel user;
    private TraceView traceView;

    public SessionConfigView(UserModel usr, TraceView trcView){

        user = usr;
        traceView = trcView;

        // creation and config of titled pane
        TitledPane sessionConfigPane = new TitledPane();
        sessionConfigPane.setText("Session Configuration");
        sessionConfigPane.setCollapsible(false);
        sessionConfigPane.setAnimated(false);

        // content placed in gridpane
        GridPane content = new GridPane();
        content.setHgap(5);
        content.setVgap(5);
        content.setPadding(new Insets(5));

        // col 0: exercise TitledPane
        TitledPane exercisePane = new TitledPane();
        exercisePane.setText("Exercise Configuration");
        GridPane exerciseContentPane = new GridPane();
        exerciseContentPane.setHgap(5);
        exerciseContentPane.setVgap(5);

        Label nameLabel = new Label("Name");
        Label lengthLabel = new Label("Length");
        Label distanceLabel = new Label("Distance");
        Text minText = new Text("minutes");
        Text kmText = new Text("km");

        TextField nameField = new TextField();
        Spinner<Integer> lengthSpinner = new Spinner<>(0, 120, 10);
        Spinner<Integer> distanceSpinner = new Spinner<>(0, 10, 1);
        lengthSpinner.setEditable(true);
        distanceSpinner.setEditable(true);
        nameField.setPrefWidth(150);

        // buttons
        FlowPane buttonsFlowPane = new FlowPane();
        buttonsFlowPane.setHgap(5);
        buttonsFlowPane.setVgap(5);
        buttonsFlowPane.setPadding(new Insets(5));

        Button addExerciseButton = new Button("Add");
        addExerciseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // 1. create Exercise object with current fields values and add it to exercise ListArray
                // 2. verbose on config trace
                // -- NO CHANGE IN JSON --
            }
        });
        Button removeExerciseButton = new Button("Remove");
        removeExerciseButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // 1. remove exercise from ListArray with INDEX as argument
                // 2. verbose on config trace
                // -- NO CHANGE IN JSON --
            }
        });
        Button saveExercisesButton = new Button("Save");
        saveExercisesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // 1. save in JSON file
                // 2. verbose on config trace
            }
        });
        Button loadExercisesButton = new Button("Load");
        loadExercisesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // 1. open fileChooser to choose a file exos.json
                // 2. fill ListArray with exercises from file
                // 3. verbose on config trace -- verbose needs to show INDEX in ListArray of each exercise
            }
        });
        Button displayExercisesButton = new Button("Display Exercises");
        displayExercisesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // 1. display current content of exercises ListArray with INDEX for each exercises (for remove purposes)
            }
        });
        buttonsFlowPane.getChildren().addAll(addExerciseButton, removeExerciseButton, saveExercisesButton, loadExercisesButton, displayExercisesButton);

        exerciseContentPane.addRow(0, nameLabel, nameField);
        exerciseContentPane.addRow(1, lengthLabel, lengthSpinner, minText);
        exerciseContentPane.addRow(2, distanceLabel, distanceSpinner, kmText);
        exerciseContentPane.add(buttonsFlowPane, 0, 3, 3, 1);

        exercisePane.setContent(exerciseContentPane);

        // col 1: session TitledPane


        // add to content GridPane
        content.addColumn(0, exercisePane);

        // add to global TitledPane
        sessionConfigPane.setContent(content);
        this.getChildren().add(sessionConfigPane);
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public TraceView getTraceView() {
        return traceView;
    }

    public void setTraceView(TraceView traceView) {
        this.traceView = traceView;
    }
}
