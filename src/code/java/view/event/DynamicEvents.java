package code.java.view.event;
/*
binding events dynamically

  One of the benefits of the new AWT event model is flexibility.
  In the old model you were forced to
  hard-code the behavior of your program,
  but with the new model you can add and remove event
  behavior with single method calls.
  The following example demonstrates this:

* */
//: DynamicEvents.java
// The new Java 1.1 event model allows you to
// change event behavior dynamically. Also
// demonstrates multiple actions for an event.

import java.awt.*;
import java.awt.event.*;
import java.util.*;
//this Demo comes from Thinking in Java.
public class DynamicEvents extends Frame {
    Vector v = new Vector();
    int i = 0;
    Button
            b1 = new Button("Button 1"),
            b2 = new Button("Button 2");

    public DynamicEvents() {
        addWindowListener(new BWL());
        setLayout(new FlowLayout());
        b1.addActionListener(new B());
        b1.addActionListener(new B1());
        b2.addActionListener(new B());
        b2.addActionListener(new B2());
        add(b1);
        add(b2);
    }

    class B implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println("B button was pressed");
        }
    }

    class CountListener implements ActionListener {
        int index;

        public CountListener(int i) {
            index = i;
        }

        public void actionPerformed(ActionEvent e) {
            System.out.println("Counted Listener " + index);
        }
    }

    class B1 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println("Button 1 pressed");
            ActionListener a = new CountListener(i++);
            v.addElement(a);
            b2.addActionListener(a);
        }
    }

    class B2 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println("Button 2 pressed");
            int end = v.size() - 1;
            if (end >= 0) {
                b2.removeActionListener(
                        (ActionListener) v.elementAt(end));
                v.removeElementAt(end);
            }
        }
    }

    class BWL extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            System.out.println("Window Closing");
            System.exit(0);
        }
    }

    public static void main(String args[]) {
        Frame f = new DynamicEvents();
        f.setSize(300, 200);
        f.show();
    }
}

/*
 * The new twists in this example are:
 *  1. There is more than one listener attached to each Button.
 * Usually, components handle
 * events as multicast(多广播), meaning you can register many
 * listeners for a single event. In the
 * special components where an event is handled as unicast,
 *  you’ll get a  TooManyListenersException.
 *
 * 2. During the execution of the program,
 * listeners are dynamically added (from the button b1)
 * and removed from
 * the Button b2. Adding is accomplished（熟练的） in
 * the way you’ve seen before, but each
 * component also has a removeXXXListener( ) method
 * to remove each type of listener.
 * This kind of flexibility provides much greater power
 * in your programming.
 */