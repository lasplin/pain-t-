/*
 * *Final Countdown plays in background*
 */
package paintla;

import java.util.Stack;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 *
 * @author Lin Asplin
 */
public class Redo {
    public Redo(GraphicsContext graphicsContext, Stack<Shape> undoStack, Stack<Shape> redoStack){
        if(!redoStack.empty()) {
            Shape shape = redoStack.lastElement();
            graphicsContext.setLineWidth(shape.getStrokeWidth());
            graphicsContext.setStroke(shape.getStroke());
            graphicsContext.setFill(shape.getFill());
                    
            redoStack.pop();
            if(shape.getClass() == Line.class) {
                Line tempLine = (Line) shape;
                graphicsContext.strokeLine(tempLine.getStartX(), tempLine.getStartY(), tempLine.getEndX(), tempLine.getEndY());
                undoStack.push(new Line(tempLine.getStartX(), tempLine.getStartY(), tempLine.getEndX(), tempLine.getEndY()));
            }
            else if(shape.getClass() == Rectangle.class) {
                Rectangle tempRect = (Rectangle) shape;
                graphicsContext.fillRect(tempRect.getX(), tempRect.getY(), tempRect.getWidth(), tempRect.getHeight());
                graphicsContext.strokeRect(tempRect.getX(), tempRect.getY(), tempRect.getWidth(), tempRect.getHeight());
                
                undoStack.push(new Rectangle(tempRect.getX(), tempRect.getY(), tempRect.getWidth(), tempRect.getHeight()));
            }
            else if(shape.getClass() == Circle.class) {
                Circle tempCirc = (Circle) shape;
                graphicsContext.fillOval(tempCirc.getCenterX(), tempCirc.getCenterY(), tempCirc.getRadius(), tempCirc.getRadius());
                graphicsContext.strokeOval(tempCirc.getCenterX(), tempCirc.getCenterY(), tempCirc.getRadius(), tempCirc.getRadius());
                
                undoStack.push(new Circle(tempCirc.getCenterX(), tempCirc.getCenterY(), tempCirc.getRadius()));
            }
            else if(shape.getClass() == Ellipse.class) {
                Ellipse tempElps = (Ellipse) shape;
                graphicsContext.fillOval(tempElps.getCenterX(), tempElps.getCenterY(), tempElps.getRadiusX(), tempElps.getRadiusY());
                graphicsContext.strokeOval(tempElps.getCenterX(), tempElps.getCenterY(), tempElps.getRadiusX(), tempElps.getRadiusY());
                    
                undoStack.push(new Ellipse(tempElps.getCenterX(), tempElps.getCenterY(), tempElps.getRadiusX(), tempElps.getRadiusY()));
            }
            Shape lastUndo = undoStack.lastElement();
            lastUndo.setFill(graphicsContext.getFill());
            lastUndo.setStroke(graphicsContext.getStroke());
            lastUndo.setStrokeWidth(graphicsContext.getLineWidth());
        } else {
            System.out.println("There is no action to redo.");
        }
    }
}
