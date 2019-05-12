import exceptions.KeyFormatException;
import exceptions.SubstitutionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SboxTest {
    byte[] test1 = {1,0,1,1};
    byte[] test2 = {0,0,1,0};
    byte[] test3 = {1,0,1,0};
    byte[] test4 = {0,1,0,0};
    byte[] adjusted = {1,0,0,0};
    private byte[] plainText = {0,1,1,0,1,1,0,1};

    Sbox sbox;

    @BeforeEach
    void setup() {
        sbox = new Sbox(plainText);
    }

    @Test
    void substituteS0() {
        byte[] result = sbox.substituteS0(test1);

        assertEquals(0, result[0]);
        assertEquals(1, result[1]);

        result = sbox.substituteS0(test2);
        assertEquals(0, result[0]);
        assertEquals(0, result[1]);

        result = sbox.substituteS0(test3);
        assertEquals(1, result[0]);
        assertEquals(0, result[1]);

        result = sbox.substituteS0(test4);
        assertEquals(1, result[0]);
        assertEquals(1, result[1]);
    }

    @Test
    void combineBits() {
        assertEquals(3, sbox.combineBits((byte) 1,(byte)1));
        assertEquals(1, sbox.combineBits((byte) 0,(byte)1));
        assertEquals(2, sbox.combineBits((byte) 1,(byte)0));
        assertEquals(0, sbox.combineBits((byte) 0,(byte)0));
    }

    @Test
    void substituteS1() {
        byte[] result = sbox.substituteS1(test1);

        assertEquals(0, result[0]);
        assertEquals(1, result[1]);

        result = sbox.substituteS1(test2);
        assertEquals(0, result[0]);
        assertEquals(1, result[1]);

        result = sbox.substituteS1(test3);
        assertEquals(0, result[0]);
        assertEquals(0, result[1]);

        result = sbox.substituteS1(test4);
        assertEquals(1, result[0]);
        assertEquals(0, result[1]);
    }

    @Test
    void getSubstitutedOutput() throws SubstitutionException {
        byte[] result = sbox.getSubstitutedOutput();
        assertArrayEquals(adjusted, result);
    }
}