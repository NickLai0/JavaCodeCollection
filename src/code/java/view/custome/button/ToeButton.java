package code.java.view.custome.button;

import code.java.view.dialog.ToeDialog;

import java.awt.*;

// from Thinking in Java，modified by myself。
public class ToeButton extends Canvas {

    private static final int STATE_DRAW_NONE = 0;
    private static final int STATE_DRAW_X = 1;
    private static final int STATE_DRAW_O = 2;

    int state = 0;

    ToeDialog toeDialog;

    public ToeButton(ToeDialog parent) {
        this.toeDialog = parent;
    }

    public void paint(Graphics g) {
        int x1 = 0;
        int y1 = 0;
        //x轴起点为0，所以要减1才得出正确的x2轴点，即自身宽度。
        int x2 = size().width - 1;
        //y轴起点为0，所以要减1才得出正确的y2轴点，即自身高度。
        int y2 = size().height - 1;
        //Draw the frame(four sides).
        g.drawRect(x1, y1, x2, y2);

        //x1y1起点为宽高的四分之一
        x1 = x2 / 4;
        y1 = y2 / 4;
        //线长和圆直径是宽高的二分之一
        int wide = x2 / 2;
        int high = y2 / 2;


        switch (state) {
            case STATE_DRAW_X:
                //draw the '\' line.
                // x1本身就偏移了宽度的四分之一，画线为宽度的二分之一，则末尾边距宽度的四分之一（外部为正方形情况下）。
                // y1同上。
                g.drawLine(x1, y1, x1 + wide, y1 + high);
                //draw the '/' line.
                // 这里的画线和上面原理一样，只是画相反方向而已。
                g.drawLine(x1, y1 + high, x1 + wide, y1);
                break;

            case STATE_DRAW_O:
                //draw a circle here.
                g.drawOval(x1, y1, x1 + wide / 2, y1 + high / 2);
                break;
        }
    }

    public boolean mouseDown(Event evt, int x, int y) {
        changeState();
        //notify to repaint.
        repaint();
        return true;
    }

    private void changeState() {
        switch (state) {
            case STATE_DRAW_NONE:
                state = STATE_DRAW_X;
                break;
            case STATE_DRAW_X:
                state = STATE_DRAW_O;
                break;
            case STATE_DRAW_O:
                state = STATE_DRAW_X;
                break;
        }
    }

}
