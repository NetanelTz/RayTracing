package Tools;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * test class to check the halton serie
 */
public class HaltonSerie2fTest {

    @Test
    public void getValues() {
        HaltonSerie2f num = new HaltonSerie2f();
        float[] arr = new float[81 * 3];
        for (int i = 0; i < 81 * 2; i += 3) {
            num.inc();
            System.out.println(num.getValues()[0] + "/" + num.getValues()[1]);
        }

    }
}