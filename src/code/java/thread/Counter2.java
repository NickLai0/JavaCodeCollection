package code.java.thread;
//From Thinking in Java.
//点击start按钮，启动新线程计数，
//: Counter2.java
// A responsive user interface with threads
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
class SeparateProcess extends Thread {
    int count = 0;
    Counter2 c2;
    boolean runFlag = true;
    SeparateProcess(Counter2 c2) {
        this.c2 = c2;
        start();
    }
    public void run() {
        while (true) {
            try {
                sleep(100);
            } catch (InterruptedException e){}
            if(runFlag)
                c2.t.setText(Integer.toString(count++));
        }
    }
}
public class Counter2 extends Applet {
    SeparateProcess sp = null;
    Button
            onOff = new Button("Toggle"),
            start = new Button("Start");
    TextField t = new TextField(10);
    public void init() {
        add(t);
        start.addActionListener(new StartL());
        add(start);
        onOff.addActionListener(new OnOffL());
        add(onOff);
    }
    class StartL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(sp == null)
                sp = new SeparateProcess(Counter2.this);
        }

    }
    class OnOffL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if(sp != null)
                sp.runFlag = !sp.runFlag;
        }
    }
    static class WL extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }
    public static void main(String args[]) {
        Counter2 applet = new Counter2();
        Frame aFrame = new Frame("Counter2");
        aFrame.addWindowListener(new WL());
        aFrame.add(applet, BorderLayout.CENTER);
        aFrame.setSize(300,200);
        applet.init();
        applet.start();
        aFrame.setVisible(true);
    }
} ///:~
