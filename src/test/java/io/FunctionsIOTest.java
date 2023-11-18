package io;

import functions.ArrayTabulatedFunction;
import functions.TabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.*;

public class FunctionsIOTest
{
    @Test
    public void testWriteTabulatedFunction() throws IOException
    {
        File tempFile = new File("temp/writeTest.txt");

        double[] xValues = {2, 4, 6, 8, 10, 12};
        double[] yValues = {1, 3, 5, 7, 9, 11};

        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);
        FunctionsIO.writeTabulatedFunction(writer, function);
        writer.close();
    }

    @Test
    public void testReadTabulatedFunction() throws IOException
    {
        File tempFile = new File("temp/writeTest.txt");

        double[] xValues = {2, 4, 6, 8, 10, 12};
        double[] yValues = {1, 3, 5, 7, 9, 11};

        BufferedReader reader = new BufferedReader(new FileReader(tempFile));
        TabulatedFunctionFactory factory = new ArrayTabulatedFunctionFactory();
        TabulatedFunction result = FunctionsIO.readTabulatedFunction(reader, factory);

        for (int i = 0; i < result.getCount(); i++)
        {
            assertEquals(xValues[i], result.getX(i));
            assertEquals(yValues[i], result.getY(i));
        }
    }

    @Test
    public void testSerializeAndDeserialize() throws IOException, ClassNotFoundException
    {
        File tempFile2 = new File("temp/serialize.bin");

        double[] xValues = {2, 4, 6, 8, 10, 12};
        double[] yValues = {1, 3, 5, 7, 9, 11};

        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);

        FunctionsIO.serialize(new BufferedOutputStream(new FileOutputStream(tempFile2)), function);
        TabulatedFunction function2 = FunctionsIO.deserialize(new BufferedInputStream(new FileInputStream(tempFile2)));

        for (int i = 0; i < function2.getCount(); i++)
        {
            assertEquals(xValues[i], function2.getX(i));
            assertEquals(yValues[i], function2.getY(i));
        }
    }

    @Test
    public void testSerializeXmlAndDeserializeXml() throws IOException
    {
        File tempFile3 = new File("output/serialize.xml");

        double[] xValues = {2, 4, 6, 8, 10, 12};
        double[] yValues = {1, 3, 5, 7, 9, 11};

        ArrayTabulatedFunction function = new ArrayTabulatedFunction(xValues, yValues);
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile3));
        FunctionsIO.serializeXml(writer, function);
        writer.close();

        BufferedReader reader = new BufferedReader(new FileReader(tempFile3));
        TabulatedFunction function3 = FunctionsIO.deserializeXml(reader);

        for (int i = 0; i < function3.getCount(); i++)
        {
            assertEquals(xValues[i], function3.getX(i));
            assertEquals(yValues[i], function3.getY(i));
        }
    }
}