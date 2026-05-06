package Engine.Components;
import Engine.Core.Component;
import Engine.Core.Entity;
import Engine.Core.GameContext;
import Engine.Data.EntityData;
import Engine.Math.Utils;
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

    //tools
    GameContext context;
    Utils utils = new Utils();
    AttackHitBox hitbox;


    //dashing variables
    private double dashDirX;
    private double dashDirY;
    private final double dashSpeed;
    private double dashDuration;
    private final double maxDashDuration;
    private final double Friction;
    private double currentDashSpeed;


    //attacking variables
    private double attackDirX;
    private double attackDirY;
    private final int  attackForce;
    private double attackSpeed;
    private final double maxAttackSpeed;


    //player state
    enum state {
        ATTACKING,
        GUARDING,
        PARRYING,
        DASHING,
        MOVING,
        IDLE
    }

    state playerState = state.IDLE; //set idle state

    //for later
    enum attacks {
        ATTACK_1,
        ATTACK_2
    }



    //an attempt at making a component to just add controls to anything
    public InputComponent(Entity Owner, GameContext context, Scene currentScene, EntityData data) {

        //get the owner and input controls, also set speed
        this.owner = Owner;
        this.context = context;
        this.input = context.controls;

        //dashing
        this.dashSpeed = data.dashSpeed;
        this.maxDashDuration = data.dashDuration;
        this.dashDuration = maxDashDuration;
        this.currentDashSpeed = dashSpeed;
        this.Friction = 0.97;


        //attacking
        this.attackForce = data.attackForce;
        this.maxAttackSpeed = data.attackSpeed;
        this.attackSpeed = maxAttackSpeed;


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
        System.out.print("\n" + playerState);

        //get the components
        TransformComponent player = owner.getComponent(TransformComponent.class);
        MovementComponent move = owner.getComponent(MovementComponent.class);
        double speed = move.speed;
        double ownerX = player.x;
        double ownerY = player.y;


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


        if (playerState == state.IDLE || playerState == state.MOVING) {

            if (dirX != 0 || dirY != 0) playerState = state.MOVING;
            else playerState = state.IDLE;
        }



        //attack block
        //----------------------------------------------------------------------------------------------------
        double radius = 60;
        double[] unit = utils.unit((ownerX - worldX) * -1, (ownerY - worldY) * -1);

        //attack 1
        if (input.onLeftHold() && playerState.equals(state.MOVING) || input.onLeftHold() && playerState.equals(state.IDLE)) {
            attackDirX = unit[0];
            attackDirY = unit[1];

            //run attack class
            attack(attackDirX, attackDirY, player);

            hitbox = new AttackHitBox(50, 50,
                    (ownerX + (attackDirX * radius)) - 8, (ownerY + (attackDirY * radius)) - 8, owner);

            context.spawner.spawn(hitbox);

            //move active hitbox with the player
            if (hitbox != null) {
                TransformComponent hitboxTrans = hitbox.getComponent(TransformComponent.class);

                if (hitboxTrans != null) {
                    hitboxTrans.x = (ownerX + (unit[0] * radius)) - 8;
                    hitboxTrans.y = (ownerY + (unit[1] * radius)) - 8;
                }
            }
        }

        //just spawns a dummy enemy
        if (input.onRightClick()) context.spawner.spawn(new Enemy((int) worldX, (int) worldY));

        //---------------------------------------------------------------------------------------------------------

        //for dashing
        if (input.isDashing() && dashDuration >= maxDashDuration && (dirX != 0 || dirY != 0)) {
            dashDirX = dirX;
            dashDirY = dirY;
            currentDashSpeed = dashSpeed;
            playerState = state.DASHING;
        }




        //block to handle what each player state does
        //---------------------------------------------------------------------------------------------------------
        switch (playerState) {

            case IDLE -> {
                player.velocityX = 0;
                player.velocityY = 0;
            }

            case MOVING -> {

                //this is for fixing and normalizing the velocity for diagonals
                double length = Math.sqrt(dirX * dirX + dirY * dirY);

                //divide direction by length
                if (length > 0) {
                    dirX /= length;
                    dirY /= length;
                }

                //calculate if player speed is player sprints
                //the ? is another way to do if, then in java.
                // ':' = then, '?' = if
                double currentSpeed = input.isSprinting() ? speed * 2.0 : speed;

                //using the transform component, add the speed modifiers here to it
                player.velocityY = dirY * currentSpeed;
                player.velocityX = dirX * currentSpeed;

            }

            case DASHING -> {
                dashDuration -= DeltaTime;
                double easeOut = utils.EaseOutSine(dashDuration);
                double length = Math.sqrt(dashDirX * dashDirX + dashDirY * dashDirY);

                //divide direction by length
                if (length > 0) {
                    dashDirX /= length;
                    dashDirY /= length;
                }

                player.velocityX = (dashDirX * easeOut) * currentDashSpeed;
                player.velocityY = (dashDirY * easeOut) * currentDashSpeed;

                if (dashDuration <= 0) {

                    playerState = state.IDLE;
                    dashDuration = maxDashDuration;
                }
            }

            case ATTACKING -> {
                attackSpeed -= DeltaTime;

                if (attackSpeed <= maxAttackSpeed / 2) {
                    player.velocityX *= Friction;
                    player.velocityY *= Friction;
                }

                if (attackSpeed <= maxAttackSpeed / 3) {
                    player.velocityX *= 0;
                    player.velocityY *= 0;
                }

                if (attackSpeed <= 0) {

                    playerState = state.IDLE;
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
}
