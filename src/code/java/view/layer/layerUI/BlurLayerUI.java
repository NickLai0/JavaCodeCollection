package code.java.view.layer.layerUI;

import javax.swing.*;
import javax.swing.plaf.LayerUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

//高斯模糊LayerUI
public class BlurLayerUI extends LayerUI<JComponent> {

    private BufferedImage bi;

    private BufferedImageOp operation;

    public BlurLayerUI() {
        float ninth = 1.0f / 9.0f;
        // 定义模糊参数
        float[] blurKernel = {
                ninth, ninth, ninth,
                ninth, ninth, ninth,
                ninth, ninth, ninth
        };
        // ConvolveOp代表一个模糊处理，它将原图片的每一个像素与周围
        // 像素的颜色进行混合，从而计算出当前像素的颜色值，
        operation = new ConvolveOp(
                new Kernel(3, 3, blurKernel),
                ConvolveOp.EDGE_NO_OP,
                null
        );
    }

    public void paint(Graphics g, JComponent c) {
        int w = c.getWidth();
        int h = c.getHeight();
        // 如果被装饰窗口大小为0X0,直接返回
        if (w == 0 || h == 0) {
            return;
        }
        // 如果screenBlurImage没有初始化，或它的尺寸对不上。
        if (bi == null || bi.getWidth() != w || bi.getHeight() != h) {
            // 新建BufferdImage
            bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        }
        Graphics2D ig2 = bi.createGraphics();
        // 把被装饰组件的界面绘制到当前screenBlurImage上
        ig2.setClip(g.getClip());
        super.paint(ig2, c);
        ig2.dispose();
        Graphics2D g2 = (Graphics2D) g;
        // 对JLayer装饰的组件进行模糊处理
        g2.drawImage(bi, operation, 0, 0);
    }

}