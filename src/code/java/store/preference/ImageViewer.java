package code.java.store.preference;

import code.java.view.container.frame.Jframe.ImageViewerFrame;

import javax.swing.*;
import java.awt.*;

/**
 * From Java Core 1
 * <p>
 * A program to test preference settings.
 * The program remembers the
 * frame position, size, and last selected file.
 *
 * @author Cay Horstmann
 * @version 1.10 2018-04-10
 */
public class ImageViewer {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            var frame = new ImageViewerFrame();
            frame.setTitle("ImageViewer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

