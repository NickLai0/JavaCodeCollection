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