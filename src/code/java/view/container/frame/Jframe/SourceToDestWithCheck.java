package code.java.view.container.frame.Jframe;

import javax.swing.*;
import java.awt.*;

public abstract class SourceToDestWithCheck extends FromSourceToDestWithTextAreaFrame {

    JCheckBox cb;
    TextField tf;

    //子类扩展north panel
    protected void prepareNorthByChild(JPanel northGridPanel) {
        cb = new JCheckBox("");
        tf = new TextField("");

        JPanel p = northGridPanel;
        p.setLayout(new GridLayout(4, 2));

        //填充左边视图
        p.add(new JLabel(getCheckBoxLabel(), SwingConstants.RIGHT));
        p.add(cb);

        //填充右边视图
        p.add(new JLabel(getTextFieldLabel(), SwingConstants.RIGHT));
        p.add(tf);
    }

    abstract protected String getTextFieldLabel();

    abstract protected String getCheckBoxLabel();

    public boolean isCheck() {
        return cb.isSelected();
    }

    public String getText() {
        return tf.getText();
    }

}
