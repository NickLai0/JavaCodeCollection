package code.java.adapter;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static code.java.utils.LU.println;

/**
 * A sample action that prints the action name to
 * System.out.
 */
public class ActionNamePrinterAdapter extends AbstractAction {

    public ActionNamePrinterAdapter(String name) {
        super(name);
    }

    public void actionPerformed(ActionEvent event) {
        println("actionPerformed->" + getValue(Action.NAME));
    }

}