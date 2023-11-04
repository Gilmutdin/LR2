package functions.factory;
import functions.*;

public class ArrayTabulatedFunctionFactory implements TabulatedFunctionFactory{
    @Override
    public TabulatedFunction create(double[] xValues, double[] yValues) {
        var funk = new ArrayTabulatedFunction(xValues, yValues);
        return funk;
    }
}
