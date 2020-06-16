package fr.limsi.View;

import com.sun.deploy.trace.Trace;
import fr.limsi.Model.Exercise;
import fr.limsi.Model.Programme;
import fr.limsi.Model.Session;
import fr.limsi.Model.UserModel;
import fr.limsi.Model.Utils.Strings;
import fr.limsi.Model.Utils.Utils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
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

       // FlowPane startFlowPane = new FlowPane();
        //startFlowPane.setHgap(5);
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

        //startFlowPane.getChildren().addAll(displayUserProfile, vertSep1, loadSessionLabel, loadSessionTF, loadIDSessionButton, vertSep2);
        startGrid.addRow(0, displayUserProfile, vertSep1, loadSessionLabel, loadSessionTF, loadIDSessionButton, vertSep2);

        // traces
        /*
        FlowPane tracesFlowPane = new FlowPane();
        tracesFlowPane.setHgap(5);
        tracesFlowPane.getChildren().addAll(staticProfileTrace, dynamicProfileTrace, simulationTrace);
         */


        startPane.setContent(startGrid);
        mainGrid.add(startPane, 0, 0, 2, 1);
        mainGrid.add(staticProfileTrace, 0,1, 1, 1);
        mainGrid.add(dynamicProfileTrace, 1,1);
        mainGrid.add(simulationTrace, 2, 0, 1, 2);
        this.getChildren().add(mainGrid);
    }


}
