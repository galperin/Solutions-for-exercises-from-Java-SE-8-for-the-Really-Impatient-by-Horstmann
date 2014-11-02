package de.galperin.javase8.capitel3;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * User: eugen
 * Date: 02.11.14
 */
public class C3E10 extends Application {

    // UnaryOperator.compose returns a Function, so we have to use Function<T,T> here
    public static Image transform(Image in, Function<Color, Color> f) {
        int width = (int) in.getWidth();
        int height = (int) in.getHeight();
        WritableImage out = new WritableImage(
                width, height);
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                out.getPixelWriter().setColor(x, y,
                        f.apply(in.getPixelReader().getColor(x, y)));
        return out;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Image image = new Image("queen-mary.png");
        Image newImage = transform(image, ((UnaryOperator<Color>) Color::brighter).compose(Color::grayscale));
        stage.setScene(new Scene(new HBox(new ImageView(image), new ImageView(newImage))));
        stage.show();
    }

}
