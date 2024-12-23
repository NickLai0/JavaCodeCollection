package code.java.view.dialog.JDialog.modal;

import javax.swing.*;

import static code.java.utils.LU.println;

public class JOptionPaneDemo extends JFrame {

    public static void main(String[] args) {
        new JOptionPaneDemo().showConfirmDialog();
    }

    private void showConfirmDialog() {
        int selection = JOptionPane.showConfirmDialog(
                this,
                "This is a message",
                "Title",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        switch (selection) {
            case JOptionPane.OK_OPTION:
                println("Clicked OK button from the dialog.");
                break;
            case JOptionPane.OK_CANCEL_OPTION:
                println("Clicked cancel button from the dialog.");
                break;
        }
    }

}
