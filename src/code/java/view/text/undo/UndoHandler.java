package code.java.view.text.undo;

import javax.swing.undo.UndoableEdit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import static code.java.utils.LU.println;

public class UndoHandler extends KeyAdapter {
    List<UndoableEdit> undoList;

    public UndoHandler(List<UndoableEdit> list) {
        this.undoList = list;
    }

    public void keyTyped(KeyEvent e) { // 为Ctrl+Z添加监听器
//        println("UndoHandler#keyTyped->");
        //println(e.getKeyChar());
        if (isUndoKey(e) && undoList.size() > 0) {
            println("UndoHandler#keyTyped-> it is undo key and there are undo edits.");
            UndoableEdit undoableEdit = undoList.remove(undoList.size() - 1);
            // 移出最后一个可撤销操作，并撤销
            undoableEdit.undo();
        }
    }

    private static boolean isUndoKey(KeyEvent e) {
        // 如果按键是Ctrl + Z或Ctrl + z
        return e.getKeyChar() == 26;
    }

}