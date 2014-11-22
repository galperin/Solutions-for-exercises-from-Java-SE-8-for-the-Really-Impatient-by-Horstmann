package de.galperin.javase8.capitel4;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * User: eugen
 * Date: 22.11.14
 */
public class C4E6 extends Application {

    public void start(Stage stage) {
        BorderPane pane = new BorderPane();
        Button top = new Button("Top");
        BorderPane.setAlignment(top, Pos.CENTER);
        pane.setTop(top);
        pane.setLeft(new Button("Left"));
        pane.setCenter(new Button("Center"));
        pane.setRight(new Button("Right"));
        Button bottom = new Button("Bottom");
        pane.setBottom(bottom);
        BorderPane.setAlignment(bottom, Pos.CENTER);
        stage.setScene(new Scene(pane));
        stage.show();
    }

}
