import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class RoundFunctionTest {

    private RoundFunction rf;
    private byte[] plainText = {0,1,1,0,1,1,0,1};
    private byte[] leftHalf = {0,1,1,0};
    private byte[] rightHalf = {1,1,0,1};
    private byte[] xor = {1,0,1,1};
    private byte[] rightHalfCopy = {1,1,0,1};

    @Mock
    Key key;

    @BeforeEach
    void setUp() {
        rf = new RoundFunction(plainText, key);
    }

    @Test
    void splitPlainText() {
        rf.splitPlainText();
        assertArrayEquals(leftHalf, rf.getLeftHalf());
        assertArrayEquals(rightHalf, rf.getRightHalf());
        assertArrayEquals(rightHalfCopy, rf.getRightHalfCopy());
    }

    @Test
    void createHalves() {
        assertArrayEquals(leftHalf, rf.createHalves(4, 0));
        assertArrayEquals(rightHalf, rf.createHalves(plainText.length, 4));
    }

    @Test
    void combineHalves() {
        assertArrayEquals(plainText, rf.combineHalves(rightHalf, leftHalf));
    }

    @Test
    void copyHalf() {
        assertArrayEquals(rightHalf, rf.copyHalf(rightHalf));
    }

    @Test
    void xor() {
        rf.xor(rightHalf, leftHalf);
        assertArrayEquals(xor, rightHalf);
    }
}