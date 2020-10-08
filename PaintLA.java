/*
 * Paint Application for Dr.&nbsp;Rosasco's CS 325 course: Main File
 * Includes calls of all other classes and starts the actual application.
 */
package paintla;

/**
 *
 * @author Lin Asplin
 */
import java.io.File;
import java.io.IOException;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelReader;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public final class PaintLA extends Application {

    /*
     * Create empty Image, Canvas, GraphicsContext, and Stacks accessible by all methods
     */
    private File file;
    private Image img;
    private final Canvas canvas = new Canvas();
    private final GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
    private final Stack<WritableImage> undoStack = new Stack<>();
    private final Stack<WritableImage> redoStack = new Stack<>();

    @Override
    public void start(Stage stage) {
        /*
         * Label Stage
         */
        stage.setTitle("Paint: Round 6");

        /*
         * Allow user to enter number which decides width of stroke
         */
        Label lineChoose = new Label("Enter desired width:");
        TextField lineWidth = new TextField("1");
        /*
        * Create a color chooser to control Stroke and Fill colors
         */
        Label fill = new Label("Fill");
        graphicsContext.setFill(Color.TRANSPARENT);
        final ColorPicker fillColor = new ColorPicker(Color.TRANSPARENT);
        fillColor.setOnAction((ActionEvent event) -> {
            graphicsContext.setFill(fillColor.getValue());
        });
        Label stroke = new Label("Outline");
        final ColorPicker strokeColor = new ColorPicker(Color.BLACK);
        strokeColor.setOnAction((ActionEvent event) -> {
            graphicsContext.setStroke(strokeColor.getValue());
        });

        /*
         * Button to allow calls to Undo
         */
        UndoRedo undoRedo = new UndoRedo(graphicsContext, canvas, undoStack, redoStack);
        Button undoButton = new Button("Undo");
        undoButton.setOnAction((ActionEvent event) -> {
            undoRedo.undo();
        });

        /*
         * Button to allow calls to Redo
         */
        Button redoButton = new Button("Redo");
        redoButton.setOnAction((ActionEvent event) -> {
            undoRedo.redo();
        });

        /*
         * Label to show which tool is in use
         */
        Label toolLabel = new Label("No Tool");

        Timer saveTimer = new Timer("Save", true);
        TimerTask timerSave = new TimerTask() {
            @Override
            public void run() {
                try {
                    ImageIO.write(SwingFXUtils.fromFXImage(canvas.snapshot(null, null),
                            null), "png", file);
                } catch (IOException e) {
                    System.out.println("Exception occured :" + e.getMessage());
                }
            }
        };
        saveTimer.scheduleAtFixedRate(timerSave,30000,10000);
        Label timerNumber = new Label("Timer Off");
        ToggleButton saveButton = new ToggleButton("Show Autosave Timer");
        saveButton.setOnAction((ActionEvent event) -> {
            if (saveButton.isSelected()) {
                saveButton.setText("Hide Autosave Timer");
                timerNumber.setText("Time until next autosave: " + timerSave.scheduledExecutionTime());
            } else {
                saveButton.setText("Show Autosave Timer");
                timerNumber.setText("Timer Off");
            }
        });
        /*
         * Layout for organization of tool pieces
         */
        HBox buttonBox = new HBox(10, fill, fillColor, stroke, strokeColor, 
                lineChoose, lineWidth, undoButton, redoButton, toolLabel, 
                saveButton, timerNumber);

        /*
         * MenuBar for organization of calls to other class files
         */
        MenuBar menuBar = new MenuBar();

        /*
         * Menu to allow calls to Open, Save, Save As
         */
        Menu menuFile = new Menu("File");

        /*
         * Menuitem to allow calls to OpenFile
         */
        MenuItem open = new MenuItem("Open");
        open.setOnAction((final ActionEvent event) -> {
            OpenFile p = new OpenFile(file, stage, img, graphicsContext, canvas, undoStack);
        });
        /*
         * Set keyboard shortcut for Open
         */
        open.setAccelerator(new KeyCodeCombination(KeyCode.O,
                KeyCombination.CONTROL_DOWN));
        /*
         * MenuItem to allow calls to Save
         */
        MenuItem save = new MenuItem("Save");
        save.setOnAction((ActionEvent event) -> {
            SmartSave p = new SmartSave(file, canvas);
        });

        /*
         * Set keyboard shortcut for smart save
         */
        save.setAccelerator(new KeyCodeCombination(KeyCode.S,
                KeyCombination.CONTROL_DOWN));
        /*
         * MenuItem to allow calls to Save As
         */
        MenuItem saveAs = new MenuItem("Save As");
        saveAs.setOnAction((ActionEvent event) -> {
            SaveAs p = new SaveAs(file, stage, img, canvas);
        });

        /*
         * Place MenuItems for Open, Save, and Save As in File
         */
        menuFile.getItems().addAll(open, save, saveAs);

        VBox menuEdit = new VBox();
        TextField sides = new TextField("3");
        ShapeMenu shapeMenu = new ShapeMenu(graphicsContext, canvas, lineWidth, undoStack);
        EditMenu editMenu = new EditMenu(graphicsContext, canvas, undoStack);
        /*
         * Create a tool to sketch on the image
         */
        Button pencil = new Button("Pencil");
        pencil.setOnAction((ActionEvent event) -> {
            shapeMenu.myPencil();
            /*
             * Change toolLabel to Pencil
             */
            toolLabel.setText("Pencil");
            pencil.setTooltip(new Tooltip("This button allows you to free draw"));
        });

        /*
         * Create a tool to draw lines
         */
        Button line = new Button("Line");
        line.setOnAction((ActionEvent event) -> {
            shapeMenu.myLine();
            /*
             * Change toolLabel to Line
             */
            toolLabel.setText("Line");
            line.setTooltip(new Tooltip("Place a straight line "));
        });

        /*
         * Create a tool to draw rectangles
         */
        Button rectangle = new Button("Rectangle");
        rectangle.setOnAction((ActionEvent event) -> {
            shapeMenu.myRect();
            /*
             * Change toolLabel to Rectangle
             */
            toolLabel.setText("Rectangle");
            rectangle.setTooltip(new Tooltip("Place a Rectangle"));
        });

        /*
         * Create a tool to draw squares
         */
        Button square = new Button("Square");
        square.setOnAction((ActionEvent event) -> {
            shapeMenu.mySquare();
            /*
             * Change toolLabel to Square
             */
            toolLabel.setText("Square");
            square.setTooltip(new Tooltip("Place a square"));
        });

        /*
         * Create a tool to draw triangles
         */
        Button triangle = new Button("Triangle");
        triangle.setOnAction((ActionEvent event) -> {
            shapeMenu.myTriangle();
            /*
             * Change toolLabel to Triangle
             */
            toolLabel.setText("Triangle");
            triangle.setTooltip(new Tooltip("Place a triangle"));
        });

        /*
         * Create a tool to draw circles
         */
        Button circle = new Button("Circle");
        circle.setOnAction((ActionEvent event) -> {
            shapeMenu.myCircle();
            /*
             * Change toolLabel to Circle
             */
            toolLabel.setText("Circle");
            circle.setTooltip(new Tooltip("Place a circle"));
        });

        /*
         * Create a tool to draw ellipses
         */
        Button ellipse = new Button("Ellipse");
        ellipse.setOnAction((ActionEvent event) -> {
            shapeMenu.myEllipse();
            /*
             * Change toolLabel to Ellipse
             */
            toolLabel.setText("Ellipse");
            ellipse.setTooltip(new Tooltip("Place an ellipse"));
        });

        /*
         * Create a tool to add text to an image
         */
        Button text = new Button("Text");
        text.setOnAction((ActionEvent event) -> {
            editMenu.myText();
            /*
             * Change toolLabel to show which tool is in use
             */
            toolLabel.setText("Text");
            text.setTooltip(new Tooltip("Place text"));
        });

        /*
         * Create a tool to grab color from an image
         */
        Button colorDropper = new Button("Color Dropper");
        colorDropper.setOnAction((ActionEvent event) -> {
            canvas.setOnMouseClicked((MouseEvent event1) -> {
                WritableImage snapshot = canvas.snapshot(null, null);
                PixelReader pixelReader = snapshot.getPixelReader();
                fillColor.setValue(pixelReader.getColor((int) event1.getX(), (int) event1.getY()));
                strokeColor.setValue(pixelReader.getColor((int) event1.getX(), (int) event1.getY()));
            });
            /*
             * Change toolLabel to Color Dropper
             */
            toolLabel.setText("Color Dropper");
        });

        /*
         * Create a tool to erase part of an image
         */
        Button erase = new Button("Erase");
        erase.setOnAction((ActionEvent event) -> {
            editMenu.erase();
            /*
             * Change toolLabel to Erase
             */
            toolLabel.setText("Erase");
            erase.setTooltip(new Tooltip("Erase anything"));
        });

        /*
         * Create a tool to cut and paste part of the image
         */
        Button cutPaste = new Button("Cut and Paste");
        cutPaste.setOnAction((ActionEvent event) -> {
            editMenu.cut();
            toolLabel.setText("Cut and Paste");
            cutPaste.setTooltip(new Tooltip("Move and paste an image"));
        });

        Button polygon = new Button("Polygon");
        polygon.setOnAction((ActionEvent event) -> {
            polygon p = new polygon(graphicsContext, canvas, sides, undoStack);
            p.drawPolygon();
            toolLabel.setText("Polygon");
            polygon.setTooltip(new Tooltip("Place a polygon with a chosen number of sides"));
        });
        Label sidesLabel = new Label("Sides of Polygon:");
        HBox polygonsides = new HBox(sidesLabel, sides);
        /*
         * Create a tool with blank actions allowing no tool to be selected
         */
        Button noTool = new Button("No Tool");
        noTool.setOnAction((ActionEvent event) -> {
            shapeMenu.noTool();
            toolLabel.setText("No Tool");
            noTool.setTooltip(new Tooltip("Have no tool selected"));
        });

        /*
         * Add tools to Edit menu
         */
        menuEdit.setSpacing(5);
        menuEdit.getChildren().addAll(pencil, line, rectangle, square, triangle, circle, ellipse, polygon,
                polygonsides, text, colorDropper, erase, cutPaste, noTool);
        /*
         * Allow user to call MyZoom from menubar
         */
        Menu menuView = new Menu("View");
        MenuItem zoom = new MenuItem("Zoom");
        zoom.setOnAction((ActionEvent event) -> {
            editMenu.zoom();
        });
        /*
         * Set keyboard shortcut for Zoom
         */
        zoom.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));

        /*
         * Allow user to call ResizeMessage from menubar
         */
        MenuItem resize = new MenuItem("Resize");
        resize.setOnAction((ActionEvent event) -> {
            editMenu.resize();
        });
        /*
         * Add zoom and resize to menuView
         */
        menuView.getItems().addAll(zoom, resize);

        /*
         * Allow user to call HelpMessage from menubar
         */
        Menu menuHelp = new Menu("Help");
        MenuItem help = new MenuItem("Help");
        help.setOnAction((ActionEvent event) -> {
            editMenu.help();
        });
        /*
         * Add help to menuHelp
         */
        menuHelp.getItems().addAll(help);
        /*
         * Set keyboard shortcut for help
         */
        help.setAccelerator(new KeyCodeCombination(KeyCode.H,
                KeyCombination.CONTROL_DOWN));
        /*
         * Allow user to exit application from the menubar
         */
        Menu menuExit = new Menu("Exit");
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction((ActionEvent event) -> {
            System.exit(0);
        });
        /*
         * Set keyboard shortcut for exit
         */
        exit.setAccelerator(new KeyCodeCombination(KeyCode.E,
                KeyCombination.CONTROL_DOWN));
        /*
         * Add exit to menuExit
         */
        menuExit.getItems().addAll(exit);

        /*
         * Add all menu pieces to menu bar
         */
        menuBar.getMenus().addAll(menuFile, menuView, menuHelp, menuExit);
        /*
         * ScrollPane to access to entirety of canvas
         */
        ScrollPane scrollbar = new ScrollPane(canvas);
        /*
         * Place menubar, control buttons and canvas in vertical layout
         */
        VBox vbox = new VBox(menuBar, buttonBox);
        BorderPane layout = new BorderPane();
        layout.setCenter(scrollbar);
        layout.setTop(vbox);
        layout.setLeft(menuEdit);
        /*
         * Set stage to contain layout and open maximized
         */
        stage.setScene(new Scene(layout));
        stage.setMaximized(true);
        stage.show();
    }

    /*
     * Launch Application
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
}
