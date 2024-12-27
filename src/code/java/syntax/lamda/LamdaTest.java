package code.java.syntax.lamda;

import java.util.ArrayList;
import java.util.function.Consumer;

public class LamdaTest {
    public static void main(String[] args) {
        lamda1();
        lamda2();
        lamda3();
    }

    private static void lamda3() {
        // wrt Inner class
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                LamdaTest.fun();
            }
        };
        // wrt Lambda
        Runnable r2 = () -> LamdaTest.fun();
        // wrt :: operator
        Runnable r3 = LamdaTest::fun;

        new Thread(r1).start();
        new Thread(r2).start();
        new Thread(r3).start();

        for (int i = 0; i < 10; i++) {
            System.out.println("Parent Thread.....");
        }

    }

    private static void lamda2() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        //anonymous class
        list.forEach(new Consumer<Integer>() {
            @Override
            public void accept(Integer i) {
                PrintDemo.print(i);
            }
        });
        // with Lambda
        list.forEach((x) -> {
            PrintDemo.print(x);
        });
        // with :: operator
        list.forEach(PrintDemo::print);
    }

    static class PrintDemo {

        public static void print(Integer s) {
            System.out.println(s);
        }
    }

    private static void lamda1() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        // With Lambda
        list.forEach(e -> System.out.println(e));
        // with (::) Double Colon Operator
        list.forEach(System.out::println);

    }

    public static void fun() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Child Thread.....");
        }
    }

}
