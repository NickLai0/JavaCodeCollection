package code.java.view.fkjjy;

import javax.swing.*;

/**
 * Modify a little.
 *
 ComponentUI使用当前PLAF。
 UIManager.setLookAndFeel()方法可改Swing外观风格。
 UIManager.getInstalledLookAndFeels()可获取系统当前可用LAF。
 按：类似Android的Theme

 这里显示当前系统支持的LAF。

 *
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
public class AllLookAndFeel {
    public static void main(String[] args) {
        System.out.println("当前系统可用的所有LAF:");
        UIManager.LookAndFeelInfo[] lafiArr = UIManager.getInstalledLookAndFeels();
        for (UIManager.LookAndFeelInfo info : lafiArr) {
            System.out.println(info.getName() + "--->" + info);
        }
    }
}
