package code.java.view.canvas;

import code.java.utils.FrameUtils;

import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Modify a lot.
 *
 * Demonstrates how use draw canvas.
 *
 * <p>
 * <p>
 * Description:
 * 网站: <a href="http://www.crazyit.org">疯狂Java联盟</a><br>
 * Copyright (C), 2001-2018, Yeeku.H.Lee<br>
 * This program is protected by copyright laws.<br>
 * Program Name:<br>
 * Date:<br>
 *
 * @author Yeeku.H.Lee kongyeeku@163.com
 * @version 1.0
 */
public class SimpleDrawCanvas extends JFrame {

    private final int AREA_WIDTH = 700;// 画图区的宽度
    private final int AREA_HEIGHT = 600;// 画图区的高度

    // 下面的preX、preY保存了上一次鼠标拖动事件的鼠标坐标
    private int preX = -1;
    private int preY = -1;

    // 定义一个右键菜单用于设置画笔颜色
    JPopupMenu pop = new JPopupMenu();

    JMenuItem menuItem = new JMenuItem("选择颜色");

    // 定义一个BufferedImage对象
    BufferedImage image = new BufferedImage(
            AREA_WIDTH
            , AREA_HEIGHT
            , BufferedImage.TYPE_INT_RGB
    );

    // 获取image对象的Graphics
    Graphics g = image.getGraphics();

    private DrawCanvas canvas = new DrawCanvas();

    // 画笔颜色（默认红色）
    private Color paintColor = new Color(255, 0, 0);

    public SimpleDrawCanvas() {
        setupView();
        setupListener();
    }

    private void setupView() {
        // 将菜单项组合成右键菜单
        pop.add(menuItem);
        // 将右键菜单添加到drawArea对象中
        canvas.setComponentPopupMenu(pop);

        // 将image对象的背景色填充成白色
        g.fillRect(0, 0, AREA_WIDTH, AREA_HEIGHT);
        canvas.setPreferredSize(new Dimension(AREA_WIDTH, AREA_HEIGHT));
        add(canvas);

        pack();
    }

    private void setupListener() {
        menuItem.addActionListener(new ChooseColorAction());
        canvas.addMouseMotionListener(new MouseDrawingCanvasHandler());
        canvas.addMouseListener(new MouseReleasedHandler());
    }

    class MouseReleasedHandler extends MouseAdapter {
        // 实现鼠标松开的事件处理器
        public void mouseReleased(MouseEvent e) {
            // 松开鼠标键时，把上一次鼠标拖动事件的X、Y坐标设为-1。
            preX = -1;
            preY = -1;
        }
    }

    class MouseDrawingCanvasHandler extends MouseMotionAdapter {
        // 实现按下鼠标键并拖动的事件处理器
        public void mouseDragged(MouseEvent e) {
            // 如果preX和preY大于0
            if (preX > 0 && preY > 0) {
                // 设置当前颜色
                g.setColor(paintColor);
                // 绘制从上一次鼠标拖动事件点到本次鼠标拖动事件点的线段
                g.drawLine(preX, preY, e.getX(), e.getY());
            }
            // 将当前鼠标事件点的X、Y坐标保存起来
            preX = e.getX();
            preY = e.getY();
            // 重绘drawArea对象
            canvas.repaint();
        }
    }

    class ChooseColorAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 下面代码直接弹出一个模式的颜色选择对话框，并返回用户选择的颜色
            // foreColor = JColorChooser.showDialog(f
            // 	, "选择画笔颜色" , foreColor);   // ①
            // 下面代码则弹出一个非模式的颜色选择对话框，
            // 并可以分别为“确定”按钮、“取消”按钮指定事件监听器
            JColorChooser colorPane = new JColorChooser(paintColor);
            JDialog jd = JColorChooser.createDialog(
                    SimpleDrawCanvas.this,
                    "选择画笔颜色"
                    , false,
                    colorPane, e2 -> paintColor = colorPane.getColor(),
                    null
            );
            jd.setVisible(true);
        }
    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(SimpleDrawCanvas.class)
                .setTitle(("简单手绘程序(空白处单击右键可选择画笔颜色)"));
    }

    // 让画图区域继承JPanel类
    class DrawCanvas extends JPanel {
        // 重写JPanel的paint方法，实现绘画
        public void paint(Graphics g) {
            // 将image绘制到该组件上
            g.drawImage(image, 0, 0, null);
        }
    }
}