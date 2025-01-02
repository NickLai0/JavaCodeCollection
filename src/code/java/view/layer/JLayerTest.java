package code.java.view.layer;

import code.java.utils.FrameUtils;

import javax.swing.*;

/**
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

public class JLayerTest extends JFrame {

    JPanel p = new JPanel();

    ButtonGroup group = new ButtonGroup();

    public JLayerTest() {
        setSize(300, 170);
        addRadioButtons();
        addCheckBoxes();
        p.add(new JButton("投票"));
        //Creates a new JLayer object with the specified view
        //component（p） and LayerUI（SpotlightLayerUI）object.
        add(new JLayer(p, new SpotlightLayerUI()));
    }

    private void addCheckBoxes() {
        // 添加3个JCheckBox
        p.add(new JCheckBox("疯狂Java讲义"));
        p.add(new JCheckBox("疯狂Android讲义"));
        p.add(new JCheckBox("疯狂Ajax讲义"));
        p.add(new JCheckBox("轻量级Java EE企业应用"));
    }

    private void addRadioButtons() {
        JRadioButton rb;
        // 创建3个RadioButton，并将它们添加成一组
        p.add(rb = new JRadioButton("网购购买", true));
        group.add(rb);
        p.add(rb = new JRadioButton("书店购买"));
        group.add(rb);
        p.add(rb = new JRadioButton("图书馆借阅"));
        group.add(rb);
    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(JLayerTest.class).setTitle("JLayer测试");
    }

}