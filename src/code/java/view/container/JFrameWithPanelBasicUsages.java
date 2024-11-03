package code.java.view.container;

import javax.swing.*;
import java.awt.*;

//JFrame为窗口容器，演示基本用法。
public class JFrameWithPanelBasicUsages extends JFrame {

    //设置操作必须写在构造方法中
    public JFrameWithPanelBasicUsages() {
        setSize(400, 400);//设置窗口大小
        setTitle("登录界面");           //设置标题
        setLocationRelativeTo(null);   //居中位置
        setResizable(false);           //设置窗口不可调整大小
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭窗口并退出程序运行

        JPanel panel =new JPanel();//创建面板
        panel.setBackground(Color.CYAN);//设置背景，还能自定义颜色。
        add(panel);// 面板为轻量级容器，需添到窗口里
    }

    public static void main(String[] args) {
        //创建FrameDesign类对象
        JFrameWithPanelBasicUsages frameDesign = new JFrameWithPanelBasicUsages();
        frameDesign.setVisible(true);              //让窗口显示出来(放在设置最后一行)
    }

}
