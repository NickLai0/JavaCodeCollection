package code.java.view.layer;

import javax.swing.*;
import javax.swing.plaf.LayerUI;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

//聚光灯层叠UI
public class SpotlightLayerUI extends LayerUI<JComponent> {

    private boolean active;

    //Current x and y coordinates.
    private int cx, cy;

    public void installUI(JComponent c) {
        super.installUI(c);
        JLayer l = (JLayer) c;
        // 设置JLayer可以响应鼠标、鼠标动作事件
        l.setLayerEventMask(
                AWTEvent.MOUSE_EVENT_MASK
                        | AWTEvent.MOUSE_MOTION_EVENT_MASK
        );
    }

    public void uninstallUI(JComponent c) {
        JLayer l = (JLayer) c;
        // 设置JLayer不响应任何事件
        l.setLayerEventMask(0);
        super.uninstallUI(c);
    }

    public void paint(Graphics g, JComponent c) {
        Graphics2D g2 = (Graphics2D) g.create();
        super.paint(g2, c);
        // 如果处于激活状态
        if (active) {
            //聚光灯cx、cy位置的点
            Point2D center = new Point2D.Float(cx, cy);
            float radius = 18;
            float[] fractions = {0.0f, 1.0f};
            //渐变色数组
            Color[] colors = {Color.YELLOW, Color.BLACK};
            RadialGradientPaint p = new RadialGradientPaint(
                    center,//聚光灯中心点
                    radius,//聚光灯半径
                    fractions,//渐变分数
                    colors//渐变颜色数组
            );
            g2.setPaint(p);
            // 设置渐变效果（去掉这行代码则层叠视图黑掉了）
            g2.setComposite(
                    AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .6f)
            );
            // 绘制矩形
            g2.fillRect(0, 0, c.getWidth(), c.getHeight());
        }
        g2.dispose();
    }

    // 处理鼠标进入和退出窗口事件
    public void processMouseEvent(MouseEvent e, JLayer layer) {
        if (e.getID() == MouseEvent.MOUSE_ENTERED)
            active = true;
        if (e.getID() == MouseEvent.MOUSE_EXITED)
            active = false;
        layer.repaint();
    }

    // 处理鼠标动作事件的方法
    public void processMouseMotionEvent(MouseEvent e, JLayer layer) {
        Point p = SwingUtilities.convertPoint(
                e.getComponent(),
                e.getPoint(),
                layer
        );
        // 获取鼠标动作事件的发生点的坐标
        cx = p.x;
        cy = p.y;
        layer.repaint();
    }

}