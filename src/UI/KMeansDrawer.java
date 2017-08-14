package UI;

import Model.DataPoint;
import Model.KMeansAlgorithm;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class KMeansDrawer extends JPanel {

    private static final int DATA_POINT_WIDTH = 4;
    private static final int DRAW_MARGIN = DATA_POINT_WIDTH;

    private static final Color[] STARTING_CLUSTER_COLORS
            = new Color[]{Color.blue, Color.red, Color.green, Color.orange, Color.magenta, Color.yellow};

    private KMeansAlgorithm kMeans;

    private boolean drawCentroidHistory = false;

    public KMeansDrawer(KMeansAlgorithm kMeans) {
        setVisible(true);
        setOpaque(false);

        this.kMeans = kMeans;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHints(rh);

        paintDataSet(g2);
        paintClusterCentroids(g2);
    }

    private void paintDataSet(Graphics g) {
        Map<DataPoint, Integer> dataPointToCluster = kMeans.getDataPointToCluster();

        for (DataPoint p : kMeans.getDataSet()) {
            setDataPointColor(g, dataPointToCluster, p);
            drawDataPoint(g, p);
        }
    }

    private void paintClusterCentroids(Graphics g) {
        Map<Integer, DataPoint[]> itertionToClusterCentroids = kMeans.getIterationToClusterCentroids();

        if (drawCentroidHistory) {
            for (int i = 0; i < kMeans.getIteration() - 1; i++) {
                //TODO iterate across historical centroids
            }
        }
        DataPoint[] currentCentroids = itertionToClusterCentroids.get(kMeans.getIteration());
        for (int cluster = 0; cluster < currentCentroids.length; cluster++) {
            setClusterColor(g, cluster);
            drawCentroid(g, currentCentroids[cluster]);
        }
    }

    private void setDataPointColor(Graphics g, Map<DataPoint, Integer> dataPointToCluster, DataPoint p) {
        setClusterColor(g, dataPointToCluster.get(p));
    }

    private void setClusterColor(Graphics g, int cluster) {
        if (cluster < STARTING_CLUSTER_COLORS.length) {
            g.setColor(STARTING_CLUSTER_COLORS[cluster]);
        } else if (false) {
            //TODO add colors past hard coded ones
        } else {
            g.setColor(Color.black);
        }
    }

    private void drawDataPoint(Graphics g, DataPoint p) {
        g.fillOval((int) p.x * 6, (int) p.y * 6, DATA_POINT_WIDTH, DATA_POINT_WIDTH);
    }

    private void drawCentroid(Graphics g, DataPoint centroid) {
        g.drawLine(
                (int) centroid.x * 6 - DATA_POINT_WIDTH,
                (int) centroid.y * 6 - DATA_POINT_WIDTH,
                (int) centroid.x * 6 + DATA_POINT_WIDTH,
                (int) centroid.y * 6 + DATA_POINT_WIDTH);
        g.drawLine(
                (int) centroid.x * 6 - DATA_POINT_WIDTH,
                (int) centroid.y * 6 + DATA_POINT_WIDTH,
                (int) centroid.x * 6 + DATA_POINT_WIDTH,
                (int) centroid.y * 6 - DATA_POINT_WIDTH);
    }

    public Dimension getPreferredSize() {
        return new Dimension(600, 600);
    }
}
