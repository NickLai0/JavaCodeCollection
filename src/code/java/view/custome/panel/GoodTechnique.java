package code.java.view.custome.panel;

/*
From Thinking in Java.I modified a lot.
demonstrate a costume panel、draw string.

inheriting a component

Another place where you’ll
often see variations on the old way of doing things
is when creating a new type of component. Here’s
a simple example showing that here, too, the new way
works:
*/
//: GoodTechnique.java
// Your first choice when overriding components
// should be to install listeners. The code is
// much safer, more modular and maintainable.

import java.awt.*;
import java.awt.event.*;

class Display {

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

    Display() {
        evnt = new String[last];
        for (int i = 0; i < last; i++) {
            evnt[i] = "";
        }
    }

    public void drawStrings(Graphics g) {
        for (int i = 0; i < last; i++) {
            int stringYPositionOffset = 10 * i + 10;
            g.drawString(evnt[i], 0, stringYPositionOffset);
        }
    }
}

class EnabledPanel extends Panel {
    Color c;
    int id;
    Display display = new Display();

    public EnabledPanel(int i, Color mc) {
        id = i;
        c = mc;
        setLayout(new BorderLayout());
        add(new MyButton(), BorderLayout.SOUTH);
        addComponentListener(new CL());
        addFocusListener(new FL());
        addKeyListener(new KL());
        addMouseListener(new ML());
        addMouseMotionListener(new MML());
    }

    /*
        repaint causes a call to this component's
        paint or update method as soon as possible.
    */
    // To eliminate flicker(闪烁):
    public void update(Graphics g) {
        paint(g);
    }

    /*
            repaint causes a call to this component's
            paint or update method as soon as possible.
        */
    public void paint(Graphics g) {
        g.setColor(c);
        Dimension s = getSize();
        g.fillRect(0, 0, s.width, s.height);
        g.setColor(Color.black);
        display.drawStrings(g);
    }

    // Don't need to enable anything for this:
    public void processEvent(AWTEvent e) {
        display.evnt[Display.event] = e.toString();
/*
    Repaints this component. If this component is a lightweight component,
    this method causes a call to this component's paint method as soon as
    possible. Otherwise, this method causes a call to this component's
    update method as soon as possible.
*/
        repaint();
        super.processEvent(e);
    }

    class CL implements ComponentListener {
        public void componentMoved(ComponentEvent e) {
            display.evnt[Display.component] = "component moved";
            //draw strings while mouse moving on component.
            repaint();
        }

        public void componentResized(ComponentEvent e) {
            display.evnt[Display.component] = "component resized";
            repaint();
        }

        public void componentHidden(ComponentEvent e) {
            display.evnt[Display.component] = "component hidden";
            repaint();
        }

        public void componentShown(ComponentEvent e) {
            display.evnt[Display.component] = "component shown";
            repaint();
        }
    }

    class FL implements FocusListener {
        public void focusGained(FocusEvent e) {
            display.evnt[Display.focus] = "focus gained";
            repaint();
        }

        public void focusLost(FocusEvent e) {
            display.evnt[Display.focus] = "focus lost";
            repaint();
        }
    }

    class KL implements KeyListener {
        public void keyPressed(KeyEvent e) {
            display.evnt[Display.key] = "key pressed: ";
            showCode(e);
        }

        public void keyReleased(KeyEvent e) {
            display.evnt[Display.key] = "key released: ";
            showCode(e);
        }

        public void keyTyped(KeyEvent e) {
            display.evnt[Display.key] = "key typed: ";
            showCode(e);
        }

        void showCode(KeyEvent e) {
            int code = e.getKeyCode();
            display.evnt[Display.key] += KeyEvent.getKeyText(code);
            repaint();
        }
    }

    class ML implements MouseListener {
        public void mouseClicked(MouseEvent e) {
            requestFocus(); // Get focus on click
            display.evnt[Display.mouse] =
                    "mouse clicked";
            showMouse(e);
        }

        public void mousePressed(MouseEvent e) {
            display.evnt[Display.mouse] =
                    "mouse pressed";
            showMouse(e);
        }

        public void mouseReleased(MouseEvent e) {
            display.evnt[Display.mouse] =
                    "mouse released";
            showMouse(e);
        }

        public void mouseEntered(MouseEvent e) {
            display.evnt[Display.mouse] =
                    "mouse entered";
            showMouse(e);
        }

        public void mouseExited(MouseEvent e) {
            display.evnt[Display.mouse] =
                    "mouse exited";
            showMouse(e);
        }

        void showMouse(MouseEvent e) {
            display.evnt[Display.mouse] +=
                    ", x = " + e.getX() +
                            ", y = " + e.getY();
            repaint();
        }
    }

    class MML implements MouseMotionListener {
        public void mouseDragged(MouseEvent e) {
            display.evnt[Display.mouseMove] = "mouse dragged";
            showMouse(e);
        }

        public void mouseMoved(MouseEvent e) {
            display.evnt[Display.mouseMove] = "mouse moved";
            showMouse(e);
        }

        void showMouse(MouseEvent e) {
            display.evnt[Display.mouseMove] += ", x = " + e.getX() + ", y = " + e.getY();
            repaint();
        }
    }
}

class MyButton extends Button {
    int clickCounter;
    String label = "";

    public MyButton() {
        addActionListener(new AL());
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
        int horizMargin = (getSize().width - width) / 2;
        int verMargin = (getSize().height - height) / 2;
        g.setColor(Color.red);
        g.drawString(label, horizMargin, verMargin + ascent + leading);
    }

    class AL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            clickCounter++;
            label = "click #" + clickCounter +
                    " " + e.toString();
            repaint();
        }
    }
}

public class GoodTechnique extends Frame {
    GoodTechnique() {
        setLayout(new GridLayout(2, 2));
        add(new EnabledPanel(1, Color.cyan));
        add(new EnabledPanel(2, Color.lightGray));
        add(new EnabledPanel(3, Color.yellow));
        addWindowListener(new WL());
    }

    class WL extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            System.out.println(e);
            System.out.println("Window Closing");
            System.exit(0);
        }
    }

    public static void main(String args[]) {
        Frame f = new GoodTechnique();
        f.setTitle("Please click the panels and move the mouse there。");
        f.setSize(700, 700);
        f.setVisible(true);
    }

}
