package code.java.view.japplet;
//awt被淘汰多年了，请用swing库。
import javax.swing.*;

/**
 * JApplet测试方法之：使用applet viewer工具
 *
 * 按：这个也不行！不知道是方法错了还是不支持了。
 *
 * 注释:
 * sun提供的Appletviewer可从源码文件或HTML文件中抽取出
 * <applet>标签，然后只运行这个applet而忽略HTML周围文本内容。
 */
//标签作为注释直接放在Java源代码文件里面。
//<applet code=code.java.applet.AppletTestByAppletviewer width=100 height=50></applet>
public class AppletTestByAppletviewer extends JApplet {

    public void init() {
        getContentPane().add(new JLabel("Applet!"));
    }

    /*public void paint(Graphics g) {
        g.drawString("Applet test By applet viewer", 10, 10);
    }*/


}
