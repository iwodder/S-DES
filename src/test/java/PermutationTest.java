import exceptions.KeyFormatException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Permutation should..")
class PermutationTest {

    private Permutation permutation;
                    //1,2,3,4,5,6,7,8,9,10
    byte[] keyBits = {0,1,1,0,1,0,1,0,1,0};
    byte[] permutedP10 = {1,1,1,1,0,0,0,1,0,0};
    byte[] p4 =    {1,0,1,0};
    byte[] permutedP8 = {0,1,1,0,0,1,0,1};
    byte[] expansionPermutation = {0,0,1,1,1,1,0,0};
    byte[] ip = {1,0,1,0,0,0,1,1};
    byte[] ipinv = {0,0,1,1,1,1,0,0};
    private Key key;

    @DisplayName("be able to have a mode set.")
    @Test
    void setPermutationMode() {
        permutation = Permutation.setPermutationMode("p10");
        assertEquals("p10" , permutation.getMode().value);

        permutation = Permutation.setPermutationMode("p8");
        assertEquals("p8", permutation.getMode().value);

        permutation = Permutation.setPermutationMode("ep");
        assertEquals("ep", permutation.getMode().value);

        permutation = Permutation.setPermutationMode("ip");
        assertEquals("ip", permutation.getMode().value);

        permutation = Permutation.setPermutationMode("ipinv");
        assertEquals("ipinv", permutation.getMode().value);
    }

    @DisplayName("should correctly permute a key and return the new key")
    @Test
    void permuteKey() {
        try {
            key = new Key(keyBits);
            permutation = Permutation.setPermutationMode("p10");
            Key newkey = permutation.permuteKey(key);
            assertArrayEquals(permutedP10, newkey.returnBitArray());

            permutation = Permutation.setPermutationMode("p8");
            newkey = permutation.permuteKey(key);
            assertArrayEquals(permutedP8, newkey.returnBitArray());

            permutation = Permutation.setPermutationMode("ep");
            newkey = permutation.permuteKey(key);
            assertArrayEquals(expansionPermutation, newkey.returnBitArray());

            permutation = Permutation.setPermutationMode("ip");
            newkey = permutation.permuteKey(key);
            assertArrayEquals(ip, newkey.returnBitArray());

            permutation = Permutation.setPermutationMode("ipinv");
            newkey = permutation.permuteKey(key);
            assertArrayEquals(ipinv, newkey.returnBitArray());

            permutation = Permutation.setPermutationMode("p4");
            newkey = permutation.permuteKey(key);
            assertArrayEquals(p4, newkey.returnBitArray());
        } catch (KeyFormatException e) {
            e.printStackTrace();
        }

    }
}