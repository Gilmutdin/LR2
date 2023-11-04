package functions.factory;
import functions.*;

public class LinkedListTabulatedFunctionFactory implements TabulatedFunctionFactory{

    public TabulatedFunction create(double[] xValues, double[] yValues) {
        var funk = new LinkedListTabulatedFunction(xValues, yValues);
        return funk;
    }
}
