/*
 * *Final Countdown plays in background*
 */
package paintla;

import java.util.Stack;
import javafx.scene.shape.Rectangle;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
/**
 *
 * @author Lin Asplin
 */
public class MySquare {
    public MySquare(GraphicsContext graphicsContext, Canvas canvas, Stack undoStack){
        Rectangle temp = new Rectangle();
        canvas.addEventFilter(MouseEvent.MOUSE_PRESSED, (MouseEvent event1) -> {
            temp.setX(event1.getX());
            temp.setY(event1.getY());
            event1.consume();
        });
        canvas.addEventFilter(MouseEvent.MOUSE_DRAGGED, (MouseEvent event2) -> {
            double xval = Math.abs(event2.getX()-temp.getX());
            double yval = Math.abs(event2.getY() - temp.getY());
            if(xval>yval){
                temp.setWidth(yval);
                temp.setHeight(yval);
            }
            else{
                temp.setWidth(xval);
                temp.setHeight(xval);
            }
            graphicsContext.fillRect(temp.getX(),temp.getY(),temp.getWidth(), 
                    temp.getHeight());
            graphicsContext.strokeRect(temp.getX(),temp.getY(),temp.getWidth(),
                    temp.getHeight());
            event2.consume();
        });
        canvas.addEventFilter(MouseEvent.MOUSE_RELEASED, (MouseEvent event2) -> {
            double xval = Math.abs(event2.getX()-temp.getX());
            double yval = Math.abs(event2.getY() - temp.getY());
            if(xval>yval){
                temp.setWidth(yval);
                temp.setHeight(yval);
            }
            else{
                temp.setWidth(xval);
                temp.setHeight(xval);
            }
            graphicsContext.fillRect(temp.getX(),temp.getY(),temp.getWidth(), 
                    temp.getHeight());
            graphicsContext.strokeRect(temp.getX(),temp.getY(),temp.getWidth(),
                    temp.getHeight());
            undoStack.push(temp);
            event2.consume();
        });
    }
}
