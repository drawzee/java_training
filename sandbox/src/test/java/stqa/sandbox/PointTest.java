package stqa.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;
import stqa.sandbox.Point;

public class DistanceTests {

    @Test
    public void distanceTest() {
        Point p1 = new Point(1.5, 2.5);
        Point p2 = new Point(3.5, 3.5);
        Assert.assertEquals(Point.distance(p1, p2), 2.23606797749979);
    }

}
