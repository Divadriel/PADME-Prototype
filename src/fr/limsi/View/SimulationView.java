package fr.limsi.View;


import fr.limsi.Model.Exercise;
import fr.limsi.Model.Programme;
import fr.limsi.Model.Session;
import fr.limsi.Model.UserModel;
import fr.limsi.Model.Utils.Strings;
import fr.limsi.Model.Utils.Utils;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class SimulationView extends Parent {

    //private UserModel user;
    private Programme programme;
    private TraceView staticProfileTrace;
    private TraceView dynamicProfileTrace;
    private TraceView simulationTrace;

    public SimulationView(Programme prog){

        programme = prog;
        staticProfileTrace = new TraceView("Static Profile", false, 15, 15);
        dynamicProfileTrace = new TraceView("Dynamic Profile", false, 15, 15);
        simulationTrace = new TraceView("Simulation Trace", true, 20,25);

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
        displayUserProfile.setOnAction(event -> {
            staticProfileTrace.getMainDisplay().setText(programme.getUser().displayStaticProfile());
            dynamicProfileTrace.getMainDisplay().setText(programme.getUser().displayDynamicProfile());
        });
        Separator vertSep1 = new Separator();
        vertSep1.setOrientation(Orientation.VERTICAL);
        Label loadSessionLabel = new Label("Load Session");
        TextField loadSessionTF = new TextField();
        loadSessionTF.setPrefWidth(120);
        Button loadIDSessionButton = new Button("Load");
        loadIDSessionButton.setOnAction(event -> {
            // get text field value
            long sessionID = Long.parseLong(loadSessionTF.getText()); // long

            Session session = Utils.findSession(sessionID, programme.getSessionArrayList());
            if(session == null){ // not found
                simulationTrace.getMainDisplay().appendText("Session ID " + sessionID + " not found in Programme Session List.\n");
            }
            else{
                programme.setCurrentSession(null);
                programme.setCurrentSession(session);
                simulationTrace.getMainDisplay().appendText("Session with ID " + sessionID + " successfully loaded.\n");
            }
        });
        Separator vertSep2 = new Separator();
        vertSep2.setOrientation(Orientation.VERTICAL);

        Label toggleAR = new Label("Adaptation Rules");
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
                loadIDSessionButton, vertSep2, toggleAR, toggleARBox, vertSep3);

        startPane.setContent(startGrid);

        // simulation accordion
        TitledPane simulationPane = new TitledPane();
        simulationPane.setText("Simulation");
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
        Button launchSimulation = new Button("Launch");
        launchSimulation.setOnAction(event -> {
            // launch simulation
        });

        // row 1: Exercise controls
        Label exerciseLabel = new Label("Exercise");
        Label exerciseName = new Label(""); // we place the name of the exercise here and change it when appropriate
        Button beginning = new Button("Beginning");
        Button middle = new Button("Middle");
        Button end = new Button("End");
        Button notCompleted = new Button("Not Completed");

        // Exercise controls config
        middle.setDisable(true);
        end.setDisable(true);
        notCompleted.setDisable(true);

        beginning.setOnAction(event -> {
            middle.setDisable(false);
        });
        middle.setOnAction(event -> {
            end.setDisable(false);
            notCompleted.setDisable(false);
        });
        end.setOnAction(event -> {
            // end exercise
        });
        notCompleted.setOnAction(event -> {
            // exercise not completed
        });


        // adding to grid and pane
        simuGrid.addRow(0, modeLabel, modeBox, launchSimulation);
        simuGrid.addRow(1, exerciseLabel, exerciseName, beginning, middle, end, notCompleted);
        simulationPane.setContent(simuGrid);

        mainGrid.add(startPane, 0, 0, 2, 1);
        mainGrid.add(staticProfileTrace, 0,1, 1, 1);
        mainGrid.add(dynamicProfileTrace, 1,1);
        mainGrid.add(simulationTrace, 2, 0, 1, 2);
        mainGrid.add(simulationPane, 0, 3, 3, 1);
        this.getChildren().add(mainGrid);
    }


}
