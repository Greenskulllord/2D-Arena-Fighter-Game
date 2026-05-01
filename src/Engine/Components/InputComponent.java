package Engine.Components;
import Engine.Core.Component;
import Engine.Core.Entity;
import Engine.Core.GameContext;
import Engine.Math.Utils;
import Game.Objects.AttackHitBox;
import Game.Objects.Enemy;
import Input.InputControls;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.Arrays;

//this is a component that handles player input
public class InputComponent implements Component {

    //declare what class to use
    private final InputControls input;
    private final Entity owner;

    //tools
    GameContext context;
    Utils utils = new Utils();
    AttackHitBox hitbox;

    //declare the base variables
    private final double dashSpeed;
    private final double attackForce;
    private double dashDirX;
    private double dashDirY;
    private double attackDirX;
    private double attackDirY;

    //player state
    enum state {
        ATTACKING,
        DASHING,
        MOVING,
    }

    //for later
    enum attacks {
        ATTACK_1,
        ATTACK_2
    }

    //timers for abilities cooldowns
    private int dashTimer = 100;
    private int attackSpeed = 50;
    private int maxAttackSpeed = 60;
    state playerState = state.MOVING;

    //an attempt at making a component to just add controls to anything
    public InputComponent(Entity Owner, GameContext context, Scene currentScene) {
        //get the owner and input controls, also set speed
        this.owner = Owner;
        this.context = context;
        this.input = context.controls;
        this.dashSpeed = 500;
        this.attackForce = 100;

        addListeners(currentScene);
        addMouseListeners(currentScene);
    }

    public void attack (double attackDirX, double attackDirY, TransformComponent transform) {
        if (playerState == state.ATTACKING) {
            return;
        }

        playerState = state.ATTACKING;
        attackSpeed = maxAttackSpeed;

        transform.velocityX = attackDirX * attackForce;
        transform.velocityY = attackDirY * attackForce;
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

        if (input.isMoveUp()) dirY -= 1;
        if (input.isMoveDown()) dirY += 1;
        if (input.isMoveRight()) dirX += 1;
        if (input.isMoveLeft()) dirX -= 1;

        //attack block
        //----------------------------------------------------------------------------------------------------
        double radius = 60;
        double[] unit = utils.unit((ownerX - worldX) * -1, (ownerY - worldY) * -1);

        //attack 1
        if (input.onLeftHold() && !playerState.equals(state.ATTACKING)) {
            attackDirX = unit[0];
            attackDirY = unit[1];

            //run attack class
            attack(attackDirX, attackDirY, transform);

            hitbox = new AttackHitBox(50, 50,
                    (ownerX + (unit[0] * radius)) - 8, (ownerY + (unit[1] * radius)) - 8, owner);

            context.spawner.spawn(hitbox);
        }

        //move active hitbox with the player
        if (hitbox != null) {
            TransformComponent hitboxTrans = hitbox.getComponent(TransformComponent.class);

            if (hitboxTrans != null) {
                hitboxTrans.x = (ownerX + (unit[0] * radius)) - 8;
                hitboxTrans.y = (ownerY + (unit[1] * radius)) - 8;
            }
        }
        //---------------------------------------------------------------------------------------------------------

        if (input.onRightClick()) {
            context.spawner.spawn(new Enemy((int) worldX, (int) worldY));

        }

        //for movement
        if (playerState == state.MOVING) {

            //movement block
            //---------------------------------------------------------------------------------------------------------

            //this is for fixing and normalizing the velocity for diagonals
            double length = Math.sqrt(dirX * dirX + dirY * dirY);

            //divide direction by length
            if (length > 0) {
                dirX /= length;
                dirY /= length;
            }

            //for dashing
            if (input.isDashing() && dashTimer == 100) {
                dashDirX = dirX;
                dashDirY = dirY;

                playerState = state.DASHING;
                dashTimer = 99;
            }

            //calculate if player speed is player sprints
            //the ? is another way to do if, then in java.
            // ':' = then, '?' = if
            double currentSpeed = input.isSprinting() ? speed * 2.0 : speed;

            //using the transform component, add the speed modifiers here to it
            transform.velocityY = dirY * currentSpeed;
            transform.velocityX = dirX * currentSpeed;
            //---------------------------------------------------------------------------------------------------------
        }


        //block to handle what each player state does
        //---------------------------------------------------------------------------------------------------------
        switch (playerState) {
            case DASHING -> {
                dashTimer -= 1;

                transform.velocityY = dashDirY * dashSpeed;
                transform.velocityX = dashDirX * dashSpeed;

                if (dashTimer <= 0) {

                    playerState = state.MOVING;
                    dashTimer = 100;
                }
            }

            case ATTACKING -> {
                attackSpeed -= 1;

                if (attackSpeed <= maxAttackSpeed / 2) {
                    transform.velocityX = 0;
                    transform.velocityY = 0;
                }

                if (attackSpeed <= 0) {


                    playerState = state.MOVING;
                    attackSpeed = maxAttackSpeed;
                }

            }
            //---------------------------------------------------------------------------------------------------------




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
