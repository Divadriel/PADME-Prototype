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
    private Button exportButton;
    private Button quitApp;

    public TraceView(String name, boolean buttons, int rows, int cols){

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
        mainDisplay.setPrefRowCount(rows); // 20
        mainDisplay.setPrefColumnCount(cols); // 25
        mainDisplay.setWrapText(true);

        // col 0, row 1: flowpane with action buttons: reset text area, export, quit app
        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(5);
        flowPane.setVgap(5);
        flowPane.setPadding(new Insets(5));

        resetButton = new Button("Reset");
        resetButton.setOnAction(event -> resetTraceView());

        if(buttons){
            exportButton = new Button("Export");
            exportButton.setOnAction(event -> {
                // export to JSON file
            });

            quitApp = new Button("Quit App");
            quitApp.setOnAction(event -> Platform.exit());

            flowPane.getChildren().addAll(resetButton, exportButton, quitApp);
        }
        else {
            flowPane.getChildren().addAll(resetButton);
        }
        gridpane.add(mainDisplay, 0, 0);
        gridpane.add(flowPane, 0, 1);

        pane.setContent(gridpane);
        this.getChildren().add(pane);
    }

    public TextArea getMainDisplay() {
        return mainDisplay;
    }

    public void setMainDisplayText(String text){
        mainDisplay.setText(text);
    }

    public void appendMainDisplayText(String text){
        mainDisplay.appendText(text);
    }

    public void resetTraceView(){
        mainDisplay.setText(Strings.APP_TITLE + Strings.APP_VERSION + "\n\n");
    }
}
