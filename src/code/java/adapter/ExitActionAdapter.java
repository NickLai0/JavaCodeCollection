package code.java.adapter;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ExitActionAdapter extends AbstractAction {
    public ExitActionAdapter() {
        super("Exit");
    }

    public ExitActionAdapter(String name) {
        super(name);
    }

    public void actionPerformed(ActionEvent event) {
        System.exit(0);
    }
}
