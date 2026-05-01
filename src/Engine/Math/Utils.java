package Engine.Math;

public class Utils {

    public Utils() {

    }

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
}
