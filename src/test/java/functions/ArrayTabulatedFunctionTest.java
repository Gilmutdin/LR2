package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayTabulatedFunctionTest
{
    double[] xValues = {2, 4, 6, 8, 10, 12};
    double[] yValues = {1, 3, 5, 7, 9, 11};

    ArrayTabulatedFunction res = new ArrayTabulatedFunction(xValues, yValues);

    @Test
    void getCount()
    {
        assertEquals(res.getCount(), 6);
    }

    @Test
    void getX()
    {
        assertEquals(res.getX(3), 8);
        assertEquals(res.getX(0), 2);
    }

    @Test
    void getY()
    {
        assertEquals(res.getY(1), 3);
        assertEquals(res.getY(5), 11);
    }

    @Test
    void setY()
    {
        ArrayTabulatedFunction res2 = new ArrayTabulatedFunction(xValues, yValues);
        res2.setY(0, 14);
        assertEquals(res2.getY(0), 14);
    }

    @Test
    void indexOfX()
    {
        assertEquals(res.indexOfX(10), 4);
        assertEquals(res.indexOfX(1), -1);
    }

    @Test
    void indexOfY()
    {
        assertEquals(res.indexOfY(3), 1);
        assertEquals(res.indexOfY(12), -1);
    }

    @Test
    void leftBound()
    {
        assertEquals(res.leftBound(), 2);
        assertNotEquals(res.leftBound(), 12);
    }

    @Test
    void rightBound()
    {
        assertEquals(res.rightBound(), 12);
        assertNotEquals(res.rightBound(), 10);
    }

    @Test
    void floorIndexOfX()
    {
        assertEquals(res.floorIndexOfX(4), 1);
        assertEquals(res.floorIndexOfX(7), 2);
        assertEquals(res.floorIndexOfX(1), 0);
        assertEquals(res.floorIndexOfX(15), 6);
    }

    @Test
    void extrapolateLeft()
    {
        assertEquals(res.extrapolateLeft(1), 0);
        assertEquals(res.extrapolateLeft(-2), -3);
    }

    @Test
    void extrapolateRight()
    {
        assertEquals(res.extrapolateRight(13), 12);
        assertEquals(res.extrapolateRight(20), 19);
    }

    @Test
    void interpolate()
    {
        assertEquals(res.interpolate(5, 2), 4);
        assertEquals(res.interpolate(11, 2), 10);
        assertEquals(res.interpolate(15, 1), 14);
    }

    @Test
    void apply()
    {
        assertEquals(res.apply(1), 0);
        assertEquals(res.apply(13), 12);
        assertEquals(res.apply(4), 3);
        assertEquals(res.apply(5), 4);
    }
}