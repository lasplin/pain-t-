/*
 * Now featuring multiple class files!
 */
package paintla;

/**
 *
 * @author Lin Asplin
 */

import java.io.File;
import java.util.Stack;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelReader;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;


public final class PaintLA extends Application {
    //Create empty Image, Canvas, GraphicsContext, and Stacks accessible by all methods
    private File file;
    private Image img;
    private final Canvas canvas = new Canvas();
    private final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
    private final Stack<Shape> undoStack = new Stack<>();
    private final Stack<Shape> redoStack = new Stack<>();

    @Override
    public void start(Stage stage) {
        //Label Stage
        stage.setTitle("Paint: Round 2");
        
        //Allow user to enter width of line
        Label lineChoose = new Label("Enter desired width:");
        TextField lineWidth = new TextField("1");

        //Create a color chooser
        final ColorPicker colorPicker = new ColorPicker();
        colorPicker.setOnAction((ActionEvent event) -> {
            graphicsContext.setStroke(colorPicker.getValue());
            graphicsContext.setFill(colorPicker.getValue());
        });
        
        //Create an Undo button
        Button undoButton = new Button("Undo");
        undoButton.setOnAction((ActionEvent event) -> {
            Undo p = new Undo(img, graphicsContext, canvas, undoStack, redoStack);
        });
        
        //Create a Redo button
        Button redoButton = new Button("Redo");
        redoButton.setOnAction((ActionEvent event) -> {
            Redo p = new Redo(graphicsContext,undoStack,redoStack);
        });
        
        //Create current tool label
        Label toolLabel = new Label("No Tool");
        
        //Place colorpicker, line width info, and buttons in layout
        HBox buttonBox = new HBox(colorPicker,lineChoose,lineWidth,undoButton,redoButton,toolLabel);
        
        //Create Menu Bar
        MenuBar menuBar = new MenuBar();
        
        //Create File dropdown menu
        Menu menuFile = new Menu("File");
        
        //Create MenuItem to open files
        MenuItem open = new MenuItem("Open");
        open.setOnAction((final ActionEvent event) -> {
            OpenFile p = new OpenFile(file,stage,img,graphicsContext,canvas);
        });
        //Set keyboard shortcut for Open
        open.setAccelerator(new KeyCodeCombination(KeyCode.O, 
                KeyCombination.CONTROL_DOWN));
        //Allow user to save to the last file location
        MenuItem save = new MenuItem("Save");
        save.setOnAction((ActionEvent event) -> {
            SmartSave p = new SmartSave(file,canvas);
        });
        
        //Set keyboard shortcut for smart save
        save.setAccelerator(new KeyCodeCombination(KeyCode.S, 
                KeyCombination.CONTROL_DOWN));
        //Set "Save As" to save a file
        MenuItem saveAs = new MenuItem("Save As");
        saveAs.setOnAction((ActionEvent event) -> {
            SaveAs p = new SaveAs(file,stage,img,canvas);
        });
        
        //Add open and save options to File menu
        menuFile.getItems().addAll(open,save,saveAs);
        
        
        Menu menuEdit = new Menu("Edit");
        MenuItem pencil = new MenuItem("Pencil");
        pencil.setOnAction((ActionEvent event) -> {
            MyPencil p = new MyPencil(graphicsContext, canvas, lineWidth);
            toolLabel.setText("Pencil");
        });
        
        //Create a line drawer
        MenuItem line = new MenuItem("Line");
        line.setOnAction((ActionEvent event) -> {
            MyLine p = new MyLine(graphicsContext,canvas,lineWidth,undoStack);
            toolLabel.setText("Line");
        });
        
        //Create a menu item to draw rectangles
        MenuItem rectangle = new MenuItem("Rectangle");
        rectangle.setOnAction((ActionEvent event) -> {
            MyRect p = new MyRect(graphicsContext, canvas,undoStack);
            toolLabel.setText("Rectangle");
        });
        
        //Create a menu item to draw squares
        MenuItem square = new MenuItem("Square");
        square.setOnAction((ActionEvent event) -> {
            MySquare p = new MySquare(graphicsContext, canvas,undoStack);
            toolLabel.setText("Square");
        });
        
        MenuItem triangle = new MenuItem("Triangle");
        triangle.setOnAction((ActionEvent event) ->{
            MyTriangle p = new MyTriangle(graphicsContext,canvas,undoStack);
            toolLabel.setText("Triangle");
        });
        
        //Create a menu item to draw circles
        MenuItem circle = new MenuItem("Circle");
        circle.setOnAction((ActionEvent event) -> {
            MyCircle p = new MyCircle(graphicsContext,canvas,undoStack);
            toolLabel.setText("Circle");
        });
        
        //Create a menu item to draw ellipses
        MenuItem ellipse = new MenuItem("Ellipse");
        ellipse.setOnAction((ActionEvent event) -> {
            MyEllipse p = new MyEllipse(graphicsContext, canvas,undoStack);
            toolLabel.setText("Ellipse");
        });
        
        //Create a text input
        MenuItem text = new MenuItem("Text");
        text.setOnAction((ActionEvent event) ->{
            MyText p = new MyText(graphicsContext,canvas,undoStack);
            toolLabel.setText("Text");
        });
        
        //Create a color grabber
        MenuItem colorDropper = new MenuItem("Color Dropper");
        colorDropper.setOnAction((ActionEvent event) -> {
            canvas.addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent event1) -> {
                WritableImage snapshot = canvas.snapshot(null, null);
                PixelReader pixelReader = snapshot.getPixelReader();
                colorPicker.setValue(pixelReader.getColor((int) event1.getX(), (int) event1.getY()));
            });
            toolLabel.setText("Color Dropper");
        });
        MenuItem erase = new MenuItem("Erase");
        erase.setOnAction((ActionEvent event) -> {
            canvas.addEventFilter(MouseEvent.MOUSE_DRAGGED, (MouseEvent event1) -> {
                graphicsContext.clearRect(event1.getX() - 2, event1.getY() - 2, 5, 5);
            });
        });
        
        menuEdit.getItems().addAll(pencil,line,rectangle,square,triangle,circle,ellipse,text,colorDropper, erase);
        
        Menu menuView = new Menu("View");
        MenuItem zoom = new MenuItem("Zoom");
        zoom.setOnAction((ActionEvent event) -> {
            MyZoom p = new MyZoom(canvas);
        });
        zoom.setAccelerator(new KeyCodeCombination(KeyCode.Z,KeyCombination.CONTROL_DOWN));
        
        MenuItem resize = new MenuItem("Resize");
        resize.setOnAction((ActionEvent event) -> {
            ResizeMessage p = new ResizeMessage(graphicsContext,canvas);
        });
        menuView.getItems().addAll(zoom,resize);
        
        //Create a help menu item
        Menu menuHelp = new Menu("Help");
        MenuItem help = new MenuItem("Help");
        help.setOnAction((ActionEvent event) -> {
            HelpMessage p = new HelpMessage();
        });
        menuHelp.getItems().addAll(help);
        help.setAccelerator(new KeyCodeCombination(KeyCode.H, 
                KeyCombination.CONTROL_DOWN));
        
        //Create "Exit" option
        Menu menuExit = new Menu("Exit");
        //Set "Exit" to exit
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction((ActionEvent event) -> {
            System.exit(0);
        });
        exit.setAccelerator(new KeyCodeCombination(KeyCode.E,
                KeyCombination.CONTROL_DOWN));
        //Place exit button in Exit
        menuExit.getItems().addAll(exit);
        
        //Add all menu pieces to menu bar
        menuBar.getMenus().addAll(menuFile, menuEdit, menuView, menuHelp, menuExit);
        //Create scrollbar
        ScrollPane scrollbar = new ScrollPane();
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
}
