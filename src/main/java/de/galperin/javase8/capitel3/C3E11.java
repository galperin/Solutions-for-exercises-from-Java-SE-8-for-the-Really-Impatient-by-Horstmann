package de.galperin.javase8.capitel3;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.function.UnaryOperator;

/**
 * User: eugen
 * Date: 02.11.14
 */
public class C3E11 extends Application {

    public static Image transform(Image in, ColorTransformer t) {
        int width = (int) in.getWidth();
        int height = (int) in.getHeight();
        WritableImage out = new WritableImage(width, height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                out.getPixelWriter().setColor(x, y,
                        t.apply(x, y, in.getPixelReader().getColor(x, y)));
            }
        }
        return out;
    }

    public static ColorTransformer compose(ColorTransformer first, ColorTransformer second) {
        return (x, y, c) -> second.apply(x, y, first.apply(x, y, c));
    }

    public static ColorTransformer map(UnaryOperator<Color> op) {
        return (x, y, c) -> op.apply(c);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Image image = new Image("queen-mary.png");
        Image newImage = transform(image, compose(map(Color::brighter),
                (x, y, c) -> (
                        x <= 10 || x >= image.getWidth() - 10
                                || y <= 10 || y >= image.getHeight() - 10) ? Color.GREY : c));
        stage.setScene(new Scene(new HBox(new ImageView(image), new ImageView(newImage))));
        stage.show();
    }

}
