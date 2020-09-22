/*
 * *Final Countdown plays in background*
 */
package paintla;

import java.util.Stack;
import javafx.scene.shape.Circle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Lin Asplin
 */
public class MyCircle{
    public MyCircle(GraphicsContext graphicsContext, Canvas canvas,Stack undoStack){
        Circle temp = new Circle();
        canvas.addEventFilter(MouseEvent.MOUSE_PRESSED, (MouseEvent event1) -> {
            temp.setCenterX(event1.getX());
            temp.setCenterY(event1.getY());
            event1.consume();
        });
        canvas.addEventFilter(MouseEvent.MOUSE_DRAGGED, (MouseEvent event2) -> {
            temp.setRadius((Math.abs(event2.getX() - 
                    temp.getCenterX()) + Math.abs(event2.getY() - 
                    temp.getCenterY())) / 2);
            if(temp.getCenterX() > event2.getX()) {
                temp.setCenterX(event2.getX());
            }
            if(temp.getCenterY() > event2.getY()) {
                temp.setCenterY(event2.getY());
             }
            graphicsContext.fillOval(temp.getCenterX(), temp.getCenterY(), 
                    temp.getRadius(), temp.getRadius());
            graphicsContext.strokeOval(temp.getCenterX(), temp.getCenterY(), 
                    temp.getRadius(), temp.getRadius());
            event2.consume();
        });
        canvas.addEventFilter(MouseEvent.MOUSE_RELEASED, (MouseEvent event2) -> {
            temp.setRadius((Math.abs(event2.getX() - 
                    temp.getCenterX()) + Math.abs(event2.getY() - 
                    temp.getCenterY())) / 2);
            if(temp.getCenterX() > event2.getX()) {
                temp.setCenterX(event2.getX());
            }
            if(temp.getCenterY() > event2.getY()) {
                temp.setCenterY(event2.getY());
             }
            graphicsContext.fillOval(temp.getCenterX(), temp.getCenterY(), 
                    temp.getRadius(), temp.getRadius());
            graphicsContext.strokeOval(temp.getCenterX(), temp.getCenterY(), 
                    temp.getRadius(), temp.getRadius());
            undoStack.push(temp);
            event2.consume();
        });
        
        
    }
}
