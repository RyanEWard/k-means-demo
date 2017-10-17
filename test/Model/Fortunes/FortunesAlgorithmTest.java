package Model.Fortunes;

import Model.DataPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class FortunesAlgorithmTest {
    private List<DataPoint> points;

    @BeforeEach
    void setUp() {
        points = new ArrayList<>();
        points.add(new DataPoint(1, 1));
        points.add(new DataPoint(3, 1));
    }

    @Test
    void run() {
        FortunesAlgorithm fa = new FortunesAlgorithm(points);
        fa.getResult();
    }
}