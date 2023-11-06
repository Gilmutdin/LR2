package functions;

import exceptions.DifferentLengthOfArraysException;
import exceptions.ArrayIsNotSortedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

class AbstractTabulatedFunctionTest
{
    @Test
    void checkLengthIsTheSame1()
    {
        double[] xValues = {2, 4, 6, 8, 10, 12};
        double[] yValues = {1, 3, 5, 7, 9, 11};
        Assertions.assertDoesNotThrow(() -> AbstractTabulatedFunction.checkLengthIsTheSame(xValues, yValues));
    }
    @Test
    void checkLengthIsTheSame2()
    {
        double[] xValues = {2, 4, 6, 8, 10, 12};
        double[] yValues = {1, 3, 5, 7, 9};
        Assertions.assertThrows(DifferentLengthOfArraysException.class, () -> AbstractTabulatedFunction.checkLengthIsTheSame(xValues, yValues));
    }

    @Test
    void checkSorted1()
    {
        double[] xValues = {2, 4, 6, 8, 10, 12};
        Assertions.assertDoesNotThrow(() -> AbstractTabulatedFunction.checkSorted(xValues));
    }
    @Test
    void checkSorted2()
    {
        double[] xValues = {2, 4, 6, 8, 12, 10};
        Assertions.assertThrows(ArrayIsNotSortedException.class, () -> AbstractTabulatedFunction.checkSorted(xValues));
    }
}