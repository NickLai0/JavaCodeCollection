package code.java.thread.share;
/*
From Thinking in Java.

how Java shares resources

Java has built-in support to prevent collisions(冲突/conflicts) over
one kind of resource: the memory in an object.
Since you typically make the data elements of
a class private and access to that memory is then only
provided through methods, collision prevention
is achieved by making a particular method
synchronized. Only one synchronized method
at a time can be called for a particular object. Here are
simple synchronized methods:

synchronized void f() { ...  }
synchronized void g(){  ... }

Each object contains a single lock that’s automatically
part of the object (you don’t have to write any
 special code). When you call any synchronized method,
 that object is locked and no other
synchronized methods can be called until the first one
finishes and thus releases the lock. In the
above example, if f( ) is called for an object,
g( ) cannot be called for the same object until f( ) is
completed and releases the lock. Thus there’s a single
lock that’s shared by all the synchronized
methods of a particular object, and this lock prevents
common memory from being written by more
than one method at a time (i.e. more than one thread at a time).

There’s also a single lock per class,
so that synchronized static methods can lock each other out from
static data on a class-wide basis.

Note that if you want to guard some other resource from
simultaneous access by multiple threads, you
can do so by forcing access to that resource through synchronized methods.

synchronizing the counters

Armed with this new keyword it appears that the solution is at hand:
 we’ll simply make the methods
in TwoCounter synchronized. The following example is the same
as before, with the addition of the
new keyword:


* */
//: Sharing2.java
// Using the synchronized keyword to prevent
// multiple access to a particular resource.

import java.awt.*;
import java.awt.event.*;
import java.applet.*;

class TwoCounter2 extends Thread {
    TextField
            t1 = new TextField(5),
            t2 = new TextField(5);
    Label l = new Label("count1 == count2");
    int count1 = 0, count2 = 0;

    //Only one synchronized method at a time can be called for a particular object.
    public synchronized void run() {
        while (true) {
            t1.setText(Integer.toString(count1++));
            t2.setText(Integer.toString(count2++));
            try {
                sleep(500);
            } catch (InterruptedException e) {
            }
        }
    }

    /*
    Only one synchronized method
    at a time can be called
    for a particular object.

    当synchronized的 run（）运行后，陷入死循环，
    其它线程调用synchronized的synchTest()则一直
    无法执行。
     */

    synchronized void synchTest() {
        Sharing2.incrementAccess();
        if (count1 != count2)
            l.setText("Unsynched");
    }

}

class Watcher2 extends Thread {
    Sharing2 share2;

    Watcher2(Sharing2 p) {
        this.share2 = p;
        start();
    }

    public void run() {
        while (true) {
            for (int i = 0; i < share2.twoCounter2Arr.length; i++)
                share2.twoCounter2Arr[i].synchTest();
            try {
                sleep(500);
            } catch (InterruptedException e) {
            }
        }
    }
}

public class Sharing2 extends Applet {
    static int accessCount = 0;
    static TextField aCount = new TextField("0", 10);

    static void incrementAccess() {
        accessCount++;
        aCount.setText(Integer.toString(accessCount));
    }

    Button
            start = new Button("Start"),
            observer = new Button("Observe");
    TwoCounter2 twoCounter2Arr[];
    boolean isApplet = true;
    int numCounters = 0;
    int numObservers = 0;

    public void init() {
        if (isApplet) {
            numCounters =
                    Integer.parseInt(getParameter("size"));
            numObservers =
                    Integer.parseInt(
                            getParameter("observers"));
        }
        twoCounter2Arr = new TwoCounter2[numCounters];
        for (int i = 0; i < twoCounter2Arr.length; i++) {
            twoCounter2Arr[i] = new TwoCounter2();
            Panel p = new Panel();
            p.add(twoCounter2Arr[i].t1);
            p.add(twoCounter2Arr[i].t2);
            p.add(twoCounter2Arr[i].l);
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
            for (int i = 0; i < twoCounter2Arr.length; i++)
                twoCounter2Arr[i].start();
        }
    }

    class ObserverL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < numObservers; i++)
                new Watcher2(Sharing2.this);
        }
    }

    static class WL extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }

    public static void main(String args[]) {
        Sharing2 applet = new Sharing2();
        // This isn't an applet, so set the flag and
        // produce the parameter values from args[]:
        applet.isApplet = false;
        applet.numCounters =
                (args.length == 0 ? 5 :
                        Integer.parseInt(args[0]));
        applet.numObservers =
                (args.length < 2 ? 5 :
                        Integer.parseInt(args[1]));
        Frame aFrame = new Frame("Sharing2");
        aFrame.addWindowListener(new WL());
        aFrame.add(applet, BorderLayout.CENTER);
        aFrame.setSize(350, applet.numCounters * 100);
        applet.init();
        applet.start();
        aFrame.setVisible(true);
    }
} /// :~
