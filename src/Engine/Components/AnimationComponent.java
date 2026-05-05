package Engine.Components;
import Engine.Core.Component;
import Engine.Data.DataBase;
import Engine.Data.EntityData;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class AnimationComponent implements Component {

    public int currentFrame;
    public int frameDelay;
    public ImageView frame = new ImageView();
    public List<Image> currentFrames = new ArrayList<>();
    Timeline timeline;

    double width;
    double height;

    public AnimationComponent (String defaultState, int frameDelay, double width, double height) {
        this.frameDelay = frameDelay;
        this.width = width;
        this.height = height;

        frame.setFitWidth(width);
        frame.setFitHeight(height);

        //set the default animation
        changeState(defaultState);

        //creates a timeline to loop animation and play it
        timeline = new Timeline(new KeyFrame(Duration.millis(frameDelay), e -> {
            frame.setImage(currentFrames.get(currentFrame));
            currentFrame = (currentFrame + 1) % currentFrames.size();
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    //class to change to different animation cycles
    public void changeState(String state) {
        EntityData data = DataBase.getTemplate(state);

        if (data == null) {
            System.out.println("error: Key Frames are currently null, check naming conventions");
            return;
        }

        currentFrames = data.keyFrames;

        //this works now
        if (!currentFrames.isEmpty()) {
            currentFrame = 0;
            frame.setImage(currentFrames.get(currentFrame));
        }

    }

    //class to destroy timelines
    public void destroy() {
        if (timeline != null) {
            timeline.stop();
        }
    }

    @Override
    public void update(double DeltaTime) {


    }
}
