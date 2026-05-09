package Engine.Components.Controllers;
import Engine.Components.MovementComponent;
import Engine.Components.StateComponent;
import Engine.Components.TransformComponent;
import Engine.Core.Component;
import Engine.Core.Entity;
import Engine.Core.GameContext;
import Engine.Data.EntityData;
import Engine.Events.InputEvent;
import Engine.Math.DeltaTime;
import Engine.Math.Utils;
import Engine.Math.Vector2D;
import Engine.Profiles.AttackProfile;
import Engine.Profiles.BaseProfile;
import Engine.Profiles.DashProfile;
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

    BaseProfile baseAttackProfile;
    AttackProfile baseAttack;

    BaseProfile baseDashProfile;
    DashProfile baseDash;

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

        //declare profiles
        baseAttackProfile = new BaseProfile(0.1, 0.2, 0.05);
        baseAttack = new AttackProfile(baseAttackProfile, data.attackForce, 50, 50, 60);

        baseDashProfile = new BaseProfile(0.1, 0.2, 0.05);
        baseDash = new DashProfile(baseDashProfile, data.dashSpeed, 1);


        addListeners(currentScene);
        addMouseListeners(currentScene);
    }


    @Override
    public void update(double DeltaTime) {

        //get the components
        TransformComponent player = owner.getComponent(TransformComponent.class);
        MovementComponent move = owner.getComponent(MovementComponent.class);
        StateComponent state = owner.getComponent(StateComponent.class);
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

        if (state.getCurrentState() == StateComponent.state.IDLE || state.getCurrentState() == StateComponent.state.MOVING) {

            if (dirX != 0 || dirY != 0)  state.changeState(StateComponent.state.MOVING);
            else   state.changeState(StateComponent.state.IDLE);

        }

        //attack variables that handle attack stuff
        double radius = baseAttack.hitBoxRadius;
        double[] unit = utils.unit((ownerX - worldX) * -1, (ownerY - worldY) * -1);
        double attackDirX = unit[0];
        double attackDirY = unit[1];




        //-------- Input block ------------------------------------------------------------------------------------
        if (input.onLeftClick()) context.bus.publishEvent(new InputEvent(owner, DeltaTime, StateComponent.state.ATTACK_STARTUP, baseAttackProfile));

        if (input.onMiddleClick()) context.spawner.spawn(new Enemy((int) worldX, (int) worldY));


        if (input.isKeyAlt() && dashDuration >= maxDashDuration && (dirX != 0 || dirY != 0) && dashCooldown <= 0) {
            context.bus.publishEvent(new InputEvent(owner, DeltaTime, StateComponent.state.DASH_STARTUP, baseDashProfile));

            currentDashSpeed = dashSpeed;
            dashCooldown = maxDashCooldown;
        }
        //-------- Input block ------------------------------------------------------------------------------------






        //block to handle what each player state does
        switch (state.getCurrentState()) {
            case IDLE -> {
                player.velocityX = 0;
                player.velocityY = 0;
            }
            case MOVING -> {
                double currentSpeed = input.isKeyShift() ? speed * 2.0 : speed;

                player.velocityY = y * currentSpeed;
                player.velocityX = x * currentSpeed;
            }
            case DASH_STARTUP -> {

                if (state.stateTimer >= baseDashProfile.startUpDuration) {

                    state.changeState(StateComponent.state.DASH_ACTIVE);
                }
            }
            case DASH_ACTIVE -> {

                currentDashSpeed = utils.easeOutCubic(state.stateTimer, dashSpeed, -dashSpeed, dashDuration);

                player.velocityX = currentDashSpeed * x;
                player.velocityY = currentDashSpeed * y;

                if (state.stateTimer >= dashDuration) {
                    state.stateTimer = 0;
                    player.velocityX = 0;
                    player.velocityY = 0;

                    state.changeState(StateComponent.state.DASH_RECOVERY);
                    dashDuration = maxDashDuration;
                }
            }
            case DASH_RECOVERY -> {

                if (state.stateTimer >= baseDashProfile.recoveryDuration) {
                    state.changeState(StateComponent.state.IDLE);
                }
            }
            case ATTACK_STARTUP -> {
                player.velocityX = 0;
                player.velocityY = 0;

                if (state.stateTimer >= baseAttackProfile.startUpDuration) {
                    state.changeState(StateComponent.state.ATTACK_ACTIVE);
                }
            }
            case ATTACK_ACTIVE -> {

                if (state.stateTimer == 0)  {
                    hitbox = new AttackHitBox(baseAttack.hitBoxWidth, baseAttack.hitBoxHeight,
                            (ownerX + (attackDirX * radius)) - 8, (ownerY + (attackDirY * radius)) - 8, owner);

                    context.spawner.spawn(hitbox);
                }

                //move active hitbox with the player
                if (hitbox != null) {
                    TransformComponent hitboxTrans = hitbox.getComponent(TransformComponent.class);

                    if (hitboxTrans != null) {
                                hitboxTrans.x = (ownerX + (attackDirX * radius)) - 8;
                                hitboxTrans.y = (ownerY + (attackDirY * radius)) - 8;
                    }
                }

                                                            //change state.stateTimer
                double currentAttackForce = utils.easeOutCubic(state.stateTimer, attackForce, -attackForce, attackSpeed);

                player.velocityX = attackDirX * currentAttackForce;
                player.velocityY = attackDirY * currentAttackForce;

                if (state.stateTimer >= attackSpeed) {
                    state.stateTimer = 0;
                    player.velocityX = 0;
                    player.velocityY = 0;

                    state.changeState(StateComponent.state.ATTACK_RECOVERY);
                    attackSpeed = maxAttackSpeed;
                }
            }
            case ATTACK_RECOVERY -> {
                player.velocityX = 0;
                player.velocityY = 0;

                if (state.stateTimer >= baseAttackProfile.recoveryDuration) {
                    state.changeState(StateComponent.state.IDLE);
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