package code.java.string;

import code.java.utils.FileUtils;
import code.java.utils.FrameUtils;
import code.java.view.container.dialog.HintDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Map;
import java.util.SortedMap;

/**
 * 字符串编码矫正器
 *
 * 单个文本文件原始编码（如“GB2312”）转为UTF-8编码纠正器。
 * <p>
 * 后续如果有需求，可以增加下拉选择该对应编码集。
 */
public class StringCharsetCorrectHandler extends JFrame {

    JTextField tfSrc = new JTextField();//源文件

    JTextField tfDest = new JTextField();//目标文件夹

    JButton btnRefreshStr = new JButton("显示源文件对应的字符串");//目标文件夹

    public StringCharsetCorrectHandler() {
        setPreferredSize(new Dimension(800, 600));
        setupNorth();
        setupCenter();
        setupSouth();
        pack();
        setupListener();
    }

    private void setupSouth() {
        JPanel p = new JPanel();
        p.add(btnRefreshStr);
        add(p, BorderLayout.SOUTH);
    }

    private void setupListener() {
        for (CharterRowInfo cri : criList) {
            cri.btn.addActionListener(new OutputCorrectCharactersAction(cri));
        }
        btnRefreshStr.addActionListener(new ShowString4AllLabelAction());
    }

    private void setupNorth() {
        JPanel p = new JPanel(new GridLayout(2, 2));
        addPathRow(p, "输入目录", tfSrc);
        addPathRow(p, "输出目录", tfDest);
        add(p, BorderLayout.NORTH);
    }

    //添加文件路径行
    private void addPathRow(JPanel p, String label, JTextField tf) {
        p.add(new JLabel(label));
        p.add(tf);
    }

    private void setupCenter() {
        SortedMap<String, Charset> charsetMap = Charset.availableCharsets();
        JPanel p = new JPanel(new GridLayout(charsetMap.size(), 1));
        for (Map.Entry<String, Charset> e : charsetMap.entrySet()) {
            addCharacterOutputRow(p, e.getValue());
        }
        add(new JScrollPane(p), BorderLayout.CENTER);
    }

    private void addCharacterOutputRow(JPanel p, Charset charset) {
        //setup view
        CharterRowInfo cri = new CharterRowInfo();
        JPanel pRow = new JPanel();
        pRow.add(cri.label);
        pRow.add(cri.tf);
        pRow.add(cri.btn);
        p.add(pRow);
        //setup data
        cri.charset = charset;
        cri.label.setText(charset.name());
        cri.label.setPreferredSize(new Dimension(150, 20));
        //add CharterRowInfo into list.
        criList.add(cri);
    }

    class ShowString4AllLabelAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (CharterRowInfo cri : criList) {
                try {
                    //（charset）string -》 unicode.
                    String str = FileUtils.fileToString(new File(tfSrc.getText()), cri.charset.name());
                    if (str.length() > 100) {
                        str = str.substring(0, 100) + "...";
                    }
                    cri.tf.setText(str);
//                    println(cri.charset + "->" + str);
                } catch (Exception ex) {
                    cri.tf.setText("unsupported");
                }
            }
        }
    }

    //输出正确（编码）字符action
    class OutputCorrectCharactersAction implements ActionListener {
        final CharterRowInfo cri;

        public OutputCorrectCharactersAction(CharterRowInfo cri) {
            this.cri = cri;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            File srcFile = new File(tfSrc.getText());
            try {  //（charset）string -》 unicode.
                String str = FileUtils.fileToString(srcFile, cri.charset.name());
                FileUtils.copy(  //unicode -》utf-8 string bytes.
                        new ByteArrayInputStream(str.getBytes("UTF-8")),
                        new FileOutputStream(new File(tfDest.getText(), srcFile.getName()))
                );
                new HintDialog(StringCharsetCorrectHandler.this, "Finished!").show();
            } catch (Exception ex) {
                new HintDialog(StringCharsetCorrectHandler.this, ex.toString()).show();
            }
        }
    }

    //存放所有CharterRowInfo，用来做选择源文件后，显示所有字符串编码对应的字符串结果
    java.util.List<CharterRowInfo> criList = new ArrayList();

    static class CharterRowInfo {
        Charset charset;
        JLabel label = new JLabel();
        JTextField tf = new JTextField(70);
        JButton btn = new JButton("输出UTF-8");
    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(StringCharsetCorrectHandler.class).setTitle("当个文件字符纠正器");
    }

}
