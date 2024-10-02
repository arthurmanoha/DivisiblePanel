package divisiblepanel;

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
import java.util.ArrayList;
import java.util.Random;
import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;
import static javax.swing.JSplitPane.VERTICAL_SPLIT;
import javax.swing.KeyStroke;

/**
 *
 * @author arthu
 */
public class DivisiblePanel extends JPanel implements MouseListener, MouseMotionListener, ComponentListener {

    private JSplitPane splitPane;
    private DivisiblePanel subpanel1, subpanel2;
    private boolean isFinal;
    private Color color;

    // The potential cut is displayed as a line.
    private int mouseX, mouseY;
    private int splitDirection;
    protected boolean isSplitting;

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

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "split");
        getActionMap().put("split",
                new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSplitting(true);
                repaint();
            }
        });

        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancel_split");
        getActionMap().put("cancel_split",
                new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSplitting(false);
                repaint();
            }

        });
    }

    public DivisiblePanel(DivisiblePanel original) {
        this();
    }

    public DivisiblePanel clone() {
        return new DivisiblePanel(this);
    }

    private void setSplitting(boolean b) {
        isSplitting = b;
        if (!isFinal) {
            subpanel1.setSplitting(b);
            subpanel2.setSplitting(b);
        }
    }

    public boolean isFinal() {
        return isFinal;
    }

    @Override
    public void paintComponent(Graphics g) {

        if (isSplitting) {

            int w = g.getClipBounds().width;
            int h = g.getClipBounds().height;
            g.setColor(Color.black);
            if (splitDirection == HORIZONTAL_SPLIT) {
                g.drawLine(mouseX, 0, mouseX, h);
            } else {
                g.drawLine(0, mouseY, w, mouseY);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

        if (isSplitting) {
            splitDirection = getSplitDirection(e.getX(), e.getY());
            split(splitDirection);
        }
    }

    /**
     *
     * @param x x-coordinate of the mouse within the panel
     * @param y y-coordinate of the mouse within the panel
     * @return the direction of the split if it were made now
     */
    private int getSplitDirection(int x, int y) {

        double ex = (double) x / this.getWidth();
        double ey = (double) y / this.getHeight();
        if (ex > ey && ex > 1 - ey || ex < ey && ex < 1 - ey) {
            return VERTICAL_SPLIT;
        } else {
            return HORIZONTAL_SPLIT;
        }
    }

    private void split(int direction) {

        // Do the actual split here.
        subpanel1 = this.clone();
        subpanel2 = this.clone();

        splitPane = new JSplitPane(direction, true, subpanel1, subpanel2) {
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
        splitPane.setDividerLocation(getWidth() / 2);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseX = -1;
        mouseY = -1;
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // Show the potential separation line.
        splitDirection = getSplitDirection(e.getX(), e.getY());
        mouseX = e.getX();
        mouseY = e.getY();
        repaint();
    }

    @Override
    public void componentResized(ComponentEvent e) {
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

    public ArrayList<DivisiblePanel> getAllFinalSubPanels() {
        ArrayList<DivisiblePanel> allPanels = new ArrayList<>();
        if (this.isFinal) {
            allPanels.add(this);
        } else {
            allPanels.addAll(subpanel1.getAllFinalSubPanels());
            allPanels.addAll(subpanel2.getAllFinalSubPanels());
        }
        return allPanels;
    }
}
