import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Permutation should..")
class PermutationTest {

    Permutation permutation;

    @BeforeEach
    void setup() {
        permutation = Permutation.setPermutationMode(PermutationMode.P10);
    }

    @DisplayName("be able to have a mode set.")
    @Test
    void setPermutationMode() {
        assertEquals(PermutationMode.P10, permutation.getMode());
    }

    @DisplayName("return a the set mode.")
    @Test
    void getMode() {
        assertEquals(PermutationMode.P10, permutation.getMode());
    }
}