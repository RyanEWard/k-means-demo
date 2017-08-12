import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class KMeansAlgorithmTest {

    @org.junit.jupiter.api.Test
    void init() {
        List<DataPoint> dataSet = new ArrayList<>();
        dataSet.add(new DataPoint(0, 0));
        dataSet.add(new DataPoint(1, 1));
        dataSet.add(new DataPoint(2, 2));

        KMeansAlgorithm kMeans = new KMeansAlgorithm(dataSet, 3);

        assertEquals(0, kMeans.getIteration());

        kMeans = new KMeansAlgorithm(dataSet, 3, 11);

        assertEquals(11, kMeans.getMaxIterations());
        assertEquals(0, kMeans.getDataSet().get(0).x);
        assertEquals(2, kMeans.getDataSet().get(2).y);
    }

    @org.junit.jupiter.api.Test
    void squareExampleTest() {
        List<DataPoint> dataSet = new ArrayList<>();
        dataSet.add(new DataPoint(0, 0));
        dataSet.add(new DataPoint(2, 0));
        dataSet.add(new DataPoint(0, 2));
        dataSet.add(new DataPoint(2, 2));

        DataPoint[] initialCentroids = new DataPoint[2];
        initialCentroids[0] = new DataPoint(1, 0);
        initialCentroids[1] = new DataPoint(1, 1);

        KMeansAlgorithm kMeans = new KMeansAlgorithm(dataSet, initialCentroids);

        kMeans.run();

        Map<Integer, DataPoint[]> iterationToCentroids = kMeans.getIterationToClusterCentroids();

        assertEquals(1, kMeans.getIteration());
        assertFalse(kMeans.getClusterChanged());

        assertEquals(1, iterationToCentroids.get(1)[0].x);
        assertEquals(0, iterationToCentroids.get(1)[0].y);
        assertEquals(1, iterationToCentroids.get(1)[1].x);
        assertEquals(2, iterationToCentroids.get(1)[1].y);
    }

    //values from http://mnemstudio.org/clustering-k-means-example-1.htm
    //values not exact so rounding when checking centroids
    @org.junit.jupiter.api.Test
    void sampleDataTest() {
        List<DataPoint> dataSet = new ArrayList<>();
        dataSet.add(new DataPoint(1, 1));
        dataSet.add(new DataPoint(1.5, 2));
        dataSet.add(new DataPoint(3, 4));
        dataSet.add(new DataPoint(5, 7));
        dataSet.add(new DataPoint(3.5, 5));
        dataSet.add(new DataPoint(4.5, 5));
        dataSet.add(new DataPoint(3.5, 4.5));

        DataPoint[] initialCentroids = new DataPoint[2];
        initialCentroids[0] = new DataPoint(1, 1);
        initialCentroids[1] = new DataPoint(5, 7);

        KMeansAlgorithm kMeans = new KMeansAlgorithm(dataSet, initialCentroids, 10);

        kMeans.step();
        assertEquals(1, kMeans.getIteration());
        assertTrue(kMeans.getClusterChanged());

        kMeans.run();
        assertEquals(2, kMeans.getIteration());
        assertFalse(kMeans.getClusterChanged());

        kMeans.step();
        assertEquals(2, kMeans.getIteration());
        assertFalse(kMeans.getClusterChanged());


        Map<Integer, DataPoint[]> iterationToCentroids = kMeans.getIterationToClusterCentroids();

        assertEquals(1, iterationToCentroids.get(0)[0].x);
        assertEquals(1, iterationToCentroids.get(0)[0].y);
        assertEquals(5, iterationToCentroids.get(0)[1].x);
        assertEquals(7, iterationToCentroids.get(0)[1].y);

        assertEquals(1.8, iterationToCentroids.get(1)[0].x, .1);
        assertEquals(2.3, iterationToCentroids.get(1)[0].y, .1);
        assertEquals(4.1, iterationToCentroids.get(1)[1].x, .1);
        assertEquals(5.4, iterationToCentroids.get(1)[1].y, .1);

        assertEquals(1.2, iterationToCentroids.get(2)[0].x, .1);
        assertEquals(1.5, iterationToCentroids.get(2)[0].y, .1);
        assertEquals(3.9, iterationToCentroids.get(2)[1].x, .1);
        assertEquals(5.1, iterationToCentroids.get(2)[1].y, .1);

        Map<DataPoint, Integer> dataPointToCluster = kMeans.getDataPointToCluster();

        assertEquals(dataPointToCluster.get(dataSet.get(0)).intValue(), 0);
        assertEquals(dataPointToCluster.get(dataSet.get(1)).intValue(), 0);

        assertEquals(dataPointToCluster.get(dataSet.get(2)).intValue(), 1);
        assertEquals(dataPointToCluster.get(dataSet.get(3)).intValue(), 1);
        assertEquals(dataPointToCluster.get(dataSet.get(4)).intValue(), 1);
        assertEquals(dataPointToCluster.get(dataSet.get(5)).intValue(), 1);
        assertEquals(dataPointToCluster.get(dataSet.get(6)).intValue(), 1);
    }

    @org.junit.jupiter.api.Test
    void randomDataTest() {
        List<DataPoint> dataSet = DataSetGenerator.generateUniformRandomSet(100, 100, 100);
        KMeansAlgorithm kMeans = new KMeansAlgorithm(dataSet, 4);

        kMeans.run();
    }
}