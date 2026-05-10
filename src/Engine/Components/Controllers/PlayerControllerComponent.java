package Engine.Components.Controllers;
import Engine.Components.MovementComponent;
import Engine.Components.StateComponent;
import Engine.Components.TransformComponent;
import Engine.Core.Component;
import Engine.Core.Entity;
import Engine.Core.GameContext;
import Engine.Data.EntityData;
import Engine.Events.InputEvent;
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

    //classes to use
    private final InputControls input;
    private final Entity owner;

    //tools
    GameContext context;
    Utils utils = new Utils();
    AttackHitBox hitbox;
    Vector2D v;


    //--------------------
    //  Game variables
    //--------------------

    private final double maxDashCooldown;
    private final double maxAttackSpeed;
    private final double maxDashDuration;
    private double lockedAttackDirX;
    private double lockedAttackDirY;
    private double lockedDashDirX;
    private double lockedDashDirY;
    double timer = 0;

    //profiles
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

        //declare profiles
        baseAttackProfile = new BaseProfile(0.1, 0.15, 0.2);
        baseAttack = new AttackProfile(baseAttackProfile, data.attackForce, 50, 50, 60, data.attackSpeed);

        baseDashProfile = new BaseProfile(0.05, 0.1, 0.05);
        baseDash = new DashProfile(baseDashProfile, data.dashSpeed, 50, 0.5, data.dashDuration);

        maxDashCooldown = baseDash.dashCooldown;
        maxDashDuration = baseDash.dashDuration;
        maxAttackSpeed = baseAttack.attackSpeed;


        //listeners
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
        baseDash.dashCooldown -= DeltaTime;


        //constantly update position of mouse
        double worldX = input.getMouseX() - context.camera.getCameraX();
        double worldY = input.getMouseY() - context.camera.getCameraY();


        //if statements handling movement
        if (input.isKeyW()) dirY -= 1;
        if (input.isKeyS()) dirY += 1;
        if (input.isKeyD()) dirX += 1;
        if (input.isKeyA()) dirX -= 1;

        v = utils.normalize(dirX, dirY);
        double x = v.x; //normalized value
        double y = v.y; //normalized value

        StateComponent.state currentState = state.getCurrentState();

        if (currentState == StateComponent.state.IDLE || currentState == StateComponent.state.MOVING) {

            if (dirX != 0 || dirY != 0)  state.changeState(StateComponent.state.MOVING);
            else   state.changeState(StateComponent.state.IDLE);

        }

        //attack variables that handle attack stuff
        double radius = baseAttack.hitBoxRadius;
        double[] unit = utils.unit((ownerX - worldX) * -1, (ownerY - worldY) * -1);
        double attackDirX = unit[0];
        double attackDirY = unit[1];




        //-------- Input block ------------------------------------------------------------------------------------
        if (input.onLeftClick() && hitbox == null &&
                (currentState == StateComponent.state.IDLE || currentState == StateComponent.state.MOVING)) {

            context.bus.publishEvent(new InputEvent(owner, DeltaTime, StateComponent.state.ATTACK_STARTUP, baseAttackProfile));
        }


        timer += DeltaTime;
        if (input.onMiddleClick() && timer >= 2.0) {
            context.spawner.spawn(new Enemy(context, (int) worldX, (int) worldY));
            timer = 0.0;

        }


        if (input.isKeyAlt() && (dirX != 0 || dirY != 0) && baseDash.dashCooldown <= 0
                && (currentState == StateComponent.state.IDLE || currentState == StateComponent.state.MOVING)) {

            context.bus.publishEvent(new InputEvent(owner, DeltaTime, StateComponent.state.DASH_STARTUP, baseDashProfile));
            baseDash.dashCooldown = maxDashCooldown;
        }
        //-------- Input block ------------------------------------------------------------------------------------




        //block to handle what each player state does
        switch (currentState) {
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
                    lockedDashDirX = x;
                    lockedDashDirY = y;
                    state.changeState(StateComponent.state.DASH_ACTIVE);
                }
            }
            case DASH_ACTIVE -> {

                double dashProgress = Math.min(state.stateTimer / baseDash.dashDuration, 1.0);
                double currentDashSpeed = utils.easeOutCubic(dashProgress, baseDash.dashSpeed, -baseDash.dashSpeed, 1.0);

                player.velocityX = currentDashSpeed * lockedDashDirX;
                player.velocityY = currentDashSpeed * lockedDashDirY;

                if (dashProgress >= 1.0) {
                    player.velocityX = 0;
                    player.velocityY = 0;

                    state.changeState(StateComponent.state.DASH_RECOVERY);
                    baseDash.dashDuration = maxDashDuration;
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
                    lockedAttackDirX = attackDirX;
                    lockedAttackDirY = attackDirY;
                    state.changeState(StateComponent.state.ATTACK_ACTIVE);
                }
            }
            case ATTACK_ACTIVE -> {
                double attackProgress = Math.min(state.stateTimer / baseAttack.attackSpeed, 1.0);


                if (hitbox == null) {

                    hitbox = new AttackHitBox(baseAttack.hitBoxWidth, baseAttack.hitBoxHeight,
                            (ownerX + (lockedAttackDirX * radius)) - 8, (ownerY + (lockedAttackDirY * radius)) - 8, owner);

                    context.spawner.spawn(hitbox);
                }

                    //move active hitbox with the player
                    TransformComponent hitboxTrans = hitbox.getComponent(TransformComponent.class);
                    if (hitboxTrans != null) {
                        hitboxTrans.x = (ownerX + (lockedAttackDirX * radius)) - 8;
                        hitboxTrans.y = (ownerY + (lockedAttackDirY * radius)) - 8;
                    }


                    //change state.stateTimer
                    double currentAttackForce = utils.easeOutCubic(attackProgress, baseAttack.attackForce, -baseAttack.attackForce,1.0);
                    player.velocityX = lockedAttackDirX * currentAttackForce;
                    player.velocityY = lockedAttackDirY * currentAttackForce;

                    if (attackProgress >= 1.0) {
                        hitbox = null;
                        player.velocityX = 0;
                        player.velocityY = 0;

                        state.changeState(StateComponent.state.ATTACK_RECOVERY);
                        baseAttack.attackSpeed = maxAttackSpeed;
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