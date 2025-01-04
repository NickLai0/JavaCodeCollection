package code.java.view.text;

import code.java.utils.FrameUtils;

import javax.swing.*;
import java.awt.*;

/**
 * Demonstrate how to drag text
 * to another text program.
 *
 * Modify a lot.
 *
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
public class DragTextToAnotherTextProgram extends JFrame {

    JTextArea srcTxt = new JTextArea(8, 30);

    JTextField jtf = new JTextField(34);

    public DragTextToAnotherTextProgram() {
        setupView();
        srcTxt.append("Swing的拖放支持.\n");
        srcTxt.append("将该文本域的内容拖入其他（通常是文本）程序.\n");
        // 启动文本域和单行文本框的拖放支持
        srcTxt.setDragEnabled(true);
        jtf.setDragEnabled(true);
    }

    private void setupView() {
        add(new JScrollPane(srcTxt), BorderLayout.CENTER);
        add(jtf, BorderLayout.SOUTH);
        pack();
    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(DragTextToAnotherTextProgram.class)
                .setTitle("Swing的拖放支持");
    }

}

