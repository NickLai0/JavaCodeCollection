package code.java.view.JFileChooser;

import code.java.adapter.ExitActionAdapter;
import code.java.utils.FrameUtils;
import code.java.utils.TimerUtils;
import code.java.view.JFileChooser.fileView.FileIconView;
import code.java.view.JFileChooser.filter.CommonImageExtensionFileFilter;
import code.java.view.JFileChooser.helper.FileChooserPreviewHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 定时递归自动查看目录和子目录下的图片
 */
public class ImageAutoViewer extends JFrame {

    JMenuBar menuBar = new JMenuBar();
    JMenuItem openItem = new JMenuItem("打开");
    JMenuItem exitItem = new JMenuItem("Exit");

    JCheckBox cbAutoSwitchImage = new JCheckBox("自动换图（秒）");
    JTextField tfSeconds = new JTextField("60");//默认六十秒

    JButton btnPrevious = new JButton("上一张");//默认六十秒
    JButton btnPNext = new JButton("下一张");//默认六十秒

    // 该label用于显示图片
    JLabel labelImage = new JLabel();

    // 以当前路径创建文件选择器
    JFileChooser chooser = new JFileChooser(".");

    // 定义文件过滤器
    CommonImageExtensionFileFilter ciefFilter = new CommonImageExtensionFileFilter();

    private FileChooserPreviewHelper previewHelper = new FileChooserPreviewHelper();

    public ImageAutoViewer() {
        setTitle("文件夹下图片自动查看器");
        initUI();
        initFileChooser();
        initListener();
    }

    private void initFileChooser() {
        /*
            If you want the user to select directories,
            use the setFileSelectionMode method.
            Call it with JFileChooser.DIRECTORIES_ONLY.
        */
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.addChoosableFileFilter(ciefFilter);
        // 禁止“文件类型”下拉列表显示“所有文件”选项。
        chooser.setAcceptAllFileFilterUsed(false);
        //if you want to show different icons for special ﬁle types, you can install your own ﬁle view
        chooser.setFileView(new FileIconView(ciefFilter));
        // 为文件选择器指定一个预览图片的附件
        chooser.setAccessory(previewHelper.getPreviewLabel());     //
    }

    private void initListener() {
        // 单击openItem菜单项显示“打开文件”的对话框
        openItem.addActionListener(openImageActionListener);
        exitItem.addActionListener(new ExitActionAdapter());
        // 用于检测被选择文件的改变事件
        chooser.addPropertyChangeListener(new FileChooserPreviewHelper());

        cbAutoSwitchImage.addActionListener(new SelectAutoSwitchAction());
        btnPrevious.addActionListener(switchImageActionListener);
        btnPNext.addActionListener(switchImageActionListener);
    }

    // ------该窗口安装菜单------
    private void initUI() {
        setupMenu();
        setupCenter();
        setupSouth();
        pack();
    }

    private void setupSouth() {
        Panel panel = new Panel();
        panel.add(cbAutoSwitchImage);
        panel.add(tfSeconds);
        panel.add(btnPrevious);
        panel.add(btnPNext);
        add(panel, BorderLayout.SOUTH);
    }

    private void setupCenter() {
        // 添加用于显示图片的JLabel组件。
        add(new JScrollPane(labelImage), BorderLayout.CENTER);
        setPreferredSize(new Dimension(500, 500));
    }

    private void setupMenu() {
        JMenu menu = new JMenu("文件");
        menuBar.add(menu);
        menu.add(openItem);
        menu.add(exitItem);
        // 为退出菜单绑定事件监听器
        setJMenuBar(menuBar);
    }

    private ActionListener openImageActionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            // 显示文件对话框
            int result = chooser.showDialog(ImageAutoViewer.this, "打开");
            // 如果用户选择了APPROVE（同意）按钮，即打开，保存的等效按钮
            if (result == JFileChooser.APPROVE_OPTION) {
                imagePathList.clear();
                organizeAllImagePaths(chooser.getSelectedFile());
                showImageByIndexLooply(currentIndex = 0);
            }
        }


    };

    private int currentIndex = 0;

    private List<String> imagePathList = new ArrayList();

    private void organizeAllImagePaths(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory()) {
            return;
        }
        for (File f : dir.listFiles()) {
            if (f.isDirectory()) {
                organizeAllImagePaths(f);
            } else {
                if (ciefFilter.accept(f)) {
                    //是图片，添加路径
                    imagePathList.add(f.getAbsolutePath());
                }
            }
        }
    }

    //循环展示列表里的图片
    private void showImageByIndexLooply(int i) {
        if (imagePathList.isEmpty()) {
            return;
        }
        if (i >= 0) {
            currentIndex = i % imagePathList.size();
            String path = imagePathList.get(currentIndex);
            labelImage.setIcon(new ImageIcon(path));
        }
    }

    ActionListener switchImageActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnPrevious) {
                preImage();
            } else if (e.getSource() == btnPNext) {
                nextImage();
            }
        }
    };

    private void preImage() {
        showImageByIndexLooply(currentIndex - 1);
    }

    private void nextImage() {
        showImageByIndexLooply(currentIndex + 1);
    }

    private Timer autoSwitchImageTimer;

    private int mSwitchMillis = 0;

    class SelectAutoSwitchAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (cbAutoSwitchImage.isSelected()) {
                int configuredSeconds = Integer.parseInt(tfSeconds.getText());
                long configuredMillis = TimeUnit.SECONDS.toMillis(configuredSeconds);
                if (autoSwitchImageTimer == null || configuredMillis != mSwitchMillis) {
                    //首次或者配置的时间有变化
                    mSwitchMillis = (int) configuredMillis;
                    //创建-》启动-》保存Timer
                    autoSwitchImageTimer = TimerUtils.start(
                            new AutoSwitchAction(),
                            mSwitchMillis
                    );
                } else {
                    //配置的时间没变化，直接启动就好
                    autoSwitchImageTimer.start();
                }
            } else {
                autoSwitchImageTimer.stop();
            }
        }
    }

    class AutoSwitchAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            nextImage();
        }
    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(ImageAutoViewer.class);
    }

}