package code.java.thread.group;
/*
From Thinking in Java. Modified a little.

thread groups

All threads belong to a thread group.
This may be either the default thread
group or a group you
explicitly specify when you create the thread.
At creation, the thread is bound to a group and cannot
change to a different group. Each application has
at least one thread that belongs to the system thread
group. If you create more threads without specifying a group,
they will also belong to the system
thread group.
Thread groups must also belong to other thread groups.
The thread group that a new one belongs to
must be specified in the constructor. If you create
a thread group without specifying a thread group for
it to belong to, it will be placed under the system
thread group. Thus, all thread groups in your
application will ultimately have the system thread
group as the parent.
The reason for the existence of thread groups is hard to
determine from the literature, which tends to
be confusing on this subject. It’s often cited as
“security reasons.” According to Arnold & Gosling

1“Threads within a thread group can modify the other
threads in the group, including any farther down
the hierarchy. A thread cannot modify threads outside
of its own group or contained groups.” It’s hard
to know what “modify” is supposed to mean here. The
following example shows a thread in a “leaf”


1 The Java Programming Language, by Ken Arnold and James Gosling, Addison-Wesley 1996 pp 179.
Chapter 14: Multiple Threads 451

subgroup modifying the priorities of all the threads in its tree of thread groups, as well as calling a
method for all the threads in its tree.

*/
//: TestAccess.java
// How threads can access other threads
// in a parent thread group
public class TestAccess {
    public static void main(String args[]) {
        /*
         x has no argument but （has） its name (a String)
         and thus it is automatically placed in the “system”
         thread group, while y is under x and z is under y.
         */
        ThreadGroup
                x = new ThreadGroup("x"),
                y = new ThreadGroup(x, "y"),
                z = new ThreadGroup(y, "z");
        //Two threads are created and placed in different thread groups.
        Thread
                one = new TestThread1(x, "one"),
                two = new TestThread2(z, "two");
    }
}
//默认不激活的线程
class TestThread1 extends Thread {
    private int i;

    TestThread1(ThreadGroup g, String name) {
        super(g, name);
    }

    void f() {
        i++; // modify this thread
        System.out.println(getName() + " f()");
    }
}
//一创建，默认就调用start()方法激活的线程
class TestThread2 extends TestThread1 {
    TestThread2(ThreadGroup g, String name) {
        super(g, name);
        start();
    }
/*
TestThread2 is a subclass of TestThread1 and it’s run( )
is fairly elaborate（详尽的/detail）: it first gets the thread
group of the current thread, and then moves up the
heritage tree by two levels using getParent( )
(this is contrived（人为的） since I purposely place
the TestThread2 object two levels down in the hierarchy).
At this point, an array of handles to Threads is created,
 using the method activeCount( ) to ask how many threads
 are in this thread group and all the child thread groups.
 The enumerate( )  method places handles to all these
 threads in the array gAll, and then I simply move
 through the entire array calling the f( ) method for
 each thread, as well as modifying the priority. Thus,
 a thread in a “leaf” thread group modifies threads in
 parent thread groups. The debugging method list( )
 prints all the information about a thread group to
 standard output, and is very helpful when investigating
 thread group behavior.
*/
    public void run() {
        /*
        getThreadGroup()获得z线程组-》
        调用z线程组的getParent()获取y线程组-》
        调用y线程组的getParent()获取x线程组。

        结论是g为x线程组
        */
        ThreadGroup g = getThreadGroup().getParent().getParent();
        //Printing ThreadGroup tree.
        g.list();
        /*
        activeCount是该线程组已经激活（调用start（））的线程个数，
        值是动态变化的，所以只是个预估值。

        下面是该方法的注释。
        Returns an estimate of the number of active threads
        in this thread group and its subgroups. Recursively
        iterates over all subgroups in this thread group.

        The value returned is only an estimate because
        the number of threads may change dynamically while
        this method traverses internal data structures,
        and might be affected by the presence of certain
        system threads. This method is intended primarily
        for debugging and monitoring purposes.

        Returns: an estimate of the number of active threads in this thread group and in any other thread group that has this thread group as an ancestor
        */
        Thread gAll[] = new Thread[g.activeCount()];
        //遍历线程组，将内部已激活的线程保存到gAll[]
        g.enumerate(gAll);
        for (int i = 0; i < gAll.length; i++) {
            gAll[i].setPriority(Thread.MIN_PRIORITY);
            ((TestThread1) gAll[i]).f();
        }
        System.out.println("After modified priorities for all threads under x thread group tree.");
        //Printing ThreadGroup tree.
        g.list();
    }
} /// :~
