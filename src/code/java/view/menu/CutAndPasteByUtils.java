package code.java.view.menu;

//: CutAndPaste.java
// Using the clipboard from Java 1.1

import code.java.utils.ClipboardUtils;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CutAndPasteByUtils extends Frame {
    MenuBar mb = new MenuBar();
    Menu edit = new Menu("Edit");
    MenuItem
            cut = new MenuItem("Cut"),
            copy = new MenuItem("Copy"),
            paste = new MenuItem("Paste");
    TextArea text = new TextArea(20, 20);

    public CutAndPasteByUtils() {
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
            ClipboardUtils.copyStrToClipboard(text.getSelectedText());
        }
    }

    //剪切的本质还是复制，只是TextArea上的文字自己清除而已。
    class CutL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            ClipboardUtils.copyStrToClipboard(text.getSelectedText());
            text.replaceRange(
                    "",
                    text.getSelectionStart(),
                    text.getSelectionEnd()
            );
        }
    }

    class PasteL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String clipString = ClipboardUtils.getStrFromClipboard();
            if (clipString == null) {
                return;
            }
            text.replaceRange(clipString,
                    text.getSelectionStart(),
                    text.getSelectionEnd());
        }
    }

    static class WL extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }

    public static void main(String args[]) {
        CutAndPasteByUtils cp = new CutAndPasteByUtils();
        cp.setSize(300, 200);
        cp.setVisible(true);
        cp.addWindowListener(new WL());
    }
} /// :~
