package code.java.view.table.tableModel;

import javax.swing.table.DefaultTableModel;

public class GetColumnClassTableModel extends DefaultTableModel {

    public GetColumnClassTableModel(String[] columnNames, Object[][] cells) {
        super(cells, columnNames);
    }

    // 重写,返回该列第一个单元的真实类（）
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

}
