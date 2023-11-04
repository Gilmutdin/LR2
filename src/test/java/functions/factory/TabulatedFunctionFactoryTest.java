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
}
