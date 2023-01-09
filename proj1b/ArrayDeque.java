public class ArrayDeque<T> implements Deque<T> {
    private T[] items;
    private int size;
    private int front;

    public ArrayDeque() {
        size = 0;
        items = (T []) new Object[8];
        front = 0;
    }

    private void resize(int newsize) {
        T[] newitems = (T []) new Object[newsize];
        if ((front + size - 1) % items.length >= front) {
            System.arraycopy(items, front, newitems, 0, size);
        } else {
            System.arraycopy(items, front, newitems, 0, items.length - front);
            System.arraycopy(items, 0, newitems,
                    items.length - front, size - items.length + front);
        }
        front = 0;
        items = newitems;
    }

    @Override
    public void addFirst(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        front = (front + items.length - 1) % items.length;
        items[front] = item;
        size++;
    }

    @Override
    public void addLast(T item) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[(front + size) % items.length] = item;
        size++;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        if (size > 0) {
            System.out.print(items[front]);
            for (int i = 1; i < size; i++) {
                System.out.print(" " + items[(front + i) % items.length]);
            }
        }
        System.out.print("\n");
    }

    @Override
    public T removeFirst() {
        if (size > 0) {
            if (items.length > 8 && size <= items.length / 2) {
                resize(items.length / 2);
            }
            T temp = items[front];
            items[front] = null;
            front = (front + 1) % items.length;
            size--;
            return temp;
        } else {
            return null;
        }
    }

    @Override
    public T removeLast() {
        if (size > 0) {
            if (items.length > 8 && size <= items.length / 2) {
                resize(items.length / 2);
            }
            T temp = items[(front + size - 1) % items.length];
            items[(front + size - 1) % items.length] = null;
            size--;
            return temp;
        } else {
            return null;
        }
    }

    @Override
    public T get(int index) {
        if (size > 0) {
            return items[(front + index) % items.length];
        } else {
            return null;
        }
    }
}
