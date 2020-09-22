/*
 * *Final Countdown plays in background*
 */
package paintla;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Lin Asplin
 */
public class MyZoom {
    public MyZoom(Canvas canvas){
        Stage zoomWindow = new Stage();
        zoomWindow.setTitle("Zoom");
        Label zoomLabel = new Label("Input how far you want to zoom.");
        TextField zoomText = new TextField("0");
        Button zoomButton = new Button("Zoom");
        zoomButton.setOnAction((ActionEvent event2) -> {
            canvas.setScaleX(Integer.parseInt(zoomText.getText()));
            canvas.setScaleY(Integer.parseInt(zoomText.getText()));
        });
        VBox layout = new VBox(zoomLabel,zoomText,zoomButton);
        zoomWindow.setScene(new Scene(layout,200,200));
        zoomWindow.show();
    }
}
