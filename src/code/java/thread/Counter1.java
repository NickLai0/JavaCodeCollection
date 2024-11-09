package code.java.thread;
/*
* From Thinking in Java.
* Demonstrate how to sleep a thread.
* But it prevents any other responses
* after pressed the go button.
*
* 14: multiple threads

"Thinking in Java" Copyright © 1996-1997 by Bruce Eckel. All
Rights Reserved. This is a work in progress. Please do not
mirror or otherwise distribute this file (In security situations,
mirroring is permitted behind a firewall if the entire site is
mirrored and regular updates are maintained). The electronic
version of the book is available free; you can get an updated
copy at http//www.EckelObjects.com/Eckel. Corrections are
greatly appreciated; please send them to
Bruce@EckelObjects.com
[[[Chapter 14 directory:c14 ]]]

Objects provide a way to divide a program up into
independent sections. Often you also need to turn a program
into separate, independently-running processes.

Each of these independent processes is called a thread,
* and you program as if they run all by
themselves, and have the CPU all to themselves.
* Some underlying mechanism is actually dividing up
the CPU time for you, but in general you don’t have
* to think about it, which makes programming with
multiple threads a much easier task.
There are many possible uses for multithreading,
* but in general some part of your program is tied to a
particular event or resource, and you don’t want
* to hang up the rest of your program because of that.
*
So you create a thread associated with that event or resource,
* and let it run independently of the main
program. A good example is a “quit” button –
* you don’t want to be forced to poll the quit button in
every piece of code you write in your program, and yet you want the quit button to be responsive, as if
you were checking it regularly. In fact, one of the most immediately compelling reasons for
multithreading is to produce a responsive user interface.
420 Thinking in Java Bruce Eckel

responsive user interfaces

As a starting point, consider a program that performs some CPU-intensive operation and thus ends up
ignoring user input and being unresponsive. This one, a combined applet/application, will simply
display the result of a running counter
* */
//: Counter1.java
// A non-responsive user interface

import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class Counter1 extends Applet {
    int count = 0;
    Button
            onOff = new Button("Toggle"),
            start = new Button("Start");
    TextField t = new TextField(10);
    boolean runFlag = true;

    public void init() {
        add(t);
        start.addActionListener(new StartL());
        add(start);
        onOff.addActionListener(new OnOffL());
        add(onOff);
    }

    public void go() {
        while (true) {
            try {
                /*
                Note that sleep( ) may throw InterruptedException,
                although throwing such an exception is
                considered a hostile（有阻碍的） way to break from
                a thread and should be discouraged (once again, exceptions
                are for exceptional conditions, not normal flow of control).
                 Interrupting a sleeping thread is included to
                support a future language feature.
                * */
                Thread.currentThread().sleep(100);
            } catch (InterruptedException e) {
            }
            if (runFlag)
                t.setText(Integer.toString(count++));
        }
    }

    class StartL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            go();
        }
    }

    class OnOffL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            runFlag = !runFlag;
        }
    }

    static class WL extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }

    public static void main(String args[]) {
        Counter1 applet = new Counter1();
        Frame aFrame = new Frame("Counter1");
        aFrame.addWindowListener(new WL());
        aFrame.add(applet, BorderLayout.CENTER);
        aFrame.setSize(300, 200);
        applet.init();
        applet.start();
        aFrame.setVisible(true);

    }
}

/*
At this point the AWT and applet code should be reasonably
familiar from Chapter 13. The go( )

method is where the program stays busy:
it puts the current value of count into the TextField t, then
increments count.
Part of the infinite loop inside go( ) is to call sleep( ).
 sleep( ) must be associated with a Thread
object, and it turns out that every application has
some thread associated with it (indeed, Java is based
on threads and there are always some running along with your application). So regardless of whether
you’re actively using threads or not you can produce
the current thread used by your program with
Thread.currentThread() (a static method of the Thread class)
and then call sleep( ) for that thread.
Note that sleep( ) may throw InterruptedException,
although throwing such an exception is
considered a hostile（有阻碍的） way to break from
a thread and should be discouraged (once again, exceptions
are for exceptional conditions, not normal flow of control).
 Interrupting a sleeping thread is included to
support a future language feature.

When the start button is pressed, go( ) is invoked.
And upon examining go( ), you may naively think
(as I did) that it should allow multithreading
because it goes to sleep. That is, while the method is
asleep it seems like the CPU could be busy monitoring
other button presses. But it turns out the real
problem is that go( ) never returns,
since it’s in an infinite loop, and this means that
actionPerformed( ) never returns.
Since you’re stuck inside actionPerformed( ) for the first keypress,
the program can’t handle any other events
(to get out, you must somehow kill the process; the easiest
way to do this is to press Control-C in the console window).
The basic problem here is that go( ) needs to continue
 performing its operations, and at the same time
it needs to return so actionPerformed( ) can complete and the user interface can continue
responding to the user. But in a conventional method like go( ) it cannot continue and at the same
time return control to the rest of the program. This sounds like an impossible thing to accomplish, as if
the CPU must be in two places at once, but this is precisely what threading allows. It is a way to have
more than one independent process running at the same time, and the CPU will pop around and give
each process some of its time. Each process has the consciousness of constantly having the CPU all to
itself, but the CPU’s time is actually sliced between all the processes.
Of course, if you have more than one CPU then the operating system can dedicate each CPU to a set
of threads or even a single thread, and then the whole program can run much faster. Multitasking and
multithreading tend to be the most reasonable ways to utilize multiprocessor systems.

*/
