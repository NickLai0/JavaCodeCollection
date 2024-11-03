package code.java.view.applet;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;

public class AppletWithChoice extends Applet/* Deprecated since version 9, use JApplet from Swing package please.)*/  {
    String description[] = {
            "Ebullient",
            "Obtuse",
            "Recalcitrant",
            "Brilliant",
            "Somnescent",
            "Timorous",
            "Florid",
            "Putrescent"
    };
    TextField t = new TextField(30);
    //单选列表（Single selection list）。
    //You can select a choice from many choices.
    Choice c = new Choice();
    Button b = new Button("Add items");
    int count = 0;

    public void init() {
        //Just for showing text.
        t.setEditable(false);
        for (int i = 0; i < 4; i++) {
            c.addItem(description[count++]);
        }
        add(t);
        add(c);
        add(b);
    }

    public boolean action(Event evt, Object arg) {
        if (evt.target.equals(c))
            //After selected a choice would go here.
            //arg is the value selected.
            t.setText("index: " + c.getSelectedIndex() + " " + arg);
        else if (evt.target.equals(b)) {
            if (count < description.length)
                //Add new item after you clicked the button until there is no item.
                c.addItem(description[count++]);
        } else
            return super.action(evt, arg);
        return true;
    }

    public static void main(String[] args) {
        testAppletWithChoice();
    }

    private static void testAppletWithChoice() {
        Applet applet = new AppletWithChoice();

        // JFrame can be a window and the container of JApplet.
        // Use JFrame to test Applet is really convenient.
        // create JFrame object.
        JFrame frame = new JFrame("AppletWithChoice");

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
