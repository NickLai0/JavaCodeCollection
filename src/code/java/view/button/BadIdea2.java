package code.java.view.button;
/*
From Thinking in Java.

mixing the approaches

The second bad idea is to mix the two approaches:
use inner listener classes, but also implement one
or more listener interfaces as part of the main class.
This approach has appeared without explanation
in books and documentation, and I can only assume
that the authors thought they must use the
different approaches for different purposes.
But you don’t – in your programming you can probably use
inner listener classes exclusively.

*/

//: BadIdea2.java
// An improvement over BadIdea1.java, since it
// uses the WindowAdapter as an inner class
// instead of implementing all the methods of
// WindowListener, but still misses the
// valuable modularity of inner classes

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class BadIdea2 extends Frame
        implements ActionListener {
    Button
            b1 = new Button("Button 1"),
            b2 = new Button("Button 2");

    public BadIdea2() {
        setLayout(new FlowLayout());
        addWindowListener(new WL());
        b1.addActionListener(this);
        b2.addActionListener(this);
        add(b1);
        add(b2);
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == b1)
            System.out.println("Button 1 pressed");
        else if (source == b2)
            System.out.println("Button 2 pressed");
        else
            System.out.println("Something else");
    }

    class WL extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            System.out.println("Window Closing");
            System.exit(0);
        }
    }

    public static void main(String args[]) {
        Frame f = new BadIdea2();
        f.setSize(300, 200);
        f.setVisible(true);
    }
}
/*

Since actionPerformed( ) is still tightly coupled to the main class, it’s hard to reuse that code. It’s also
messier and less pleasant to read than the inner class approach.
Chapter 13: Creating Windows & Applets 391

There’s no reason you have to use any of the old thinking for events in Java 1.1 – so why do it?
*/