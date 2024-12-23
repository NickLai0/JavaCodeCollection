package code.java.utils;

import javax.swing.*;
import java.awt.*;

public final class FrameUtils {

    public static void visibleAndExitOnClose(Class<? extends JFrame> clz) {
        if (clz == null) {
            return;
        }
        try {
            JFrame f = clz.newInstance();
            f.setVisible(true);
            f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
