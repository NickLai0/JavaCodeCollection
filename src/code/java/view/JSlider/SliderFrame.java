package code.java.view.JSlider;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;


/**
 * From Java Core 1. Modify a little.
 *
 * A frame with many sliders and a text field to show slider values.
 */
public class SliderFrame extends JFrame {

    private JPanel sliderPanel = new JPanel();
    private JTextField textField = new JTextField();

    private ChangeListener listener = event -> {
        // update text field when the slider value changes
        JSlider source = (JSlider) event.getSource();
        textField.setText("" + source.getValue());
    };

    public SliderFrame() {
        sliderPanel.setLayout(new GridBagLayout());

        // add a plain slider
        var slider = new JSlider();
        addSlider(slider, "Plain");

        // add a slider with major and minor ticks
        slider = new JSlider();
        slider.setPaintTicks(true);//显示标记
        slider.setMajorTickSpacing(20);//每个主要标记间隔20个单元
        slider.setMinorTickSpacing(5);//每个次要标记间隔25个单元
        addSlider(slider, "Ticks");

        // add a slider that snaps to ticks
        slider = new JSlider();
        slider.setPaintTicks(true);
        slider.setSnapToTicks(true);//标记拉拽后自动对齐
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        addSlider(slider, "Snap to ticks");

        // add a slider with no track
        slider = new JSlider();
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        slider.setPaintTrack(false);//不要进度条
        addSlider(slider, "No track");

        // add an inverted slider
        slider = new JSlider();
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        slider.setInverted(true);//反向进度条
        addSlider(slider, "Inverted");

        // add a slider with numeric labels
        slider = new JSlider();
        slider.setPaintTicks(true);
        slider.setPaintLabels(true); //允许绘制标签，默认是数值:0 20 ... 100
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        addSlider(slider, "Labels");

        // add a slider with alphabetic labels
        slider = new JSlider();
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);//允许绘制标签
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        //按照自定义标签表绘制
        var labelTable = new Hashtable<Integer, Component>();
        labelTable.put(0, new JLabel("A"));
        labelTable.put(20, new JLabel("B"));
        labelTable.put(40, new JLabel("C"));
        labelTable.put(60, new JLabel("D"));
        labelTable.put(80, new JLabel("E"));
        labelTable.put(100, new JLabel("F"));
        slider.setLabelTable(labelTable);
        addSlider(slider, "Custom labels");

        // add a slider with icon labels
        slider = new JSlider();
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);//允许绘制标签
        slider.setSnapToTicks(true);//允许拉拽后自动对齐
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(20);//次要标记单元也是20

        labelTable = new Hashtable<Integer, Component>();
        // add card images（按：没有这些图片,所以展示不出来）
        labelTable.put(0, new JLabel(new ImageIcon("nine.gif")));
        labelTable.put(20, new JLabel(new ImageIcon("ten.gif")));
        labelTable.put(40, new JLabel(new ImageIcon("jack.gif")));
        labelTable.put(60, new JLabel(new ImageIcon("queen.gif")));
        labelTable.put(80, new JLabel(new ImageIcon("king.gif")));
        labelTable.put(100, new JLabel(new ImageIcon("ace.gif")));
        slider.setLabelTable(labelTable);
        addSlider(slider, "Icon labels");

        // add the text field that displays the slider value
        add(sliderPanel, BorderLayout.CENTER);
        add(textField, BorderLayout.SOUTH);

        pack();
    }

    /**
     * Adds a slider to the slider panel and hooks up the
     * listener
     *
     * @param slider      the slider
     * @param description the slider description
     */
    public void addSlider(JSlider slider, String description) {
        var panel = new JPanel();
        panel.add(slider);
        panel.add(new JLabel(description));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        //给GridBagLayout添加约束
        var gbc = new GridBagConstraints();
        //指定在Y轴的位置
        gbc.gridy = sliderPanel.getComponentCount();
        //这应该指左对齐
        gbc.anchor = GridBagConstraints.WEST;
        //GridBagLayout添加

        sliderPanel.add(panel, gbc);

        slider.addChangeListener(listener);
    }

    public static void main(String[] args) {
        new SliderFrame().setVisible(true);
    }
}
