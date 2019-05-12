import exceptions.KeyFormatException;
import exceptions.SubstitutionException;

import javax.security.auth.Subject;

public class RoundFunction {
    byte[] leftHalf;
    byte[] rightHalf;
    byte[] plainText;
    byte[] rightHalfCopy;
    private Permutation permuter;
    private Key key;
    private Sbox sbox;

    public RoundFunction(byte[] plainText, Key roundKey) {
        this.plainText = plainText;
        this.key = roundKey;
    }

    public void splitPlainText() {
        leftHalf = createHalves(4,0);
        rightHalf = createHalves(plainText.length, 4);
        rightHalfCopy = copyHalf(rightHalf);
    }

    public byte[] createHalves(int end, int start) {
        byte[] half = new byte[end - start];
        for(int index = start; index < end; index++) {
            half[index - start] =  this.plainText[index];
        }
        return half;
    }

    private void innerFunction() throws KeyFormatException {
        try {
            splitPlainText();
            permuter = Permutation.setPermutationMode("ep");
            rightHalf = permuter.permutePlainText(rightHalf);
            xor(rightHalf, key.returnBitArray());
            sbox = new Sbox(rightHalf);
            rightHalf = sbox.getSubstitutedOutput();
            permuter = Permutation.setPermutationMode("p4");
            rightHalf = permuter.permutePlainText(rightHalf);
        } catch (SubstitutionException e) {
            throw new KeyFormatException("Error with substitution");
        }
    }

    public void xor(byte[] half, byte[] xorMaterial) {
        for(int x = 0; x < half.length; x++) {
            if (xorMaterial[x] == half[x]) {
                half[x] = (byte) 0;
            } else {
                half[x] = (byte) 1;
            }
        }
    }

    public byte[] apply() throws KeyFormatException {
        innerFunction();
        xor(leftHalf, rightHalf);
        return combineHalves(rightHalfCopy, leftHalf);
    }

    public byte[] copyHalf(byte[] half) {
        byte[] copyOfHalf = new byte[half.length];
        for(int x = 0; x < half.length; x++) {
            copyOfHalf[x] = half[x];
        }
        return copyOfHalf;
    }

    public byte[] combineHalves(byte[] rightHalf, byte[] leftHalf) {
        for(int x = 0; x < rightHalf.length && x < leftHalf.length; x++) {
            plainText[x] = leftHalf[x];
            plainText[x + rightHalf.length] = rightHalf[x];
        }
        return plainText;
    }

    public byte[] getLeftHalf() {
        byte[] leftCopy = new byte[leftHalf.length];
        System.arraycopy(leftHalf, 0, leftCopy, 0, leftHalf.length);
        return leftCopy;
    }

    public byte[] getRightHalf() {
        byte[] rightCopy = new byte[leftHalf.length];
        System.arraycopy(rightHalf, 0, rightCopy, 0, rightHalf.length);
        return rightCopy;
    }

    public byte[] getRightHalfCopy() {
        byte[] rightHalfCopy = new byte[leftHalf.length];
        System.arraycopy(this.rightHalfCopy, 0, rightHalfCopy, 0, rightHalfCopy.length);
        return rightHalfCopy;
    }


    public static byte[] swap(byte[] array) {
        byte[] right = new byte[4];
        System.arraycopy(array, 4, right, 0, 4);
        byte[] left = new byte[4];
        System.arraycopy(array, 0, left, 0, 4);
        System.arraycopy(right, 0, array,0, 4);
        System.arraycopy(left, 0, array, 4, 4);
        return array;
    }
}