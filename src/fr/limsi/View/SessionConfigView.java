package fr.limsi.View;

//import fr.limsi.Model.AdaptationRules;
import fr.limsi.Model.Exercise;
import fr.limsi.Model.Programme;
import fr.limsi.Model.Session;
import fr.limsi.Model.Utils.Utils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;

public class SessionConfigView extends Parent {

    private TraceView traceView;
    //private AdaptationRules adaptationRules;
    private ArrayList<Integer> stepsRecord;
    private int dayCount;
    private boolean randomSessionConfig;
    private Session sessionToConfig;

    public SessionConfigView(Programme programme, TraceView trcView){

        traceView = trcView;
        sessionToConfig = new Session();
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
        exerciseContentPane.setVgap(7);

        Label nameLabel = new Label("Name");
        Label lengthLabel = new Label("Duration");
        Label distanceLabel = new Label("Distance");
        Text minText = new Text("minutes");
        //Text kmText = new Text("km");
        Text stepsText = new Text("steps");

        TextField nameField = new TextField();
        Spinner<Integer> durationSpinner = new Spinner<>(0, 120, 15);
        Spinner<Integer> distanceSpinner = new Spinner<>(1000, 15000, 3000);
        durationSpinner.setEditable(true);
        distanceSpinner.setEditable(true);
        nameField.setPrefWidth(150);

        // buttons
        FlowPane buttonsFlowPane = new FlowPane();
        buttonsFlowPane.setHgap(5);
        buttonsFlowPane.setVgap(5);
        //buttonsFlowPane.setPadding(new Insets(5));

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
            boolean loadExercises = programme.loadContentFromJSONFile(Exercise.class.getSimpleName());
            // 3. verbose on config trace
            if(loadExercises){
                traceView.getMainDisplay().appendText("Exercise List loaded and updated successfully.\n");
            }
            else{
                traceView.getMainDisplay().appendText("Error while loading Exercise List.\n");
            }
        });
        Button displayExercisesButton = new Button("Display Exercises");
        displayExercisesButton.setOnAction(event -> {
            // 1. display current content of exerciseListArray
            traceView.getMainDisplay().appendText(Utils.arrayListToString(programme.getExerciseArrayList()));
        });
        buttonsFlowPane.getChildren().addAll(addExerciseButton, resetExerciseListButton, saveExerciseListButton, loadExercisesButton, displayExercisesButton);

        exerciseContentPane.addRow(0, nameLabel, nameField);
        exerciseContentPane.addRow(1, lengthLabel, durationSpinner, minText);
        exerciseContentPane.addRow(2, distanceLabel, distanceSpinner, stepsText);
        exerciseContentPane.add(buttonsFlowPane, 0, 3, 3, 1);

        exercisePane.setContent(exerciseContentPane);


        // col 1, row 0: session TitledPane
        TitledPane sessionPane = new TitledPane();
        sessionPane.setText("Session Configuration");
        GridPane sessionContentPane = new GridPane();
        sessionContentPane.setHgap(5);
        sessionContentPane.setVgap(7);

        // inside sessionContentPane, row 0:
        Label randomLabel = new Label("Random");
        HBox randomBox = new HBox();
        randomBox.setSpacing(5);

        ToggleGroup randomGroup = new ToggleGroup();
        RadioButton randomYesRB = new RadioButton("Yes");
        randomYesRB.setToggleGroup(randomGroup);
        randomYesRB.setFocusTraversable(false);

        RadioButton randomNoRB = new RadioButton("No");
        randomNoRB.setToggleGroup(randomGroup);
        randomNoRB.setFocusTraversable(false);

        // adding to HBox
        randomBox.getChildren().addAll(randomYesRB, randomNoRB);

        // inside sessionContentPane, row 1: -- depends on what is selected on row 0 (yes or no)
        // case YES: exercises will be randomly selected
        Spinner<Integer> exNbSpinner = new Spinner<>(1,15,3);
        exNbSpinner.setVisible(false);
        // case NO: user will decide which exercises will be included in the session
        TextField exIDTextField = new TextField();
        exIDTextField.setVisible(false);
        Button addExToSessionButton = new Button("Add");
        addExToSessionButton.setOnAction(event -> {
            // search for exoID in programme.exoAL and add it to session.exoAL
            // check if already present in sessionToConfig.exoAL + verbose if yes
            // verbose if not found
            // verbose anyway if success
        });
        addExToSessionButton.setVisible(false);
        Button removeExFromSessionButton = new Button("Remove");
        removeExFromSessionButton.setOnAction(event -> {
            // search for exoID in sessionToConfig.exoAL and remove it + verbose
            // verbose if not found
            // del all occurrences + verbose if yes
        });
        removeExFromSessionButton.setVisible(false);

        // action of radio button random
        randomGroup.selectedToggleProperty().addListener((observableValue, oldValue, newValue) -> {
            if(newValue.equals(randomYesRB)){
                randomSessionConfig = true;
                // make spinner appear
                exNbSpinner.setVisible(true);

                exIDTextField.setVisible(false);
                addExToSessionButton.setVisible(false);
                removeExFromSessionButton.setVisible(false);
            }
            else{
                randomSessionConfig = false;

                exNbSpinner.setVisible(false);

                exIDTextField.setVisible(true);
                addExToSessionButton.setVisible(true);
                removeExFromSessionButton.setVisible(true);
            }
        });

        // row 2: button "save session" to conclude the configuration + other general buttons
        FlowPane sessionConfigFlowPane1 = new FlowPane();
        sessionConfigFlowPane1.setVgap(5);
        sessionConfigFlowPane1.setHgap(5);
       // sessionConfigFlowPane1.setPadding(new Insets(5));

        FlowPane sessionConfigFlowPane2 = new FlowPane();
        sessionConfigFlowPane2.setVgap(5);
        sessionConfigFlowPane2.setHgap(5);
        //sessionConfigFlowPane2.setPadding(new Insets(5));

        Button saveSessionButton = new Button("Save Session");
        saveSessionButton.setOnAction(event -> {
            boolean ok = true;
            if (randomSessionConfig){ // random session config
                // 1. get spinner number and randomly select N indexes in range 0 to size of prog.exoAL
                // 2. add each index to sessionToConfig.exoAL
                // 3. save verbose : "random mode selected" or equivalent
            }
            else { // manual session config
                if(sessionToConfig.getExerciseList().size() < 1){
                    ok = false;
                    // verbose for abort / ex missing / list empty
                }
                // save verbose : "manual mode selected" or equivalent
            }
            if(ok){
                // 3. save new Session object
                // 3.1. save session to prog.sessionAL
                // 3.2. save session to JSON META + JSON file
                // 4. verbose if session config success + display session details (toString)
            }
        });
        Button loadSessionButton = new Button("Load List");
        loadSessionButton.setOnAction((event -> {
            // open dialog to select file with session info
            // handle whole process in class Programme
        }));
        Button resetSessionListButton = new Button("Reset List");
        resetSessionListButton.setOnAction((event -> {
            // reset prog.sessionAL list
            programme.resetSessionArrayList();
            traceView.getMainDisplay().appendText("sessionArrayList reset.\n");
        }));
        Button newSessionButton = new Button("New Session");
        newSessionButton.setOnAction((event -> {
            // create new session object to be filled
            sessionToConfig = null;
            sessionToConfig = new Session();
            traceView.getMainDisplay().appendText("New empty session object created.\n");
        }));
        Button displaySessionsButton = new Button("Display List");
        displaySessionsButton.setOnAction((event -> {
            // 1. display current content of sessionListArray
            traceView.getMainDisplay().appendText(Utils.arrayListToString(programme.getSessionArrayList()));
        }));

        sessionConfigFlowPane1.getChildren().addAll(saveSessionButton, newSessionButton);
        sessionConfigFlowPane2.getChildren().addAll(loadSessionButton, resetSessionListButton, displaySessionsButton);

        // adding to gridPane sessionContentPane
        sessionContentPane.addRow(0, randomLabel, randomBox);
        sessionContentPane.addRow(1, exNbSpinner);
        sessionContentPane.addRow(1, exIDTextField, addExToSessionButton, removeExFromSessionButton);
        sessionContentPane.add(sessionConfigFlowPane1,0,2,4,1);
        sessionContentPane.add(sessionConfigFlowPane2,0,3,4,1);
        // adding to sessionPane
        sessionPane.setContent(sessionContentPane);


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
            dayCount = daysSpinner.getValueFactory().getValue();
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
