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
        return (x, y, m) -> {
            double r = 0;
            double g = 0;
            double b = 0;
            int counter = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    Color c = m[i][j];
                    if (c != null) {
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
        Color nullColor = Color.color(0.0, 0.0, 0.0, 0.0);
        return (x, y, m) -> {
            Color c = m[1][1];
            Color n = m[0][1] != null ? m[0][1] : nullColor;
            Color e = m[1][2] != null ? m[1][2] : nullColor;
            Color s = m[2][1] != null ? m[2][1] : nullColor;
            Color w = m[1][0] != null ? m[1][0] : nullColor;
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

    public static EnhancedLatentImage from(Image in) {
        EnhancedLatentImage result = new EnhancedLatentImage();
        result.in = in;
        result.pendingOperations = new ArrayList<>();
        return result;
    }

    EnhancedLatentImage transform(UnaryOperator<Color> f) {
        pendingOperations.add(new TransformOperation(map(f)));
        return this;
    }

    EnhancedLatentImage transform(ColorTransformer f) {
        pendingOperations.add(new TransformOperation(map(f)));
        return this;
    }

    EnhancedLatentImage transform(EnhancedColorTransformer f) {
        pendingOperations.add(new TransformOperation(f, true));
        return this;
    }

    public Image toImage() {
        int width = (int) in.getWidth();
        int height = (int) in.getHeight();
        WritableImage out = new WritableImage(width, height);
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++) {
                Color[][] matrix = new Color[3][3];
                matrix[1][1] = in.getPixelReader().getColor(x, y);
                Color c = matrix[1][1];
                for (int i = 0; i < pendingOperations.size(); i++) {
                    TransformOperation o = pendingOperations.get(i);
                    if (o.eager) {
                        c = o.transformer.apply(x, y, compute(x, y, pendingOperations.subList(0, i)));
                    } else {
                        c = o.transformer.apply(x, y, matrix);
                    }
                    matrix[1][1] = c;
                }
                out.getPixelWriter().setColor(x, y, c);
            }
        return out;
    }

    private EnhancedColorTransformer map(UnaryOperator<Color> op) {
        return (x, y, c) -> op.apply(c[1][1]);
    }

    private EnhancedColorTransformer map(ColorTransformer op) {
        return (x, y, c) -> op.apply(x, y, c[1][1]);
    }

    private Color[][] compute(int x, int y, List<TransformOperation> operations) {
        Color[][] result = new Color[3][3];
        for (int i = x - 1, a = 0; i <= x + 1; i++, a++) {
            for (int j = y - 1, b = 0; j <= y + 1; j++, b++) {
                if (i >= 0 && j >= 0 && i < in.getWidth() && j < in.getHeight()) {
                    Color[][] matrix = new Color[3][3];
                    Color color = in.getPixelReader().getColor(i, j);
                    matrix[1][1] = color;
                    result[a][b] = color; //result contains current colors if operations is empty
                    for (TransformOperation o : operations) {
                        result[a][b] = o.transformer.apply(i, j, matrix);
                    }
                }
            }
        }
        return result;
    }


}

@FunctionalInterface
interface EnhancedColorTransformer {
    Color apply(int x, int y, Color[][] neighborhood);
}
