import exceptions.KeyFormatException;

import java.util.Arrays;

public class S_DES {
    public static void main(String[] args) {
        try {
            byte[] keyBits = {0,0,1,0,0,1,0,1,1,1};
            byte[] plainText = {1,0,1,0,0,1,0,1};

            Key key = new Key(keyBits);
            KeyGeneration keyGeneration = new KeyGeneration(key);
            RoundFunction rf;

            Key k1 = keyGeneration.createKey1();
            Key k2 = keyGeneration.createKey2();

            System.out.println(Arrays.toString(plainText));

            Permutation ip = Permutation.setPermutationMode("ip");
            byte[] ipPermuatation = ip.permutePlainText(plainText);

            rf = new RoundFunction(ipPermuatation, k1);
            plainText = rf.apply();

            plainText = RoundFunction.swap(plainText);

            rf = new RoundFunction(plainText, k2);
            plainText = rf.apply();

            ip = Permutation.setPermutationMode("ipinv");
            byte[] ipInv = ip.permutePlainText(plainText);

            System.out.println(Arrays.toString(ipInv));
        } catch (KeyFormatException  e) {
            System.out.println("Error creating key");
        }
    }
}
