package code.java.view.table.JTable;

import code.java.utils.FrameUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 来源疯狂Java讲义
 * Modify a lot.
 * <p>
 * Description:
 * 网站: <a href="http://www.crazyit.org">疯狂Java联盟</a><br>
 * Copyright (C), 2001-2018, Yeeku.H.Lee<br>
 * This program is protected by copyright laws.<br>
 * Program Name:<br>
 * Date:<br>
 *
 * @author Yeeku.H.Lee kongyeeku@163.com
 * @version 1.0
 */
public class AdjustingWidth extends JFrame {

    JMenuBar menuBar = new JMenuBar();

    JMenu adjustModeMenu = new JMenu("调整方式");
    JMenu selectUnitMenu = new JMenu("选择单元");
    JMenu selectModeMenu = new JMenu("选择方式");

    // 定义5个单选框按钮，用以控制表格的宽度调整方式
    JRadioButtonMenuItem[] adjustModeItems = new JRadioButtonMenuItem[]{
            new JRadioButtonMenuItem("只调整表格"),
            new JRadioButtonMenuItem("只调整下一列"),
            new JRadioButtonMenuItem("平均调整余下列"),
            new JRadioButtonMenuItem("只调整最后一列"),
            new JRadioButtonMenuItem("平均调整所有列")
    };

    // 定义3个单选框按钮，用以控制表格的选择方式
    JRadioButtonMenuItem[] selectModeRadioItems = new JRadioButtonMenuItem[]{
            new JRadioButtonMenuItem("无限制"),
            new JRadioButtonMenuItem("单独的连续区"),
            new JRadioButtonMenuItem("单选")
    };
    JCheckBoxMenuItem rowsItem = new JCheckBoxMenuItem("选择行");
    JCheckBoxMenuItem columnsItem = new JCheckBoxMenuItem("选择列");
    JCheckBoxMenuItem cellsItem = new JCheckBoxMenuItem("选择单元格");

    ButtonGroup modeMenuButtonGroup = new ButtonGroup();
    ButtonGroup selectBg = new ButtonGroup();

    // 定义一个int类型的数组，用于保存表格所有的宽度调整方式
    int[] adjustModes = new int[]{
            JTable.AUTO_RESIZE_OFF
            , JTable.AUTO_RESIZE_NEXT_COLUMN
            , JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS
            , JTable.AUTO_RESIZE_LAST_COLUMN
            , JTable.AUTO_RESIZE_ALL_COLUMNS
    };
    int[] selectionModes = new int[]{
            ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
            , ListSelectionModel.SINGLE_INTERVAL_SELECTION
            , ListSelectionModel.SINGLE_SELECTION
    };
    // 定义一维数据作为列标题
    Object[] columnTitle = {"姓名", "年龄", "性别"};
    // 定义二维数组作为表格数据
    Object[][] tableData = {
            new Object[]{"李清照", 29, "女"},
            new Object[]{"苏格拉底", 56, "男"},
            new Object[]{"李白", 35, "男"},
            new Object[]{"弄玉", 18, "女"},
            new Object[]{"虎头", 2, "男"}
    };

    JTable table = new JTable(tableData, columnTitle);

    public AdjustingWidth() {
        setupMenu();
        setupTable();
        setupListener();
    }

    private void setupListener() {
        for (int i = 0; i < adjustModeItems.length; i++) {
            adjustModeItems[i].addActionListener(new AdjustModeAction(i));
        }
        for (int i = 0; i < selectModeRadioItems.length; i++) {
            selectModeRadioItems[i].addActionListener(new SelectModeAction(i));
        }
        rowsItem.addActionListener(new SelectRowAction());
        columnsItem.addActionListener(new SelectColumnAction());
        cellsItem.addActionListener(new SelectCellAction());
    }

    private void setupTable() {
        // 分别获取表格的三个表格列，并设置三列的最小宽度，最佳宽度和最大宽度
        table.getColumn(columnTitle[0]).setMinWidth(40);//名字列
        table.getColumn(columnTitle[1]).setPreferredWidth(50);//年龄列
        table.getColumn(columnTitle[2]).setMaxWidth(50);//性别列
        // 将JTable对象放在JScrollPane中，并将该JScrollPane放在窗口中显示出来
        add(new JScrollPane(table));
        pack();
    }

    private void setupMenu() {
        //调整方式菜单设置
        menuBar.add(adjustModeMenu);
        for (int i = 0; i < adjustModeItems.length; i++) {
            JRadioButtonMenuItem modeRadio = adjustModeItems[i];
            adjustModeMenu.add(modeRadio);
            modeMenuButtonGroup.add(modeRadio);
            // 默认选中第三个菜单项，即对应表格默认的宽度调整方式
            if (i == 2) {
                modeRadio.setSelected(true);
            }
        }

        //选择单元菜单设置
        menuBar.add(selectModeMenu);
        for (int i = 0; i < selectModeRadioItems.length; i++) {
            JRadioButtonMenuItem radio = selectModeRadioItems[i];
            selectBg.add(radio);
            selectModeMenu.add(radio);
            // 默认选中第一个菜单项
            if (i == 0) {
                radio.setSelected(true);
            }
        }

        menuBar.add(selectUnitMenu);
        selectUnitMenu.add(rowsItem);
        selectUnitMenu.add(columnsItem);
        selectUnitMenu.add(cellsItem);
        updateCheckboxMenuItems();
        setJMenuBar(menuBar);
    }

    private void updateCheckboxMenuItems() {
        // 如果该菜单项处于选中状态，设置表格的选择单元是单元格
//        table.setCellSelectionEnabled(cellsItem.isSelected());
        // 该选项的改变会同时影响选择行、选择列两个菜单
        rowsItem.setSelected(table.getRowSelectionAllowed());
        columnsItem.setSelected(table.getColumnSelectionAllowed());
        cellsItem.setSelected(table.getCellSelectionEnabled());
    }

    class SelectModeAction implements ActionListener {
        int index;
        public SelectModeAction(int index) {
            this.index = index;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            // 如果当前菜单项处于选中状态，表格使用对应的选择方式s
            if (selectModeRadioItems[index].isSelected()) {
                table.getSelectionModel().setSelectionMode(selectionModes[index]);     //②
            }
        }
    }

    class AdjustModeAction implements ActionListener {
        int index;
        public AdjustModeAction(int index) {
            this.index = index;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            // 如果当前菜单项处于选中状态，表格使用对应的调整方式
            if (adjustModeItems[index].isSelected()) {
                table.setAutoResizeMode(adjustModes[index]);   //①
            }
        }
    }

    class SelectRowAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            table.clearSelection();
            // 如果选择行、选择列同时被选中，其实质是选择单元格
            table.setRowSelectionAllowed(rowsItem.isSelected());
            updateCheckboxMenuItems();
        }
    }

    class SelectColumnAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            table.clearSelection();
            // 如果选择行、选择列同时被选中，其实质是选择单元格
            table.setColumnSelectionAllowed(columnsItem.isSelected());
            updateCheckboxMenuItems();
        }
    }

    class SelectCellAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            table.clearSelection();
            table.setCellSelectionEnabled(cellsItem.isSelected());
            updateCheckboxMenuItems();
        }
    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(AdjustingWidth.class)
                .setTitle("调整表格列宽");
    }

}
