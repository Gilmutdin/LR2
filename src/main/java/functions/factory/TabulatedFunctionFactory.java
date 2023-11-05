package functions.factory;
import functions.*;
public interface TabulatedFunctionFactory {
    TabulatedFunction create(double[] xValues, double[] yValues);

    // реализация метода по умолчанию - в интерфейсе
    // поэтому не делаем реализации в соотв классах
    default TabulatedFunction createStrict(double[] xValues, double[] yValues) {
        return new StrictTabulatedFunction(this.create(xValues, yValues));
    };

    default TabulatedFunction createUnmodifiable(double[] xValues, double[] yValues) {
        return this.create(xValues, yValues); //!!!! заменить на анмодиф
    };

    default TabulatedFunction createStrictUnmodifiable (double[] xValues, double[] yValues) {
        return new StrictTabulatedFunction( //!!!! заменить на анмодиф
                new StrictTabulatedFunction(
                    this.create(xValues, yValues)
                )
               );
    };
}
