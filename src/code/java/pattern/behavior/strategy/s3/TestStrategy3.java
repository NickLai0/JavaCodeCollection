package code.java.pattern.behavior.strategy.s3;

public class TestStrategy3 {

    public static void main(String[] args) {
        Context ctx = new Context();

        ctx.setStra(new StrategyImplA());
        ctx.doMethod();

        ctx.setStra(new StrategyImplB());
        ctx.doMethod();

        ctx.setStra(new StrategyImplC());
        ctx.doMethod();
    }

}
