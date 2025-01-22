package code.java.view.table.JTable;

import code.java.io.file.book.fkjjy.utils.FKJJYUtils;
import code.java.utils.FrameUtils;

import java.io.IOException;
import java.sql.*;

/**
 * 用JDBC API查询SELECT_TEST数据库表，
 * 选择某个表，则展示它所有数据。
 * 展示用JTable，数据由TableModel存储。
 */
public class TablesDataFromSelectTestDB extends BaseTablesDataFromDatabase {

    @Override
    protected Connection getConnection() throws SQLException, IOException, ClassNotFoundException {
        return FKJJYUtils.getSelectTestDBConnection();
    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(
                TablesDataFromSelectTestDB.class
        );
    }

}

