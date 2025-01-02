package code.java.view.container.panel;

import code.java.view.event.action.ChangeAction;

import javax.swing.*;

// 定义一个JPanel类扩展类，该类的对象包含多个纵向排列的JRadioButton控件
// 且JPanel扩展类可以指定一个字符串作为TitledBorder
public class ButtonPanel extends JPanel {
    private ButtonGroup group;

    public ButtonPanel(ChangeAction action, String title, String[] labels) {
        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), title)
        );
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        group = new ButtonGroup();
        for (int i = 0; labels != null && i < labels.length; i++) {
            JRadioButton b = new JRadioButton(labels[i]);
            b.setActionCommand(labels[i]);
            add(b);
            // 添加事件监听器
            b.addActionListener(action);
            group.add(b);
            b.setSelected(i == 0);
        }
    }

    // 定义一个方法，用于返回用户选择的选项
    public String getSelection() {
        return group.getSelection().getActionCommand();
    }
}