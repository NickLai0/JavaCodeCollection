package code.java.view.game;

/*
* dialog boxes

This is a direct rewrite of the earlier ToeTest.java.
* In this version, however, everything is placed
* inside an inner class.
*
* Although this completely eliminates
* the need to keep track of the object that spawned(led/caused/引起)
any class, as was the case in ToeTest.java,
* it may be taking the concept of inner classes
* a bit too far. At one point, the inner classes
* are nested four deep! This is the kind of design
* where you need to decide
whether the benefit of inner classes is worth
* the increased complexity. In addition, when
* you create
an inner class you’re tying that class to its
*  surrounding class. A standalone class can
* more easily be reused.
* */

//: ToeTestNew.java
// Demonstration of dialog boxes
// and creating your own components

import java.awt.*;
import java.awt.event.*;
//This demo is good at using listeners，
//but not that good at using inner classes。
public class ToeGameNew extends Frame {
    TextField rows = new TextField("3");
    TextField cols = new TextField("3");

    public ToeGameNew() {
        setTitle("Toe Test");

        Panel p = new Panel();
        p.setLayout(new GridLayout(2, 2));
        p.add(new Label("Rows", Label.CENTER));
        p.add(rows);
        p.add(new Label("Columns", Label.CENTER));
        p.add(cols);
        add(p, BorderLayout.NORTH);

        Button b = new Button("go");
        b.addActionListener(new BL());
        add(b, BorderLayout.SOUTH);
    }

    static final int XX = 1;
    static final int OO = 2;

    class ToeDialog extends Dialog {
        // w = number of cells wide
        // h = number of cells high
        int turn = XX; // Start with x's turn

        public ToeDialog(int w, int h) {
            super(ToeGameNew.this,
                    "The game itself", false);
            setLayout(new GridLayout(w, h));
            for (int i = 0; i < w * h; i++)
                add(new ToeButton());
            setSize(w * 50, h * 50);
            addWindowListener(new WLD());
        }

        class ToeButton extends Canvas {
            int state = 0;

            ToeButton() {
                addMouseListener(new ML());
            }

            public void paint(Graphics g) {
                int x1 = 0;
                int y1 = 0;
                int x2 = getSize().width - 1;
                int y2 = getSize().height - 1;
                g.drawRect(x1, y1, x2, y2);
                x1 = x2 / 4;
                y1 = y2 / 4;
                int wide = x2 / 2;
                int high = y2 / 2;
                if (state == 1) {
                    g.drawLine(x1, y1,
                            x1 + wide, y1 + high);
                    g.drawLine(x1, y1 + high,
                            x1 + wide, y1);
                }
                if (state == 2) {
                    g.drawOval(x1, y1,
                            x1 + wide / 2, y1 + high / 2);
                }
            }

            class ML extends MouseAdapter {
                public void mousePressed(MouseEvent e) {
                    if (state == 0) {
                        state = turn;
                        turn = (turn == 1 ? 2 : 1);
                    } else
                        state = (state == 1 ? 2 : 1);
                    repaint();
                }
            }
        }

        class WLD extends WindowAdapter {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        }
    }

    class BL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Dialog d = new ToeDialog(
                    Integer.parseInt(rows.getText()),
                    Integer.parseInt(cols.getText()));
            d.show();
        }
    }

    static class WL extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }

    public static void main(String args[]) {
        Frame f = new ToeGameNew();
        f.setSize(200, 100);
        f.setVisible(true);
        f.addWindowListener(new WL());
    }
}

/*
* There are some restrictions when using inner classes here.
* In particular(especially), statics can only be at the outer
level of the class, so inner classes cannot have static data
* or inner classes.
* */