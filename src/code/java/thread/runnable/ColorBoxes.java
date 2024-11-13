package code.java.thread.runnable;
/*
From thinking in Java.

Demonstrate how a Thread run the run()
method from a Runnable interface.

This program also makes an interesting benchmark（基准）,
since it can show dramatic（戏剧性的） speed differences
between one JVM implementation and another.

Modified a little.

Runnable revisited

Earlier in this chapter, I suggested that you think
carefully before making an applet or main Frame as
an implementation of Runnable, because then you can
only make one of those threads in your
program. This limits your flexibility if you decide
you want to have more than one thread of that type.
Of course, if you must inherit from a class and you
want to add threading behavior to the class,

Runnable is the correct solution.
The final example in this chapter exploits(发挥)
this by making a Runnable
Canvas class that paints different colors on itself.
This application is set up to take values from the
command line to determine how big the grid(网格) of colors
is and how long to sleep( ) between color changes,
and by playing with these values you’ll discover
some interesting and possibly inexplicable（费解的）
features of threads:

*/

//: ColorBoxes.java
// Using the Runnable interface

import java.awt.*;
import java.awt.event.*;

class CBox extends Canvas implements Runnable {

    Thread t;

    int pauseMillis;
//an enumeration  of all the colors in class Color
    static final Color colorArr[] = {
            Color.black, Color.blue, Color.cyan,
            Color.darkGray, Color.gray, Color.green,
            Color.lightGray, Color.magenta,
            Color.orange, Color.pink, Color.red,
            Color.white, Color.yellow
    };

    Color color = randomColor();

    static final Color randomColor() {
        return colorArr[
                (int) (Math.random() * colorArr.length)
                ];
    }

    public void paint(Graphics g) {
        g.setColor(color);
        Dimension s = getSize();
        g.fillRect(0, 0, s.width, s.height);
    }

    CBox(int pause) {
        this.pauseMillis = pause;
        t = new Thread(this);
        t.start();
    }

    public void run() {
        while (true) {
            color = randomColor();
            //请求重新绘制，会调用到paint(Graphics g) 。
            repaint();
            try {
                t.sleep(pauseMillis);
            } catch (InterruptedException e) {
            }
        }
    }

}

public class ColorBoxes extends Frame {
    public ColorBoxes(int pause, int grid) {
        //在构造器内部创建UI。
        setTitle("ColorBoxes");
        setLayout(new GridLayout(grid, grid));
        for (int i = 0; i < grid * grid; i++)
            add(new CBox(pause));
        addWindowListener(new WL());
    }

    class WL extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        int pauseMillis = 1000;
        int grid = 8;
        if (args.length > 0)
            pauseMillis = Integer.parseInt(args[0]);
        if (args.length > 1)
            grid = Integer.parseInt(args[1]);
        Frame f = new ColorBoxes(pauseMillis, grid);
        f.setSize(500, 400);
        f.setVisible(true);
    }
} /// :~

/*
ColorBoxes is a typical application with a constructor
 that sets up the GUI. This constructor takes an
argument of int grid to set up the GridLayout so
it has grid cells in each dimension. Then it adds the
appropriate number of CBox objects to fill the grid,
passing the pause value to each one. In main( )
you can see how pause and grid have default values
that can be changed if you pass in command-line
arguments.

CBox is where all the work takes place.
This is inherited from Canvas and it implements the

Runnable interface so each Canvas can also be a Thread.
Remember that when you implement
Runnable, you don’t make a Thread object,
just a class that has a run( ) method. Thus you must
explicitly create a Thread object and hand
the Runnable object to the constructor, then call start( )
(this happens in the constructor). In CBox this thread is called t.

Note the array colors, which is an enumeration
of all the colors in class Color. This is used in
newColor( ) to produce a randomly-selected color.
The current cell color is cColor.

paint( ) is quite simple – it just sets the
color to cColor and fills the whole canvas with that color.
In run( ), you see the infinite loop that
sets the cColor to a new random color and then calls repaint( )

to show it. Then the thread goes to sleep( ) for
the amount of time specified on the command line.

Precisely(正是) because this design
is flexible and threading is tied to each Canvas element, you can
experiment by making as many as you want
(in reality, there is a restriction imposed by the number of
threads your JVM can comfortably handle).

This program also makes an interesting benchmark（基准）,
since it can show dramatic（戏剧性的） speed differences
between one JVM implementation and another.

*/