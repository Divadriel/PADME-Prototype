package eu.exploptimist.View;

import eu.exploptimist.Model.Utils.Strings;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

public class DisplayActions extends Parent {

    private TextArea mainDisplay;
    private Button resetButton;
    private Button importButton;
    private Button exportButton;
    private Button quitApp;

    public DisplayActions(){

        TitledPane pane = new TitledPane();
        pane.setText("Trace");
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
        resetButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainDisplay.setText(Strings.APP_TITLE + Strings.APP_VERSION + "\n");
            }
        });

        importButton = new Button("Import");
        importButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // import text and whole session + profile from a JSON file
            }
        });

        exportButton = new Button("Export");
        exportButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // export to JSON file
            }
        });

        quitApp = new Button("Quit App");
        quitApp.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });

        flowPane.getChildren().addAll(resetButton, importButton, exportButton, quitApp);
        gridpane.add(mainDisplay, 0, 0);
        gridpane.add(flowPane, 0, 1);

        pane.setContent(gridpane);
        this.getChildren().add(pane);
    }

    public TextArea getMainDisplay() {
        return mainDisplay;
    }

    public void setMainDisplay(TextArea displayActions) {
        this.mainDisplay = displayActions;
    }

    public Button getResetButton() { return resetButton; }

    public void setResetButton(Button resetButton) { this.resetButton = resetButton; }

    public Button getImportButton() {
        return importButton;
    }

    public void setImportButton(Button importButton) {
        this.importButton = importButton;
    }

    public Button getExportButton() {
        return exportButton;
    }

    public void setExportButton(Button exportButton) {
        this.exportButton = exportButton;
    }

    public Button getQuitApp() {
        return quitApp;
    }

    public void setQuitApp(Button quitApp) {
        this.quitApp = quitApp;
    }
}
