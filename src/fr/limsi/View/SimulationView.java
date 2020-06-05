package fr.limsi.View;

import fr.limsi.Model.Exercise;
import fr.limsi.Model.Programme;
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

    //private UserModel user;
    private Programme programme;
    private TraceView traceView;

    public SimulationView(Programme prog, TraceView trcView){

        programme = prog;
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
        content.addColumn(1, progressLabel, exOneProgress, exTwoProgress, exThreeProgress, feedbackPane, endResetFlowPane);

        simulationPane.setContent(content);
        this.getChildren().add(simulationPane);
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
                if(programme.getUser().getChronicRegulatoryFocus() >= 0){ // PROMOTION
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
                if(programme.getUser().getChronicRegulatoryFocus() >= 0){ // PROMOTION
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
                if(programme.getUser().getChronicRegulatoryFocus() >= 0){ // PROMOTION
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
}
