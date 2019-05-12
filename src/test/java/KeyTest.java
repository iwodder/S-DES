import exceptions.KeyFormatException;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KeyTest {
    Key key;
    byte[] keyBits = {0,1,1,0,1,0,1,0,1,0};
    byte[] leftHalf = {0,1,1,0,1};
    byte[] rightHalf = {0,1,0,1,0};
    @BeforeEach
    void setup() throws KeyFormatException  {
        key = new Key(keyBits);
    }

    @Test
    void printBits() {
        String result = key.printBits();
        assertEquals("0110101010", result);
    }

    @Test
    void returnBitArray() {
        assertArrayEquals(keyBits, key.returnBitArray());
    }

    @Test
    void adjustBit() {
        try {
            key.adjustBit(0);
            key.adjustBit(2);
            key.adjustBit(6);
            key.adjustBit(13);

                                    //0 1 2 3 4 5 6 7 8 9 10 11 12 13
            byte[] keyBitsModified = {1,1,0,0,1,0,0,0,1,0, 1, 0, 1, 0};
            assertArrayEquals(keyBitsModified, key.returnBitArray());
            assertThrows(IllegalAccessException.class, () -> key.adjustBit(keyBits.length + 1));
            assertThrows(IllegalAccessException.class, () -> key.adjustBit(-1));
        } catch (IllegalAccessException e) {
            System.err.println(e);
        }

    }

    @Test
    void returnPartialArray() {
        byte[] ls1 = key.returnPartialArray(0, 5);
        assertArrayEquals(leftHalf,ls1);

        byte[] rs1 = key.returnPartialArray(5,10);
        assertArrayEquals(rightHalf, rs1);
    }
}