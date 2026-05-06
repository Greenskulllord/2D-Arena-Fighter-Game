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

}