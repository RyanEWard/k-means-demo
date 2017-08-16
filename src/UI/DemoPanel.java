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

        KMeansDrawer drawer = new KMeansDrawer(kMeans);
        add(drawer, BorderLayout.CENTER);

        OptionsPanel options = new OptionsPanel(kMeans, this);
        add(options, BorderLayout.PAGE_END);
    }

    private void initializeAlgorithm() {
        List<DataPoint> dataSet = DataSetGenerator.generateUniformRandomSet(200, 800, 540);
        kMeans = new KMeansAlgorithm(dataSet, 10);
    }

    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }
}
