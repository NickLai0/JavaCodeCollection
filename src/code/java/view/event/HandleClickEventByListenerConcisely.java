package code.java.view.event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 据HandleClickEventByListener修改而来，比它简化好多。
 * <p>
 * Demonstrating how to handle click events
 * by listeners.
 * <p>
 * A frame with a button panel.
 */
public class HandleClickEventByListenerConcisely extends JFrame {

    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;

    public HandleClickEventByListenerConcisely() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        JPanel buttonPanel = new JPanel();
        makeButtonAndAddToPanel("yellow", Color.YELLOW, buttonPanel);
        makeButtonAndAddToPanel("blue", Color.BLUE, buttonPanel);
        makeButtonAndAddToPanel("red", Color.RED, buttonPanel);
        // add panel to frame
        add(buttonPanel);
    }

    public void makeButtonAndAddToPanel(
            String name,
            Color backgroundColor,
            JPanel buttonPanel) {
        var button = new JButton(name);
        buttonPanel.add(button);
        button.addActionListener(
                event -> buttonPanel.setBackground(backgroundColor)
                /*
                上面一行，相当于下面那么多代码。可见lamda节约了new ActionListener（），
                actionPerformed的方法签名，ActionEvent这个参数类型，以及大括号，取而代之
                的是event ->（参数名 ->）。


                new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        buttonPanel.setBackground(backgroundColor)
                    }
                }
             */
        );

    }

    public static void main(String[] args) {
        var frame = new HandleClickEventByListenerConcisely();
        frame.setVisible(true);
    }
}
