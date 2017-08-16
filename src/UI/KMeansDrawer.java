package UI;

import Model.DataPoint;
import Model.KMeansAlgorithm;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class KMeansDrawer extends JPanel {

    private static final int DATA_POINT_WIDTH = 6;
    private static final int DRAW_MARGIN = DATA_POINT_WIDTH;

    private static final Color[] STARTING_CLUSTER_COLORS
            = new Color[]{Color.blue, Color.red, Color.green, Color.orange, Color.magenta, Color.yellow};
    private static Random rnd = new Random();

    private KMeansAlgorithm kMeans;
    private ArrayList<Color> additionalColors;
    private boolean drawCentroidHistory = true;

    public KMeansDrawer(KMeansAlgorithm kMeans) {
        setVisible(true);
        setOpaque(false);

        additionalColors = new ArrayList<>();

        this.kMeans = kMeans;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setBackground(Color.white);

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
        Map<Integer, DataPoint[]> iterationToClusterCentroids = kMeans.getIterationToClusterCentroids();

        if (drawCentroidHistory) {
            for (int cluster = 0; cluster < iterationToClusterCentroids.get(0).length; cluster++) {
                setClusterColor(g, cluster);

                for (int iteration = 0; iteration < kMeans.getIteration() - 1; iteration++) {
                    DataPoint priorCentroidHistory = iterationToClusterCentroids.get(iteration)[cluster];
                    DataPoint nextCentroidHistory = iterationToClusterCentroids.get(iteration + 1)[cluster];
                    drawPriorCentroid(g, priorCentroidHistory);
                    drawCentroidHistoryLine(g, priorCentroidHistory, nextCentroidHistory);
                }

                int lastIteration = kMeans.getIteration();

                if (lastIteration > 0) {
                    DataPoint lastIterationCentroid = iterationToClusterCentroids.get(lastIteration - 1)[cluster];
                    DataPoint currentCentroid = iterationToClusterCentroids.get(lastIteration)[cluster];

                    drawPriorCentroid(g, lastIterationCentroid);
                    drawCentroidHistoryLine(g, lastIterationCentroid, currentCentroid);
                }
            }
        }
        DataPoint[] currentCentroids = iterationToClusterCentroids.get(kMeans.getIteration());
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
        } else if (cluster - STARTING_CLUSTER_COLORS.length < additionalColors.size()) {
            //additional color already randomly assigned
            g.setColor(additionalColors.get(cluster - STARTING_CLUSTER_COLORS.length));
        } else {
            //assign additional random colors as needed
            //using 200 max to avoid white-like colors
            while (cluster - STARTING_CLUSTER_COLORS.length >= additionalColors.size()) {
                additionalColors.add(new Color(rnd.nextInt(200), rnd.nextInt(200), rnd.nextInt(200)));
            }
            g.setColor(additionalColors.get(cluster - STARTING_CLUSTER_COLORS.length));
        }
    }

    private void drawDataPoint(Graphics g, DataPoint p) {
        g.fillOval(
                (int) p.x + DRAW_MARGIN,
                (int) p.y + DRAW_MARGIN,
                DATA_POINT_WIDTH,
                DATA_POINT_WIDTH);
    }

    private void drawCentroid(Graphics g, DataPoint centroid) {
        g.drawLine(
                (int) centroid.x - DATA_POINT_WIDTH + DRAW_MARGIN,
                (int) centroid.y - DATA_POINT_WIDTH + DRAW_MARGIN,
                (int) centroid.x + DATA_POINT_WIDTH + DRAW_MARGIN,
                (int) centroid.y + DATA_POINT_WIDTH + DRAW_MARGIN);
        g.drawLine(
                (int) centroid.x - DATA_POINT_WIDTH + DRAW_MARGIN,
                (int) centroid.y + DATA_POINT_WIDTH + DRAW_MARGIN,
                (int) centroid.x + DATA_POINT_WIDTH + DRAW_MARGIN,
                (int) centroid.y - DATA_POINT_WIDTH + DRAW_MARGIN);
    }

    private void drawPriorCentroid(Graphics g, DataPoint p) {
        g.drawOval(
                (int) p.x - DATA_POINT_WIDTH + DRAW_MARGIN,
                (int) p.y - DATA_POINT_WIDTH + DRAW_MARGIN,
                2 * DATA_POINT_WIDTH,
                2 * DATA_POINT_WIDTH);
    }

    private void drawCentroidHistoryLine(Graphics g, DataPoint p1, DataPoint p2) {
        g.drawLine((int) p1.x + DRAW_MARGIN,
                (int) p1.y + DRAW_MARGIN,
                (int) p2.x + DRAW_MARGIN,
                (int) p2.y + DRAW_MARGIN);
    }

    public Dimension getPreferredSize() {
        return new Dimension(800, 500);
    }
}
