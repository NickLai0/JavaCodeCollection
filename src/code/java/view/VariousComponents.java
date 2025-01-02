package code.java.view;

import code.java.utils.FrameUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;

import static code.java.utils.ImageUtils.nii;
import static code.java.utils.LU.println;

/**
 演示：
 JradioButton、
 ButtonGroup、
 JCheckBox 、
 JComboBox、
 JList、
 JTextArea、
 JTextField、
 JMenuBar、
 JMenu、
 JMenuItem、
 JCheckBoxMenuItem、
 JPopupMenu、
 LookAndFeel切换
 */
public class VariousComponents extends JFrame {

    // 定义一个单选按钮，初始处于选中状态
    JRadioButton maleRadio = new JRadioButton("男", true);
    // 定义一个单选按钮，初始处于没有选中状态
    JRadioButton femaleRadio = new JRadioButton("女", false);
    // 定义一个ButtonGroup，用于将上面两个JRadioButton组合在一起
    ButtonGroup bg = new ButtonGroup();
    // 定义一个复选框，初始处于没有选中状态。
    JCheckBox marriedCheckBox = new JCheckBox("是否已婚？", false);

    String[] colors = new String[]{"红色", "绿色", "蓝色"};
    // 定义一个下拉选择框
    JComboBox<String> colorComboBox = new JComboBox<>(colors);
    // 定义一个列表选择框
    JList<String> colorList = new JList<>(colors);

    // 定义一个8行、20列的多行文本域
    JTextArea jTextArea = new JTextArea(8, 20);
    // 定义一个40列的单行文本域
    JTextField name = new JTextField(40);

    JMenuBar jMenuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("文件");
    JMenu editMenu = new JMenu("编辑");

    // 创建“新建”菜单项，并为之指定图标
    JMenuItem newItem = new JMenuItem("新建", nii("new.png"));
    // 创建“保存”菜单项，并为之指定图标
    JMenuItem saveItem = new JMenuItem("保存", nii("save.png"));
    // 创建“退出”菜单项，并为之指定图标
    JMenuItem exitItem = new JMenuItem("退出", nii("exit.png"));

    JCheckBoxMenuItem cbmiAutoWrap = new JCheckBoxMenuItem("自动换行");

    // 创建“复制”菜单项，并为之指定图标
    JMenuItem copyItem = new JMenuItem("复制", nii("copy.png"));
    // 创建“粘贴”菜单项，并为之指定图标
    JMenuItem pasteItem = new JMenuItem("粘贴", nii("paste.png"));

    JMenu formatMenu = new JMenu("格式");
    JMenuItem commentItem = new JMenuItem("注释");
    JMenuItem cancelItem = new JMenuItem("取消注释");

    // 定义一个右键菜单用于设置程序风格
    JPopupMenu popMenu = new JPopupMenu();
    // 用于组合三个风格菜单项的ButtonGroup
    ButtonGroup flavorGroup = new ButtonGroup();

    // 定义一个按钮,并为之指定图标
    JButton okBtn = new JButton("确认", nii("ok.png"));

    // -----------------用于执行界面初始化的init方法---------------------
    public VariousComponents() {
        setupMenuBar();
        setupPopupMenu();
        setupCenter();
        setupSouth();
        pack();
        setupListener();
    }

    // 创建一个装载了下拉选择框、三个JCheckBox的JPanel
    private void setupCenter() {
        JPanel checkPanel = new JPanel();
        checkPanel.add(colorComboBox);
        checkPanel.add(maleRadio);
        checkPanel.add(femaleRadio);
        checkPanel.add(marriedCheckBox);
        bg.add(maleRadio);
        bg.add(femaleRadio);

        // 创建一个垂直排列组件的Box，盛装多行文本域JPanel
        Box verticalBox = Box.createVerticalBox();
        // 使用JScrollPane作为普通组件的JViewPort
        verticalBox.add(new JScrollPane(jTextArea));
        verticalBox.add(checkPanel);

        // 创建一个水平排列组件的Box，盛装topLeft、colorList
        Box horizontalBox = Box.createHorizontalBox();
        horizontalBox.add(verticalBox);
        horizontalBox.add(colorList);

        // 将top Box容器添加到窗口的中间
        add(horizontalBox, BorderLayout.CENTER);
    }

    // -----------下面开始组合右键菜单、并安装右键菜单----------
    private void setupPopupMenu() {
        UIManager.LookAndFeelInfo[] lafiArr = UIManager.getInstalledLookAndFeels();
        for (UIManager.LookAndFeelInfo lafi : lafiArr) {
            JMenuItem mi = popMenu.add(lafi.getName());
            flavorGroup.add(mi);
            mi.addActionListener(e -> {
                try {
                    changeUIStyle(lafi);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });
        }

        // 调用该方法即可设置右键菜单，无须使用事件机制
        jTextArea.setComponentPopupMenu(popMenu);
    }

    private void setupMenuBar() {
        // -----------下面开始组合菜单、并为菜单添加监听器----------
        fileMenu.add(newItem);
        // 为newItem设置快捷键，设置快捷键时要使用大写字母
        newItem.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_MASK));    //①
        fileMenu.add(saveItem);
        // 为file菜单添加菜单项
        fileMenu.add(exitItem);
        // 为edit菜单添加菜单项
        editMenu.add(cbmiAutoWrap);

        // 使用addSeparator方法来添加菜单分隔线
        editMenu.addSeparator();
        editMenu.add(copyItem);
        editMenu.add(pasteItem);
        // 添加提示信息
        commentItem.setToolTipText("将程序代码注释起来！");
        // 为format菜单添加菜单项
        formatMenu.add(commentItem);
        formatMenu.add(cancelItem);
        // 使用添加new JMenuItem("-")的方式不能添加菜单分隔符
        editMenu.add(new JMenuItem("-"));
        // 将format菜单组合到edit菜单中，从而形成二级菜单
        editMenu.add(formatMenu);
        // 将file、edit菜单添加到mb菜单条中
        jMenuBar.add(fileMenu);
        jMenuBar.add(editMenu);

        // 为f窗口设置菜单条
        setJMenuBar(jMenuBar);
    }

    private void setupSouth() {
        // 创建一个装载了文本框、按钮的JPanel
        JPanel bottom = new JPanel();
        bottom.add(name);
        bottom.add(okBtn);
        add(bottom, BorderLayout.SOUTH);
    }

    private void setupListener() {
        newItem.addActionListener(e -> jTextArea.append("用户单击了“新建”菜单\n"));
    }

    // 定义一个方法，用于改变界面风格
    private void changeUIStyle(UIManager.LookAndFeelInfo lafi) throws Exception {
        println(lafi.getName() + " -> " + lafi.getClassName());
        UIManager.setLookAndFeel(lafi.getClassName());
        // 更新f窗口内顶级容器以及内部所有组件的UI
        SwingUtilities.updateComponentTreeUI(getContentPane());  //②
        // 更新mb菜单条以及内部所有组件的UI
        SwingUtilities.updateComponentTreeUI(jMenuBar);
        // 更新pop右键菜单以及内部所有组件的UI
        SwingUtilities.updateComponentTreeUI(popMenu);
    }

    public static void main(String[] args) {
        // 设置Swing窗口使用Java风格
        //JFrame.setDefaultLookAndFeelDecorated(true);
        FrameUtils.visibleAndExitOnClose(VariousComponents.class)
                .setTitle("各种Swing控件测试（右键空白处有页面风格菜单，点击可切换）");
    }
}

