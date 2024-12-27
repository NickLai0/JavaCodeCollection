package code.java.view.table.JTable;

import code.java.utils.FrameUtils;
import code.java.view.table.tableModel.InvestmentTableModel;

import javax.swing.*;

/**
 * From Java Core 2.
 * Modified.
 * <p>
 * This program shows how to build
 * a table from a table model.
 *
 * @author Cay Horstmann
 * @version 1.05 2021-09-09
 */
public class InvestmentTable extends JFrame {

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(InvestmentTable.class).setTitle("InvestmentTable");
    }

    public InvestmentTable() {
        var model = new InvestmentTableModel(30, 5, 10);
        add(new JScrollPane(new JTable(model)));
        pack();
    }

}

