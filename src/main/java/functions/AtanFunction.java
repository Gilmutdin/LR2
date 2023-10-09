package functions;

public class AtanFunction implements MathFunction {
    public double apply(double x) {

        return (Math.atan(x) + Math.atan(x));
    }
}
