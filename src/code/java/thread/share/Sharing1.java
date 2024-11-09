package code.java.thread.share;
/*
From Thinking in Java. Modified a little.

Demonstrate: Click Start button to trigger multi-thread(TwoCounter)，
every thread counting indefinitely；click
Observer button to  trigger multi-thread(Watcher),
every watcher calls every TwoCounter thread's
synchTest method, which first checks whether count1 and
count2 is synched, if unsynched, show "Unsynched once at least"；
second calls Sharing1.incrementAccess() to sharing and operating
accessCount static field from  Sharing1 class.
* */
//: Sharing1.java
// Problems with resource sharing while threading

import java.awt.*;
import java.awt.event.*;
import java.applet.*;

class TwoCounter extends Thread {
    boolean started = false;
    TextField
            textField1 = new TextField(5),
            textField2 = new TextField(5);
    Label label = new Label("count1 == count2");
    int count1 = 0, count2 = 0;

    public void run() {
        while (true) {
            textField1.setText(Integer.toString(count1++));
            textField2.setText(Integer.toString(count2++));
            try {
                sleep(500);
            } catch (InterruptedException e) {
            }
        }
    }

    void synchTest() {
        Sharing1.incrementAccess();
        if (count1 != count2){
            label.setText("Unsynched once at least");
        }/*else {
            l.setText("Synched");
        }*/
    }

}

class Watcher extends Thread {
    Sharing1 sharing1;

    Watcher(Sharing1 p) {
        this.sharing1 = p;
        start();
    }

    public void run() {
        while (true) {
            for (int i = 0; i < sharing1.twoCounterArr.length; i++)
                sharing1.twoCounterArr[i].synchTest();
            try {
                sleep(500);
            } catch (InterruptedException e) {
            }
        }
    }
}

public class Sharing1 extends Applet {
    static int accessCount = 0;
    static TextField aCount = new TextField("0", 10);

    static void incrementAccess() {
        accessCount++;
        aCount.setText(Integer.toString(accessCount));
    }

    Button
            start = new Button("Start"),

    observer = new Button("Observe");
    TwoCounter twoCounterArr[];
    boolean isApplet = true;
    int numCounters = 0;
    int numObservers = 0;

    public void init() {
        //Get parameter from web.
        if (isApplet) {
            numCounters =
                    Integer.parseInt(getParameter("size"));
            numObservers =
                    Integer.parseInt(
                            getParameter("observers"));
        }
        twoCounterArr = new TwoCounter[numCounters];
        for (int i = 0; i < twoCounterArr.length; i++) {
            twoCounterArr[i] = new TwoCounter();
            Panel p = new Panel();
            p.add(twoCounterArr[i].textField1);
            p.add(twoCounterArr[i].textField2);
            p.add(twoCounterArr[i].label);
            add(p);
        }
        Panel p = new Panel();
        start.addActionListener(new StartL());
        p.add(start);
        observer.addActionListener(new ObserverL());
        p.add(observer);
        p.add(new Label("Access Count"));
        p.add(aCount);
        add(p);
    }

    class StartL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < twoCounterArr.length; i++) {
                if (!twoCounterArr[i].started) {
                    twoCounterArr[i].started = true;
                    //创建Thread在前，启动在点击start按钮时。多次点击虽多次触发，但仅第一次就可启动线程，后续意义。
                    twoCounterArr[i].start();
                }
            }
        }
    }

    class ObserverL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < numObservers; i++)
                //多次点击则多次触发numObservers个Watcher线程。
                new Watcher(Sharing1.this);
        }
    }

    static class WL extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }

        public static void main(String args[]) {
        Sharing1 applet = new Sharing1();
        // This isn't an applet, so set the flag and
        // produce the parameter values from args[]:
        applet.isApplet = false;
        applet.numCounters =
                (args.length == 0 ? 5 : Integer.parseInt(args[0]));
        applet.numObservers =
                (args.length < 2 ? 5 :  Integer.parseInt(args[1]));
        Frame aFrame = new Frame("Sharing1");
        aFrame.addWindowListener(new WL());
        aFrame.add(applet, BorderLayout.CENTER);
        aFrame.setSize(350, applet.numCounters * 100);
        applet.init();
        applet.start();
        aFrame.setVisible(true);
    }
} /// :~
