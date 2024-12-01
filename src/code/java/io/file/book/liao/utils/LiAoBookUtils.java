package code.java.io.file.book.liao.utils;

public class LiAoBookUtils {
    public static final String TITLE_NUMBER_SEPARATOR = ",";
    public static final String TITLE_NUMBER_PREFIX_LESS_THAN_TEN = "0";
    public static final String TITLE_NUMBER_PREFIX_LESS_THAN_ONE_HUNDRED = "00";

    //给数字前面加0。String.format应该也能实现，但暂时忘了！！！
    public static String addZerosDependsOnSize(int number, int size, String seperator) {
        StringBuilder sb = new StringBuilder();
        if (size < 100) {
            //两位数序号前面补0
            if (number < 10) {
                sb.append(TITLE_NUMBER_PREFIX_LESS_THAN_TEN);
            }
        } else if (size < 1000) {
            //三位数序号前面补0
            if (number < 10) {
                sb.append(TITLE_NUMBER_PREFIX_LESS_THAN_ONE_HUNDRED);
            } else if (number < 100) {
                sb.append(TITLE_NUMBER_PREFIX_LESS_THAN_TEN);
            }
        }
        sb.append(number);
        sb.append(seperator);
        return sb.toString();
    }

    public static String addZerosDependsOnSize(int number, int size) {
        return addZerosDependsOnSize(number, size, TITLE_NUMBER_SEPARATOR);
    }
}