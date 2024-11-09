package code.java.thread;
/*
From Thinking in Java.
Demonstrate multi-thread for
counting.
*/
//: Counter4.java
// If you separate your thread from the main
// class, you can have as many threads as you
// want.

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
//Ticker:跑马灯；滚动条
class Ticker extends Thread {
    Button b = new Button("Toggle");
    TextField t = new TextField(10);
    int count = 0;
    boolean runFlag = true;

    Ticker() {
        b.addActionListener(new ToggleL());
    }

    class ToggleL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            runFlag = !runFlag;
        }
    }

    public void run() {
        while (true) {
            if (runFlag)
                t.setText(Integer.toString(count++));
            try {
                sleep(100);
            } catch (InterruptedException e) {
            }
        }

    }
}

public class Counter4 extends Applet {
    Button start = new Button("Start");
    boolean started = false;
    Ticker s[];
    boolean isApplet = true;
    int size;

    public void init() {
        // Get parameter "size" from Web page:
        if (isApplet)
            size =
                    Integer.parseInt(getParameter("size"));
        s = new Ticker[size];
        for (int i = 0; i < s.length; i++) {
            s[i] = new Ticker();
            Panel p = new Panel();
            p.add(s[i].t);
            p.add(s[i].b);
            add(p);
        }
        start.addActionListener(new StartL());
        add(start);
    }

    class StartL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (!started) {
                started = true;
                for (int i = 0; i < s.length; i++)
                    s[i].start();
            }
        }
    }

    static class WL extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }

    public static void main(String args[]) {
        Counter4 applet = new Counter4();
        // This isn't an applet, so set the flag and
        // produce the parameter values from args[]:
        applet.isApplet = false;
        applet.size = (args.length == 0 ? 5 :
                Integer.parseInt(args[0]));
        Frame aFrame = new Frame("Counter4");
        aFrame.addWindowListener(new WL());
        aFrame.add(applet, BorderLayout.CENTER);
        aFrame.setSize(200, applet.size * 50);
        applet.init();
        applet.start();
        aFrame.setVisible(true);
    }
} /// :~
