package code.java.view.container.applet;

import code.java.utils.LU;

import javax.swing.*;
import java.applet.Applet;
//awt means Abstract Window Toolkit
import java.awt.*;

/**
 * JApplet测试方法之：用JFrame来承载JApplet。此方式最方便。
 */

public class AppletWithButton extends Applet/* Deprecated since version 9, use JApplet from Swing package please.)*/ {

    private Button a, b,c;

    public void init() {
        a = new Button("Button A");
        b = new Button("Button B");
        c = new Button("Button C");
        //如果继承JApplet，这Button B全屏遮住 Button A。
        // 继承Applet无此问题。
        add(a);
        add(b);
        add(c);
    }

    /**
     * From thinking in Java.
     * <p>
     * The method inside an Applet subclass
     * where your cascaded(流注/倾泻/a lot of) if statement
     * resides is called action().
     * <p>
     * There are two arguments: the first is of type Event
     * and it contains all the information about the event
     * that triggered this call to action( ). For example,
     * it could be a mouse click, a normal keyboard press or
     * release, a special method key press or release, the
     * fact that the component got or lost the focus,
     * mouse movements or drags, etc.
     * <p>
     * The second argument
     * is, in the normal case, the target of the event,
     * which you’ll often ignore. In addition, the second
     * argument is also encapsulated(压缩) within the Event
     * object and so it is redundant although you may
     * choose not to extract it from the Event object (it seems
     * to be given as a second argument to action( )
     * for other programming situations and not for regular
     * applets or applications).
     * <p>
     * The situations where action( ) gets called are extremely
     * limited: when you place controls on a form,
     * some types of controls (buttons, check boxes, drop-down
     * lists, menus) have a “standard action” that
     * occurs, which causes the call to action( ) with the
     * appropriate Event object.
     * <p>
     * For example, with a
     * button the action( ) method is called when the button
     * is pressed, and at no other time. Normally this
     * is just fine, since that’s what you ordinarily look
     * for with a button. However, it’s possible to deal with
     * many other types of events via the handleEvent( )
     * method as we shall see later in this chapter.
     * The previous example can be extended to handle
     * button clicks as follows:
     */
    // capturing an event by action method.
    public boolean action(
            /**
             * The type of the event
             * it could be a mouse click, a normal keyboard press or
             *release, a special method key press or release, the
             *fact that the component got or lost the focus,
             *mouse movements or drags, etc.
             */
            Event evt,
            /**
             * the target of the event, which you’ll often ignore
             */
            Object arg
    ) {
        if (evt.target.equals(a)) {
            //Exception in thread "AWT-EventQueue-0" java.lang.NullPointerException
            //at java.desktop/java.applet.Applet.getAppletContext(Applet.java:218)
            getAppletContext().showStatus("Button 1");
        } else if (evt.target.equals(b)) {
            //Exception in thread "AWT-EventQueue-0" java.lang.NullPointerException
            //at java.desktop/java.applet.Applet.getAppletContext(Applet.java:218)
            getAppletContext().showStatus("Button 2");
        }
        // Let the base class handle it:
        else {
            LU.println("action-> Button C clicked.");
            return super.action(evt, arg);
        }
        return true; // We've handled it here
    }

    public static void main(String[] args) {
        testAppletWithButton();
    }

    private static void testAppletWithButton() {
        Applet applet = new AppletWithButton();

        // JFrame can be a window and the container of JApplet.
        // Use JFrame to test Applet is really convenient.
        // create JFrame object.
        JFrame frame = new JFrame("AppletTest");

        //Configurates JFrame object.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(applet);
        frame.setSize(300, 300);

        //Calls applet methods.
        applet.init();
        applet.start();

        frame.setVisible(true);
    }

}
