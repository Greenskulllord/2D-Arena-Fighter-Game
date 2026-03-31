package Engine.Components;
import Engine.Core.Component;
import Engine.Core.Entity;
import KeyboardInput.InputControls;

public class InputComponent implements Component {
    //declare what class to use
    private final InputControls input;
    private final Entity owner;

    //declare the base variables
    private final double speed;


    //an attempt at making a component to just add controls to anything
    public InputComponent(Entity Owner, InputControls Input) {
        //get the owner and input controls, also set speed
        this.owner = Owner;
        this.input = Input;
        this.speed = 200;
    }

    @Override
    public void update(double DeltaTime) {

        //get the transform component
        TransformComponent transform = owner.getComponent(TransformComponent.class);

        //if there is no transform component, do nothing
        //makes it not crash
        if (transform != null) {
            //refresh every time
            double dirY = 0.0;
            double dirX = 0.0;

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


            //calculate if player speed is player sprints
            //the ? is another way to do if, then in java.
            // ':' = then, '?' = if
            double currentSpeed = input.isSprinting() ? speed * 1.5 : speed;

            //using the transform component, add the speed modifiers here to it
            transform.velocityY = dirY * currentSpeed;
            transform.velocityX = dirX * currentSpeed;

        }
    }
}
