package code.java.thread.runnable;
/*
 * 本案例根据ColorBoxes2升级而来，
 * 用单线程循环请求随机刷新某个Cbox
 * 的颜色，当pause为3时，效果和
 * ColorBoxes2的pause为10时差不多，
 * 肉眼还难以分辨。可流畅度却没有
 * ColorBoxes2那么流畅，这也正常。
 */
//: ColorBoxes3.java
// Balancing thread use

import code.java.utils.ThreadUtils;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

class CBox3 extends Canvas {
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
class CBoxVector2 extends Vector {

    public void modifyAllColors(int pause) {
        ((CBox3) elementAt((int) (Math.random() * size())))
                .nextColor();
        ThreadUtils.sleep(pause);

       /*
        //用for循环实现效果没有那么好。
       for (int i = 0; i < size(); i++) {
            ((CBox3) elementAt(i)).nextColor();
                    ThreadUtils.sleep(pause);
        }*/
    }

}

public class ColorBoxes3 extends Frame {
    CBoxVector2 cBoxVectorArr[];
    int pause;

    public ColorBoxes3(int pause, int grid) {
        this.pause = pause;
        setTitle("ColorBoxes3");
        setLayout(new GridLayout(grid, grid));

        //每个CBoxVector相当于一行网格，CBoxVector则相当于网格的行和列二维。
        cBoxVectorArr = new CBoxVector2[grid];
        for (int i = 0; i < grid; i++)
            cBoxVectorArr[i] = new CBoxVector2();
        int gridSquare = grid * grid;
        for (int i = 0; i < gridSquare; i++) {
            CBox3 cBox3 = new CBox3();
            /*
            That design has the advantage that the addElement( )
            and elementAt( ) methods could then be given
            specific argument and return value types
            rather than just generic Objects (their names could also be
            changed to something shorter).
            */
            cBoxVectorArr[i % grid].addElement(cBox3);
            add(cBox3);
        }

        addWindowListener(new WL());
        new Thread(new LoopingModifyGridColorsRunnable()) {
        }.start();
    }

    //轮训更改网格格子颜色Runnable
    class LoopingModifyGridColorsRunnable implements Runnable {
        @Override
        public void run() {
            while (true) {
                for (int i = 0; i < cBoxVectorArr.length; i++) {
                    CBoxVector2 cBoxVector2 = cBoxVectorArr[i];
                    cBoxVector2.modifyAllColors(pause);
                }
            }
        }
    }

    class WL extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        int pause = 3;
        int grid = 8;
        if (args.length > 0)
            pause = Integer.parseInt(args[0]);
        if (args.length > 1)
            grid = Integer.parseInt(args[1]);
        Frame f = new ColorBoxes3(pause, grid);
        f.setSize(500, 400);
        f.setVisible(true);
    }
} /// :~
