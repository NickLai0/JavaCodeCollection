package code.java.utils;

import code.java.database.select.ShowAllData;

public class JDBCUtils {
    public static void showTableData(String tableName) {
        try {
            new ShowAllData(tableName).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
