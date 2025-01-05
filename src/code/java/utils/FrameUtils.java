package code.java.utils;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Method;

public final class FrameUtils {
    //20250106:visibleAndExitOnClose旧逻辑的备份，后续无用可删。
    public static JFrame visibleAndExitOnClose2(Class<? extends JFrame> clz) {
        JFrame f = null;
        if (clz != null) {
            try {
                f = clz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (f != null) {
            f.setVisible(true);
            f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        }
        return f;
    }

    public static JFrame visibleAndExitOnClose(Class<? extends JFrame> clz) {
        return visibleAndExitOnClose(clz, false);
    }

    public static JFrame visibleAndExitOnClose(Class<? extends JFrame> clz, boolean callInitMethod) {
        JFrame f = null;
        if (clz != null) {
            try {
                f = clz.newInstance();
                if (callInitMethod) {
                    Method initMethod = clz.getMethod("init");
                    initMethod.invoke(f);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (f != null) {
            f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            f.setVisible(true);
        }

        return f;
    }

    // locate the owner frame
    public static Frame getFrameAncestorOfClass(Component c) {
        if (c instanceof Frame)
            return (Frame) c;
        // returns the innermost parent container of
        // the given component that belongs to
        // the given class or one of its subclasses.
        return (Frame) SwingUtilities.getAncestorOfClass(Frame.class, c);
    }

}
