package synthesizer;

import java.util.Iterator;

public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        first = 0;
        last = 0;
        this.fillCount = 0;
        this.capacity = capacity;
        rb = (T []) new Object[capacity];
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        if (!this.isFull()) {
            this.fillCount++;
            rb[last] = x;
            last = (last + 1) % this.capacity();
        } else {
            throw new RuntimeException("Ring Buffer Overflow");
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        if (!this.isEmpty()) {
            T temp = rb[first];
            rb[first] = null;
            this.fillCount--;
            first = (first + 1) % this.capacity();
            return temp;
        } else {
            throw new RuntimeException("Ring Buffer Underflow");
        }
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        if (fillCount() > 0) {
            return rb[first];
        } else {
            throw new RuntimeException("Ring Buffer Underflow");
        }
    }


    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }
    private class ArrayRingBufferIterator implements Iterator<T> {
        private int position;

        ArrayRingBufferIterator() {
            position = 0;
        }

        public boolean hasNext() {
            return position < capacity();
        }

        public T next() {
            T returnItem = rb[(first + position) % capacity()];
            position++;
            return returnItem;
        }
    }
}
