package code.java.view.container.applet.japplet;
//awt被淘汰多年了，请用swing库。
import javax.swing.*;
//awt means Abstract Window Toolkit
import java.awt.*;

/**
 * 测试JApplet方式之：网页测试方式
 *
 * Applet1.java代码：
 *
 * import javax.swing.*;
 * public class Applet1 extends JApplet
 * {
 *    public void init()
 *    {
 *        getContentPane().add(new JLabel("Applet!"));
 *    }
 * }
 *
 *
 * Applet1.html代码：
 *
 * <html>
 * <body>
 *     <!--code属性要对应applet的.class文件名-->
 *    <applet code=Applet1 width=100 height=50></applet>
 * </body>
 * </html>
 *
 * 注意:
 * 将.java转为.class，然后运行html才行。
 * .class和.html得同目录，或.html可寻到.class路径才行。
 *
 * */
public class AppletTestByHTML extends JApplet {
   //Html运行时，这种方式不行。
   /*public void init() {
        getContentPane().add(new JLabel("code.java.applet.AppletTestByHTML!"));
    }*/

    //这是Thinking in Java 给出的例子，但是不是继承JApplet而是Applet，可是测了也不行。
   // 貌似需要给Chrome和FireFox配置才行，暂时不知如何配置。
    public void paint(Graphics g) {
        g.drawString("code.java.applet.AppletTestByHTML", 10, 10);

        /**
         *
         * Thinking in Java 给出的Applet案例，测试不行，暂不知原因。
         *
         * With this information you can create a very simple applet:
         * //: Applet1.java
         * // Very simple applet
         * import java.awt.*;
         * import java.applet.*;
         * public class Applet1 extends Applet {
         *  public void paint(Graphics g) {
         *  g.drawString("First applet", 10, 10);
         *  }
         * } ///:~
         * Notice that applets have no main( ).
         * That’s all wired in to the application framework;
         * you put any
         * startup code in init( ).
         *
         * To run this program you must place it inside
         * a Web page and view that page inside your Java-enabled
         *
         * Web browser. To place an applet inside a Web page you
         * put a special tag inside the HTML source for
         * that Web page
         *
         * 1, to tell the page how to load and run the applet.
         * This is the applet tag, and it looks like
         * this for Applet1:
         * <applet
         * code=Applet1
         * width=200
         * height=200>
         * </applet>
         */
    }

    public static void main(String[] args) {

    }
}
