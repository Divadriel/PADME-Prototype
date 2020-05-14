package fr.limsi.View;

import fr.limsi.Model.Exercise;
import fr.limsi.Model.Session;
import fr.limsi.Model.UserModel;
import fr.limsi.Model.Utils.Strings;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class SimulationView extends Parent {

    private UserModel user;
    private TraceView traceView;
    private Session sessionOne;
    private Session sessionTwo;

    public SimulationView(UserModel usr, TraceView trcView){

        user = usr;
        traceView = trcView;

        // creation and config of titled pane
        TitledPane simulationPane = new TitledPane();
        simulationPane.setText("Simulation");
        simulationPane.setCollapsible(false);
        simulationPane.setAnimated(false);

        // content placed in gridpane
        GridPane content = new GridPane();
        content.setHgap(10);
        content.setVgap(10);
        content.setPrefWidth(700);

        // col 0: session configuration column
        // row 0
        Label configLabel = new Label("Session Configuration");
        configLabel.setPadding(new Insets(5));
        // row 1
        TitledPane exerciseOne = new TitledPane();
        TextField exOneNameField = new TextField();
        Spinner<Integer> exOneLengthSpinner = new Spinner<>(0, 120, 10);
        Spinner<Integer> exOneDistanceSpinner = new Spinner<>(0, 10, 1);
        generateExerciseConfigTitledPane(exerciseOne, exOneNameField,exOneLengthSpinner, exOneDistanceSpinner, 1);
        // row 2
        TitledPane exerciseTwo = new TitledPane();
        TextField exTwoNameField = new TextField();
        Spinner<Integer> exTwoLengthSpinner = new Spinner<>(0, 120, 10);
        Spinner<Integer> exTwoDistanceSpinner = new Spinner<>(0, 10, 1);
        generateExerciseConfigTitledPane(exerciseTwo, exTwoNameField, exTwoLengthSpinner, exTwoDistanceSpinner, 2);
        // row 3
        TitledPane exerciseThree = new TitledPane();
        TextField exThreeNameField = new TextField();
        Spinner<Integer> exThreeLengthSpinner = new Spinner<>(0, 120, 10);
        Spinner<Integer> exThreeDistanceSpinner = new Spinner<>(0, 10, 1);
        generateExerciseConfigTitledPane(exerciseThree, exThreeNameField, exThreeLengthSpinner, exThreeDistanceSpinner, 3);
        // row 4
        FlowPane saveStartFlowPane = new FlowPane();
        saveStartFlowPane.setHgap(5);
        saveStartFlowPane.setVgap(5);
        saveStartFlowPane.setPadding(new Insets(5));

        Button saveSessionButton = new Button("Save Session");
        Button startSessionButton = new Button("Start Session");
        // handler for save session button
        saveSessionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // 1. create new session object, with all 3 exercises + verbose on display
                Exercise exOne = createExercise(exOneNameField, exOneLengthSpinner, exOneDistanceSpinner);
                Exercise exTwo = createExercise(exTwoNameField, exTwoLengthSpinner, exTwoDistanceSpinner);
                Exercise exThree = createExercise(exThreeNameField, exThreeLengthSpinner, exThreeDistanceSpinner);
                sessionOne = createSession(exOne, exTwo, exThree);
                // 2. make "start session" clickable
                startSessionButton.setDisable(false);
            }
        });
        // handler for start session button
        startSessionButton.setDisable(true);
        startSessionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // action to do
            }
        });
        // adding to flowpane
        saveStartFlowPane.getChildren().addAll(saveSessionButton, startSessionButton);

        // col 1: progress through the session
        // row 0
        Label progressLabel = new Label("Session Progress");
        progressLabel.setPadding(new Insets(5));
        // row 1
        TitledPane exOneProgress = generateExerciseProgressTitledPane(1);
        // row 2
        TitledPane exTwoProgress = generateExerciseProgressTitledPane(2);
        // row 3
        TitledPane exThreeProgress = generateExerciseProgressTitledPane(3);
        // row 4
        FlowPane feedbackPane = new FlowPane();
        feedbackPane.setVgap(5);
        feedbackPane.setHgap(5);
        feedbackPane.setPadding(new Insets(5));

        Label feedbackLabel = new Label("Session User Feedback");
        Spinner<Integer> feedbackSpinner = new Spinner<>(1, 5, 3);

        feedbackPane.getChildren().addAll(feedbackLabel, feedbackSpinner);
        // row 5
        FlowPane endResetFlowPane = new FlowPane();
        endResetFlowPane.setHgap(5);
        endResetFlowPane.setVgap(5);
        endResetFlowPane.setPadding(new Insets(5));

        Button endSession = new Button("End Session");
        endSession.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // 1.
            }
        });
        Button resetSimulationButton = new Button("Reset Simulation");
        resetSimulationButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // 1. reset display
                // 2. delete session and exercise objects (null assignation)
            }
        });
        endResetFlowPane.getChildren().addAll(endSession, resetSimulationButton);

        // adding to gridpane and positioning
        content.addColumn(0, configLabel, exerciseOne, exerciseTwo, exerciseThree, saveStartFlowPane);
        content.addColumn(1, progressLabel, exOneProgress, exTwoProgress, exThreeProgress, feedbackPane, endResetFlowPane);

        simulationPane.setContent(content);
        this.getChildren().add(simulationPane);

    }

    private void generateExerciseConfigTitledPane(TitledPane pane, TextField nameField, Spinner<Integer> length, Spinner<Integer> distance, int exerciseNb){

        pane.setText("Exercise " + exerciseNb);
        GridPane gridpane = new GridPane();
        gridpane.setHgap(10);
        gridpane.setVgap(10);

        Label nameLabel = new Label("Name");
        Label lengthLabel = new Label("Length");
        Label distanceLabel = new Label("Distance");
        Text minText = new Text("minutes");
        Text kmText = new Text("km");

        length.setEditable(true);
        distance.setEditable(true);
        nameField.setPrefWidth(150);

        gridpane.addRow(0, nameLabel, nameField);
        gridpane.addRow(1, lengthLabel, length, minText);
        gridpane.addRow(2, distanceLabel, distance, kmText);

        pane.setContent(gridpane);

    }

    private TitledPane generateExerciseProgressTitledPane(int exerciseNb){
        TitledPane pane  = new TitledPane();
        pane.setText("Exercise " + exerciseNb);
        FlowPane flowpane = new FlowPane();
        flowpane.setHgap(10);
        flowpane.setVgap(10);
        flowpane.setPadding(new Insets(10));

        // button beginning
        Button beginningButton = new Button("Beginning");
        beginningButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // 1. displays image of exercise beginning in a dialog
                // 2. verbose on display
                if(user.getRegulatoryFocus() <= 50){ // PROMOTION
                    traceView.getMainDisplay().appendText(Strings.PROM_ASCII_EX_BEG);
                }
                else{
                    traceView.getMainDisplay().appendText(Strings.PREV_ASCII_EX_BEG);
                }

            }
        });

        // button mid
        Button midButton = new Button("Mid");
        midButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // 1. displays image of exercise midway in a dialog
                // 2. verbose on display
                if(user.getRegulatoryFocus() <= 50){ // PROMOTION
                    traceView.getMainDisplay().appendText(Strings.PROM_ASCII_EX_MID);
                }
                else{
                    traceView.getMainDisplay().appendText(Strings.PREV_ASCII_EX_MID);
                }
            }
        });

        // button end
        Button endButton = new Button("End");
        endButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // 1. displays image of exercise end in a dialog
                // 2. verbose on display
                if(user.getRegulatoryFocus() <= 50){ // PROMOTION
                    traceView.getMainDisplay().appendText(Strings.PROM_ASCII_EX_END);
                }
                else{
                    traceView.getMainDisplay().appendText(Strings.PREV_ASCII_EX_END);
                }

            }
        });

        // button not finished
        Button notFinishedButton = new Button("Not Completed");
        notFinishedButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // 1. displays not finished message
                // 2. verbose on display
            }
        });

        // adding to panes
        flowpane.getChildren().addAll(beginningButton, midButton, endButton, notFinishedButton);
        pane.setContent(flowpane);
        return pane;
    }

    private Exercise createExercise(TextField nameField, Spinner<Integer> lengthSpinner, Spinner<Integer> distanceSpinner){
        Exercise exercise = new Exercise(nameField.getText(), lengthSpinner.getValue(), distanceSpinner.getValue());
        traceView.getMainDisplay().appendText(exercise.toString());
        return exercise;
    }

    private Session createSession(Exercise exOne, Exercise exTwo, Exercise exThree){
        // create session
        List<Exercise> list = new ArrayList<Exercise>();
        list.add(exOne);
        list.add(exTwo);
        list.add(exThree);
        Session session = new Session(list, user, 3);
        traceView.getMainDisplay().appendText(session.toString());
        return session;
    }
}
