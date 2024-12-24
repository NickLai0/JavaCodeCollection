package code.java.view.table.JTable;

import java.awt.*;
import java.awt.print.*;
import javax.swing.*;

/**
 * From Core Java 2.
 * Modify a little.
 * This program demonstrates how to show a simple table.
 *
 * @author Cay Horstmann
 * @version 1.14 2018-05-01
 */
public class TableTest {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            PlanetTableFrame frame = new PlanetTableFrame();
            frame.setTitle("TableTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

/**
 * This frame contains a table of planet data.
 */
class PlanetTableFrame extends JFrame {

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
        table.setAutoCreateRowSorter(true);
        add(new JScrollPane(table), BorderLayout.CENTER);
        var printButton = new JButton("Print");
        printButton.addActionListener(event -> {
            try {
                table.print();
            } catch (SecurityException | PrinterException e) {
                e.printStackTrace();
            }
        });
        var buttonPanel = new JPanel();
        buttonPanel.add(printButton);
        add(buttonPanel, BorderLayout.SOUTH);
        pack();
    }

}
