package Engine;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class fxWindow extends Application {
    private final int WIDTH = 600;
    private final int HEIGHT = 400;
    private final int tileCount = 32;

    //declare variables
    Group root = new Group(); //visual elements
    Scene scene = new Scene(root, Color.BLACK); //content in stage
    Canvas canvas = new Canvas(WIDTH, HEIGHT);
    GraphicsContext graphics = canvas.getGraphicsContext2D(); //get the 2D graphics (draws shi)
    Rectangle player1 = new Rectangle(50, 50, Color.WHITE);

    //block of code to engine update frames
    AnimationTimer gameLoop = new AnimationTimer() {
        @Override
        public void handle(long l) {
            //clears canvas at 1 frame
            graphics.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            //redraws at 1 frame
            draw(graphics);
        }
    };

    //block of code to run engine
    @Override
    public void start(Stage stage) throws IOException {
        //the loop to update frames
        gameLoop.start();

        //windows properties
        stage.setScene(scene); //scene in stage
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        stage.setTitle("test");

        //canvas properties
        canvas.setWidth(WIDTH);
        canvas.setHeight(HEIGHT);

        //player 1 properties
        player1.setX(canvas.getWidth() / 2);
        player1.setY(canvas.getHeight() / 2);

        //math to split the canvas into 32x32
        int columns = WIDTH / tileCount;
        int rows = HEIGHT / tileCount;

        //draw
        draw(graphics);

        //add canvas to root
        //root getChildren is like adding game object to javafx
        root.getChildren().add(canvas);
        root.getChildren().add(player1);

        //handles keyboard input
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                double px = player1.getTranslateX(); //get player x
                double py = player1.getTranslateY(); //get player y

                //move the rect based on key inputs
                switch(event.getCode()) {

                    //cases to move player object
                    case KeyCode.W -> player1.setTranslateY(py - 10);
                    case KeyCode.S -> player1.setTranslateY(py + 10);
                    case KeyCode.D -> player1.setTranslateX(px + 10);
                    case KeyCode.A -> player1.setTranslateX(px - 10);

                }
            }
        });

        //must always be at the end to show the application
        stage.show();
    }

    //helper method to draw
    public void draw(GraphicsContext graphics) {


    }

        /*===============================================
           this is for icons and stuff for the window
        ================================================*/
//        Image icon = new Image("icon goes here");
//        stage.getIcons().add(icon);


}
