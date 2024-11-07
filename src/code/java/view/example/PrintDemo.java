package code.java.view.example;
/*

Demonstrate how to print strings and graphics.
(演示打印机打印字符串、图形，用Canon TS3380测试了一下，可以。)
You can also see how to use printer by Java code.
Modified by myself.

new Java 1.1 UI APIs

Java 1.1 has also added some important new functionality,
including focus traversal, desktop color
access, printing “inside the sandbox” and
 the beginnings of clipboard support.

Focus traversal is quite easy, since it’s transparently
present in the AWT library components and you
don’t have to do anything to make it work.
If you make your own components and want them to
handle focus traversal, you override isFocusTraversable( )
to return true. If you want to capture the
keyboard focus on a mouse click,
you catch the mouse down event and call requestFocus( ).

desktop colors

The desktop colors provide a way for you
to know what the various color choices are on the current
user’s desktop. This way, you can use
those colors in your program, if you desire. The colors are
automatically initialized and placed in
static members of class SystemColor, so all you need to do is
read the member you’re interested in.
The names are intentionally self-explanatory(一目了然/自我解释):
desktop, activeCaption, activeCaptionText, activeCaptionBorder,
inactiveCaption, inactiveCaptionText, inactiveCaptionBorder,
window, windowBorder, windowText, menu, menuText, text, textText,
textHighlight, textHighlightText, textInactiveText, control,
controlText, controlHighlight, controlLtHighlight, controlShadow,
controlDkShadow, scrollbar, info (for help), infoText (for help text).

printing

There’s some confusion involved with Java 1.1 printing support.
Some of the publicity（宣传/announcement） seemed to claim
that you’d be able to print from within an applet.
However, to print anything you must get a PrintJob
object through a Toolkit object’s getPrintJob( ) method,
which only takes a Frame object, and not an
Applet. Thus it is only possible to print from
within an application, not an applet.
Unfortunately, there isn’t much that’s automatic with printing;
instead you must go through a number
        of mechanical, non-OO steps in order to print. Printing a component graphically can be slightly more
automatic: by default, the print( ) method calls paint( ) to do it’s work. There are times when this is
satisfactory, but if you want to do anything more specialized you must know that you’re printing so you
can in particular find out the page dimensions.

The following example demonstrates the printing of
both text and graphics, and the different
approaches you can use for printing graphics.
In addition, it tests the printing support (printing in Java
        1.1 is a bit “fragile” and the example explores this):

*/
//: PrintDemo.java
// Printing with Java 1.1

import java.awt.*;
import java.awt.event.*;

public class PrintDemo extends Frame {

    Button
            printText = new Button("Print Text"),
            printGraphics = new Button("Print Graphics");

    TextField ringNum = new TextField(3);

    Choice faces = new Choice();

    //打印机的图形对象
    Graphics printerGraphics = null;

    Plot plot = new Plot3(); // Try different plots

    //可通过Toolkit来获取fontList（字体列表）
    Toolkit tk = Toolkit.getDefaultToolkit();

    public PrintDemo() {
        ringNum.setText("3");
        ringNum.addTextListener(new TrackingRingsTextListener());
        Panel p = new Panel();
        p.setLayout(new FlowLayout());
        printText.addActionListener(new PrintTextActionListener());
        p.add(printText);
        p.add(new Label("Font:"));
        p.add(faces);
        printGraphics.addActionListener(new PrintGraghicActionListener());
        p.add(printGraphics);
        p.add(new Label("Rings:"));
        p.add(ringNum);
        setLayout(new BorderLayout());

        add(p, BorderLayout.NORTH);
        add(plot, BorderLayout.CENTER);
        String fontList[] = tk.getFontList();
        for (int i = 0; i < fontList.length; i++)
            faces.add(fontList[i]);
        faces.select("Serif");
    }

    class PrintData {
        public PrintJob printJob;
        public int pageWidth, pageHeight;

        PrintData(String jobName) {
            /*
            However, to print anything you must get a PrintJob
            object through a Toolkit object’s getPrintJob( ) method,
            which only takes a Frame object, and not an
            Applet. Thus it is only possible to print from
            within an application, not an applet.

......
            whenever you want to begin a print job
        (whether for graphics or text), you must create a
        PrintJob object, which has its own Graphics object along with
        the width and height of the page
            */
            // Returns: a PrintJob object, or null if the user
            // cancelled the print job.
            printJob = getToolkit().getPrintJob(
                    PrintDemo.this, jobName, null);
            if (printJob != null) {
                pageWidth = printJob.getPageDimension().width;
                pageHeight = printJob.getPageDimension().height;
                //获取打印机的图像对象
                printerGraphics = printJob.getGraphics();
            }
        }

        void end() {
            printJob.end();
        }
    }

    //定义一个数据结构来做字体的改变存储，设计一流。
    class ChangeFont {
        private int stringHeight;

        ChangeFont(String face, int style, int point) {
            if (printerGraphics != null) {
                printerGraphics.setFont(new Font(face, style, point));
                stringHeight = printerGraphics.getFontMetrics().getHeight();
            }
        }

        int calculateStringWidth(String s) {
            return printerGraphics.getFontMetrics().stringWidth(s);
        }

        int getStringHeight() {
            return stringHeight;
        }
    }

    //打印文本的ActionListener，事件一触发，则获取PrintJob对象来打印。
    class PrintTextActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            PrintData pd = new PrintData("Print Text Test");
            //pd.pj(PrintJob object) is Null means print job canceled:
            if (pd.printJob == null) {
                return;
            }

            String s = "Thinking In Java";

            ChangeFont cf = new ChangeFont(
                    faces.getSelectedItem(),//字体
                    Font.ITALIC, //斜体
                    72//文字72像素大小
            );

            printerGraphics.drawString(
                    s,
                    //字符串左右居中
                    (pd.pageWidth - cf.calculateStringWidth(s)) / 2,
                    //字符串下移除当前（减去字符串高度）页面30%的边距
                    (pd.pageHeight - cf.getStringHeight()) / 3
            );

            s = "PrintDemo";
            cf = new ChangeFont(
                    faces.getSelectedItem(), Font.ITALIC, 50);
            printerGraphics.drawString(s,
                    //字符串左右居中
                    (pd.pageWidth - cf.calculateStringWidth(s)) / 2,
                    //字符串下移除当前（减去字符串高度）页面50%的边距
                    (int) ((pd.pageHeight - cf.getStringHeight()) / 2));

            s = "A smaller point size";
            cf = new ChangeFont(
                    faces.getSelectedItem(),
                    Font.BOLD,
                    48
            );
            printerGraphics.drawString(
                    s,
                    //字符串左右居中
                    (pd.pageWidth - cf.calculateStringWidth(s)) / 2,
                    //字符串下移除当前（减去字符串高度）页面66.6%的边距
                    (int) ((pd.pageHeight - cf.getStringHeight()) / 1.5)
            );
            printerGraphics.dispose();
            pd.end();
        }
    }

    //打印图形的ActionListener,一点击打印，则获取PrintJob去将图像打印出来。
    class PrintGraghicActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            PrintData pd = new PrintData("Print Graphics Test");
            if (pd == null) return;
            plot.print(printerGraphics);
            printerGraphics.dispose();
            pd.end();
        }
    }

    class TrackingRingsTextListener implements TextListener {
        public void textValueChanged(TextEvent e) {
            int i = 1;
            try {
                i = Integer.parseInt(ringNum.getText());
            } catch (NumberFormatException ex) {
                i = 1;
            }
            plot.rings = i;
            plot.repaint();
        }
    }

    static class WL extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }

    public static void main(String args[]) {
        Frame pdemo = new PrintDemo();
        pdemo.setTitle("Print Demo");
        pdemo.addWindowListener(new WL());
        pdemo.setSize(500, 500);
        pdemo.setVisible(true);
    }
}

class Plot extends Canvas {
    public int rings = 3;
}

class Plot1 extends Plot {
    // Default print() calls paint():
    public void paint(Graphics g) {
        int w = getSize().width;
        int h = getSize().height;
        int xc = w / 2;
        int yc = w / 2;
        int x = 0, y = 0;
        for (int i = 0; i < rings; i++) {
            if (x < xc && y < yc) {
                g.drawOval(x, y, w, h);
                x += 10;
                y += 10;
                w -= 20;
                h -= 20;
            }
        }
    }
}

class Plot2 extends Plot {
    // To fit the picture to the page, you must
    // know whether you're printing or painting:

    public void paint(Graphics g) {
        int w, h;
        if (g instanceof PrintGraphics) {
            //printing something.
            PrintJob pj =
                    ((PrintGraphics) g).getPrintJob();
            w = pj.getPageDimension().width;
            h = pj.getPageDimension().height;
        } else {
            w = getSize().width;
            h = getSize().height;
        }
        int xc = w / 2;
        int yc = w / 2;
        int x = 0, y = 0;
        for (int i = 0; i < rings; i++) {
            if (x < xc && y < yc) {
                g.drawOval(x, y, w, h);
                x += 10;
                y += 10;
                w -= 20;
                h -= 20;
            }
        }
    }
}

class Plot3 extends Plot {
    // Somewhat（a little） better. Separate
    // printing from painting:（打印和绘制分离，更好）
    public void print(Graphics g) {

        // Assume it's a PrintGraphics object:
        PrintJob pj =
                ((PrintGraphics) g).getPrintJob();
        int w = pj.getPageDimension().width;
        int h = pj.getPageDimension().height;
        drawRings(g, w, h);
    }

    public void paint(Graphics g) {
        int w = getSize().width;
        int h = getSize().height;
        drawRings(g, w, h);
    }

    //画环形，有多少个就画多少个。
    private void drawRings(Graphics g, int w, int h) {
        int xc = w / 2;

        //int yc = w / 2;
        //求Y的center点应该是用h来除以2才对，上为教科书源码，我认为是错误的，所以改为下面
        int yc = h / 2;

        int x = 0, y = 0;
        for (int i = 0; i < rings; i++) {
            if (x < xc && y < yc) {
                //画圆
                g.drawOval(x, y, w, h);
                //x,y偏移10像素
                x += 10;
                y += 10;
                //w，h要减少20像素，形成的效果才是上下左右都有10像素margin值。
                w -= 20;
                h -= 20;
            }
        }
    }
} /*

The program allows you to select fonts
from a Choice list (and you’ll see that the number of fonts
available in Java 1.1 is still extremely limited,
and has nothing to do with any extra fonts you install on
your machine). It uses these to print out text in bold and italic
and in different sizes. In addition, a new
type of component called a Plot is created to demonstrate graphics.
A Plot has rings that it will display
on the screen and print onto paper,
and the three derived classes Plot1, Plot2 and Plot3 perform these
tasks in different ways so you can see your alternatives
when printing graphics. Also, you can change
the number of rings in a plot –
this is interesting because it shows the printing fragility in Java 1.1,
since
on my system the printer gave error messages and didn’t print correctly
when the ring count got “too high” (whatever that means),

//按：On my system is good for that.

but worked fine when the count was “low enough.” You will notice, too,
that the page dimensions produced when printing do not seem to
correspond to the actual
dimensions of the page. This may be fixed in a future release of Java,
and you can use this program to test it.
This program encapsulates functionality inside inner classes
whenever possible, to facilitate（使...便利） reuse. For
example, whenever you want to begin a print job
(whether for graphics or text), you must create a
PrintJob object, which has its own Graphics object along with
the width and height of the page. The
creation of a PrintJob and extraction of page dimensions
is encapsulated in the PrintData class.
*/