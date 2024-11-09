package code.java.thread.deamon;
/*
Demonstrate The Java Virtual Machine exits
when the only threads running are all
daemon threads.

From Thinking in Java

daemon threads

A “daemon” thread is one that is supposed to provide
a general service in the background as long as
the program is running, but is not part of
the essence（本质/实质） of the program.
Thus, when all the non-daemon
threads complete then the program is terminated.

按：Why all the non-daemon threads complete then the program is terminated？

You can find out if a thread is a daemon by calling isDaemon( ),
and you can turn the daemon hood of
a thread on and off with setDaemon( ). If a thread is a daemon,
then any threads it creates will
automatically be daemons.
The following example demonstrates daemon threads:
*/
//: Daemons.java
// Daemonic behavior
class Daemon extends Thread {
    int j;
    static final int size = 10;
    Thread t[] = new Thread[size];

    Daemon() {
        /*
        Marks this thread as either a daemon thread or
        a user thread. The Java Virtual Machine exits
        when the only threads running are all daemon threads.

        This method must be invoked before the thread is started.

        IllegalThreadStateException – if this thread is alive
        SecurityException – if checkAccess determines that the current thread cannot modify this thread
        * */
        setDaemon(true);
        start();
    }

    /*
    This run method is running in a daemon thread.

    If a thread is a daemon,
    then any threads it creates will
    automatically be daemons.
    * */
    public void run() {
        for (int i = 0; i < size; i++)
            //a DaemonSpawn thread would be a daemon thread automatically.
            t[i] = new DaemonSpawn(i);
        for (int i = 0; i < size; i++)
            System.out.println(
                    "t[" + i + "].isDaemon() = "
                            + t[i].isDaemon());
        while (true) j++;
    }
}

//Spawn：繁衍
class DaemonSpawn extends Thread {
    int i;

    DaemonSpawn(int i) {
        System.out.println(
                "DaemonSpawn " + i + " started");
        start();
    }

    public void run() {
        /*
        When all the daemon threads are created,
        Daemons.main( ) has nothing else to do.
        Since there are
        nothing but daemon threads running,
        the program should terminate (note that this program did not
        produce the desired behavior on all JVM’s when this was written).
        */
        while (true) i++;
    }
}

public class Daemons {
    public static void main(String args[]) {
        Thread d = new Daemon();
        System.out.println("d.isDaemon() = " + d.isDaemon());
    }
}

/*
The Daemon thread sets its daemon flag to “true”
and then spawns a bunch of other threads to show
that they are also daemons. Then it goes into an
infinite loop and counts, which is what the
DaemonSpawn threads do as well.

When all the daemon threads are created,
Daemons.main( ) has nothing else to do. Since there are
nothing but daemon threads running,
the program should terminate (note that this program did not
produce the desired behavior on all JVM’s when this was written).

*/
