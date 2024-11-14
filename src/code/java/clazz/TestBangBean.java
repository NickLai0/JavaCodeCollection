package code.java.clazz;

/*
from Thinking In Java.


a more sophisticated Bean

        This next example is slightly more sophisticated,
        albeit frivolous(尽管无聊，even though boring).
        It’s a canvas that draws a
        little circle around the mouse whenever it moves.
         When you press the mouse, the word “Bang!” appears
         in the middle of the screen,
         and an action listener is fired(is triggered).
        The properties you can change are the size of the circle
        as well as the color, size and text of the word
        that is displayed when you press the mouse.
        A BangBean also has its own addActionListener( ) and
        removeActionListener( ) so you can attach your
        own listener that will be fired when the user clicks
        on the BangBean. You should be able to recognize
        the property and event support:

* */
//: TestBangBean.java
// A graphical Bean

import code.java.utils.ThreadUtils;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

class BangBean extends Canvas implements Serializable {
    private int mouseX, mouseY;
    private int circleSize = 20; // Circle size
    private String text = "Bang!";
    private int fontSize = 48;
    private Color tColor = Color.red;
    private ActionListener actionListener;

    public BangBean() {
        addMouseListener(new ML());
        addMouseMotionListener(new MM());
    }

    public int getCircleSize() {
        return circleSize;
    }

    public void setCircleSize(int newSize) {
        circleSize = newSize;
    }

    public String getBangText() {
        return text;
    }

    public void setBangText(String newText) {
        text = newText;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int newSize) {
        fontSize = newSize;
    }

    public Color getTextColor() {
        return tColor;
    }

    public void setTextColor(Color newColor) {
        tColor = newColor;
    }

    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.drawOval(
                mouseX - circleSize / 2,
                mouseY - circleSize / 2,
                circleSize,
                circleSize
        );
    }

    // This is a unicast listener, which is
    // the simplest form of listener management:
    public void addActionListener(
            ActionListener l)
            throws TooManyListenersException {
        if (actionListener != null)
            throw new TooManyListenersException();
        actionListener = l;
    }

    public void removeActionListener(
            ActionListener l) {
        actionListener = null;
    }

    class ML extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            Graphics g = getGraphics();
            g.setColor(tColor);
            //Java Core 1: The third argument is the point size. Points are commonly used in typography（版面设计） to indicate the size of a font. There are 72 points per inch.
            g.setFont(new Font("TimesRoman", Font.BOLD, fontSize));
            int width = g.getFontMetrics().stringWidth(text);
            g.drawString(text,
                    (getSize().width - width) / 2,
                    getSize().height / 2);
            //上面字符串显示后，立马隐藏。
            g.dispose();
            // Call the listener’s method:
            if (actionListener != null)
                actionListener.actionPerformed(
                        new ActionEvent(BangBean.this,
                                ActionEvent.ACTION_PERFORMED,
                                null)
                );
        }
    }

    class MM extends MouseMotionAdapter {
        public void mouseMoved(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
            repaint();
        }
    }
}

public class TestBangBean extends Frame {
    BangBean bb = new BangBean();

    public TestBangBean() {
        setTitle("BangBean Test");
        addWindowListener(new WL());
        setSize(300, 300);
        add(bb, BorderLayout.CENTER);
        try {
            bb.addActionListener(new BBL());
        } catch (TooManyListenersException e) {
        }
    }

    class WL extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }

    class BBL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println("BangBean action");
        }
    }

    public static void main(String args[]) {
        Frame f = new TestBangBean();
        f.setVisible(true);
    }
} /// :~
/*

The first thing you’ll notice is that BangBean
implements the Serializable interface. This means that
the application builder tool can “pickle”(腌渍) all the
information for the BangBean using serialization after
the program designer has adjusted the values of the properties.
When the Bean is created as part of
the running application, these “pickled” properties
are restored so you get exactly what you designed.
You can see that all the fields are private,
which is what you’ll normally do with a Bean – allow access
only through methods, usually using the “property” scheme.
When you look at the signature for addActionListener( ),
you’ll see that it may throw a

TooManyListenersException. This indicates that it is unicast,
 which means it only notifies one listener
when the event occurs. Normally, you’ll use multicast events,
which means that many listeners may be
notified of an event. However, that runs into issues
that you won’t be ready for until the next chapter,
so it will be revisited there
(under the heading “JavaBeans revisited”).
A unicast event sidesteps the
problem.
When you press the mouse, the text is put
in the middle of the BangBean, and if the actionListener

field is not null, its actionPerformed( )
is called. Whenever the mouse is moved, its new coordinates
are captured and the canvas is repainted
(erasing, you’ll see, any text that’s on the canvas).

TestBangBean creates a Frame and places
a BangBean within it, attaching a simple ActionListener to
the BangBean. Normally, of course,
the application builder tool would create most of the code that
uses the Bean.
When you run the BangBean through BeanDumper,
you’ll notice there are many more properties
and actions than are evident from the above code.
That’s because BangBean is inherited from

Canvas, and Canvas is itself a Bean, so you’re seeing
its properties and events as well.

* */