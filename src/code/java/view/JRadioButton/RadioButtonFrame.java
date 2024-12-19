package code.java.view.JRadioButton;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * A frame with a sample text label and radio buttons for
 * selecting font sizes.
 */
public class RadioButtonFrame extends JFrame {

    private JPanel buttonPanel;

    private ButtonGroup group;

    private JLabel label;

    private static final int DEFAULT_SIZE = 36;

    public RadioButtonFrame() {
        // add the sample text label
        label = new JLabel("The quick brown fox jumps over the lazy dog.");
        label.setFont(new Font("Serif", Font.PLAIN, DEFAULT_SIZE));
        add(label, BorderLayout.CENTER);

        // add the radio buttons
        buttonPanel = new JPanel();
        group = new ButtonGroup();
        addRadioButton("Small", 8);
        addRadioButton("Medium", 12);
        addRadioButton("Large", 18);
        addRadioButton("Extra large", 36);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
    }

    /**
     * Adds a radio button that sets the font size of the sample
     * text.
     *
     * @param name the string to appear on the button
     * @param size the font size that this button sets
     */
    public void addRadioButton(String name, int size) {
        boolean selected = size == DEFAULT_SIZE;
        var radio = new JRadioButton(name, selected);
        //居然设计成显添加到ButtonGroup后再添加到Panel，设计等不如android
        group.add(radio);
        buttonPanel.add(radio);

        // this listener sets the label font size
        ActionListener listener = event ->
                //每个radio被点击后，重新设置一下字体。
                label.setFont(new Font("Serif", Font.PLAIN, size));
        radio.addActionListener(listener);
    }

    public static void main(String[] args) {
        new RadioButtonFrame().setVisible(true);
    }
}