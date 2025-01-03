package code.java.view.layer;

import code.java.utils.FrameUtils;
import code.java.view.layer.layerUI.SpotlightLayerUI;

import javax.swing.plaf.LayerUI;

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

public class SpotlightLayerUITest extends BaseLayerUITest {

    @Override
    protected LayerUI getLayerUI() {
        return new SpotlightLayerUI();
    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(SpotlightLayerUITest.class).setTitle("JLayer测试");
    }

}