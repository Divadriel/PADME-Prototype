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
        sessionToConfig.setUserID(programme.getUser().getUserID());
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
        Spinner<Integer> distanceSpinner = new Spinner<>(0, 15000, 3000);
        durationSpinner.setEditable(true);
        distanceSpinner.setEditable(true);
        Utils.commitSpinnerValueOnLostFocus(durationSpinner);
        Utils.commitSpinnerValueOnLostFocus(distanceSpinner);
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
        Label randomLabel = new Label("Random Exercises");
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
        Utils.commitSpinnerValueOnLostFocus(exNbSpinner);

        // case NO: user will decide which exercises will be included in the session
        TextField exIDTextField = new TextField();
        exIDTextField.setVisible(false);
        Button addExToSessionButton = new Button("Add");
        addExToSessionButton.setVisible(false);
        addExToSessionButton.setOnAction(event -> {
            // get text field value
            long exID = Long.parseLong(exIDTextField.getText()); // long
            // search for exoID in programme.exoAL and add it to session.exoAL
            Exercise exercise = Utils.findExercise(exID, programme.getExerciseArrayList());
            if(exercise == null){ // not found
                traceView.getMainDisplay().appendText("Exercise ID " + exID + " not found in Programme Exercise List.\n");
            }
            else{
                sessionToConfig.getExerciseList().add(exercise);
                traceView.getMainDisplay().appendText("Exercise with ID " + exID + " successfully added to Session Exercise List.\n");
            }
        });
        Button removeExFromSessionButton = new Button("Remove");
        removeExFromSessionButton.setVisible(false);
        removeExFromSessionButton.setOnAction(event -> {
            // get text field value
            long exID = Long.parseLong(exIDTextField.getText()); // long
            // search for exoID in sessionToConfig.exoAL and remove it
            Exercise exercise = Utils.findExercise(exID, sessionToConfig.getExerciseList());
            if(exercise == null){ // not found
                traceView.getMainDisplay().appendText("Exercise ID " + exID + " not found in Session Exercise List.\n");
            }
            else{
                sessionToConfig.getExerciseList().remove(exercise);
                traceView.getMainDisplay().appendText("Exercise with ID " + exID + " successfully removed from Session Exercise List.\n");
            }
        });

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

        // row 2: buttons: save session, new session, display current session
        FlowPane sessionConfigFlowPane1 = new FlowPane();
        sessionConfigFlowPane1.setVgap(5);
        sessionConfigFlowPane1.setHgap(5);

        // row 3: buttons on session list: load, reset, display
        FlowPane sessionConfigFlowPane2 = new FlowPane();
        sessionConfigFlowPane2.setVgap(5);
        sessionConfigFlowPane2.setHgap(5);

        Button saveSessionButton = new Button("Save Session");
        saveSessionButton.setOnAction(event -> {
            boolean ok = true; // sessionToConfig exAL has at least 1 exercise
            if (randomSessionConfig){ // random session config

                // 3. save verbose : "random mode selected"
                traceView.getMainDisplay().appendText("Random Mode Selected.\n");

                int randNb = exNbSpinner.getValueFactory().getValue();
                for (int i = 0; i < randNb; i++){
                    Exercise randEx = Utils.getRandomExercise(programme.getExerciseArrayList());
                    sessionToConfig.getExerciseList().add(randEx);
                    traceView.getMainDisplay().appendText("Exercise " + randEx.getName() + " added to Session Exercise List.\n");
                }
                traceView.getMainDisplay().appendText("Exercise random selection complete.\n");
            }
            else { // manual session config
                if(sessionToConfig.getExerciseList().size() < 1){
                    ok = false;
                    // verbose for empty list
                    traceView.getMainDisplay().appendText("Session Exercise List is empty.\nPlease add at least one exercise.\n");
                }
                else{
                    // save verbose : "manual mode selected"
                    traceView.getMainDisplay().appendText("Manual Mode Selected.\n");
                }
            }
            if(ok){
                // save or update session to programme.sessionAL according to sessionID
                Session session = Utils.findSession(sessionToConfig.getSessionID(), programme.getSessionArrayList());
                if(!(session == null)){
                    programme.getSessionArrayList().remove(sessionToConfig);
                }
                programme.getSessionArrayList().add(sessionToConfig);
                // save session to user JSON META + JSON file
                // 1. save in JSON file
                try {
                    programme.saveSessionArrayListToJSON(programme.getUser().getUserID());
                } catch (IOException e) {
                    e.printStackTrace();
                    traceView.getMainDisplay().appendText("Error while saving sessionArrayList to JSON.\n");
                    return;
                }
                // 2. verbose on config trace + display session details (toString)
                traceView.getMainDisplay().appendText("sessionArrayList saved to JSON.\n");
                //traceView.getMainDisplay().appendText(sessionToConfig.toString());
            }
        });
        Button loadSessionButton = new Button("Load S List");
        loadSessionButton.setOnAction((event -> {
            // open dialog to select file with session info
            // handle whole process in class Programme
        }));
        Button resetSessionListButton = new Button("Reset S List");
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
            sessionToConfig.setUserID(programme.getUser().getUserID());
            traceView.getMainDisplay().appendText("New empty session object created.\n");
        }));
        Button displaySessionsButton = new Button("Display S List");
        displaySessionsButton.setOnAction((event -> traceView.getMainDisplay().appendText(Utils.arrayListToString(programme.getSessionArrayList()))));

        Button displayCurrentSessionButton = new Button("Display current session");
        displayCurrentSessionButton.setOnAction((event -> traceView.getMainDisplay().appendText(sessionToConfig.toString())));

        // row 4: load a session from session list using its ID
        FlowPane sessionConfigFlowPane3 = new FlowPane();
       // sessionConfigFlowPane3.setVgap(5);
        sessionConfigFlowPane3.setHgap(5);

        Label loadSessionLabel = new Label("Load Session");
        TextField loadSessionTF = new TextField();
        loadSessionTF.setPrefWidth(120);
        Button loadIDSessionButton = new Button("Load with ID");
        loadIDSessionButton.setOnAction(event -> {
            // get text field value
            long sessionID = Long.parseLong(loadSessionTF.getText()); // long

            Session session = Utils.findSession(sessionID, programme.getSessionArrayList());
            if(session == null){ // not found
                traceView.getMainDisplay().appendText("Session ID " + sessionID + " not found in Programme Session List.\n");
            }
            else{
                sessionToConfig = null;
                sessionToConfig = session;
                traceView.getMainDisplay().appendText("Session with ID " + sessionID + " successfully loaded.\n");
            }
        });
        Button loadRandSession = new Button("Load Random");
        loadRandSession.setDisable(true);
        loadRandSession.setOnAction(event -> {
            sessionToConfig = null;
            sessionToConfig = Utils.getRandomSession(programme.getSessionArrayList());
            traceView.getMainDisplay().appendText("Session with ID " + sessionToConfig.getSessionID() + " randomly loaded.\n");
        });


        sessionConfigFlowPane1.getChildren().addAll(saveSessionButton, newSessionButton, displayCurrentSessionButton);
        sessionConfigFlowPane2.getChildren().addAll(loadSessionButton, resetSessionListButton, displaySessionsButton);
        sessionConfigFlowPane3.getChildren().addAll(loadSessionLabel, loadSessionTF, loadIDSessionButton, loadRandSession);

        // adding to gridPane sessionContentPane
        sessionContentPane.addRow(0, randomLabel, randomBox);
        sessionContentPane.addRow(1, exNbSpinner, exIDTextField, addExToSessionButton, removeExFromSessionButton);
        sessionContentPane.add(sessionConfigFlowPane1,0,2,4,1);
        sessionContentPane.add(sessionConfigFlowPane2,0,3,4,1);
        sessionContentPane.add(sessionConfigFlowPane3, 0, 4, 4, 1);
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
        Utils.commitSpinnerValueOnLostFocus(percentileSpinner);
        Spinner<Integer> daysSpinner = new Spinner<>(1, 100, 9);
        daysSpinner.setEditable(true);
        daysSpinner.setPrefWidth(60);
        daysSpinner.setTooltip(new Tooltip("Moving days"));
        Utils.commitSpinnerValueOnLostFocus(daysSpinner);
        Spinner<Integer> verboseSpinner = new Spinner<>(0, 1, 0);
        verboseSpinner.setPrefWidth(60);
        verboseSpinner.setTooltip(new Tooltip("1 if you want to display more info"));
        Utils.commitSpinnerValueOnLostFocus(verboseSpinner);
        Spinner<Integer> correctionSpinner = new Spinner<>(0,1,1);
        correctionSpinner.setPrefWidth(60);
        correctionSpinner.setTooltip(new Tooltip("1 if you want to apply correction"));
        Utils.commitSpinnerValueOnLostFocus(correctionSpinner);
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
