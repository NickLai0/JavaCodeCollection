package code.java.pattern.behavior.strategy.s1;

import static code.java.utils.LU.println;

public class TestStrategy1 {

    public static void main(String[] args) {
        Context c = new Context(new OperationMultiply());
        println(c.executeStrategy(5, 5));

        c = new Context(new OperationAdd());
        println(c.executeStrategy(5, 5));

        c = new Context(new OperationSubtract());
        println(c.executeStrategy(5, 5));
    }

}
