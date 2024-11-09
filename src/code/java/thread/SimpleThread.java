package code.java.thread;
/*
From Thinking in Java.
Inheriting from Thread.
Modified a little.
 */
//: SimpleThread.java
// Very simple Threading example
public class SimpleThread extends Thread {
    private static int sThreadCount = 0;

    private int countDown = 5;
    private int threadNumber;
    public SimpleThread() {
        threadNumber = ++sThreadCount;
        System.out.println("Making " + threadNumber);
    }
    public void run() {
        while(true) {
            System.out.println("Thread " +
                    threadNumber + "(" + countDown + ")");
            if(--countDown == 0) return;
        }
    }
    public static void main(String args[]) {
        for(int i = 0; i < 5; i++)
            new SimpleThread().start();
        System.out.println("All Threads Started");
    }
} ///:~
