package code.java.view.text.undo;

import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoableEdit;
import java.util.LinkedList;

import static code.java.utils.LU.println;

public class UndoMonitor implements UndoableEditListener {
    // 最多允许撤销多少次
    final int UNDO_LIMIT;

    // 保存撤销操作的List对象
    LinkedList<UndoableEdit> undoList = new LinkedList<>();

    public UndoMonitor() {
        this(20);
    }

    public UndoMonitor(int undoLimit) {
        UNDO_LIMIT = undoLimit;
    }

    @Override
    public void undoableEditHappened(UndoableEditEvent e) { // 每次发生可撤销操作都会触发该代码块      // ①
        //println("UndoMonitor#undoableEditHappened->");
        UndoableEdit edit = e.getEdit();
        if (edit.canUndo() && undoList.size() < UNDO_LIMIT) {
            println("UndoMonitor#undoableEditHappened-> can undo, so add undo edit.");
            // 将撤销操作装入List内
            undoList.add(edit);
        } else if (edit.canUndo() && undoList.size() >= UNDO_LIMIT) { // 已经达到了最大撤销次数
            println("UndoMonitor#undoableEditHappened-> beyond undo limit, remove first and then add the new one.");
            // 弹出第一个撤销操作
            undoList.removeFirst();
            // 将撤销操作装入List内
            undoList.add(edit);
        }
    }

    public LinkedList<UndoableEdit> getUndoList() {
        return undoList;
    }

}