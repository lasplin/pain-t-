/*
 * Opens a file chooser to allow user to choose name of file
 */
package paintla;

import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

/**
 *
 * @author Lin Asplin
 */
public class SaveAs {
    public SaveAs(File file, Stage stage, Image img, Canvas canvas){
        /*
         * Create FileChooser
         */
            FileChooser fileChooser = new FileChooser();
            /*
             * Open FileChooser to save
             */
            file = fileChooser.showSaveDialog(stage);
            if (img != null) {
                try {
                    /*
                     *Save image
                     */
                    ImageIO.write(SwingFXUtils.fromFXImage(
                            canvas.snapshot(null,null),null), "png", file);
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
    }
}
