package Engine.Components;
import Engine.Core.Component;
import Engine.Core.Entity;
import Engine.Core.GameContext;
import Engine.Data.EntityData;
import Engine.Math.Utils;
import Engine.Math.Vector2D;
import Game.Objects.AttackHitBox;
import Game.Objects.Enemy;
import Input.InputControls;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

//this is a component that handles player input
public class PlayerControllerComponent implements Component {

    //declare what class to use
    private final InputControls input;
    private final Entity owner;


    //tools
    GameContext context;
    Utils utils = new Utils();
    AttackHitBox hitbox;
    Vector2D v;
    private double x;
    private double y;


    //dashing variables
    private final double dashSpeed;
    private double dashDuration;
    private final double maxDashDuration;
    private double currentDashSpeed;
    private double dashTimer = 0;
    private double dashCooldown;
    private double maxDashCooldown;

    private final double attackForce;
    private double attackSpeed;
    private final double maxAttackSpeed;
    private double attackTimer = 0;


    enum state {
        ATTACKING,
        GUARDING,
        PARRYING,
        DASHING,
        MOVING,
        IDLE
    }
    state playerState = state.IDLE; //set idle state

    enum attacks {
        IDLE,
        ATTACK_1,
        ATTACK_2
    }
    attacks attackState = attacks.IDLE; //set current attack state to idle


    //an attempt at making a component to just add controls to anything
    public PlayerControllerComponent(Entity Owner, GameContext context, Scene currentScene, EntityData data) {

        //get the owner and input controls, also set speed
        this.owner = Owner;
        this.context = context;
        this.input = context.controls;

        //dashing
        this.dashSpeed = data.dashSpeed;
        this.maxDashDuration = data.dashDuration;
        this.dashDuration = maxDashDuration;
        this.currentDashSpeed = dashSpeed;
        this.dashCooldown = maxDashCooldown;
        this.maxDashCooldown = 1;


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


        //get the components
        TransformComponent player = owner.getComponent(TransformComponent.class);
        MovementComponent move = owner.getComponent(MovementComponent.class);
        double speed = move.speed;
        double ownerX = player.x;
        double ownerY = player.y;

        //refresh every time
        double dirY = 0.0;
        double dirX = 0.0;
        dashCooldown -= DeltaTime;


        //constantly update position of mouse
        double worldX = input.getMouseX() - context.camera.getCameraX();
        double worldY = input.getMouseY() - context.camera.getCameraY();


        //if statements handling movement
        if (input.isKeyW()) dirY -= 1;
        if (input.isKeyS()) dirY += 1;
        if (input.isKeyD()) dirX += 1;
        if (input.isKeyA()) dirX -= 1;

        v = utils.normalize(dirX, dirY);
        x = v.x; //normalized value
        y = v.y; //normalized value

        if (playerState == state.IDLE || playerState == state.MOVING) {

            if (dirX != 0 || dirY != 0) playerState = state.MOVING;
            else playerState = state.IDLE;

        }


        //attack variables that handle attack stuff
        double radius = 60;
        double[] unit = utils.unit((ownerX - worldX) * -1, (ownerY - worldY) * -1);

        //attacking variables
        double attackDirX = unit[0];
        double attackDirY = unit[1];

        //-------- Input block --------------
        if (input.onLeftHold() && playerState.equals(state.MOVING) || input.onLeftHold() && playerState.equals(state.IDLE)) {

            //run attack class
            attack(attackDirX, attackDirY, player);

            attackState = attacks.ATTACK_1;
            hitbox = new AttackHitBox(50, 50,
                    (ownerX + (attackDirX * radius)) - 8, (ownerY + (attackDirY * radius)) - 8, owner);

            context.spawner.spawn(hitbox);

            //move active hitbox with the player
            if (hitbox != null) {
                TransformComponent hitboxTrans = hitbox.getComponent(TransformComponent.class);

                if (hitboxTrans != null) {
                    hitboxTrans.x = (ownerX + (attackDirX * radius)) - 8;
                    hitboxTrans.y = (ownerY + (attackDirY * radius)) - 8;
                }
            }
        }
        if (input.onRightClick()) {
            attackDirX = unit[0];
            attackDirY = unit[1];

            //run attack class
            attack(attackDirX, attackDirY, player);

            attackState = attacks.ATTACK_2;
            hitbox = new AttackHitBox(25, 25,
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
        if (input.onMiddleClick()) context.spawner.spawn(new Enemy((int) worldX, (int) worldY));
        if (input.isKeyAlt() && dashDuration >= maxDashDuration && (dirX != 0 || dirY != 0) && dashCooldown <= 0) {
            currentDashSpeed = dashSpeed;
            dashCooldown = maxDashCooldown;
            playerState = state.DASHING;
        }



        //block to handle what each player state does
        switch (playerState) {
            case IDLE -> {
                player.velocityX = 0;
                player.velocityY = 0;
            }
            case MOVING -> {
                double currentSpeed = input.isKeyShift() ? speed * 2.0 : speed;

                player.velocityY = y * currentSpeed;
                player.velocityX = x * currentSpeed;
            }
            case DASHING -> {
                dashTimer += DeltaTime;
                currentDashSpeed = utils.easeOutCubic(dashTimer, dashSpeed, -dashSpeed, dashDuration);

                player.velocityX = currentDashSpeed * x;
                player.velocityY = currentDashSpeed * y;

                if (dashTimer >= dashDuration) {
                    dashTimer = 0;

                    player.velocityX = 0;
                    player.velocityY = 0;

                    playerState = state.IDLE;
                    dashDuration = maxDashDuration;
                }
            }
            case ATTACKING -> {
                switch (attackState) {
                    case ATTACK_1, ATTACK_2 -> {
                        attackTimer += DeltaTime;
                        double currentAttackforce = utils.easeOutCubic(attackTimer, attackForce, -attackForce, attackSpeed);

                        player.velocityX = attackDirX * currentAttackforce;
                        player.velocityY = attackDirY * currentAttackforce;

                        if (attackTimer >= attackSpeed) {
                            attackTimer = 0;

                            player.velocityX = 0;
                            player.velocityY = 0;

                            playerState = state.IDLE;
                            attackState = attacks.IDLE;
                            attackSpeed = maxAttackSpeed;
                        }
                    }

                }
            }
        }
    }

    public void addListeners(Scene currentScene) {
        currentScene.addEventFilter(KeyEvent.KEY_PRESSED, input.getKeyPressedHandler());
        currentScene.addEventFilter(KeyEvent.KEY_RELEASED, input.getKeyReleasedHandler());
    }
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
    public void removeMouseListeners(Scene currentScene) {
        currentScene.removeEventFilter(MouseEvent.MOUSE_PRESSED, input.getMousePressedHandler());
        currentScene.removeEventFilter(MouseEvent.MOUSE_RELEASED, input.getMouseReleasedHandler());
        currentScene.removeEventFilter(MouseEvent.MOUSE_MOVED, input.getMouseMovedHandler());
        currentScene.removeEventFilter(MouseEvent.MOUSE_DRAGGED, input.getMouseMovedHandler());
    }
}
