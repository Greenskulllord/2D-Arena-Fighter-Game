package Engine.Components;
import Engine.Core.Component;
import Engine.Core.Entity;
import Engine.Core.GameContext;
import Engine.System.MovementSystem;
import Game.Objects.AttackHitBox;
import Game.Objects.Enemy;
import Input.InputControls;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

//this is a component that handles player input
public class InputComponent implements Component {
    //declare what class to use
    private final InputControls input;
    private final Entity owner;
    GameContext context;

    //declare the base variables
    private final double dashSpeed;
    private double dashDirX;
    private double dashDirY;

    //timers for abilities cooldowns
    private int dashTimer = 100;
    private boolean canMove = true;

    //an attempt at making a component to just add controls to anything
    public InputComponent(Entity Owner, GameContext context, Scene currentScene) {
        //get the owner and input controls, also set speed
        this.owner = Owner;
        this.context = context;
        this.input = context.controls;
        this.dashSpeed = 300;

        addListeners(currentScene);
        addMouseListeners(currentScene);
    }

    @Override
    public void update(double DeltaTime) {

        //get the components
        TransformComponent transform = owner.getComponent(TransformComponent.class);
        MovementComponent move = owner.getComponent(MovementComponent.class);
        double speed = move.speed;
        double ownerX = transform.x;
        double ownerY = transform.y;

        //if there is no transform component, do nothing
        //makes it not crash
        if (transform == null) return;

        //refresh every time
        double dirY = 0.0;
        double dirX = 0.0;

        //constantly update position of mouse
        double worldX = input.getMouseX() - context.camera.getCameraX();
        double worldY = input.getMouseY() - context.camera.getCameraY();

        //for movement
        if (canMove) {

            if (input.isMoveUp()) dirY -= 1;
            if (input.isMoveDown()) dirY += 1;
            if (input.isMoveRight()) dirX += 1;
            if (input.isMoveLeft()) dirX -= 1;

            if (input.onLeftClick()) {
                //calculates distance between two points
                double distance = Math.hypot(ownerX - worldX, ownerY - worldY);
                System.out.println(distance);

                context.spawner.spawn(new AttackHitBox(50, 50, (int) worldX, (int) worldY, owner));

            }

            if (input.onRightClick()) {
                context.spawner.spawn(new Enemy((int) worldX, (int) worldY));

            }

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
            double currentSpeed = input.isSprinting() ? speed * 2.0 : speed;

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
