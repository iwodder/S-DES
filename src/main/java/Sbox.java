import exceptions.SubstitutionException;

import java.security.KeyException;

public class Sbox {

    private static final int SIZE = 4;
    private static final int[][] S0 = {
            //0         1          2          3
            {1,0,3,2}, {3,2,1,0}, {0,2,1,3}, {3,1,3,2},
    };

    private static final int[][] S1 = {
            //0         1          2          3
            {0,1,2,3}, {2,0,1,3}, {3,0,1,0}, {2,1,0,3},
    };

    private boolean initialized;
    private byte[] input;

    public Sbox(byte[] input) {
        initialized = true;
        this.input = input;
    }

    public byte[] substituteS0(byte[] toBeReduced) {
        byte[] substituted = new byte[2];

        byte r1 = toBeReduced[0];
        byte r2 = toBeReduced[3];

        byte c1 = toBeReduced[1];
        byte c2 = toBeReduced[2];

        byte row = combineBits(r1, r2);
        byte col = combineBits(c1, c2);

        byte toBeSubbed = (byte) S0[row][col];

        substituted[0] = getBit(toBeSubbed, 1);
        substituted[1] = getBit(toBeSubbed, 0);

        return substituted;
    }

    public byte[] substituteS1(byte[] toBeReduced) {
        byte[] substituted = new byte[2];

        byte r1 = toBeReduced[0];
        byte r2 = toBeReduced[3];

        byte c1 = toBeReduced[1];
        byte c2 = toBeReduced[2];

        byte row = combineBits(r1, r2);
        byte col = combineBits(c1, c2);

        byte toBeSubbed = (byte) S1[row][col];

        substituted[0] = getBit(toBeSubbed, 1);
        substituted[1] = getBit(toBeSubbed, 0);

        return substituted;
    }

    public byte getBit(byte retrieveBitFrom, int position) {
        byte mask = 0b0000_0001;
        byte bit = (byte) (retrieveBitFrom >> position);
        return (byte) (mask & bit);
    }

    public byte combineBits(byte one, byte two) {
        return (byte) ((one << 1) | two);
    }

    public byte[] getSubstitutedOutput() throws SubstitutionException {
        if(initialized) {
            byte[] left = splitInput(0, input.length / 2);
            byte[] right = splitInput(input.length / 2, input.length);
            left = substituteS0(left);
            right = substituteS1(right);
            return combineHalves(left, right);
        } else {
            throw new SubstitutionException();
        }
    }

    private byte[] splitInput(int start, int end) {
        byte[] half = new byte[end - start];
        for(int index = start; index < end; index++) {
            half[index - start] =  this.input[index];
        }
        return half;
    }

    private byte[] combineHalves(byte[] left, byte[] right) {
        byte[] output = new byte[left.length + right.length];
        int pos = 0;
        for(int x = 0; x < left.length; x++, pos++) {
            output[pos] = left[x];
            output[pos+2] = right[x];
        }
        return output;
    }
}

