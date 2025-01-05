package code.java.view.tree.jTree;

import code.java.utils.FrameUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.tree.*;

/**
 * 演示JTree添加兄弟和子节点、删除节点。
 * 这个案例和EditTreeNode好像，它源自
 * 疯狂Java讲义。但它多了拖拽内容。
 * <p>
 * From Core Java  2
 * Modify a lot.
 * <p>
 * A frame with a tree and buttons to edit the tree.
 */
public class TreeEditFrame extends SimpleTreeFrame {

    private DefaultTreeModel model;

    JButton addSiblingButton = new JButton("Add Sibling");
    JButton addChildButton = new JButton("Add Child");
    JButton deleteButton = new JButton("Delete");

    public TreeEditFrame() {
        super();
        // DefaultTreeModel已经在super()-》new JTree(root)构造方法里创建了。
        // model = new DefaultTreeModel(root);
        // tree = new JTree(model);
        setupSouth();
        model = (DefaultTreeModel) tree.getModel();
        tree.setEditable(true);//设置节点可编辑
        setupListener();
    }

    private void setupListener() {
        addSiblingButton.addActionListener(new AddSiblingAction());
        addChildButton.addActionListener(new AddChildAction());
        deleteButton.addActionListener(new DeleteAction());
    }


    /**
     * Makes the buttons to add a sibling,
     * add a child, and delete a node.
     */
    public void setupSouth() {
        var panel = new JPanel();
        panel.add(addSiblingButton);
        panel.add(addChildButton);
        panel.add(deleteButton);
        add(panel, BorderLayout.SOUTH);
    }

    //添加sibling action
    class AddSiblingAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            var selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (selectedNode == null) return;
            var parent = (DefaultMutableTreeNode) selectedNode.getParent();
            if (parent == null) return;
            var newNode = new DefaultMutableTreeNode("New");
            int selectedIndex = parent.getIndex(selectedNode);
            model.insertNodeInto(newNode, parent, selectedIndex + 1);
            // now display new node
            TreeNode[] nodes = model.getPathToRoot(newNode);
            var path = new TreePath(nodes);
            //展开显示新增加的node
            tree.scrollPathToVisible(path);
        }
    }

    //添加child action
    class AddChildAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            var selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (selectedNode == null) return;
            var newNode = new DefaultMutableTreeNode("New");
            model.insertNodeInto(newNode, selectedNode, selectedNode.getChildCount());
            // now display new node
            TreeNode[] nodes = model.getPathToRoot(newNode);
            var path = new TreePath(nodes);
            tree.scrollPathToVisible(path);
        }
    }

    class DeleteAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            var selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if (selectedNode != null && selectedNode.getParent() != null)
                model.removeNodeFromParent(selectedNode);
        }
    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(TreeEditFrame.class);
    }
}
