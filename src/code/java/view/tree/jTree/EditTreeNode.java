package code.java.view.tree.jTree;

import code.java.utils.FrameUtils;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.*;

/**
 * 演示树节点的移动、添加兄弟和子节点、删除节点
 */
public class EditTreeNode extends SimpleJTree {

    JButton addSiblingButton;
    JButton addChildButton;
    JButton deleteButton;
    JButton editButton;

    DefaultTreeModel model;

    // 定义需要被拖动的TreePath
    TreePath movePath;

    public EditTreeNode() {
        super();//super()->setupView()
        setupData();
        setupListener();
    }

    private void setupData() {
        // 获取JTree对应的TreeModel对象
        model = (DefaultTreeModel) tree.getModel();
        // 设置JTree（双击节点）可编辑
        tree.setEditable(true);
    }

    private void setupListener() {
        // 为JTree添加鼠标监听器
        tree.addMouseListener(MoveNodeHandler);
        addSiblingButton.addActionListener(new AddBrotherNodeAction());
        addChildButton.addActionListener(new AddSubNodeAction());
        deleteButton.addActionListener(new DeleteNodeAction());
        editButton.addActionListener(new EditNodeAction());
    }

    //EditJTree2()->super()->setupView()
    @Override
    protected void setupView() {
        addSiblingButton = new JButton("添加兄弟节点");
        addChildButton = new JButton("添加子节点");
        deleteButton = new JButton("删除节点");
        editButton = new JButton("编辑当前节点");

        JPanel panel = new JPanel();
        panel.add(addSiblingButton);
        panel.add(addChildButton);
        panel.add(deleteButton);
        panel.add(editButton);

        add(new JScrollPane(tree), BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
    }

    //鼠标激动节点处理者
    MouseListener MoveNodeHandler = new MouseAdapter() {
        // 按下鼠标时获得被拖动的节点
        public void mousePressed(MouseEvent e) {
            // 如果需要唯一确定某个节点，必须通过TreePath来获取。
            TreePath tp = tree.getPathForLocation(e.getX(), e.getY());
            if (tp != null) {
                movePath = tp;
            }
        }

        // 鼠标松开时获得需要拖到哪个父节点
        public void mouseReleased(MouseEvent e) {
            // 根据鼠标松开时的TreePath来获取TreePath
            TreePath tp = tree.getPathForLocation(e.getX(), e.getY());
            if (tp == null || movePath == null) {
                return;
            }

            // 阻止向子节点拖动
            if (movePath.isDescendant(tp) && movePath != tp) {
                JOptionPane.showMessageDialog(EditTreeNode.this,
                        "目标节点是被移动节点的子节点，无法移动！",
                        "非法操作", JOptionPane.ERROR_MESSAGE);
            } else if (movePath != tp) { // 既不是向子节点移动，而且鼠标按下、松开的不是同一个节点
                // add方法先将该节点从原父节下删除，再添加到新父节点下
                ((DefaultMutableTreeNode) tp.getLastPathComponent())
                        .add((DefaultMutableTreeNode) movePath
                                .getLastPathComponent());
                movePath = null;
                tree.updateUI();
            }
        }
    };

    // 实现添加兄弟节点
    class AddBrotherNodeAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 获取选中节点
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)
                    tree.getLastSelectedPathComponent();
            // 如果节点为空，直接返回
            if (selectedNode == null) return;
            // 获取该选中节点的父节点
            DefaultMutableTreeNode parent = (DefaultMutableTreeNode)
                    selectedNode.getParent();
            // 如果父节点为空，直接返回
            if (parent == null) return;
            // 创建一个新节点
            DefaultMutableTreeNode newNode = new
                    DefaultMutableTreeNode("新节点");
            // 获取选中节点的选中索引
            int selectedIndex = parent.getIndex(selectedNode);
            // 在选中位置插入新节点
            model.insertNodeInto(newNode, parent, selectedIndex + 1);
            scrollToNode(newNode);
        }
    }

    private void scrollToNode(DefaultMutableTreeNode newNode) {
        // --------下面代码实现显示新节点（自动展开父节点）-------
        // 获取从根节点到新节点的所有节点
        TreeNode[] nodes = model.getPathToRoot(newNode);
        // 使用指定的节点数组来创建TreePath
        TreePath path = new TreePath(nodes);
        // 显示指定TreePath
        tree.scrollPathToVisible(path);
    }

    // 实现添加子节点
    class AddSubNodeAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 获取选中节点
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)
                    tree.getLastSelectedPathComponent();
            // 如果节点为空，直接返回
            if (selectedNode == null) return;
            // 创建一个新节点
            DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("新节点");
            // 通过model来添加新节点，则无须通过调用JTree的updateUI方法
            // model.insertNodeInto(newNode, selectedNode , selectedNode.getChildCount());
            // 通过节点添加新节点，则需要调用tree的updateUI方法
            selectedNode.add(newNode);
            // --------下面代码实现显示新节点（自动展开父节点）-------
            scrollToNode(newNode);
            tree.updateUI();
        }
    }

    // 实现删除节点
    class DeleteNodeAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)
                    tree.getLastSelectedPathComponent();
            if (selectedNode != null && selectedNode.getParent() != null) {
                // 删除指定节点
                model.removeNodeFromParent(selectedNode);
            }
        }
    }

    // 实现编辑节点
    class EditNodeAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TreePath selectedPath = tree.getSelectionPath();
            if (selectedPath != null) {
                // 编辑选中节点（回车后才真正更改）
                tree.startEditingAtPath(selectedPath);
            }
        }
    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(EditTreeNode.class)
                .setTitle("可编辑节点的树");
    }

}
