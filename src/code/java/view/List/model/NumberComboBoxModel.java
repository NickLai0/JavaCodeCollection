package code.java.view.List.model;

import javax.swing.*;
import java.math.BigDecimal;

public class NumberComboBoxModel extends NumberListModel implements ComboBoxModel<BigDecimal> {

    // 用于保存用户选中项的索引
    private int selectedIndex = 0;

    public NumberComboBoxModel(double start, double end, double step) {
        super(start, end, step);
    }

    // 设置选中“选择项”
    public void setSelectedItem(Object anItem) {
        if (anItem instanceof BigDecimal) {
            BigDecimal target = (BigDecimal) anItem;
            // 根据选中的值来修改选中项的索引
            selectedIndex = target.subtract(super.start).divide(step).intValue();
        }
    }

    // 获取“选择项”的值
    public BigDecimal getSelectedItem() {
        // 根据选中项的索引来取得选中项
        return BigDecimal.valueOf(selectedIndex).multiply(step).add(start);
    }

}
