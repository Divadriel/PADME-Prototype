package fr.limsi.View;

import fr.limsi.Model.Utils.Strings;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

public class TraceView extends Parent {

    private TextArea mainDisplay;
    private Button resetButton;
    private Button importButton;
    private Button exportButton;
    private Button quitApp;

    public TraceView(String name){

        TitledPane pane = new TitledPane();
        pane.setText(name);
        pane.setCollapsible(false);
        pane.setAnimated(false);

        GridPane gridpane = new GridPane();
        gridpane.setVgap(5);
        gridpane.setHgap(5);
        gridpane.setPadding(new Insets(5));

        // col 0, row 0: main display text area
        mainDisplay = new TextArea();
        mainDisplay.setEditable(false);
        mainDisplay.appendText(Strings.APP_TITLE + Strings.APP_VERSION + "\n");
        mainDisplay.setPrefRowCount(20);
        mainDisplay.setPrefColumnCount(30);

        // col 0, row 1: flowpane with action buttons: reset text area, import, export, quit app
        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(5);
        flowPane.setVgap(5);
        flowPane.setPadding(new Insets(5));

        resetButton = new Button("Reset");
        resetButton.setOnAction(event -> mainDisplay.setText(Strings.APP_TITLE + Strings.APP_VERSION + "\n"));

        importButton = new Button("Import");
        importButton.setOnAction(event -> {
            // import text and whole session + profile from a JSON file
        });

        exportButton = new Button("Export");
        exportButton.setOnAction(event -> {
            // export to JSON file
        });

        quitApp = new Button("Quit App");
        quitApp.setOnAction(event -> Platform.exit());

        flowPane.getChildren().addAll(resetButton, importButton, exportButton, quitApp);
        gridpane.add(mainDisplay, 0, 0);
        gridpane.add(flowPane, 0, 1);

        pane.setContent(gridpane);
        this.getChildren().add(pane);
    }

    public TextArea getMainDisplay() {
        return mainDisplay;
    }
}
