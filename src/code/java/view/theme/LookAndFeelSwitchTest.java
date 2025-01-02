package code.java.view.theme;

import javax.swing.*;

/**
 */
public class LookAndFeelSwitchTest {
    public static void main(String[] args) {
        System.out.println("当前系统可用的所有LAF:");
        UIManager.LookAndFeelInfo[] lafiArr = UIManager.getInstalledLookAndFeels();
        for (UIManager.LookAndFeelInfo info : lafiArr) {
            System.out.println(info.getName() + "--->" + info);
        }
    }
}
