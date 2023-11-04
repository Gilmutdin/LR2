package functions.factory;
import functions.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TabulatedFunctionFactoryTest {
    TabulatedFunctionFactory factoryLinked = new LinkedListTabulatedFunctionFactory();
    TabulatedFunctionFactory factoryArr = new ArrayTabulatedFunctionFactory();

    TabulatedFunction funkLinked = factoryLinked.create(new double[]{1, 2}, new double[]{1, 2});
    TabulatedFunction funkArr = factoryArr.create(new double[]{1, 2}, new double[]{1, 2});

    @Test
    void CreateFunctionFactoryTest(){
        assertTrue(funkLinked instanceof LinkedListTabulatedFunction);
        assertTrue(funkArr instanceof ArrayTabulatedFunction);
    }
}
