package code.java.view.table.tableModel;

import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import static code.java.utils.LU.println;

public class ResultSetTableModel extends AbstractTableModel {

    private ResultSet resultSet;

    /**
     * 可通过ResultSetMetaData提供的大量方
     * 法来返回ResultSet的描述信息。常用的方法有如下三个。
     * ➢ int getColumnCount()：返回该ResultSet的列数量。
     * ➢ String getColumnName(int column)：返回指定索引的列名。
     * ➢ int getColumnType(int column)：返回指定索引的列类型。
     */
    private ResultSetMetaData resultSetMetaData;

    // 构造器，初始化rs和rsmd两个属性
    public ResultSetTableModel(ResultSet rs) {
        resultSet = rs;
        try {
            resultSetMetaData = resultSet.getMetaData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 重写getColumnName方法，用于为该TableModel设置列名
    public String getColumnName(int c) {
        try {
            return resultSetMetaData.getColumnName(c + 1);
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }

    // 重写getColumnCount方法，用于设置该TableModel的列数
    public int getColumnCount() {
        try {
            return resultSetMetaData.getColumnCount();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 重写getValueAt方法，用于设置该TableModel指定单元格的值
    public Object getValueAt(int r, int c) {
        try {
            resultSet.absolute(r + 1);
            return resultSet.getObject(c + 1);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 重写getColumnCount方法，用于设置该TableModel的行数
    public int getRowCount() {
        try {
            resultSet.last();
            return resultSet.getRow();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 重写isCellEditable返回true，让每个单元格可编辑
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    // 重写setValueAt()方法，当用户编辑单元格时，将会触发该方法
    public void setValueAt(Object value, int row, int column) {
        try {
            // 设置结果集到对应的行
            resultSet.absolute(row + 1);
            int valueColumnIndex = column + 1;
            Object oldValue = resultSet.getObject(valueColumnIndex);
            if ((oldValue == value)) {
                //新老值一样，不做处理，直接返回
                return;
            }
            //编辑Cell的数据时，即使数据库里的类型是INTEGER，
            //value居然是String类型，所以全部按String来比较
            if (oldValue != null && oldValue.toString().equals(value)) {
                return;
            }

            //println("setValueAt: oldValue = " + oldValue + ", value = " + value);
            // 修改单元格多对应的值
            resultSet.updateObject(valueColumnIndex, value);
            // 提交修改
            resultSet.updateRow();
            // 触发单元格的修改事件
            fireTableCellUpdated(row, column);
        } catch (SQLException evt) {
            evt.printStackTrace();
        }
    }

}
