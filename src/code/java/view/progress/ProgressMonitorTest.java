package code.java.view.progress;

import javax.swing.*;

/**
 * Description:
 * 网站: <a href="http://www.crazyit.org">疯狂Java联盟</a><br>
 * Copyright (C), 2001-2018, Yeeku.H.Lee<br>
 * This program is protected by copyright laws.<br>
 * Program Name:<br>
 * Date:<br>
 *
 * @author Yeeku.H.Lee kongyeeku@163.com
 * @version 1.0
 */
public class ProgressMonitorTest {

    Timer timer;
    SimulatedActivity target = new SimulatedActivity(1000);
    Thread targetThread = new Thread(target);

    public void init() {
        // 以启动一条线程的方式来执行一个耗时的任务
        final ProgressMonitor dialog = new ProgressMonitor(
                null,
                "等待任务完成",
                "已完成：",
                0,
                target.getAmount()
        );


        timer = new Timer(300, e -> {
            // 以任务的当前完成量设置进度对话框的完成比例
            dialog.setProgress(target.getCurrent());
            // 如果用户单击了进度对话框的"取消"按钮
            if (dialog.isCanceled()) {
                // 停止计时器
                timer.stop();
                // 中断任务的执行线程
                targetThread.interrupt();    // ①
                // 系统退出
                System.exit(0);
            }
        });

        targetThread.start();
        timer.start();
    }

    public static void main(String[] args) {
        new ProgressMonitorTest().init();
    }
}
