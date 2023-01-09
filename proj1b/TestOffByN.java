import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {

    // Your tests go here.
    @Test
    public void testEqual() {
        CharacterComparator offByN = new OffByN(5);
        assertTrue(offByN.equalChars('a', 'f'));
        assertTrue(offByN.equalChars('f', 'a'));
        assertFalse(offByN.equalChars('f', 'h'));
        assertFalse(offByN.equalChars('a', 'b'));
    }
}
