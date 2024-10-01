package divisiblecontainer;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author arthu
 */
public class DivisibleContainerMain {

    public static void main(String[] args) {

        JFrame f = new JFrame();

        JPanel customPanel = new DivisiblePanel();
        f.add(customPanel);
        f.setPreferredSize(new Dimension(600, 400));
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
    }

}
