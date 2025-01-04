package code.java.view.tree.node;

import code.java.utils.FrameUtils;
import code.java.view.tree.renderer.TreeImageCellRenderer;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

/**
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

public class CustomTreeNode extends JFrame {
    JTree tree;

    // 定义几个初始节点
    DefaultMutableTreeNode friends = new DefaultMutableTreeNode("我的好友");
    DefaultMutableTreeNode qingzhao = new DefaultMutableTreeNode("李清照");
    DefaultMutableTreeNode suge = new DefaultMutableTreeNode("苏格拉底");
    DefaultMutableTreeNode libai = new DefaultMutableTreeNode("李白");
    DefaultMutableTreeNode nongyu = new DefaultMutableTreeNode("弄玉");
    DefaultMutableTreeNode hutou = new DefaultMutableTreeNode("虎头");

    public void CustomTreeNode() {
        initFriendNodes();
        // 以根节点创建树
        tree = new JTree(friends);
        // 设置是否显示根节点的“展开/折叠”图标,默认是false
        tree.setShowsRootHandles(true);
        // 设置节点是否可见,默认是true
        tree.setRootVisible(true);
        // 设置使用定制的节点绘制器
        tree.setCellRenderer(new TreeImageCellRenderer());
        add(new JScrollPane(tree));

        pack();
    }

    private void initFriendNodes() {
        // 通过add()方法建立树节点之间的父子关系
        friends.add(qingzhao);
        friends.add(suge);
        friends.add(libai);
        friends.add(nongyu);
        friends.add(hutou);
    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(CustomTreeNode.class)
                .setTitle("定制树的节点");
    }

}
