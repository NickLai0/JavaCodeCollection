package code.java.view.container.panel.color;

import code.java.utils.FrameUtils;

import javax.swing.*;
import java.awt.*;

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
public class DrawingColorToModifyText extends JFrame {

    JColorChooser chooser = new JColorChooser();

    JTextArea textArea = new JTextArea("直接将下文字的颜色\n拖入此处以改变文本（前景）颜色");

    public DrawingColorToModifyText() {
        setupView();

        // 允许颜色选择器面板的拖放功能
        chooser.setDragEnabled(true);
        //允许文本域拖放功能
        textArea.setDragEnabled(true);
        // 允许直接将一个Color对象拖入该JTextArea对象，并赋给它的foreground属性
        textArea.setTransferHandler(new TransferHandler("foreground"));
    }

    private void setupView() {
        add(new JScrollPane(textArea));
        add(chooser, BorderLayout.SOUTH);
        pack();
    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(DrawingColorToModifyText.class)
                .setTitle("测试拖拽文字颜色");
    }

}
