package code.java.thread.runnable;
/*

From Thinking in Java. Modified a little.

ColorBoxes2用一个线程管理多个CBox的绘制请求，
导致以极少的线程（如ColorBoxes1需要64个线程，这里则只需8个）
实现和ColorBoxes1一样的网格box颜色变化功能。

too many threads

At some point, you’ll find that ColorBoxes bogs down（陷入沼泽）.
On my machine, this happened somewhere
after a 10 by 10 grid. Why does this happen?

You’re naturally suspicious that the AWT might have
something to do with it, so here’s an example that
tests the premise(前提) by making fewer threads. The
code is reorganized so a Vector implements Runnable and
that Vector holds a number of color
blocks, and randomly chooses ones to update. Then a number
of these Vector objects are created,
roughly(粗暴地) depending on the grid dimension you choose.
As a result, you have far fewer threads than
color blocks, so if there’s a speedup(加速) we’ll know it was
because there were too many threads in the
previous example:

*/
//: ColorBoxes2.java
// Balancing thread use

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class CBox2 extends Canvas {
    static final Color colors[] = {
            Color.black, Color.blue, Color.cyan,
            Color.darkGray, Color.gray, Color.green,
            Color.lightGray, Color.magenta,
            Color.orange, Color.pink, Color.red,
            Color.white, Color.yellow
    };
    Color cColor = newColor();

    static final Color newColor() {
        return colors[
                (int) (Math.random() * colors.length)
                ];
    }

    void nextColor() {
        cColor = newColor();
        repaint();
    }

    public void paint(Graphics g) {
        g.setColor(cColor);
        Dimension s = getSize();
        g.fillRect(0, 0, s.width, s.height);
    }
}
/*

//此类也可设计为继承Thread，然后持有一个Vector成员变量

All of the threading has been moved into CBoxVector.
The CBoxVector could also have inherited Thread
and had a member object of type Vector.
*/
class CBoxVector extends Vector implements Runnable {

    Thread t;

    int pause;

    CBoxVector(int pause) {
        this.pause = pause;
        t = new Thread(this);
    }

    void go() {
        t.start();
    }

    public void run() {
        while (true) {
            int i = (int) (Math.random() * size());
            //用for循环实现效果没有那么好。
//            for (int i = 0; i < size(); i++) {
             /*
            That design has the advantage that the addElement( )
            and elementAt( ) methods could then be given
            specific argument and return value types
            rather than just generic Objects (their names could also be
            changed to something shorter).
            */
                ((CBox2) elementAt(i)).nextColor();
//            }

            try {
                t.sleep(pause);
            } catch (InterruptedException e) {
            }
        }
    }
}

public class ColorBoxes2 extends Frame {
    CBoxVector cBoxVectorArr[];

    public ColorBoxes2(int pause, int grid) {
        setTitle("ColorBoxes2");
        setLayout(new GridLayout(grid, grid));

        //每个CBoxVector相当于一行网格，CBoxVector则相当于网格的行和列二维。
        cBoxVectorArr = new CBoxVector[grid];
        for (int i = 0; i < grid; i++)
            cBoxVectorArr[i] = new CBoxVector(pause);
        int gridSquare = grid * grid;
        for (int i = 0; i < gridSquare; i++) {
            CBox2 cBox2 = new CBox2();
            /*
            That design has the advantage that the addElement( )
            and elementAt( ) methods could then be given
            specific argument and return value types
            rather than just generic Objects (their names could also be
            changed to something shorter).
            */
            cBoxVectorArr[i % grid].addElement(cBox2);
            //总是将最后一个位置的元素添加到网格里
//            Border lineBorder = BorderFactory.createLineBorder(Color.black, 10);
//            add(newBorderPanel(lineBorder, cBox2));
            add(cBox2);
        }
        for (int i = 0; i < grid; i++)
            cBoxVectorArr[i].go();
        addWindowListener(new WL());
    }

    private JPanel newBorderPanel(Border border, CBox2 cBox2) {
        JPanel p = new JPanel();
        //JPanel默认内容水平居中
        p.add(cBox2);
        // 为Panel设置边框
        p.setBorder(border);
        return p;
    }

    class WL extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        // Shorter default pause than ColorBoxes:
        int pause = 10;
        int grid = 8;
        if (args.length > 0)
            pause = Integer.parseInt(args[0]);
        if (args.length > 1)
            grid = Integer.parseInt(args[1]);
        Frame f = new ColorBoxes2(pause, grid);
        f.setSize(500, 400);
        f.setVisible(true);
    }
} /// :~

/*
too many threads

In ColorBoxes2 an array of CBoxVector is created
and initialized to hold grid CBoxVectors, each of
which knows how long to sleep. An equal number of
Cbox2 objects are then added to each
CBoxVector, and each vector is told to go( ),
which starts its thread.

CBox2 is very similar to CBox: it paints itself with
a randomly-chosen color. But that’s all a CBox2
does. All of the threading has been moved into CBoxVector.
The CBoxVector could also have inherited Thread
and had a member object of type Vector. That
design has the advantage that the addElement( )
and elementAt( ) methods could then be given
specific argument and return value types
rather than just generic Objects (their names could also be
changed to something shorter).
However, the design used here seemed at first glance to require less
code. In addition it automatically retains all
the other behaviors of a Vector.

 With all the casting and parentheses(圆括号) necessary for elementAt( ),
this might not be the case as your body of code grows.
As before, when you implement Runnable you don’t
get all the equipment that comes with Thread,
so you actually have to create a new Thread and
 hand yourself to it’s constructor in order to have
something to start( ), as you can see in the CBoxVector
constructor and in go( ). The run( ) method
simply chooses a random element number within the
vector and calls nextColor( ) for that element to
cause it to choose a new randomly-selected color.
Upon running this program, you see that it does
 indeed run faster and respond more quickly (for
instance, when you interrupt it, it stops more quickly),
and it doesn’t seem to bog down as much at
higher grid sizes. Thus a new factor is added into
the threading equation(方程式): you must watch to see that
you don’t have “too many threads”
(whatever that turns out to mean for your particular program and
platform). If so, you must try to use techniques
like the one above to “balance” the number of threads
in your program. Thus, if you see performance
problems in a multithreaded program you now have a
number of issues to examine:

1. Do you have enough calls to sleep( ), yield( ) and/or wait( ) ?
2. Are calls to sleep( ) long enough?
3. Are you running too many threads?

Issues like this are one reason multithreaded programming is
often considered an art.


*/