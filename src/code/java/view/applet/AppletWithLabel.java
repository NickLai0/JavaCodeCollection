package code.java.view.applet;

import javax.swing.*;
import java.applet.Applet;
//awt means Abstract Window Toolkit
import java.awt.*;

public class AppletWithLabel extends Applet/* Deprecated since version 9, use JApplet from Swing package please.)*/  {

    TextField t1 = new TextField("t1", 10);

    //Label
    Label labl1 = new Label("Label 1");
    Label labl2 = new Label("                                    ");
    Label labl3 = new Label("                                    ", Label.RIGHT);

    Button b1 = new Button("Set text for label1");
    Button b2 = new Button("Set text for label2");

    public void init() {
        add(labl1);
        add(t1);
        add(b1);
        add(labl2);
        add(b2);
        add(labl3);
    }

    public boolean action(Event evt, Object arg) {
        if (evt.target.equals(b1)) {
            labl2.setText("Text set into Label2");
        } else if (evt.target.equals(b2)) {
            if (labl3.getText().trim().length() == 0) {
                labl3.setText("Text set into Label3");
            }
            switchAlignment4Label3();
        } else {
            //Otherwise handle by
            return super.action(evt, arg);
        }
        return true;
    }

    private void switchAlignment4Label3() {
        if (labl3.getAlignment() == Label.LEFT) {
            labl3.setAlignment(Label.CENTER);
        } else if (labl3.getAlignment() == Label.CENTER) {
            labl3.setAlignment(Label.RIGHT);
        } else if (labl3.getAlignment() == Label.RIGHT) {
            labl3.setAlignment(Label.LEFT);
        }
    }

    public static void main(String[] args) {
        testAppletWithLabel();
    }

    private static void testAppletWithLabel() {
        Applet applet = new AppletWithLabel();

        // JFrame can be a window and the container of JApplet.
        // Use JFrame to test Applet is really convenient.
        // create JFrame object.
        JFrame frame = new JFrame("AppletWithLabel");

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
