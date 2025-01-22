package code.java.pattern.proxy.dynamic;

import code.java.view.table.JTable.TableCellRenderFrame;

/*
TableCellRenderFrame用到了ColorTableCellEditor
ColorTableCellEditor用到了EventHandler
EventHandler内部就是用动态代理
*/
public class EventHandlerTest {
    public static void main(String[] args) {
        TableCellRenderFrame.main(args);
    }
}
