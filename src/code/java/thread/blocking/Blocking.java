package code.java.thread.blocking;
/*
From Thinking in Java.
Modified a little.

线程阻塞的五种情况：thread.sleep、thread.suspend、
object.wait、synchronized、waiting for some
IO to complete。阻塞下的Thread，JVM跳过不执行.

可推论出以前DDKT项目在socket阻塞时，CPU不会被抢。

The blocked state is the most interesting and is worth further examination.
A thread can become blocked for five reasons:
    1. You’ve put the thread to sleep by calling sleep(milliseconds) in which case it will not be run for the specified time.
    2. You’ve suspended the execution of the thread with suspend( ). It will not become runnable again until the thread gets the resume( ) message.
    3. You’ve suspended the execution of the thread with wait( ). It will not become runnable again until the thread gets the notify( ) or notifyAll( ) message (yes, this looks just like number 2, but there’s a distinct difference that will be revealed).
    4. The thread is waiting for some IO to complete.
    5. The thread is trying to call a synchronized method on another object and that object’s lock is not available. You can also call yield( )（按：yield：让步；放弃） (a method of the Thread class) to voluntarily（自愿地） give up the CPU so other threads may run.

 However, this is no different than(then?) if the scheduler
 decides that your thread has had enough time and
 jumps to another thread. That is, nothing prevents
 the scheduler from re-starting your thread.
 When a thread is blocked, there’s some reason
 that it cannot continue running. The following
 example shows all five ways of becoming blocked.
 It all exists in a single file called
*/

//: Blocking.java
// Demonstrates the various ways a thread
// can be blocked.

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.io.*;

class Blockable extends Thread {
    TextField tfState = new TextField(40);
    //Peeker is a thread, and it starts the thread in the structure method.
    Peeker peeker = new Peeker(this);
    protected int value;

    public synchronized int readValue() {
        return value;
    }

    //update method would be called by  subclass.
    protected synchronized void showLatestValue() {
        tfState.setText(getClass().getName() + " state: i = " + value);
    }
}

//Peeker：偷窥狂、窥视者
class Peeker extends Thread {
    Blockable blockable;
    int session;
    TextField tfStatus = new TextField(40);

    Peeker(Blockable b) {
        this.blockable = b;
        //Why does this thread calls start method
        //here in the structure method? just for
        //testing easier？
        start();
    }

    public void run() {
        while (true) {
            tfStatus.setText(blockable.getClass().getName()
                    + " Peeker " + (++session)
                    + "; value = " + blockable.readValue());
            try {
                sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }
} /// :Continued


//sleeping  The first test in this program is with sleep( )

/// :Continuing
/// ////////// Blocking via sleep() ///////////
class Sleeper1 extends Blockable {
    public synchronized void run() {
        while (true) {
            value++;
            showLatestValue();
            try {
                sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }

}

class Sleeper2 extends Blockable {
    public void run() {
        while (true) {
            /*
            对比Sleeper1的synchronized的run()方法内部的死循环，
            以及Sleeper2的run()方法内部的死循环，
            Sleeper1一旦执行run（）后，外部所有调用Sleeper1对象synchronized方法的
            地方都得进入休眠状态以待run（）执行完；Sleeper2在run（）方法死循环代码
            内部，只有执行change（）方法时，外部所有调用Sleeper2对象synchronized
            的方法的地方，才进入休眠状态，change（）方法执行完后，外部线程可正常执行
            Sleeper2对象的synchronized方法。

           可见Sleeper1的synchronized的run()方法内部的死循环,
           会使得外部调用Sleeper1对象的其它synchronized方法时，“卡死”掉；
           Sleeper2的run()方法内部的死循环，除了调用到change（）方法时，
           会短暂使外部“卡死”掉，在change（）方法执行完后就不影响了。
            */
            change();
            try {
                sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }

    synchronized void change() {
        value++;
        showLatestValue();
    }
} /// :Continued

/// :Continuing
/// //////// Blocking via suspend() ///////////
class SuspendResume/*子类suspend，Resumer则不断调用resume*/ extends Blockable {
    SuspendResume() {
        //Resumer is a thread, and it starts the thread in the structure method.
        new Resumer(this);
    }
}

class SuspendResume1 extends SuspendResume {
    /*
在synchronized方法里调用suspend方法时，
然后外部线程调用本线程的resume方法，
则外部线程进入死锁，
可是本线程却被激活，
而且本线程后续再调用suspend
也不会再被挂起
     */

    public synchronized void run() {
        while (true) {
            value++;
            showLatestValue();

            /**
             *
             *  This method has been deprecated,
             *  as it is inherently（与生俱来）
             *  deadlock-prone（容易死锁）.
             *  If the target thread holds a lock on
             *  the monitor protecting a critical
             *  system resource when it is suspended,
             *  no thread can access this
             *  resource until the target thread is resumed.
             *  If the thread that
             *  would resume the target thread attempts
             *  to lock this monitor prior
             *  to calling resume,
             *  deadlock results.
             */
            //deadlock（死锁）问题看本run方法的注释
            suspend();
        }
    }
}

class SuspendResume2 extends SuspendResume {
    public void run() {
        while (true) {
            change();
            /*
              suspend不是在synchronized方法里被调用的，
              所以外部线程调用resume的话，不会进入deadlock.
            */
            suspend();
        }
    }

    synchronized void change() {
        value++;
        showLatestValue();
    }
}

//线程恢复器，每个SuspendResume和其子类都持有一个Resumer对象
class Resumer extends Thread {
    SuspendResume suspendResume;

    Resumer(SuspendResume suspendResume) {
        this.suspendResume = suspendResume;
        start();
    }

    public void run() {
        while (true) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
            }
            suspendResume.resume();
        }
    }
} /// :Continued

/// :Continuing
/// //////// Blocking via wait() ///////////
class WaitNotify1 extends Blockable {
    public synchronized void run() {
        while (true) {
            value++;
            showLatestValue();
            try {
                //相当于调用Sleep方法，可是却额外多个外部在超时时间内
                //调用本对象的notify或者notifyAll方法的话，
                //即刻唤醒本线程的效果
                wait(1000);
            } catch (InterruptedException e) {
            }
        }
    }
}

class WaitNotify2 extends Blockable {
    WaitNotify2() {
        //和WaitNotify1唯一的区别就是多了个Notifier
        new Notifier(this);
    }

    public synchronized void run() {
        while (true) {
            value++;
            showLatestValue();
            try {
                //Notifier总是每两秒调用一次本线程对象的notify方法，
                //然后本线程就被唤醒
                wait();
            } catch (InterruptedException e) {
            }
        }
    }
}

class Notifier extends Thread {
    WaitNotify2 waitNotify2;

    Notifier(WaitNotify2 WN2) {
        waitNotify2 = WN2;
        start();
    }

    public void run() {
        while (true) {
            try {
                sleep(2000);
            } catch (InterruptedException e) {
            }
            synchronized (waitNotify2) {
                waitNotify2.notify();
            }
        }
    }
}

/// :Continued
/// :Continuing
class Sender extends Blockable { // send
    Writer out;

    Sender(Writer out) {
        this.out = out;
    }

    public void run() {
        while (true) {//死循环
            for (char c = 'A'; c <= 'z'; c++) {//每次循环都从头再发
                try {
                    value++;
                    out.write(c);
                    tfState.setText("Sender sent: " + c);
                    //连睡眠都能搞个随机！
                    sleep((int) (3000 * Math.random()));
                } catch (InterruptedException e) {
                } catch (IOException e) {
                }
            }
        }
    }
}

class Receiver extends Blockable {
    Reader in;

    Receiver(Reader in) {
        this.in = in;
    }

    public void run() {
        try {
            while (true) {
                value++; // show peeker it's alive
                // Blocks until characters are there:
                tfState.setText("Receiver read: "  + (char) in.read());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} /// :Continued

/// :Continuing
/// //////// Testing Everything ///////////
public class Blocking extends Applet {
    Button
            start = new Button("Start"),
            stopPeekers = new Button("Stop Peekers");
    boolean started = false;
    Blockable blockableArr[];
    PipedWriter out;
    PipedReader in;

    public void init() {
        out = new PipedWriter();
        try {
            in = new PipedReader(out);
        } catch (IOException e) {
        }
        Blockable b2[] = {
                new Sleeper1(),
                new Sleeper2(),
                new SuspendResume1(),
                new SuspendResume2(),
                new WaitNotify1(),
                new WaitNotify2(),
                new Sender(out),
                new Receiver(in)
        };
        blockableArr = b2;
        for (int i = 0; i < blockableArr.length; i++) {
            /*
            State is used to describe a stage in a process
            (e.g. pending/dispatched).
            Status is used to describe an outcome of an operation
            (e.g. success/fail).
            */
            add(blockableArr[i].tfState);
            add(blockableArr[i].peeker.tfStatus);
        }
        start.addActionListener(new StartL());
        add(start);
        stopPeekers.addActionListener(new StopPeekersL());
        add(stopPeekers);
    }

    class StartL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (!started) {
                started = true;
                for (int i = 0; i < blockableArr.length; i++)
                    blockableArr[i].start();
            }
        }
    }

    class StopPeekersL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Demonstration of Thread.stop():
            for (int i = 0; i < blockableArr.length; i++)
                blockableArr[i].peeker.stop();
        }
    }

    static class WL extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }

    public static void main(String args[]) {
        Blocking appletBlocking = new Blocking();
        Frame aFrame = new Frame("Blocking");

        aFrame.addWindowListener(new WL());
        aFrame.add(appletBlocking, BorderLayout.CENTER);
        aFrame.setSize(350, 550);
        appletBlocking.init();
        appletBlocking.start();
        aFrame.setVisible(true);
    }
} /// :~

/*
In init( ), note the loop that moves through the entire array and
adds the state and peeker.status text fields to the page.
When the Blockable threads are initially created,
each one automatically creates and starts its own
Peeker. Thus you’ll see the Peekers running before
the Blockable threads are started. This is
essential, as some of the Peekers will get
blocked and stop when the Blockable threads start, and it’s
essential to see this to understand that particular aspect of blocking.

deadlock

Because threads can become blocked and because
objects can have synchronized methods that
prevent threads from accessing that object
until the synchronization lock is released, it’s possible for
one thread to get stuck waiting for another thread,
which in turn waits for another thread, etc. until the
chain leads back to a thread waiting on the first one.
Thus there’s a continuous loop of threads waiting
on each other and no one can move. This is called deadlock.
The claim is that it doesn’t happen that
often, but when it happens to you it’s very frustrating to debug.
There is no language support to help prevent deadlock;
it’s up to you to avoid it by careful design.
These are not very comforting words to the person who’s
trying to debug a deadlocking program.

* */