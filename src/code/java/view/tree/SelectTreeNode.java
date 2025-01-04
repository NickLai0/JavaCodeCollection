package code.java.view.tree;

import code.java.utils.FrameUtils;
import code.java.utils.LU;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeSelectionModel;

import static code.java.utils.LU.println;
//Demonstrate listening select node action.
public class SelectTreeNode extends SimpleJTree {

    JTextArea textArea;

   /*
   空参构造是默认生成
   public SelectTreeNode() {
        //super();默认调用
    }
    */

    @Override
    protected void setupView() {
        // 设置只能选择一个TreePath
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        // 添加监听树节点选择事件的监听器
        // 当JTree中被选择节点发生改变时，将触发该方法
        tree.addTreeSelectionListener(new TreeSelectionHandler());
        //设置是否显示根节点的“展开/折叠”图标,默认是false
        tree.setShowsRootHandles(true);
        //设置根节点是否可见,默认是true
        tree.setRootVisible(true);

        Box horizontalBox = new Box(BoxLayout.X_AXIS);
        horizontalBox.add(new JScrollPane(tree));
        //setupView是在构造方法里，所以new JTextArea放这里才正确。
        horizontalBox.add(new JScrollPane(textArea = new JTextArea(5, 20)));
        add(horizontalBox);

    }

    class TreeSelectionHandler implements TreeSelectionListener {

        @Override
        public void valueChanged(TreeSelectionEvent e) {
            if (e.getOldLeadSelectionPath() != null) {
                textArea.append("原选中的节点路径：" + e.getOldLeadSelectionPath() + "\n");
            }
            textArea.append("新选中的节点路径：" + e.getNewLeadSelectionPath() + "\n");
            println("text = " + textArea.getText());
        }
    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(SelectTreeNode.class)
                .setTitle("监控选择的节点，点击可见效。");
    }

}

