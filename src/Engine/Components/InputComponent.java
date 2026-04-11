package Engine.Components;
import Engine.Core.Component;
import Engine.Core.Entity;
import Input.InputControls;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

//this is a component that handles player input
public class InputComponent implements Component {
    //declare what class to use
    private final InputControls input;
    private final Entity owner;
    private final Scene currentScene;

    //declare the base variables
    private final double speed;
    private final double dashSpeed;
    private double dashDirX;
    private double dashDirY;

    //timers for abilities cooldowns
    private int dashTimer = 100;
    private boolean canMove = true;

    //an attempt at making a component to just add controls to anything
    public InputComponent(Entity Owner, InputControls Input, Scene currentScene) {
        //get the owner and input controls, also set speed
        this.owner = Owner;
        this.input = Input;
        this.speed = 200;
        this.dashSpeed = 300;

        this.currentScene = currentScene;
        addListeners(currentScene);
        addMouseListeners(currentScene);
    }

    @Override
    public void update(double DeltaTime) {

        //get the transform component
        TransformComponent transform = owner.getComponent(TransformComponent.class);

        //if there is no transform component, do nothing
        //makes it not crash
        if (transform == null) return;

        //refresh every time
        double dirY = 0.0;
        double dirX = 0.0;

        //for movement
        if (canMove) {

            if (input.isMoveUp()) dirY -= 1;
            if (input.isMoveDown()) dirY += 1;
            if (input.isMoveRight()) dirX += 1;
            if (input.isMoveLeft()) dirX -= 1;

            if (input.onLeftClick()) System.out.print("\nleft click");
            if (input.onRightClick()) System.out.print("\nright click");
            if (input.onMiddleClick()) System.out.print("\nmiddle click");


            //-----------------------------------------------------------
            //this is for fixing and normalizing the velocity for diagonals

            //get length
            double length = Math.sqrt(dirX * dirX + dirY * dirY);

            //divide direction by length
            if (length > 0) {
                dirX /= length;
                dirY /= length;
            }
            //-----------------------------------------------------------

            //for dashing
            if (input.isDashing() && dashTimer == 100) {
                dashDirX = dirX;
                dashDirY = dirY;
                System.out.print("\ndashing");
                canMove = false;
                dashTimer = 99;
            }

            //calculate if player speed is player sprints
            //the ? is another way to do if, then in java.
            // ':' = then, '?' = if
            double currentSpeed = input.isSprinting() ? speed * 2 : speed;

            //using the transform component, add the speed modifiers here to it
            transform.velocityY = dirY * currentSpeed;
            transform.velocityX = dirX * currentSpeed;
        }

        //for dashing
        if (!canMove) {
            dashTimer -= 1;

            transform.velocityY = dashDirY * dashSpeed;
            transform.velocityX = dashDirX * dashSpeed;

            if (dashTimer <= 0) {

                canMove = true;
                dashTimer = 100;
            }
        }


    }

    //add listeners so program knows when a key is being press
    public void addListeners(Scene currentScene) {
        currentScene.addEventFilter(KeyEvent.KEY_PRESSED, input.getKeyPressedHandler());
        currentScene.addEventFilter(KeyEvent.KEY_RELEASED, input.getKeyReleasedHandler());
    }

    //remove listeners so program doesn't when a key is being press
    public void removeListeners(Scene currentScene) {
        currentScene.removeEventFilter(KeyEvent.KEY_PRESSED, input.getKeyPressedHandler());
        currentScene.removeEventFilter(KeyEvent.KEY_RELEASED, input.getKeyReleasedHandler());
    }

    public void addMouseListeners(Scene currentScene) {
       currentScene.addEventFilter(MouseEvent.MOUSE_PRESSED, input.getMousePressedHandler());
       currentScene.addEventFilter(MouseEvent.MOUSE_RELEASED, input.getMouseReleasedHandler());
       currentScene.addEventFilter(MouseEvent.MOUSE_MOVED, input.getMouseMovedHandler());
       currentScene.addEventFilter(MouseEvent.MOUSE_DRAGGED, input.getMouseMovedHandler());
    }

    //remove listeners so program doesn't when a key is being press
    public void removeMouseListeners(Scene currentScene) {
        currentScene.removeEventFilter(MouseEvent.MOUSE_PRESSED, input.getMousePressedHandler());
        currentScene.removeEventFilter(MouseEvent.MOUSE_RELEASED, input.getMouseReleasedHandler());
        currentScene.removeEventFilter(MouseEvent.MOUSE_MOVED, input.getMouseMovedHandler());
        currentScene.removeEventFilter(MouseEvent.MOUSE_DRAGGED, input.getMouseMovedHandler());
    }

    public int getDashTimer() {
        return dashTimer;
    }

    public void setDashTimer(int dashTimer) {
        this.dashTimer = dashTimer;
    }
}
