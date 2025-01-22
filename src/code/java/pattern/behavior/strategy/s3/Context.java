package code.java.pattern.behavior.strategy.s3;
//策略模式的另一种实现，Context可set一个strategy
public class Context {
    Strategy stra;

//    public Context(Strategy stra) {
//        this.stra = stra;
//    }


    public void setStra(Strategy stra) {
        this.stra = stra;
    }

    public void doMethod() {
        stra.method();
    }

}
