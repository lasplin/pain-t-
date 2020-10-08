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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Lin Asplin
 */
public class ShapeMenu {

    private final GraphicsContext graphicsContext;
    private final Canvas canvas;
    private final TextField lineWidth;
    private Stack<WritableImage> undoStack = new Stack<>();

    public ShapeMenu(GraphicsContext gc, Canvas c, TextField lw,Stack<WritableImage> us) {
        graphicsContext = gc;
        canvas = c;
        lineWidth = lw;
        undoStack = us;
    }

    public void myTriangle() {
        canvas.setOnMouseDragged((MouseEvent event) -> {
        });
        canvas.setOnMouseClicked((MouseEvent event) -> {
        });
        graphicsContext.setLineWidth(Integer.parseInt(
                lineWidth.getCharacters().toString()));
        double[] xpoints = new double[3];
        double[] ypoints = new double[3];
        /*
         * Place event values in the appropriate point coordinates 
         */
        canvas.setOnMousePressed((MouseEvent event) -> {
            xpoints[1] = event.getX();
            ypoints[0] = event.getY();
        });
        /*
         * When mouse released
         */
        canvas.setOnMouseReleased((MouseEvent event) -> {
            /*
             * Calculate other coordinates
             */
            xpoints[2] = event.getX();
            ypoints[2] = event.getY();
            xpoints[0] = (xpoints[1] + xpoints[2]) / 2;
            ypoints[1] = ypoints[2];

            /*
             * Draw triangle to graphicscontext
             */
            graphicsContext.fillPolygon(xpoints, ypoints, 3);
            graphicsContext.strokePolygon(xpoints, ypoints, 3);
            /*
             * Push triangle to undoStack
             */
            undoStack.push(canvas.snapshot(null, null));
        });
    }

    public void mySquare() {
        canvas.setOnMouseDragged((MouseEvent event) -> {
        });
        canvas.setOnMouseClicked((MouseEvent event) -> {
        });
        graphicsContext.setLineWidth(Integer.parseInt(
                lineWidth.getCharacters().toString()));
        /*
         * Blank Rectangle
         */
        Rectangle temp = new Rectangle();
        /*
         * Start rectangle when mouse pressed
         */
        canvas.setOnMousePressed((MouseEvent event) -> {
            temp.setX(event.getX());
            temp.setY(event.getY());
        });

        /*
         * When mouse released
         */
        canvas.setOnMouseReleased((MouseEvent event) -> {
            /*
             * Ensure sides are the smaller value
             */
            double xval = Math.abs(event.getX() - temp.getX());
            double yval = Math.abs(event.getY() - temp.getY());
            if (xval > yval) {
                temp.setWidth(yval);
                temp.setHeight(yval);
            } else {
                temp.setWidth(xval);
                temp.setHeight(xval);
            }
            /*
             * Draw square to graphicscontext
             */
            graphicsContext.fillRect(temp.getX(), temp.getY(), temp.getWidth(),
                    temp.getHeight());
            graphicsContext.strokeRect(temp.getX(), temp.getY(), temp.getWidth(),
                    temp.getHeight());
            /*
             * Push square to stack
             */
            undoStack.push(canvas.snapshot(null, null));
        });
    }

    public void myRect() {
        canvas.setOnMouseDragged((MouseEvent event) -> {
        });
        canvas.setOnMouseClicked((MouseEvent event) -> {
        });
        graphicsContext.setLineWidth(Integer.parseInt(
                lineWidth.getCharacters().toString()));
        /*
         * Blank rectangle
         */
        Rectangle temp = new Rectangle();
        /*
         * Begin rectangle when mouse pressed
         */
        canvas.setOnMousePressed((MouseEvent event) -> {
            temp.setX(event.getX());
            temp.setY(event.getY());
        });

        /*
         * When mouse released
         */
        canvas.setOnMouseReleased((MouseEvent event) -> {
            /*
             * Calculate size and direction of rectangle
             */
            temp.setWidth(Math.abs(event.getX() - temp.getX()));
            temp.setHeight(Math.abs(event.getY() - temp.getY()));
            if (temp.getX() > event.getX()) {
                temp.setX(event.getX());
            }
            if (temp.getY() > event.getY()) {
                temp.setY(event.getY());
            }
            /*
             * Add rectangle to graphicscontext
             */
            graphicsContext.fillRect(temp.getX(), temp.getY(), temp.getWidth(),
                    temp.getHeight());
            graphicsContext.strokeRect(temp.getX(), temp.getY(), temp.getWidth(),
                    temp.getHeight());
            /*
             * Add rectangle to undostack
             */
            undoStack.push(canvas.snapshot(null, null));
        });
    }

    public void myCircle() {
        canvas.setOnMouseDragged((MouseEvent event) -> {
        });
        canvas.setOnMouseClicked((MouseEvent event) -> {
        });
        graphicsContext.setLineWidth(Integer.parseInt(
                lineWidth.getCharacters().toString()));
        Circle temp = new Circle();
        canvas.setOnMousePressed((MouseEvent event) -> {
            temp.setCenterX(event.getX());
            temp.setCenterY(event.getY());
        });

        canvas.setOnMouseReleased((MouseEvent event) -> {
            temp.setRadius((Math.abs(event.getX()
                    - temp.getCenterX()) + Math.abs(event.getY()
                    - temp.getCenterY())) / 2);
            if (temp.getCenterX() > event.getX()) {
                temp.setCenterX(event.getX());
            }
            if (temp.getCenterY() > event.getY()) {
                temp.setCenterY(event.getY());
            }
            graphicsContext.fillOval(temp.getCenterX(), temp.getCenterY(),
                    temp.getRadius(), temp.getRadius());
            graphicsContext.strokeOval(temp.getCenterX(), temp.getCenterY(),
                    temp.getRadius(), temp.getRadius());
            undoStack.push(canvas.snapshot(null, null));
        });
    }

    public void myEllipse() {
        Ellipse temp = new Ellipse();
        canvas.setOnMouseClicked((MouseEvent event) -> {
        });
        canvas.setOnMouseDragged((MouseEvent event) -> {
        });
        graphicsContext.setLineWidth(Integer.parseInt(
                lineWidth.getCharacters().toString()));
        /*
         * Begin an ellipse centered on the first click
         */
        canvas.setOnMousePressed((MouseEvent event) -> {
            temp.setCenterX(event.getX());
            temp.setCenterY(event.getY());
            event.consume();
        });

        /*
         * When mouse is released
         */
        canvas.setOnMouseReleased((MouseEvent event) -> {
            /*
             * Calculate radius of ellipse
             */
            temp.setRadiusX(Math.abs(event.getX() - temp.getCenterX()));
            temp.setRadiusY(Math.abs(event.getY() - temp.getCenterY()));
            if (temp.getCenterX() > event.getX()) {
                temp.setCenterX(event.getX());
            }
            if (temp.getCenterY() > event.getY()) {
                temp.setCenterY(event.getY());
            }
            /*
             * Add ellipse to graphicscontext
             */
            graphicsContext.fillOval(temp.getCenterX(), temp.getCenterY(),
                    temp.getRadiusX(), temp.getRadiusY());
            graphicsContext.strokeOval(temp.getCenterX(), temp.getCenterY(),
                    temp.getRadiusX(), temp.getRadiusY());
            /*
             * Add ellipse to undostack
             */
            undoStack.push(canvas.snapshot(null, null));
        });
    }

    public void myLine() {
        canvas.setOnMouseDragged((MouseEvent event) -> {
        });
        canvas.setOnMouseClicked((MouseEvent event) -> {
        });
        /*
         * Create a blank line with width from the lineWidth textfield
         */
        Line temp = new Line();
        graphicsContext.setLineWidth(Integer.parseInt(
                lineWidth.getCharacters().toString()));
        /*
         * Begin line from first click
         */
        canvas.setOnMousePressed((MouseEvent event) -> {
            temp.setStartX(event.getX());
            temp.setStartY(event.getY());
        });

        /*
         * When mouse released
         */
        canvas.setOnMouseReleased((MouseEvent event) -> {
            /*
             * End line
             */
            temp.setEndX(event.getX());
            temp.setEndY(event.getY());
            /*
             * Add line to graphicsContext
             */
            graphicsContext.strokeLine(temp.getStartX(), temp.getStartY(),
                    temp.getEndX(), temp.getEndY());
            /*
             * Push line to undostack
             */
            undoStack.push(canvas.snapshot(null, null));
        });
    }

    public void myPencil() {
        canvas.setOnMouseClicked((MouseEvent event) -> {
        });
        graphicsContext.setLineWidth(Integer.parseInt(
                lineWidth.getCharacters().toString()));
        //Allow user to drag mouse to create line
        canvas.setOnMousePressed((MouseEvent event) -> {
            graphicsContext.beginPath();
            graphicsContext.moveTo(event.getX(), event.getY());
            graphicsContext.stroke();
        });
        canvas.setOnMouseDragged((MouseEvent event) -> {
            graphicsContext.lineTo(event.getX(), event.getY());
            graphicsContext.stroke();
            graphicsContext.closePath();
            graphicsContext.beginPath();
            graphicsContext.moveTo(event.getX(), event.getY());
        });
        //End line when mouse is released
        canvas.setOnMouseReleased((MouseEvent event) -> {
            graphicsContext.lineTo(event.getX(), event.getY());
            graphicsContext.stroke();
            graphicsContext.closePath();
            undoStack.push(canvas.snapshot(null, null));
        });
    }

    public void noTool() {
        /*
        * Assigns an empty mouseevent to all mouse usage types in order to remove other action calls
         */
        canvas.setOnMouseClicked((MouseEvent e) -> {
        });
        canvas.setOnMousePressed((MouseEvent e) -> {
        });
        canvas.setOnMouseDragged((MouseEvent e) -> {
        });
        canvas.setOnMouseReleased((MouseEvent e) -> {
        });
    }

    
}
