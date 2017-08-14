package UI;

import Model.DataPoint;
import Model.DataSetGenerator;
import Model.KMeansAlgorithm;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DemoPanel extends JPanel {

    private KMeansAlgorithm kMeans;

    public DemoPanel() {
        initializeAlgorithm();

        this.setLayout(new BorderLayout());

        OptionsPanel options = new OptionsPanel(kMeans, this);
        add(options, BorderLayout.LINE_START);

        KMeansDrawer drawer = new KMeansDrawer(kMeans);
        add(drawer, BorderLayout.CENTER);
    }

    private void initializeAlgorithm() {
        List<DataPoint> dataSet = DataSetGenerator.generateUniformRandomSet(100, 100, 100);
        kMeans = new KMeansAlgorithm(dataSet, 3);
    }

    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }
}
