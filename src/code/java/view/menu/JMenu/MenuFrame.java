package code.java.view.menu.JMenu;

import code.java.adapter.ActionNamePrinterAdapter;
import code.java.adapter.ExitActionAdapter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * From Java Core 1.
 * Modify a lot.
 *
 * <p>
 * Demonstrate how to use:
 * JMenuBar、
 * JMenu、
 * JMenuItem、
 * JPopupMenu、
 * JCheckBoxMenuItem、
 * JRadioButtonMenuItem
 * <p>
 * <p>
 * A frame with a sample menu bar.
 */


public class MenuFrame extends JFrame {

    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;

    private Action saveAction = new ActionNamePrinterAdapter("Save");
    private Action saveAsAction = new ActionNamePrinterAdapter("Save As");
    private Action cutAction = new ActionNamePrinterAdapter("Cut");
    private Action copyAction = new ActionNamePrinterAdapter("Copy");
    private Action pasteAction = new ActionNamePrinterAdapter("Paste");

    {
        // demonstrate icons for menus(There is no image, so can not display.)
        cutAction.putValue(Action.SMALL_ICON, new ImageIcon("cut.gif"));
        copyAction.putValue(Action.SMALL_ICON, new ImageIcon("copy.gif"));
        pasteAction.putValue(Action.SMALL_ICON, new ImageIcon("paste.gif"));
    }

    private JCheckBoxMenuItem readonlyItem;

    private JPopupMenu popup;

    public MenuFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        JMenu fileMenu = prepareFileMenu();
        JMenu editMenu = prepareEditMenu();
        JMenu helpMenu = prepareHelpManu();
        setupMenuBar(fileMenu, editMenu, helpMenu);
        setupPopupMenu();
    }

    private void setupPopupMenu() {
        // demonstrate pop-ups
        popup = new JPopupMenu();
        popup.add(cutAction);
        popup.add(copyAction);
        popup.add(pasteAction);
        JPanel panel = new JPanel();
        panel.setComponentPopupMenu(popup);
        add(panel);
    }

    private void setupMenuBar(JMenu fileMenu, JMenu editMenu, JMenu helpMenu) {
        // add all top-level menus to menu ba
        var menuBar = new JMenuBar();
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);
        //Setup menu bar.
        setJMenuBar(menuBar);
    }

    private JMenu prepareHelpManu() {
        // demonstrate mnemonics
        JMenu helpMenu = new JMenu("Help");
        //JMenu需要调用setMnemonic才能配置助记符（快捷方式）
        helpMenu.setMnemonic('H');
        JMenuItem indexItem = new JMenuItem("Index");
        indexItem.setMnemonic('I');
        helpMenu.add(indexItem);
        // you can also add the mnemonic key to an action
        var aboutAction = new ActionNamePrinterAdapter("About");
        aboutAction.putValue(Action.MNEMONIC_KEY, Integer.valueOf('A'));
        helpMenu.add(aboutAction);
        return helpMenu;
    }

    //创建（Edit）的JMenu，添加两个子JMenuItem(save and saveAs).
    private JMenu prepareEditMenu() {
        JMenu editMenu = new JMenu("Edit");
        editMenu.add(cutAction);
        editMenu.add(copyAction);
        editMenu.add(pasteAction);
        editMenu.addSeparator();
        JMenu optionMenu = prepareOptionMenu();
        editMenu.add(optionMenu);
        return editMenu;
    }

    private JMenu prepareOptionMenu() {
        // demonstrate nested menus（Edit下内嵌Option这个menu）
        JMenu optionMenu = new JMenu("Options");
        // demonstrate checkbox and radio button menus
        //添加复选框菜单项
        readonlyItem = new JCheckBoxMenuItem("Read-only");
        readonlyItem.addActionListener(new ReadOnlyActionHandler());
        optionMenu.add(readonlyItem);
        //添加分隔符
        optionMenu.addSeparator();

        //创建和添加单选钮组
        JRadioButtonMenuItem insertItem = new JRadioButtonMenuItem("Insert");
        insertItem.setSelected(true);
        JRadioButtonMenuItem overtypeItem = new JRadioButtonMenuItem("Overtype");
        //Radio组管理
        ButtonGroup group = new ButtonGroup();
        group.add(insertItem);
        group.add(overtypeItem);
        //添加单选钮菜单项
        optionMenu.add(insertItem);
        optionMenu.add(overtypeItem);

        return optionMenu;
    }

    //创建（file）的JMenu，添加两个子JMenuItem(save and saveAs).
    private JMenu prepareFileMenu() {
        JMenu fileMenu = new JMenu("File");
        //添加Action即创建JMenuItem，有趣。
        fileMenu.add(new ActionNamePrinterAdapter("New"));
        JMenuItem openItem = fileMenu.add(new ActionNamePrinterAdapter("Open"));
        // demonstrate accelerators(加速器：快捷方式)
        openItem.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
        //添加分隔符
        fileMenu.addSeparator();

        JMenuItem saveItem = fileMenu.add(saveAction);
        //设置加速器(快捷方式)
        saveItem.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
        fileMenu.add(saveAsAction);
        //添加分隔符
        fileMenu.addSeparator();

        fileMenu.add(new ExitActionAdapter("Exit"));

        return fileMenu;
    }

    class ReadOnlyActionHandler implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            //是否禁止保存按钮
            boolean saveOk = !readonlyItem.isSelected();
            saveAction.setEnabled(saveOk);
            saveAsAction.setEnabled(saveOk);
        }

    }

    public static void main(String[] args) {
        new MenuFrame().setVisible(true);
    }

}
