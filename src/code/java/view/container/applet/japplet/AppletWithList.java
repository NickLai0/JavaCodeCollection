package code.java.view.container.applet.japplet;
//awt被淘汰多年了，请用swing库。

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static code.java.utils.LU.println;

public class AppletWithList extends JApplet {

    String flavors[] = {
            "Chocolate",
            "Strawberry",
            "Vanilla Fudge Swirl",
            "Mint Chip",
            "Mocha Almond Fudge",
            "Rum Raisin",
            "Praline Cream",
            "Mud Pie"
    };

    JList lst = new JList(flavors);
    //滚动显示文本
    JTextArea t = new JTextArea(flavors.length/*行数*/, 30/*列数*/);
    //添加新item的按钮
    JButton b = new JButton("add new item");

    public void init() {
        initView();
        initListener();
    }


    private void initView() {
        /*
         * awt的Applet默认为BorderLayout,
         * JApplet一样，但add控件后，
         * 新的会遮住老的。
         *
         * 若设为FlowLayout
         * 控件则从左到右，从上到下，
         * 均匀地散布在panel上。
         */
        setLayout(new FlowLayout());
        add(t);
        add(lst);
        add(b);
        /*
         * ListSelectionModel.MULTIPLE_INTERVAL_SELECTION:
         * select one or more contiguous(相邻的)
         * ranges of indices at a time.
         */
        lst.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        t.setEditable(false);
    }

    private void initListener() {
        lst.addListSelectionListener(mListSelectionListener);
        b.addActionListener(mActionListener);
    }

    @Override
    public void stop() {
        lst.removeListSelectionListener(mListSelectionListener);
        b.removeActionListener(mActionListener);
    }

    /**
     * List的item被选择后的事件回调
     */
    private ListSelectionListener mListSelectionListener = new ListSelectionListener() {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            List selectedValues = lst.getSelectedValuesList();
            println("selectedValues = " + selectedValues);
            t.setText("");
            for (int i = 0; i < selectedValues.size(); i++) {
                t.append(selectedValues.get(i) + "\n");
            }
        }
    };
    /**
     * JButton被点击后的回调。
     */
    private ActionListener mActionListener = new ActionListener() {

        private List strList;
        private int newItemIndex;

        @Override
        public void actionPerformed(ActionEvent e) {
            if (strList == null) {
                strList = new ArrayList();
                for (String flavor : flavors) {
                    strList.add(flavor);
                }
            }
            strList.add("add new item "+(++newItemIndex));
            lst.setListData(strList.toArray());
        }
    };

    public static void main(String[] args) {
        testAppletWithList();
    }

    private static void testAppletWithList() {
        JApplet applet = new AppletWithList();

        // JFrame can be a window and the container of JApplet.
        // Use JFrame to test Applet is really convenient.
        // create JFrame object.
        JFrame frame = new JFrame("AppletWithList");

        //Configurates JFrame object.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(applet);
        frame.setSize(800, 500);

        //Calls applet methods.
        applet.init();
        applet.start();

        frame.setVisible(true);
    }

}
