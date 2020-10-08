/*
 * Opens a filechooser and then opens selected image to canvas
 */
package paintla;

import java.util.Stack;
import java.io.File;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Lin Asplin
 */
public class OpenFile {
    public OpenFile(File file,Stage stage,Image img,GraphicsContext graphicsContext, Canvas canvas,Stack<WritableImage> undoStack){
        /*
         *Create FileChooser
         */
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("View Pictures");
        /*
         * Open FileChooser to user's home directory
         */
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));    
        /*
         * Limit FileChooser to image files
         */
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("All Images", "*.*"),
            new FileChooser.ExtensionFilter("JPG", "*.jpg"),
            new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        /*
         * Open FileChooser
         */
        file = fileChooser.showOpenDialog(stage);
        /*
         * Open a file
         */
        if (file != null){
            try{
                /*
                 * Create an image from the file opened and place it in graphicsContext
                 */
                img = new Image(file.toURI().toString());
                /*
                 * Place image on Canvas
                 */
                canvas.setHeight(img.getHeight());
                canvas.setWidth(img.getWidth());
                graphicsContext.drawImage(img,0,0);
                undoStack.push(canvas.snapshot(null, null));
            }catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        }
    } 
}

