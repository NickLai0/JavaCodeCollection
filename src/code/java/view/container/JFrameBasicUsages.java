package code.java.view.container;

import javax.swing.*;

//JFrame为窗口容器，演示基本用法。
public class JFrameBasicUsages extends JFrame {

    //设置操作必须写在构造方法中
    public JFrameBasicUsages() {
        this.setSize(400, 400);//设置窗口大小
        this.setTitle("登录界面");           //设置标题
        this.setLocationRelativeTo(null);   //居中位置
        this.setResizable(false);           //设置窗口不可调整大小
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭窗口并退出程序运行
    }

    public static void main(String[] args) {
        //创建FrameDesign类对象
        JFrameBasicUsages frameDesign = new JFrameBasicUsages();
        frameDesign.setVisible(true);              //让窗口显示出来(放在设置最后一行)
    }

}
