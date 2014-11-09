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
import java.util.Optional;
import java.util.function.UnaryOperator;

/**
 * User: eugen
 * Date: 08.11.14
 */
public class C3E14 extends Application {

    public void start(Stage stage) {
        try {
            Image image = new Image("queen-mary.png");
            Image finalImage = HighlyEnhancedLatentImage.from(image)
                    .transform(Color::brighter)
                    .transform(blur())
                    .transform(edgeDetection())
                    .transform(frame(image))
                    .toImage();
            stage.setScene(new Scene(new HBox(
                    new ImageView(image),
                    new ImageView(finalImage))));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private HighlyEnhancedColorTransformer blur() {
        return (x, y, reader) -> {
            double r = 0;
            double g = 0;
            double b = 0;
            int counter = 0;
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    Optional<Color> colorOptional = reader.get(x + i, y + j);
                    if (colorOptional.isPresent()) {
                        Color c = colorOptional.get();
                        r += c.getRed();
                        g += c.getGreen();
                        b += c.getBlue();
                        counter++;
                    }
                }
            }
            return Color.color(r / counter, g / counter, b / counter);
        };
    }

    private HighlyEnhancedColorTransformer edgeDetection() {
        Color nullColor = Color.color(0.0, 0.0, 0.0);
        return (x, y, reader) -> {
            Color c = reader.get(x, y).get();
            Color n = reader.get(x, y - 1).orElse(nullColor);
            Color e = reader.get(x + 1, y).orElse(nullColor);
            Color s = reader.get(x, y + 1).orElse(nullColor);
            Color w = reader.get(x - 1, y).orElse(nullColor);
            double r = 4 * c.getRed() - n.getRed() - e.getRed() - s.getRed() - w.getRed();
            double g = 4 * c.getGreen() - n.getGreen() - e.getGreen() - s.getGreen() - w.getGreen();
            double b = 4 * c.getBlue() - n.getBlue() - e.getBlue() - s.getBlue() - w.getBlue();
            return Color.color(
                    r < 0 ? 0.0 : r > 1 ? 1.0 : r,
                    g < 0 ? 0.0 : g > 1 ? 1.0 : g,
                    b < 0 ? 0.0 : b > 1 ? 1.0 : b
            );
        };
    }

    private HighlyEnhancedColorTransformer frame(Image image) {
        return (x, y, reader) -> (
                x <= 5 || x >= image.getWidth() - 5
                        || y <= 5 || y >= image.getHeight() - 5) ? Color.WHITE : reader.get(x, y).get();
    }

}

class HighlyEnhancedLatentImage {

    Image image;
    List<HighlyEnhancedColorTransformer> pendingOperations;
    List<Color[][]> cache;

    public static HighlyEnhancedLatentImage from(Image in) {
        HighlyEnhancedLatentImage result = new HighlyEnhancedLatentImage();
        result.image = in;
        result.pendingOperations = new ArrayList<>();
        result.cache = new ArrayList<>();
        return result;
    }

    public HighlyEnhancedLatentImage transform(UnaryOperator<Color> f) {
        pendingOperations.add(map(f));
        return this;
    }

    public HighlyEnhancedLatentImage transform(HighlyEnhancedColorTransformer f) {
        pendingOperations.add(f);
        return this;
    }

    public Image toImage() {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        for (int i = 0; i < pendingOperations.size() - 1; i++) {
            cache.add(new Color[width][height]);
        }
        WritableImage out = new WritableImage(width, height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color c = image.getPixelReader().getColor(x, y);
                for (int i = 0; i < pendingOperations.size(); i++) {
                    HighlyEnhancedColorTransformer operation = pendingOperations.get(i);
                    c = operation.apply(x, y, new PixelReader(this, i));
                    if (i < pendingOperations.size() - 1 && cache.get(i)[x][y] == null) {
                        cache.get(i)[x][y] = c;
                    }
                }
                out.getPixelWriter().setColor(x, y, c);
            }
        }
        return out;
    }

    private HighlyEnhancedColorTransformer map(UnaryOperator<Color> op) {
        return (x, y, reader) -> op.apply(reader.get(x, y).get());
    }

}

class PixelReader {

    HighlyEnhancedLatentImage latentImage;
    int level;

    PixelReader(HighlyEnhancedLatentImage latentImage, int level) {
        this.latentImage = latentImage;
        this.level = level;
    }

    public Optional<Color> get(int x, int y) {
        if (x < 0 || x >= latentImage.image.getWidth() || y < 0 || y >= latentImage.image.getHeight()) {
            return Optional.empty();
        }
        if (level == 0) {
            return Optional.of(latentImage.image.getPixelReader().getColor(x, y));
        }
        Color[][] levelCache = latentImage.cache.get(level - 1);
        if (levelCache[x][y] != null) {
            return Optional.of(levelCache[x][y]);
        }
        levelCache[x][y] = latentImage.pendingOperations.get(level - 1).apply(x, y, new PixelReader(latentImage, level - 1));
        return Optional.of(levelCache[x][y]);
    }
}

@FunctionalInterface
interface HighlyEnhancedColorTransformer {
    Color apply(int x, int y, PixelReader reader);
}
