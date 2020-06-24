package fr.limsi.View;

import fr.limsi.Model.Programme;
import fr.limsi.Model.Session;
import fr.limsi.Model.Utils.Utils;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.concurrent.ThreadLocalRandom;

public class SimulationView extends Parent {

    //private UserModel user;
    private Programme programme;
    private TraceView staticProfileTrace;
    private TraceView dynamicProfileTrace;
    private TraceView simulationTrace;
    private boolean autoMode;
    private boolean toggleAR;

    // buttons
    private Button beginning;
    private Button middle;
    private Button end;
    private Button notCompleted;
    private Button launchSimulation;
    private Button abortSimuButton;
    private Button endSimuButton;
    private Button updateProfileButton;

    private Label exerciseName;

    public SimulationView(Programme prog){

        programme = prog;
        staticProfileTrace = new TraceView("Static Profile", false, 15, 15);
        dynamicProfileTrace = new TraceView("Dynamic Profile", false, 15, 15);
        simulationTrace = new TraceView("Simulation Trace", true, 20,25);

        /* ----------------------------------------------------------
        ---------------- UI CONFIGURATION ---------------------------
        ---------------------------------------------------------- */

        // GridPane around everything
        GridPane mainGrid = new GridPane();
        mainGrid.setVgap(5);
        mainGrid.setHgap(5);
        mainGrid.setPadding(new Insets(5));

        // first TitledPane = start pane: display user, select session, activate or not Adaptation Rules
        TitledPane startPane = new TitledPane();
        startPane.setText("Start");
        startPane.setCollapsible(false);
        startPane.setAnimated(false);

        // startPane content
        GridPane startGrid = new GridPane();
        startGrid.setHgap(5);

        Button displayUserProfile = new Button("Display Profile");
        Separator vertSep1 = new Separator();
        vertSep1.setOrientation(Orientation.VERTICAL);
        Label loadSessionLabel = new Label("Load Session");
        TextField loadSessionTF = new TextField();
        loadSessionTF.setPrefWidth(120);
        Button loadIDSessionButton = new Button("Load");

        Separator vertSep2 = new Separator();
        vertSep2.setOrientation(Orientation.VERTICAL);

        Label toggleARLabel = new Label("Adaptation Rules");
        HBox toggleARBox = new HBox();
        toggleARBox.setSpacing(5);
        toggleARBox.setAlignment(Pos.CENTER);

        ToggleGroup toggleARGroup = new ToggleGroup();
        RadioButton ARYes = new RadioButton("Yes");
        ARYes.setToggleGroup(toggleARGroup);
        ARYes.setFocusTraversable(false);

        RadioButton ARNo = new RadioButton("No");
        ARNo.setToggleGroup(toggleARGroup);
        ARNo.setFocusTraversable(false);

        // adding to HBox
        toggleARBox.getChildren().addAll(ARYes, ARNo);

        Separator vertSep3 = new Separator();
        vertSep3.setOrientation(Orientation.VERTICAL);

        // adding to grid and pane
        startGrid.addRow(0, displayUserProfile, vertSep1, loadSessionLabel, loadSessionTF,
                loadIDSessionButton, vertSep2, toggleARLabel, toggleARBox, vertSep3);

        startPane.setContent(startGrid);

        // simulation accordion
        TitledPane simulationPane = new TitledPane();
        simulationPane.setText("Simulation");
        simulationPane.setCollapsible(false);
        simulationPane.setAnimated(false);
        GridPane simuGrid = new GridPane();
        simuGrid.setHgap(5);
        simuGrid.setVgap(5);

        // row 0: mode auto / manual + launch button
        Label modeLabel = new Label("Mode");
        HBox modeBox = new HBox();
        modeBox.setSpacing(5);
        modeBox.setAlignment(Pos.CENTER);

        ToggleGroup modeGroup = new ToggleGroup();
        RadioButton autoRB = new RadioButton("Auto");
        autoRB.setToggleGroup(modeGroup);
        autoRB.setFocusTraversable(false);

        RadioButton manualRB = new RadioButton("Manual");
        manualRB.setToggleGroup(modeGroup);
        manualRB.setFocusTraversable(false);

        modeBox.getChildren().addAll(autoRB, manualRB);
        launchSimulation = new Button("Launch");

        // row 1: Exercise controls
        Label exerciseLabel = new Label("Exercise");
        exerciseName = new Label(""); // we place the name of the exercise here and change it when appropriate
        beginning = new Button("Beginning");
        middle = new Button("Middle");
        end = new Button("End");
        notCompleted = new Button("Not Completed");

        // row 2: session user feedback
        Label sessionFeedbackLabel = new Label("Session User Feedback");
        Spinner<Integer> feedbackSpinner = new Spinner<>(1, 5, 3);
        Utils.commitSpinnerValueOnLostFocus(feedbackSpinner);

        // invisible row 3 to make space
        Label fakeLabel = new Label("fakeLabel");
        fakeLabel.setVisible(false);

        // row 4: stop and end buttons + update user profile
        abortSimuButton = new Button("Abort Simulation");
        endSimuButton = new Button("End Simulation");
        endSimuButton.setDisable(true);
        updateProfileButton = new Button("Update Profile");
        updateProfileButton.setDisable(true);


        // adding to grid and pane
        simuGrid.addRow(0, modeLabel, modeBox, launchSimulation);
        simuGrid.addRow(1, exerciseLabel, exerciseName, beginning, middle, end, notCompleted);
        simulationPane.setContent(simuGrid);
        simuGrid.addRow(2, sessionFeedbackLabel, feedbackSpinner);
        simuGrid.addRow(3, fakeLabel);
        simuGrid.add(abortSimuButton, 2, 4, 2, 1);
        simuGrid.add(endSimuButton, 4, 4, 2, 1);
        simuGrid.add(updateProfileButton,6,4,1,1);

        mainGrid.add(startPane, 0, 0, 2, 1);
        mainGrid.add(staticProfileTrace, 0,1, 1, 1);
        mainGrid.add(dynamicProfileTrace, 1,1);
        mainGrid.add(simulationTrace, 2, 0, 1, 2);
        mainGrid.add(simulationPane, 0, 3, 3, 1);

        /* ----------------------------------------------------------
        ---------------- BEHAVIOUR CONFIGURATION --------------------
        ---------------------------------------------------------- */

        // behaviour - start pane content
        displayUserProfile.setOnAction(event -> {
            staticProfileTrace.setMainDisplayText(programme.getUser().displayStaticProfile());
            dynamicProfileTrace.setMainDisplayText(programme.getUser().displayDynamicProfile());
        });

        loadIDSessionButton.setOnAction(event -> {
            // get text field value
            long sessionID = Long.parseLong(loadSessionTF.getText()); // long

            Session session = Utils.findSession(sessionID, programme.getSessionArrayList());
            if(session == null){ // not found
                simulationTrace.appendMainDisplayText("Session ID " + sessionID + " not found in Programme Session List.\n");
            }
            else{
                programme.setCurrentSession(null);
                programme.setCurrentSession(session);
                simulationTrace.appendMainDisplayText("Session with ID " + sessionID + " successfully loaded.\n");
            }
        });

        toggleARGroup.selectedToggleProperty().addListener(((observable, oldValue, newValue) -> {
            toggleAR = newValue.equals(ARYes);
        }));

        // behaviour - simulation pane
        // row 0: mode auto / manual + launch button
        modeGroup.selectedToggleProperty().addListener(((observable, oldValue, newValue) -> {
            autoMode = newValue.equals(autoRB);
        }));

        launchSimulation.setOnAction(event -> { // launch simulation
            // apply adaptation rules
            if(toggleAR){
                simulationTrace.appendMainDisplayText("--- Adaptation Rules applied ---\n");
                programme.setMessagesFromUserModel();
                programme.setColorsFromUserModel();
            }
            else{
                simulationTrace.appendMainDisplayText("--- Adaptation Rules ignored ---\n");
            }
            // manual or auto mode
            if(autoMode){
                simulationTrace.appendMainDisplayText("--- Auto Mode Activated ---\n");
            }
            else {
                simulationTrace.appendMainDisplayText("--- Manual Mode Activated ---\n");
                beginning.setDisable(false);
            }
            launchSimulation.setDisable(true); // only 1 simulation active at a time

            if(programme.getUser().getStartedSessions() < 1){
                simulationTrace.appendMainDisplayText(programme.getMessages().getString("MESS_PROFILE_CREATED"));
            }

            // begin first exercise
            programme.getCurrentSession().setFirstExercise();
            exerciseName.setText(programme.getCurrentSession().getCurrentExercise().getName());
            simulationTrace.appendMainDisplayText("\nExercise " + exerciseName.getText() + " has begun.\n\n");
            // update UserModel
            programme.getUser().incrementStartedExercises(1);
            programme.getUser().incrementStartedSessions(1);
        });

        // row 1: Exercise controls
        // Exercise controls config
        beginning.setDisable(true);
        middle.setDisable(true);
        end.setDisable(true);
        notCompleted.setDisable(true);

        beginning.setOnAction(event -> {
            middle.setDisable(false);
            beginning.setDisable(true);

            double comp = Utils.roundToNDecimals(ThreadLocalRandom.current().nextDouble(1, 34),2);
            programme.getCurrentSession().getCurrentExercise().setCompleted(comp);

            simulationTrace.appendMainDisplayText("Completed at: " + comp + "\n");
            simulationTrace.appendMainDisplayText(programme.getMessages().getString("MESS_EX_BEG"));
            simulationTrace.appendMainDisplayText(programme.getMessages().getString("ASCII_EX_BEG"));
        });
        middle.setOnAction(event -> {
            end.setDisable(false);
            notCompleted.setDisable(false);
            middle.setDisable(true);

            double comp = Utils.roundToNDecimals(ThreadLocalRandom.current().nextDouble(45, 55),2);
            programme.getCurrentSession().getCurrentExercise().setCompleted(comp);

            simulationTrace.appendMainDisplayText("Completed at: " + comp + "\n");
            simulationTrace.appendMainDisplayText(programme.getMessages().getString("MESS_EX_MID1"));
            simulationTrace.appendMainDisplayText(programme.getMessages().getString("ASCII_EX_MID"));
        });
        end.setOnAction(event -> {
            // end exercise
            programme.getCurrentSession().getCurrentExercise().setCompleted(100);
            programme.getUser().incrementCompletedExercises(1);
            programme.getUser().incrementTotalMinutesActivity((int)(programme.getCurrentSession().getCurrentExercise().getDuration()));
            programme.getUser().incrementTotalSteps(programme.getCurrentSession().getCurrentExercise().getStepNb());

            dynamicProfileTrace.setMainDisplayText(programme.getUser().displayDynamicProfile());
            simulationTrace.appendMainDisplayText("Exercise completed.\n");
            simulationTrace.appendMainDisplayText(programme.getMessages().getString("MESS_EX_END"));
            simulationTrace.appendMainDisplayText(programme.getMessages().getString("ASCII_EX_END"));

            // load next exercise
            loadNextExercise();
        });
        notCompleted.setOnAction(event -> {
            // exercise not completed
            double comp = Utils.roundToNDecimals(ThreadLocalRandom.current().nextDouble(67, 100),2);
            programme.getCurrentSession().getCurrentExercise().setCompleted(comp);
            programme.getUser().incrementTotalMinutesActivity((int)((comp / 100) * programme.getCurrentSession().getCurrentExercise().getDuration()));
            programme.getUser().incrementTotalSteps(programme.getCurrentSession().getCurrentExercise().getStepNb());

            dynamicProfileTrace.setMainDisplayText(programme.getUser().displayDynamicProfile());
            simulationTrace.appendMainDisplayText("Completed at: " + comp + "\n");

            // load next exercise
            loadNextExercise();
        });

        // row 4: stop and end buttons
        abortSimuButton.setOnAction(event -> {
            // cancel simulation

            // reset simulation
            resetSimulation();
        });

        endSimuButton.setOnAction(event -> {
            // available when every exercise is complete
            // ends simulation by registering feedback into session
            // writes everything in a json file
            // + verbose
            // + display session summary
            // + update programme arraylist of sessions with current (ended) session

            // reset simulation
            //resetSimulation();
        });

        updateProfileButton.setOnAction(event -> {
            // available when session has ended
            // registers in JSON the updated profile -- app profile has been updated along session progress
        });

        // ----------------------------------
        // ---------- END OF CLASS ----------
        this.getChildren().add(mainGrid);
    }

    private void loadNextExercise(){
        end.setDisable(true);
        notCompleted.setDisable(true);
        if(programme.getCurrentSession().nextExercise()){
            exerciseName.setText(programme.getCurrentSession().getCurrentExercise().getName());
            programme.getUser().incrementStartedExercises(1);

            simulationTrace.appendMainDisplayText("\nExercise " + exerciseName.getText() + " has begun.\n\n");
            resetExerciseButtons();
        }
        else{
            simulationTrace.appendMainDisplayText(
                    "Session has ended.\n"
                    + programme.getMessages().getString("MESS_SESSION_END"));
            programme.getUser().incrementCompletedSessions(1);
            endSimuButton.setDisable(false);
        }
        dynamicProfileTrace.setMainDisplayText(programme.getUser().displayDynamicProfile());
    }

    private void resetSimulation(){
        launchSimulation.setDisable(false);
        beginning.setDisable(true);
        middle.setDisable(true);
        end.setDisable(true);
        notCompleted.setDisable(true);
        simulationTrace.resetTraceView();
        staticProfileTrace.resetTraceView();
        dynamicProfileTrace.resetTraceView();
        exerciseName.setText("");
    }

    private void resetExerciseButtons(){
        beginning.setDisable(false);
        middle.setDisable(true);
        end.setDisable(true);
        notCompleted.setDisable(true);
    }

}
