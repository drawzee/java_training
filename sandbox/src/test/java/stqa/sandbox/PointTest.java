package stqa.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTest {

    @Test
    public void distanceTest() {
        Point p1 = new Point(1.5, 2.5);
        Point p2 = new Point(3.5, 3.5);
        double distance = Point.distance(p1, p2);
        Assert.assertEquals(distance, 2.23606797749979);
    }

    @Test
    public void notNullTest() {
        Point p1 = new Point(1.5, 2.5);
        Point p2 = new Point(3.5, 3.5);
        double distance = Point.distance(p1, p2);
        Assert.assertNotNull(distance);
    }

    @Test
    public void positiveValueTest() {
        Point p1 = new Point(1.5, 2.5);
        Point p2 = new Point(3.5, 3.5);
        double distance = Point.distance(p1, p2);
        Assert.assertTrue(distance > 0);
    }

}
