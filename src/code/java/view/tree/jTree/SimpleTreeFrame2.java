package code.java.view.tree.jTree;

import code.java.utils.FrameUtils;

/**
 * 演示tree的各个配置方法：
 * JTree.lineStyle属性
 * tree.setShowsRootHandles(true);
 * tree.setRootVisible(false);
 * stateCalifornia.setAllowsChildren(false);
 */
public class SimpleTreeFrame2 extends SimpleTreeFrame {
    public SimpleTreeFrame2() {
        super();//本来就默认调用，这里显性调用，好理解。
        //去除Tree的Node层级线
        //tree.putClientProperty("JTree.lineStyle", "None");
        //去Tree的Node层级线(似乎是默认)
        //tree.putClientProperty("JTree.lineStyle", "Angled");
        //用横线来区分节点块
        tree.putClientProperty("JTree.lineStyle", "Horizontal");

        //是否显示根节点的手柄，其实不显示更好看。
//        tree.setShowsRootHandles(true);

        //是否显示根节点，有时候不显示更好。
        //tree.setRootVisible(false);

        //禁止本节点下面有节点
        //stateCalifornia.setAllowsChildren(false);

//        mod.setAsksAllowsChildren(true);
    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(SimpleTreeFrame2.class);
    }

}
