import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DataPointTest {
    private DataPoint p1, p2;

    @BeforeEach
    void setUp() {
        p1 = new DataPoint(1, 2);
        p2 = new DataPoint(4, 6);
    }

    @Test
    void initCopy() {
        DataPoint p = new DataPoint(p1);
        assertEquals(1, p.x);
        assertEquals(2, p.y);
    }

    @Test
    void add() {
        DataPoint p = DataPoint.add(p1, p2);
        assertEquals(5, p.x);
        assertEquals(8, p.y);
    }

    @Test
    void divide() {
        DataPoint p = DataPoint.divide(p1, .2);
        assertEquals(5, p.x);
        assertEquals(10, p.y);
    }

    @Test
    void distance() {
        double distance = DataPoint.distance(p1, p2);
        assertEquals(5, distance);
    }

    @Test
    void centroidOf() {
        ArrayList<DataPoint> points = new ArrayList<>();
        points.add(new DataPoint(1, 1));
        points.add(new DataPoint(1, 5));
        points.add(new DataPoint(3, 1));
        points.add(new DataPoint(3, 5));

        DataPoint centroid = DataPoint.centroidOf(points);
        assertEquals(2, centroid.x);
        assertEquals(3, centroid.y);
    }
}