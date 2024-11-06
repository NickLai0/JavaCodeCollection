package code.java.view.button;

/*
From Thinking in Java.



implementing the main class as a listener

The first bad idea is a very common and
recommended approach. This makes the main
class (typically
Applet or Frame, but it could be any class)
implement the various listeners.
Here’s an example:

*/
//: BadIdea1.java
// Some literature(作品/书) recommends this approach,
// but it's missing the point of the new event
// model in Java 1.1

import java.awt.*;
import java.awt.event.*;

import java.util.*;

public class BadIdea1 extends Frame

    /*
    this way is much less modular so it’s harder to grab and paste.
    ...
        Not only is
    this going backwards, away from the listener model,
    but you can’t easily reuse the
    actionPerformed( ) method since it’s very specific
    to this particular application.
    */
        implements ActionListener, WindowListener {
    Button
            b1 = new Button("Button 1"),
            b2 = new Button("Button 2");

    public BadIdea1() {
        setLayout(new FlowLayout());
        addWindowListener(this);
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

    public void windowClosing(WindowEvent e) {
        System.out.println("Window Closing");
        System.exit(0);
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }

    public void windowOpened(WindowEvent e) {
    }

    public static void main(String args[]) {
        Frame f = new BadIdea1();
        f.setSize(300, 200);
        f.setVisible(true);
    }
}
/*
From Thinking in Java.

The use of this shows up in the three lines:

addWindowListener(this);
 b1.addActionListener(this);
 b2.addActionListener(this);

Since BadIdea1 implements ActionListener and WindowListener
these lines are certainly
acceptable, and if you’re still stuck in the mode of trying
to make fewer classes to reduce server hits
during applet loading, it seems to be a good idea. However:
        1. Java 1.1 supports JAR files so all your files
        can be placed in a single compressed JAR archive
        which only requires one server hit. You no longer need
        to reduce class count for Internet efficiency.

    2. The above code is much less modular so it’s harder
    to grab and paste. Notice that you must
    not only implement the various interfaces for your main class,
    but in actionPerformed( )

you’ve got to detect which action was performed using
a cascaded if statement. Not only is
this going backwards, away from the listener model,
but you can’t easily reuse the
actionPerformed( ) method since it’s very specific
to this particular application. Contrast

this with GoodIdea.java, where you can just grab one listener class
and paste it in anywhere else with minimal fuss(无谓忧虑).
Plus you can register multiple listener classes with a
single event, allowing even more modularity
in what each listener class does.

*/