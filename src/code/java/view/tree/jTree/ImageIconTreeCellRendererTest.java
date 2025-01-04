package code.java.view.tree.jTree;

import code.java.utils.FrameUtils;
import code.java.view.tree.data.NodeData;
import code.java.view.tree.renderer.ImageIconRenderer;

import javax.swing.*;
import javax.swing.tree.*;

/**
 * Modified a lot.
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
public class ImageIconTreeCellRendererTest extends JFrame {

    JTree tree;

    // 定义几个初始节点
    DefaultMutableTreeNode root = new DefaultMutableTreeNode(new NodeData(ImageIconRenderer.TYPE_ROOT, "数据库导航"));
    DefaultMutableTreeNode salaryDb = new DefaultMutableTreeNode(new NodeData(ImageIconRenderer.TYPE_DATABASE, "公司工资数据库"));
    DefaultMutableTreeNode customerDb = new DefaultMutableTreeNode(new NodeData(ImageIconRenderer.TYPE_DATABASE, "公司客户数据库"));

    // 定义salaryDb的两个子节点
    DefaultMutableTreeNode employee = new DefaultMutableTreeNode(new NodeData(ImageIconRenderer.TYPE_TABLE, "员工表"));
    DefaultMutableTreeNode attend = new DefaultMutableTreeNode(new NodeData(ImageIconRenderer.TYPE_TABLE, "考勤表"));

    // 定义customerDb的一个子节点
    DefaultMutableTreeNode contact = new DefaultMutableTreeNode(new NodeData(ImageIconRenderer.TYPE_TABLE, "联系方式表"));

    // 定义employee的三个子节点
    DefaultMutableTreeNode employee1 = new DefaultMutableTreeNode(new NodeData(ImageIconRenderer.TYPE_COLUMN, "员工1"));
    DefaultMutableTreeNode employee2 = new DefaultMutableTreeNode(new NodeData(ImageIconRenderer.TYPE_COLUMN, "员工2"));
    DefaultMutableTreeNode employee3 = new DefaultMutableTreeNode(new NodeData(ImageIconRenderer.TYPE_COLUMN, "员工3"));

    public ImageIconTreeCellRendererTest() {
        setSize(300,300);
        setupRoot();
        setupTree();
        add(new JScrollPane(tree));
        useWindowsUIStyle();
    }

    private void useWindowsUIStyle() {
        try {
            // 设置使用Windows风格外观
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception ex) {
        }
        // 更新JTree的UI外观
        SwingUtilities.updateComponentTreeUI(tree);
    }

    private void setupTree() {
        // 以根节点创建树
        tree = new JTree(root);
        // 设置该JTree使用自定义的节点绘制器
        tree.setCellRenderer(new ImageIconRenderer());
        // 设置是否显示根节点的“展开/折叠”图标,默认是false
        tree.setShowsRootHandles(true);
        // 设置节点是否可见,默认是true
        tree.setRootVisible(true);
    }

    private void setupRoot() {
        setupSalaryNode();
        setupCustomerNode();
        root.add(salaryDb);
        root.add(customerDb);
    }

    private void setupCustomerNode() {
        customerDb.add(contact);
    }

    private void setupEmployeeNode() {
        employee.add(employee1);
        employee.add(employee2);
        employee.add(employee3);
    }

    private void setupSalaryNode() {
        setupEmployeeNode();
        salaryDb.add(employee);
        salaryDb.add(attend);
    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(ImageIconTreeCellRendererTest.class).setTitle("根据节点类型定义图标");
    }
}
