package code.java.view.toolbar;

import code.java.utils.FrameUtils;

import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
import javax.swing.*;

import static code.java.utils.ImageUtils.nii;

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
public class JToolBarTest extends JFrame {

    JTextArea jta = new JTextArea(6, 35);
    //ToolBar最大特色就是显示icon，可触发action，但是可能会导致误解。
    JToolBar jtb = new JToolBar();
    JMenuBar jmb = new JMenuBar();
    JMenu edit = new JMenu("编辑");

    // 创建"粘贴"Action，该Action用于创建菜单项、工具按钮和普通按钮
    Action pasteAction = new AbstractAction("粘贴", nii("paste.png")) {
        public void actionPerformed(ActionEvent e) {
            // 如果剪贴板中包含stringFlavor内容
            if (clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
                try {
                    // 取出剪贴板中stringFlavor内容
                    String content = (String) clipboard.getData(DataFlavor.stringFlavor);
                    // 将选中内容替换成剪贴板中的内容
                    jta.replaceRange(content, jta.getSelectionStart(), jta.getSelectionEnd());
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
        }
    };
    // 创建"复制"Action
    Action copyAction = new AbstractAction("复制", nii("copy.png")) {
        public void actionPerformed(ActionEvent e) {
            StringSelection contents = new StringSelection(jta.getSelectedText());
            // 将StringSelection对象放入剪贴板
            clipboard.setContents(contents, null);
            // 如果剪贴板中包含stringFlavor内容
            if (clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
                // 将pasteAction激活
                pasteAction.setEnabled(true);
            }
        }
    };

    JButton copyBn = new JButton(copyAction);
    JButton pasteBn = new JButton(pasteAction);

    // 获取系统剪贴板
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

    public JToolBarTest() {
        setupMenuBar();
        setupToolBar();
        setupCenter();
        setupSouth();
        pack();
        // pasteAction默认处于不激活状态
        pasteAction.setEnabled(false);   // ①
        setupListener();
    }

    private void setupListener() {

    }

    private void setupSouth() {
        // 以Action创建按钮，并将该按钮添加到Panel中
        JPanel jp = new JPanel();
        jp.add(copyBn);
        jp.add(pasteBn);
        add(jp, BorderLayout.SOUTH);
    }

    private void setupCenter() {
        add(new JScrollPane(jta), BorderLayout.CENTER);
    }

    private void setupToolBar() {
        // 向工具条中添加Action对象，该对象将会转换成工具按钮
        jtb.add(copyAction);
        jtb.addSeparator();
        jtb.add(pasteAction);
        // 设置工具条和工具按钮之间的页边距。
        jtb.setMargin(new Insets(20, 10, 5, 30));    // ②
        // 向窗口中添加工具条
        add(jtb, BorderLayout.NORTH);
    }

    private void setupMenuBar() {
        // 向菜单中添加Action对象，该对象将会转换成菜单项
        edit.add(copyAction);
        edit.add(pasteAction);
        // 将edit菜单添加到菜单条中
        jmb.add(edit);
        setJMenuBar(jmb);
    }


    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(JToolBarTest.class)
                .setTitle("测试工具条");
    }

}
