package code.java.view.layer;

import code.java.utils.FrameUtils;
import code.java.view.layer.layerUI.BlurLayerUI;

import javax.swing.plaf.LayerUI;

/**
 * 测试高斯模糊LayerUI
 *
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

public class BlurLayerUITest extends BaseLayerUITest {

    @Override
    protected LayerUI getLayerUI() {
        return new BlurLayerUI();
    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(BlurLayerUITest.class).setTitle("JLayer测试");
    }

}