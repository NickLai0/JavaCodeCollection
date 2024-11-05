package code.java.view.game;
//ToeTest.java comes from Thinking in Java
// Demonstration of dialog boxes
// and creating your own components
//Modified by myself.
import code.java.view.dialog.ToeDialog;

import java.awt.*;

public class ToeGame extends Frame {

    TextField tfRows = new TextField("3");
    TextField tfColumns = new TextField("3");

    public ToeGame() {
        setTitle("Toe Test");

        Panel p = new Panel();
        p.setLayout(new GridLayout(2,2));
        p.add(new Label("Rows", Label.CENTER));
        p.add(tfRows);
        p.add(new Label("Columns", Label.CENTER));
        p.add(tfColumns);

        //Put the panel on top.
        add("North", p);
        //Put the button on bottom.
        add("South", new Button("go"));
    }

    public boolean handleEvent(Event evt) {
        if(evt.id == Event.WINDOW_DESTROY)
            System.exit(0);
        else
            return super.handleEvent(evt);
        return true;
    }

    public boolean action(Event evt, Object arg) {
        if(arg.equals("go")) {
            showToeDialog();
        }
        else
            return super.action(evt, arg);
        return true;
    }

    private void showToeDialog() {
        Dialog d = new ToeDialog(
        this,
                Integer.parseInt(tfRows.getText()),
                Integer.parseInt(tfColumns.getText()));
        d.show();
    }

    public static void main(String args[]) {
        Frame f = new ToeGame();
        f.resize(200,100);
        f.show();
    }

}

/**
 * from thinking in Java.
 *
 * The ToeButton class keeps a handle to its parent,
 * which must be of type ToeDialog. As before(just like before),
 * this introduces high coupling because a ToeButton can only
 * be used with a ToeDialog but it solves a
 * number of problems, and in truth it doesn’t
 * seem like such a bad solution because there’s no other
 * kind of dialog that’s keeping track of whose turn it is.
 * Of course, you can take another approach which
 * is to make ToeDialog. turn a static member of ToeButton,
 * which eliminates the coupling, but prevents
 * you from having more than one ToeDialog
 * at a time (more than one that works properly, anyway).
 *
 * The paint( ) method is concerned with the graphics:
 * drawing the square around the button and
 * drawing the “x” or the “o.” This is full of
 * tedious calculations, but it’s straightforward.
 *
 * A mouse click is captured by the overridden
 * mouseDown( ) method, which first checks to
 * see if the button has anything written on it.
 * If not, the parent
 * window is queried to find out whose turn it is and
 * that is used to establish the state of the button.
 * Note that the button then reaches back into the parent
 * and changes the turn. If the button is already
 * displaying an “x” or an “o” then that is flopped.
 *
 * You can see in these calculations the convenient
 * use of the ternary(三元) if-else described in
 * Chapter 3. After a  button state change, the button is repainted.
 *
 * The constructor for ToeDialog is quite simple:
 * it adds into a GridLayout as many buttons as you
 * request, then resizes it for 50 pixels on a side
 * for each button (if you don’t resize a Window, it won’t
 * show up!).
 *
 *
 * Notice that handleEvent( ) just calls dispose( )
 * for a WINDOW_DESTROY so the whole
 * application doesn’t go away.
 *
 * ToeTest sets up the whole application by creating
 * the TextFields (for inputting the rows and columns
 * of the button grid) and the “go” button.
 * You’ll see in action( ) that this program uses the
 * less-desirable “string match” technique for
 * detecting the button press (make sure you get spelling
 * and capitalization right!). When the button is pressed,
 * the data in the TextFields must be fetched and,
 * since they are in String form, turned into ints using
 * the static Integer.parseInt( ) method.
 *
 * Once the Dialog is created,
 * the show( ) method must be called to
 * display and activate it.
 *
 * You’ll note that the ToeDialog object
 * is assigned to a Dialog handle d.
 * This is an example of upcasting,
 *
 * although it really doesn’t make much
 * difference here since all that’s happening
 * is the show( ) method
 * is called. However, if you wanted to
 * call some method that only existed in
 * ToeDialog you would want
 * to assign to a ToeDialog handle and
 * not lose the information in an upcast.
 */