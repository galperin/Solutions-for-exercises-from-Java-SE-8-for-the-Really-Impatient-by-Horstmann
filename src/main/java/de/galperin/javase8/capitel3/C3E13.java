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
 * Date: 02.11.14
 */
public class C3E13 extends Application {

    public void start(Stage stage) {
        try {
            Image image = new Image("queen-mary.png");
            Image finalImage = EnhancedLatentImage.from(image)
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

    private EnhancedColorTransformer blur() {
        return (x, y, colors) -> {
            double r = 0;
            double g = 0;
            double b = 0;
            int counter = 0;
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if (x + i >= 0 && y + j >= 0 && x + i < colors.length && y + j < colors[x + i].length) {
                        Color c = colors[x + i][y + j];
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

    private EnhancedColorTransformer edgeDetection() {
        Color nullColor = Color.color(0.0, 0.0, 0.0);
        return (x, y, colors) -> {
            Color c = colors[x][y];
            Color n = y > 0 ? colors[x][y - 1] : nullColor;
            Color e = x < colors.length - 1 ? colors[x + 1][y] : nullColor;
            Color s = y < colors[y].length - 1 ? colors[x][y + 1] : nullColor;
            Color w = x > 0 ? colors[x - 1][y] : nullColor;
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

    private ColorTransformer frame(Image image) {
        return (x, y, c) -> (
                x <= 5 || x >= image.getWidth() - 5
                        || y <= 5 || y >= image.getHeight() - 5) ? Color.WHITE : c;
    }

}

class EnhancedLatentImage {

    private class TransformOperation {

        EnhancedColorTransformer transformer;
        boolean eager;

        private TransformOperation(EnhancedColorTransformer transformer) {
            this.transformer = transformer;
        }

        private TransformOperation(EnhancedColorTransformer transformer, boolean eager) {
            this.transformer = transformer;
            this.eager = eager;
        }
    }

    private Image in;
    private List<TransformOperation> pendingOperations;
    List<Color[][]> stages;

    public static EnhancedLatentImage from(Image in) {
        EnhancedLatentImage result = new EnhancedLatentImage();
        result.in = in;
        result.pendingOperations = new ArrayList<>();
        int width = (int) in.getWidth();
        int height = (int) in.getHeight();
        result.stages = new ArrayList<>();
        Color[][] initStage = new Color[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                initStage[x][y] = in.getPixelReader().getColor(x, y);
            }
        }
        result.stages.add(initStage);
        return result;
    }

    public EnhancedLatentImage transform(UnaryOperator<Color> f) {
        pendingOperations.add(new TransformOperation(map(f)));
        stages.add(new Color[(int) in.getWidth()][(int) in.getHeight()]);
        return this;
    }

    public EnhancedLatentImage transform(ColorTransformer f) {
        pendingOperations.add(new TransformOperation(map(f)));
        stages.add(new Color[(int) in.getWidth()][(int) in.getHeight()]);
        return this;
    }

    public EnhancedLatentImage transform(EnhancedColorTransformer f) {
        pendingOperations.add(new TransformOperation(f, true));
        stages.add(new Color[(int) in.getWidth()][(int) in.getHeight()]);
        return this;
    }

    public Image toImage() {
        int width = (int) in.getWidth();
        int height = (int) in.getHeight();
        WritableImage out = new WritableImage(width, height);
        for (int i = pendingOperations.size() - 1; i > 0; i--) {
            TransformOperation o = pendingOperations.get(i);
            if (o.eager) {
                compute(pendingOperations.subList(0, i));
                break;
            }
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color c = in.getPixelReader().getColor(x, y);
                for (int i = 0; i < pendingOperations.size(); i++) {
                    TransformOperation o = pendingOperations.get(i);
                    if(i < pendingOperations.size()) {
                        Color[][] nextStage = stages.get(i + 1);
                        if(nextStage[x][y] == null) {
                            c = o.transformer.apply(x, y, stages.get(i));
                            nextStage[x][y] = c;
                        }
                    }
                }
                out.getPixelWriter().setColor(x, y, c);
            }
        }
        return out;
    }

    private EnhancedColorTransformer map(UnaryOperator<Color> op) {
        return (x, y, colors) -> op.apply(colors[x][y]);
    }

    private EnhancedColorTransformer map(ColorTransformer op) {
        return (x, y, colors) -> op.apply(x, y, colors[x][y]);
    }

    private void compute(List<TransformOperation> operations) {
        for (int i = 0; i < operations.size(); i++) {
            TransformOperation o = operations.get(i);
            int width = (int) in.getWidth();
            int height = (int) in.getHeight();
            Color[][] stage = new Color[width][height];
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    stage[x][y] = o.transformer.apply(x, y, stages.get(i));
                }
            }
            stages.set(i + 1, stage);
        }
    }
}


@FunctionalInterface
interface EnhancedColorTransformer {
    Color apply(int x, int y, Color[][] colors);
}
