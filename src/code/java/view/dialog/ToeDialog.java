package code.java.view.dialog;

import code.java.view.custome.button.ToeButton;

import java.awt.*;

//(Toe game)脚趾游戏， comes from Thinking in Java，modified by myself。
//Toe dialog.
public class ToeDialog extends Dialog {
   /*

    // w = number of cells wide
    // h = number of cells high
    static final int XX = 1;
    static final int OO = 2;
    public int turn = XX; // Start with x's turn

    */

    public ToeDialog(Frame parent, int rows, int columns) {
        super(parent, "The toe game dialog", false);
        setLayout(new GridLayout(rows, columns));
        for (int i = 0; i < rows * columns; i++) {
            add(new ToeButton(this));
        }
        int width = rows * 50;
        int height = columns * 50;
        //if you don’t resize a Window, it won’t  show up!
        resize(width, height);
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
}
