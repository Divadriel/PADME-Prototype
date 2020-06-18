package fr.limsi.Controller;

import com.sun.deploy.trace.Trace;
import fr.limsi.Model.*;
import fr.limsi.Model.Utils.Strings;
import fr.limsi.View.SessionConfigView;
import fr.limsi.View.TraceView;
import fr.limsi.View.ProfileView;
import fr.limsi.View.SimulationView;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args){
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {

        // config components
        Programme programme = new Programme();

        // config UI
        stage.setTitle(Strings.APP_TITLE + Strings.APP_VERSION);
        Group root = new Group();
        Scene scene = new Scene(root, 1500, 900, Color.WHITE);

        // config UI components
        TraceView configTrace = new TraceView("Configuration Trace", true, 20, 25);

        ProfileView userProfileView = new ProfileView(programme, configTrace);
        SessionConfigView sessionConfigView = new SessionConfigView(programme, configTrace);

        SimulationView simulation = new SimulationView(programme);

        // update text fields in profile view with data loaded from creating a new Programme object (init.json)
        userProfileView.updateUserTextFields(programme.getUser());

        // config TabPane + 2 tabs: configuration and simulation
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.setPadding(new Insets(0, 0, 5, 5));
        Tab configTab = new Tab();
        configTab.setText("Configuration");
        Tab simuTab = new Tab();
        simuTab.setText("Simulation");
        tabPane.getTabs().add(configTab);
        tabPane.getTabs().add(simuTab);

        // configTab gridpane
        GridPane configGridpane = new GridPane();
        configGridpane.setVgap(10);
        configGridpane.setHgap(20);
        configGridpane.setPadding(new Insets(10));
        // adding components to configGridpane
        configGridpane.add(userProfileView, 0, 0, 1,1);
        configGridpane.add(configTrace, 0, 1, 1, 1);
        configGridpane.add(sessionConfigView, 1, 0, 1, 1);

        // simuTab gridpane
        GridPane simuGridpane = new GridPane();
        simuGridpane.setVgap(10);
        simuGridpane.setHgap(20);
        simuGridpane.setPadding(new Insets(10));
        // adding components to simuGridPane
        simuGridpane.add(simulation, 0, 0,1,1);

        // fill tabs with content
        simuTab.setContent(simuGridpane);
        configTab.setContent(configGridpane);

        // init
        root.getChildren().add(tabPane);
        stage.setScene(scene);
        stage.show();
    }
}
