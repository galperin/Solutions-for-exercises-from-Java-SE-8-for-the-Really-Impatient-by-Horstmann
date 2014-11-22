package de.galperin.javase8.capitel4;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * User: eugen
 * Date: 22.11.14
 */
public class C4E7 extends Application {

    public void start(Stage stage) {
        BorderPane pane = new BorderPane();
        Label label = new Label("Test");
        label.setBorder(new Border(
                new BorderStroke(
                        Color.web("0xd62645"),
                        BorderStrokeStyle.SOLID,
                        null,
                        new BorderWidths(4))));
        pane.setCenter(label);
        stage.setScene(new Scene(pane));
        stage.show();
    }

}
