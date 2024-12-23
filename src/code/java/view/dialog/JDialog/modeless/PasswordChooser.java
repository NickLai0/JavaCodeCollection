package code.java.view.dialog.JDialog.modeless;

import code.java.data.User;
import code.java.utils.FrameUtils;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * From Java Core 1: Listing 11.14 dataExchange/PasswordChooser.java
 * Modify a lot.
 * A password chooser that is shown inside a dialog.
 */
public class PasswordChooser extends JPanel {

    private JTextField username = new JTextField("");

    private JPasswordField password = new JPasswordField("");

    private JButton okButton = new JButton("Ok");
    private JButton cancelButton = new JButton("Cancel");

    private boolean okButtonClicked;

    private JDialog dialog;

    public PasswordChooser() {
        setLayout(new BorderLayout());
        prepareCenter();
        prepareSouth();
        prepareListener();
    }

    private void prepareListener() {
        // create Ok and Cancel buttons that terminate the
        okButton.addActionListener(event -> {
            okButtonClicked = true;
            dialog.setVisible(false);
        });
        cancelButton.addActionListener(
                event -> dialog.setVisible(false)
        );
    }

    private void prepareSouth() {
        // add buttons to southern border
        var buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void prepareCenter() {
        // construct a panel with user name and password fields
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        panel.add(new JLabel("User name:"));
        panel.add(username);
        panel.add(new JLabel("Password:"));
        panel.add(password);
        add(panel, BorderLayout.CENTER);
    }

    /**
     * Sets the dialog defaults.
     *
     * @param u the default user information
     */
    public void setUser(User u) {
        username.setText(u.getName());
    }

    /**
     * Gets the dialog entries.
     *
     * @return a User object whose state represents the dialog
     * entries
     */
    public User getUser() {
        return new User(username.getText(), new String(password.getPassword()));
    }

    /**
     * Show the chooser panel in a dialog.
     *
     * @param parent a component in the owner frame or null
     * @param title  the dialog window title
     */
    public boolean showDialog(Component parent, String title) {
        okButtonClicked = false;
        Frame owner = FrameUtils.getFrameAncestorOfClass(parent);
        // if first time, or if owner has changed, make new
        if (dialog == null || dialog.getOwner() != owner) {
            dialog = new JDialog(owner, true);
            //Set dialog view.
            dialog.add(this);
            //Config default button outside.
            dialog.getRootPane().setDefaultButton(okButton);
            dialog.pack();
        }
        // set title and show dialog
        dialog.setTitle(title);
        dialog.setVisible(true);
        return okButtonClicked;
    }

}