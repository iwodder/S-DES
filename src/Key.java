import exceptions.ExceptionMessages;
import exceptions.KeyFormatException;
import org.jetbrains.annotations.NotNull;

import java.util.BitSet;

public class Key {

    private int KEY_SIZE;
    private BitSet key;
    private StringBuilder sb;

    public Key(@NotNull byte[] initialKey) throws KeyFormatException {

        this.KEY_SIZE = initialKey.length;

        key = new BitSet(KEY_SIZE);

        for(int index = 0; index < KEY_SIZE; index++) {
            if(initialKey[index] != 1 && initialKey[index] != 0) {
                throw new KeyFormatException(ExceptionMessages.keyContentMsg);
            }
            key.set(index, initialKey[index] == 1 ? true : false);
        }
    }

    public String printBits() {
        sb = new StringBuilder();
        for(int index = 0; index < KEY_SIZE; index++) {
            sb.append(key.get(index) ? "1" : "0");
        }
        return sb.toString();
    }

    public byte[] returnBitArray() {
        byte[] keyArr = new byte[KEY_SIZE];
        for(int index = 0; index < KEY_SIZE; index++) {
            keyArr[index] =  (byte) (key.get(index) ? 1 : 0);
        }

        return keyArr;
    }

    /*
        Performs a not operation on a particular bit located at the index.
     */
    public void adjustBit(int index) {
        key.flip((index - key.length()));
    }
}
