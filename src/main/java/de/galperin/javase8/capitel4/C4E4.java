package de.galperin.javase8.capitel4;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import static javafx.beans.binding.Bindings.divide;
import static javafx.beans.binding.Bindings.min;

/**
 * User: eugen
 * Date: 22.11.14
 */
public class C4E4 extends Application {

    public void start(Stage stage) {
        Circle circle = new Circle(100, 100, 100);
        circle.setFill(Color.RED);
        Pane pane = new Pane();
        pane.getChildren().add(circle);
        Scene scene = new Scene(pane);
        circle.centerXProperty().bind(divide(scene.widthProperty(), 2));
        circle.centerYProperty().bind(divide(scene.heightProperty(), 2));
        circle.radiusProperty().bind(
                min(
                        divide(scene.widthProperty(), 2),
                        divide(scene.heightProperty(), 2))
        );
        stage.setScene(scene);
        stage.show();
    }

}
