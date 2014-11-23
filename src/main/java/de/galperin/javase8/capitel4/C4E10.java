package de.galperin.javase8.capitel4;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * User: eugen
 * Date: 23.11.14
 */
public class C4E10 extends Application {

    @Override
    public void start(Stage stage) {
        BorderPane pane = new BorderPane();

        WebView browser = new WebView();
        WebEngine engine = browser.getEngine();

        HBox controls = new HBox();
        TextField location = new TextField();
        location.textProperty().setValue("http://google.com");
        location.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                engine.load(location.getText());
            }
        });
        Button back = new Button("Back");
        back.setOnAction(event -> {
            engine.getHistory().go(-1);
            location.textProperty().setValue(engine.getLocation());
        });
        back.disableProperty().bind(Bindings.createBooleanBinding(
                () -> engine.getHistory().getCurrentIndex() == 0,
                engine.getHistory().currentIndexProperty()));
        Button go = new Button("Go");
        go.setOnAction(event -> engine.load(location.getText()));
        HBox.setHgrow(location, Priority.ALWAYS);
        controls.getChildren().addAll(back, location, go);

        pane.setTop(controls);
        pane.setCenter(browser);

        engine.load(location.getText());

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.setWidth(800);
        stage.setHeight(600);
        stage.show();
    }

}
