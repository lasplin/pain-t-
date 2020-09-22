/*
 * *Final Countdown plays in background*
 */
package paintla;

import java.util.Stack;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
/**
 *
 * @author Lin Asplin
 */
public class MyRect {
    public MyRect(GraphicsContext graphicsContext, Canvas canvas, Stack undoStack){
        Rectangle temp = new Rectangle();
        canvas.addEventFilter(MouseEvent.MOUSE_PRESSED, (MouseEvent event1) -> {
            temp.setX(event1.getX());
            temp.setY(event1.getY());
            event1.consume();
        });
        canvas.addEventFilter(MouseEvent.MOUSE_DRAGGED, (MouseEvent event2) -> {
            temp.setWidth(Math.abs(event2.getX() - temp.getX()));
            temp.setHeight(Math.abs(event2.getY() - temp.getY()));
            if (temp.getX() > event2.getX()) {
                temp.setX(event2.getX());
            }
            if (temp.getY() > event2.getY()) {
                temp.setY(event2.getY());
            }
            graphicsContext.fillRect(temp.getX(),temp.getY(),temp.getWidth(),
                    temp.getHeight());
            graphicsContext.strokeRect(temp.getX(), temp.getY(),temp.getWidth(),
                    temp.getHeight());
            event2.consume();
        });
        canvas.addEventFilter(MouseEvent.MOUSE_RELEASED, (MouseEvent event2) -> {
            temp.setWidth(Math.abs(event2.getX() - temp.getX()));
            temp.setHeight(Math.abs(event2.getY() - temp.getY()));
            if (temp.getX() > event2.getX()) {
                temp.setX(event2.getX());
            }
            if (temp.getY() > event2.getY()) {
                temp.setY(event2.getY());
            }
            graphicsContext.fillRect(temp.getX(),temp.getY(),temp.getWidth(),
                    temp.getHeight());
            graphicsContext.strokeRect(temp.getX(), temp.getY(),temp.getWidth(),
                    temp.getHeight());
            undoStack.push(temp);
            event2.consume();
        });
    }
}
