package code.java.view.applet;

import code.java.view.custome.button.MyButton;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.util.Hashtable;

//用自定义控件MyButton来测试如下事件，并自动显示x，y轴坐标和键盘码等：
// "keyDown",
// "keyUp",
// "lostFocus",
// "gotFocus",
// "mouseDown",
// "mouseUp",
// "mouseMove",
// "mouseDrag",
// "mouseEnter",
// "mouseExit"
public class AutoEvent extends Applet {
    public Hashtable h = new Hashtable();
    String event[] = {
            "keyDown",
            "keyUp",
            "lostFocus",
            "gotFocus",
            "mouseDown",
            "mouseUp",
            "mouseMove",
            "mouseDrag",
            "mouseEnter",
            "mouseExit"
    };

    //Attach parent when create MyButton object.
    MyButton b1 = new MyButton(this, Color.blue, "test1");
    MyButton b2 = new MyButton(this, Color.red, "test2");

    public void init() {
        setLayout(new GridLayout(event.length + 1, 2));
        for (int i = 0; i < event.length; i++) {
            TextField t = new TextField();
            t.setEditable(false);
            //GridLayout网格布局的左边放Label标签，文字来源数组
            add(new Label(event[i], Label.CENTER));
            //GridLayout网格布局的右边放TextField字段，文字来源MyButton的实时键盘按钮事件监控
            add(t);
            h.put(event[i], t);
        }
        add(b1);
        add(b2);
    }

    public static void main(String[] args) {
        testAutoEvent();
    }

    private static void testAutoEvent() {
        Applet applet = new AutoEvent();

        // JFrame can be a window and the container of JApplet.
        // Use JFrame to test Applet is really convenient.
        // create JFrame object.
        JFrame frame = new JFrame("Testing MyButton");

        //Configurates JFrame object.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(applet);
        frame.setSize(900, 500);

        //Calls applet methods.
        applet.init();
        applet.start();

        frame.setVisible(true);
    }
}