package de.galperin.javase8.capitel4;

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * User: eugen
 * Date: 23.11.14
 */
public class C4E9 extends Application {

    public void start(Stage stage) {
        final Group group = new Group();
        final Scene scene = new Scene(group, 205, 215, Color.GHOSTWHITE);
        stage.setScene(scene);
        stage.setTitle("C4E9");
        stage.show();

        final Circle circle = new Circle(20, 20, 15);
        circle.setFill(Color.BLUE);

        final Path path = new Path();

        ArcTo arcTo = new ArcTo();
        arcTo.setX(151);
        arcTo.setY(30);
        arcTo.setSweepFlag(false);
        arcTo.setLargeArcFlag(true);
        arcTo.setRadiusX(50);
        arcTo.setRadiusY(100);
        arcTo.setXAxisRotation(45);

        path.getElements().add(new MoveTo(150, 30));
        path.getElements().add(arcTo);
        path.getElements().add(new ClosePath());

        path.setStroke(Color.DODGERBLUE);
        path.getStrokeDashArray().setAll(5d, 5d);
        path.setOpacity(0.5);

        group.getChildren().add(path);
        group.getChildren().add(circle);

        final PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(10));
        pathTransition.setPath(path);
        pathTransition.setNode(circle);
        pathTransition.setCycleCount(Timeline.INDEFINITE);
        pathTransition.setAutoReverse(false);
        pathTransition.play();
    }



}
