package UI;

import Model.DataPoint;
import Model.DataSetGenerator;
import Model.KMeansAlgorithm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class OptionsPanel extends JPanel {
    private static final int DEFAULT_K = 10;

    private KMeansAlgorithm kMeans;
    private JPanel parent;

    private JLabel iterationLabel;
    private JTextField clusterInput;

    public OptionsPanel(KMeansAlgorithm kMeans, JPanel parent) {
        this.kMeans = kMeans;
        this.parent = parent;

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        initializeIterationLabel();

        initializeStepButton();
        initializeRunButton();

        this.add(Box.createVerticalStrut(50));

        initializeClusterInput();
        initializeResetButton();
    }

    private void initializeIterationLabel() {
        iterationLabel = new JLabel("Iteration: 0");
        iterationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(iterationLabel);
    }

    private void initializeStepButton() {
        JButton stepButton = new JButton("Step");
        stepButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                kMeans.step();
                setIterationLabel();
                parent.repaint();
            }
        });
        stepButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(stepButton);
    }

    private void initializeRunButton() {
        JButton runButton = new JButton("Run");
        runButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                kMeans.run();
                setIterationLabel();
                parent.repaint();
            }
        });
        runButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(runButton);
    }

    private void initializeResetButton() {
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                List<DataPoint> dataSet = DataSetGenerator.generateUniformRandomSet(200, 590, 590);
                kMeans.reset(dataSet, getCurrentSetClusterInput());
                setIterationLabel();
                parent.repaint();
            }
        });
        resetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(resetButton);
    }

    private void initializeClusterInput() {
        JLabel kLabel = new JLabel("k:");
        kLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(kLabel);

        clusterInput = new JTextField(10);
        clusterInput.setText(Integer.toString(DEFAULT_K));
        clusterInput.setAlignmentX(Component.CENTER_ALIGNMENT);
        clusterInput.setMaximumSize(new Dimension(50, 20));
        this.add(clusterInput);
    }

    private int getCurrentSetClusterInput() {
        int value = DEFAULT_K;
        try {
            value = Integer.parseInt(clusterInput.getText());
        } catch (Exception e) {
        }

        return value;
    }

    private void setIterationLabel() {
        iterationLabel.setText("Iteration: " + kMeans.getIteration());
    }

    public Dimension getPreferredSize() {
        return new Dimension(200, 600);
    }
}
