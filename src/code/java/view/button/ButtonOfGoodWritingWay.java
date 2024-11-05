package code.java.view.button;
/*
from Thinking in Java Bruce Eckel
So you have something to compare with,
here’s an example showing the recommended approach. By
now it should be reasonably familiar and comfortable:
*/


//: GoodIdea.java
// The best way to design classes using the new
// Java 1.1 event model: use an inner class for
// each different event. This maximizes
// flexibility and modularity.
import java.awt.*;
import java.awt.event.*;

public class ButtonOfGoodWritingWay extends Frame {
    Button
            b1 = new Button("Button 1"),
            b2 = new Button("Button 2");
    public ButtonOfGoodWritingWay() {
        setLayout(new FlowLayout());
        addWindowListener(new WL());
        b1.addActionListener(new B1());
        b2.addActionListener(new B2());
        add(b1);
        add(b2);
    }
    public class B1 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println("Button 1 pressed");
        }
    }
    public class B2 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println("Button 2 pressed");
        }
    }
    class WL extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            System.out.println("Window Closing");
            System.exit(0);
        }
    }
    public static void main(String args[]) {
        Frame f = new ButtonOfGoodWritingWay();
        f.setSize(300,200);
        f.setVisible(true);
    }
}

/*
This is fairly trivial（琐碎的）: each button has its own listener
which prints something out to the console. But
notice there isn’t an if statement in the entire program,
or any statement which says “I wonder what
caused this event.” Each piece of code is concerned with doing,
not type-checking. This is the best
way to write your code – not only is it easier to conceptualize（构思/概念化）,
but much easier to read and maintain.
Cutting-and-pasting to create new programs is also much easier.
*/

