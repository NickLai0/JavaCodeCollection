package code.java.view.theme;

import javax.swing.*;

/**
 * Modify a little.
 *
 ComponentUI使用当前PLAF。
 UIManager.setLookAndFeel()方法可改Swing外观风格。
 UIManager.getInstalledLookAndFeels()可获取系统当前可用LAF。
 按：类似Android的Theme
 */
public class ShowAllLookAndFeel {
    public static void main(String[] args) {
        System.out.println("当前系统可用的所有LAF:");
        UIManager.LookAndFeelInfo[] lafiArr = UIManager.getInstalledLookAndFeels();
        for (UIManager.LookAndFeelInfo info : lafiArr) {
            System.out.println(info.getName() + " -> " + info.getClassName()
            );
        }
    }
}
