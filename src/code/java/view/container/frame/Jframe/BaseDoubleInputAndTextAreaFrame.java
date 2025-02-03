package code.java.view.container.frame.Jframe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class BaseDoubleInputAndTextAreaFrame extends JFrame/*BorderLayout(default)*/ {

    public static final int TEXTAREA_ROWS = 8;

    public static final int TEXTAREA_COLUMNS = 20;

    protected JTextField tfFrom = new JTextField();

    protected JTextField tfTo = new JTextField();

    protected JTextArea textArea = new JTextArea(TEXTAREA_ROWS, TEXTAREA_COLUMNS);

    protected JButton btn = new JButton(buttonName());

    public BaseDoubleInputAndTextAreaFrame() {
        prepareNorthPanel();
        prepareCenterPanel();
        prepareSouthPanel();
        pack();
        initListener();
    }

    private void prepareSouthPanel() {
        // add button to append text into the text area
        //按钮也套个布局。
        JPanel southPanel = new JPanel();
        southPanel.add(btn);
        //南部布局
        add(southPanel, BorderLayout.SOUTH);
    }

    private void prepareCenterPanel() {
        //用JScrollPane将JTextArea包起来，有点像Android的ScrollView
        add(new JScrollPane(textArea), BorderLayout.CENTER);
    }

    private void prepareNorthPanel() {
        //输入账号密码布局
        JPanel northPanel = new JPanel();//FlowLayout(default)
        northPanel.setLayout(new GridLayout(2, 2));
        northPanel.add(new JLabel(labelFrom(), SwingConstants.RIGHT));
        northPanel.add(tfFrom);
        northPanel.add(new JLabel(labelTo(), SwingConstants.RIGHT));
        northPanel.add(tfTo);
        prepareNorthByChild(northPanel);
        add(northPanel, BorderLayout.NORTH);
    }

    protected void prepareNorthByChild(JPanel northGridPanel) {
    }

    private String buttonName() {
        return "OK";
    }

    //子类配置来源label名称
    protected abstract String labelFrom();

    //子类配置目标label名称
    protected abstract String labelTo();

    private void initListener() {
        btn.addActionListener(new StartButtonActionListener());
    }

    private class StartButtonActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (mListener != null) {
                mListener.onButtonClick(tfFrom, tfTo, textArea);
            }
        }
    }

    private OnButtonClickListener mListener;

    public void setOnButtonClickListener(OnButtonClickListener l) {
        mListener = l;
    }

    public interface OnButtonClickListener {
        void onButtonClick(
                JTextField tfTxtDirFrom,
                JTextField tfDocxDirTo,
                JTextArea jTextArea
        );
    }

}
