package Engine.Math;


import Engine.Components.CollisionComponent;
import Engine.Components.InputComponent;
import Engine.Components.TransformComponent;

// https://www.metanetsoftware.com/technique/tutorialA.html this is the main math code I followed
public class SweptCollision {
    //call components to work with
    TransformComponent transform;
    CollisionComponent collisionOwner;
    CollisionComponent collisionOther;
    InputComponent input;

    //declare variables
    double vector;
    double lengthVector;

    SweptCollision (TransformComponent transformComponent, CollisionComponent owner,
                    InputComponent inputComponent, CollisionComponent other) {

        this.transform = transformComponent;
        this.collisionOwner = owner;
        this.collisionOther = other;
        this.input = inputComponent;

        //maker owner check for collision with other
        owner.checkCollision(other);
    }

    //create a method for handling swept AABB collision
    public void boxBoxCollision() {
        //state what other and owner positions are


        //do the normalization



        //do the dot production



        //do the projection

    }

    //create a method for handling swept for circles

    //create a method for handling swept for circles to boxes



}
