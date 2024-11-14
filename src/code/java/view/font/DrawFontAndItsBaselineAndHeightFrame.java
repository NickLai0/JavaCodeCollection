package code.java.view.font;

import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import javax.swing.*;

import static code.java.utils.LU.println;

/**
 * From Core Java volume 1。Modified a little.
 * <p>
 * Demonstrate how to draw font And Its
 * baseline and height frame
 *
 * @author Cay Horstmann
 * @version 1.35 2018-04-10
 */
public class DrawFontAndItsBaselineAndHeightFrame {

    public static void main(String[] args) {
        EventQueue.invokeLater(() ->//==new Runnable(){public void run(){...}}
        {
            var frame = new FontFrame();
            frame.setTitle("根据font height = ascent + descent + leading的公式，leading在这个案例中，岂不是指在字母顶部那一点点？");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

/**
 * A frame with a text message component.
 */

class FontFrame extends JFrame {
    public FontFrame() {
        add(new FontComponent());
        pack();
    }
}

/*
 A component that shows a centered message in a box.
*/

class FontComponent extends JComponent {
    private static final int DEFAULT_WIDTH = 900;
    private static final int DEFAULT_HEIGHT = 300;

    public void paintComponent(Graphics g) {
        draw1((Graphics2D) g);
//        draw2((Graphics2D) g);
    }

    private void draw1(Graphics2D g) {
        var g2 = g;
        String message = "Hello, World!";

        //设置为Serif（家族）字体
        var font = new Font("Serif", Font.BOLD, 36);
        g2.setFont(font);

        // measure the size of the message
        FontRenderContext context = g2.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(message, context);

        // set (x,y) = top left corner of text
        double x = (getWidth() - bounds.getWidth()) / 2;
        double y = (getHeight() - bounds.getHeight()) / 2;

        // add ascent to y to reach the baseline
        double ascent = -bounds.getY();
        double baseLineY = y + ascent;

        // draw the message（可见洋鬼子的字母是根据下面的baseLineY来画的，中文方块字直接基于上面的baseLine来画即可。）
        g2.drawString(message, (int) x, (int) baseLineY);
        g2.setPaint(Color.LIGHT_GRAY);

        // draw the baseline
        g2.draw(new Line2D.Double(x, baseLineY, x + bounds.getWidth(), baseLineY));

        // draw the enclosing rectangle
        //按：根据font height = ascent + descent + leading的公式，leading在这个案例中，岂不是在字母顶部那一点点？
        var rect = new Rectangle2D.Double(x, y, bounds.getWidth(), bounds.getHeight());
        g2.draw(rect);
    }

    //比draw1多画decent 和 leading 线，但效果不好。
    private void draw2(Graphics2D g) {
        var g2 = g;
        String message = "Hello, World!";

        //设置为Serif（家族）字体
        var font = new Font("Serif", Font.BOLD, 36);
        g2.setFont(font);

        // measure the size of the message
        FontRenderContext context = g2.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(message, context);

        // set (x,y) = top left corner of text
        double x = (getWidth() - bounds.getWidth()) / 2;
        double y = (getHeight() - bounds.getHeight()) / 2;

        // add ascent to y to reach the baseline
        double ascent = -bounds.getY();
        double baseLineY = y + ascent;

        // draw the message
        g2.drawString(message, (int) x, (int) baseLineY);
        g2.setPaint(Color.LIGHT_GRAY);

        // draw the baseline
        g2.draw(new Line2D.Double(x, baseLineY, x + bounds.getWidth(), baseLineY));

        LineMetrics lineMetrics = font.getLineMetrics(message, context);
        float descent = lineMetrics.getDescent();
        float leading = lineMetrics.getLeading();

        double descentLineY = baseLineY + descent;
        //打印的结果是：leading = 1.5292969， 也就1.5个像素呐！
        //println("leading = " +leading);
        double leadingLineY = baseLineY + descent + leading;

        // draw the descent line
        g2.draw(new Line2D.Double(x, descentLineY, x + bounds.getWidth(), descentLineY));

        // draw the leading line
        g2.draw(new Line2D.Double(x, leadingLineY, x + bounds.getWidth(), leadingLineY));

        // draw the enclosing rectangle
        var rect = new Rectangle2D.Double(x, y, bounds.getWidth(), bounds.getHeight());
        g2.draw(rect);
    }

    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

}