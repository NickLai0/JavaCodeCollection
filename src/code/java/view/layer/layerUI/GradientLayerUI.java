package code.java.view.layer.layerUI;

import javax.swing.*;
import javax.swing.plaf.LayerUI;
import java.awt.*;
//渐变层
public class GradientLayerUI extends LayerUI<JComponent> {
    public void paint(Graphics g, JComponent c) {
        super.paint(g, c);
        Graphics2D g2 = (Graphics2D) g.create();
        // 设置透明效果
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .5f));
        // 使用渐变画笔绘图
        g2.setPaint(new GradientPaint(0, 0, Color.RED, 0, c.getHeight(), Color.BLUE));
        // 绘制一个与被装饰组件相同大小的矩形
        g2.fillRect(0, 0, c.getWidth(), c.getHeight());    // ①
        g2.dispose();
    }
}
