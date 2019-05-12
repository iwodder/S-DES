import exceptions.ExceptionMessages;
import exceptions.KeyFormatException;
import org.jetbrains.annotations.NotNull;

import java.util.BitSet;

public class Key {

    private int key_size;
    private BitSet key;
    private StringBuilder sb;

    public Key(@NotNull byte[] initialKey) throws KeyFormatException {

        this.key_size = initialKey.length;

        key = new BitSet(key_size);

        for(int index = 0; index < key_size; index++) {
            if(initialKey[index] != 1 && initialKey[index] != 0) {
                throw new KeyFormatException(ExceptionMessages.keyContentMsg);
            }
            key.set(index, initialKey[index] == 1 ? true : false);
        }
    }

    public String printBits() {
        sb = new StringBuilder();
        for(int index = 0; index < key_size; index++) {
            sb.append(key.get(index) ? "1" : "0");
        }
        return sb.toString();
    }

    public byte[] returnBitArray() {
        return returnPartialArray(0, key_size);
    }

     public byte[] returnPartialArray(int start, int end) {
        byte[] keyArr = new byte[end - start];
        for(int index = start; index < end; index++) {
            keyArr[index - start] =  (byte) (key.get(index) ? 1 : 0);
        }

        return keyArr;
     }

    /*
        Performs a not operation on a particular bit located at the index.
     */
    public void adjustBit(int index) throws IllegalAccessException {
        if(index > key.length() || index < 0) {
            throw new IllegalAccessException();
        }
        key.flip((index));
    }

    public int sizeOfKey() {
        return key_size;
    }
}
