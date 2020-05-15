package fr.limsi.Controller;

import fr.limsi.Model.AdaptationRules;
import fr.limsi.Model.Exercise;
import fr.limsi.Model.Session;
import fr.limsi.Model.UserModel;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {



    public static void main(String[] args){ Application.launch(args);
    }

    @Override
    public void start(Stage stage) {

        // config components
        JSONObject jsonMETAObject = new JSONObject(); // jsonObject representing all data for user : userModel, exercises, sessions, etc. hence the name
        UserModel initUser = new UserModel(jsonMETAObject);

        ArrayList<Exercise> exerciseList = new ArrayList<Exercise>();
        exerciseList.add(new Exercise("init", 0, 0));
        ArrayList<Session> sessionList = new ArrayList<Session>();
        sessionList.add(new Session(exerciseList, initUser,0));

        AdaptationRules adaptationRules = new AdaptationRules(initUser, sessionList.get(0));

        // config UI
        stage.setTitle(Strings.APP_TITLE + Strings.APP_VERSION);
        Group root = new Group();
        Scene scene = new Scene(root, 1600, 1000, Color.WHITE);

        // config UI components
        TraceView configTrace = new TraceView("Configuration Trace");
        ProfileView userProfileView = new ProfileView(initUser, configTrace);
        SessionConfigView sessionConfigView = new SessionConfigView(initUser, configTrace, adaptationRules);
        SimulationView simulation = new SimulationView(initUser, configTrace);

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
        simuGridpane.add(simulation, 0, 0);

        // fill tabs with content
        configTab.setContent(configGridpane);
        simuTab.setContent(simuGridpane);

        // init
        root.getChildren().add(tabPane);
        stage.setScene(scene);
        stage.show();
    }

    private void initApp(){
        // load all external files and parse their content
        // exercises

        // sessions
    }
}
