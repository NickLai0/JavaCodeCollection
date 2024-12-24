package code.java.view.menu.JMenu;

import code.java.utils.FrameUtils;
import code.java.view.dialog.JDialog.modeless.PasswordChooser;
import code.java.data.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * From Java Core 1:Listing 11.13 dataExchange/DataExchangeFrame.java
 * <p>
 * Demonstrate how to exchange data between frame and dialog.
 *
 * <p>
 * A frame with a menu whose File->
 * Connect action shows a
 * password dialog.
 */
public class DataExchangeFrame extends JFrame {

    public static final int TEXT_ROWS = 20;

    public static final int TEXT_COLUMNS = 40;

    private JMenuItem connectItem = new JMenuItem("Connect");
    private JMenuItem exitItem = new JMenuItem("Exit");

    private PasswordChooser pc = null;

    private JTextArea ta;

    public DataExchangeFrame() {
        setupMenu();
        setupCenterPanel();
        setupListener();
        pack();
    }

    private void setupListener() {
        connectItem.addActionListener(new ConnectAction());
        exitItem.addActionListener(event -> System.exit(0));
    }




    private void setupCenterPanel() {
        ta = new JTextArea(TEXT_ROWS, TEXT_COLUMNS);
        add(new JScrollPane(ta), BorderLayout.CENTER);
    }

    private void setupMenu() {
        // construct a File menu
        JMenuBar mbar = new JMenuBar();
        setJMenuBar(mbar);
        JMenu fileMenu = new JMenu("File");
        mbar.add(fileMenu);
        // add Connect and Exit menu items
        fileMenu.add(connectItem);
        // the Exit item exits the program
        fileMenu.add(exitItem);
    }

    /**
     * The Connect action pops up the password dialog.
     */
    private class ConnectAction implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            // if first time, construct dialog
            if (pc == null) {
                pc = new PasswordChooser();
            }
            // set default values
            pc.setUser(new User("", ""));
            // pop up dialog
            if (pc.showDialog(DataExchangeFrame.this, "Connect")) {
                // if accepted, retrieve user input
                User u = pc.getUser();
                ta.append("user name = " + u.getName() + ", password = " + u.getPassword() + "\n");
            }
        }

    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(DataExchangeFrame.class);
    }

}