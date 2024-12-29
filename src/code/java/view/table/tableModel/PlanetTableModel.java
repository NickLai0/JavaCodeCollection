package code.java.view.table.tableModel;

import java.awt.*;
import javax.swing.table.*;

import static code.java.utils.ImageUtils.newImageIcon;

/**
 * From Java Core 2
 * Modify a lot.
 * <p>
 * The planet table model specifies the values, rendering
 * and editing properties for the
 * planet data.
 */
public class PlanetTableModel extends AbstractTableModel {

    public static final int PLANET_COLUMN = 0;
    public static final int MOONS_COLUMN = 2;
    public static final int GASEOUS_COLUMN = 3;
    public static final int COLOR_COLUMN = 4;

    private Object[][] cells =
            {
                    //There are no many images, so all use the same gif image.
                    {"Mercury", 2440.0, 0, false, Color.YELLOW, newImageIcon("Mercury.gif")},
                    {"Venus", 6052.0, 0, false, Color.YELLOW, newImageIcon("Mercury.gif")},
                    {"Earth", 6378.0, 1, false, Color.BLUE, newImageIcon("Mercury.gif")},
                    {"Mars", 3397.0, 2, false, Color.RED, newImageIcon("Mercury.gif")},
                    {"Jupiter", 71492.0, 16, true, Color.ORANGE, newImageIcon("Mercury.gif")},
                    {"Saturn", 60268.0, 18, true, Color.ORANGE, newImageIcon("Mercury.gif")},
                    {"Uranus", 25559.0, 17, true, Color.BLUE, newImageIcon("Mercury.gif")},
                    {"Neptune", 24766.0, 8, true, Color.BLUE, newImageIcon("Mercury.gif")},
                    {"Pluto", 1137.0, 1, false, Color.BLACK, newImageIcon("Mercury.gif")}
            };

    private String[] columnNames = {"Planet", "Radius", "Moons", "Gaseous", "Color", "Image"};

    public String getColumnName(int c) {
        return columnNames[c];
    }

    public Class<?> getColumnClass(int c) {
        return cells[0][c].getClass();
    }

    public int getColumnCount() {
        return cells[0].length;
    }

    public int getRowCount() {
        return cells.length;
    }

    public Object getValueAt(int r, int c) {
        return cells[r][c];
    }

    public void setValueAt(Object obj, int r, int c) {
        cells[r][c] = obj;
    }

    public boolean isCellEditable(int r, int c) {
        return c == PLANET_COLUMN
                || c == MOONS_COLUMN
                || c == GASEOUS_COLUMN
                || c == COLOR_COLUMN
                ;
    }
}
