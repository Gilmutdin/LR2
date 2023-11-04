package functions.operations;
import functions.*;
public class TabulatedFunctionOperationService {
    public static Point[] asPoints(TabulatedFunction tabulatedFunction){
        Point[] points = new Point[tabulatedFunction.getCount()];
        int idx = 0;
        for (Point point : tabulatedFunction) {
            points[idx] = point;
            idx++;
        }
        return points;
    }
}
