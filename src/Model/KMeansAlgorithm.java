package Model;

import java.util.*;

public class KMeansAlgorithm {

    private static final int MAX_ITERATIONS = 100;

    private int iteration;
    private int maxIterations;
    private boolean clusterChanged;

    private List<DataPoint> dataSet;

    private DataPoint[] currentClusterCentroids;
    private Map<Integer, DataPoint[]> iterationToClusterCentroids;
    private Map<Integer, ArrayList<DataPoint>> clusterToDataPoints;
    private Map<DataPoint, Integer> dataPointToCluster;

    public KMeansAlgorithm(List<DataPoint> dataSet, int k) {
        this(dataSet, k, new DataPoint[0], MAX_ITERATIONS);
    }

    public KMeansAlgorithm(List<DataPoint> dataSet, int k, int maxIterations) {
        this(dataSet, k, new DataPoint[0], maxIterations);
    }

    public KMeansAlgorithm(List<DataPoint> dataSet, DataPoint[] initialCentroids) {
        this(dataSet, initialCentroids.length, initialCentroids, MAX_ITERATIONS);
    }

    public KMeansAlgorithm(List<DataPoint> dataSet, DataPoint[] initialCentroids, int maxIterations) {
        this(dataSet, initialCentroids.length, initialCentroids, maxIterations);
    }

    private KMeansAlgorithm(List<DataPoint> dataSet, int k, DataPoint[] initialCentroids, int maxIterations) {
        this.dataSet = dataSet;

        iteration = 0;
        this.maxIterations = maxIterations;
        clusterChanged = true;

        if (initialCentroids.length != 0) {
            currentClusterCentroids = initialCentroids;
        } else {
            currentClusterCentroids = initializeRandomCentroids(dataSet, k);
        }

        initializeClusterAssignments();
        initializeIterationToClusterCentroids();
    }

    private DataPoint[] initializeRandomCentroids(List<DataPoint> dataSet, int k) {
        List<DataPoint> copy = new ArrayList<>(dataSet);
        Collections.shuffle(copy);
        return copy.subList(0, k).toArray(new DataPoint[0]);
    }

    private void initializeClusterAssignments() {
        clusterToDataPoints = new HashMap<>();
        dataPointToCluster = new HashMap<>();

        for (int i = 0; i < currentClusterCentroids.length; i++) {
            clusterToDataPoints.put(i, new ArrayList<>());
        }

        assignDataPoints();
    }

    private void initializeIterationToClusterCentroids() {
        iterationToClusterCentroids = new HashMap<>();
        assignIterationClusters();
    }

    public void run() {
        while (!stopCriteriaReached()) {
            step();
        }
    }

    public void step() {
        if (stopCriteriaReached()) {
            return;
        }

        iteration++;
        clusterChanged = false;

        assignCentroids();
        assignDataPoints();
        assignIterationClusters();
    }

    private void assignDataPoints() {
        for (Integer cluster : clusterToDataPoints.keySet()) {
            clusterToDataPoints.get(cluster).clear();
        }

        for (DataPoint p : dataSet) {
            int newCluster = findClosestClusterCentroid(p);

            clusterToDataPoints.get(newCluster).add(p);

            if (!clusterChanged && dataPointToCluster.containsKey(p) && dataPointToCluster.get(p) != newCluster) {
                clusterChanged = true;
            }
            dataPointToCluster.put(p, newCluster);
        }
    }

    private void assignIterationClusters() {
        iterationToClusterCentroids.put(iteration, deepCopy(currentClusterCentroids));
    }

    private void assignCentroids() {
        for (int i = 0; i < currentClusterCentroids.length; i++) {
            currentClusterCentroids[i] = DataPoint.centroidOf(clusterToDataPoints.get(i));
        }
    }

    private boolean stopCriteriaReached() {
        return (iteration >= maxIterations || !clusterChanged);
    }

    private int findClosestClusterCentroid(DataPoint p) {
        int closestCentroid = 0;
        double minDistance = Double.MAX_VALUE;

        for (int i = 0; i < currentClusterCentroids.length; i++) {
            double dist = DataPoint.distance(p, currentClusterCentroids[i]);
            if (dist < minDistance) {
                closestCentroid = i;
                minDistance = dist;
            }
        }

        return closestCentroid;
    }

    private DataPoint[] deepCopy(DataPoint[] points) {
        DataPoint[] copy = new DataPoint[points.length];
        for (int i = 0; i < points.length; i++) {
            copy[i] = new DataPoint(points[i]);
        }
        return copy;
    }

    public int getIteration() {
        return iteration;
    }

    public int getMaxIterations() {
        return maxIterations;
    }

    public boolean getClusterChanged() {
        return clusterChanged;
    }

    public List<DataPoint> getDataSet() {
        return Collections.unmodifiableList(dataSet);
    }

    public Map<Integer, DataPoint[]> getIterationToClusterCentroids() {
        return Collections.unmodifiableMap(iterationToClusterCentroids);
    }

    public Map<DataPoint, Integer> getDataPointToCluster() {
        return Collections.unmodifiableMap(dataPointToCluster);
    }
}
