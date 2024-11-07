package code.java.view.menu;
/*
From Thinking in Java.

the clipboard

Java 1.1 supports limited operations with the system clipboard.
You can copy String objects to the
clipboard as text, and you can paste text from the clipboard
into String objects. Of course, the
clipboard is designed to hold any type of data, but how this
data is represented on the clipboard is up
to the program doing the cutting and pasting. Although it
currently only supports string data, the Java
clipboard API provides for extensibility through the concept
of a “flavor.” When data comes off the
clipboard, it has an associated set of flavors that it can
be converted to (for example, a graph might be
represented as a string of numbers or as an image) and you
can see if that particular clipboard data
supports the flavor you’re interested in.
The following program is a simple demonstration of cut, copy
and paste with String data in a TextArea.
One thing you’ll notice is that the keyboard sequences you
normally use for cutting, copying and
pasting also work. But if you look at any TextField or
TextArea in any other program you’ll find they
also automatically support the clipboard key sequences.
This example simply adds programmatic
control of the clipboard, and you could use these
techniques if you want to capture clipboard text into
some non-TextComponent.

*/
//: CutAndPaste.java
// Using the clipboard from Java 1.1

import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.*;

public class CutAndPaste extends Frame {
    MenuBar mb = new MenuBar();
    Menu edit = new Menu("Edit");
    MenuItem
            cut = new MenuItem("Cut"),
            copy = new MenuItem("Copy"),
            paste = new MenuItem("Paste");
    TextArea text = new TextArea(20, 20);
    Clipboard clipbd =
            getToolkit().getSystemClipboard();

    public CutAndPaste() {
        cut.addActionListener(new CutL());
        copy.addActionListener(new CopyL());
        paste.addActionListener(new PasteL());
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        mb.add(edit);
        setMenuBar(mb);
        add(text, BorderLayout.CENTER);
    }

    class CopyL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String selection = text.getSelectedText();
            StringSelection clipString =
                    new StringSelection(selection);
            clipbd.setContents(clipString, clipString);
        }
    }

    class CutL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String selection = text.getSelectedText();

            StringSelection clipString =
                    new StringSelection(selection);
            clipbd.setContents(clipString, clipString);
            text.replaceRange("",
                    text.getSelectionStart(),
                    text.getSelectionEnd());
        }
    }

    class PasteL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Transferable clipData =
                    clipbd.getContents(CutAndPaste.this);
            try {
                String clipString =
                        (String) clipData.
                                getTransferData(
                                        DataFlavor.stringFlavor);
                text.replaceRange(clipString,
                        text.getSelectionStart(),
                        text.getSelectionEnd());
            } catch (Exception ex) {
                System.out.println("not String flavor");
            }
        }
    }

    static class WL extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }

    public static void main(String args[]) {
        CutAndPaste cp = new CutAndPaste();
        cp.setSize(300, 200);
        cp.setVisible(true);
        cp.addWindowListener(new WL());
    }
}
/*
The creation and addition of the menu and TextArea
should by now seem a pedestrian activity. What’s
different is the creation of the Clipboard field clipbd,
which is done through the Toolkit.
All the action takes place in the listeners.
The CopyL and CutL listeners are the same except for the
last line of CutL, which erases the line that’s been copied.
The special two lines are the creation of a
StringSelection object from the String, and the call to
setContents( ) with this StringSelection.
That’s all there is to putting a String on the clipboard.
In PasteL data is pulled off the clipboard using getContents( ).
What comes back is a fairly anonymous
Transferable object, and you don’t really know what it contains.
One way to find out is to call

getTransferDataFlavors( ), which returns an array of DataFlavor
objects indicating which flavors are
supported by this particular object.
You can also ask it directly with isDataFlavorSupported( ), passing
in the flavor you’re interested in.
Here, however, the bold approach is taken: getTransferData( ) is
called assuming that the contents supports the String flavor,
and if it doesn’t the problem is sorted out
in the exception handler.
In the future you can expect more data flavors to be supported.
*/
