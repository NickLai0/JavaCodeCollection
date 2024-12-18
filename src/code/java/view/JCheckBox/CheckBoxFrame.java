package code.java.view.JCheckBox;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * From Java Core volume 1.
 * Modify a little.
 * Demonstrating JCheckBox、JLabel and Font(bold、italic).
 * <p>
 * <p>
 * A frame with a sample text label and check boxes
 * for selecting font attributes.
 */
public class CheckBoxFrame extends JFrame {

    private JLabel label;
    private JCheckBox bold;
    private JCheckBox italic;

    private static final int FONTSIZE = 24;

    public CheckBoxFrame() {
        prepareCenter();
        prepareBottom();
        pack(); //UI自适应
        prepareListener();
        bold.setSelected(true);
    }

    private void prepareListener() {
        bold.addActionListener(listener);
        italic.addActionListener(listener);
    }

    private void prepareBottom() {
        // add the check boxes
        var buttonPanel = new JPanel();
        bold = new JCheckBox("Bold");
        buttonPanel.add(bold);

        italic = new JCheckBox("Italic");
        buttonPanel.add(italic);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void prepareCenter() {
        // add the sample text label
        label = new JLabel("The quick brown fox jumps over the lazy dog.");
        label.setFont(new Font("Serif", Font.BOLD, FONTSIZE));
        add(label, BorderLayout.CENTER);
    }

    // this listener sets the font attribute of
    // the label to the check box state
    ActionListener listener = event -> {
        int mode = 0;
        if (bold.isSelected()) {
            mode = Font.BOLD;
        }
        if (italic.isSelected()) {
            //叠加斜体
            mode |= Font.ITALIC;
        }
        label.setFont(new Font("Serif", mode, FONTSIZE));
    };

    public static void main(String[] args) {
        CheckBoxFrame cbf = new CheckBoxFrame();
        cbf.setVisible(true);
    }
}