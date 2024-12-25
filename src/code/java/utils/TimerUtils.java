package code.java.utils;

import javax.swing.*;
import java.awt.event.ActionListener;

public class TimerUtils {

    //创建和启动Timer（定时器），然后将Timer对象返回。
    public static Timer start(ActionListener l, int delay) {
        Timer timer = new Timer(delay, l);
        timer.start();
        return timer;
    }
}
