package Engine.Core;

public class DeltaTime {
    private long lastUpdate;

    //method to easily call DeltaTime
    public double getDeltaTime(long AnimationTimerHandler) {

        //make last update always equal the handler
        if (lastUpdate == 0) {
            lastUpdate = AnimationTimerHandler;
            return 0;
        }

        //math for DeltaTime
        double dT = (AnimationTimerHandler - lastUpdate) / 1_000_000_000.0;
        lastUpdate = AnimationTimerHandler;

        return dT;
    }


    //getters and setters
    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
