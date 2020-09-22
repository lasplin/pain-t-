/*
 * *Final Countdown plays in background*
 */
package paintla;

import java.util.Stack;
import javafx.scene.image.Image;
import javafx.scene.canvas.Canvas;
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
public class Undo {
    public Undo(Image img, GraphicsContext graphicsContext, Canvas canvas, Stack<Shape> undoStack, Stack<Shape> redoStack){
        if (!undoStack.empty()) {
            graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            Shape removedShape = undoStack.lastElement();
            if (removedShape.getClass() == Line.class) {
                System.out.println("Line");
                Line tempLine = (Line) removedShape;
                tempLine.setFill(graphicsContext.getFill());
                tempLine.setStroke(graphicsContext.getStroke());
                tempLine.setStrokeWidth(graphicsContext.getLineWidth());
                redoStack.push(new Line(tempLine.getStartX(), tempLine.getStartY(), tempLine.getEndX(), tempLine.getEndY()));

            } else if (removedShape.getClass() == Rectangle.class) {
                Rectangle tempRect = (Rectangle) removedShape;
                tempRect.setFill(graphicsContext.getFill());
                tempRect.setStroke(graphicsContext.getStroke());
                tempRect.setStrokeWidth(graphicsContext.getLineWidth());
                redoStack.push(new Rectangle(tempRect.getX(), tempRect.getY(), tempRect.getWidth(), tempRect.getHeight()));
            } else if (removedShape.getClass() == Circle.class) {
                Circle tempCirc = (Circle) removedShape;
                tempCirc.setStrokeWidth(graphicsContext.getLineWidth());
                tempCirc.setFill(graphicsContext.getFill());
                tempCirc.setStroke(graphicsContext.getStroke());
                redoStack.push(new Circle(tempCirc.getCenterX(), tempCirc.getCenterY(), tempCirc.getRadius()));
            } else if (removedShape.getClass() == Ellipse.class) {
                Ellipse tempElps = (Ellipse) removedShape;
                tempElps.setFill(graphicsContext.getFill());
                tempElps.setStroke(graphicsContext.getStroke());
                tempElps.setStrokeWidth(graphicsContext.getLineWidth());
                redoStack.push(new Ellipse(tempElps.getCenterX(), tempElps.getCenterY(), tempElps.getRadiusX(), tempElps.getRadiusY()));
            }
            Shape lastRedo = undoStack.lastElement();
            lastRedo.setFill(removedShape.getFill());
            lastRedo.setStroke(removedShape.getStroke());
            lastRedo.setStrokeWidth(removedShape.getStrokeWidth());
            undoStack.pop();
            graphicsContext.drawImage(img,0,0);
            for (int i = 0; i < undoStack.size(); i++) {
                Shape shape = undoStack.elementAt(i);
                if (shape.getClass() == Line.class) {
                    Line temp = (Line) shape;
                    graphicsContext.setLineWidth(temp.getStrokeWidth());
                    graphicsContext.setStroke(temp.getStroke());
                    graphicsContext.setFill(temp.getFill());
                    graphicsContext.strokeLine(temp.getStartX(), temp.getStartY(), temp.getEndX(), temp.getEndY());
                } else if (shape.getClass() == Rectangle.class) {
                    Rectangle temp = (Rectangle) shape;
                    graphicsContext.setLineWidth(temp.getStrokeWidth());
                    graphicsContext.setStroke(temp.getStroke());
                    graphicsContext.setFill(temp.getFill());
                    graphicsContext.fillRect(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight());
                    graphicsContext.strokeRect(temp.getX(), temp.getY(), temp.getWidth(), temp.getHeight());
                } else if (shape.getClass() == Circle.class) {
                    Circle temp = (Circle) shape;
                    graphicsContext.setLineWidth(temp.getStrokeWidth());
                    graphicsContext.setStroke(temp.getStroke());
                    graphicsContext.setFill(temp.getFill());
                    graphicsContext.fillOval(temp.getCenterX(), temp.getCenterY(), temp.getRadius(), temp.getRadius());
                    graphicsContext.strokeOval(temp.getCenterX(), temp.getCenterY(), temp.getRadius(), temp.getRadius());
                } else if (shape.getClass() == Ellipse.class) {
                    Ellipse temp = (Ellipse) shape;
                    graphicsContext.setLineWidth(temp.getStrokeWidth());
                    graphicsContext.setStroke(temp.getStroke());
                    graphicsContext.setFill(temp.getFill());
                    graphicsContext.fillOval(temp.getCenterX(), temp.getCenterY(), temp.getRadiusX(), temp.getRadiusY());
                    graphicsContext.strokeOval(temp.getCenterX(), temp.getCenterY(), temp.getRadiusX(), temp.getRadiusY());
                }
            }
        } else {
            System.out.println("There is no action to undo.");
        }
    }
}
