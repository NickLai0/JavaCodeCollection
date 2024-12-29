package code.java.view.table.JTable;

import code.java.utils.FrameUtils;
import code.java.view.table.renderer.ColorTableCellRenderer;
import code.java.view.table.tableModel.PlanetTableModel;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

import static code.java.utils.ImageUtils.newImageIcon;

/**
 * This frame contains a table of planet data.
 */
public class TableCellRenderFrame extends JFrame {

    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 400;

    PlanetTableModel model = new PlanetTableModel();

    JTable table = new JTable(model);

    public TableCellRenderFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setupView();
        table.setRowSelectionAllowed(false);
        // set up renderers and editors（设置颜色列的默认渲染器和编辑器）
        table.setDefaultRenderer(Color.class, new ColorTableCellRenderer());
        table.setDefaultEditor(Color.class, new ColorTableCellEditor());
    }

    private void setupView() {
        // show table
        table.setRowHeight(100);
        setupMoonColumn();
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void setupMoonColumn() {
        JComboBox moonCombo = new JComboBox<Integer>();
        for (int i = 0; i <= 20; i++) {
            moonCombo.addItem(i);
        }
        TableColumnModel columnModel = table.getColumnModel();
        TableColumn moonColumn = columnModel.getColumn(PlanetTableModel.MOONS_COLUMN);
        moonColumn.setCellEditor(new DefaultCellEditor(moonCombo));
        moonColumn.setHeaderRenderer(table.getDefaultRenderer(ImageIcon.class));
        moonColumn.setHeaderValue(newImageIcon("Mercury.gif"));
    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(TableCellRenderFrame.class);
    }

}
