/*
 * *Final Countdown plays in background*
 */
package paintla;

import java.util.Stack;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


/**
 *
 * @author Lin Asplin
 */
public class EditMenu {
    private final GraphicsContext graphicsContext;
    private final Canvas canvas;
    private Stack<WritableImage> undoStack = new Stack<>();
    public EditMenu(GraphicsContext gc, Canvas c,Stack<WritableImage> us){
        graphicsContext = gc;
        canvas = c;
        undoStack = us;
    }
    public void cut(){
        Rectangle borders = new Rectangle();
        canvas.setOnMousePressed((MouseEvent event) -> {
            borders.setX(event.getX());
            borders.setY(event.getY());
            canvas.setOnMousePressed((MouseEvent event1) ->{});
        });
        canvas.setOnMouseReleased((MouseEvent event) -> {
            borders.setWidth(event.getX()-borders.getX());
            borders.setHeight(event.getY()-borders.getY());
            WritableImage crop = new WritableImage(canvas.snapshot(null, null).getPixelReader(),(int)borders.getX(),(int)borders.getY(),(int)borders.getWidth(),(int)borders.getHeight());
            graphicsContext.clearRect(borders.getX(), borders.getY(), borders.getWidth(), borders.getHeight());
            undoStack.push(canvas.snapshot(null, null));
            paste(crop);
            canvas.setOnMouseReleased((MouseEvent event1) ->{});
        });
    }
    public void paste(WritableImage crop){
        canvas.setOnMousePressed((MouseEvent event)->{
            graphicsContext.drawImage(crop, event.getX(), event.getY(), crop.getWidth(), crop.getHeight());
            undoStack.push(canvas.snapshot(null, null));
            canvas.setOnMouseReleased((MouseEvent event1)->{});
        });
    }
    public void zoom(){
        /*
         * Open a window to input zoom value
         */
        Stage zoomWindow = new Stage();
        zoomWindow.setTitle("Zoom");
        Label zoomLabel = new Label("Input how far you want to zoom.");
        TextField zoomText = new TextField("0");
        Button zoomButton = new Button("Zoom");
        zoomButton.setOnAction((ActionEvent event2) -> {
            /*
             * Zoom canvas by value given
             */
            canvas.setScaleX(Integer.parseInt(zoomText.getText()));
            canvas.setScaleY(Integer.parseInt(zoomText.getText()));
        });
        VBox layout = new VBox(zoomLabel,zoomText,zoomButton);
        zoomWindow.setScene(new Scene(layout,200,200));
        zoomWindow.show();
    }
    public void resize(){
        /*
         * Open window for user to input new canvas size
         */
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
            /*
             * Change canvas to given dimensions
             */
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
    public void myText(){
        canvas.setOnMouseDragged((MouseEvent event)->{});
        canvas.setOnMousePressed((MouseEvent event)->{});
        canvas.setOnMouseReleased((MouseEvent event)->{});
        /*
         * Open window for text input
         */
        Stage textWindow = new Stage();
        textWindow.setTitle("Draw Text");
        Label textLabel = new Label("Input your text:");
        TextField text = new TextField();
        Button textButton = new Button("Return to Canvas");
        textButton.setOnAction((ActionEvent event2) -> {
            /*
             * Close window when text entered
             */
            textWindow.close();
        });
        VBox layout = new VBox(textLabel,text,textButton);
        textWindow.setScene(new Scene(layout,200,200));
        textWindow.show();
        
        /*
         * Draw text to canvas on mouse click
         */
        canvas.setOnMouseClicked((MouseEvent event) -> {
            graphicsContext.strokeText(text.getText(), event.getX(), event.getY());
            undoStack.push(canvas.snapshot(null,null));
        });
    }
    public void help(){
        Stage helpWindow = new Stage();
        /*
         * Label containing the text of the helpmessage
         */
        Label helpText = new Label("Open file: Ctrl+O\n"
                + "Save file: Ctrl+S\n"
                + "Help: Ctrl+H\nExit: Ctrl+E"
                + "\nTo change image file type, select \"Save As\" "
                + "\nand input the file type in the save window.");
        /*
         * Place message in layout and layout in the window
         */
        BorderPane helpBox = new BorderPane();
        helpBox.setCenter(helpText);
        helpWindow.setScene(new Scene(helpBox,300,150));
        helpWindow.show();
    }
    public void erase(){
        canvas.setOnMouseClicked((MouseEvent e)->{});
        canvas.setOnMousePressed((MouseEvent e)->{});
        canvas.setOnMouseReleased((MouseEvent e)->{});
        canvas.setOnMouseDragged((MouseEvent event1) -> {
            graphicsContext.clearRect(event1.getX() - 2, event1.getY() - 2, 5, 5);
            undoStack.push(canvas.snapshot(null,null));
        });
    }
}
