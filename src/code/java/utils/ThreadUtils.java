package code.java.utils;

public class ThreadUtils {
    public static void sleep(long millis) {
        if (millis <= 0) {
            return;
        }
        try {
            /*
                Note that sleep( ) may throw InterruptedException,
                although throwing such an exception is
                considered a hostile（有阻碍的） way to break from
                a thread and should be discouraged (once again, exceptions
                are for exceptional conditions, not normal flow of control).
                 Interrupting a sleeping thread is included to
                support a future language feature.
                * */
            Thread.sleep(millis);
        } catch (InterruptedException ignore) {
            System.out.println(ignore);
        }
    }

}
