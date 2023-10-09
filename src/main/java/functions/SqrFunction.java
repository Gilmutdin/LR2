package functions;

public class SqrFunction implements MathFunction // квадрат x
{
    public double apply(double x)
    {
        return Math.pow(x,2);
    }
}