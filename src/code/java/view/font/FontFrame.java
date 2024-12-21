package code.java.view.font;

import code.java.view.layout.GBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * From Java Core 1: Listing 11.7 gridbag/FontFrame.java
 * Modify a lot.
 * Demonstrate how to use Font、GridBagLayout、GridBagConstraints.
 *
 * <p>
 * <p>
 * A frame that uses a grid bag layout to arrange font
 * selection components.
 */
public class FontFrame extends JFrame {

    public static final int TEXT_ROWS = 10;
    public static final int TEXT_COLUMNS = 20;

    private JComboBox<String> face = new JComboBox<>(
            new String[]{
                    "Serif",
                    "SansSerif",
                    "Monospaced",
                    "Dialog",
                    "DialogInput"
            });

    private JComboBox<Integer> size = new JComboBox<>(new Integer[]{
            8, 10, 12, 15, 18, 24, 36, 48
    });

    private JCheckBox bold = new JCheckBox("Bold");
    private JCheckBox italic = new JCheckBox("Italic");
    private JTextArea sample = new JTextArea(TEXT_ROWS, TEXT_COLUMNS);

    ActionListener listener = event -> updateSample();

    public FontFrame() {
        initView();
        initListener();
        updateSample();
    }

    private void initView() {
        setLayout(new GridBagLayout());
        // construct components
        JLabel faceLabel = new JLabel("Face: ");
        JLabel sizeLabel = new JLabel("Size: ");

        sample.setText("The quick brown fox jumps over the lazy dog");
        sample.setEditable(false);
        sample.setLineWrap(true);
        sample.setBorder(BorderFactory.createEtchedBorder());

        // add components to grid, using GBC convenience class
        add(faceLabel, new GBC(0, 0)
                .setAnchor(GBC.EAST));//东（右）向对齐
        add(face, new GBC(1, 0)
                // speciﬁes the ﬁll behavior of the component inside the cell: one of NONE, BOTH, HORIZONTAL, or VERTICAL. The default is NONE.
                .setFill(GBC.HORIZONTAL).//横向填充剩余空间
                /*

                You always need to set the weight ﬁelds
                (weightx and weighty) for each area in a
                grid bag layout. If you set the weight to
                0, the area never grows or shrinks beyond
                its initial size in that direction.

                I recommend that you set all weights at 100
                */
                        setWeight(100/*X轴自动填充*/, 0).
                setInsets(1)//Padding
        );
        add(sizeLabel, new GBC(0, 1)
                .setAnchor(GBC.EAST));//东（右）向对齐
        add(size, new GBC(1, 1).
                setFill(GBC.HORIZONTAL).//横向填充剩余空间
                        setWeight(100, 0).
                setInsets(1));//Padding
        add(bold, new GBC(0, 2, 2, 1).
                setAnchor(GBC.CENTER).//中间对齐
                        setWeight(100, 100));//X，Y轴自动填充
        add(italic, new GBC(0, 3, 2, 1).
                setAnchor(GBC.CENTER).//中间对齐
                        setWeight(100, 100));//两边填充

        add(sample, new GBC(2, 0, 1/*宽占一个单元格*/, 4/*高占四个单元格*/).
                setFill(GBC.BOTH)//一开始的时候两边填充剩余空间
                .setWeight(100, 100)//拖拉拽时两边自适应
        );

        pack();
    }

    private void initListener() {
        face.addActionListener(listener);
        size.addActionListener(listener);
        bold.addActionListener(listener);
        italic.addActionListener(listener);
    }

    public void updateSample() {
        var fontFace = (String) face.getSelectedItem();
        int fontStyle = (bold.isSelected() ? Font.BOLD : 0)
                + (italic.isSelected() ? Font.ITALIC : 0);
        int fontSize = size.getItemAt(size.getSelectedIndex());
        var font = new Font(fontFace, fontStyle, fontSize);
        sample.setFont(font);
        sample.repaint();
    }

    public static void main(String[] args) {
        new FontFrame().setVisible(true);
    }
}
