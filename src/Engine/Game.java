package Engine;
import GameComponents.Player;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import keyboardInput.InputControls;
public class Game extends Application {
    private final int WIDTH = 600;
    private final int HEIGHT = 400;

    //declare variables
    Group root = new Group(); //visual elements
    Scene scene = new Scene(root, Color.BLACK); //content in stage
    Canvas canvas = new Canvas(WIDTH, HEIGHT);
    GraphicsContext graphics = canvas.getGraphicsContext2D();//get the 2D graphics (draws shi)
    InputControls input = new InputControls(scene);
    Player player = new Player(input);

    //block of code to run engine
    @Override
    public void start(Stage stage) {
        //add input listeners
        input.addListeners();

        //windows properties
        stage.setScene(scene); //scene in stage
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        stage.setTitle("test");

        //canvas properties
        canvas.setWidth(WIDTH);
        canvas.setHeight(HEIGHT);

        //add canvas to root
        //getChildren is like adding game object to javafx
        root.getChildren().add(canvas);
        root.getChildren().add(player.viewPlayer());

        //player properties
        player.viewPlayer().setX(canvas.getWidth() / 2);
        player.viewPlayer().setY(canvas.getHeight() / 2);

        //math to split the canvas into 32x32
        int tileCount = 32;
        int columns = WIDTH / tileCount;
        int rows = HEIGHT / tileCount;

        //must always be at the end to show the application
        draw(graphics);
        gameLoop.start();
        stage.show();
        root.requestFocus();
    }

    //block of code to engine update frames
    AnimationTimer gameLoop = new AnimationTimer() {

        @Override
        public void handle(long l) {

            graphics.clearRect(0, 0, canvas.getWidth(), canvas.getHeight()); //clear
            draw(graphics); //redraw
        }

    };

    //helper method to draw
    public void draw(GraphicsContext graphics) {
    }
}
