package fr.limsi.View;

//import fr.limsi.Model.AdaptationRules;
import fr.limsi.Model.Exercise;
import fr.limsi.Model.Programme;
import fr.limsi.Model.Utils.Utils;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;

public class SessionConfigView extends Parent {

    private TraceView traceView;
    //private AdaptationRules adaptationRules;
    private ArrayList<Integer> stepsRecord;
    private int dayCount;

    public SessionConfigView(Programme programme, TraceView trcView){

        traceView = trcView;
        //adaptationRules = programme.getAdaptationRules();

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

        // col 0, row 0: exercise TitledPane
        TitledPane exercisePane = new TitledPane();
        exercisePane.setText("Exercise Configuration");
        GridPane exerciseContentPane = new GridPane();
        exerciseContentPane.setHgap(5);
        exerciseContentPane.setVgap(5);

        Label nameLabel = new Label("Name");
        Label lengthLabel = new Label("Duration");
        Label distanceLabel = new Label("Distance");
        Text minText = new Text("minutes");
        Text kmText = new Text("km");

        TextField nameField = new TextField();
        Spinner<Integer> durationSpinner = new Spinner<>(0, 120, 10);
        Spinner<Integer> distanceSpinner = new Spinner<>(0, 10, 1);
        durationSpinner.setEditable(true);
        distanceSpinner.setEditable(true);
        nameField.setPrefWidth(150);

        // buttons
        FlowPane buttonsFlowPane = new FlowPane();
        buttonsFlowPane.setHgap(5);
        buttonsFlowPane.setVgap(5);
        buttonsFlowPane.setPadding(new Insets(5));

        Button addExerciseButton = new Button("Add");
        addExerciseButton.setOnAction(event -> {
            // 1. create Exercise object with current fields values and add it to exercise ListArray
            Exercise exercise = new Exercise(nameField.getText(),durationSpinner.getValue(), distanceSpinner.getValue());
            programme.getExerciseArrayList().add(exercise);
            // 2. verbose on config trace
            traceView.getMainDisplay().appendText("Exercise "+ nameField.getText() +" added to exerciseArrayList.\n");
            // -- NO CHANGE IN JSON --
        });
        Button resetExerciseListButton = new Button("Reset List");
        resetExerciseListButton.setOnAction(event -> {
            // 1. reset Exercise ArrayList (null assignment then new object creation)
            programme.resetExerciseArrayList();
            // 2. verbose on config trace
            traceView.getMainDisplay().appendText("exerciseArrayList reset.\n");
            // -- NO CHANGE IN JSON --
        });
        Button saveExerciseListButton = new Button("Save");
        saveExerciseListButton.setOnAction(event -> {
            // 1. save in JSON file
            try {
                programme.saveExerciseArrayListToJSON(programme.getUser().getUserID());
            } catch (IOException e) {
                e.printStackTrace();
                traceView.getMainDisplay().appendText("Error while saving exerciseArrayList to JSON.\n");
                return;
            }
            // 2. verbose on config trace
            traceView.getMainDisplay().appendText("exerciseArrayList saved to JSON.\n");
        });
        Button loadExercisesButton = new Button("Load");
        loadExercisesButton.setOnAction(event -> {
            // 1. open fileChooser to choose a file
            // 2. fill ListArray with exercises from file
            // 3. verbose on config trace
        });
        Button displayExercisesButton = new Button("Display Exercises");
        displayExercisesButton.setOnAction(event -> {
            // 1. display current content of exerciseListArray
            traceView.getMainDisplay().appendText(Utils.arrayListToString(programme.getExerciseArrayList()));
        });
        buttonsFlowPane.getChildren().addAll(addExerciseButton, resetExerciseListButton, saveExerciseListButton, loadExercisesButton, displayExercisesButton);

        exerciseContentPane.addRow(0, nameLabel, nameField);
        exerciseContentPane.addRow(1, lengthLabel, durationSpinner, minText);
        exerciseContentPane.addRow(2, distanceLabel, distanceSpinner, kmText);
        exerciseContentPane.add(buttonsFlowPane, 0, 3, 3, 1);

        exercisePane.setContent(exerciseContentPane);


        // col 1, row 0: session TitledPane
        TitledPane sessionPane = new TitledPane();
        sessionPane.setText("Session Configuration");
        GridPane sessionContentPane = new GridPane();
        sessionContentPane.setHgap(5);
        sessionContentPane.setVgap(5);



        // col 0 and 1, full row 1: adaptation rules TitledPane
        TitledPane adaptRulesPane = new TitledPane();
        adaptRulesPane.setText("Adaptation Rules Configuration");
        GridPane adaptRulesContentPane = new GridPane();
        adaptRulesContentPane.setHgap(5);
        adaptRulesContentPane.setVgap(5);

        // content of adaptation rules titled pane
            // row 0: duration rule // DISABLED
        Label exerciseDurationRule = new Label("Exercise duration rule");
        TextField exerciseDurationRuleField = new TextField("0");
        Button exerciseDurationRuleButton = new Button("Apply");
        exerciseDurationRuleButton.setDisable(true);
            // row 1: distance rule // DISABLED
        Label exerciseDistanceRule = new Label("Exercise distance rule");
        TextField exerciseDistanceRuleField = new TextField("0");
        Button exerciseDistanceRuleButton = new Button("Apply");
        exerciseDistanceRuleButton.setDisable(true);

        exerciseDurationRuleButton.setOnAction(event -> {
            // later
        });
        exerciseDistanceRuleButton.setOnAction(event -> {
            // later
        });

            // row 2: PERCENTILE ALGORITHM
        Label percentileAlgoLabel = new Label("Percentile Algorithm");

        FlowPane percentileFlowpane = new FlowPane();
        percentileFlowpane.setHgap(5);
        percentileFlowpane.setVgap(5);
        percentileFlowpane.setPadding(new Insets(5));

        Spinner<Integer> percentileSpinner = new Spinner<>(1, 100, 60);
        percentileSpinner.setEditable(true);
        percentileSpinner.setPrefWidth(60);
        percentileSpinner.setTooltip(new Tooltip("Percentile"));
        Spinner<Integer> daysSpinner = new Spinner<>(1, 100, 9);
        daysSpinner.setEditable(true);
        daysSpinner.setPrefWidth(60);
        daysSpinner.setTooltip(new Tooltip("Moving days"));
        Spinner<Integer> verboseSpinner = new Spinner<>(0, 1, 0);
        verboseSpinner.setPrefWidth(60);
        verboseSpinner.setTooltip(new Tooltip("1 if you want to display more info"));
        Spinner<Integer> correctionSpinner = new Spinner<>(0,1,1);
        correctionSpinner.setPrefWidth(60);
        correctionSpinner.setTooltip(new Tooltip("1 if you want to apply correction"));
        Button percentileAlgoButton = new Button("Iterate");
        Button initPercentileAlgo = new Button("Init");
        initPercentileAlgo.setTooltip(new Tooltip("Initial set of day records"));
        percentileAlgoButton.setOnAction(event -> {
            boolean verbose;
            boolean correction;
            verbose = !verboseSpinner.getValueFactory().getValue().equals(0);
            correction = !correctionSpinner.getValueFactory().getValue().equals(0);
            dayCount++;
            traceView.getMainDisplay().appendText(Utils.nthPercentileAlgorithmDisplay(
                    percentileSpinner.getValueFactory().getValue(),
                    stepsRecord,
                    dayCount,
                    correction,
                    verbose
            ));

        });
        initPercentileAlgo.setOnAction(event -> {
            stepsRecord = Utils.generateRandomStepsRecord(daysSpinner.getValueFactory().getValue()); // init: returns a random set of steps, of size "days"
            traceView.getMainDisplay().appendText("Initial set of records:\n" + Utils.arrayListToString(stepsRecord));
            dayCount = 0;
        });

        percentileFlowpane.getChildren().addAll(percentileSpinner, daysSpinner, correctionSpinner, verboseSpinner, initPercentileAlgo, percentileAlgoButton);
        adaptRulesContentPane.addRow(0, exerciseDurationRule, exerciseDurationRuleField, exerciseDurationRuleButton);
        adaptRulesContentPane.addRow(1, exerciseDistanceRule, exerciseDistanceRuleField, exerciseDistanceRuleButton);
        adaptRulesContentPane.add(percentileAlgoLabel, 0, 2);
        adaptRulesContentPane.add(percentileFlowpane, 1, 2, 2, 1);
        adaptRulesPane.setContent(adaptRulesContentPane);

        // add to content GridPane
        content.addColumn(0, exercisePane);
        content.addColumn(1, sessionPane);
        content.add(adaptRulesPane, 0, 1, 2, 1);
        // add to global TitledPane
        sessionConfigPane.setContent(content);
        this.getChildren().add(sessionConfigPane);
    }
}
