package code.java.view.container.frame.Jframe;

import code.java.utils.FrameUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.prefs.Preferences;

/**
 * From Java Core 1,demonstrating how to use
 * JFrame and Preference API. Modified a little.
 * <p>
 * An image viewer that restores position, size, and image
 * from user
 * preferences and updates the preferences upon exit.
 */
public class ImageViewerFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;
    private String imagePath;

    public ImageViewerFrame() {
        Preferences root = Preferences.userRoot();
        Preferences node = root.node("/com/horstmann/corejava/ImageViewer");
        // get position, size, title from properties
        //获取之前退出时保存的视图左上右下位置
        int left = node.getInt("left", 0);
        int top = node.getInt("top", 0);
        int width = node.getInt("width", DEFAULT_WIDTH);
        int height = node.getInt("height", DEFAULT_HEIGHT);
        //设置自己的当前的位置
        setBounds(left, top, width, height);
        //获取之前保存的字符串
        imagePath = node.get("image", null);
        var label = new JLabel();
        //设置只有JFrame的一般那么大
        label.setBounds(0, 0, width / 2, height / 2);
        //label.setPreferredSize(new Dimension(width / 2, height / 2));
        //先add label后setIcon才能显示图片，真他妈妈的！Java Core此案例有bug！
        add(new JScrollPane(label));

        if (imagePath != null) label.setIcon(new ImageIcon(imagePath));
        // use a label to display the images

        setupMenuBar(label);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                node.putInt("left", getX());
                node.putInt("top", getY());
                node.putInt("width", getWidth());
                node.putInt("height", getHeight());
                if (imagePath != null) node.put("image", imagePath);
            }
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            FrameUtils.visibleAndExitOnClose(ImageViewerFrame.class);
        });
    }

    private void setupMenuBar(JLabel label) {
        // set up the file chooser
        var chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));

        // set up the menu bar
        var menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        //Add a menu into manuBar
        var menu = new JMenu("File");
        menuBar.add(menu);

        //Add a menu item into menu.
        var openItem = new JMenuItem("Open");
        menu.add(openItem);

        openItem.addActionListener(event -> {
            // show file chooser dialog
            int result = chooser.showOpenDialog(null);

            // if file selected, set it as icon of the label
            if (result == JFileChooser.APPROVE_OPTION) {
                imagePath = chooser.getSelectedFile().getPath();
                ImageIcon imageIcon = new ImageIcon(imagePath);
                label.setIcon(imageIcon);
            }
        });

        //add an exit item into menu.
        var exitItem = new JMenuItem("Exit");
        menu.add(exitItem);
        exitItem.addActionListener(event -> System.exit(0));
    }

}