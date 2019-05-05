import exceptions.KeyFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KeyTest {
    Key key;
    byte[] keyBits = {0,1,1,0,1,0,1,0,1,0,1,0,1,1};
    @BeforeEach
    void setup() throws KeyFormatException  {
        key = new Key(keyBits);
    }

    @Test
    void printBits() {
        String result = key.printBits();
        assertEquals("01101010101011", result);
    }

    @Test
    void returnBitArray() {
        assertArrayEquals(keyBits, key.returnBitArray());
    }

    @Test
    void adjustBit() {
        key.adjustBit();
                                //0 1 2 3 4 5 6 7 8 9 10 11 12 13
        byte[] keyBitsModified = {0,1,1,0,1,0,1,0,1,0, 1, 0, 1, 1};
        assertArrayEquals(keyBitsModified, key.returnBitArray());
    }
}