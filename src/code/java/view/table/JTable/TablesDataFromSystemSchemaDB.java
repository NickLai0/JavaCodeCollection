package code.java.view.table.JTable;

import code.java.io.file.book.fkjjy.utils.FKJJYUtils;
import code.java.utils.FrameUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 用JDBC API查询MySql的information_schema(系统数据库)
 * 数据库的所有表，
 * 选择某个表，则展示它所有数据。
 * 展示用JTable，数据由TableModel存储。
 *
 * 注：不知为何，查询information_schema的所有表，
 * 显示居然是select_test的所有表，而且差某张表时，
 * 报information_schema数据库无此表。
 */
public class TablesDataFromSystemSchemaDB extends BaseTablesDataFromDatabase {

    @Override
    protected Connection getConnection() throws SQLException, IOException, ClassNotFoundException {
        return FKJJYUtils.getInformationSchemaDBConnection();
    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(
                TablesDataFromSystemSchemaDB.class
        );
    }

}

