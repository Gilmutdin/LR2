package functions.operations;

import functions.LinkedListTabulatedFunction;
import functions.factory.ArrayTabulatedFunctionFactory;
import functions.factory.LinkedListTabulatedFunctionFactory;
import functions.factory.TabulatedFunctionFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TabulatedFunctionOperationServiceTest {

    @Test
    void getFactory() {
        TabulatedFunctionFactory factoryLinked = new LinkedListTabulatedFunctionFactory();
        TabulatedDifferentialOperator op = new TabulatedDifferentialOperator(factoryLinked);
        assertEquals(factoryLinked, op.getFactory());
    }

    @Test
    void setFactory() {
        TabulatedFunctionFactory factoryLinked = new LinkedListTabulatedFunctionFactory();
        TabulatedDifferentialOperator op = new TabulatedDifferentialOperator();
        op.setFactory(factoryLinked);
        assertEquals(factoryLinked, op.getFactory());
    }

    @Test
    void constructor() {
        // если без параметра то фактори с массивом
        TabulatedDifferentialOperator op = new TabulatedDifferentialOperator();
        ArrayTabulatedFunctionFactory fac = new ArrayTabulatedFunctionFactory();
        assertInstanceOf(fac.getClass(), op._factory);
    }

    @Test
    void asPoints() {
        ///// напишет напарник
    }

    @Test
    void multiplyTest() {
        var op = new TabulatedFunctionOperationService();

        double[] xValues1 = {2, 3, 4};
        double[] yValues1 = {2, 2, 2};
        double[] yValues2 = {11, 22, 33};

        // с линкед лист
        var func1 = new LinkedListTabulatedFunction(xValues1, yValues1);
        var func2 = new LinkedListTabulatedFunction(xValues1, yValues2);
        var factLink = new LinkedListTabulatedFunctionFactory();
        op.setFactory(factLink);

        var res = assertDoesNotThrow( () -> op.multiply(func1, func2) );
        for(int i = 0; i < res.getCount(); i++){
            assertEquals(yValues1[i] * yValues2[i], res.getY(i));
        }

        // с аррай
        var funcA1 = new LinkedListTabulatedFunction(xValues1, yValues1); //!! !!!
        var funcA2 = new LinkedListTabulatedFunction(xValues1, yValues2); //!!
        var factArr = new ArrayTabulatedFunctionFactory();
        op.setFactory(factArr);

        var resA = assertDoesNotThrow( () -> op.multiply(funcA1, funcA2) );
        for(int i = 0; i < resA.getCount(); i++){
            assertEquals(yValues1[i] * yValues2[i], res.getY(i));
        }

        // проверка исключений
        double[] xValues3 = {12, 13, 14}; // разные Иксы
        var func3 = new LinkedListTabulatedFunction(xValues3, yValues1);
        var ex3 = assertThrows(TabulatedFunctionOperationService.InconsistentFunctionsException.class,
                () -> op.multiply(func1, func3) );
        assertEquals(true, ex3.getMessage().contains("Разные значения"));

        double[] xValues4 = {21, 23, 24, 25}; // разное колво Иксов
        double[] yValues4 = {21, 23, 24, 25};
        var func4 = new LinkedListTabulatedFunction(xValues4, yValues4);
        var ex4 = assertThrows(TabulatedFunctionOperationService.InconsistentFunctionsException.class,
                () -> op.multiply(func1, func4) );
        assertEquals(true, ex4.getMessage().contains("Разное кол-во"));
    }

    @Test
    void divisionTest() {

        double[] xValues1 = {2, 3, 4};
        double[] yValues1 = {2, 2, 2};

        double[] xValues2 = {2, 3, 4};
        double[] yValues2 = {1, 2, 3};

        var func1 = new LinkedListTabulatedFunction(xValues1, yValues1);
        var func2 = new LinkedListTabulatedFunction(xValues2, yValues2);

        var fact = new LinkedListTabulatedFunctionFactory();
        var op = new TabulatedFunctionOperationService(fact);

        var res = assertDoesNotThrow( () ->op.division(func1, func2) );
        for(int i = 0; i < res.getCount(); i++){
            assertEquals(yValues1[i] / yValues2[i], res.getY(i));
        }

    }
}