package code.java.view.text;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * From Java Core 1
 * A frame with sample text components.
 */
public class TextComponentFrame extends JFrame/*BorderLayout(default)*/ {
    public static final int TEXTAREA_ROWS = 8;
    public static final int TEXTAREA_COLUMNS = 20;

    public TextComponentFrame() {
        //输入账号密码布局
        var northPanel = new JPanel();//FlowLayout(default)
        northPanel.setLayout(new GridLayout(2, 2));
        northPanel.add(new JLabel("User name: ", SwingConstants.RIGHT));
        var textField = new JTextField();
        northPanel.add(textField);
        northPanel.add(new JLabel("Password: ", SwingConstants.RIGHT));
        var passwordField = new JPasswordField();
        northPanel.add(passwordField);

        add(northPanel, BorderLayout.NORTH);

        var textArea = new JTextArea(TEXTAREA_ROWS, TEXTAREA_COLUMNS);
        //用JScrollPane将JTextArea包起来，有点像Android的ScrollView
        var scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);
        // add button to append text into the text area
        //按钮也套个布局。
        var southPanel = new JPanel();
        var insertButton = new JButton("Insert");
        southPanel.add(insertButton);
        insertButton.addActionListener(
                event -> textArea.append("User name: " + textField.getText() + " Password:" + new String(passwordField.getPassword()) + "\n")
        );
        //南部布局
        add(southPanel, BorderLayout.SOUTH);
        pack();
    }
}
