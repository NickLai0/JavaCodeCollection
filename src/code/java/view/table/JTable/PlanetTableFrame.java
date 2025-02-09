package code.java.view.table.JTable;

import code.java.utils.FrameUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.print.PrinterException;

/**
 * From Core Java 2.
 * Modified.
 * PlanetTableFrame2 shows more demos.
 * <p>
 * This frame contains a table of planet data.
 */
public class PlanetTableFrame extends JFrame {

    private String[] columnNames = {
            "Planet", "Radius", "Moons", "Gaseous", "Color"
    };

    private Object[][] cells =
            {
                    {"Mercury", 2440.0, 0, false, Color.YELLOW},
                    {"Venus", 6052.0, 0, false, Color.YELLOW},
                    {"Earth", 6378.0, 1, false, Color.BLUE},
                    {"Mars", 3397.0, 2, false, Color.RED},
                    {"Jupiter", 71492.0, 16, true, Color.ORANGE},
                    {"Saturn", 60268.0, 18, true, Color.ORANGE},
                    {"Uranus", 25559.0, 17, true, Color.BLUE},
                    {"Neptune", 24766.0, 8, true, Color.BLUE},
                    {"Pluto", 1137.0, 1, false, Color.BLACK}
            };

    public PlanetTableFrame() {
        JTable table = new JTable(cells, columnNames);
        //自动创建行排序器
        table.setAutoCreateRowSorter(true);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton printButton = new JButton("Print");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(printButton);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();

        printButton.addActionListener(event -> {
            try {
                table.print();
            } catch (SecurityException | PrinterException e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(PlanetTableFrame.class)
                .setTitle("Planet Table");
    }

}

