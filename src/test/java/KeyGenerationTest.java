import exceptions.KeyFormatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;


@RunWith(MockitoJUnitRunner.class)
class KeyGenerationTest {

    KeyGeneration keyGeneration;
    Key key;
    Key key1;

    byte[] keyBits = {0,1,1,0,1,0,1,0,1,0};
    byte[] keyBits1 = {0,1,1,0,1,0,1,0,1,0};
    byte[] leftHalf = {0,1,1,0,1};
    byte[] rightHalf = {0,1,0,1,0};
    byte[] leftShiftOne = {1,1,0,1,0};
    byte[] leftShiftThree = {0,1,0,1,1};
    byte[] k1 = {1,0,0,1,1,0,0,0};
    byte[] k2 = {1,0,0,1,0,1,0,1};

    @BeforeEach
    void setup() throws KeyFormatException {
        key = new Key(keyBits);
        key1 = new Key(keyBits1);
        keyGeneration = new KeyGeneration(key);
    }

    @Test
    void getHalvesOfKey() {
        keyGeneration.splitArray();
        assertArrayEquals(leftHalf, keyGeneration.getLeftHalfOfKey());
        assertArrayEquals(rightHalf, keyGeneration.getRightHalfOfKey());
    }

    @Test
    void leftShift() {
        keyGeneration.splitArray();
        keyGeneration.leftShift(1);
        assertArrayEquals(leftShiftOne, keyGeneration.getLeftHalfOfKey());
        keyGeneration.leftShift(2);
        assertArrayEquals(leftShiftThree, keyGeneration.getLeftHalfOfKey());
        keyGeneration.leftShift(2);
        assertArrayEquals(leftHalf, keyGeneration.getLeftHalfOfKey());
        keyGeneration.leftShift(6);
        assertArrayEquals(leftShiftOne, keyGeneration.getLeftHalfOfKey());
    }

    @Test
    void createKey1() throws KeyFormatException {
        keyGeneration = new KeyGeneration(key1);
        Key result = keyGeneration.createKey1();
        assertArrayEquals(k1, result.returnBitArray());
        Key result2 = keyGeneration.createKey2();
        assertArrayEquals(k2, result2.returnBitArray());
    }
}