package UI;

import Model.KMeansAlgorithm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionsPanel extends JPanel {

    private KMeansAlgorithm kMeans;
    private JPanel parent;

    public OptionsPanel(KMeansAlgorithm kMeans, JPanel parent) {
        this.kMeans = kMeans;
        this.parent = parent;

        this.setLayout(new FlowLayout());

        initializeStepButton();
        initializeRunButton();
    }

    private void initializeStepButton() {
        JButton stepButton = new JButton("Step");
        stepButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                kMeans.step();
                parent.repaint();
            }
        });
        this.add(stepButton);
    }

    private void initializeRunButton() {
        JButton runButton = new JButton("Run");
        runButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                kMeans.run();
                parent.repaint();
            }
        });
        this.add(runButton);
    }

    public Dimension getPreferredSize() {
        return new Dimension(200, 600);
    }
}
