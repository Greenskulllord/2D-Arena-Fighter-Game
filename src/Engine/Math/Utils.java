package Engine.Math;

/**
 * Utility class providing common mathematical operations for engine calculations
 */

public class Utils {

    public Utils() {

    }

    /**
     * Calculates the unit vector (direction) of a 2D vector defined by X and Y.
     * This process normalizes the vector so that its magnitude equals 1.0.
     * If the magnitude is 0, it returns a vector of [0, 0] to avoid division by zero.
     *
     * @param X The horizontal component of the vector.
     * @param Y The vertical component of the vector.
     * @return A double array of size 2 where index 0 is the normalized X and index 1 is the normalized Y.
     */
    public double[] unit(double X, double Y) {

        double[] array = new double[2];
        double magnitude = Math.sqrt(X * X + Y * Y);

        //don't divide by zero
        if (magnitude == 0) {
            array[0] = 0;
            array[1] = 0;
        }
        else {
            array[0] = X / magnitude;
            array[1] = Y / magnitude;

        }

        return array;
    }

    /**
     * Applies an "Ease Out" sine interpolation. This is typically used to
     * smoothly decelerate an object as it approaches its destination.
     *
     * @param time The current progress value (typically between 0.0 and 1.0).
     * @return The interpolated value representing the eased position or speed.
     */
    public double EaseOutSine (double time) {
        return Math.sin((time * Math.PI) / 2);
    }

    /** @function easeInCubic (t, b, c, d)
     * @description Calculates a value on an easing curve that starts slowly and then accelerates.
     *              Represents the beginning of an acceleration curve.
     * @param time The current time or progress of the animation, typically a counter that goes from 0 to d. (e.g., a timer variable).
     * @param beginningValue The beginning value of the property you are animating (e.g., the initial x-coordinate or alpha value).
     * @param change The total change in the value over the entire animation. This is calculated as: (end_value - beginning_value).
     * @param duration The total duration of the animation, measured in the same units as 't' (usually game frames/steps).
     */
    public double easeInCubic(double time, double beginningValue, double change, double duration) {
        if (duration == 0) {
            return beginningValue + change;
        }

        time /= duration;
        return change * time * time * time +  beginningValue;
    }

    /** @function easeInCubic (t, b, c, d)
     * @description Calculates a value on an easing curve that starts fast and then deccelerates towards the end.
     *              Represents the beginning of an acceleration curve.
     * @param time The current time or progress of the animation, typically a counter that goes from 0 to d. (e.g., a timer variable).
     * @param beginningValue The beginning value of the property you are animating (e.g., the initial x-coordinate or alpha value).
     * @param change The total change in the value over the entire animation. This is calculated as: (end_value - beginning_value).
     * @param duration The total duration of the animation, measured in the same units as 't' (usually game frames/steps).
     */
    public double easeOutCubic(double time, double beginningValue, double change, double duration) {
        time /= duration;
        time --;

        return change * (time * time * time + 1) + beginningValue;
    }

    public Vector2D normalize(double directionX, double directionY) {
        double length = Math.sqrt(directionX * directionX + directionY * directionY);
        double dirX = directionX;
        double dirY = directionY;

        if (length > 0) {
            dirX /= length;
            dirY /= length;
        }

        return new Vector2D(dirX, dirY);
    }
}