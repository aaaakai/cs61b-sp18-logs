public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int front;
    private int RFACTOR = 3;

    public ArrayDeque() {
        size = 0;
        items = (T []) new Object[size];
        front = 0;
    }

    public void resize(int newsize) {
        T[] newitems = (T []) new Object[newsize];
        if (front + size <= items.length) {
            System.arraycopy(items, front, newitems, 0, size);
        } else {
            System.arraycopy(items, front, newitems, 0, items.length - front);
            System.arraycopy(items, 0, newitems, items.length - front, front);
        }
        front = 0;
        items = newitems;
    }

    public void addFirst(T item) {
        if (size == items.length) {
            resize(size + RFACTOR);
        }
        front = (front + items.length - 1) % items.length;
        items[front] = item;
        size++;
    }

    public void addLast(T item) {
        if (size == items.length) {
            resize(size + RFACTOR);
        }
        items[(front + size) % items.length] = item;
        size++;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        if (size > 0) {
            System.out.print(items[front]);
            for (int i = 1; i < size; i++) {
                System.out.print(" " + items[(front + i) % items.length]);
            }
        }
        System.out.print("\n");
    }

    public T removeFirst() {
        if (size > 0) {
            T temp = items[front];
            items[front] = null;
            front = (front + 1) % items.length;
            size--;
            return temp;
        } else {
            return null;
        }
    }

    public T removeLast() {
        if (size > 0) {
            T temp = items[(front + size - 1) % items.length];
            items[front + size - 1] = null;
            size--;
            return temp;
        } else {
            return null;
        }
    }

    public T get(int index) {
        if (size > 0) {
            return items[(front + index) % items.length];
        } else {
            return null;
        }
    }
}
