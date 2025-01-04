package code.java.view.tree;

import code.java.utils.FrameUtils;

import javax.swing.*;
import javax.swing.tree.*;

/**
 * 简单树演示
 *
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

    DefaultMutableTreeNode root = new DefaultMutableTreeNode("中国");
    DefaultMutableTreeNode guangdong = new DefaultMutableTreeNode("广东");
    DefaultMutableTreeNode guangxi = new DefaultMutableTreeNode("广西");
    DefaultMutableTreeNode foshan = new DefaultMutableTreeNode("佛山");
    DefaultMutableTreeNode shantou = new DefaultMutableTreeNode("汕头");
    DefaultMutableTreeNode guilin = new DefaultMutableTreeNode("桂林");
    DefaultMutableTreeNode nanning = new DefaultMutableTreeNode("南宁");

    public SimpleJTree() {
        // 通过add()方法建立树节点之间的父子关系
        setupGDProvinceNode();
        setupGXProvinceNode();
        root.add(guangdong);
        root.add(guangxi);
        // 以根节点创建树
        add(new JScrollPane(new JTree(root)));
        pack();
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
