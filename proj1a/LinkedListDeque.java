public class LinkedListDeque<T> {
    private class Node {
        private Node prev;
        private T item;
        private Node next;

        public Node(T itemcontained, Node prevnode, Node nextnode) {
            item = itemcontained;
            prev = prevnode;
            next = nextnode;
        }

        public Node() {
            this(null, null, null);
        }

        public T gethelper(int index) {
            if (index == 0) {
                return item;
            } else {
                return next.gethelper(index - 1);
            }
        }
    }

    private Node sentinelFirst;
    private Node sentinelTail;
    private int size;

    /** create an empty Deque
     *
     */
    public LinkedListDeque() {
        size = 0;
        sentinelFirst = new Node();
        sentinelTail = new Node();
        sentinelFirst.next = sentinelTail;
        sentinelTail.prev = sentinelFirst;
    }

    public void addFirst(T value) {
        size++;
        Node newnode = new Node(value, sentinelFirst, sentinelFirst.next);
        sentinelFirst.next.prev = newnode;
        sentinelFirst.next = newnode;
    }

    public void addLast(T value) {
        size++;
        Node newnode = new Node(value, sentinelTail.prev, sentinelTail);
        sentinelTail.prev.next = newnode;
        sentinelTail.prev = newnode;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        if (size > 0) {
            Node temp = sentinelFirst.next;
            System.out.print(temp.item);
            for (int i = 0; i < size - 1; i++) {
                temp = temp.next;
                System.out.print(" " + temp.item);
            }
        }
        System.out.print("\n");
    }

    public T removeFirst() {
        if (size > 0) {
            size--;
            Node temp = sentinelFirst.next;
            T value = temp.item;
            temp.next.prev = sentinelFirst;
            sentinelFirst.next = temp.next;
            temp = null;
            return value;
        } else {
            return null;
        }
    }

    public T removeLast() {
        if (size > 0) {
            size--;
            Node temp = sentinelTail.prev;
            T value = temp.item;
            temp.prev.next = sentinelTail;
            sentinelTail.prev = temp.prev;
            temp = null;
            return value;
        } else {
            return null;
        }
    }

    public T get(int index) {
        if (index >= 0 && index <= size - 1) {
            Node temp = sentinelFirst;
            for (int i = 0; i <= index; i++) {
                temp = temp.next;
            }
            return temp.item;
        } else {
            return null;
        }
    }

    public T getRecursive(int index) {
        if (index >= 0 && index <= size - 1) {
            return sentinelFirst.next.gethelper(index);
        } else {
            return null;
        }
    }
}
