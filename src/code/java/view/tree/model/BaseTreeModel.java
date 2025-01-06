package code.java.view.tree.model;

import javax.swing.event.EventListenerList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;

//实现通用功能：添加、删除、通知listeners.
public abstract class BaseTreeModel implements TreeModel {

    private EventListenerList listenerList = new EventListenerList();

    @Override
    public void addTreeModelListener(TreeModelListener l) {
        listenerList.add(TreeModelListener.class, l);
    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {
        listenerList.remove(TreeModelListener.class, l);
    }

    protected void fireTreeStructureChanged(Object oldRoot) {
        TreeModelEvent event = new TreeModelEvent(this, new Object[]{oldRoot});
        for (TreeModelListener l : listenerList.getListeners(TreeModelListener.class))
            l.treeStructureChanged(event);
    }

}
