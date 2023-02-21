package stqa.sandbox;

public class Point {

    double x;
    double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Point other) {
        double xdif = this.x - other.x;
        double ydif = this.y - other.y;
        return Math.sqrt(Math.pow(xdif, 2) + Math.pow(ydif, 2));
    }

}
