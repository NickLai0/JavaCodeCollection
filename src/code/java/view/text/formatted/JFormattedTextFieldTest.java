package code.java.view.text.formatted;

import code.java.utils.FrameUtils;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;

public class JFormattedTextFieldTest extends JFrame {

    // 定义用于添加格式化文本框的容器
    JPanel centerPanel = new JPanel();

    JFormattedTextField[] fields = new JFormattedTextField[6];

    String[] behaviorLabels = new String[]{"COMMIT", "COMMIT_OR_REVERT", "PERSIST", "REVERT"};

    JRadioButton[] radioButtonArr = new JRadioButton[behaviorLabels.length];

    {
        for (int i = 0; i < behaviorLabels.length; i++) {
            radioButtonArr[i] = new JRadioButton(behaviorLabels[i]);
        }
    }

    int[] behaviors = new int[]{
            JFormattedTextField.COMMIT,
            JFormattedTextField.COMMIT_OR_REVERT,
            JFormattedTextField.PERSIST,
            JFormattedTextField.REVERT
    };

    ButtonGroup bg = new ButtonGroup();

    JButton okButton = new JButton("确定");

    public JFormattedTextFieldTest() {
        setupCenter();
        setupSouth();
        pack();
        initListener();
    }

    private void initListener() {
        for (int i = 0; i < radioButtonArr.length; i++) {
            radioButtonArr[i].addActionListener(new FocusAction(i));
        }
    }

    private void setupCenter() {
        centerPanel.setLayout(new GridLayout(0, 3));

        // 使用NumberFormat的integerInstance创建一个JFormattedTextField
        fields[0] = new JFormattedTextField(NumberFormat.getIntegerInstance());
        fields[0].setValue(100); // 设置初始值
        addRow("整数格式文本框 :", fields[0]);

        // 使用NumberFormat的currencyInstance创建一个JFormattedTextField
        fields[1] = new JFormattedTextField(NumberFormat.getCurrencyInstance());
        fields[1].setValue(100.0);
        addRow("货币格式文本框:", fields[1]);

        // 使用默认的日期格式创建一个JFormattedTextField对象
        fields[2] = new JFormattedTextField(DateFormat.getDateInstance());
        fields[2].setValue(new Date());
        addRow("默认的日期格式器:", fields[2]);

        // 使用SHORT类型的日期格式创建一个JFormattedTextField对象，
        DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT);
        // 要求采用严格的日期格式语法
        format.setLenient(false);
        fields[3] = new JFormattedTextField(format);
        fields[3].setValue(new Date());
        addRow("SHORT类型的日期格式器（语法严格）:", fields[3]);

        // 创建默认的DefaultFormatter对象
        DefaultFormatter df = new DefaultFormatter();
        // 关闭overwrite状态
        df.setOverwriteMode(false);
        fields[4] = new JFormattedTextField(df);
        try {
            // 使用DefaultFormatter来格式化URL
            fields[4].setValue(new URL("http://www.crazyit.org"));
            addRow("URL:", fields[4]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            MaskFormatter mf = new MaskFormatter("020-########");
            mf.setPlaceholderCharacter('□');// 设置占位符
            fields[5] = new JFormattedTextField(mf);
            fields[5].setValue("020-28309378");// 设置初始值
            addRow("电话号码：", fields[5]);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        add(centerPanel, BorderLayout.CENTER);
    }

    private void setupSouth() {
        JPanel radioPanel = new JPanel();
        for (JRadioButton radio : radioButtonArr) {
            bg.add(radio);
            radioPanel.add(radio);
        }
        // 默认选中第二个单选按钮
        radioButtonArr[1].setSelected(true);

        radioPanel.setBorder(new TitledBorder(new EtchedBorder(), "请选择焦点失去后的行为"));

        JPanel borderedPanel = new JPanel();
        borderedPanel.setLayout(new BorderLayout());
        borderedPanel.add(radioPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        borderedPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(borderedPanel, BorderLayout.SOUTH);
    }

    // 定义添加一行格式化文本框的方法
    private void addRow(String labelText, final JFormattedTextField formattedTextField) {
        centerPanel.add(new JLabel(labelText));
        centerPanel.add(formattedTextField);
        final JLabel valueLabel = new JLabel();
        centerPanel.add(valueLabel);
        //每个都add一个监听器，这种写法真不好！
        okButton.addActionListener(event -> {
            Object value = formattedTextField.getValue();
            // 输出格式化文本框的值
            valueLabel.setText(value.toString());
        });
    }

    class FocusAction implements ActionListener {
        int index;

        FocusAction(int index) {
            this.index = index;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (radioButtonArr[index].isSelected()) {
                // 设置所有的格式化文本框的失去焦点的行为
                for (int j = 0; j < fields.length; j++) {
                    fields[j].setFocusLostBehavior(behaviors[index]);
                }
            }
        }
    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(JFormattedTextFieldTest.class)
                .setTitle("测试格式化文本框");
    }

}
