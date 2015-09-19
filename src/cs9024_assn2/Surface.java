package cs9024_assn2;

import javax.swing.*;
import java.awt.*;

/**
 * Created by christophernheu on 13/09/15.
 */
public class Surface extends JPanel {

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.drawString("Java 2D", 50, 50);
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }
}
