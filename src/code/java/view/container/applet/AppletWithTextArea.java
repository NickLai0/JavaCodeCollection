package code.java.view.container.applet;

import javax.swing.*;
import java.applet.Applet;
//awt means Abstract Window Toolkit
import java.awt.*;

public class AppletWithTextArea extends Applet/* Deprecated since version 9, use JApplet from Swing package please.)*/  {

    Button b1 = new Button("Text Area 1");
    Button b2 = new Button("Text Area 2");
    Button b3 = new Button("Replace Text");
    Button b4 = new Button("Insert Text");
    //TextArea可输入多行，而TextField只能输入一行。
    TextArea t1 = new TextArea("t1", 1, 30);
    TextArea t2 = new TextArea("t2", 4, 30);

    public void init() {
        add(b1);
        add(t1);
        add(b2);
        add(t2);
        add(b3);
        add(b4);
    }

    public boolean action(Event evt, Object arg) {
        if (evt.target.equals(b1)) {
            t1.setText("Inserted by Button 1");
        } else if (evt.target.equals(b2)) {
            t2.setText("Inserted by Button 2");
            t2.appendText(". And text from button 1: " + t1.getText());
        } else if (evt.target.equals(b3)) {
            String s = " Replacement ";
            t2.replaceText(s, 3, 3 + s.length());
            //Inserted by Button 2. And text from button 1: Inserted by Button 1
            //上面字符串用" Replacement"去replaceText后，得出下面结果。
            //Ins Replacement on 2. And text from button 1: Inserted by Button 1
        } else if (evt.target.equals(b4)) {
            t2.insertText(" Inserted ", 10);
            //Inserted by Button 2. And text from button 1: Inserted by Button 1
            //上面字符串在insertText插入" Inserted "后，结果如下：
            //Inserted b Inserted y Button 2. And text from button 1: Inserted by Button 1
        } else {
            // Let the base class handle it:
            return super.action(evt, arg);
        }
        return true; // We've handled it here
    }

    public static void main(String[] args) {
        testAppletWithTextArea();
    }

    private static void testAppletWithTextArea() {
        Applet applet = new AppletWithTextArea();

        // JFrame can be a window and the container of JApplet.
        // Use JFrame to test Applet is really convenient.
        // create JFrame object.
        JFrame frame = new JFrame("testAppletWithTextArea");

        //Configurates JFrame object.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(applet);
        frame.setSize(400, 500);

        //Calls applet methods.
        applet.init();
        applet.start();

        frame.setVisible(true);
    }
}
