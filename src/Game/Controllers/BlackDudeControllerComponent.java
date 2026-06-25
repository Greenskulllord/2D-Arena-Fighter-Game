package Game.Controllers;
import Engine.Components.CollisionComponent;
import Engine.Components.MovementComponent;
import Engine.Components.StateComponent;
import Engine.Components.TransformComponent;
import Engine.Core.ActiveEntities;
import Engine.Core.Component;
import Engine.Core.Entity;
import Engine.Core.GameContext;
import Engine.Data.Types.EntityData;
import Engine.Events.InputEvent;
import Engine.Math.Utils;
import Engine.Math.Vector2D;
import Engine.Profiles.AttackProfile;
import Engine.Profiles.BaseProfile;
import Game.Objects.AttackHitBox;

public class BlackDudeControllerComponent implements Component {
    Entity owner;
    GameContext context;
    Vector2D v;



    double stunTimer;
    double attackRange;
    double lockedAttackDirX;
    double lockedAttackDirY;
    double distance;
    double timer;
    double randomNumber;


    BaseProfile baseAttack = new BaseProfile(0.5, 0.15, 0.1);
    AttackProfile attack1 = new AttackProfile(baseAttack, 20, 50, 50, 60, 1);
    AttackHitBox hitBox;


    public BlackDudeControllerComponent(Entity owner, GameContext context, EntityData data) {
        this.owner = owner;
        this.context = context;
        this.attackRange = 100;
        this.stunTimer = 1;
        this.timer = 1;

    }


    @Override
    public void update(double DeltaTime) {
        TransformComponent enemy = owner.getComponent(TransformComponent.class);
        MovementComponent move = owner.getComponent(MovementComponent.class);
        StateComponent state = owner.getComponent(StateComponent.class);


        double speed = move.speed;
        double ownerX = enemy.x;
        double ownerY = enemy.y;

        double dirY = 0.0;
        double dirX = 0.0;

        for (Entity A : ActiveEntities.getActiveEntities()) {
            PlayerControllerComponent player = A.getComponent(PlayerControllerComponent.class);

            if (player == null) continue;

            TransformComponent playerTrans = A.getComponent(TransformComponent.class);
            double playerX = playerTrans.x;
            double playerY = playerTrans.y;


            if (state.getCurrentState() == StateComponent.state.IDLE) {
                state.changeState(StateComponent.state.MOVING);
            }


            double[] unit = Utils.unit(playerX - enemy.x, playerY - enemy.y);
            double attackDirX = unit[0];
            double attackDirY = unit[1];
            getAttackProfile(state.currentProfile).attackSpeed -= DeltaTime;

            switch (state.getCurrentState()) {

                case HIT_STUN -> {
                    stunTimer -= DeltaTime;
                    enemy.velocityX = 0.0;
                    enemy.velocityY = 0.0;

                    if (stunTimer <= 0) {
                        state.changeState(StateComponent.state.MOVING);
                        stunTimer = 1;
                    }
                }

                case MOVING -> {

                    //move to player using trans cords with direction
                    dirX += attackDirX;
                    dirY += attackDirY;

                    double disX = ownerX - playerX;
                    double disY = ownerY - playerY;
                    distance = Math.sqrt(disX * disX + disY * disY);

                    if (dirX == 0 && dirY == 0) return;

                    v = Utils.normalize(dirX, dirY);
                    enemy.velocityX = v.x * speed;
                    enemy.velocityY = v.y * speed;

                    timer -= DeltaTime;
                    if (timer <= 0) {
                        randomNumber = Math.random() > 0.5 ? 1 : -1;
                        timer = 1;
                        if (randomNumber == 1) state.changeState(StateComponent.state.CIRCLING);
                    }


                    //start an attack
                    if (distance <= attackRange && getAttackProfile(state.currentProfile).attackSpeed <= 0) {
                        context.bus.publishEvent(new InputEvent(owner, DeltaTime, StateComponent.state.ATTACK_STARTUP, baseAttack));
                        getAttackProfile(state.currentProfile).attackSpeed = 1;

                    }

                }

                case CIRCLING -> {
                    double strafeX = -attackDirY * randomNumber;
                    double strafeY =  attackDirX * randomNumber;

                    double disX = ownerX - playerX;
                    double disY = ownerY - playerY;
                    distance = Math.sqrt(disX * disX + disY * disY);

                    enemy.velocityX = (attackDirX * 0.4 + strafeX * 0.6) * (speed * 2);
                    enemy.velocityY = (attackDirY * 0.4 + strafeY * 0.6) * (speed * 2);


                    timer -= DeltaTime;
                    if (timer <= 0) {
                        randomNumber = Math.random() > 0.5 ? 1 : -1;
                        timer = 1;
                        if (randomNumber == 1) state.changeState(StateComponent.state.MOVING);
                    }


                    //start an attack
                    if (distance <= attackRange && getAttackProfile(state.currentProfile).attackSpeed <= 0) {
                        context.bus.publishEvent(new InputEvent(owner, DeltaTime, StateComponent.state.ATTACK_STARTUP, baseAttack));
                        getAttackProfile(state.currentProfile).attackSpeed = 1;

                    }
                }

                case ATTACK_STARTUP -> {
                    //stop enemy
                    enemy.velocityX = 0;
                    enemy.velocityY = 0;

                    if (hitBox != null) {
                        hitBox = null;
                    }

                    if (state.stateTimer >= state.currentProfile.startUpDuration) {
                        lockedAttackDirX = attackDirX;
                        lockedAttackDirY = attackDirY;

                        state.changeState(StateComponent.state.ATTACK_ACTIVE);
                    }
                }

                case ATTACK_ACTIVE -> {

                    AttackProfile profile = getAttackProfile(state.currentProfile);
                    double radius = profile.hitBoxRadius;
                    double attackProgress = Math.min(state.stateTimer / profile.attackSpeed, 1.0);

                    double playerHalfX = 16;
                    double playerHalfY = 16;

                    double playerCenterX = ownerX + playerHalfX;
                    double playerCenterY = ownerY + playerHalfY;

                    double hitBoxCenterX = playerCenterX + (lockedAttackDirX * radius) + (lockedAttackDirX * playerHalfX);
                    double hitBoxCenterY = playerCenterY + (lockedAttackDirY * radius) + (lockedAttackDirY * playerHalfX);

                    boolean isHorizontalAttack = Math.abs(lockedAttackDirX) >= Math.abs(lockedAttackDirY);

                    double currentWidth = isHorizontalAttack ? profile.hitBoxHeight : profile.hitBoxWidth;
                    double currentHeight = isHorizontalAttack ? profile.hitBoxWidth : profile.hitBoxHeight;

                    double hitBoxX = hitBoxCenterX - (currentWidth / 2);
                    double hitBoxY = hitBoxCenterY - (currentHeight / 2);


                    if (hitBox != null) {

                        CollisionComponent hitboxColl = hitBox.getComponent(CollisionComponent.class);
                        if (hitboxColl != null &&
                                (hitboxColl.width != currentWidth || hitboxColl.height != currentHeight)) {
                            hitBox = null; // force respawn with correct dimensions
                        }
                    }


                    if (hitBox == null) {
                        hitBox = new AttackHitBox(currentWidth, currentHeight, hitBoxX, hitBoxY, owner);
                        context.spawner.spawn(hitBox);
                        System.out.println("hitbox has spawned: " + hitBox);

                    }

                    if (attackProgress >= 1.0) {
                        hitBox = null;
                        enemy.velocityX = 0;
                        enemy.velocityY = 0;

                        state.changeState(StateComponent.state.ATTACK_RECOVERY);
                    }

                }

                case ATTACK_RECOVERY -> {

                    if (state.stateTimer >= state.currentProfile.recoveryDuration) {
                        state.changeState(StateComponent.state.MOVING);

                    }
                }

            }
        }
    }



    private AttackProfile getAttackProfile(BaseProfile base) {
        if (base == baseAttack) return attack1;
        return attack1; // default
    }

}

