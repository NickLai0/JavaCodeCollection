package code.java.view.box;

import code.java.adapter.WindowCloserAdapter;

import javax.swing.*;
import java.awt.*;

public class BoxLayoutDemo2 {
    public static void main(String[] args) {
        //1.创建Frame对象
        Frame frame = new Frame("这里测试BoxLayout");
        //2.创建一个横向的Box,并添加两个按钮
        Box hBox = Box.createHorizontalBox();
        hBox.add(new JButton("水平按钮一"));
        hBox.add(new JButton("水平按钮二"));
        //3.创建一个纵向的Box，并添加两个按钮
        Box vBox = Box.createVerticalBox();
        vBox.add(new JButton("垂直按钮一"));
        vBox.add(new JButton("垂直按钮二"));
        //4.把box容器添加到frame容器中
        frame.add(hBox, BorderLayout.NORTH);
        frame.add(vBox);
        //5.设置frame最佳大小并可见
        frame.pack();
        frame.setVisible(true);

        frame.addWindowListener(new WindowCloserAdapter());
    }
}
