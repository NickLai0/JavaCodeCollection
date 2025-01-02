package code.java.view.container.applet;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;

public class AppletWithRadioButton extends Applet/* Deprecated since version 9, use JApplet from Swing package please.)*/  {

    TextField t = new TextField(30);

    //创建CheckboxGroup容器，用来存放多个Checkbox
    CheckboxGroup g = new CheckboxGroup();

    //创建多个Checkbox,存放到CheckboxGroup里。
    Checkbox cb1 = new Checkbox("one", g, false);
    Checkbox cb2 = new Checkbox("two", g, true);
    Checkbox cb3 = new Checkbox("three", g, false);

    public void init() {
        add(t);
        // 为何是添加checkBox，而不是添加CheckboxGroup？
        // 我始终认为添加CheckboxGroup才是良好的设计，
        // 因为其管理CheckBox就好了呀！
        // Android就这样设计的。
        add(cb1);
        add(cb2);
        add(cb3);

        //Disable edit. Just for showing text.
        t.setEditable(false);
    }

    public boolean action(Event evt, Object arg) {
        if (evt.target.equals(cb1))
            t.setText("Radio button 1");
        else if (evt.target.equals(cb2))
            t.setText("Radio button 2");
        else if (evt.target.equals(cb3))
            t.setText("Radio button 3");
        else
            return super.action(evt, arg);
        return true;
    }

    public static void main(String[] args) {
        testAppletWithRadioButton();
    }

    private static void testAppletWithRadioButton() {
        Applet applet = new AppletWithRadioButton();

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
