package functions;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdentityFunctionTest {

    private IdentityFunction test;

    @BeforeEach
    void setUp()  {
        test = new IdentityFunction();
    }

    @Test
    void testApply1() {
        assertEquals(test.apply(1), 1);
    }

    @Test
    void testApply2() {
        assertEquals(test.apply(0.5), 0.5);
    }

    @Test
    void testApply3() {
        assertEquals(test.apply(-2), -2);
    }

    @Test
    void testApply4() {
        assertEquals(test.apply(-0.01), -0.01);
    }

    @AfterEach
    void tearDown(){
        System.out.println("@AfterEach executed");
    }

    @AfterAll
    static void tear(){
        System.out.println("@AfterAll executed");
    }
}