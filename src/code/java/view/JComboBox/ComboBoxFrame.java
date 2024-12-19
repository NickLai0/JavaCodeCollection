package code.java.view.JComboBox;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * From Java Core Vol 1. Modify a little.
 * Demonstrate how to use JComboBox.
 * List是直接一个列表显示出来，而JComboBox则是下拉选择。
 * A frame with a sample text label and a combo box for
 * selecting font faces.
 */
public class ComboBoxFrame extends JFrame {

    private static final int DEFAULT_SIZE = 24;

    private JComboBox<String> faceCombo;

    private JLabel label;

    public ComboBoxFrame() {
        prepareCenter();
        prepareBottom();
        pack();
        // the combo box listener changes the label font to the selected face name
        faceCombo.addActionListener(event ->
                label.setFont(
                        new Font(faceCombo.getItemAt(faceCombo.getSelectedIndex()), Font.PLAIN, DEFAULT_SIZE)
                )
        );
    }

    private void prepareBottom() {
        // make a combo box and add face names
        faceCombo = new JComboBox<>();
        faceCombo.addItem("Serif");
        faceCombo.addItem("SansSerif");
        faceCombo.addItem("Monospaced");
        faceCombo.addItem("Dialog");
        faceCombo.addItem("DialogInput");
        // add combo box to a panel at the frame's southern border
        var comboPanel = new JPanel();
        comboPanel.add(faceCombo);
        add(comboPanel, BorderLayout.SOUTH);
    }

    private void prepareCenter() {
        // add the sample text label
        label = new JLabel("The quick brown fox jumps over the lazy dog.");
        label.setFont(new Font("Serif", Font.PLAIN, DEFAULT_SIZE));
        add(label, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        new ComboBoxFrame().setVisible(true);
    }

}
