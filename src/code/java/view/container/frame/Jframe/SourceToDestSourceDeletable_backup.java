package code.java.view.container.frame.Jframe;

import javax.swing.*;
import java.awt.*;

public class SourceToDestSourceDeletable_backup extends FromSourceToDestWithTextAreaFrame {
    
    JCheckBox cbSourceDeletable;
    TextField tfPrefix;


    //子类扩展north panel
    protected void prepareNorthByChild(JPanel northGridPanel) {
        cbSourceDeletable = new JCheckBox("");
        tfPrefix = new TextField("");

        JPanel p = northGridPanel;
        p.setLayout(new GridLayout(4, 2));
        //填充左边视图
        p.add(new JLabel("复制后删来源", SwingConstants.RIGHT));
        p.add(cbSourceDeletable);
        //填充右边视图
        p.add(new JLabel("目标文件前缀", SwingConstants.RIGHT));
        p.add(tfPrefix);
    }

    public boolean isSourceDeletable() {
        return cbSourceDeletable.isSelected();
    }

    public String getDestFilePrefix() {
        return tfPrefix.getText();
    }

}
