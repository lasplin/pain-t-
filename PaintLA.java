/*
 * We're leaving together
 * But still it's farewell
 */
package paintla;

/**
 *
 * @author Lin Asplin
 */

import java.io.*;
import java.io.IOException;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.input.MouseEvent;
import javafx.event.*;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane;
import javafx.stage.FileChooser;
import javafx.scene.image.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.imageio.*;

 
public final class PaintLA extends Application {
    //Create empty Image, Canvas and GraphicsContext accessible by all methods
    private File file;
    private Image img;
    private Canvas canvas = new Canvas();
    private GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
    
    @Override
    public void start(Stage stage) {
        //Label Stage
        stage.setTitle("Paint: Round 2");
        
        //Create a Menu Bar
        MenuBar menuBar = new MenuBar();
        //Add a "File" option to Menu Bar
        Menu menuFile = new Menu("File");
        
        //Create "Open" option
        MenuItem open = new MenuItem("Open");
        //Assign open to open a file
        open.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(final ActionEvent event) {
                    //Create FileChooser
                    final FileChooser fileChooser = new FileChooser();
                    //Pass file chooser to configuration method
                    configureFileChooser(fileChooser);
                    //Open FileChooser
                    file = fileChooser.showOpenDialog(stage);
                    //Open a file
                    if (file != null) {
                        openFile(file);
                    }
                }
            });
        //Allow user to save to the last file location
        MenuItem save = new MenuItem("Save");
        save.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event){
                try {
                    ImageIO.write(SwingFXUtils.fromFXImage(canvas.snapshot(null,null), 
                        null), "png", file);
                } catch (IOException e) {
                    System.out.println("Exception occured :" + e.getMessage());
                }
            }
        });
        //Set "Save As" to save a file
        MenuItem saveAs = new MenuItem("Save As");
        saveAs.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                //Create FileChooser
                FileChooser fileChooser = new FileChooser();
                //Open FileChooser to save
                file = fileChooser.showSaveDialog(stage);
                if (img != null) {
                    try {
                        //Save image
                        ImageIO.write(SwingFXUtils.fromFXImage(
                                canvas.snapshot(null,null),null), "png", file);
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        });
        menuFile.getItems().addAll(open,save,saveAs);
        
        //Create a help menu item
        Menu helpButton = new Menu("Help");
        MenuItem help = new MenuItem("Help");
        help.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event){
                Stage helpWindow = new Stage();
                Label no = new Label("No.");
                helpWindow.setScene(new Scene(no,400,400));
                helpWindow.show();
            }
        });
        helpButton.getItems().addAll(help);
        
        //Create "Exit" option
        Menu exitButton = new Menu("Exit");
        //Set "Exit" to exit
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                //Exit
                System.exit(0);
            }
        });
        //Place exit button in Exit
        exitButton.getItems().addAll(exit);
        //Add all menu pieces to menu bar
        menuBar.getMenus().addAll(menuFile, helpButton, exitButton);
        
        //Allow user to enter width of line
        Label lineChoose = new Label("Enter desired width:");
        TextField lineWidth = new TextField("1");
        
        //Create a color chooser
        final ColorPicker colorPicker = new ColorPicker();
        colorPicker.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                graphicsContext.setFill(colorPicker.getValue());
            }
        });
        //Create box for input buttons
        HBox buttonBox = new HBox(colorPicker,lineChoose,lineWidth);
        
        //Allow user to drag mouse to create line
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, 
        new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                graphicsContext.beginPath();
                graphicsContext.moveTo(event.getX(), event.getY());
                graphicsContext.stroke();
            }
        });
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, 
                new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                graphicsContext.lineTo(event.getX(), event.getY());
                graphicsContext.stroke();
                graphicsContext.closePath();
                graphicsContext.beginPath();
                graphicsContext.moveTo(event.getX(), event.getY());
            }
        });
        //End line when mouse is released
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, 
                new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                graphicsContext.lineTo(event.getX(), event.getY());
                graphicsContext.stroke();
                graphicsContext.closePath();
            }
        });
        
        //Create scrollbar
        ScrollPane scrollbar = new ScrollPane();
        scrollbar.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollbar.setContent(canvas);
        
        //Place menubar, control buttons and canvas in layout
        VBox layout = new VBox(menuBar, buttonBox, scrollbar);
        //Set scene
        stage.setScene(new Scene(layout));
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
        if (file!=null){
            try{
                //Create an image from the file opened and place it in gc
                img = new Image(file.toURI().toString());
                //Place image on Canvas
                canvas.setHeight(img.getHeight());
                canvas.setWidth(img.getWidth());
                graphicsContext.drawImage(img,0,0);
            }catch(Exception ex){
                //Kill Bill sirens
                System.out.println(ex.getMessage());
            }
        }
    }
}