package code.java.view.event.keyboard;

import code.java.utils.FrameUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/*
Demonstrate ctrl + enter to fire an action.
 */
public class BindEnterKeyFrame extends JFrame {

    public static final String TEXT_SEND = "Send";

    JTextArea textArea = new JTextArea(5, 30);

    JTextField textField = new JTextField(15);

    JButton btn = new JButton("发送");

    public BindEnterKeyFrame() {
        setupView();
        btn.addActionListener(sendMsgAction);
        bindKeyStor();

    }

    private void bindKeyStor() {
        KeyStroke keyStroke = KeyStroke.getKeyStroke(
                KeyEvent.VK_ENTER, InputEvent.CTRL_MASK
        );
        textField.getInputMap().put(keyStroke, TEXT_SEND);
        // 将"send"和sendMsg Action关联
        textField.getActionMap().put(TEXT_SEND, sendMsgAction);
    }

    private void setupView() {
        add(textArea, BorderLayout.CENTER);
        JPanel jp = new JPanel();
        jp.add(textField);
        jp.add(btn);
        add(jp, BorderLayout.SOUTH);
        pack();
    }

    // 发送消息Action
    Action sendMsgAction = new AbstractAction() {
        public void actionPerformed(ActionEvent e) {
            textArea.append(textField.getText() + "\n");
            textField.setText("");
        }
    };

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(BindEnterKeyFrame.class);
    }
}
