package code.java.view.tree.renderer;


import code.java.view.tree.data.NodeData;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

import static code.java.utils.ImageUtils.nii;

public class ImageIconRenderer extends DefaultTreeCellRenderer {
    public static int TYPE_ROOT = 0;
    public static int TYPE_DATABASE = 1;
    public static int TYPE_TABLE = 2;
    public static int TYPE_COLUMN = 3;
    public static int TYPE_INDEX = 4;

    // 初始化5个图标
    ImageIcon rootIcon = nii("root.gif");
    ImageIcon databaseIcon = nii("database.gif");
    ImageIcon tableIcon = nii("table.gif");
    ImageIcon columnIcon = nii("column.gif");
    ImageIcon indexIcon = nii("index.gif");

    ImageIcon[] iiArr = {rootIcon, databaseIcon, tableIcon, columnIcon, indexIcon};

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        // 执行父类默认的节点绘制操作
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        NodeData d = (NodeData) node.getUserObject();
        if (d.nodeType < iiArr.length) {
            // 改变图标
            this.setIcon(iiArr[d.nodeType]);
        }
        return this;
    }
}