package eu.exploptimist.Controller;

import eu.exploptimist.Model.UserModel;
import eu.exploptimist.Model.Utils.Strings;
import eu.exploptimist.View.DisplayActions;
import eu.exploptimist.View.ProfileView;
import eu.exploptimist.View.SimulationView;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {



    public static void main(String[] args){ Application.launch(args);
    }

    @Override
    public void start(Stage stage) {

        // config components
        UserModel david = new UserModel("David");

        // config UI
        stage.setTitle(Strings.APP_TITLE + Strings.APP_VERSION);
        Group root = new Group();
        Scene scene = new Scene(root, 1600, 800, Color.WHITE);

        // config UI components
        DisplayActions mainDisplay = new DisplayActions();
        ProfileView userProfileView = new ProfileView(david, mainDisplay);
        SimulationView simulation = new SimulationView(david, mainDisplay);

        // config gridpane
        GridPane gridpane = new GridPane();
        gridpane.setVgap(10);
        gridpane.setHgap(20);
        gridpane.setPadding(new Insets(10));
        // adding components to gridpane
        gridpane.add(userProfileView, 0, 0, 1,1);
        gridpane.add(mainDisplay, 0, 1, 1, 1);
        gridpane.add(simulation, 1, 0, 1, 2);

        // init
        root.getChildren().add(gridpane);
        stage.setScene(scene);
        stage.show();
    }
}
