package code.java.syntax;

import code.java.utils.LU;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static code.java.utils.LU.println;

/*

from https://www.geeksforgeeks.org/double-colon-operator-in-java/
modify a little.

 * The double colon (::) operator,
 * also known as method reference operator in Java,
 * is used to call a method by referring
 * to it with the help of its class directly.
 *
 * They behave exactly as the lambda expressions.
 * The only difference it has from lambda
 * expressions is that this uses direct
 * reference to the method by name instead
 * of providing a delegate to the method.
 *
 */
public class DoubleColonOperator extends SuperClass {

    /*
     * When and how to use double colon operator?
     *
     * Method reference or double colon operator can be used to refer:
     *      1:a static method,
     *      2:an instance method or a constructor.
     */


    public static void main(String[] args) {
        testMethodReferenceOperator4StaticMethod();
        LU.printSeparateLine();
        testMethodReferenceOperator4NonStaticMethod();
        LU.printSeparateLine();
        testMethodReferenceOperator4SuperMethod();
        LU.printSeparateLine();
        //测试特殊类型任意实例方法
        testMethodReferenceOperator4InstanceMethodOfAnArbitraryObjectOfAParticularType();
        LU.printSeparateLine();
        //测试特殊类型任意实例方法
        testMethodReferenceOperator4InstanceStructure();
    }

    private static void testMethodReferenceOperator4InstanceMethodOfAnArbitraryObjectOfAParticularType() {
        List<TestClass> list = new ArrayList<TestClass>();
        list.add(new TestClass("Geeks"));
        list.add(new TestClass("For"));
        list.add(new TestClass("GEEKS"));

        // call the instance method
        // using double colon operator
        list.forEach(TestClass::print);
    }


    static void staticMethod(String s) {
        System.out.println("staticMethod-> " + s);
    }


    private static void testMethodReferenceOperator4StaticMethod() {
        List<String> list = new ArrayList<String>();
        list.add("Geeks");
        list.add("For");
        list.add("GEEKS");

        /*
         * （Method reference or double colon operator）
         *  behave exactly as the lambda expressions.
         *
         * The only difference it has from lambda
         * expressions is that this uses direct
         * reference to the method by name instead
         * of providing a delegate to the method.
         *
         */

        //1: call the static method using double colon operator
        list.forEach(DoubleColonOperator::staticMethod);
    }

    void nonStaticMethod(String s) {
        System.out.println("nonStaticMethod-> " + s);
    }

    private static void testMethodReferenceOperator4NonStaticMethod() {
        List<String> list = new ArrayList<String>();
        list.add("Geeks");
        list.add("For");
        list.add("GEEKS");

        /*
         * （Method reference or double colon operator）
         *  behave exactly as the lambda expressions.
         *
         * The only difference it has from lambda
         * expressions is that this uses direct
         * reference to the method by name instead
         * of providing a delegate to the method.
         *
         */

        //2:an instance method or a constructor using double colon operator.
        list.forEach((new DoubleColonOperator())::nonStaticMethod);
    }

    // instance method to override super method
    @Override
    String print(String s) {
        // call the super method  using double colon operator
        Function<String, String> func = super::print;

        String newValue = func.apply(s);
        newValue += "After super method be called:" + s + "\n";
        System.out.println(newValue);

        return newValue;
    }

    private static void testMethodReferenceOperator4SuperMethod() {
        List<String> list = new ArrayList<String>();
        list.add("Geeks");
        list.add("For");
        list.add("GEEKS");

        // call the instance method using double colon operator
        list.forEach(new DoubleColonOperator()::print);
    }

    private static void testMethodReferenceOperator4InstanceStructure() {
        List<String> list = new ArrayList<String>();
        list.add("Geeks");
        list.add("For");
        list.add("GEEKS");

        // call the class constructor using double colon operator
        list.forEach(TestStructure::new);
    }

}


class SuperClass {

    // super function to be called
    String print(String str) {
        return ("SuperClass-> " + str + "\n");
    }

}

class TestClass {
    String str = null;

    public TestClass(String s) {
        this.str = s;
    }

    // super function to be called
    void print() {
        println("TestClass " + str);
    }

}

class TestStructure {
    public TestStructure(String s) {
        println("TestStructure->" + s);
    }
}