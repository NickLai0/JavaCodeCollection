package code.java.view.custome.component;

import code.java.utils.LU;

import javax.swing.*;
import java.awt.*;

/*
From Core Java volume 1. Modified a little.


In our case, we want to add a single component
to the frame onto which we will draw our message.
To draw on a component, you deﬁne a class that
extends JComponent and override the paint
Component method in that class. The paint
Component method takes one parameter of type
Graphics.

A Graphics object remembers a
collection of settings for drawing images
and text, such as the font you set or the
 current color. All drawing in Java must
 go through a Graphics object.
*/

public class NotHelloWorld {

    public static void main(String[] args) {
        LU.println("main method run on thread: " + Thread.currentThread());
        /**
         * Causes runnable to have its run method
         * called in the dispatch thread of the
         * system EventQueue. This will happen
         * after all pending events are processed.
         *
         * Params:
         * runnable – the Runnable whose run method
         * should be executed asynchronously
         * in the event dispatch thread of the
         * system EventQueue
         *
         * Since:
         * 1.2
         * See Also:
         * invokeAndWait,
         * Toolkit. getSystemEventQueue,
         * isDispatchThread
         */
        EventQueue.invokeLater(() -> {
            LU.println("run method run on thread: " + Thread.currentThread());
            var frame = new NotHelloWorldFrame();
            frame.setTitle("NotHelloWorld");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

}

/**
 * A frame that contains a message panel.
 */

class NotHelloWorldFrame extends JFrame {

    public NotHelloWorldFrame() {
        add(new NotHelloWorldComponent());
        /**
         * resizes this window,
         * taking into account（考虑/considering）
         * the preferred sizes of its
         * components.
         */
        pack();
    }

}

/**
 * A component that displays a message.
 */

class NotHelloWorldComponent extends JComponent {

    public static final int MESSAGE_X = 75;
    public static final int MESSAGE_Y = 100;
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;
//is the method to override to describe how your component needs to
//be painted.
    public void paintComponent(Graphics g) {
        g.drawString(
                "Not a Hello, World program",
                MESSAGE_X,
                MESSAGE_Y
        );
    }

    //让Jframe在调用pack方法后，优先使用此方法返回的宽高来布局。
    //the method to override to return the preferred size of this component.
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

}
