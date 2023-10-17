package functions;


public class LinkedListTabulatedFunction extends AbstractTabulatedFunction implements Insertable, Removable {

    protected class Node {
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
            if (!(o instanceof Node) || (o == null))
                return false;
            Node onode = (Node) o;
            return ((this.x == onode.x) && (this.y == onode.y));
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
        for (int i = 0; i < xValues.length; i++) {
            addNode(xValues[i], yValues[i]);
        }
    }

    // конструктор 2
    LinkedListTabulatedFunction(MathFunction source, double xFrom, double xTo, int count) {
        super();
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
        return getNode(index).x;
    }

    @Override
    public double getY(int index) {
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
        if (!(o instanceof LinkedListTabulatedFunction) || (o == null))
            return false;
        LinkedListTabulatedFunction ofunk = (LinkedListTabulatedFunction) o;
        Node curnode = this.head;
        Node onode = ofunk.getNode(0);
        do {
            if ((curnode.x == onode.x) && (curnode.y == onode.y)){
                curnode = curnode.next;
                onode = onode.next;
            }
            else return false;
        } while (curnode != head);
        return true;
    }
}