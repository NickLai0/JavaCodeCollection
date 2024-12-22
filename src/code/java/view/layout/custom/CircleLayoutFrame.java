package code.java.view.layout.custom;

import javax.swing.*;

/**
 * From Java Core 1.
 *  * Modify a little.
 * A frame that shows buttons arranged along a circle.
 */
public class CircleLayoutFrame extends JFrame {

    public CircleLayoutFrame() {
        setLayout(new CircleLayout());
        add(new JButton("Yellow"));
        add(new JButton("Blue"));
        add(new JButton("Red"));
        add(new JButton("Green"));
        add(new JButton("Orange"));
        add(new JButton("Fuchsia"));
        add(new JButton("Indigo"));
        pack();
    }

    public static void main(String[] args) {
        new CircleLayoutFrame().setVisible(true);
    }

}
