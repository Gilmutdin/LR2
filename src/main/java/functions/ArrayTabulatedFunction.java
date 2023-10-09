package functions;

import java.util.Arrays;
public class ArrayTabulatedFunction extends AbstractTabulatedFunction
{
    public ArrayTabulatedFunction(double[] xValues, double[] yValues)
    {
        super();
        this.xValues = Arrays.copyOf(xValues, xValues.length);
        this.yValues = Arrays.copyOf(yValues, yValues.length);
        this.count = xValues.length;
    }

    public ArrayTabulatedFunction(MathFunction source, double xFrom, double xTo, int count)
    {
        if (xFrom > xTo)
        {
            double temp = xFrom;
            xFrom = xTo;
            xTo = temp;
        }

        this.count = count;
        this.xValues = new double[count];
        this.yValues = new double[count];

        if (xFrom == xTo)
        {
            Arrays.fill(xValues, xFrom);
            Arrays.fill(yValues, source.apply(xFrom));
        }
        else
        {
            double step = (xTo - xFrom) / (count - 1);
            for (int i = 0; i < count; i++)
            {
                xValues[i] = xFrom + i * step;
                yValues[i] = source.apply(xValues[i]);
            }
        }
    }
    public int getCount()
    {
        return this.count;
    }
    public double getX(int index)
    {
        return xValues[index];
    }
    public double getY(int index)
    {
        return yValues[index];
    }
    public void setY(int index, double value)
    {
        yValues[index] = value;
    }
    public int indexOfX(double x)
    {
        for (int i = 0; i < this.count; i++)
        {
            if (xValues[i] == x)
                return i;
        }
        return -1;
    }
    public int indexOfY(double y)
    {
        for (int i = 0; i < this.count; i++)
        {
            if (yValues[i] == y)
                return i;
        }
        return -1;
    }
    public double leftBound()
    {
        return xValues[0];
    }
    public double rightBound()
    {
        return xValues[count-1];
    }
    public int floorIndexOfX(double x)
    {
        double maxx = Double.MIN_VALUE;
        int ind = 0;
        for (int i = 0; i < count; i++)
        {
            if (xValues[i] == x)
                return i;
            else
            {
                if ((xValues[i] < x) && (xValues[i] > maxx))
                {
                    maxx = xValues[i];
                    ind = i;
                }
            }
        }
        if ((ind == count-1) && (rightBound() != x))
            return ind+1;
        else
            return ind;
    }
    public double extrapolateLeft(double x)
    {
        if (count == 1)
            return yValues[0];
        else
            return interpolate(x, 0);
    }
    public double extrapolateRight(double x)
    {
        if (count == 1)
            return yValues[0];
        else
            return interpolate(x, count - 2);
    }
    public double interpolate(double x, int floorIndex)
    {
        if (count == 1)
        {
            return yValues[0];
        }
        double leftX = xValues[floorIndex];
        double leftY = yValues[floorIndex];
        double rightX = xValues[floorIndex + 1];
        double rightY = yValues[floorIndex + 1];
        return interpolate(x, leftX, rightX, leftY, rightY);
    }
    public double apply(double x)
    {
        if (x < leftBound())
            return extrapolateLeft(x);
        else
        {
            if (x > rightBound())
                return extrapolateRight(x);
            else
            {
                int index = indexOfX(x);
                if (index != -1)
                    return getY(index);
                else
                {
                    index = floorIndexOfX(x);
                    return interpolate(x, index);
                }
            }
        }
    }
}