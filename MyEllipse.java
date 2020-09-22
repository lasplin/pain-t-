/*
 * *Final Countdown plays in background*
 */
package paintla;

import java.util.Stack;
import javafx.scene.input.MouseEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Ellipse;

/**
 *
 * @author Lin Asplin
 */
public class MyEllipse {
    public MyEllipse(GraphicsContext graphicsContext, Canvas canvas, Stack undoStack){
        Ellipse temp = new Ellipse();
        canvas.addEventFilter(MouseEvent.MOUSE_PRESSED, (MouseEvent event1) -> {
            temp.setCenterX(event1.getX());
            temp.setCenterY(event1.getY());
            event1.consume();
        });
        canvas.addEventFilter(MouseEvent.MOUSE_DRAGGED, (MouseEvent event1) -> {
            temp.setRadiusX(Math.abs(event1.getX() - temp.getCenterX()));
            temp.setRadiusY(Math.abs(event1.getY() - temp.getCenterY()));
            if (temp.getCenterX() > event1.getX()) {
                temp.setCenterX(event1.getX());
            }
            if (temp.getCenterY() > event1.getY()) {
                temp.setCenterY(event1.getY());
            }
            graphicsContext.fillOval(temp.getCenterX(), temp.getCenterY(),
                    temp.getRadiusX(), temp.getRadiusY());
            graphicsContext.strokeOval(temp.getCenterX(), temp.getCenterY(),
                    temp.getRadiusX(), temp.getRadiusY());
            event1.consume();
        });
        canvas.addEventFilter(MouseEvent.MOUSE_RELEASED, (MouseEvent event1) -> {
            temp.setRadiusX(Math.abs(event1.getX() - temp.getCenterX()));
            temp.setRadiusY(Math.abs(event1.getY() - temp.getCenterY()));
            if (temp.getCenterX() > event1.getX()) {
                temp.setCenterX(event1.getX());
            }
            if (temp.getCenterY() > event1.getY()) {
                temp.setCenterY(event1.getY());
            }
            graphicsContext.fillOval(temp.getCenterX(), temp.getCenterY(),
                    temp.getRadiusX(), temp.getRadiusY());
            graphicsContext.strokeOval(temp.getCenterX(), temp.getCenterY(),
                    temp.getRadiusX(), temp.getRadiusY());
            undoStack.push(temp);
            event1.consume();
        });
    }
}
