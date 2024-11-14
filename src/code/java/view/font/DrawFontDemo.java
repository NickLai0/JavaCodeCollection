package code.java.view.font;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

class DrawFontButton extends Button {

    int clickCounter;

    String label = "";

    public DrawFontButton() {
        super("Click me or move the mouse on me.");
        addActionListener(new AL());
    }

    public void paint(Graphics g) {
        g.setColor(Color.green);
        Dimension s = getSize();
        g.fillRect(0, 0, s.width, s.height);
        g.setColor(Color.black);
        g.drawRect(0, 0, s.width - 1, s.height - 1);
        drawLabel(g);
    }

    private void drawLabel(Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int width = fm.stringWidth(label);
         /*
         字体高度=
         ascent（字母上部分，含出头部分）
         + descent（字母下面出头部分）
         + leading（本行跟下一行的间隙）
        */
        int height = fm.getHeight();
        int ascent = fm.getAscent();
        int leading = fm.getLeading();
        int horizMargin = (getSize().width - width) / 2;
        int verMargin = (getSize().height - height) / 2;
        g.setColor(Color.red);
        // 添加ascent使得y达到baseline，添加leading，使得本行开始就跟上一行保持间隙
        g.drawString(label, horizMargin, verMargin + ascent + leading);
    }

    class AL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            clickCounter++;
            label = "click #" + clickCounter + " " + e.toString();
            repaint();
        }
    }
}

public class DrawFontDemo extends Frame {

    DrawFontButton drawFontButton = new DrawFontButton();

    public DrawFontDemo() {
        setLayout(new FlowLayout());
        add(drawFontButton);
        drawFontButton.addActionListener(new B1());
        addWindowListener(new WL());
    }

    public class B1 implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println("Button 1 pressed");
        }
    }

    class WL extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            System.out.println("Window Closing");
            System.exit(0);
        }
    }

    public static void main(String args[]) {
        Frame f = new DrawFontDemo();
        f.setSize(400, 300);
        f.setVisible(true);
    }
}