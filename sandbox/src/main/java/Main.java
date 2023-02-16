public class Main {

    public static double distance(double x1, double x2, double y1, double y2) {
        double xdif = x1 - x2;
        double ydif = y1 - y2;
        return Math.sqrt(Math.pow(xdif, 2) + Math.pow(ydif, 2));
    }

    public static void main(String[] args) {
        double x1 = 5.5;
        double x2 = 9.5;
        double y1 = 1.5;
        double y2 = 9.5;
        double distance = distance(x1, x2, y1, y2);
        System.out.println("Distance between (" + x1 + ", " + y1 + ") and (" + x2 + ", " + y2 + ") is " + distance);

        Point p1 = new Point(5.5, 1.5);
        Point p2 = new Point(9.5, 9.5);
        double qwe = Point.distance(p1, p2);
        System.out.println("Distance between (" + p1.x + ", " + p1.y + ") and (" + p2.x + ", " + p2.y + ") is " + qwe);
    }

}
