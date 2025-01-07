package code.java.thread.sync;
/*
From Thinking in Java.
 Demonstrate how to use synchronized keyword.

 JavaBeans revisited

Now that you understand synchronization you can take another look at JavaBeans. Whenever you
create a Bean, you must assume that it will run in a multithreaded environment. This means that:
1. Whenever possible, all the public methods of a Bean should be synchronized. Of course
this incurs the synchronized runtime overhead. If that’s a problem, methods that will not
cause problems in critical sections may be left un-synchronized, but keep in mind this is
not always obvious. Methods that qualify tend to be very small (such as getCircleSize( ) in
the following example) and/or “atomic,” that is, the method call executes in such a short
amount of code that the object cannot be changed during execution. Making such
methods un-synchronized may not have a significant effect on the execution speed of
your program. You might as well make all public methods of a Bean synchronized and
only remove the synchronized keyword when you know for sure that it’s necessary and
that it makes a difference.
2. When firing a multicast event to a bunch of listeners interested in that event, you must
assume that listeners may be added or removed while moving through the list.
The first point is fairly easy to deal with, but the second point requires a little more thought. Consider
the BangBean.java example presented in the last chapter. That ducked out of the multithreading

question by ignoring the synchronized keyword
 (which hadn’t been introduced yet) and making the
event unicast. Here’s that example modified to
work in a multithreaded environment and to use
multicasting for events:
 */

//: BangBean2.java
// You should write your Beans this way so they
// can run in a multithreaded environment.


import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class BangBean2 extends Canvas implements Serializable {
    private int xm, ym;
    private int cSize = 20; // Circle size
    private String text = "Bang!";
    private int fontSize = 48;
    private Color tColor = Color.red;
    private Vector actionListeners = new Vector();

    public BangBean2() {
        addMouseListener(new ML());
        addMouseMotionListener(new MM());
    }

    public synchronized int getCircleSize() {
        return cSize;
    }

    public synchronized void setCircleSize(int newSize) {
        cSize = newSize;
    }

    public synchronized String getBangText() {
        return text;
    }

    public synchronized void setBangText(String newText) {
        text = newText;
    }

    public synchronized int getFontSize() {
        return fontSize;
    }

    public synchronized void setFontSize(int newSize) {
        fontSize = newSize;
    }

    public synchronized Color getTextColor() {
        return tColor;
    }

    public synchronized void setTextColor(Color newColor) {
        tColor = newColor;
    }

    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.drawOval(xm - cSize / 2, ym - cSize / 2, cSize, cSize);
    }
    // This is a multicast listener, which is
    // more typically used than the unicast
    // approach taken in BangBean.java:

    public synchronized void addActionListener(ActionListener l) {
        actionListeners.addElement(l);
    }

    public synchronized void removeActionListener(ActionListener l) {
        actionListeners.removeElement(l);
    }

    // Notice this isn't synchronized:
    public void notifyListeners() {
        ActionEvent a = new ActionEvent(BangBean2.this, ActionEvent.ACTION_PERFORMED, null);
        Vector lv = null;
        // Make a copy of the vector in case someone
        // adds a listener while we're
        // calling listeners:
        synchronized (this) {
            //每次通知外部之前都线程安全地clone出来新的监听器集合。
            lv = (Vector) actionListeners.clone();
        }
        // Call all the listener methods:
        for (int i = 0; i < lv.size(); i++) {
            ActionListener al = (ActionListener) lv.elementAt(i);
            al.actionPerformed(a);
        }
    }

    class ML extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            Graphics g = getGraphics();
            g.setColor(tColor);
            //Java Core 1: The third argument is the point size. Points are commonly used in typography（版面设计） to indicate the size of a font. There are 72 points per inch.
            g.setFont(new Font("TimesRoman", Font.BOLD, fontSize));
            int width = g.getFontMetrics().stringWidth(text);
            g.drawString(text, (getSize().width - width) / 2, getSize().height / 2);
            g.dispose();
            notifyListeners();
        }
    }

    class MM extends MouseMotionAdapter {
        public void mouseMoved(MouseEvent e) {
            xm = e.getX();
            ym = e.getY();
            repaint();
        }
    }
}

