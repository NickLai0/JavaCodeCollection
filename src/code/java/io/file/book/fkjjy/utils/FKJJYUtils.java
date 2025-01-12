package code.java.io.file.book.fkjjy.utils;

import code.java.utils.ProjectFileUtils;

public class FKJJYUtils {
    private static final String ROOT_DIR;
    private static final String SQL_DIR;
    private static final String INIT_FILE_PATH;

    static {
        ROOT_DIR = ProjectFileUtils.getsDatabaseDir() + "\\fkjjy";
        SQL_DIR = ROOT_DIR + "\\sql";
        INIT_FILE_PATH = SQL_DIR + "\\conn.ini";
    }

    public static String getRootDir() {
        return ROOT_DIR;
    }

    public static String getSqlDir() {
        return SQL_DIR;
    }

    public static String getInitFilePath() {
        return INIT_FILE_PATH;
    }

}
