package code.java.view.jrame;

import code.java.view.dialog.JDialog.modeless.AboutDialog;

import javax.swing.*;

/**
 * Java Core 1.
 * <p>
 * Listing 11.11 dialog/DialogFrame.java
 * <p>
 * A frame with a menu whose File->
 * About action shows a dialog.
 */
public class DialogFrame extends JFrame {

    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;

    private AboutDialog dialog;

    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenuItem aboutItem = new JMenuItem("About");
    JMenuItem exitItem = new JMenuItem("Exit");

    public DialogFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        initView();
        initListener();
    }

    private void initListener() {
        aboutItem.addActionListener(event -> {
            if (dialog == null) // first time
                dialog = new AboutDialog(DialogFrame.this);
            dialog.setVisible(true); // pop up dialog
        });

        exitItem.addActionListener(event -> System.exit(0));
    }

    private void initView() {
        // construct a File menu
        setJMenuBar(menuBar);
        menuBar.add(fileMenu);
        // add About and Exit menu items
        // the About item shows the About dialog
        fileMenu.add(aboutItem);
        // the Exit item exits the program
        fileMenu.add(exitItem);
    }

    public static void main(String[] args) {
        new DialogFrame().setVisible(true);
    }

}
