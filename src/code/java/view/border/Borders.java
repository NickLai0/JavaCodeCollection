package code.java.view.border;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Demonstrate how to use various borders.
 * <p>
 * 1. Call a static method of the BorderFactory to create a border. You can choose among the following styles (see Figure 11.14):
 * • Lowered bevel
 * • Raised bevel
 * • Etched
 * • Line
 * • Matte
 * • Empty (just to create some blank space around the component)
 * <p>
 * 2. If you like, add a title to your border by passing your border to BorderFactory.createTitledBorder.
 * 3. If you really want to go all out, combine several borders with a call to BorderFactory.createCompoundBorder.
 * 4. Add the resulting border to your component by calling the setBorder method of the JComponent class. For example, here is how you add an etched（被侵蚀的） border with a title to a panel:
 * <p>
 * Border etched = BorderFactory.createEtchedBorder();
 * Border titled = BorderFactory.createTitledBorder(etched, "A Title");
 * panel.setBorder(titled);
 */
public class Borders extends JFrame {

    public static final String LOWERED_BEVEL = "Lowered bevel";
    public static final String RAISED_BEVEL = "Raised bevel";
    public static final String ETCHED = "Etched";
    public static final String LINE = "Line";
    public static final String MATTE = "Matte";
    public static final String EMPTY = "Empty ";

    public static String DEFAULT_BORDER = LOWERED_BEVEL;

    private JPanel panelRadioButtons;
    private JPanel jPanelCenter;
    private JPanel jPanelSouth;

    private ButtonGroup group;

    private JLabel label;

    private static final int DEFAULT_SIZE = 36;

    public Borders() {
        prepareNorth();
        prepareCenter();
        prepareSouth();
        pack();
    }

    private void prepareNorth() {
        // add the radio buttons
        panelRadioButtons = new JPanel();
        group = new ButtonGroup();
        addRadioButton("Lowered bevel", LOWERED_BEVEL);
        addRadioButton("Raised bevel", RAISED_BEVEL);
        addRadioButton("Etched", ETCHED);
        addRadioButton("Line", LINE);
        addRadioButton("Matte", MATTE);
        addRadioButton("Empty", EMPTY);
        add(panelRadioButtons, BorderLayout.NORTH);
    }

    private void prepareCenter() {
        // add the sample text label
        label = new JLabel("Border with no title.");
        label.setFont(new Font("Serif", Font.PLAIN, DEFAULT_SIZE));
        jPanelCenter = new JPanel();
        jPanelCenter.add(label);
        add(jPanelCenter, BorderLayout.CENTER);
    }

    private void prepareSouth() {
        // add the sample text label
        JLabel label = new JLabel("Titled Border.");
        label.setFont(new Font("Serif", Font.PLAIN, DEFAULT_SIZE));
        jPanelSouth = new JPanel();
        jPanelSouth.add(label);
        add(jPanelSouth, BorderLayout.SOUTH);
    }

    /**
     * Adds a radio button that sets the font size of the sample
     * text.
     *
     * @param name the string to appear on the button
     */
    public void addRadioButton(String name, String borderName) {
        boolean selected = DEFAULT_BORDER.equals(borderName);
        JRadioButton radio = new JRadioButton(name, selected);
        //居然设计成显添加到ButtonGroup后再添加到Panel，设计等不如android
        group.add(radio);
        panelRadioButtons.add(radio);
        radio.addActionListener(new BorderSetter(borderName));
    }

    class BorderSetter implements ActionListener {
        String borderName;

        BorderSetter(String borderName) {
            this.borderName = borderName;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Border border = null;
            switch (borderName) {
                case LOWERED_BEVEL:
                    border = BorderFactory.createLoweredBevelBorder();
                    break;
                case RAISED_BEVEL:
                    border = BorderFactory.createRaisedBevelBorder();
                    break;
                case ETCHED:
                    border = BorderFactory.createEtchedBorder();
                    break;
                case LINE:
                    border = BorderFactory.createLineBorder(Color.RED);
                    break;
                case MATTE:
                    border = BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLUE);
                    break;
                case EMPTY:
                default:
                    border = BorderFactory.createEmptyBorder();
                    break;
            }
            jPanelCenter.setBorder(border);
            Border titled = BorderFactory.createTitledBorder(border, borderName);
            jPanelSouth.setBorder(titled);
        }

    }

    public static void main(String[] args) {
        Borders borders = new Borders();
        borders.setTitle("Test various borders");
        borders.setVisible(true);
    }

}
