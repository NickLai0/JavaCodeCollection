package code.java.view.List.model;

import javax.swing.*;
import java.math.BigDecimal;
//支持小数跳步长的数字列表model
public class NumberListModel extends AbstractListModel<BigDecimal> {

    protected BigDecimal start;
    protected BigDecimal end;
    protected BigDecimal step;//步长

    public NumberListModel(double start, double end, double step) {
        this.start = BigDecimal.valueOf(start);
        this.end = BigDecimal.valueOf(end);
        this.step = BigDecimal.valueOf(step);
    }

    // 返回列表项的个数
    public int getSize() {
        return (int) Math.floor(
                end.subtract(start).divide(step)
                        .doubleValue()
        ) + 1;
    }

    // 返回指定索引处的列表项数字
    public BigDecimal getElementAt(int index) {
        return BigDecimal
                .valueOf(index)//起始索引
                .multiply(step)//乘以步长
                .add(start);//加上起始位置
    }

}
