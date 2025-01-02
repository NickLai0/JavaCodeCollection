package code.java.view.container.pane;

import javax.swing.*;
import java.beans.PropertyVetoException;

public class MyDesktopPane extends JDesktopPane {
    // 将所有窗口以级联方式显示,
    // 其中offset是两个窗口的位移距离,
    // scale是内部窗口与JDesktopPane的大小比例
    //级联窗口-》其实就是层层叠加
    public void cascadeWindows(int offset, double scale) {
        // 定义级联显示窗口时内部窗口的大小
        int width = (int) (getWidth() * scale);
        int height = (int) (getHeight() * scale);
        // 用于保存级联窗口时每个窗口的位置
        int x = 0;
        int y = 0;
        for (JInternalFrame frame : getAllFrames()) {
            try {
                // 取消内部窗口的最大化最小化
                frame.setMaximum(false);
                frame.setIcon(false);
                // 把窗口重新放置在指定位置
                frame.reshape(x, y, width, height);
                x += offset;
                y += offset;
                // 如果到了虚拟桌面边界
                if (x + width > getWidth()) x = 0;
                if (y + height > getHeight()) y = 0;
            } catch (PropertyVetoException e) {
            }
        }
    }

    // 将所有窗口以平铺方式显示
    public void tileWindows() {
        // 统计所有窗口
        int frameCount = getAllFrames().length;
        // 计算需要多少行、多少列才可以平铺所有窗口
        int rows = (int) Math.sqrt(frameCount);
        int cols = frameCount / rows;
        // 需要额外增加到其他列中的窗口
        int extra = frameCount % rows;
        // 计算平铺时内部窗口的大小
        int internalWindowWidth = getWidth() / cols;
        int internalWindowHeight = getHeight() / rows;
        // 用于保存平铺窗口时每个窗口在横向、纵向上的索引
        int x = 0;
        int y = 0;
        for (JInternalFrame frame : getAllFrames()) {
            try {
                // 取消内部窗口的最大化,最小化
                frame.setMaximum(false);
                frame.setIcon(false);
                // 将窗口放在指定位置
                frame.reshape(
                        x * internalWindowWidth,
                        y * internalWindowHeight,
                        internalWindowWidth,
                        internalWindowHeight
                );
                y++;
                // 每排完一列窗口
                if (y == rows) {
                    // 开始排放下一列窗口
                    y = 0;
                    x++;
                    // 如果额外多出的窗口与剩下的列数相等，
                    // 则后面所有列都需要多排列一个窗口
                    if (extra == cols - x) {
                        rows++;
                        internalWindowHeight = getHeight() / rows;
                    }
                }
            } catch (PropertyVetoException e) {
            }
        }
    }

    // 选中下一个非图标窗口
    public void selectNextShowingWindow() {
        JInternalFrame[] frames = getAllFrames();
        for (int i = 0; i < frames.length; i++) {
            if (frames[i].isSelected()) {
                //  找出下一个非最小化的窗口，尝试选中它，
                // 如果选中失败，则继续尝试选中下一个窗口
                int next = i;
                while ((next = (next + 1) % frames.length) != i) {
                    // 如果该窗口不是处于最小化状态
                    if (!frames[next].isIcon()) {
                        try {
                            //设置选择
                            frames[next].setSelected(true);
                            //显示在前面
                            frames[next].toFront();
                            //显示在后面
                            frames[i].toBack();
                            return;
                        } catch (PropertyVetoException e) {
                        }
                    }

                }
            }
        }
    }
}