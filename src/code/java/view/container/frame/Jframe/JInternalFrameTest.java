package code.java.view.container.frame.Jframe;

import code.java.utils.FrameUtils;
import code.java.view.container.pane.MyDesktopPane;
import code.java.view.event.action.CloseAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static code.java.utils.ImageUtils.newImageIcon;

/**
 * Demonstrates virtual desktop（虚拟桌面）.
 *
 * 云盘系统可能需要用到这个技术。
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
public class JInternalFrameTest extends JFrame {

    final int DESKTOP_WIDTH = 480;
    final int DESKTOP_HEIGHT = 360;
    final int INTERNAL_FRAME_DISTANCE = 30;

    // 定义一个虚拟桌面
    private MyDesktopPane desktop = new MyDesktopPane();

    // 保存下一个内部窗口的坐标点
    private int nextInternalFrameX;
    private int nextInternalFrameY;

    // 定义内部窗口为虚拟桌面的1/2大小
    private int width = DESKTOP_WIDTH / 2;
    private int height = DESKTOP_HEIGHT / 2;

    // 为主窗口定义两个菜单
    JMenu fileMenu = new JMenu("文件");
    JMenu windowMenu = new JMenu("窗口");

    JMenuItem nextItem = new JMenuItem("下一个");
    JMenuItem cascadeItem = new JMenuItem("级联");
    JMenuItem tileItem = new JMenuItem("平铺");

    final JCheckBoxMenuItem dragOutlineItem = new JCheckBoxMenuItem("仅显示拖动窗口的轮廓");

    private CloseAction closeAction = new CloseAction();

    public JInternalFrameTest() {
        setupMenu();
        setupToolBar();
        setupCenter();
        initListener();
    }

    private void setupCenter() {
        desktop.setPreferredSize(new Dimension(DESKTOP_WIDTH, DESKTOP_HEIGHT));
        add(desktop, BorderLayout.CENTER); // 将虚拟桌面添加到顶级JFrame容器中
        pack();
    }

    // 为窗口安装菜单条和工具条
    private void setupToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.add(newAction);
        toolBar.add(closeAction);
        add(toolBar, BorderLayout.NORTH);
    }

    private void setupMenu() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        menuBar.add(fileMenu);
        fileMenu.add(newAction);
        fileMenu.add(closeAction);
        menuBar.add(windowMenu);
        windowMenu.add(cascadeItem);
        windowMenu.add(tileItem);
        windowMenu.add(nextItem);
        windowMenu.add(dragOutlineItem);
    }

    private void initListener() {
        nextItem.addActionListener(e -> desktop.selectNextShowingWindow());
        // 级联显示窗口，内部窗口的大小是外部窗口的0.75
        cascadeItem.addActionListener(e -> desktop.cascadeWindows(INTERNAL_FRAME_DISTANCE, 0.75));
        // 平铺显示所有内部窗口
        tileItem.addActionListener(e -> desktop.tileWindows());
        dragOutlineItem.addActionListener(new DragOutlineAction());
    }

    // 新建文本action
    Action newAction = new AbstractAction("新建", newImageIcon("new.png")) {
        public void actionPerformed(ActionEvent event) {
            addAndShowInternalFrame();
            setNextInternalFrameXY();
        }
    };

    private void addAndShowInternalFrame() {
        // 创建内部窗口
        final JInternalFrame internalFrame = new JInternalFrame(
                "新文档",
                true, // 可改变大小
                true, // 可关闭
                true, // 可最大化
                true// 可最小化
        );
        //internalFrame添加JTextArea
        internalFrame.add(new JScrollPane(new JTextArea(8, 40)));
        // 将内部窗口添加到虚拟桌面中
        desktop.add(internalFrame);
        // 设置内部窗口的原始位置（内部窗口默认大小是0X0，放在0,0位置）
        internalFrame.reshape(nextInternalFrameX, nextInternalFrameY, width, height);
        // 使该窗口可见，并尝试选中它
        internalFrame.show();
    }

    private void setNextInternalFrameXY() {
        // 计算下一个内部窗口的位置
        nextInternalFrameX += INTERNAL_FRAME_DISTANCE;
        nextInternalFrameY += INTERNAL_FRAME_DISTANCE;
        if (nextInternalFrameX + width > desktop.getWidth()) {
            nextInternalFrameX = 0;
        }
        if (nextInternalFrameY + height > desktop.getHeight()) {
            nextInternalFrameY = 0;
        }
    }

    class DragOutlineAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 根据该菜单项是否选择来决定采用哪种拖动模式
            desktop.setDragMode(
                    dragOutlineItem.isSelected() ?
                            JDesktopPane.OUTLINE_DRAG_MODE //仅拖动窗口轮廓
                            : JDesktopPane.LIVE_DRAG_MODE//活动拖拽
            );
        }
    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(JInternalFrameTest.class);
    }

}

