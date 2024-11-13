package code.java.thread.group;

import code.java.utils.LU;

//: ThreadGroup1.java
// How thread groups control priorities
// of the threads inside them.
public class ThreadGroup1 {
    public static void main(String[] args) {
        // Get the system thread & print its Info:
        ThreadGroup sys = Thread.currentThread().getThreadGroup();
        sys.list(); // (1)
        LU.printSeparateLine();

        // Reduce the system thread group priority:
        sys.setMaxPriority(Thread.MAX_PRIORITY - 1);
        // Increase the main thread priority:
        Thread curr = Thread.currentThread();
        curr.setPriority(curr.getPriority() + 1);
        sys.list(); // (2)
        LU.printSeparateLine();

        // Attempt to set a new group to the max:
        ThreadGroup threadGroup1 = new ThreadGroup("g1");
        threadGroup1.setMaxPriority(Thread.MAX_PRIORITY);
        // Attempt to set a new thread to the max:
        Thread thread = new Thread(threadGroup1, "A");
        thread.setPriority(Thread.MAX_PRIORITY);
        threadGroup1.list(); // (3)
        LU.printSeparateLine();

        // Reduce g1's max priority, then attempt
        // to increase it:
        threadGroup1.setMaxPriority(Thread.MAX_PRIORITY - 2);
        threadGroup1.setMaxPriority(Thread.MAX_PRIORITY);
        threadGroup1.list(); // (4)
        LU.printSeparateLine();


        // Attempt to set a new thread to the max:
        thread = new Thread(threadGroup1, "B");
        thread.setPriority(Thread.MAX_PRIORITY);
        threadGroup1.list(); // (5)
        LU.printSeparateLine();


        // Lower the max priority below the default
        // thread priority:
        threadGroup1.setMaxPriority(Thread.MIN_PRIORITY + 2);
        // Look at a new thread's priority before
        // and after changing it:
        thread = new Thread(threadGroup1, "C");
        threadGroup1.list(); // (6)


        LU.printSeparateLine();
        thread.setPriority(thread.getPriority() - 1);
        threadGroup1.list(); // (7)
        LU.printSeparateLine();


        // Make g2 a child Threadgroup of g1 and
        // try to increase its priority:
        ThreadGroup g2 = new ThreadGroup(threadGroup1, "g2");
        g2.list(); // (8)
        LU.printSeparateLine();


        g2.setMaxPriority(Thread.MAX_PRIORITY);
        g2.list(); // (9)
        LU.printSeparateLine();

        // Add a bunch of new threads to g2:
        for (int i = 0; i < 5; i++)
            new Thread(g2, Integer.toString(i));
        // Show information about all threadgroups
        // and threads:
        sys.list(); // (10)
        LU.printSeparateLine();

        System.out.println("Starting all threads:");
        Thread[] all = new Thread[sys.activeCount()];
        sys.enumerate(all);
        for (int i = 0; i < all.length; i++)
            if (!all[i].isAlive())
                all[i].start();
        // Suspends & Stops all threads in
        // this group and its subgroups:
        System.out.println("All threads started");
        LU.printSeparateLine();
        sys.suspend();
        // Never gets here...
        System.out.println("All threads suspended");
        sys.stop();
        System.out.println("All threads stopped");
    }
} /// :~

/*
From Thinking in Java，可到JavaCodeCollection找到案例运行测试。

ThreadGroup1程序运行和输出分析（和Thinking in Java输出结果不一样，这里以自己输出来分析）：

ThreadGroup sys = Thread.currentThread().getThreadGroup();
        sys.list(); // (1)
        LU.printSeparateLine();
//上面的代码运行得到的结果就是主ThreadGroup，默认有两个线程：
java.lang.ThreadGroup[name=main,maxpri=10]
    Thread[main,5,main]
    Thread[Monitor Ctrl-Break,5,main]
----------------------------------------

// Reduce the system thread group priority:
        sys.setMaxPriority(Thread.MAX_PRIORITY - 1);
        // Increase the main thread priority:
        Thread curr = Thread.currentThread();
        curr.setPriority(curr.getPriority() + 1);
        sys.list(); // (2)
        LU.printSeparateLine();

//上面的代码运行得到的结果就是主ThreadGroup的优先级为9，当前线程优先级为6，符合预期：

java.lang.ThreadGroup[name=main,maxpri=9]
    Thread[main,6,main]
    Thread[Monitor Ctrl-Break,5,main]
----------------------------------------

// Attempt to set a new group to the max:
        ThreadGroup threadGroup1 = new ThreadGroup("g1");
        threadGroup1.setMaxPriority(Thread.MAX_PRIORITY);
        // Attempt to set a new thread to the max:
        Thread thread = new Thread(threadGroup1, "A");
        thread.setPriority(Thread.MAX_PRIORITY);
        threadGroup1.list(); // (3)
        LU.printSeparateLine();

//上面的代码运行得到的结果就是新ThreadGroup的优先级为9，比预期少了1，
而新thread没被打印，说明没被激活，符合预期：

java.lang.ThreadGroup[name=g1,maxpri=9]
----------------------------------------

// Reduce g1's max priority, then attempt
        // to increase it:
        threadGroup1.setMaxPriority(Thread.MAX_PRIORITY - 2);
        threadGroup1.setMaxPriority(Thread.MAX_PRIORITY);
        threadGroup1.list(); // (4)
        LU.printSeparateLine();

//上面的代码运行得到的结果就是新ThreadGroup的优先级为9，比预期少了1:

java.lang.ThreadGroup[name=g1,maxpri=9]
----------------------------------------

 // Attempt to set a new thread to the max:
        thread = new Thread(threadGroup1, "B");
        thread.setPriority(Thread.MAX_PRIORITY);
        threadGroup1.list(); // (5)
        LU.printSeparateLine();

//上面的代码运行得到的结果就是新ThreadGroup的优先级为9，比预期少了1,
新创建的B Thread因没调用start方法而没被激活，故未打印，符合预期：

java.lang.ThreadGroup[name=g1,maxpri=9]
----------------------------------------

        // Lower the max priority below the default
        // thread priority:
        threadGroup1.setMaxPriority(Thread.MIN_PRIORITY + 2);
        // Look at a new thread's priority before
        // and after changing it:
        thread = new Thread(threadGroup1, "C");
        threadGroup1.list(); // (6)
        LU.printSeparateLine();

//上面的代码运行得到的结果就是新ThreadGroup的优先级为3，符合预期,
新创建的C Thread因没调用start方法而没被激活，故未打印，符合预期：

java.lang.ThreadGroup[name=g1,maxpri=3]
----------------------------------------
        thread.setPriority(thread.getPriority() - 1);
        threadGroup1.list(); // (7)
        LU.printSeparateLine();

//上面的代码运行得到的结果就是新ThreadGroup的优先级为3，符合预期:
(C thread 此时的priority值应该是2了)

java.lang.ThreadGroup[name=g1,maxpri=3]
----------------------------------------

// Make g2 a child Threadgroup of g1 and
        // try to increase its priority:
        ThreadGroup g2 = new ThreadGroup(threadGroup1, "g2");
        g2.list(); // (8)
        LU.printSeparateLine();

//上面的代码运行得到的结果就是g2 ThreadGroup的优先级为3,符合预期，
因为它的parent threadGroup 是threadGroup1，所以沿用了parent的
优先级：

java.lang.ThreadGroup[name=g2,maxpri=3]
----------------------------------------

 // Add a bunch of new threads to g2:
        for (int i = 0; i < 5; i++)
            new Thread(g2, Integer.toString(i));
        // Show information about all threadgroups
        // and threads:
        sys.list(); // (10)
 LU.printSeparateLine();

//上面的代码运行得到的结果就是g2 ThreadGroup的优先级为3,符合预期，
添加一堆没被激活的线程，所以线程们都没被打印出来，符合预期：

java.lang.ThreadGroup[name=g2,maxpri=3]
----------------------------------------

// Add a bunch of new threads to g2:
        for (int i = 0; i < 5; i++)
            new Thread(g2, Integer.toString(i));
        // Show information about all thread groups and threads:
        sys.list(); // (10)
        LU.printSeparateLine();

//上面的代码运行得到的结果就是main ThreadGroup有两个线程和g1 ThreadGroup。
//而g1 ThreadGroup 又有 g2 ThreadGroup，符合预期。

java.lang.ThreadGroup[name=main,maxpri=9]
    Thread[main,6,main]
    Thread[Monitor Ctrl-Break,5,main]
    java.lang.ThreadGroup[name=g1,maxpri=3]
        java.lang.ThreadGroup[name=g2,maxpri=3]
--------------------------------------------------

    Thread[] all = new Thread[sys.activeCount()];
        sys.enumerate(all);
        for (int i = 0; i < all.length; i++)
            if (!all[i].isAlive())
                all[i].start();
        // Suspends & Stops all threads in
        // this group and its subgroups:
        System.out.println("All threads started");
        LU.printSeparateLine();
        sys.suspend();
        // Never gets here...
        System.out.println("All threads suspended");
        sys.stop();
        System.out.println("All threads stopped");

//上面的代码运行得到的结果就是因为没有任何激活过的Thread，
所以循环不会走。走到suspend后，mian方法所在系统线程整个被挂起，
符合预期。

Starting all threads:
All threads started
--------------------------------------------------
*/
