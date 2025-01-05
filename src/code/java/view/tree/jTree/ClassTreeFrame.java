package code.java.view.tree.jTree;

import code.java.utils.FrameUtils;
import code.java.view.tree.renderer.ClassNameTreeCellRenderer;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;

import static code.java.utils.ImageUtils.nii;
import static code.java.utils.ReflectUtils.getFieldDescription;

/**
 * From Core Java 2
 * Modify a lot.
 * <p>
 * This  frame displays the class tree,
 * a text field, and an "Add" button to add more classes
 * into the tree.
 */
public class ClassTreeFrame extends JFrame {

    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 300;

    private DefaultMutableTreeNode root = new DefaultMutableTreeNode(Object.class);

    private DefaultTreeModel model = new DefaultTreeModel(root);

    private JTree tree = new JTree(model);
    // new class names are typed into this text field

    private JTextField textField = new JTextField(20);

    // this text area holds the class description
    private JTextArea textArea = new JTextArea();

    private JButton addButton = new JButton("Add");

    ClassNameTreeCellRenderer renderer = new ClassNameTreeCellRenderer();

    /**
     * 《疯狂Java讲义》喜欢将components的处理放到init方法，
     * 而《Core Java》则喜欢放到构造函数，前者可避免super
     * class的构造还没走完-》在其内部调到child class的method，
     * 此时该method若用到了未初始化的全局变量
     * （因super构造器执行完后才能轮到sub class的字段初始化），
     * 导致一用就报空指针；但后者使用起来方便，直接new出Custom
     * Frame后，setVisible(true)即可，少调用了一个init方法。
     * <p>
     * 如何将两者优点结合在一起？在FrameUtils里加个方法：反射创建
     * 出Custom  Frame，然后再反射获取init方法，调用init后，
     * 再调用visible方法。
     */

    public void init() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        // add tree and text area
        setupCenter();
        setupSouth();
        setupRenderer();
        // the root of the class tree is Object
        // add this class to populate the tree with some data
        addTreeNodeByClass(getClass());
        setupListener();
    }

    private void setupRenderer() {
        // set up node icons
        renderer.setClosedIcon(nii("exit.png"));
        renderer.setOpenIcon(nii("open.png"));
        renderer.setLeafIcon(nii("leaf.png"));
        tree.setCellRenderer(renderer);
        tree.getSelectionModel().setSelectionMode(
                TreeSelectionModel.SINGLE_TREE_SELECTION);
    }

    private void setupListener() {
        // set up selection mode
        tree.addTreeSelectionListener(new ShowSelectedNode());
        ActionListener addClassAction = new AddClassAction();
        textField.addActionListener(addClassAction);//回车触发
        addButton.addActionListener(addClassAction);
    }

    private void setupCenter() {
        var panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        panel.add(new JScrollPane(tree));
        panel.add(new JScrollPane(textArea));
        add(panel, BorderLayout.CENTER);
    }

    /**
     * Add the text field and "Add" button to add a new class.
     */
    public void setupSouth() {
        var panel = new JPanel();
        panel.add(textField);
        panel.add(addButton);
        add(panel, BorderLayout.SOUTH);
    }

    /**
     * Finds an object in the tree.
     *
     * @param obj the object to find
     * @return the node containing the object
     * or null if the object is not present in the tree
     */
    public DefaultMutableTreeNode findNodeByUserObject(Object obj) {
        // find the node containing a user object
        Enumeration<TreeNode> e = root.breadthFirstEnumeration();
        while (e.hasMoreElements()) {
            var node = (DefaultMutableTreeNode) e.nextElement();
            //UserObject是node里面所存放的（data）object.
            if (node.getUserObject().equals(obj)) {
                return node;
            }
        }
        return null;
    }

    /**
     * 从当前class一直向上找到 root class
     * （ Object class，或则已存在的class），
     * 作为 root node，
     * 然后从root node 一路add sub node下去，直到
     * 整个类tree完成add为止。
     *
     * <p>
     * Adds a new class and any
     * parent classes that aren't
     * yet part of the tree.
     *
     * @param subClass the class to add
     * @return the newly added node
     */
    public DefaultMutableTreeNode addTreeNodeByClass(Class<?> subClass) {
        // add a new class to the tree skip non-class types
        //跳过接口和基础类型（Byte、Integer等）
        if (subClass.isInterface() || subClass.isPrimitive()) {
            return null;
        }
        // if the class is already in the tree, return its node
        DefaultMutableTreeNode node = findNodeByUserObject(subClass);
        if (node != null) {
            return node;
        }
        // class isn't present--first add class parent recursively
        Class<?> superClz = subClass.getSuperclass();
        DefaultMutableTreeNode parent;
        if (superClz == null) {
            parent = root;
        } else {
            parent = addTreeNodeByClass(superClz);
        }
        // add the class as a child to the parent
        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(subClass);
        //Insert newNode to the last index.
        model.insertNodeInto(newNode, parent, parent.getChildCount());
        // make node visible
        TreePath path = new TreePath(model.getPathToRoot(newNode));
        //expanding newNode's path.
        tree.makeVisible(path);

        return newNode;
    }

    class ShowSelectedNode implements TreeSelectionListener {

        @Override
        public void valueChanged(TreeSelectionEvent e) {
            // the user selected a different node--
            // update description
            TreePath path = tree.getSelectionPath();
            if (path == null) return;
            var selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
            Class<?> c = (Class<?>) selectedNode.getUserObject();
            textArea.setText(getFieldDescription(c));
        }

    }

    class AddClassAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // add the class whose name is in the text field
            try {
                String text = textField.getText();
                addTreeNodeByClass(Class.forName(text));
                // clear text field to indicate success
                textField.setText("");
            } catch (ClassNotFoundException exception) {
                JOptionPane.showMessageDialog(null, "Class not found");
            }
        }
    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(ClassTreeFrame.class, true)
                .setTitle("Show fields of selected class.");
    }

}
