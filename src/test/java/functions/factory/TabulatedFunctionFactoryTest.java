package functions.factory;
import functions.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TabulatedFunctionFactoryTest {

    @Test
    void CreateFunctionFactoryTest() {
        TabulatedFunctionFactory factoryLinked = new LinkedListTabulatedFunctionFactory();
        TabulatedFunction funkLinked = factoryLinked.create(new double[]{1, 2}, new double[]{1, 2});
        assertTrue(funkLinked instanceof LinkedListTabulatedFunction);

        TabulatedFunctionFactory factoryArr = new ArrayTabulatedFunctionFactory();
        TabulatedFunction funkArr = factoryArr.create(new double[]{1, 2}, new double[]{1, 2});
        assertTrue(funkArr instanceof ArrayTabulatedFunction);
    }

    @Test
    void StrictFactoryTest(){
        TabulatedFunctionFactory factoryLinked = new LinkedListTabulatedFunctionFactory();
        TabulatedFunction funkLinked = factoryLinked.createStrict(new double[]{1, 2}, new double[]{1, 2});

        TabulatedFunctionFactory factoryArr = new ArrayTabulatedFunctionFactory();
        TabulatedFunction funkArr = factoryArr.createStrict(new double[]{1, 2}, new double[]{1, 2});


        var res1 = assertThrows(UnsupportedOperationException.class, () -> {
            var y = funkLinked.apply(5);
        });
        var res2 = assertThrows(UnsupportedOperationException.class, () -> {
            var y =  funkArr.apply(3);
        });
    }

    @Test
    void StrictUnmodifFactoryTest(){
        TabulatedFunctionFactory factoryLinked = new LinkedListTabulatedFunctionFactory();
        TabulatedFunction funkLinked = factoryLinked.createStrictUnmodifiable(new double[]{1, 2}, new double[]{1, 2});

        TabulatedFunctionFactory factoryArr = new ArrayTabulatedFunctionFactory();
        TabulatedFunction funkArr = factoryArr.createStrictUnmodifiable(new double[]{1, 2}, new double[]{1, 2});


        var res1 = assertThrows(UnsupportedOperationException.class, () -> {
            var y = funkLinked.apply(5);
        });

        ///!!!! раскоментить
        /*
        var res2 = assertThrows(UnsupportedOperationException.class, () -> {
            funkArr.setY(3, 6);
        });
         */
    }
}
