package code.java.view.tree;

import code.java.utils.FrameUtils;

import javax.swing.*;
import javax.swing.tree.*;

/**
 * Modify a lot.
 * <p>
 * Description:
 * 网站: <a href="http://www.crazyit.org">疯狂Java联盟</a><br>
 * Copyright (C), 2001-2018, Yeeku.H.Lee<br>
 * This program is protected by copyright laws.<br>
 * Program Name:<br>
 * Date:<br>
 *
 * @author Yeeku.H.Lee kongyeeku@163.com
 * @version 1.0
 */
public class SimpleJTree extends JFrame {
    JTree tree;

    DefaultMutableTreeNode root = new DefaultMutableTreeNode("中国");
    DefaultMutableTreeNode guangdong = new DefaultMutableTreeNode("广东");
    DefaultMutableTreeNode guangxi = new DefaultMutableTreeNode("广西");
    DefaultMutableTreeNode foshan = new DefaultMutableTreeNode("佛山");
    DefaultMutableTreeNode shantou = new DefaultMutableTreeNode("汕头");
    DefaultMutableTreeNode guilin = new DefaultMutableTreeNode("桂林");
    DefaultMutableTreeNode nanning = new DefaultMutableTreeNode("南宁");

    public SimpleJTree() {
        setupGDProvinceNode();
        setupGXProvinceNode();
        root.add(guangdong);
        root.add(guangxi);
        tree = new JTree(root);
        // 以根节点创建树
        setupView();
        pack();
    }

    //子类可重写来实现视图个性化
    protected void setupView() {
        add(new JScrollPane(tree));
    }

    private void setupGXProvinceNode() {
        guangxi.add(guilin);
        guangxi.add(nanning);
    }

    private void setupGDProvinceNode() {
        guangdong.add(foshan);
        guangdong.add(shantou);
    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(SimpleJTree.class)
                .setTitle("简单树");
    }

}
