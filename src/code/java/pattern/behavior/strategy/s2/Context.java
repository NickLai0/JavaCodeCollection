package code.java.pattern.behavior.strategy.s2;

public class Context {
    Strategy stra;

    public Context(Strategy stra) {
        this.stra = stra;
    }

    public void doMethod() {
        stra.method();
    }

}
