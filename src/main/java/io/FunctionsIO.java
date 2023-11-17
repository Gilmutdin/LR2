package io;
import functions.*;
import functions.factory.*;

import java.io.*;
final class FunctionsIO {
    private FunctionsIO(){
        throw new UnsupportedOperationException();
    }
    public static void writeTabulatedFunction(BufferedOutputStream outputStream, TabulatedFunction function) throws IOException {
        DataOutputStream sout = new DataOutputStream(outputStream);
        // записать число значений таблицы count
        sout.writeInt(function.getCount());
        // записать в поток все x и y
        for (Point point : function) {
            sout.writeDouble(point.x);
            sout.writeDouble(point.y);
        }
        sout.flush();
    }

    public static TabulatedFunction readTabulatedFunction(BufferedInputStream inputStream, TabulatedFunctionFactory factory) throws IOException {
        DataInputStream sin = new DataInputStream(inputStream);
        // считать число значений таблицы count
        int cnt = sin.readInt();
        // записать все x и y
        double[] xValues = new double[cnt];
        double[] yValues = new double[cnt];
        for (int i = 0; i < cnt; i++){
            xValues[i] = sin.readDouble();
            yValues[i] = sin.readDouble();
        }
        return factory.create(xValues, yValues);
    }

    // !! дописать
    public static TabulatedFunction readTabulatedFunction(BufferedReader reader, TabulatedFunctionFactory factory) throws IOException {

        return null;
    }
}
