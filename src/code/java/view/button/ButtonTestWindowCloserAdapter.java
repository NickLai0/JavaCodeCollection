package code.java.view.button;

//: Button2NewB.java
// An application and an applet

import code.java.adapter.WindowCloserAdapter;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonTestWindowCloserAdapter extends Applet {

    Button b1, b2;

    TextField t = new TextField(20);

    public void init() {
        b1 = new Button("Button 1");
        b2 = new Button("Button 2");
        b1.addActionListener(new B1());
        b2.addActionListener(new B2());
        add(b1);
        add(b2);
        add(t);
    }

    class B1 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            t.setText("Button 1");
        }
    }

    class B2 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            t.setText("Button 2");
        }
    }

    // A main() for the application:
    public static void main(String args[]) {
        ButtonTestWindowCloserAdapter applet = new ButtonTestWindowCloserAdapter();
        Frame aFrame = new Frame("Button2NewB");
        aFrame.addWindowListener(new WindowCloserAdapter());
        aFrame.add(applet, BorderLayout.CENTER);
        aFrame.setSize(300, 200);
        applet.init();
        applet.start();
        aFrame.setVisible(true);
    }

}

/*
 * The inner class WL and the main( ) are the only
 * two elements added to the applet, and the rest of the
 * applet is untouched. In fact, you can generally copy
 * and paste the WL class and main( ) into your own
 * applets with very little modification.
 * The WL class is static so it can be easily created in main( )
 *
 * (remember that an inner class normally needs
 * an outer class handle when it’s created. Making it static
 * eliminates this need). You can see that in main( ),
 * the applet is explicitly initialized and started since in
 * this case the browser isn’t available to do it for you.
 * Of course, this doesn't provide the full behavior of
 * the browser, which also calls stop( )
 * and destroy( ), but for most situations it's acceptable. If it’s a
 * problem, you can:
 * 1. Make the handle applet a static member of the class
 *  (rather than a local variable of main( )).
 * 2. Call applet.stop( ) and applet.destroy( ) inside
 * WindowAdapter.windowClosing( ) before you
 * call System.exit( ).
 * Notice the last line:
 *
 * aFrame.setVisible(true);
 *
 * This is one of the changes in the Java 1.1 AWT:
 *  the show( ) method is deprecated, and
 * setVisible(true) replaces it. These sorts of
 * seemingly capricious changes will make more sense when
 * you learn about JavaBeans later in the chapter.
 * This example is also modified to use a TextField
 * rather than printing to the console or to the browser
 * status line. One restriction to making a program
 * that’s both an applet and an application is that you
 * must choose input and output forms that work for
 * both situations.
 *
 * There’s another small new feature of the
 * Java 1.1 AWT shown here. You no longer need to use the
 * error-prone(容易出错) approach of specifying BorderLayout
 * positions using a String. When adding an element to
 * a BorderLayout in Java 1.1, you can say:
 *
 * aFrame.add(applet, BorderLayout.CENTER);
 *
 * You name the location with one of the BorderLayout constants,
 * which can then be checked at
 * compile-time (rather than just quietly doing the wrong thing,
 * as with the old form). This is a definite
 * improvement, and shall be used throughout the rest of the book.
 */