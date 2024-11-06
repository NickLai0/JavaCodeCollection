package code.java.view.custome.panel;

/*
ugly component inheritance

The alternative, which you will see
 put forward in
many published works, is to call enableEvents( )

and pass it the masks corresponding to the events
you want to handle. This causes those events to be
sent to the old-style methods (although they’re new
to Java 1.1) with names like

processFocusEvent( ). You must also remember to call
the base-class version. Here’s what it looks
like:

*/

//: BadTechnique.java
// It's possible to override components this way,
// but the listener approach is much better, so
// why would you?

import java.awt.*;
import java.awt.event.*;

class Display1 {
    public static final int
            event = 0,
            component = 1,
            mouse = 2,
            mouseMove = 3,
            focus = 4,
            key = 5,
            action = 6,
            last = 7;
    public String evnt[];

    Display1() {
        evnt = new String[last];
        for (int i = 0; i < last; i++)
            evnt[i] = new String();
    }

    public void show(Graphics g) {
        for (int i = 0; i < last; i++)
            g.drawString(evnt[i], 0, 10 * i + 10);
    }
}

class EnabledPanel1 extends Panel {
    Color c;
    int id;
    Display1 display1 = new Display1();

    public EnabledPanel1(int i, Color mc) {
        id = i;
        c = mc;
        setLayout(new BorderLayout());
        add(new MyButton1(), BorderLayout.SOUTH);
        //There is using listeners method in GoodTechnique.java.
        // Type checking is lost. You can enable and
        // process events that the component doesn't
        // capture:
        enableEvents(
                // Panel doesn't handle these:
                AWTEvent.ACTION_EVENT_MASK |
                        AWTEvent.COMPONENT_EVENT_MASK |
                        AWTEvent.ADJUSTMENT_EVENT_MASK |
                        AWTEvent.ITEM_EVENT_MASK |
                        AWTEvent.TEXT_EVENT_MASK |
                        AWTEvent.WINDOW_EVENT_MASK |
                        // Panel can handle these:
                        AWTEvent.FOCUS_EVENT_MASK |
                        AWTEvent.KEY_EVENT_MASK |
                        AWTEvent.MOUSE_EVENT_MASK |
                        AWTEvent.MOUSE_MOTION_EVENT_MASK |
                        AWTEvent.CONTAINER_EVENT_MASK);
        // You can enable an event without
        // overriding its process method.
    }

    // To eliminate flicker:
    public void update(Graphics g) {
        paint(g);
    }

    public void paint(Graphics g) {
        g.setColor(c);
        Dimension s = getSize();
        g.fillRect(0, 0, s.width, s.height);
        g.setColor(Color.black);
        display1.show(g);
    }

    public void
    processEvent(AWTEvent e) {
        display1.evnt[Display1.event] = e.toString();
        repaint();
        super.processEvent(e);
    }

    public void
    processComponentEvent(ComponentEvent e) {
        switch (e.getID()) {
            case ComponentEvent.COMPONENT_MOVED:
                display1.evnt[Display1.component] =
                        "component moved";
                break;
            case ComponentEvent.COMPONENT_RESIZED:
                display1.evnt[Display1.component] =
                        "component resized";
                break;
            case ComponentEvent.COMPONENT_HIDDEN:
                display1.evnt[Display1.component] =
                        "component hidden";
                break;
            case ComponentEvent.COMPONENT_SHOWN:
                display1.evnt[Display1.component] =
                        "component shown";
                break;
            default:
        }
        repaint();
        // Must always remember to call the "super"
        // version of whatever you override:
        super.processComponentEvent(e);
    }

    public void
    processFocusEvent(FocusEvent e) {
        switch (e.getID()) {
            case FocusEvent.FOCUS_GAINED:
                display1.evnt[Display1.focus] =
                        "focus gained";
                break;
            case FocusEvent.FOCUS_LOST:
                display1.evnt[Display1.focus] =
                        "focus lost";
                break;
            default:
        }
        repaint();
        super.processFocusEvent(e);
    }

    public void
    processKeyEvent(KeyEvent e) {
        switch (e.getID()) {
            case KeyEvent.KEY_PRESSED:
                display1.evnt[Display1.key] =
                        "key pressed: ";
                break;
            case KeyEvent.KEY_RELEASED:
                display1.evnt[Display1.key] =
                        "key released: ";
                break;
            case KeyEvent.KEY_TYPED:
                display1.evnt[Display1.key] =
                        "key typed: ";
                break;
            default:
        }
        int code = e.getKeyCode();
        display1.evnt[Display1.key] +=
                KeyEvent.getKeyText(code);
        repaint();
        super.processKeyEvent(e);
    }

    public void
    processMouseEvent(MouseEvent e) {
        switch (e.getID()) {
            case MouseEvent.MOUSE_CLICKED:
                requestFocus(); // Get focus on click
                display1.evnt[Display1.mouse] =
                        "mouse clicked";
                break;
            case MouseEvent.MOUSE_PRESSED:
                display1.evnt[Display1.mouse] =
                        "mouse pressed";
                break;
            case MouseEvent.MOUSE_RELEASED:
                display1.evnt[Display1.mouse] =
                        "mouse released";
                break;
            case MouseEvent.MOUSE_ENTERED:
                display1.evnt[Display1.mouse] =
                        "mouse entered";
                break;
            case MouseEvent.MOUSE_EXITED:
                display1.evnt[Display1.mouse] =
                        "mouse exited";
                break;
            default:
        }
        display1.evnt[Display1.mouse] +=
                ", x = " + e.getX() +
                        ", y = " + e.getY();
        repaint();
        super.processMouseEvent(e);
    }

    public void
    processMouseMotionEvent(MouseEvent e) {
        switch (e.getID()) {
            case MouseEvent.MOUSE_DRAGGED:
                display1.evnt[Display1.mouseMove] =
                        "mouse dragged";
                break;
            case MouseEvent.MOUSE_MOVED:
                display1.evnt[Display1.mouseMove] =
                        "mouse moved";
                break;
            default:
        }
        display1.evnt[Display1.mouseMove] +=
                ", x = " + e.getX() +
                        ", y = " + e.getY();
        repaint();
        super.processMouseMotionEvent(e);
    }
}

class MyButton1 extends Button {
    int clickCounter;
    String label = "";

    public MyButton1() {
        enableEvents(AWTEvent.ACTION_EVENT_MASK);
    }

    public void paint(Graphics g) {
        g.setColor(Color.green);
        Dimension s = getSize();
        g.fillRect(0, 0, s.width, s.height);
        g.setColor(Color.black);
        g.drawRect(0, 0, s.width - 1, s.height - 1);
        drawLabel(g);
    }

    private void drawLabel(Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int width = fm.stringWidth(label);
        int height = fm.getHeight();
        int ascent = fm.getAscent();
        int leading = fm.getLeading();
        int horizMargin =
                (getSize().width - width) / 2;
        int verMargin =
                (getSize().height - height) / 2;
        g.setColor(Color.red);
        g.drawString(label, horizMargin,
                verMargin + ascent + leading);
    }

    public void
    processActionEvent(ActionEvent e) {
        clickCounter++;
        label = "click #" + clickCounter +
                " " + e.toString();
        repaint();
        super.processActionEvent(e);
    }
}

public class BadTechnique extends Frame {
    BadTechnique() {
        setLayout(new GridLayout(2, 2));
        add(new EnabledPanel1(1, Color.cyan));
        add(new EnabledPanel1(2, Color.lightGray));
        add(new EnabledPanel1(3, Color.yellow));
        // You can also do it for Windows:
        enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    }

    public void processWindowEvent(WindowEvent e) {
        System.out.println(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            System.out.println("Window Closing");
            System.exit(0);
        }
    }

    public static void main(String args[]) {
        Frame f = new BadTechnique();
        f.setTitle("Bad Technique");
        f.setSize(700, 700);
        f.setVisible(true);
    }
}
/*
Sure, it works. But it’s ugly and hard to write,
read, debug, maintain and reuse. So why bother when
you can use inner listener classes?
*/