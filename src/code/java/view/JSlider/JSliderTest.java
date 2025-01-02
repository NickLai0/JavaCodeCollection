package code.java.view.JSlider;

import code.java.utils.FrameUtils;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

import java.util.Dictionary;
import java.util.Hashtable;

import static code.java.utils.ImageUtils.nii;

/**
 * Modify a lot.
 *
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
public class JSliderTest extends JFrame {

    Box sliderBox = new Box(BoxLayout.Y_AXIS);
    
    JTextField textField = new JTextField();

    public JSliderTest() {
        setupSliders();
        add(sliderBox, BorderLayout.CENTER);
        add(textField, BorderLayout.SOUTH);
        pack();
    }

    private void setupSliders() {
        // -----------添加一个普通滑动条-----------
        addSlider(new JSlider(), "普通滑动条");

        // -----------添加保留区为30的滑动条-----------
        JSlider  slider = new JSlider();
        slider.setExtent(30);
        addSlider(slider, "保留区为30");

        // ---添加带主、次刻度的滑动条,并设置其最大值，最小值---
        slider = new JSlider(30, 200);

        // 设置绘制刻度
        slider.setPaintTicks(true);
        // 设置主、次刻度的间距
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        addSlider(slider, "有刻度");

        // -----------添加滑块必须停在刻度处滑动条-----------
        slider = new JSlider();
        // 设置滑块必须停在刻度处
        slider.setSnapToTicks(true);
        // 设置绘制刻度
        slider.setPaintTicks(true);
        // 设置主、次刻度的间距
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        addSlider(slider, "滑块停在刻度处");

        // -----------添加没有滑轨的滑动条-----------
        slider = new JSlider();
        // 设置绘制刻度
        slider.setPaintTicks(true);
        // 设置主、次刻度的间距
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        // 设置不绘制滑轨
        slider.setPaintTrack(false);
        addSlider(slider, "无滑轨");

        // -----------添加方向反转的滑动条-----------
        slider = new JSlider();
        // 设置绘制刻度
        slider.setPaintTicks(true);
        // 设置主、次刻度的间距
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        // 设置方向反转
        slider.setInverted(true);
        addSlider(slider, "方向反转");

        // --------添加绘制默认刻度标签的滑动条--------
        slider = new JSlider();
        // 设置绘制刻度
        slider.setPaintTicks(true);
        // 设置主、次刻度的间距
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        // 设置绘制刻度标签，默认绘制数值刻度标签
        slider.setPaintLabels(true);
        addSlider(slider, "数值刻度标签");

        // ------添加绘制Label类型的刻度标签的滑动条------
        slider = new JSlider();
        // 设置绘制刻度
        slider.setPaintTicks(true);
        // 设置主、次刻度的间距
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);

        // 设置绘制刻度标签
        slider.setPaintLabels(true);
        Dictionary<Integer, Component> dic = new Hashtable<>();
        dic.put(0, new JLabel("A"));
        dic.put(20, new JLabel("B"));
        dic.put(40, new JLabel("C"));
        dic.put(60, new JLabel("D"));
        dic.put(80, new JLabel("E"));
        dic.put(100, new JLabel("F"));
        // 指定刻度标签，标签是JLabel
        slider.setLabelTable(dic);
        addSlider(slider, "JLable标签");

        // ------添加绘制Label类型的刻度标签的滑动条------
        slider = new JSlider();
        // 设置绘制刻度
        slider.setPaintTicks(true);
        // 设置主、次刻度的间距
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        // 设置绘制刻度标签
        slider.setPaintLabels(true);
        dic = new Hashtable<Integer, Component>();
        dic.put(0, new JLabel(nii("0.gif")));
        dic.put(20, new JLabel(nii("2.GIF")));
        dic.put(40, new JLabel(nii("4.GIF")));
        dic.put(60, new JLabel(nii("6.gif")));
        dic.put(80, new JLabel(nii("8.gif")));
        // 指定刻度标签，标签是ImageIcon
        slider.setLabelTable(dic);
        addSlider(slider, "Icon标签");
    }

    // 定义一个方法，用于将滑动条添加到容器中
    public void addSlider(JSlider slider, String desc) {
        slider.addChangeListener(l);
        Box box = new Box(BoxLayout.X_AXIS);
        box.add(new JLabel(desc + "："));
        box.add(slider);
        sliderBox.add(box);
    }

    // 定义一个监听器，用于监听所有滑动条
    ChangeListener l = event -> {
        // 取出滑动条的值，并在文本中显示出来
        JSlider source = (JSlider) event.getSource();
        textField.setText("当前滑动条的值为：" + source.getValue());
    };

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(JSliderTest.class)
                .setTitle("滑动条示范");
    }

}

