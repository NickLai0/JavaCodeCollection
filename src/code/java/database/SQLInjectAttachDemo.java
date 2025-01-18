package code.java.database;

import code.java.io.file.book.fkjjy.utils.FKJJYUtils;
import code.java.utils.FrameUtils;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import java.sql.*;

import static code.java.utils.LU.println;

/**
 * 演示SQL注入、防止注入案例：
 *      verifyByAvoidingSQLInject校验登录时防止注入，verify方法相反。
 *
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
public class SQLInjectAttachDemo extends JFrame {
    private final String PROP_FILE = FKJJYUtils.getInitFilePath();

    private String driver;
    // url是数据库的服务地址
    private String url;
    private String user;
    private String pass;

    private JTextField userField = new JTextField(20);
    private JTextField passField = new JTextField(20);
    private JButton loginButton = new JButton("登录");

    public void init() throws Exception {
        add(userField, BorderLayout.NORTH);
        add(passField, BorderLayout.CENTER);
        add(loginButton, BorderLayout.SOUTH);
        pack();
        loadDBDriverParams();
        loginButton.addActionListener(new LoginAction());
    }

    //Load database driver parameters.
    private void loadDBDriverParams() throws IOException, ClassNotFoundException {
        Properties connProp = new Properties();
        connProp.load(new FileInputStream(PROP_FILE));
        driver = connProp.getProperty("jdbc.drivers");
        url = connProp.getProperty("jdbc.url");
        user = connProp.getProperty("jdbc.username");
        pass = connProp.getProperty("jdbc.password");
        // 加载驱动
        Class.forName(driver);
    }

    //使用Statement，不防sql注入，userName为：'or true or'，即可完成注入。
    private boolean verify(String userName, String userPass) {

/*
原始SQL:
select * from jdbc_test where jdbc_name='' and jdbc_desc=''

输入'or true or'进行注入攻击的sql：
select * from jdbc_test where jdbc_name=''or true or'' and jdbc_desc=''

注入后，将原本and条件操作变成了or操作，且or true永远是true，达到了攻击效果。
*/

        // 用户名
        String sql = "select * from jdbc_test "
                + "where jdbc_name='" + userName
                + "' and jdbc_desc='" + userPass + "'";
        println(sql);
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            // 如果查询的ResultSet里有超过一条的记录，则登录成功
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //使用PreparedStatement防止sql注入案例
    private boolean verifyByAvoidingSQLInject(String userName, String userPass) {
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement pstmt = conn.prepareStatement(
                     "select * from jdbc_test where jdbc_name=? and jdbc_desc=?")) {
            pstmt.setString(1, userName);
            pstmt.setString(2, userPass);
            try (
                    ResultSet rs = pstmt.executeQuery()) {
                //如果查询的ResultSet里有超过一条的记录，则登录成功
                if (rs.next()) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    //登录action
    class LoginAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (verifyByAvoidingSQLInject(userField.getText(), passField.getText())) {
                // 登录成功则显示“登录成功”
                JOptionPane.showMessageDialog(SQLInjectAttachDemo.this, "登录成功");
            } else {
                // 否则显示“登录失败”
                JOptionPane.showMessageDialog(SQLInjectAttachDemo.this, "登录失败");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        FrameUtils.visibleAndExitOnClose(SQLInjectAttachDemo.class, true)
                .setTitle("登录");
    }

}
