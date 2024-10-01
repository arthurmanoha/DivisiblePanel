package divisiblecontainer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;
import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;

/**
 *
 * @author arthu
 */
public class DivisiblePanel extends JPanel implements MouseListener, MouseMotionListener, ComponentListener {

    private JSplitPane splitPane;
    private JPanel subpanel1, subpanel2;
    private boolean isFinal;
    private Color color;

    private int id;
    private static int NB_DIV_PANELS_CREATED = 0;

    public DivisiblePanel() {
        super();

        id = NB_DIV_PANELS_CREATED;
        NB_DIV_PANELS_CREATED++;

        isFinal = true;
        Random r = new Random();
        color = computeColor(this.id);
        setPreferredSize(new Dimension(600, 400));
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addComponentListener(this);

        getInputMap().put(KeyStroke.getKeyStroke("S"), "split");
        getActionMap().put("split",
                new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("split.");
            }
        });

        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancel_split");
        getActionMap().put("cancel_split",
                new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("cancel_split.");
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(color);
        g.fillRect(0, 0, g.getClipBounds().width, g.getClipBounds().height);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Mouse pressed on divpanel " + id + " (" + e.getX() + ", " + e.getY() + ");");
        split();
    }

    private void split() {
        System.out.println("    do split.");

        // Do the actual split here.
        subpanel1 = new DivisiblePanel();
        subpanel2 = new DivisiblePanel();

        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, subpanel1, subpanel2) {
            @Override
            protected void paintChildren(Graphics g) {
                super.paintChildren(g);
            }

        };
        this.setLayout(new BorderLayout());
        subpanel1.setLayout(new BorderLayout());
        subpanel2.setLayout(new BorderLayout());
        add(splitPane);
        isFinal = false;
        revalidate();
        splitPane.setDividerLocation(0.5);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Show the potential separation line.
    }

    @Override
    public void componentResized(ComponentEvent e) {
        if (!isFinal) {
            int w = this.getWidth();
            int h = this.getHeight();
            splitPane.setSize(w, h);
        }
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

    private Color computeColor(int id) {
        switch (id % 5) {
        case 0:
            return Color.red;
        case 1:
            return Color.blue;
        case 2:
            return Color.yellow;
        case 3:
            return Color.green;
        case 4:
            return Color.gray;
        }
        return Color.white;
    }
}
