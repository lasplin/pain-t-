/*
 * *Final Countdown plays in background*
 */
package paintla;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author Lin Asplin
 */
public class HelpMessage {
    public HelpMessage(){
        Stage helpWindow = new Stage();
        Label helpText = new Label("Open file: Ctrl+O\n"
                + "Save file: Ctrl+S\n"
                + "Help: Ctrl+H\nExit: Ctrl+E"
                + "\nTo change image file type, select \"Save As\" "
                + "\nand input the file type in the save window.");
        BorderPane helpBox = new BorderPane();
        helpBox.setCenter(helpText);
        helpWindow.setScene(new Scene(helpBox,300,150));
        helpWindow.show();
    }
}
