package code.java.view.dialog;

import java.awt.*;

//用来提示的dialog
public class HintDialog extends Dialog {

    Button button = new Button("OK");

    public HintDialog(Frame parent, String msg) {
        this(parent, "hint", msg);
    }

    public HintDialog(Frame parent, String title, String msg) {
        super(parent, title, false);
        setLayout(new BorderLayout());
        add(new Label(msg, Label.CENTER));
        add(button, BorderLayout.SOUTH);
        //if you don’t resize a Window, it won’t  show up!
        resize(200, 200);
    }

    /*
     * Notice that handleEvent( ) just calls dispose( )
     * for a WINDOW_DESTROY so the whole
     * application doesn’t go away.
     */

    public boolean handleEvent(Event evt) {
        if (evt.id == Event.WINDOW_DESTROY) {
            dispose();
        } else {
            return super.handleEvent(evt);
        }
        return true;
    }

    @Override
    public boolean action(Event evt, Object what) {
        if (evt.target == button) {
            dispose();
        } else {
            return super.action(evt, what);
        }
        return true;
    }

}
