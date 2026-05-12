package Engine.Components.Controllers;
import Engine.Components.MovementComponent;
import Engine.Components.StateComponent;
import Engine.Components.TransformComponent;
import Engine.Core.ActiveEntities;
import Engine.Core.Component;
import Engine.Core.Entity;
import Engine.Core.GameContext;
import Engine.Data.EntityData;
import Engine.Math.Utils;
import Engine.Math.Vector2D;

public class BlackDudeControllerComponent implements Component {
    Entity owner;
    GameContext context;
    Vector2D v;
    Utils utils = new Utils();
    double stunTimer;


    public BlackDudeControllerComponent(Entity owner, GameContext context, EntityData data) {
        this.owner = owner;
        this.context = context;


    }



    @Override
    public void update(double DeltaTime) {
        TransformComponent enemy = owner.getComponent(TransformComponent.class);
        MovementComponent move = owner.getComponent(MovementComponent.class);
        StateComponent state = owner.getComponent(StateComponent.class);
        stunTimer = 1;

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


            if (state.getCurrentState() == StateComponent.state.IDLE || state.getCurrentState() == StateComponent.state.MOVING ||
                    state.getCurrentState() == StateComponent.state.HIT_STUN) {

                if (dirX == 0 || dirY == 0)  state.changeState(StateComponent.state.MOVING);

            }


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
                    dirX += playerX - ownerX;
                    dirY += playerY - ownerY;

                    if (dirX == 0 && dirY == 0) return;

                    v = utils.normalize(dirX, dirY);
                    enemy.velocityX = v.x * speed;
                    enemy.velocityY = v.y * speed;
                }
            }
        }
    }
}
