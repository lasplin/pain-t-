/*
 * *Final Countdown plays in background*
 */
package paintla;

import java.util.Stack;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.shape.Line;
import javafx.scene.input.MouseEvent;
/**
 *
 * @author Lin Asplin
 */
public class MyLine {
    public MyLine(GraphicsContext graphicsContext, Canvas canvas, TextField lineWidth, Stack undoStack){
        
        Line temp = new Line();
        graphicsContext.setLineWidth(Integer.parseInt(
                lineWidth.getCharacters().toString()));
        canvas.addEventFilter(MouseEvent.MOUSE_PRESSED,(MouseEvent event1)->{
            temp.setStartX(event1.getX());
            temp.setStartY(event1.getY());
            event1.consume();
        });
        canvas.addEventFilter(MouseEvent.MOUSE_DRAGGED, (MouseEvent event2) -> {
            temp.setEndX(event2.getX());
            temp.setEndY(event2.getY());
            graphicsContext.strokeLine(temp.getStartX(),temp.getStartY(),
                temp.getEndX(),temp.getEndY());
            event2.consume();
        });
        canvas.addEventFilter(MouseEvent.MOUSE_RELEASED, (MouseEvent event2) -> {
            temp.setEndX(event2.getX());
            temp.setEndY(event2.getY());
            graphicsContext.strokeLine(temp.getStartX(),temp.getStartY(),
                temp.getEndX(),temp.getEndY());
            undoStack.push(temp);
            event2.consume();
        });
    }
}
