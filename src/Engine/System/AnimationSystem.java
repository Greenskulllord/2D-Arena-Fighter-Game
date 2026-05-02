package Engine.System;
import Engine.Components.RenderComponent;
import Engine.Core.Entity;
import Engine.Data.DataBase;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.List;
import Engine.Data.EntityData;

public class AnimationSystem {

    List<Image> frame;
    int currentFrame;
    float frameTime;
    float frameDuration;

    //the system to animate things
    public AnimationSystem() {


    }

    //animate the target
    public ImageView animate(String AnimationName, Entity owner, float duration, double DeltaTime) {

        //get the owners image node
        RenderComponent render = owner.getComponent(RenderComponent.class);
        ImageView image = (ImageView) render.getNode();

        //run the frame changer timer
        frameDuration = duration;

        frameTimer(AnimationName, DeltaTime);
        ImageView result = getFrame(currentFrame, frame);

        image.setImage(result.getImage());

        return image;
    }

    //the timer that changes frames on set intervals
    public void frameTimer(String AnimationName, double DeltaTime) {
        frameTime += (float) DeltaTime;

        EntityData data = DataBase.getTemplate(AnimationName);
        frame = data.keyFrames;

        if (frameTime >= frameDuration) {
            currentFrame++;

            frameTime = 0;
        }

        if (currentFrame >= frame.size()) {
            currentFrame = 0;
        }
    }

    //class to get each png frame of the animation
    public ImageView getFrame(int currentFrame, List<Image> frame) {
        ImageView image = new ImageView(frame.get(currentFrame));

        return image;
    }


}
