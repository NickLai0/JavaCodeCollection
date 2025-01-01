package code.java.view.text.formatted;

import code.java.utils.FrameUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.*;

import java.lang.reflect.Array;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;

/**
 *
 *
 * Modify a lot.
 *
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
public class JFormattedTextFieldTest2 extends JFrame {

    private JButton okButton = new JButton("确定");

    // 定义用于添加格式化文本框的容器
    private JPanel enterPanel = new JPanel();

    public JFormattedTextFieldTest2() {
        setupCenter();
        setupSouth();
        pack();
    }

    private void setupCenter() {
        enterPanel.setLayout(new GridLayout(0, 3));
        add(enterPanel, BorderLayout.CENTER);

        JFormattedTextField intField0 = new JFormattedTextField(getInternationalFormatter());
        intField0.setValue(100);
        addRow("只接受数字的文本框", intField0);

        JFormattedTextField intField1 = new JFormattedTextField(NumberFormat.getIntegerInstance());
        intField1.setInputVerifier(new FormattedTextFieldVerifier());
        intField1.setValue(100);
        // 添加输入校验器
        addRow("带输入校验器的文本框", intField1);

        // 创建自定义格式器对象
        IPAddressFormatter ipFormatter = new IPAddressFormatter();
        //禁止重写
        ipFormatter.setOverwriteMode(false);
        // 以自定义格式器对象创建格式化文本框
        JFormattedTextField ipField = new JFormattedTextField(ipFormatter);
        ipField.setValue(new byte[]{(byte) 192, (byte) 168, 4, 1});
        addRow("IP地址格式", ipField);
    }

    private void setupSouth() {
        // 添加按钮
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /*
        数字过滤器
    */
    private static InternationalFormatter getInternationalFormatter() {
        return new InternationalFormatter(NumberFormat.getIntegerInstance()) {
            protected DocumentFilter getDocumentFilter() {
                return new NumberFilter();
            }
        };
    }

    // 定义添加一行格式化文本框的方法
    private void addRow(String labelText, JFormattedTextField field) {
        enterPanel.add(new JLabel(labelText));
        enterPanel.add(field);
        final JLabel valueLabel = new JLabel();
        enterPanel.add(valueLabel);
        // 为"确定"按钮添加事件监听器
        // 当用户单击“确定”按钮时，文本框后显示文本框内的值
        okButton.addActionListener(new OnButtonClickAction(field, valueLabel));
    }

    class OnButtonClickAction implements ActionListener {
        JFormattedTextField field;
        JLabel valueLabel;

        public OnButtonClickAction(JFormattedTextField field, JLabel valueLabel) {
            this.field = field;
            this.valueLabel = valueLabel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Object value = field.getValue();
            // 如果该值是数组，使用Arrays的toString方法输出数组
            if (value.getClass().isArray()) {
                StringBuilder sb = new StringBuilder();
                sb.append('{');
                for (int i = 0; i < Array.getLength(value); i++) {
                    if (i > 0)
                        sb.append(',');
                    sb.append(Array.get(value, i).toString());
                }
                sb.append('}');
                valueLabel.setText(sb.toString());
            } else {
                // 输出格式化文本框的值
                valueLabel.setText(value.toString());
            }

        }
    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(JFormattedTextFieldTest2.class)
                .setTitle("测试格式化文本框");
    }
}

// 输入校验器
class FormattedTextFieldVerifier extends InputVerifier {
    // 当输入组件失去焦点时，该方法被触发
    public boolean verify(JComponent component) {
        JFormattedTextField field = (JFormattedTextField) component;
        // 返回用户输入是否有效
        return field.isEditValid();
    }
}

// 数字过滤器
class NumberFilter extends DocumentFilter {
    public void insertString(FilterBypass fb, int offset
            , String string, AttributeSet attr) throws BadLocationException {
        StringBuilder builder = new StringBuilder(string);
        // 过滤用户输入的所有字符
        filterInt(builder);
        super.insertString(fb, offset, builder.toString(), attr);
    }

    public void replace(FilterBypass fb, int offset, int length
            , String string, AttributeSet attr) throws BadLocationException {
        if (string != null) {
            StringBuilder builder = new StringBuilder(string);
            // 过滤用户替换的所有字符
            filterInt(builder);
            string = builder.toString();
        }
        super.replace(fb, offset, length, string, attr);
    }

    // 过滤整数字符，把所有非0～9的字符全部删除
    private void filterInt(StringBuilder builder) {
        for (int i = builder.length() - 1; i >= 0; i--) {
            int cp = builder.codePointAt(i);
            if (cp > '9' || cp < '0') {
                builder.deleteCharAt(i);
            }
        }
    }
}

class IPAddressFormatter extends DefaultFormatter {
    public String valueToString(Object value)
            throws ParseException {
        if (!(value instanceof byte[])) {
            throw new ParseException("该IP地址的值只能是字节数组", 0);
        }
        byte[] a = (byte[]) value;
        if (a.length != 4) {
            throw new ParseException("IP地址必须是四个整数", 0);
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int b = a[i];
            if (b < 0) b += 256;
            builder.append(String.valueOf(b));
            if (i < 3) builder.append('.');
        }
        return builder.toString();
    }

    public Object stringToValue(String text) throws ParseException {
        // 将格式化文本框内的字符串以点号（.）分成四节。
        String[] nums = text.split("\\.");
        if (nums.length != 4) {
            throw new ParseException("IP地址必须是四个整数", 0);
        }
        byte[] a = new byte[4];
        for (int i = 0; i < 4; i++) {
            int b = 0;
            try {
                b = Integer.parseInt(nums[i]);
            } catch (NumberFormatException e) {
                throw new ParseException("IP地址必须是整数", 0);
            }
            if (b < 0 || b >= 256) {
                throw new ParseException("IP地址值只能在0~255之间", 0);
            }
            a[i] = (byte) b;
        }
        return a;
    }

}
