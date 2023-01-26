import static org.junit.Assert.*;
import org.junit.Test;

public class RadixSortTester {
    private static String[] stringList = {"bca", "bc", "ab", "a", "cba", "cab"};

    @Test
    public void testRadixSort() {
        String[] sorted = RadixSort.sort(stringList);
        for (String item : sorted) {
            System.out.print(item + " ");
        }
        return;
    }
}