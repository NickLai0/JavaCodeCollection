package code.java.view.border;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class BorderTest extends JFrame {

    public BorderTest(String title) {
        super(title);

        setLayout(new GridLayout(2, 4));

        // 静态工厂方法创建BevelBorder，可在组件四边随意配色。
        Border bevelBorder = BorderFactory.createBevelBorder(
                BevelBorder.RAISED,
                Color.RED,
                Color.GREEN
                , Color.BLUE,
                Color.GRAY
        );
        add(newBorderPanel(bevelBorder, "BevelBorder"));

        // 静态工厂方法创建LineBorder，可在组件四边增加边边的厚度。
        Border lineBorder = BorderFactory.createLineBorder(Color.ORANGE, 10);
        add(newBorderPanel(lineBorder, "LineBorder"));

        // 静态工厂方法创建EmptyBorder，组件四边透明，可指定类似Android的padding（内边距）。
        Border eb = BorderFactory.createEmptyBorder(30, 50, 10, 0);
        add(newBorderPanel(eb, "EmptyBorder"));

        // 静态工厂方法创建Etched
        Border etb = BorderFactory.createEtchedBorder(
                EtchedBorder.RAISED,
                Color.RED,//高亮颜色
                Color.GREEN//阴影颜色
        );
        add(newBorderPanel(etb, "EtchedBorder"));

        // 创建TitledBorder，可在原有边框加标题
        Border tb = new TitledBorder(
                lineBorder,
                "Test Title",
                TitledBorder.LEFT,//调整的位置
                TitledBorder.BOTTOM,//所在位置
                new Font("StSong", Font.BOLD, 18),
                Color.BLUE
        );
        add(newBorderPanel(tb, "TitledBorder"));

        // 直接创建EmptyBorder的子类MatteBorder，它可指定留空区域的颜色
        Border mb = new MatteBorder(20, 5, 10, 30, Color.BLUE);
        add(newBorderPanel(mb, "MatteBorder"));

        // 直接创建CompoundBorder，可包住另一个边框，形成双重边框
        CompoundBorder cb = new CompoundBorder(
                new LineBorder(Color.RED, 4),
                tb
        );
        add(newBorderPanel(cb, "CompoundBorder"));

        pack();
    }

    private JPanel newBorderPanel(Border border, String panelName) {
        JPanel p = new JPanel();
        //JPanel默认内容水平居中
        p.add(new JLabel(panelName));
        // 为Panel设置边框
        p.setBorder(border);
        return p;
    }

    public static void main(String[] args) {
        BorderTest borderTest = new BorderTest("BorderTest");
        borderTest.setVisible(true);
    }

}
