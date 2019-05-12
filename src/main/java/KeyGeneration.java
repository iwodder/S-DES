import exceptions.KeyFormatException;

import static exceptions.ExceptionMessages.keyOneCreation;

public class KeyGeneration {
    private Key keyToBePermuted;
    private byte[] leftHalf;
    private byte[] rightHalf;
    private boolean arraySplit;
    private boolean keyOneCreated;
    private boolean keyTwoCreated;
    private static final int LS1 = 1;
    private static final int LS2 = 2;
    private static final String P8 = "p8";
    private static final String P10 = "p10";
    private Permutation p8;
    private Permutation p10;

    public KeyGeneration(Key initialKey) {
        if(initialKey != null) {
            this.keyToBePermuted = initialKey;
            arraySplit = false;
            p8 = Permutation.setPermutationMode(P8);
            p10 = Permutation.setPermutationMode(P10);
            keyOneCreated = false;
            keyTwoCreated = false;
        }
    }

    public void splitArray() throws KeyFormatException {
        keyToBePermuted = p10.permuteKey(keyToBePermuted);
        leftHalf = keyToBePermuted.returnPartialArray(0, keyToBePermuted.sizeOfKey()/2);
        rightHalf = keyToBePermuted.returnPartialArray(keyToBePermuted.sizeOfKey()/2, keyToBePermuted.sizeOfKey());
        arraySplit = true;
    }

    public byte[] getLeftHalfOfKey() {
        return this.leftHalf;
    }

    public byte[] getRightHalfOfKey() {
        return this.rightHalf;
    }

    public void leftShift(int numOfShifts) {
        shiftBits(leftHalf, numOfShifts);
        shiftBits(rightHalf, numOfShifts);
    }

    private void shiftBits(byte[] arrayOfBits, int numOfShifts) {
        int placesToMove = numOfShifts % arrayOfBits.length;

        for(int x = 0; x < placesToMove; x++) {
            int tempLeft = arrayOfBits[0];
            for(int y = 0; y < arrayOfBits.length -1; y++) {
                arrayOfBits[y] = arrayOfBits[y+1];
            }
            arrayOfBits[arrayOfBits.length - 1] = (byte) tempLeft;
        }
    }

    public Key createKey1() throws KeyFormatException {
        if(arraySplit) {
            leftShift(LS1);
            keyOneCreated = true;
            return p8.permuteKey(combineHalves());
        } else {
            splitArray();
            leftShift(LS1);
            keyOneCreated = true;
            return p8.permuteKey(combineHalves());
        }
    }

    public Key createKey2() throws KeyFormatException {
        if(keyOneCreated) {
            leftShift(LS2);
            keyTwoCreated = true;
            return p8.permuteKey(combineHalves());
        } else {
            throw new KeyFormatException(keyOneCreation);
        }
    }

    public Key combineHalves() throws KeyFormatException {
        byte[] newKey = new byte[leftHalf.length + rightHalf.length];
        int copyPos = 0;
        for(int x = 0; x < leftHalf.length; x++, copyPos++) {
            newKey[copyPos] = leftHalf[x];
        }
        for(int x = 0; x < rightHalf.length; x++, copyPos++) {
            newKey[copyPos] = rightHalf[x];
        }
        return new Key(newKey);
    }
}
