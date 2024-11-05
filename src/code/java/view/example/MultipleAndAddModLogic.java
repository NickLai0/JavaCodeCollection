package code.java.view.example;
/*
from Thinking in Java

separating business logic from UI logic

In general you’ll want to design your classes so that each one does “only one thing.” This is particularly
important where user-interface code is concerned, since it’s very easy to wrap up “what you’re doing”
with “how you’re displaying it.” This kind of coupling prevents code reuse. It’s much more desirable to
separate your “business logic” from the GUI. This way, you can not only reuse the business logic more
easily, it’s also easier to reuse the GUI.
Another issue is multi-tiered systems, where the “business objects” reside on a completely separate
machine. This central location of the business rules allows changes to be instantly effective for all new
transactions, and is thus a very compelling way to set up a system. However, these business objects
may be used in many different applications and thus should not be tied to any particular mode of
display. They should just perform the business operations, nothing more.

The following example shows how easy it is to separate the business logic from the GUI code:

*/
//: Separation.java
// Separating GUI logic and business objects

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
//Comes from Thinking in Java，I modified a bit。
//测试设一个值、一个模，用两种计算方式：一、值*模；二值+模，结果显示出来。
public class MultipleAndAddModLogic {
    private int modifier;

    MultipleAndAddModLogic(int mod) {
        modifier = mod;
    }

    public void setModifier(int mod) {
        modifier = mod;
    }

    public int getModifier() {
        return modifier;
    }

    // Some business operations:
    public int calculation1(int arg) {
        return arg * modifier;
    }

    public int calculation2(int arg) {
        return arg + modifier;
    }
}

 class Separation extends Applet {
    TextField
            t = new TextField(20),
            mod = new TextField(20);
     MultipleAndAddModLogic bl = new MultipleAndAddModLogic(2);
    Button
            calc1 = new Button("Calculation 1"),
            calc2 = new Button("Calculation 2");

    public void init() {
        add(t);
        calc1.addActionListener(new Calc1L());
        calc2.addActionListener(new Calc2L());
        add(calc1);
        add(calc2);
        mod.addTextListener(new ModL());
        add(new Label("Modifier:"));
        add(mod);
    }

    static int getValue(TextField tf) {
        try {
            return Integer.parseInt(tf.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    class Calc1L implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            t.setText(Integer.toString(
                    bl.calculation1(getValue(t))));
        }
    }

    class Calc2L implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            t.setText(Integer.toString(
                    bl.calculation2(getValue(t))));
        }
    }

    class ModL implements TextListener {
        public void textValueChanged(TextEvent e) {
            bl.setModifier(getValue(mod));
        }
    }

    static class WL extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }

    public static void main(String args[]) {
        Separation applet = new Separation();
        Frame aFrame = new Frame("Separation");
        aFrame.addWindowListener(new WL());
        aFrame.add(applet, BorderLayout.CENTER);
        aFrame.setSize(200, 200);
        applet.init();
        applet.start();
        aFrame.setVisible(true);
    }
} /*

You can see that BusinessLogic is a very straightforward class that performs its operations without
        even a hint that it may be used in a GUI environment. It just does its job.

Separation keeps track of all the UI details, and it only talks to BusinessLogic through its public

interface. All the operations are centered around getting information back and forth through the UI and
the BusinessLogic object. So Separation, in turn, just does its job. Since it’s only talking to a

BusinessLogic object and doesn’t know much else about it, this could be massaged into talking to
other types of objects without much trouble.
Thinking in terms of separating UI from business logic also makes life easier when you’re adapting
legacy code to work with Java.
*/