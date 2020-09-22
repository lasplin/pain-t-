/*
 * *Final Countdown plays in background*
 */
package paintla;

import java.util.Stack;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;

/**
 *
 * @author Lin Asplin
 */
public class MyTriangle {
    double x0 = 0;
    double y0 = 0;
    public MyTriangle(GraphicsContext graphicsContext, Canvas canvas, Stack undoStack){
        Polygon temp = new Polygon();
        canvas.addEventFilter(MouseEvent.MOUSE_PRESSED, (MouseEvent event1) -> {
            x0 = event1.getX();
            y0 = event1.getY();
            event1.consume();
        });
        canvas.addEventFilter(MouseEvent.MOUSE_DRAGGED, (MouseEvent event2) -> {
            double point3X = event2.getX();
            double point3Y = event2.getY();
            double point1X = (x0+point3X)/2;
            double point1Y = y0;
            double point2X = x0;
            double point2Y = point3Y;
            
            double[] xpoints = {point1X,point2X,point3X};
            double[] ypoints = {point1Y,point2Y,point3Y};
            graphicsContext.fillPolygon(xpoints,ypoints,3);
            graphicsContext.strokePolygon(xpoints,ypoints,3);
            event2.consume();
        });
        
        canvas.addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event2) {
                double point3X = event2.getX();
                double point3Y = event2.getY();
                double point1X = (x0+point3X)/2;
                double point1Y = y0;
                double point2X = x0;
                double point2Y = point3Y;
                
                double[] xpoints = {point1X,point2X,point3X};
                double[] ypoints = {point1Y,point2Y,point3Y};
                graphicsContext.fillPolygon(xpoints,ypoints,3);
                graphicsContext.strokePolygon(xpoints,ypoints,3);
                event2.consume();
                undoStack.push(temp);
                //canvas.removeEventFilter(MouseEvent.MOUSE_RELEASED, event2);
            }
        });
    }
}
