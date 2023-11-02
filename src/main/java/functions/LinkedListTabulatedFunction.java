package functions;


import java.util.Iterator;

public class LinkedListTabulatedFunction extends AbstractTabulatedFunction implements Insertable, Removable {

    public Iterator<Point> iterator() throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }
    protected static class Node {
        public Node prev;
        public Node next;

        public double x;
        public double y;

        Node(double x, double y, Node next, Node prev) {
            this.next = next;
            this.prev = prev;
            this.x = x;
            this.y = y;
        }

        // ---- методы лабы 3 для ноды
        @Override
        public String toString() {
            String finalStr = "(" + Double.toString(x) + "; " + Double.toString(y) + ")";
            return finalStr;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if ((o == null) || !(o instanceof Node))
                return false;
            Node onode = (Node) o;
            return ((this.x == onode.x) && (this.y == onode.y));
        }

        @Override
        public Object clone() {
            Node newNode = new Node(this.x, this.y, null, null);
            return newNode;
        }

        @Override
        public int hashCode() {
            int hashRes = Double.hashCode(this.x) ^ Double.hashCode(this.y);
            return hashRes;
        }
    }

    private Node head = null;
    private int count = 0;

    protected void addNode(double x, double y) {
        if (head == null) {
            head = new Node(x, y, null, null);
            head.next = head;
            head.prev = head;
        } else {
            Node addNode = new Node(x, y, head, head.prev);
            head.prev.next = addNode;
            head.prev = addNode;
        }
        count++;
    }

    /**
     * возвращающий ссылку на узел номер index. Предполагается, что индекс всегда корректный.
     * Необходимо в цикле бежать по элементам списка, пока не будет найден нужный по счёту.
     * Его и нужно будет вернуть из метода.
     * Дополнительно можно реализовать, чтобы в случае, когда индекс больше половины count, бежать с хвоста списка.
     */
    protected Node getNode(int index) {
        Node node = this.head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node;
    }


    // конструктор 1

    /**
     * Предполагается, что значения xValues не повторяются и упорядочены.
     * Также подразумевается, что длина этих массивов совпадает
     */
    LinkedListTabulatedFunction(double[] xValues, double[] yValues) {
        super();
        if (xValues.length < 2 || xValues.length != yValues.length) {
            throw new IllegalArgumentException("count of values must be equal for x and y and not less than 2");
        }
        for (int i = 0; i < xValues.length; i++) {
            addNode(xValues[i], yValues[i]);
        }
    }

    // конструктор 2
    LinkedListTabulatedFunction(MathFunction source, double xFrom, double xTo, int count) {
        super();

        if (count < 2) {
            throw new IllegalArgumentException("count of values must be not less than 2");
        }

        if (xFrom > xTo) {
            double t = xFrom;
            xFrom = xTo;
            xTo = t;
        }

        double step = (xTo - xFrom) / (count - 1);
        double xCur = xFrom;
        for (int i = 0; i < count; i++) {
            addNode(xCur, source.apply(xCur));
            xCur += step;
        }

    }
    //---------------

    @Override
    public int getCount() {
        return this.count;
    }

    // Метод, получающий значение аргумента x по номеру индекса
    @Override
    public double getX(int index) {
        if (index < 0 || index >= count)
            throw new IllegalArgumentException("x index is out of range");
        return getNode(index).x;
    }

    @Override
    public double getY(int index) {
        if (index < 0 || index >= count)
            throw new IllegalArgumentException("y index is out of range");
        return getNode(index).y;
    }

    @Override
    public void setY(int index, double value) {
        getNode(index).y = value;
    }

    @Override
    public int indexOfX(double x) {
        Node node = this.head;
        for (int i = 0; i < this.count; i++) {
            if (node.x == x)
                return i;
            node = node.next;
        }
        return -1;
    }

    @Override
    public int indexOfY(double y) {
        Node node = this.head;
        for (int i = 0; i < this.count; i++) {
            if (node.y == y)
                return i;
            node = node.next;
        }
        return -1;
    }

    @Override
    public double leftBound() {
        return head.x;
    }

    @Override
    public double rightBound() {
        return head.prev.x;
    }

    /**
     * Метод поиска индекса x, который, в отличие от обычного indexOfX(), не должен возвращать -1 (если x не найден),
     * а должен вернуть индекс максимального значения x, которое меньше заданного x.
     * Так, для набора значений x [-3., 4., 6.] – индексация начинается с нуля – метод, применённый к 4.5,
     * должен вернуть 1, так как 4 – максимальный x из всего массива, который меньше 4.5, и имеет индекс 1.
     * Если все x больше заданного, то метод должен вернуть 0;
     * если все x меньше заданного, то метод должен вернуть count.
     */
    @Override
    protected int floorIndexOfX(double x) {
        if (x < leftBound())
            throw new IllegalArgumentException("x value is out of range");
        Node node = this.head;
        for (int i = 0; i < this.count; i++) {
            if (node.x == x)
                return i;
            else if (node.x > x) {
                return (i == 0) ? 0 : i - 1;
            }
            node = node.next;
        }
        return count;
    }

    private Node floorNodeOfX(double x) {
        if (x < leftBound())
            throw new IllegalArgumentException("x value is out of range");
        Node node = this.head;
        for (int i = 0; i < this.count; i++) {
            if (node.x == x)
                return node;
            else if (node.x > x) {
                return (i == 0) ? head : node.prev;
            }
            node = node.next;
        }
        return head;
    }

    @Override
    protected double extrapolateLeft(double x) {
        return interpolate(x, head);
    }

    @Override
    protected double extrapolateRight(double x) {
        return interpolate(x, head.prev.prev);
    }

    @Override
    protected double interpolate(double x, int floorIndex) {
        Node left = getNode(floorIndex);
        return interpolate(x, left);
    }

    private double interpolate(double x, Node floorNode) {
        double leftX = floorNode.x;
        double leftY = floorNode.y;
        floorNode = floorNode.next;
        double rightX = floorNode.x;
        double rightY = floorNode.y;
        return this.interpolate(x, leftX, rightX, leftY, rightY);
    }

    @Override
    protected double interpolate(double x, double leftX, double rightX, double leftY, double rightY) {
        double y = leftY + ((rightY - leftY) / (rightX - leftX)) * (x - leftX);
        return y;
    }

    /**
     * Метод apply() принимает на вход x.
     * Если этот x меньше левой границы, то нужно использовать левую интерполяцию.
     * Если он больше правой границы, то нужно использовать правую интерполяцию.
     * Если он внутри интервала, можно попытаться найти, а есть ли он в таблице, использовав метод indexOf() –
     * если вернулось не -1, то вернуть соответствующее y через метод getY().
     * В противном случае вызвать метод интерполяции с указанием индекса интервала,
     * предварительно отыскав его с помощью метода floorIndexOfX(double x)
     */
    @Override
    public double apply(double x) {
        if (x < leftBound()) {
            return extrapolateLeft(x);
        }
        if (x > rightBound()) {
            return extrapolateRight(x);
        }
        int idxx = indexOfX(x);
        if (idxx != -1)
            return getY(idxx);

        Node node = floorNodeOfX(x);
        return interpolate(x, node);

        //idxx = floorIndexOfX(x);
        //return interpolate(x, idxx);
    }

    /**
     * Метод, вставляющий значение в табулированную функцию.
     * При этом предполагается, что если x уже содержится в таблице, то его значение y заменяется на новое.
     * Если входное x находится между двумя другими значениями x, то в таблицу добавляется значение между ними.
     * Если входное x левее всех имеющихся x, то значение добавляется в начало таблицы.
     * Если правее – то в конец.
     */
    public void insert(double x, double y) {
        //Если список пустой, то просто делегировать выполнение методу addNode() и завершить выполнение метода insert()
        if (head == null) {
            addNode(x, y);
            return;
        }
        Node node = head;
        for (int i = 0; i < count; i++) {
            if (node.x == x) {
                node.y = y;
                return;
            }
            //добавление в самое начало с обновлением головы
            else if ((node.x > x) & (i == 0)) {
                Node addNode = new Node(x, y, head, head.prev);
                head.prev.next = addNode;
                head.prev = addNode;
                head = addNode;
                count++;
                return;
            }
            //добавление в середину
            else if ((node.x > x) & (i != 0)) {
                Node addNode = new Node(x, y, node, node.prev);
                node.prev.next = addNode;
                node.prev = addNode;
                count++;
                return;
            }
            node = node.next;
        }
        //добавление в конец
        Node addNode = new Node(x, y, node, node.prev);
        node.prev.next = addNode;
        node.prev = addNode;
        count++;
    }

    public void remove(int index) {
        //Если список пустой, то return
        if (head == null) return;

        //Если список из одного эл-та то зануляем голову
        if (count == 1) {
            count = 0;
            head = null;
            return;
        }

        Node node = head;
        //идем до эл-та с index
        for (int i = 0; i < index; i++)
            node = node.next;

        //remove его
        node.prev.next = node.next;
        node.next.prev = node.prev;
        // если удаляем 0й элемент - обновляем голову
        if (index == 0) head = node.next;

        count--;
    }

    // ---- методы лабы 3 для табулированной функции
    @Override
    public String toString() {
        Node curnode = head;
        String result = "";
        do {
            if (result != "")
                result += ", ";

            result += "(" + Double.toString(curnode.x) + "; " + Double.toString(curnode.y) + ")";
            curnode = curnode.next;
        } while (curnode != head);

        return result;
    }

    @Override
    public boolean equals(Object o) {
        //если o не наследует Tabuleted, то пока-пока
        if ((o == null) || !(o instanceof TabulatedFunction))
            return false;
        //если o LinkedList, то работаем с ним как со списком
        if (o instanceof LinkedListTabulatedFunction) {
            LinkedListTabulatedFunction ofunk = (LinkedListTabulatedFunction) o;
            if (this.hashCode() == ofunk.hashCode()) {
                Node curnode = this.head;
                Node onode = ofunk.getNode(0);
                do {
                    if (curnode.hashCode() == onode.hashCode()) {
                        if ((curnode.x == onode.x) && (curnode.y == onode.y)) {
                            curnode = curnode.next;
                            onode = onode.next;
                        }
                    } else return false;
                } while (curnode != head);
                return true;
            } else return false;
        } else {
            TabulatedFunction ofunk = (TabulatedFunction) o;
            int cnt = ofunk.getCount();

            if (this.getCount() != cnt)
                return false;

            Node curnode = this.head;
            for (int i = 0; i < cnt; i++) {
                if ((curnode.x == ofunk.getX(i)) && (curnode.y == ofunk.getY(i)))
                    curnode = curnode.next;
                else return false;
            }
            return true;
        }
    }

    @Override
    public Object clone() {

        double[] xValues = new double[count];
        double[] yValues = new double[count];
        Node curnode = this.head;
        int i = 0;
        do {
            xValues[i] = curnode.x;
            yValues[i] = curnode.y;
            curnode = curnode.next;
            i++;
        } while (curnode != head);

        return new LinkedListTabulatedFunction(xValues, yValues);
    }

    @Override
    public int hashCode() {
        int hashRes = 0;
        Node curnode = this.head;
        do {
            hashRes += curnode.hashCode();
            curnode = curnode.next;
        } while (curnode != head);

        return hashRes;
    }
}