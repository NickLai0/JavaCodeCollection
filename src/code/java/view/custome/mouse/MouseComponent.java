package code.java.view.custome.mouse;

import code.java.utils.LU;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;

/**
 * A component with mouse operations for adding and removing
 * squares.
 */
public class MouseComponent extends JComponent {

    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 200;
    private static final int SQUARE_SIDE_LENGTH = 10;

    private ArrayList<Rectangle2D> squares;

    private Rectangle2D current; // the square containing the

    public MouseComponent() {
        squares = new ArrayList<>();
        current = null;

        addMouseListener(new MouseClickHandler());
        addMouseMotionListener(new MouseMotionHandler());
    }


    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }


    public void paintComponent(Graphics g) {
        var g2 = (Graphics2D) g;
        // draw all squares（绘制所有方块）
        for (Rectangle2D r : squares)
            g2.draw(r);
    }

    /**
     * Finds the first square containing a point.
     *
     * @param p a point
     * @return the first square that contains p
     */

    public Rectangle2D find(Point2D p) {
        for (Rectangle2D r : squares) {
            if (r.contains(p)/*p是否在r的2D point范围内*/){
                return r;
            }
        }
        return null;
    }

    /**
     * Adds a square to the collection.
     *
     * @param p the center of the square
     */

    public void add(Point2D p) {
        double x = p.getX();
        double y = p.getY();
        current = new Rectangle2D.
                Double(x - SQUARE_SIDE_LENGTH / 2, y -
                SQUARE_SIDE_LENGTH / 2,
                SQUARE_SIDE_LENGTH, SQUARE_SIDE_LENGTH);
        squares.add(current);
        repaint();
    }

    /**
     * Removes a square from the collection.
     *
     * @param s the square to remove
     */

    public void remove(Rectangle2D s) {
        if (s == null) return;
        if (s == current) current = null;
        squares.remove(s);
        repaint();
    }

    //鼠标点击事件处理者：单击MouseComponent空白处添加Square，双击Square则移出它。
    private class MouseClickHandler extends MouseAdapter {

        public void mousePressed(MouseEvent event) {
            // add a new square if the cursor isn't inside a square
            current = find(event.getPoint());
            if (current == null) {
                add(event.getPoint());
            }
        }

        public void mouseClicked(MouseEvent event) {
            // remove the current square if double clicked
            current = find(event.getPoint());
            if (current != null && event.getClickCount() >= 2) {
                remove(current);
            }
        }

    }

    //鼠标移动监控者：在空白处显示默认游标；在小方块内则显示十字架。
    private class MouseMotionHandler implements MouseMotionListener {

        //鼠标移动回调方法
        public void mouseMoved(MouseEvent event) {
            // set the mouse cursor to cross hairs if it is inside a rectangle
            if (find(event.getPoint()) == null)
                //空白处显示普通游标
                setCursor(Cursor.getDefaultCursor());
            else
                //Square内显示十字线游标
                setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        }

        //鼠标按住拖拽回调方法
        public void mouseDragged(MouseEvent event) {
            if (current != null) {
                //获取square拖拽所在x，y轴点
                int x = event.getX();
                int y = event.getY();
                /*
                 * current.setFrame方法的注释如下：
                 * Sets the location and size of the outer
                 *  bounds of this Rectangle2D to the specified
                 *  rectangular values.
                 *
                 * 按：就是将自身的位置、大小绘制到Frame。
                 */
                // drag the current rectangle to center it at (x,  y)
                current.setFrame(
                        x - SQUARE_SIDE_LENGTH / 2,
                        y - SQUARE_SIDE_LENGTH / 2,
                        SQUARE_SIDE_LENGTH,
                        SQUARE_SIDE_LENGTH
                );
                //请求Component去触发paintComponent方法来重新绘制。
                repaint();
            }
        }
    }

    public static void main(String[] args) {
        LU.println("main method run on thread: " + Thread.currentThread());

        EventQueue.invokeLater(() -> {
            LU.println("run method run on thread: " + Thread.currentThread());
            var frame = new JFrame();
            frame.add(new MouseComponent());
            //让Frame自适应子Component的宽高
            frame.pack();
            frame.setTitle("Click anywhere to add squares and double-click the square to remove it.");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
