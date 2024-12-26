package code.java.utils;

import javax.swing.*;
import java.awt.*;

public final class FrameUtils {

    public static JFrame visibleAndExitOnClose(Class<? extends JFrame> clz) {
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
