package code.java.view.progress;

public class SimulatedActivity implements Runnable {
    // 任务的当前完成量
    private volatile int current;
    // 总任务量
    private int amount;

    public SimulatedActivity(int amount) {
        current = 0;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public int getCurrent() {
        return current;
    }

    // run方法代表不断完成任务的过程
    public void run() {
        while (current < amount) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
            }
            current++;
        }
    }

}