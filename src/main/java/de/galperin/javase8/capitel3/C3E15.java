package de.galperin.javase8.capitel3;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

/**
 * User: eugen
 * Date: 10.11.14
 */
public class C3E15 extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Image image = new Image("queen-mary.png");
        Image finalImage = ParallelLatentImage.from(image)
                .transform(Color::darker)
                .transform(Color::grayscale)
                .toImage();
        stage.setScene(new Scene(new HBox(
                new ImageView(image),
                new ImageView(finalImage))));
        stage.show();
    }
}

class ParallelLatentImage {
    private int width;
    private int height;
    private List<UnaryOperator<Color>> pendingOperations;
    private List<Pixel> pixels = new ArrayList<>();

    private static class Pixel {
        int x;
        int y;
        Color color;

        Pixel(int x, int y, Color color) {
            this.x = x;
            this.y = y;
            this.color = color;
        }
    }

    public static ParallelLatentImage from(Image in) {
        ParallelLatentImage result = new ParallelLatentImage();
        result.width = (int) in.getWidth();
        result.height = (int) in.getHeight();
        result.pendingOperations = new ArrayList<>();
        for (int x = 0; x < result.width; x++) {
            for (int y = 0; y < result.height; y++) {
                result.pixels.add(new Pixel(x, y, in.getPixelReader().getColor(x, y)));
            }
        }
        return result;
    }

    ParallelLatentImage transform(UnaryOperator<Color> f) {
        pendingOperations.add(f);
        return this;
    }

    public Image toImage() {
        pixels.parallelStream().forEach(p -> {
            Color c = p.color;
            for (UnaryOperator<Color> f : pendingOperations) c = f.apply(c);
            p.color = c;
        });
        WritableImage out = new WritableImage(width, height);
        pixels.stream().forEach(p -> out.getPixelWriter().setColor(p.x, p.y, p.color));
        return out;
    }
}

