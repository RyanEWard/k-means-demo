import java.util.List;

public class DataPoint {
    public double x;
    public double y;

    public DataPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public DataPoint(DataPoint p) {
        this.x = p.x;
        this.y = p.y;
    }

    public static DataPoint add(DataPoint p1, DataPoint p2) {
        return new DataPoint(p1.x + p2.x, p1.y + p2.y);
    }

    public static DataPoint divide(DataPoint p, double s) {
        return new DataPoint(p.x / s, p.y / s);
    }

    public static double distance(DataPoint p1, DataPoint p2) {
        return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }

    public static DataPoint centroidOf(List<DataPoint> points) {
        return DataPoint.divide(
                points
                        .stream()
                        .reduce(new DataPoint(0, 0), DataPoint::add)
                , points.size());
    }
}
