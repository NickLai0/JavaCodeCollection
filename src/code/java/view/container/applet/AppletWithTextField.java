package code.java.view.container.applet;

import javax.swing.*;
import java.applet.Applet;
//awt means Abstract Window Toolkit
import java.awt.*;

public class AppletWithTextField extends Applet/* Deprecated since version 9, use JApplet from Swing package please.)*/  {

    Button b1, b2;
    //TextArea可输入多行，而TextField只能输入一行。
    TextField t;
    String s = new String();

    public void init() {
        b1 = new Button("Get Text");
        b2 = new Button("Set Text");
        t = new TextField("Starting text", 30);
        add(b1);
        add(b2);
        add(t);
    }

    public boolean action (Event evt, Object arg) {
        if(evt.target.equals(b1)) {
//            getAppletContext().showStatus(t.getText());
            s = t.getSelectedText();
            if(s.length() == 0) s = t.getText();
            t.setEditable(true);
        }
        else if(evt.target.equals(b2)) {
            t.setText("Inserted by Button 2: " + s);
            //disable edit.
            //t.setEditable(false);
        }
        // Let the base class handle it:
        else
            return super.action(evt, arg);
        return true; // We've handled it here
    }

    public static void main(String[] args) {
        testAppletWithTextField();
    }

    private static void testAppletWithTextField() {
        Applet applet = new AppletWithTextField();

        // JFrame can be a window and the container of JApplet.
        // Use JFrame to test Applet is really convenient.
        // create JFrame object.
        JFrame frame = new JFrame("code.java.applet.AppletWithTextField");

        //Configurates JFrame object.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(applet);
        frame.setSize(500, 500);

        //Calls applet methods.
        applet.init();
        applet.start();

        frame.setVisible(true);
    }

}
