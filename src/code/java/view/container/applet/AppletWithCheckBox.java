package code.java.view.container.applet;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;

public class AppletWithCheckBox extends Applet/* Deprecated since version 9, use JApplet from Swing package please.)*/  {

    TextArea t = new TextArea(6, 20);
    //复选框，和Android很像
    Checkbox cb1 = new Checkbox("Check Box 1");
    Checkbox cb2 = new Checkbox("Check Box 2");
    Checkbox cb3 = new Checkbox("Check Box 3");

    public void init() {
        add(t);
        add(cb1);
        add(cb2);
        add(cb3);
    }

    public boolean action(Event evt, Object arg) {
        if (evt.target.equals(cb1))
            trace("1", cb1.getState());
        else if (evt.target.equals(cb2))
            trace("2", cb2.getState());
        else if (evt.target.equals(cb3))
            trace("3", cb3.getState());
        else
            return super.action(evt, arg);
        return true;
    }

    void trace(String b, boolean state) {
        if (state)
            t.appendText("\nCheck Box " + b + " Set");
        else
            t.appendText("\nCheck Box " + b + " Cleared");
    }

    public static void main(String[] args) {
        testAppletWithCheckBox();
    }

    private static void testAppletWithCheckBox() {
        Applet applet = new AppletWithCheckBox();

        // JFrame can be a window and the container of JApplet.
        // Use JFrame to test Applet is really convenient.
        // create JFrame object.
        JFrame frame = new JFrame("AppletWithCheckBox");

        //Configurates JFrame object.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(applet);
        frame.setSize(800, 500);

        //Calls applet methods.
        applet.init();
        applet.start();

        frame.setVisible(true);
    }

}
