package code.java.view.event.action;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 定义改变标签页的布局策略，放置位置的监听器
public class ChangeAction implements ActionListener {

    private JTabbedPane tabbedPane;
    private String[] layouts;
    private String[] positions;

    public ChangeAction(JTabbedPane tabbedPane, String[] layouts, String[] positions) {
        this.tabbedPane = tabbedPane;
        this.layouts = layouts;
        this.positions = positions;
    }

    public void actionPerformed(ActionEvent event) {
        JRadioButton source = (JRadioButton) event.getSource();
        String selection = source.getActionCommand();
        // 设置标签页的标题的布局策略
        if (selection.equals(layouts[0])) {
            tabbedPane.setTabLayoutPolicy(
                    JTabbedPane.WRAP_TAB_LAYOUT);
        } else if (selection.equals(layouts[1])) {
            tabbedPane.setTabLayoutPolicy(
                    JTabbedPane.SCROLL_TAB_LAYOUT);
        }
        // 设置标签页上的标题的放置位置
        else if (selection.equals(positions[0])) {
            tabbedPane.setTabPlacement(JTabbedPane.LEFT);
        } else if (selection.equals(positions[1])) {
            tabbedPane.setTabPlacement(JTabbedPane.TOP);
        } else if (selection.equals(positions[2])) {
            tabbedPane.setTabPlacement(JTabbedPane.RIGHT);
        } else if (selection.equals(positions[3])) {
            tabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
        }
    }
}
