import UI.DemoPanel;

import javax.swing.*;

public class KMeansDemo {

    public static void main(String[] args) {
        show();
    }

    private static void show() {
        JFrame frame = new JFrame("k-Means Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DemoPanel panel = new DemoPanel();

        frame.getContentPane().add(panel);

        frame.pack();
        frame.setVisible(true);
    }
}
