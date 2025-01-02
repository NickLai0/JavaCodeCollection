package code.java.view.button;

//: Button2New.java
// Capturing button presses

import code.java.view.container.dialog.HintDialog;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListeningEvents extends Applet {
    Button b1, b2;

    public void init() {
        b1 = new Button("Button 1");
        b2 = new Button("Button 2");
        add(b1);
        add(b2);
    }

 /* The old way:
 public boolean action(Event evt, Object arg) {
 if(evt.target.equals(b1))
 getAppletContext().showStatus("Button 1");
 else if(evt.target.equals(b2))
 getAppletContext().showStatus("Button 2");
 // Let the base class handle it:
 else
 return super.action(evt, arg);
 return true; // We've handled it here
 }
 */


    public static void main(String[] args) {
        testButton2New();
    }

    private static void testButton2New() {
        ButtonListeningEvents b = new ButtonListeningEvents();

        // JFrame can be a window and the container of JApplet.
        // Use JFrame to test Applet is really convenient.
        // create JFrame object.
        JFrame frame = new JFrame("testButton2New");

        //Configurates JFrame object.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(b);
        frame.setSize(300, 300);

        //Calls applet methods.
        b.init();
        b.start();


        initListeners(b, frame);

        frame.setVisible(true);
    }

    private static void initListeners(ButtonListeningEvents b, JFrame frame) {
        b.b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HintDialog(frame, "Button 1 clicked").show();;
            }
        });

        b.b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HintDialog(frame, "Button 2 clicked").show();
            }
        });
    }
}
