/*
 * *Final Countdown plays in background*
 */
package paintla;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Lin Asplin
 */
public class ResizeMessage {
    public ResizeMessage(GraphicsContext graphicsContext, Canvas canvas){
        Stage resizeWindow = new Stage();
        resizeWindow.setTitle("resize");
        Label zoomLabel = new Label("Input how far you want to zoom.");
        Label width = new Label("Width");
        Label height = new Label("Height");
        TextField widthText = new TextField("500");
        TextField heightText = new TextField("500");
        HBox heightBox = new HBox(height,heightText);
        HBox widthBox = new HBox(width,widthText);
        Button resizeButton = new Button("Resize");
        resizeButton.setOnAction((ActionEvent event) -> {
            WritableImage img = canvas.snapshot(null,null);
            canvas.setHeight(Integer.parseInt(heightText.getText()));
            canvas.setWidth(Integer.parseInt(widthText.getText()));
            graphicsContext.drawImage(img,0,0,canvas.getWidth(),canvas.getHeight());
            resizeWindow.close();
        });
        VBox layout = new VBox(zoomLabel,heightBox,widthBox,resizeButton);
        resizeWindow.setScene(new Scene(layout,200,200));
        resizeWindow.show();
    }
}
