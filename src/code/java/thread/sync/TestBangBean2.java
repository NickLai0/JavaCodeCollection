package code.java.thread.sync;

import code.java.adapter.ActionNamePrinterAdapter;
import code.java.adapter.WL;

import java.awt.*;

public class TestBangBean2 extends Frame {
    BangBean2 bb = new BangBean2();

    public TestBangBean2() {
        setTitle("BangBean2 Test");
        addWindowListener(new WL());
        setSize(300, 300);
        add(bb, BorderLayout.CENTER);
        bb.addActionListener(new ActionNamePrinterAdapter("Action 1"));
        bb.addActionListener(new ActionNamePrinterAdapter("Action 2"));
    }


    public static void main(String args[]) {
        Frame f = new TestBangBean2();
        f.setVisible(true);
    }
} /// :~
