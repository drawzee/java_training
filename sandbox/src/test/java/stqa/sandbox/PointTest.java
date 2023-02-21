package stqa.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTest {

    @Test
    public void distanceTest() {
        Point p1 = new Point(1.5, 2.5);
        Point p2 = new Point(3.5, 3.5);
        double distance = p1.distance(p2);
        Assert.assertEquals(distance, 2.23606797749979);
    }

    @Test
    public void zeroCoordinates() {
        Point p1 = new Point(0.0, 0.0);
        Point p2 = new Point(3.5, 3.5);
        double distance = p1.distance(p2);
        Assert.assertEquals(distance, 4.949747468305833);
    }

    @Test
    public void negativeCoordinates() {
        Point p1 = new Point(-1.5, -2.5);
        Point p2 = new Point(-3.5, -3.5);
        double distance = p1.distance(p2);
        Assert.assertEquals(distance, 2.23606797749979);
    }

}
