package code.java.view.layer.layerUI;

import javax.swing.*;
import javax.swing.plaf.LayerUI;
import java.awt.*;
import java.beans.PropertyChangeEvent;

//Loading UI。
public class LoadingLayerUI extends LayerUI<JComponent> {

    private boolean isRunning;

    private Timer timer;

    // 记录转过的角度
    private int angle;      // ①

    public void paint(Graphics g, JComponent c) {
        super.paint(g, c);
        int w = c.getWidth();
        int h = c.getHeight();
        // 已经停止运行，直接返回
        if (!isRunning) {
            return;
        }
        Graphics2D g2 = (Graphics2D) g.create();
        Composite urComposite = g2.getComposite();
        g2.setComposite(AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, .5f));
        // 填充矩形
        g2.fillRect(0, 0, w, h);
        g2.setComposite(urComposite);
        // -----下面代码开始绘制转动中的“齿轮”----
        // 计算得到宽、高中较小值的1/5
        int s = Math.min(w, h) / 5;
        int cx = w / 2;
        int cy = h / 2;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING
                , RenderingHints.VALUE_ANTIALIAS_ON);
        // 设置笔触
        g2.setStroke(new BasicStroke(s / 2
                , BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setPaint(Color.BLUE);
        // 画笔绕被装饰组件的中心转过angle度
        g2.rotate(Math.PI * angle / 180, cx, cy);
        // 循环绘制12条线条，形成“齿轮”
        for (int i = 0; i < 12; i++) {
            float scale = (11.0f - (float) i) / 11.0f;
            g2.drawLine(cx + s, cy, cx + s * 2, cy);
            g2.rotate(-Math.PI / 6, cx, cy);
            g2.setComposite(AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, scale));
        }
        g2.dispose();
    }

    // 控制等待（齿轮开始转动）的方法
    public void start() {
        // 如果已经在运行中，直接返回
        if (isRunning)
            return;
        isRunning = true;
        // 每隔0.1秒重绘一次
        timer = new Timer(100, e -> {
            if (isRunning) {
                // 触发applyPropertyChange()方法，让JLayer重绘。
                // 在这行代码中，后面两个参数没有意义。
                firePropertyChange("crazyitFlag", 0, 1);
                // 角度加6
                angle += 6;      // ②
                // 到达360后再从0开始
                if (angle >= 360)
                    angle = 0;
            }
        });
        timer.start();
    }

    // 控制停止等待（齿轮停止转动）的方法
    public void stop() {
        isRunning = false;
        // 最后通知JLayer重绘一次，清除曾经绘制的图形
        firePropertyChange("crazyitFlag", 0, 1);
        timer.stop();
    }

    public void applyPropertyChange(PropertyChangeEvent pce
            , JLayer layer) {
        // 控制JLayer重绘
        if (pce.getPropertyName().equals("crazyitFlag")) {
            layer.repaint();
        }
    }
}