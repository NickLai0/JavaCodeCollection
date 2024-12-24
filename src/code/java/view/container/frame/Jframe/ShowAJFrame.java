package code.java.view.container.frame.Jframe;

import javax.swing.JFrame;

public class ShowAJFrame {

    public static void main(String[] args) {
        JFrame myFrame = new JFrame();
        myFrame.setTitle("Blank Frame");
        myFrame.setSize(300, 300);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setVisible(true);
    }

}