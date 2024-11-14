package code.java.view.image;


import javax.swing.*;
import java.awt.*;
import java.io.File;

import static code.java.utils.LU.println;
import static code.java.utils.ProjectFileUtils.getImageDir;

/*
Demonstrating how to draw an image
and copy it to the area of component.
*/
//
public class ShowImageDemo {

    public static void main(String[] args) {
        EventQueue.invokeLater(() ->//==new Runnable(){public void run(){...}}
        {

            var frame = new JFrame("ShowImageDemo");
            frame.add(new ShowImageComponent());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);

//            println("ImageDir=" + getImageDir() + ", exist=" + getImageDir().exists());
        });
    }

}

class ShowImageComponent extends JComponent {

    @Override
    protected void paintComponent(Graphics g) {
        println("paintComponent->");
        paintImageIcons(g);
    }

    private boolean onlyPaintIconsOnce = true;

    private void paintImageIcons(Graphics g) {
        println("paintImageIcons-> ");

        ImageIcon imageIcon = new ImageIcon(
                new File(getImageDir(), "ic_finished.png").toString()
        );

        //draw image
        Image image = imageIcon.getImage();
        g.drawImage(image, 0, 0, null);

        int imageWidth = imageIcon.getIconWidth();
        int imageHeight = imageIcon.getIconWidth();
        //coping icon from top to bottom and then from left to right
        //on the component.
        for (int i = 0; i * imageWidth <= getWidth(); i++) {
            //外循环结束后，行列iconds就copy完毕
            for (int j = 0; j * imageHeight <= getHeight(); j++) {
                //每内循环结束后，一列icons就copy完毕。
                if (i + j > 0) {
                    //Skip first copy,because there was already an image there.
                    continue;
                }
                g.copyArea(
                        //copy from
                        0, 0, imageWidth, imageHeight,
                        //copy to
                        i * imageWidth, j * imageHeight
                );
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300, 300);
    }
}
