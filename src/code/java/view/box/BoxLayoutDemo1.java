package code.java.view.box;

import code.java.adapter.WindowCloserAdapter;

import javax.swing.*;
import java.awt.*;
/*
不知为何，换成JFrame，一添加UI（如Button）就报错：BoxLayout can't be shared
 */
public class BoxLayoutDemo1 {
    public static void main(String[] args) {
        //1.创建Frame对象
        Frame frame = new Frame("这里测试BoxLayout");
        //2.创建BoxLayout布局管理器，并指定容器为上面的frame对象，指定组件排列方向为纵向
        BoxLayout boxLayout = new BoxLayout(frame, BoxLayout.Y_AXIS);
        frame.setLayout(boxLayout);
        //3.往frame对象中添加两个按钮
        frame.add(new JButton("按钮1"));
        frame.add(new JButton("按钮2"));
        //4.设置frame最佳大小，并可见
        frame.pack();
        frame.setVisible(true);

        frame.addWindowListener(new WindowCloserAdapter());
    }
}
