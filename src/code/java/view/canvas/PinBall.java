package code.java.view.canvas;


import code.java.adapter.WindowCloserAdapter;

import java.util.Random;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * Demonstrate how to draw filled circle、
 * rectangle by Ping Pong Game.
 * From 《疯狂Java讲义》（4th edition）.
 * I modified a little.
 *
 * 原理：每100毫秒改变一下PingPong的xy轴点， 然后绘制，
 * 碰到左右上和下边球拍边界，即改反方向，直至到达底部超出
 * 球拍边界，游戏结束。
 *
 * <p>
 * <p>
 * <p>
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
public class PinBall {
    // 桌面的宽度
    private final int TABLE_WIDTH = 300;
    // 桌面的高度
    private final int TABLE_HEIGHT = 400;
    // 球拍的垂直位置
    private final int RACKET_Y = 340;
    // 下面定义球拍的高度和宽度
    private final int RACKET_HEIGHT = 20;
    private final int RACKET_WIDTH = 160;
    // 小球的大小
    private final int BALL_SIZE = 16;
    private Frame f = new Frame("Ping Pong Game");
    Random rand = new Random();
    // 小球纵向的运行速度
    private int ySpeed = 10;
    // 返回一个-0.5~0.5的比率，用于控制小球的运行方向。
    private double xyRate = rand.nextDouble() - 0.5;
    // 小球横向的运行速度,ySpeed为10的情况下，取值区间为[-10，10]
    private int xSpeed = (int) (ySpeed * xyRate * 2);
    // ballX和ballY代表小球的坐标
    private int ballX = rand.nextInt(200) + 20;
    private int ballY = rand.nextInt(10) + 20;
    // racketX坐标
    private int racketX = rand.nextInt(200);
    private MyCanvas tableArea = new MyCanvas();
    Timer timer;
    // 游戏是否结束的旗标
    private boolean isLose = false;

    public void init() {
        // 设置桌面区域的最佳大小
        tableArea.setPreferredSize(
                new Dimension(TABLE_WIDTH, TABLE_HEIGHT)
        );
        f.add(tableArea);
        // 定义键盘监听器
        KeyAdapter keyProcessor = new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                // 按下向左、向右键时，球拍水平坐标分别减少、增加
                if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (racketX > 0)
                        racketX -= 10;
                }
                if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (racketX < TABLE_WIDTH - RACKET_WIDTH)
                        racketX += 10;
                }
            }
        };
        // 为窗口和tableArea对象分别添加键盘监听器
        f.addKeyListener(keyProcessor);
        f.addWindowListener(new WindowCloserAdapter());
        tableArea.addKeyListener(keyProcessor);
        // 定义每0.1秒执行一次的事件监听器。
        ActionListener timerActionListener = evt -> {
            // 如果小球碰到边框，调转为反方向
            if (ballX <= 0 || ballX >= TABLE_WIDTH - BALL_SIZE) {
                xSpeed = -xSpeed;
            }
            // 如果小球底部超出球拍Y轴位置，且在球拍外，游戏结束。
            if (ballY >= RACKET_Y - BALL_SIZE &&
                    (ballX < racketX || ballX > racketX + RACKET_WIDTH)) {
                timer.stop();
                // 设置游戏是否结束的旗标为true。
                isLose = true;
                tableArea.repaint();
            }
            // 如果小球位于球拍内，反弹
            else if (ballY <= 0 ||
                    (ballY >= RACKET_Y - BALL_SIZE
                            && ballX > racketX && ballX <= racketX + RACKET_WIDTH)) {
                //反弹
                ySpeed = -ySpeed;
            }
            // 小球坐标增加
            ballY += ySpeed;
            ballX += xSpeed;
            //请求重新绘制小球的新位置
            tableArea.repaint();
        };
        //定时器，每100毫秒执行一次。
        timer = new Timer(100, timerActionListener);
        timer.start();
        f.pack();
        f.setVisible(true);
    }

    public static void main(String[] args) {
        new PinBall().init();
    }

    class MyCanvas extends Canvas {
        // 重写Canvas的paint方法，实现绘画
        public void paint(Graphics g) {
            // 如果游戏已经结束
            if (isLose) {
                g.setColor(new Color(255, 0, 0));
                //Java Core 1: The third argument is the point size. Points are commonly used in typography（版面设计） to indicate the size of a font. There are 72 points per inch.
                g.setFont(new Font("Times", Font.BOLD, 30));
                g.drawString("游戏已结束！", 50, 200);
            }
            // 如果游戏还未结束
            else {
                // 设置颜色，并绘制小球
                g.setColor(new Color(240, 240, 80));
                g.fillOval(ballX, ballY, BALL_SIZE, BALL_SIZE);
                // 设置颜色，并绘制球拍
                g.setColor(new Color(80, 80, 200));
                g.fillRect(racketX, RACKET_Y, RACKET_WIDTH, RACKET_HEIGHT);
            }
        }
    }
}