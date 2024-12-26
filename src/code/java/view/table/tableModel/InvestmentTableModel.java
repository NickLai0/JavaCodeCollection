package code.java.view.table.tableModel;

import javax.swing.table.AbstractTableModel;

/**
 * This  table  model computes  the
 * cell entries each time they are requested. The table contents
 * <p>
 * shows the  growth of an
 * investment for a number of years under different interest rates.
 */
//后续可改成银行利息分析模型，用来分析银行利率等。
public class InvestmentTableModel extends AbstractTableModel {
    //初始余额
    private static double INITIAL_BALANCE = 100000.0;

    private int years;//既是年，又是行count
    private int minRate;//最小比率
    private int maxRate;//最小比率

    /**
     * Constructs an investment table model.
     *
     * @param y  the number of years
     * @param r1 the lowest interest rate to tabulate
     * @param r2 the highest interest rate to tabulate
     */
    public InvestmentTableModel(int y, int r1, int r2) {
        years = y;
        minRate = r1;
        maxRate = r2;
    }

    //行号
    public int getRowCount() {
        return years;
    }

    //列号
    public int getColumnCount() {
        return maxRate - minRate + 1;
    }

    /**
     * @param r the row whose value is to be queried
     * @param c the column whose value is to be queried
     * @return data object
     */
    public Object getValueAt(int r, int c) {
        double rate = (c + minRate) / 100.0;
        int nperiods = r;
        //没看明白什么意思！数学功底不好~
        double futureBalance = INITIAL_BALANCE * Math.pow(1 + rate, nperiods);
        return String.format("%.2f", futureBalance);
    }

    public String getColumnName(int c) {
        return (c + minRate) + "%";
    }
}