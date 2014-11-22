package de.galperin.javase8.capitel4;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * User: eugen
 * Date: 22.11.14
 */
public class C4E5 extends Application {

    public void start(Stage stage) {
        Button smaller = new Button("Smaller");
        Button larger = new Button("Larger");
        Rectangle gauge = new Rectangle(0, 5, 50, 15);
        Rectangle outline = new Rectangle(0, 5, 100, 15);
        outline.setFill(null);
        outline.setStroke(Color.BLACK);
        Pane pane = new Pane();
        pane.getChildren().addAll(gauge, outline);
        smaller.setOnAction(event -> gauge.setWidth(gauge.getWidth() - 10));
        larger.setOnAction(event -> gauge.setWidth(gauge.getWidth() + 10));

        smaller.disableProperty().bind(observe((w) -> w.intValue() <= 0, gauge.widthProperty()));
        larger.disableProperty().bind(observe((w) -> w.intValue() >= 100, gauge.widthProperty()));

        HBox box = new HBox(10);
        box.getChildren().addAll(smaller, pane, larger);
        Scene scene = new Scene(box);
        stage.setScene(scene);
        stage.show();
    }

    public static <T, R> ObservableValue<R> observe(Function<T, R> f, ObservableValue<T> t) {

        return new SimpleObjectProperty<R>() {

            @Override
            public void addListener(ChangeListener<? super R> listener) {
                t.addListener((observable, oldValue, newValue) -> listener.changed(this, f.apply(oldValue), f.apply(newValue)));
            }

            @Override
            public R getValue() {
                return f.apply(t.getValue());
            }

            @Override
            public void addListener(InvalidationListener listener) {
                t.addListener(listener);
            }

        };

    }

    public static <T, U, R> ObservableValue<R> observe(BiFunction<T, U, R> f, ObservableValue<T> t, ObservableValue<U> u) {

        return new SimpleObjectProperty<R>() {

            @Override
            public void addListener(ChangeListener<? super R> listener) {
                t.addListener((observable, oldValue, newValue) ->
                        listener.changed(this, f.apply(oldValue, u.getValue()), f.apply(newValue, u.getValue())));
                u.addListener((observable, oldValue, newValue) ->
                        listener.changed(this, f.apply(t.getValue(), oldValue), f.apply(t.getValue(), newValue)));
            }

            @Override
            public R getValue() {
                return f.apply(t.getValue(), u.getValue());
            }

            @Override
            public void addListener(InvalidationListener listener) {
                t.addListener(listener);
                u.addListener(listener);
            }

        };

    }
}
