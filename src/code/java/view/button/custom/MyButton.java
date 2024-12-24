package code.java.view.button.custom;

import code.java.view.applet.AutoEvent;

import java.awt.*;

import static code.java.utils.LU.println;

/*
Code and description From <Thinking in Java>。
But I modify a little for the code.

This example also shows you how to make your own button object,
because that’s what is used as the
target of all the events of interest.
You might first (naturally) assume that to make a new button, you’d
inherit from Button. But this doesn’t work. Instead,
you inherit from Canvas (a much more generic
component) and paint your button on that canvas by
overriding the paint( ) method. As you’ll see, it’s
really too bad that overriding Button doesn’t work,
since there’s a bit of code involved to paint the
button (if you don’t believe me, try exchanging Button
for Canvas in this example, and remember to
call the base-class constructor super(label). You’ll
see that the button doesn’t get painted and the
events don’t get handled).

The myButton class is very specific:
it only works with an AutoEvent “parent window” (not a base
class, but the window in which this button is created and lives).
 With this knowledge, myButton can
reach into the parent window and manipulate its text fields,
which is what’s necessary to be able to
write the status information into the fields of the parent.
Of course this is a much more limited
solution, since myButton can only be used in conjunction
with AutoEvent. This kind of code is
sometimes called “highly coupled.” However, to make
myButton more generic requires a lot more
effort which isn’t warranted for this example
 (and possibly for many of the applets that you will write).
Again, keep in mind that the following code uses APIs
that are deprecated in Java 1.1.
*/
//自定义按钮，只能继承Canvas来实现。演示实时监控事件如下：
//           "keyDown",
//            "keyUp",
//            "lostFocus",
//            "gotFocus",
//            "mouseDown",
//            "mouseUp",
//            "mouseMove",
//            "mouseDrag",
//            "mouseEnter",
//            "mouseExit"
public class MyButton extends Canvas {
    /*
    上面注释中最重要的一段如下：
     * You might first (naturally) assume that to make a new button, you’d
     * inherit from Button. But this doesn’t work. Instead,
     * you inherit from Canvas (a much more generic
     * component) and paint your button on that canvas by
     * overriding the paint( ) method.
      */

    AutoEvent parent;//Highly coupled.
    Color color;
    String label;

    public MyButton(AutoEvent parent, Color color, String label) {
        this.parent = parent;
        this.color = color;
        this.label = label;
    }

    public void paint(Graphics g) {
        Dimension size = size();
        int btnWidth = size.width;
        int btnHeight = size.height;
        //round corner.
        int round = 30;

        //set the color from outside for background.
        g.setColor(color);
        //set round corner.
        g.fillRoundRect(0, 0, btnWidth, btnHeight, round, round);

        //Modify the color to black for borders.
        g.setColor(Color.black);
        //set round corner.
        g.drawRoundRect(0, 0, btnWidth, btnHeight, round, round);

        FontMetrics fm = g.getFontMetrics();
        // measure the width of babel.
        int strWidth = fm.stringWidth(label);
        // measure the height of babel.
        int strHeight = fm.getHeight();

        //what does ascent mean here?
        int ascent = fm.getAscent();
        //what does leading mean here?
        int leading = fm.getLeading();

        println("ascent = " + ascent + ", leading = " + leading);
        //After ran the code above, I get: ascent = 13, leading = 0
        //But I don't really understand what do they mean.

        //水平居中的margin值 = 按钮控件宽度 - 字符串宽度 / 2
        int horizMargin = (btnWidth - strWidth) / 2;
        //垂直居中的margin值 = 按钮控件高度 - 字符串高度 / 2
        int verMargin = (btnHeight - strHeight) / 2;

        //Modify the color to white for font.
        g.setColor(Color.white);
        g.drawString(label, horizMargin, verMargin + ascent + leading);
    }

    public boolean keyDown(Event evt, int key) {
        TextField t = (TextField) parent.h.get("keyDown");
        t.setText(evt.toString());
        return true;
    }

    public boolean keyUp(Event evt, int key) {
        TextField t = (TextField) parent.h.get("keyUp");
        t.setText(evt.toString());
        return true;
    }

    public boolean lostFocus(Event evt, Object w) {
        TextField t = (TextField) parent.h.get("lostFocus");
        t.setText(evt.toString());
        return true;
    }

    public boolean gotFocus(Event evt, Object w) {
        TextField t = (TextField) parent.h.get("gotFocus");
        t.setText(evt.toString());
        return true;
    }

    public boolean mouseDown(Event evt, int x, int y) {
        TextField t = (TextField) parent.h.get("mouseDown");
        t.setText(evt.toString());
        return true;
    }

    public boolean mouseDrag(Event evt, int x, int y) {
        TextField t = (TextField) parent.h.get("mouseDrag");
        t.setText(evt.toString());
        return true;
    }

    public boolean mouseEnter(Event evt, int x, int y) {
        TextField t = (TextField) parent.h.get("mouseEnter");
        t.setText(evt.toString());
        return true;
    }

    public boolean mouseExit(Event evt, int x, int y) {
        TextField t = (TextField) parent.h.get("mouseExit");
        t.setText(evt.toString());
        return true;
    }

    public boolean mouseMove(Event evt, int x, int y) {
        TextField t = (TextField) parent.h.get("mouseMove");
        t.setText(evt.toString());
        return true;
    }

    public boolean mouseUp(Event evt, int x, int y) {
        TextField t = (TextField) parent.h.get("mouseUp");
        t.setText(evt.toString());
        return true;
    }

}