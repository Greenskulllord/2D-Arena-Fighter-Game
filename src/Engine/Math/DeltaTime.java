package Engine.Math;

/**
 * The {@code DeltaTime} class calculates the time elapsed between frames,
 * converting nanoseconds to seconds for smooth physics and animation scaling.
 */
public class DeltaTime {

    private long lastUpdate;
    
    /**
     * Calculates the time difference between the current call and the previous update.
     *
     * @param AnimationTimerHandler The current timestamp in nanoseconds (usually from an AnimationTimer).
     * @return The elapsed time in seconds.
     */
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

    /**
     * Gets the timestamp of the last recorded update.
     *
     * @return The last update time in nanoseconds.
     */
    public long getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Manually sets the last update timestamp.
     *
     * @param lastUpdate The timestamp in nanoseconds.
     */
    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}