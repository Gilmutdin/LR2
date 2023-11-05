package functions.operations;
import functions.*;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;

public class TabulatedFunctionOperationService {

    TabulatedFunctionFactory _factory;

    public TabulatedFunctionFactory getFactory() {
        return _factory;
    }

    public void setFactory(TabulatedFunctionFactory _factory) {
        this._factory = _factory;
    }

    //----

    public TabulatedFunctionOperationService(TabulatedFunctionFactory factory) {
        this._factory = factory;
    }

    public TabulatedFunctionOperationService() {
        this._factory = new ArrayTabulatedFunctionFactory();
    }

    //---------

    public static Point[] asPoints(TabulatedFunction tabulatedFunction) {
        Point[] points = new Point[tabulatedFunction.getCount()];
        int idx = 0;
        for (Point point : tabulatedFunction) {
            points[idx] = point;
            idx++;
        }
        return points;
    }

    //---
    @FunctionalInterface // чтобы можно было создавать анон классы лямбдой
    private interface BiOperation {
        public double apply(double u, double v);
    }

    //!! потом перенести в exceptions!!!!!!!!!!!!!!
    public class InconsistentFunctionsException extends Exception {
        public InconsistentFunctionsException(String err) {
            super(err);
        }
    };
    //!!

    private TabulatedFunction doOperation(TabulatedFunction a, TabulatedFunction b, BiOperation operation)
            throws InconsistentFunctionsException {
        //получаем значения точек а и б
        Point[] aPoints = TabulatedFunctionOperationService.asPoints(a);
        Point[] bPoints = TabulatedFunctionOperationService.asPoints(b);
        if(aPoints.length != bPoints.length){
            throw new InconsistentFunctionsException("Разное кол-во табличных точек у функций");
        }
        double[] xValues = new double[aPoints.length];
        double[] yValues = new double[aPoints.length];

        for (int i = 0; i < aPoints.length; i++) {
            if(aPoints[i].x != bPoints[i].x)
                throw new InconsistentFunctionsException("Разные значения x табличных точек у функций");
            xValues[i] = aPoints[i].x;
            yValues[i] = operation.apply(aPoints[i].y, bPoints[i].y);
        }

        var res = this._factory.create(xValues, yValues);
        return res;
    }


    public TabulatedFunction multiply(TabulatedFunction a, TabulatedFunction b)
            throws InconsistentFunctionsException {
        return doOperation(a, b,  (y1, y2) -> y1 * y2 );
    }

    public TabulatedFunction division(TabulatedFunction a, TabulatedFunction b)
            throws InconsistentFunctionsException {
        return doOperation(a, b,  (y1, y2) -> y1 / y2 );
    }
}
