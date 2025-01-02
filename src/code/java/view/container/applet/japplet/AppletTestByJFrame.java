package code.java.view.container.applet.japplet;

import code.java.utils.LU;
//awt被淘汰多年了，请用swing库。
import javax.swing.*;
//awt means Abstract Window Toolkit
import java.awt.*;

/**
 * JApplet测试方法之：用JFrame来承载JApplet。此方式最方便。
 */

public class AppletTestByJFrame extends JApplet {

    @Override
    public void init() {
        super.init();
        LU.println("init-》");
    }

    @Override
    public void start() {
        super.start();
        LU.println("start-》");
    }

    public void paint(Graphics g) {
        g.drawString("This is my first Applet.", 10, 10);
        g.draw3DRect(0, 0, 200, 40, true);
        LU.println("paint-》");
    }

    @Override
    public void stop() {
        super.stop();
        LU.println("stop-》");
    }

    @Override
    public void destroy() {
        super.destroy();
        LU.println("destroy-》");
    }

    public static void main(String[] args) {
        testAppletByJFrame();
    }

    private static void testAppletByJFrame() {
        JApplet applet = new AppletTestByJFrame();

        // JFrame can be a window and the container of JApplet.
        // Use JFrame to test Applet is really convenient.
        // create JFrame object.
        JFrame frame = new JFrame("AppletTest");

        //Configurates JFrame object.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(applet);
        frame.setSize(300, 300);

        //Calls applet methods.
        applet.init();
        applet.start();

        frame.setVisible(true);
    }

}
