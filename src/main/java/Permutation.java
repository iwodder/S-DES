import exceptions.KeyFormatException;

public class Permutation {

    private static PermutationMode mode;
    private int[] order;

    private Permutation(PermutationMode mode) {
        this.mode = mode;
        order = PermutationMode.getPermutationOrder(this.mode);
    }

    public PermutationMode getMode() {
        return this.mode;
    }

    public static Permutation setPermutationMode(String mode) {
        mode = mode.toLowerCase();
        switch(mode) {
            case "p10": {
                return new Permutation(PermutationMode.P10);
            }
            case "p8": {
                return new Permutation(PermutationMode.P8);
            }
            case "ep": {
                return new Permutation(PermutationMode.EP);
            }
            case "ip": {
                return new Permutation(PermutationMode.IP);
            }
            case "ipinv": {
                return new Permutation(PermutationMode.IPINV);
            }
            case "p4": {
                return new Permutation(PermutationMode.P4);
            }
        }
        return null;
    }

    public byte[] permutePlainText(byte[] pt) throws KeyFormatException {
        byte[] ip = new byte[order.length];
        int index = 0;
        for(int value : order) {
            ip[index] = pt[value];
            index++;
        }
        return ip;
    }

    public Key permuteKey(Key key) throws KeyFormatException {
        byte[] newKey = new byte[order.length];
        byte[] byteKey = key.returnBitArray();
        int index = 0;
        for(int value : order) {
            newKey[index] = byteKey[value];
            index++;
        }
        return new Key(newKey);
    }

    public enum PermutationMode {
        P10("p10"),
        P8("p8"),
        EP("ep"),
        IP("ip"),
        IPINV("ipinv"),
        P4("p4");

        public String value;
        PermutationMode(String value) {
            this.value = value;
        }

        private static final int[] p10Permutation = {2,4,1,6,3,9,0,8,7,5};
        private static final int[] p8Permutation = {5,2,6,3,7,4,9,8};
        private static final int[] expansionPermutation = {3,0,1,2,1,2,3,0};
        private static final int[] ip = {1,5,2,0,3,7,4,6};
        private static final int[] ipInverse = {3,0,2,4,6,1,7,5};
        private static final int[] p4Permutation = {1,3,2,0};

        private static int[] getPermutationOrder(PermutationMode mode) {
            switch (mode) {
                case P10: {
                    return p10Permutation;
                }
                case P8: {
                    return p8Permutation;
                }
                case EP: {
                    return expansionPermutation;
                }
                case IP: {
                    return ip;
                }
                case IPINV: {
                    return ipInverse;
                }
                case P4: {
                    return p4Permutation;
                }
                default: {
                    return null;
                }
            }
        }
    }
}