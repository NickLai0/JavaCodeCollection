package code.java.view.layer;

import code.java.utils.FrameUtils;
import code.java.view.layer.layerUI.LoadingLayerUI;

import javax.swing.*;
import javax.swing.plaf.LayerUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Modify a lot.
 *
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
public class LoadingLayerUITest extends BaseLayerUITest {

    private static final int DELAY_MILLIS = 4000;

    private LoadingLayerUI layerUI;

    //调用顺序LoadingLayerUITest2-》super();-》getLayerUI
    @Override
    protected LayerUI getLayerUI() {
        return layerUI = new LoadingLayerUI();
    }

    public LoadingLayerUITest() {
        super();
        // 定时器不重复触发
        timer.setRepeats(false);
        btn.addActionListener(new ShowLoadingLayerUIAction());
    }

    //定时器间隔4秒救停止loading
    Timer timer = new Timer(DELAY_MILLIS, e -> layerUI.stop());

    //点击按钮就显示loading layer UI
    class ShowLoadingLayerUIAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            layerUI.start();
            // 如果stopper定时器已停止，启动它
            if (!timer.isRunning()) {
                timer.start();
            }
        }
    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(LoadingLayerUITest.class)
                .setTitle("点击按钮显示Loading层的UI，" + DELAY_MILLIS + "毫秒后消失");
    }

}