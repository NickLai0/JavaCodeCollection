package code.java.view.container.component.custom;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

/**
 * From Core Java volume 1. Modified a little.
 * Demonstrate how to use 2D library and
 * draw 2D graphics, such as Rectangle, Ellipse,
 * circle.
 *
 * @author Cay Horstmann
 * @version 1.34 2018-04-10
 */
public class Draw2DRectangleEllipseCircle {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            var frame = new DrawFrame();
            frame.setTitle("DrawTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

/**
 * A frame that contains a panel with drawings.
 */
class DrawFrame extends JFrame {
    public DrawFrame() {
        add(new Draw2DGraphicsComponent());
        pack();
    }
}

/**
 * A component that displays rectangles and ellipses.
 */
class Draw2DGraphicsComponent extends JComponent {

    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 400;

    public void paintComponent(Graphics g) {
        //Cast g to Graphics2D object.
        var g2 = (Graphics2D) g;

        // draw a rectangle
        double rectOffsetX = 100;//矩形x偏移点
        double rectOffsetY = 100;//矩形y偏移点
        double width = 200;//矩形宽
        double height = 150;//矩形高

        var rect = new Rectangle2D.Double(
                rectOffsetX,
                rectOffsetY,
                width,
                height
        );
        g2.draw(rect);

        // draw the enclosed ellipse（椭圆/circle）
        var ellipse = new Ellipse2D.Double();
        //设置矩形的边框（内含矩形x，y轴点和长宽信息）
        ellipse.setFrame(rect);
        //按照矩形的信息，在矩形之内绘制椭圆
        g2.draw(ellipse);

        // draw a diagonal line
        g2.draw(new Line2D.Double(
                rectOffsetX,
                rectOffsetY,
                rectOffsetX + width,
                rectOffsetY + height
        ));

        // draw a circle with the same center
        double centerX = rect.getCenterX();
        double centerY = rect.getCenterY();
        double radius = 150;
        var circle = new Ellipse2D.Double();
        circle.setFrameFromCenter(
                centerX,//x中心点，即圆形x中心点
                centerY,//y中心点，即圆形y中心点
                centerX + radius,//绘制弧度的x点
                centerY + radius//绘制弧度的y点
        );
        g2.draw(circle);
    }

    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

}
