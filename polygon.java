/*
 * *Final Countdown plays in background*
 */
package paintla;
import java.util.Stack;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Lin Asplin
 */
public class polygon {

    private final int sides;
    private final Canvas canvas;
    private final GraphicsContext graphicsContext;
    private double[] xpoints;
    private double[] ypoints;
    private double startx, starty, endx, endy;
    private Stack<WritableImage> undoStack = new Stack<>();

    public polygon(GraphicsContext gc, Canvas c, TextField s,Stack<WritableImage>us) {
        sides = Integer.parseInt(s.getCharacters().toString());
        canvas = c;
        graphicsContext = gc;
        undoStack = us;
    }

    public double[] findPoints(double length, double point, String direction) {
        double radians = Math.toRadians(360 / sides);
        double radiansBetweenVertices = radians;
        double[] points = new double[sides];
        for (int i = 0; i < sides; i++) {
            if (direction.equals("x")) {
                points[i] = (length * Math.cos(radians)) + point;
            }
            if (direction.equals("y")) {
                points[i] = (length * Math.sin(radians)) + point;
            }
            radians = radians + radiansBetweenVertices;
        }

        return points;
    }

    public void drawPolygon() {
        double[] eventpoints = new double[4];
        canvas.setOnMouseClicked((MouseEvent event) -> {
            startx = event.getX();
            starty = event.getY();
        });
        canvas.setOnMouseReleased((MouseEvent event) -> {
            endx = event.getX();
            endy = event.getY();
            xpoints = findPoints(endx - startx, startx, "x");
            ypoints = findPoints(endy - starty, starty, "y");
            graphicsContext.strokePolygon(xpoints,ypoints,sides);
            graphicsContext.fillPolygon(xpoints, ypoints, sides);
            undoStack.push(canvas.snapshot(null,null));
        });
        
    }
}
