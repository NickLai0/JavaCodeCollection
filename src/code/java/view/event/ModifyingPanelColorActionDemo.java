package code.java.view.event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


/**
 * 演示自定义Action，用以响应按钮Click事件和保持数据，
 * 所保存数据在回调时可取出使用。
 * <p>
 * 奇怪的是，用自定义action来保存JButton的name后，
 * 运行程序，JButton上的文字显示缩略文字。
 */

class ModifyingPanelColorActionDemo extends JFrame {

    private JPanel buttonPanel;

    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;

    public ModifyingPanelColorActionDemo() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        buttonPanel = new JPanel();

        var yellowAction = new ModifyingPanelColorAction(
                "Yellow",
                //本地没有这个gif
                new ImageIcon("blueball.gif"),
                Color.YELLOW);
        // add panel to frame
        buttonPanel.add(new JButton(yellowAction));

        var blueAction = new ModifyingPanelColorAction(
                "Blue",
                //本地没有这个gif
                new ImageIcon("blueball.gif"),
                Color.BLUE);
        // add panel to frame
        buttonPanel.add(new JButton(blueAction));

        add(buttonPanel);
    }

    //自定义修改panel背景色Action
    class ModifyingPanelColorAction extends AbstractAction {

        public ModifyingPanelColorAction(String name, Icon icon, Color c) {
            //按钮文字
            putValue(Action.NAME, name);
            //按钮图标？？？
            putValue(Action.SMALL_ICON, icon);
            //自定义的key，值为Color对象。
            putValue("color", c);
            //用手表移到按钮悬停时显示的文字。
            putValue(Action.SHORT_DESCRIPTION, "Set panel color to " + name.toLowerCase());
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Color c = (Color) getValue("color");
            buttonPanel.setBackground(c);
        }
    }

    public static void main(String[] args) {
        var frame = new ModifyingPanelColorActionDemo();
        frame.setVisible(true);
    }

}

