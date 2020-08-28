/*
 *Final Countdown plays in background*
 */
package paint1;

/**
 *
 * @author Lin Asplin
 */
// Java program to create a combo box and add event handler to it 
import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javax.imageio.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.embed.swing.SwingFXUtils;
 
public final class Paint1 extends Application {
    //Create empty ImageView accessible by all methods
    ImageView view = new ImageView();
    @Override
    public void start(Stage stage) {
        //Label Stage
        stage.setTitle("Paint: Round 1");
        
        //Create a Menu Bar
        MenuBar menuBar = new MenuBar();
        //Add a "File" option to Menu Bar
        Menu menuFile = new Menu("File");
        menuBar.getMenus().addAll(menuFile);
        
        //Create dropdown menu from File
        
        //Create "Open" option
        MenuItem open = new MenuItem("Open");
        //Assign open to open a file
        open.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent e) {
                    //Create FileChooser
                    final FileChooser fileChooser = new FileChooser();
                    //Pass file chooser to configuration method
                    configureFileChooser(fileChooser);
                    //Open FileChooser
                    File file = fileChooser.showOpenDialog(stage);
                    //Open a file
                    if (file != null) {
                        openFile(file);
                    }
                }
            });
        
        //Create "Save" option
        MenuItem save = new MenuItem("Save");
        //Set "Save" to save a file
        save.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                //Create FileChooser
                FileChooser fileChooser = new FileChooser();
                //Open FileChooser to save
                File file = fileChooser.showSaveDialog(stage);
                //If "view" ImageView holds a previously chosen image
                if (view.getImage() != null) {
                    try {
                        //Save image
                        ImageIO.write(SwingFXUtils.fromFXImage(view.getImage(),
                            null), "png", file);
                    //Unless you can't
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        });
        //Create "Exit" option
        MenuItem exit = new MenuItem("Exit");
        //Set "Exit" to exit
        exit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                //Exit
                System.exit(0);
            }
        });
        //Add menu items to menu
        menuFile.getItems().addAll(open,save,exit);   
        
        //Place menu and image in layout
        VBox layout = new VBox(menuBar,view);
        //Set scene
        stage.setScene(new Scene(layout));
        //Open stage at full screen
        stage.setMaximized(true);
        stage.show();
    }
 
    public static void main(String[] args) {
        Application.launch(args);
    }
 
    private static void configureFileChooser(
        final FileChooser fileChooser) {      
            fileChooser.setTitle("View Pictures");
            fileChooser.setInitialDirectory(
                //Open FileChooser to your home directory
                new File(System.getProperty("user.home"))
            );    
            //Limit FileChooser to image files
            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
            );
    }
 
    private void openFile(File file) {
        //Create an image from the file opened and place it in ImageView
        view.setImage(new Image(file.toURI().toString()));
        //Keep image at current ratio
        view.setPreserveRatio(true);
    }
}