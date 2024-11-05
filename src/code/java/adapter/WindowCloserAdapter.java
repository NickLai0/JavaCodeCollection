package code.java.adapter;

import code.java.view.dialog.HintDialog;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// To close the application:
public class WindowCloserAdapter extends WindowAdapter {
    public void windowClosing(WindowEvent e) {
        /*
        不知为何，dialog显示代码走了，但是窗口还是消失了。
        if (e.getSource() instanceof Frame) {
            new HintDialog(
                    (Frame) e.getSource(),
                    "Window would close after click OK button."
            ).show();
        }*/
        System.exit(0);
    }
}