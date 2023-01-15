package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer<Integer> qeque = new ArrayRingBuffer<Integer>(8);
        qeque.enqueue(3);
        qeque.enqueue(2);
        assertTrue(3 == qeque.peek());
        qeque.enqueue(1);
        assertTrue(3 == qeque.dequeue());
        assertTrue(2 == qeque.dequeue());
        assertTrue(1 == qeque.dequeue());
        qeque.enqueue(4);
        assertTrue(4 == qeque.dequeue());
        for (int i = 1; i <= 8; i++) {
            qeque.enqueue(i);
        }
        for (int item : qeque) {
            System.out.print(item);
        }
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
