package code.java.view.table.tableModel;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public class SortableTableModel extends AbstractTableModel {

    private TableModel model;

    private Row[] rowArr;

    private int sortColumn;

    // 将一个已经存在TableModel对象包装成SortableTableModel对象
    public SortableTableModel(TableModel m) {
        // 将被封装的TableModel传入
        model = m;
        rowArr = new Row[model.getRowCount()];
        // 将原TableModel中的每行记录的索引使用Row数组保存起来
        for (int i = 0; i < rowArr.length; i++) {
            rowArr[i] = new Row(i);
        }
    }

    // 实现根据指定列进行排序
    public void sort(int c) {
        sortColumn = c;
        java.util.Arrays.sort(rowArr);
        fireTableDataChanged();
    }

    // 下面三个方法需要访问model中的数据，所以涉及本model中数据
    // 和被包装model数据中的索引转换，程序使用rows数组完成这种转换。
    public Object getValueAt(int r, int c) {
        return model.getValueAt(rowArr[r].index, c);
    }

    public boolean isCellEditable(int r, int c) {
        return model.isCellEditable(rowArr[r].index, c);
    }

    public void setValueAt(Object aValue, int r, int c) {
        model.setValueAt(aValue, rowArr[r].index, c);
    }

    // 下面方法的实现把该model的方法委托为原封装的model来实现
    public int getRowCount() {
        return model.getRowCount();
    }

    public int getColumnCount() {
        return model.getColumnCount();
    }

    public String getColumnName(int c) {
        return model.getColumnName(c);
    }

    public Class getColumnClass(int c) {
        return model.getColumnClass(c);
    }

    // 定义一个Row类，该类用于封装JTable中的一行
    // 实际上它并不封装行数据，它只封装行索引
    private class Row implements Comparable<Row> {
        // 该index保存着被封装Model里每行记录的行索引
        public int index;

        public Row(int index) {
            this.index = index;
        }

        // 实现两行之间的大小比较
        public int compareTo(Row other) {
            Object currentCell = model.getValueAt(index, sortColumn);
            Object anotherCell = model.getValueAt(other.index, sortColumn);
            if (currentCell instanceof Comparable) {
                return ((Comparable) currentCell).compareTo(anotherCell);
            } else {
                return currentCell.toString().compareTo(anotherCell.toString());
            }
        }

    }

}
