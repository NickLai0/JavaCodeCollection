package code.java.view.dialog;

//: FileDialogTest.java
// Demonstration of File dialog boxes
//I modified the source code, it would be better.

import java.awt.*;

public class FileDialogTest extends Frame {

    TextField tfFilename = new TextField();
    TextField tfDir = new TextField();
    Button open = new Button("Open");
    Button save = new Button("Save");

    public FileDialogTest() {
        setTitle("File Dialog Test");

        tfDir.setEditable(false);
        tfFilename.setEditable(false);

        Panel p = new Panel();
        //Just like a list.
        p.setLayout(new GridLayout(2, 1));
        p.add(tfFilename);
        p.add(tfDir);
        add("North", p);

        p = new Panel();
        p.setLayout(new FlowLayout());
        p.add(open);
        p.add(save);
        add("South", p);
    }

    public boolean handleEvent(Event evt) {
        if (evt.id == Event.WINDOW_DESTROY)
            System.exit(0);
        else
            return super.handleEvent(evt);
        return true;
    }

    public boolean action(Event evt, Object arg) {
        if (evt.target.equals(open)) {

            // Two arguments, defaults to open file:
            FileDialog d = new FileDialog(this,
                    "What file do you want to open?");
            d.setFile("*.java");
            // Current directory
            d.setDirectory(".");
            /*
             * the show( ) command doesn't return until
             * the dialog is closed. This is different
             * with Android.
             */
            d.show();

            boolean fileSelected = d.getFile() != null;
            if (fileSelected) {
                tfFilename.setText(d.getFile());
                tfDir.setText(d.getDirectory());
            }

        } else if (evt.target.equals(save)) {
            FileDialog d = new FileDialog(this,
                    "What file do you want to save?",
                    //How to understand this Save?
                    FileDialog.SAVE);
            d.setFile("*.java");
            d.setDirectory(".");
            d.show();
            if (d.getFile() != null) {
                tfFilename.setText(d.getFile());
                tfDir.setText(d.getDirectory());
            }
        } else
            return super.action(evt, arg);
        return true;
    }

    public static void main(String args[]) {
        Frame f = new FileDialogTest();
        f.resize(250, 110);
        f.show();
    }
}

/**
 * from "Thinking in Jav"
 *
 *
 * For an “open file” dialog, you use the constructor
 * that takes two arguments; the first is the parent
 * window handle and the second is the title for the
 * title bar of the FileDialog. The method setFile( )
 * provides an initial file name – presumably(推测)
 * the native OS supports wildcards, so in this example
 * all the .java files will initially be displayed.
 *
 *
 * The setDirectory( ) method chooses the directory in
 * which the file selection will begin (generally the
 * OS allows the user to change directories).
 *
 * Now the behavior departs from a normal dialog,
 * which is gone when it closes (the show( ) command
 * doesn't return until the dialog is closed).
 *
 * By some magic, when a FileDialog is closed by the user its
 * data still exists, so you can read it – as long as the user didn’t “cancel” out of the dialog, in which case
 * the handle becomes null. You can see the test for null followed by the calls to getFile( ) and
 *
 * getDirectory( ), the results of which are displayed in the TextFields.
 * The button for saving works the same way, except that it uses a different constructor for the
 *
 * FileDialog. This constructor takes three arguments, and the third argument must be either
 *
 * FileDialog.SAVE or FileDialog.OPEN.
 */