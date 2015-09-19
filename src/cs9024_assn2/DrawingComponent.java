package cs9024_assn2;

import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.security.InvalidParameterException;

/**
 * Created by christophernheu on 13/09/15.
 */
public class DrawingComponent extends JComponent {

    public AVLTree tree;

    public DrawingComponent(){
        this.tree = null;
        System.out.println("Please enter a tree to print.");
    }

    public DrawingComponent(AVLTree tree) {
        this.tree = tree;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Rectangle rect1 = new Rectangle(5,5,100,200);
        g2.draw(rect1);
        g2.fill(rect1);

        Ellipse2D.Double ellipse1 = new Ellipse2D.Double(100,100,50,20);
        g2.fill(ellipse1);

        Line2D.Double line1 = new Line2D.Double(150,150,100,50);
        g2.draw(line1);
    }
}
