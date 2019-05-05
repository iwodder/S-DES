import exceptions.KeyFormatException;

import java.util.BitSet;

public class Permutation {

    private static PermutationMode mode;
    private int[] order;

    private Permutation(PermutationMode mode) {
        this.mode = mode;
    }

    public static Permutation setPermutationMode(PermutationMode mode) {
        switch(mode) {
            case P10: {
                return new Permutation(mode);
            }

        }
        return null;
    }

    public PermutationMode getMode() {
        return this.mode;
    }

    public BitSet permutateKey(Key key) throws KeyFormatException {



        return null;
    }

    private void mutationOrder(int[] permutationOrder) {
        this.order = permutationOrder;
    }
}