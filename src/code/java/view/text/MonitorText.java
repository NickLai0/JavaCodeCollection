package code.java.view.text;

import code.java.utils.FrameUtils;
import code.java.view.text.undo.UndoHandler;
import code.java.view.text.undo.UndoMonitor;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.undo.UndoableEdit;
import java.util.LinkedList;

import static code.java.utils.LU.println;

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
public class MonitorText extends JFrame {

    JLabel label = new JLabel("文本域的修改信息");
    JTextArea taTarget = new JTextArea(4, 35);
    JTextArea taMsg = new JTextArea(5, 35);

    Document doc = taTarget.getDocument();

    UndoMonitor mUndoMonitor = new UndoMonitor(50);

    public MonitorText() {
        setupCenter();
        setupListener();
    }

    private void setupCenter() {
        taMsg.setEditable(false);
        Box verticalBox = new Box(BoxLayout.Y_AXIS);
        verticalBox.add(new JScrollPane(taTarget));
        JPanel panel = new JPanel();
        panel.add(label);
        verticalBox.add(panel);
        verticalBox.add(new JScrollPane(taMsg));
        add(verticalBox);
        pack();
    }

    private void setupListener() {
        doc.addDocumentListener(new DocumentMonitor()); // 添加DocumentListener
        doc.addUndoableEditListener(mUndoMonitor); // 添加可撤销操作的监听器
        taTarget.addKeyListener(new UndoHandler(mUndoMonitor.getUndoList()));
    }

    class DocumentMonitor implements DocumentListener {
        // 当Document的属性或属性集发生了改变时触发该方法
        public void changedUpdate(DocumentEvent e) {
        }

        // 当向Document中插入文本时触发该方法
        public void insertUpdate(DocumentEvent e) {
            int offset = e.getOffset();
            int len = e.getLength();
            // 取得插入事件的位置
            taMsg.append("插入文本的长度：" + len + "\n");
            taMsg.append("插入文本的起始位置：" + offset + "\n");
            try {
                taMsg.append("插入文本内容：" + doc.getText(offset, len) + "\n");
            } catch (BadLocationException evt) {
                evt.printStackTrace();
            }
        }

        // 当从Document中删除文本时触发该方法
        public void removeUpdate(DocumentEvent e) {
            int offset = e.getOffset();
            int len = e.getLength();
            // 取得插入事件的位置
            taMsg.append("删除文本的长度：" + len + "\n");
            taMsg.append("删除文本的起始位置：" + offset + "\n");
        }

    }


    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(MonitorText.class)
                .setTitle("监听Document对象");
    }
}

