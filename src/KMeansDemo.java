import javax.swing.*;
import java.awt.*;
import java.util.List;

public class KMeansDemo {
    public static void main(String[] args) {
        initializeAlgorithm();
        show();
    }

    private static void show() {
        JFrame frame = new JFrame("k-Means Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(800, 600));

        frame.getContentPane().add(panel);

        frame.pack();
        frame.setVisible(true);
    }

    private static void initializeAlgorithm() {
        List<DataPoint> dataSet = DataSetGenerator.generateUniformRandomSet(100, 100, 100);
        KMeansAlgorithm kmeans = new KMeansAlgorithm(dataSet, 3);
    }
}
