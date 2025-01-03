package code.java.view.progress;

import code.java.utils.FrameUtils;
import code.java.utils.ThreadUtils;
import code.java.utils.TimerUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Modify a lot.
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
public class JProgressBarTest extends JFrame {
    // 创建一条垂直进度条
    JProgressBar pb = new JProgressBar(JProgressBar.VERTICAL);

    JCheckBox indeterminate = new JCheckBox("不确定进度");

    JCheckBox noBorder = new JCheckBox("不绘制边框");

    public JProgressBarTest() {
        setupView();
        setupListener();
        progressing();
    }

    private void setupListener() {
        // 根据该选择框决定是否绘制进度条的边框
        noBorder.addActionListener(e -> pb.setBorderPainted(!noBorder.isSelected()));
        indeterminate.addActionListener(e -> {
            // 设置该进度条的进度是否确定
            pb.setIndeterminate(indeterminate.isSelected());
            pb.setStringPainted(!indeterminate.isSelected());
        });
    }

    private void setupView() {
        setLayout(new FlowLayout());

        Box verticalBox = new Box(BoxLayout.Y_AXIS);
        verticalBox.add(indeterminate);
        verticalBox.add(noBorder);

        add(verticalBox);
        add(pb);// 把进度条添加到JFrame窗口中

        // 设置进度条的最大值和最小值
        pb.setMinimum(0);
        pb.setMaximum(100);
        // 设置在进度条中绘制完成百分比
        pb.setStringPainted(true);

        pack();
    }

    Timer timer;

    private void progressing() {
        timer = TimerUtils.start(new ProgressingAction(), 500);
        timer.setRepeats(true);
    }

    class ProgressingAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (pb.getValue() < pb.getMaximum()) {
                pb.setValue(pb.getValue() + 1);
            }else {
                timer.stop();
            }
        }
    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(JProgressBarTest.class).setTitle("测试进度条");
    }

}

