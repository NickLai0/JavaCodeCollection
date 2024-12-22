package code.java.view.dialog.JDialog;

import javax.swing.*;
import java.awt.*;

/**
 * From Java Core 1.
 * Modify a little.
 * Listing 11.12 dialog/AboutDialog.java
 */
public class AboutDialog extends JDialog {

    public AboutDialog(JFrame owner) {
        super(owner, "About DialogTest", true);
        setSize(250, 150);

        add(new JLabel("<html><h1><i>Core Java</i></h1><hr>By Cay Horstmann </html>"), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        JButton ok = new JButton("OK");
        panel.add(ok);//用Panel add Button，Button才不会拉得很大
        add(panel, BorderLayout.SOUTH);

        ok.addActionListener(event -> setVisible(false));
    }

    public static void main(String[] args) {
        new AboutDialog(new JFrame()).setVisible(true);
    }

}