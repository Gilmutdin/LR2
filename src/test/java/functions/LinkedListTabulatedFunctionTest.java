package functions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LinkedListTabulatedFunctionTest {

    @Test
    void constructor1() {
        var func1 = new LinkedListTabulatedFunction(new double[]{1}, new double[]{2});
        assertEquals(func1.getCount(), 1);

        var func2 = new LinkedListTabulatedFunction(new double[]{0.1, 4.5, 9.99}, new double[]{2.1, 6.5, 11.99});
        assertEquals(func2.getCount(), 3);
    }

    @Test
    void constructor2() {
        var funk = new LinkedListTabulatedFunction(new IdentityFunction(), 1, 5, 5);
        assertEquals(funk.getCount(), 5); // задали 5 точек - ждем 5 точек

        assertNotEquals(funk.getX(2), 3.5); // проверяем как он внутри нагенерил таблич точек, для 2 д б 3
        assertEquals(funk.getX(2), 3); // проверяем как он внутри нагенерил таблич точек, для 2 д б 3

        assertEquals(funk.getY(3), 4);

        // проверяем что границы поменяет есл зад меньше переда
        funk = new LinkedListTabulatedFunction(new IdentityFunction(), 10, 1, 4);
        assertEquals(funk.getCount(),4);
        assertEquals(funk.getX(0), 1);
        assertEquals(funk.getY(3), 10);
        assertEquals(funk.getX(1), 4);

        // для гориз линии все У одинак
        funk = new LinkedListTabulatedFunction(new IdentityFunction(), 1, 1, 4);
        assertEquals(funk.getCount(),4);
        assertEquals(funk.getX(0), 1);
        assertEquals(funk.getX(3), 1);
        assertNotEquals(funk.getX(2), 4);
    }

    @Test
    void addNode() {
        var func1 = new LinkedListTabulatedFunction(new double[]{1}, new double[]{2});

        // проверяем что колво нод прибавилось
        func1.addNode(11,99);
        assertEquals(func1.getCount(), 2);

        // смотрим хвост
        var last = func1.getNode(1);
        assertEquals(last.x, 11);
        assertEquals(last.y, 99);
    }

    @Test
    void getNode() {
        var func1 = new LinkedListTabulatedFunction(new double[]{0.1}, new double[]{2.1});
        var node = func1.getNode(0);
        assertEquals(node.x, 0.1);
        assertEquals(node.y, 2.1);

        //---

        var func2 = new LinkedListTabulatedFunction(new double[]{0.1, 4.5}, new double[]{2.1, 6.5});
        node = func2.getNode(0);
        assertEquals(node.x, 0.1);
        assertEquals(node.y, 2.1);

        node = func2.getNode(1);
        assertEquals(node.x, 4.5);
        assertEquals(node.y, 6.5);

        //---

        var func3 = new LinkedListTabulatedFunction(new double[]{0.1, 4.5, 9.99}, new double[]{2.1, 6.5, 11.99});
        node = func3.getNode(0);
        assertEquals(node.x, 0.1);
        assertEquals(node.y, 2.1);

        node = func3.getNode(1);
        assertEquals(node.x, 4.5);
        assertEquals(node.y, 6.5);

        node = func3.getNode(2);
        assertEquals(node.x, 9.99);
        assertEquals(node.y, 11.99);
    }

    @Test
    void getCount() {
        var funk = new LinkedListTabulatedFunction(new double[]{}, new double[]{});
        assertEquals(funk.getCount(), 0);
        funk.addNode(1, 2);
        assertEquals(funk.getCount(), 1);
        funk.addNode(4, 3);
        assertEquals(funk.getCount(), 2);
        funk.addNode(11, 22);
        assertEquals(funk.getCount(), 3);

        //--

        funk = new LinkedListTabulatedFunction(new IdentityFunction(), 1, 10, 5);
        assertEquals(funk.getCount(), 5);
    }

    @Test
    void getX() {
        var funk = new LinkedListTabulatedFunction(new double[]{1, 5, 9, 15}, new double[]{1, 5, 9, 15});
        assertEquals(funk.getX(2), 9);
        funk = new LinkedListTabulatedFunction(new IdentityFunction(), 10, 1, 4);
        assertEquals(funk.getX(1), 4);
    }

    @Test
    void getY() {
        var funk = new LinkedListTabulatedFunction(new double[]{1, 5, 9, 15}, new double[]{1, 5, 9, 15});
        assertEquals(funk.getY(2), 9);
        funk = new LinkedListTabulatedFunction(new IdentityFunction(), 10, 1, 4);
        assertEquals(funk.getY(1), 4);
    }

    @Test
    void setY() {
        var funk = new LinkedListTabulatedFunction(new double[]{1}, new double[]{2});
        funk.setY(0, 1);
        assertEquals(funk.getY(0), 1);

        funk = new LinkedListTabulatedFunction(new double[]{1, 2, 3, 4, 5}, new double[]{1, 2, 30, 4, 5});
        funk.setY(2, 3);
        assertEquals(funk.getY(2), 3);
    }

    @Test
    void indexOfX() {
        var funk = new LinkedListTabulatedFunction(new double[]{1, 2, 3, 40, 50}, new double[]{10, 20, 30, 40, 50});
        assertEquals(funk.indexOfX(40), 3);
        assertNotEquals(funk.indexOfX(4), 3);
        assertEquals(funk.indexOfX(17), -1);
        assertEquals(funk.indexOfX(-17), -1);
        assertEquals(funk.indexOfX(177), -1);
    }

    @Test
    void indexOfY() {
        var funk = new LinkedListTabulatedFunction(new double[]{1, 2, 3, 40, 50}, new double[]{10, 20, 30, 40, 50});
        assertEquals(funk.indexOfY(40), 3);
        assertNotEquals(funk.indexOfY(4), 3);
        assertEquals(funk.indexOfY(17), -1);
        assertEquals(funk.indexOfY(-17), -1);
        assertEquals(funk.indexOfY(177), -1);
    }

    @Test
    void leftBound() {
        var funk = new LinkedListTabulatedFunction(new double[]{1, 2, 3, 4, 5}, new double[]{10, 20, 30, 40, 50});
        assertEquals(funk.leftBound(), 1);
        funk = new LinkedListTabulatedFunction(new double[]{1}, new double[]{1});
        assertEquals(funk.leftBound(), 1);
    }

    @Test
    void rightBound() {
        var funk = new LinkedListTabulatedFunction(new double[]{1, 2, 3, 4, 5}, new double[]{10, 20, 30, 40, 50});
        assertEquals(funk.rightBound(), 5);
        funk = new LinkedListTabulatedFunction(new double[]{1}, new double[]{1});
        assertEquals(funk.rightBound(), 1);
    }

    @Test
    void floorIndexOfX() {
        var funk = new LinkedListTabulatedFunction(new double[]{1, 2, 3, 4, 5}, new double[]{10, 20, 30, 40, 50});
        assertEquals(funk.floorIndexOfX(4.5), 3);

        // для табличных точек долж возвр их идх
        assertEquals(funk.floorIndexOfX(1), 0);
        assertEquals(funk.floorIndexOfX(3), 2);
        assertEquals(funk.floorIndexOfX(5), 4);

        assertEquals(funk.floorIndexOfX(0.5), 0);
        assertEquals(funk.floorIndexOfX(5.5), 5);
    }

    @Test
    void extrapolateLeft() {
        var funk = new LinkedListTabulatedFunction(new IdentityFunction(), 1, 15, 5);
        double res = funk.extrapolateLeft(-20);
        assertEquals(res, -20);

        //---
        funk = new LinkedListTabulatedFunction(new double[]{1, 5, 9, 15}, new double[]{1, 1, 1, 1});
        res = funk.extrapolateLeft(-20);
        assertEquals(res, 1);
    }

    @Test
    void extrapolateRight() {
        var funk = new LinkedListTabulatedFunction(new IdentityFunction(), 1, 15, 5);
        double res = funk.extrapolateRight(20);
        assertEquals(res, 20);

        //---
        var funk2 = new LinkedListTabulatedFunction(new double[]{1, 5, 9, 15}, new double[]{1, 1, 1, 1});
        double res2 = funk2.extrapolateRight(20);
        assertEquals(res2, 1);
    }

    @Test
    void interpolate() {
        var funk = new LinkedListTabulatedFunction(new double[]{1, 5, 9, 15}, new double[]{1, 5, 9, 15});
        double res = funk.interpolate(3, 0);
        assertEquals(res, 3);
        res = funk.interpolate(7,1);
        assertEquals(res, 7);

        //---
        var funk2 = new LinkedListTabulatedFunction(new double[]{1, 5, 9, 15}, new double[]{1, 1, 1, 1});
        double res2 = funk2.interpolate(3, 0);
        assertEquals(res2, 1);
        res2 = funk2.interpolate(7,1);
        assertEquals(res2, 1);
    }


    @Test
    void apply() {
        var funk = new LinkedListTabulatedFunction(new double[]{1, 5, 9, 15}, new double[]{1, 5, 9, 15});
        // слева
        double res = funk.apply(-20);
        assertEquals(res, -20);
        // в промежут
        res = funk.apply(3);
        assertEquals(res, 3);
        // в табл точ
        res = funk.apply(5);
        assertEquals(res, 5);
        // справа
        res = funk.apply(30);
        assertEquals(res, 30);

        funk = new LinkedListTabulatedFunction(new double[]{1,11}, new double[]{1, 1});
        // слева
        res = funk.apply(-20);
        assertEquals(res, 1);
        // в табл точ
        res = funk.apply(1);
        assertEquals(res, 1);
        // справа
        res = funk.apply(30);
        assertEquals(res, 1);
    }

    @Test
    void insert(){
        var funk = new LinkedListTabulatedFunction(new double[]{1, 3, 9, 15}, new double[]{1, 3, 9, 15});
        // слева
        funk.insert(0, 0);
        assertEquals(funk.leftBound(), 0);
        assertEquals(funk.getCount(), 5);
        // в промежут
        funk.insert(12,13);
        assertEquals(funk.getX(4), 12);
        assertEquals(funk.getCount(), 6);
        // в табл точ
        funk.insert(12, 12);
        assertEquals(funk.getY(4), 12);
        assertEquals(funk.getCount(), 6);
        // справа
        funk.insert(18, 18);
        assertEquals(funk.rightBound(), 18);
        assertEquals(funk.getCount(), 7);
    }

    @Test
    void remove(){
        var funk = new LinkedListTabulatedFunction(new double[]{1, 3, 9, 15}, new double[]{1, 3, 9, 15});
        // слева
        funk.remove(0);
        assertEquals(funk.leftBound(), 3);
        assertEquals(funk.getCount(), 3);

        // в табл точ
        funk.remove(1);
        assertEquals(funk.getX(1), 15);
        assertEquals(funk.getCount(), 2);

        // справа
        funk.remove(1);
        assertEquals(funk.rightBound(), 3);
        assertEquals(funk.getCount(), 1);

        funk.remove(0);
        assertEquals(funk.getCount(), 0);
    }
}