package stqa.sandbox;

public class Point {

    double x;
    double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static double distance(Point p1, Point p2) {
        double xdif = p2.x - p1.x;
        double ydif = p2.y - p1.y;
        return Math.sqrt(Math.pow(xdif, 2) + Math.pow(ydif, 2));
    }

}
