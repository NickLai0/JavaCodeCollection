package code.java.view.event.action;

import javax.swing.*;
import java.awt.event.ActionEvent;

import static code.java.utils.ImageUtils.newImageIcon;

//CloseAction和CloseAction2功能一样，但CloseAction2兼容性更好
public class CloseAction extends AbstractAction {

    /**
     * Creates an {@code Action}.
     */
    public CloseAction() {
        this("exit");
    }

    /**
     * Creates an {@code Action} with the specified name.
     *
     * @param name the name ({@code Action.NAME}) for the action; a
     *             value of {@code null} is ignored
     */
    public CloseAction(String name) {
        this(name, newImageIcon("exit.png"));
    }

    /**
     * Creates an {@code Action} with the specified name and small icon.
     *
     * @param name the name ({@code Action.NAME}) for the action; a
     *             value of {@code null} is ignored
     * @param icon the small icon ({@code Action.SMALL_ICON}) for the action; a
     *             value of {@code null} is ignored
     */
    public CloseAction(String name, Icon icon) {
        super(name, icon);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.exit(0);
    }
}
