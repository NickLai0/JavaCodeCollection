package code.java.view.event.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Deprecated
public class CloseAction2 implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
}
