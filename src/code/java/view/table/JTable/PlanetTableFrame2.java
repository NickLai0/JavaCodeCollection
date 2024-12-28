package code.java.view.table.JTable;

import code.java.utils.FrameUtils;
import code.java.utils.ProjectFileUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 * From Java Core 2.
 * Modified a lot.
 * <p>
 * Demonstrates:
 * allowing row selection,
 * allowing column selection,
 * allowing cell selection,
 * hide selected row,
 * hide selected column,
 * show hided rows,
 * show hided columns,
 * print selected info,
 *
 *
 * <p>
 * This frame contains a table of planet data.
 */
public class PlanetTableFrame2 extends JFrame {

    private static final int DEFAULT_WIDTH = 600;
    private static final int DEFAULT_HEIGHT = 500;

    //颜色列索引
    public static final int COLOR_COLUMN = 4;
    //图片列索引
    public static final int IMAGE_COLUMN = 5;

    private HashSet<Integer> removedRowModelIndices = new HashSet<>();
    //store removed columns for "show columns" command
    private ArrayList<TableColumn> removedColumns = new ArrayList<>();

    JMenuBar menuBar = new JMenuBar();
    JMenu selectionMenu = new JMenu("Selection");
    JCheckBoxMenuItem rowsItem = new JCheckBoxMenuItem("Rows");
    JCheckBoxMenuItem columnsItem = new JCheckBoxMenuItem("Columns");
    JCheckBoxMenuItem cellsItem = new JCheckBoxMenuItem("Cells");
    JMenu tableMenu = new JMenu("Edit");
    JMenuItem hideColumnsItem = new JMenuItem("Hide Columns");
    JMenuItem hideRowsItem = new JMenuItem("Hide Rows");
    JMenuItem showColumnsItem = new JMenuItem("Show Columns");
    JMenuItem showRowsItem = new JMenuItem("Show Rows");
    JMenuItem printSelectionItem = new JMenuItem("Print Selection");

    private String[] columnNames = {"Planet", "Radius", "Moons", "Gaseous", "Color", "Image"};
    //因为Java Core 2里面的git图全部找不到，所以到网上下载一张Mercury.gif，全部用此图。
    private Object[][] cells = {{"Mercury", 2440.0, 0, false, Color.YELLOW, new ImageIcon(ProjectFileUtils.getImageAbsPathByName("Mercury.gif"))}, {"Venus", 6052.0, 0, false, Color.YELLOW, new ImageIcon(ProjectFileUtils.getImageAbsPathByName("Mercury.gif"))}, {"Earth", 6378.0, 1, false, Color.BLUE, new ImageIcon(ProjectFileUtils.getImageAbsPathByName("Mercury.gif"))}, {"Mars", 3397.0, 2, false, Color.RED, new ImageIcon(ProjectFileUtils.getImageAbsPathByName("Mercury.gif"))}, {"Jupiter", 71492.0, 16, true, Color.ORANGE, new ImageIcon(ProjectFileUtils.getImageAbsPathByName("Mercury.gif"))}, {"Saturn", 60268.0, 18, true, Color.ORANGE, new ImageIcon(ProjectFileUtils.getImageAbsPathByName("Mercury.gif"))}, {"Uranus", 25559.0, 17, true, Color.BLUE, new ImageIcon(ProjectFileUtils.getImageAbsPathByName("Mercury.gif"))}, {"Neptune", 24766.0, 8, true, Color.BLUE, new ImageIcon(ProjectFileUtils.getImageAbsPathByName("Mercury.gif"))}, {"Pluto", 1137.0, 1, false, Color.BLACK, new ImageIcon(ProjectFileUtils.getImageAbsPathByName("Mercury.gif"))},};

    DefaultTableModel tableModel = new DefaultTableModel(cells, columnNames) {
        //the common ancestor class of the object values in the model.
        public Class<?> getColumnClass(int c) {
            //根据每列的第一个值返回该列真实的数据类型
            return cells[0][c].getClass();
        }
    };

    private JTable table = new JTable(tableModel);

    TableRowSorter tableRowSorter = new TableRowSorter<TableModel>(tableModel);
    /*
        determines which rows,
        if any, should be hidden from the view.
        The filter is applied before sorting.
    */
    RowFilter rowFilter = new RowFilter<TableModel, Integer>() {
        //to indicate which rows should be displayed.
        public boolean include(Entry<? extends TableModel, ? extends Integer> entry) {
            /*
             Returns the identifer (in the model) of the entry.
             For a table this corresponds to the index of the
             row in the model, expressed as an Integer.
           */
            return !removedRowModelIndices.contains(entry.getIdentifier());
        }
    };

    public PlanetTableFrame2() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        initMenu();
        setupTable();
        initListener();
    }

    private void initListener() {
        rowsItem.addActionListener(new SelectRowAction());
        columnsItem.addActionListener(new SelectColumAction());
        cellsItem.addActionListener(new SelectCellAction());
        hideColumnsItem.addActionListener(new HideColumnsAction());
        showColumnsItem.addActionListener(new ShowColumnsAction());
        hideRowsItem.addActionListener(new HideRowsAction());
        showRowsItem.addActionListener(new ShowRowsAction());
        printSelectionItem.addActionListener(new SelectionInfoPrintAction());
    }

    private void initMenu() {
        setJMenuBar(menuBar);
        menuBar.add(selectionMenu);
        rowsItem.setSelected(table.getRowSelectionAllowed());
        columnsItem.setSelected(table.getColumnSelectionAllowed());
        cellsItem.setSelected(table.getCellSelectionEnabled());
        selectionMenu.add(rowsItem);
        selectionMenu.add(columnsItem);
        selectionMenu.add(cellsItem);
        menuBar.add(tableMenu);
        tableMenu.add(hideColumnsItem);
        tableMenu.add(showColumnsItem);
        tableMenu.add(hideRowsItem);
        tableMenu.add(showRowsItem);
        tableMenu.add(printSelectionItem);
    }

    private void setupTable() {
        table.setRowHeight(100);
        table.getColumnModel().getColumn(COLOR_COLUMN).setMinWidth(250);
        table.getColumnModel().getColumn(IMAGE_COLUMN).setMinWidth(100);
        table.setRowSorter(tableRowSorter);
        tableRowSorter.setComparator(
                COLOR_COLUMN,
                Comparator.comparing(Color::getBlue)
                        .thenComparing(Color::getGreen)
                        .thenComparing(Color::getRed)
        );
        tableRowSorter.setSortable(IMAGE_COLUMN, false);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    //更新复选框的状态（不明白为何要如此设计，展示效果?）
    private void updateCheckboxMenuItems() {
        rowsItem.setSelected(table.getRowSelectionAllowed());
        columnsItem.setSelected(table.getColumnSelectionAllowed());
        cellsItem.setSelected(table.getCellSelectionEnabled());
    }

    //打印所选的行和列的下标
    class SelectionInfoPrintAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(
                    "Selected cell:"
                            + Arrays.toString(table.getSelectedRows())
                            + Arrays.toString(table.getSelectedColumns())
            );
        }

    }

    //隐藏所选择的行
    class HideRowsAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int[] selected = table.getSelectedRows();
            for (int i : selected) {
                removedRowModelIndices.add(table.convertRowIndexToModel(i));
            }
            //通过RowFilter来决定是否显示或隐藏。跟疯狂Java讲义则是保持每一列的model，然后挨个移出。
            tableRowSorter.setRowFilter(rowFilter);
        }
    }

    //显示所隐藏的行
    class ShowRowsAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            removedRowModelIndices.clear();
            tableRowSorter.setRowFilter(rowFilter);
        }
    }

    //显示所选的列
    class ShowColumnsAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // restore all removed columns
            for (TableColumn tc : removedColumns) {
                table.addColumn(tc);
            }
            removedColumns.clear();
        }
    }

    //隐藏所选的列
    class HideColumnsAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int[] selected = table.getSelectedColumns();
            TableColumnModel columnModel = table.getColumnModel();
            // remove columns from view, starting at the last
            // index so that column numbers aren't affected
            for (int i = selected.length - 1; i >= 0; i--) {
                TableColumn column = columnModel.getColumn(selected[i]);
                table.removeColumn(column);
                // store removed columns for "show columns" command
                removedColumns.add(column);
            }
        }
    }

    //去掉所有选择，然后允许单元选择
    class SelectCellAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            table.clearSelection();
            table.setCellSelectionEnabled(cellsItem.isSelected());
            updateCheckboxMenuItems();
        }
    }

    //去掉所有选择，然后允许列选择
    class SelectColumAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            table.clearSelection();
            table.setColumnSelectionAllowed(columnsItem.isSelected());
            updateCheckboxMenuItems();
        }
    }

    //去掉所有选择，然后允许行选择
    class SelectRowAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //Deselects all selected columns and rows.
            table.clearSelection();
            //Sets the rows in this model can be selected.
            table.setRowSelectionAllowed(rowsItem.isSelected());
            updateCheckboxMenuItems();
        }
    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(PlanetTableFrame2.class);
    }

}
