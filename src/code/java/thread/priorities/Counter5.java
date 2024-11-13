package code.java.thread.priorities;
//From thinking in Java. Modified a little.
//: Counter5.java
// Adjusting the priorities of threads
/*
 * The priority of a thread tells the scheduler how important this thread is. If there are a number of
 * threads blocked and waiting to be run, the scheduler will run the one with the highest priority first.
 * However, this doesn’t mean that threads with lower priority don’t get run (that is, you can’t get
 * deadlocked because of priorities). Lower priority threads just tend to run less often.
 * You can read the priority of a thread with getPriority( ) and change it with setPriority( ). The form of
 * the prior “counter” examples can be used to show the effect of changing the priorities. In this applet
 * you’ll see that the counters slow down as the associated threads have their priorities lowered:
 */

import java.awt.*;
import java.awt.event.*;
import java.applet.*;

class Ticker2 extends Thread {

    Button
            b = new Button("Toggle"),
            incPriority = new Button("up"),
            decPriority = new Button("down");

    TextField
            t = new TextField(10),
            p = new TextField(3); // Display priority

    int count = 0;

    boolean runFlag = true;

    Ticker2() {
        b.addActionListener(new ToggleL());
        incPriority.addActionListener(new UpL());
        decPriority.addActionListener(new DownL());
    }

    class ToggleL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            runFlag = !runFlag;
        }
    }

    class UpL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int newPriority = getPriority() + 1;
            if (newPriority > Thread.MAX_PRIORITY)
                newPriority = Thread.MAX_PRIORITY;
            //设置线程组（ThreadGroup）的优先级，最大为10，最小为1。可动态设置
            setPriority(newPriority);
        }
    }

    class DownL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int newPriority = getPriority() - 1;
            if (newPriority < Thread.MIN_PRIORITY)
                newPriority = Thread.MIN_PRIORITY;
            //设置线程组（ThreadGroup）的优先级，最大为10，最小为1。可动态设置
            setPriority(newPriority);
        }
    }

    public void run() {
        while (true) {
            if (runFlag) {
                t.setText(Integer.toString(count++));
                p.setText(Integer.toString(getPriority()));
            }

            /**
             * A hint to the scheduler that the current thread is willing to
             *  yield its current use of a processor. The scheduler is free
             *  to ignore this hint.
             *
             *
             * From Thinking in Java:
             * voluntarily hands control back to the scheduler.
             * Without this the multithreading mechanism still works,
             * but you’ll notice it runs very slowly
             */
            yield();
            try {
                //不加sleep，按钮的点击事件因这里的死循环持续
                // setText而忽略掉，导致不响应。加的时间越短
                // 越不好点击按钮。一秒以上最好点，可 又测不出
                // 线程优先级问题。
                sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

public class Counter5 extends Applet {

    static final int size = 10;

    Button
            start = new Button("Start"),
            upMax = new Button("Inc Max Priority"),
            downMax = new Button("Dec Max Priority");

    boolean started = false;

    Ticker2 ticker2Arr[] = new Ticker2[size];

    TextField textField = new TextField(3);

    public void init() {
        for (int i = 0; i < ticker2Arr.length; i++) {
            ticker2Arr[i] = new Ticker2();
            Panel p = new Panel();
            p.add(ticker2Arr[i].t);
            p.add(ticker2Arr[i].p);
            p.add(ticker2Arr[i].b);
            p.add(ticker2Arr[i].incPriority);
            p.add(ticker2Arr[i].decPriority);
            add(p);
        }
        add(new Label("MAX_PRIORITY = "
                + Thread.MAX_PRIORITY));
        add(new Label("MIN_PRIORITY = "
                + Thread.MIN_PRIORITY));
        add(new Label("Thread Group Max Priority = "));
        add(textField);

        add(start);
        add(upMax);
        add(downMax);
        start.addActionListener(new StartL());
        upMax.addActionListener(new UpMaxL());
        downMax.addActionListener(new DownMaxL());
        showMaxPriority();
        // Recursively display parent thread groups:
        ThreadGroup parent =
                ticker2Arr[0].getThreadGroup().getParent();
        while (parent != null) {
            add(new Label(
                    "Parent threadgroup max priority = "
                            + parent.getMaxPriority()));
            parent = parent.getParent();
        }
    }

    void showMaxPriority() {
        textField.setText(Integer.toString(
                ticker2Arr[0].getThreadGroup().getMaxPriority()));
    }

    class StartL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (!started) {
                started = true;
                for (int i = 0; i < ticker2Arr.length; i++)
                    ticker2Arr[i].start();
            }
        }
    }

    class UpMaxL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int maxp =
                    ticker2Arr[0].getThreadGroup().getMaxPriority();
            if (++maxp > Thread.MAX_PRIORITY)
                maxp = Thread.MAX_PRIORITY;
            //设置线程组（ThreadGroup）的优先级，最大为10，最小为1。可动态设置
            ticker2Arr[0].getThreadGroup().setMaxPriority(maxp);
            showMaxPriority();
        }
    }

    class DownMaxL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int maxp =
                    ticker2Arr[0].getThreadGroup().getMaxPriority();
            if (--maxp < Thread.MIN_PRIORITY)
                maxp = Thread.MIN_PRIORITY;
            //设置线程组（ThreadGroup）的优先级，最大为10，最小为1。可动态设置
            ticker2Arr[0].getThreadGroup().setMaxPriority(maxp);
            showMaxPriority();
        }
    }

    static class WL extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }

    public static void main(String args[]) {
        Counter5 applet = new Counter5();
        Frame aFrame = new Frame("Counter5");
        aFrame.addWindowListener(new WL());
        aFrame.add(applet, BorderLayout.CENTER);
        aFrame.setSize(300, 600);

        applet.init();
        applet.start();
        aFrame.setVisible(true);
    }
} /// :~

/*
The Ticker2 follows the form established earlier in this chapter, but there’s an extra TextField for
displaying the priority of the thread and two more buttons, for incrementing and decrementing the
priority.
Also notice the use of yield( ), which voluntarily hands control back to the scheduler. Without this the
multithreading mechanism still works, but you’ll notice it runs very slowly (try removing the call to

yield( )!). You could also call sleep( ), but then the rate of counting would be controlled by the

sleep( ) duration instead of the priority.
The init( ) in Counter5 creates an array of 10 Ticker2s and places their buttons and fields on the form,
along with buttons to start up the whole process as well as increment and decrement the maximum
priority of the threadgroup. In addition, there are labels that display the maximum and minimum
priorities possible for a thread, and a TextField to show the thread group’s maximum priority (the next
section will fully describe thread groups). Finally, the priorities of the parent thread groups are also
displayed as labels.
When you press an “up” or “down” button, that Ticker2’s priority is fetched and incremented or
decremented, accordingly.
When you run this program, you’ll notice several things. First of all, the thread group’s default priority is
5. Even if you decrement the maximum priority below 5 before starting the threads (or before creating
the threads, which requires a code change) each thread will have a default priority of 5.
The simple test is to take one counter and decrement its priority to one, and observe that it counts
much slower. But now try to increment it again. You can get it back up to the thread group’s priority,
but no higher. Now decrement the thread group’s priority a couple of times. The thread priorities are
unchanged, but if you try to modify them either up or down you’ll see that they’ll automatically pop to
the priority of the thread group. Also, new threads will still be given a default priority, even if that’s
higher than the group priority (thus the group priority is not a way to prevent new threads from having
higher priorities than existing ones).
Finally, try to increment the group maximum priority. It can’t be done. You can only reduce thread
group maximum priorities, not increase them.

 */