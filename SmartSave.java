/*
 * *Final Countdown plays in background*
 */
package paintla;

import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * @author Lin Asplin
 */
public class SmartSave {
    public SmartSave(File file, Canvas canvas){
        /*
         * Open SmartSave dialog only if file is not null
         */
        if(file!=null){
            Stage saveDialog = new Stage();
            saveDialog.setTitle("Smart Save");
            Label saveQuestion = new Label("This file already exists. Overwrite?");
                    
            /*
             * This is an example of coding for users with anxiety. 
             * Instead of simple yes/no buttons that don't say what you're
             * doing, the buttons clearly state what will happen if you push them.
             */
            Button yesButton = new Button("Overwrite File");
            yesButton.setOnAction((ActionEvent event2) -> {
                /*
                 * If yes, try to save
                 */
                try {
                    ImageIO.write(SwingFXUtils.fromFXImage(canvas.snapshot(null,null),
                            null), "png", file);
                } catch (IOException e) {
                    System.out.println("Exception occured :" + e.getMessage());
                }
                saveDialog.close();
            });
            Button noButton = new Button("Don't Overwrite");
            noButton.setOnAction((ActionEvent event3) -> {
                /*
                 * If no, close dialog
                 */
                saveDialog.close();
            });
            BorderPane layout = new BorderPane();
            layout.setTop(saveQuestion);
            HBox yesnobuttonBox = new HBox(yesButton,noButton);
            layout.setCenter(yesnobuttonBox);
            saveDialog.setScene(new Scene(layout,200,200));
            saveDialog.show();
        }
        else{
            /*
             * If file is empty, try to save
             */
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(canvas.snapshot(null,null), 
                        null), "png", file);
            } catch (IOException e) {
                System.out.println("Exception occured :" + e.getMessage());
            }
        }
        
        
    }
}
