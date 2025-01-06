package code.java.view.tree.model;

import code.java.clazz.field.FieldsKeeper;

import java.lang.reflect.*;
import java.util.*;
import javax.swing.tree.*;

import static code.java.utils.LU.print;

/**
 * From Core Java 2.
 * Modify a lot.
 * <p>
 * <p>
 * 对象的字段简单树模型
 *
 * <p>
 * This tree model describes
 * the tree structure of a Java
 * object. Children are the objects
 * that are stored in instance variables.
 */
public class ObjectFieldTreeModel extends BaseTreeModel {

    private FieldsKeeper root;

   /* public ObjectTreeModel() {
        this(null);
    }

    public ObjectTreeModel(Variable root) {
        this.root = root;
    }*/

    /**
     * Sets the root to a given variable.
     *
     * @param v the variable that is being described by this tree
     */
    public void setRoot(FieldsKeeper v) {
        FieldsKeeper oldRoot = v;
        root = v;
        fireTreeStructureChanged(oldRoot);
    }

    public Object getRoot() {
        return root;
    }

    public int getChildCount(Object parent) {
        return ((FieldsKeeper) parent).getNonStaticFieldList().size();
    }

    public Object getChild(Object parent, int index) {
        List<Field> fieldList = ((FieldsKeeper) parent).getNonStaticFieldList();
        Field field = fieldList.get(index);
        Object parentValue = ((FieldsKeeper) parent).getValue();
        print("type = " + field.getType());
        try {
            return new FieldsKeeper(
                    field.getType(),
                    field.getName(),
                    field.get(parentValue)
            );
        } catch (IllegalAccessException e) {
        }
        return null;
    }

    public int getIndexOfChild(Object parent, Object child) {
        int n = getChildCount(parent);
        for (int i = 0; i < n; i++) {
            if (getChild(parent, i).equals(child)) {
                return i;
            }
        }
        return -1;
    }

    public boolean isLeaf(Object node) {
        return getChildCount(node) == 0;
    }

    public void valueForPathChanged(TreePath path, Object newValue) {
    }

}
