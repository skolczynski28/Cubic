import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestCube {

    @Test
    public void testClockwise() {
        Cube cub = new Cube();
        cub.rotate(Color.WHITE, true);
        System.out.println(cub.toString());
    }

    @Test
    public void testCounterClockwise() {
        Cube cub = new Cube();
        cub.rotate(Color.WHITE, false);
        System.out.println(cub.toString());
        cub.rotate(Color.RED, false);
        System.out.println(cub.toString());
    }

    @Test
    public void testEnum() {
        assertEquals((Character)'W', Color.WHITE.getLetter());
    }

    @Test
    public void orientation() {
        Cube cub = new Cube();
        cub.rotate(Color.WHITE, false);
        System.out.println(cub.toString());
    }

}