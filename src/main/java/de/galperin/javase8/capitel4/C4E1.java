package de.galperin.javase8.capitel4;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * User: eugen
 * Date: 20.11.14
 */
public class C4E1 extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        VBox vBox = new VBox();
        Label message = new Label();
        message.setFont(new Font(100));
        TextField textField = new TextField("Hello, JavaFX!");
        message.textProperty().bind(textField.textProperty());
        vBox.getChildren().addAll(message, textField);
        stage.setScene(new Scene(vBox));
        stage.setTitle("Hello");
        stage.show();
    }
}
