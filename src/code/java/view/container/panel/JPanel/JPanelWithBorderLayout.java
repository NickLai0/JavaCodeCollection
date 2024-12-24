package code.java.view.container.panel.JPanel;

import javax.swing.*;
import java.awt.*;

//演示JPanel配合BorderLayout（边界布局）的基本用法
public class JPanelWithBorderLayout extends JFrame {
    //设置操作必须写在构造方法中
    public JPanelWithBorderLayout() {
        setSize(400, 400);//设置窗口大小
        setTitle("登录界面");           //设置标题
        setLocationRelativeTo(null);   //居中位置
        setResizable(false);           //设置窗口不可调整大小
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭窗口并退出程序运行

        JPanel panel = new JPanel(new BorderLayout(10, /*从左往右控件之间间隙*/ 10/*从上往下控件之间间隙*/));

        JButton button1 = new JButton("按钮1");
        JButton button2 = new JButton("按钮2");
        JButton button3 = new JButton("按钮3");
        JButton button4 = new JButton("按钮4");
        JButton button5 = new JButton("按钮5");

        panel.add(button1, BorderLayout.NORTH);//添加时指明位置
        panel.add(button2, BorderLayout.SOUTH);
        panel.add(button3, BorderLayout.WEST);
        panel.add(button4, BorderLayout.EAST);
        //中间不能省略，上下左右任意一边缺少，自动填充
        panel.add(button5, BorderLayout.CENTER);

        add(panel);//将面板添加到窗口中
    }

    public static void main(String[] args) {
        JPanelWithBorderLayout jPanelWithFlowLayout = new JPanelWithBorderLayout();//创建FrameDesign类对象
        jPanelWithFlowLayout.setVisible(true);              //让窗口显示出来(放在设置最后一行)
    }

}

