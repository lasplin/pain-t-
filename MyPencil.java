/*
 * *Final Countdown plays in background*
 */
package paintla;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Lin Asplin
 */
public class MyPencil {
    public MyPencil(GraphicsContext graphicsContext, Canvas canvas, TextField lineWidth){
        graphicsContext.setLineWidth(Integer.parseInt(
                    lineWidth.getCharacters().toString()));
        //Allow user to drag mouse to create line
        canvas.addEventFilter(MouseEvent.MOUSE_PRESSED, (MouseEvent event1) -> {
            graphicsContext.beginPath();
            graphicsContext.moveTo(event1.getX(), event1.getY());
            graphicsContext.stroke();
            event1.consume();
        });
        canvas.addEventFilter(MouseEvent.MOUSE_DRAGGED, (MouseEvent event2) -> {
            graphicsContext.lineTo(event2.getX(), event2.getY());
            graphicsContext.stroke();
            graphicsContext.closePath();
            graphicsContext.beginPath();
            graphicsContext.moveTo(event2.getX(), event2.getY());
            event2.consume();
        });
        //End line when mouse is released
        canvas.addEventFilter(MouseEvent.MOUSE_RELEASED, (MouseEvent event3) -> {
            graphicsContext.lineTo(event3.getX(), event3.getY());
            graphicsContext.stroke();
            graphicsContext.closePath();
            event3.consume();
        });
    }
}
