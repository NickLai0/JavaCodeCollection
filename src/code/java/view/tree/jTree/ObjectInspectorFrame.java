package code.java.view.tree.jTree;

import code.java.utils.FrameUtils;
import code.java.view.tree.model.ObjectFieldTreeModel;
import code.java.clazz.field.FieldsKeeper;

import java.awt.*;
import javax.swing.*;

/**
 * This frame holds the object tree.
 */
public class ObjectInspectorFrame extends JFrame {

    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 300;

    private JTree tree;

    ObjectFieldTreeModel model = new ObjectFieldTreeModel();

    public ObjectInspectorFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        // we inspect this frame object
        model.setRoot(new FieldsKeeper(getClass(), "this", this));
        // construct and show tree
        tree = new JTree(model);
        add(new JScrollPane(tree), BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(ObjectInspectorFrame.class);
    }

}
