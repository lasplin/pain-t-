/*
 * *Final Countdown plays in background*
 */
package paintla;

import java.util.Stack;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;

/**
 *
 * @author Lin Asplin
 */
public class UndoRedo {
    private final GraphicsContext graphicsContext;
    private final Canvas canvas;
    private Stack<WritableImage> undoStack = new Stack<>();
    private Stack<WritableImage> redoStack = new Stack<>();
    public UndoRedo(GraphicsContext gc, Canvas c,Stack<WritableImage> us, Stack<WritableImage> rs){
        graphicsContext = gc;
        canvas = c;
        undoStack = us;
        redoStack = rs;
    }
    public void undo(){
        if(undoStack.size()>1){
            redoStack.push(undoStack.pop());
            graphicsContext.drawImage(undoStack.peek(), 0, 0);
        }
        else{
            System.out.println("There is nothing to undo");
        }
    }
    public void redo(){
        if (redoStack.isEmpty()!=true){
            graphicsContext.drawImage(redoStack.peek(),0,0);
            undoStack.push(redoStack.pop());
        }
        else{
            System.out.println("There is nothing to undo.");
        }
    }
}
