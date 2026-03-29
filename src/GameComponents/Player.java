package GameComponents;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import keyboardInput.InputControls;

public class Player {
    private long lastUpdate = 0;
    //player variables
    private int ID = 0; //ID of player
    private double moveSpeed = 200; //speed player moves
    private double playerX; //player X
    private double playerY; //player Y
    private final Rectangle rectangle;

    //call input method
    InputControls input;

    //player is an object
    public Player(InputControls input) {
        this.rectangle = new Rectangle(50, 50, Color.WHITE);
        this.input = input;
        this.ID = ID;
        this.playerX = rectangle.getX();
        this.playerY = rectangle.getY();

        timer.start();
    }

    public Rectangle viewPlayer() {
       return rectangle;
    }

    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long l) {

            if (lastUpdate == 0) {
                lastUpdate = l;
                return;
            }

            //delta time calculations
            double secondsElapsed = (l - lastUpdate) / 1_000_000_000.0;
            lastUpdate = l;

            //get the direction of the player
            double dirX = 0.0;
            double dirY = 0.0;

            //might be better to use nested if's over a switch to handle multiple inputs
            if (input.isMoveUp()) {
                //code to add negative y movement
                dirY -= 1;
            }
            if (input.isMoveDown()) {
                //code to add positive y movement
                dirY += 1;
            }
            if (input.isMoveRight()) {
                //code to add positive x movement
                dirX += 1;
            }
            if (input.isMoveLeft()) {
                //code to add negative x movement
                dirX -= 1;
            }

            //I disliked having to do all of this fuck ass math
            //recreating the entirety of movement fucking sucks

            //get the length
            double length = Math.sqrt(dirX * dirX + dirY * dirY);

            //divide direction by length
            if (length > 0) {
                dirX /= length;
                dirY /= length;
            }

            //calculate if player speed is player sprints
            //the ? is another way to do if, then in java.
            // ':' = then, '?' = if
            double currentSpeed = input.isSprinting() ? moveSpeed * 1.5 : moveSpeed;

            //add speed to the player
            playerX += dirX * currentSpeed * secondsElapsed;
            playerY += dirY * currentSpeed * secondsElapsed;

            //update the position at the end
            rectangle.setTranslateX(playerX);
            rectangle.setTranslateY(playerY);

        }
    };


     /*
    =========================
        Getters and Setter
    =========================
     */

    public double getPlayerX() {
        return playerX;
    }

    public void setPlayerX(double playerX) {
        this.playerX = playerX;
    }

    public double getPlayerY() {
        return playerY;
    }

    public void setPlayerY(double playerY) {
        this.playerY = playerY;
    }
}
