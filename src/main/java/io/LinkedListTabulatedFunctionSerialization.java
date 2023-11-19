package io;

import functions.ArrayTabulatedFunction;
import functions.LinkedListTabulatedFunction;
import functions.TabulatedFunction;
import operations.TabulatedDifferentialOperator;

import java.io.*;

import static io.FunctionsIO.writeTabulatedFunction;

public class LinkedListTabulatedFunctionSerialization {
    public static void main(String[] args)
    {
        double[] xValues = {2, 4, 6, 8, 10, 12};
        double[] yValues = {1, 3, 5, 7, 9, 11};

        // сериализация
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("output/serialized linked list functions.bin")))
        {
            // создание табулированной функции
            TabulatedFunction linkedF = new LinkedListTabulatedFunction(xValues, yValues);

            // нахождение первой и второй производных функции
            TabulatedDifferentialOperator differentialOperator = new TabulatedDifferentialOperator();
            LinkedListTabulatedFunction linkedD1 = (LinkedListTabulatedFunction) differentialOperator.derive(linkedF);
            LinkedListTabulatedFunction linkedD2 = (LinkedListTabulatedFunction) differentialOperator.derive(linkedD1);

            // сериализация функций
            FunctionsIO.serialize(bos, linkedF);
            FunctionsIO.serialize(bos, linkedD1);
            FunctionsIO.serialize(bos, linkedD2);
        }
        catch(IOException exc){
            exc.printStackTrace();
        }

        // десериализация
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream("output/serialized linked list functions.bin")))
        {
            LinkedListTabulatedFunction deserializedlinkedF = (LinkedListTabulatedFunction) FunctionsIO.deserialize(bis);
            LinkedListTabulatedFunction deserializedlinkedD1 = (LinkedListTabulatedFunction) FunctionsIO.deserialize(bis);
            LinkedListTabulatedFunction deserializedlinkedD2 = (LinkedListTabulatedFunction) FunctionsIO.deserialize(bis);

            // вывод значений функций
            System.out.println(deserializedlinkedF.toString());
            System.out.println(deserializedlinkedD1.toString());
            System.out.println(deserializedlinkedD2.toString());
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
