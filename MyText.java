/*
 * *Final Countdown plays in background*
 */
package paintla;


import java.util.Stack;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
/**
 *
 * @author Lin Asplin
 */
public class MyText {
    public MyText(GraphicsContext graphicsContext, Canvas canvas, Stack<Shape> undoStack){
        Stage textWindow = new Stage();
        textWindow.setTitle("Draw Text");
        Label textLabel = new Label("Input your text:");
        TextField text = new TextField();
        Button textButton = new Button("Return to Canvas");
        textButton.setOnAction((ActionEvent event2) -> {
            textWindow.close();
        });
        VBox layout = new VBox(textLabel,text,textButton);
        textWindow.setScene(new Scene(layout,200,200));
        textWindow.show();
        canvas.addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> {
                graphicsContext.fillText(text.getText(), event.getX(), event.getY());
                event.consume();
        });
    }
}
