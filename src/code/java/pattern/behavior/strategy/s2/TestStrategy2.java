package code.java.pattern.behavior.strategy.s2;

public class TestStrategy2 {
    public static void main(String[] args) {
        Context ctx = new Context(new StrategyImplA());
        ctx.doMethod();

        ctx = new Context(new StrategyImplB());
        ctx.doMethod();

        ctx = new Context(new StrategyImplC());
        ctx.doMethod();
    }

}
