package Engine.Components;
import Engine.Core.Component;

//this is a component that gets added to entities with x and y locations
public class TransformComponent implements Component {

    //declare the variable
    public double x;
    public double y;
    public double velocityX;
    public double velocityY;

    //create the constructor for the component
     public TransformComponent(double startX, double startY) {
         //grab the x and y of object
         this.x = startX;
         this.y = startY;
     }

    @Override
    public void update(double DeltaTime) {

    }
}
