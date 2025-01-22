package code.java.database.dml;

import code.java.data.ImageHolder;
import code.java.database.ddl.CreateImgTableIfNotExists;
import code.java.io.file.book.fkjjy.utils.FKJJYUtils;
import code.java.utils.FrameUtils;
import code.java.utils.filenameUtils;
import code.java.view.JFileChooser.SingleImageChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;

import static code.java.utils.ImageUtils.nii;

/**
 * 添加 InsertImageByBlobDataType来演示如何
 * insert图片（blob数据类型）到数据库，然后通过id查询出来展示
 * <p>
 * modify a lot.
 *
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
public class InsertImageByBlobDataType extends JFrame {

    private Connection conn;
    private PreparedStatement insert;
    private PreparedStatement query;
    private PreparedStatement queryAll;

    // 定义一个DefaultListModel对象
    private DefaultListModel<ImageHolder> imageModel = new DefaultListModel<>();

    private JList<ImageHolder> imageList = new JList<>(imageModel);

    private JTextField tfImagePath = new JTextField(26);

    private JButton browserBn = new JButton("...");
    private JButton uploadBn = new JButton("图片插入数据库");

    private JLabel imageLabel = new JLabel();

    // 以当前路径创建文件选择器
    SingleImageChooser imageChooser = new SingleImageChooser(this);

    {
        try {
            // 获取数据库连接
            conn = FKJJYUtils.getSelectTestDBConnection();
            // 创建执行插入的PreparedStatement对象，该对象执行插入后可以返回自动生成的主键
            insert = conn.prepareStatement("insert into img_table values(null,?,?)"
                    , Statement.RETURN_GENERATED_KEYS);
            // 创建两个PreparedStatement对象，用于查询指定图片，查询所有图片
            query = conn.prepareStatement("select img_data from img_table where img_id=?");
            queryAll = conn.prepareStatement("select img_id, img_name from img_table");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init() throws SQLException {
        setSize(620, 400);
        setupCenter();
        setupEast();
        setupListener();
    }

    private void setupEast() throws SQLException {
        add(new JScrollPane(imageList), BorderLayout.EAST);
        fillListModel();
        // 只能单选
        imageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //Cell宽度
        imageList.setFixedCellWidth(160);
    }

    private void setupCenter() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(new JScrollPane(imageLabel), BorderLayout.CENTER);
        JPanel btnPanel = new JPanel();
        btnPanel.add(tfImagePath);
        btnPanel.add(browserBn);
        btnPanel.add(uploadBn);
        leftPanel.add(btnPanel, BorderLayout.SOUTH);

        tfImagePath.setEditable(false);

        add(leftPanel);
    }

    private void setupListener() {
        browserBn.addActionListener(a -> imageChooser.showImageChooser());
        imageChooser.setOnImageSelectedListener(path -> tfImagePath.setText(path.getAbsolutePath()));
        uploadBn.addActionListener(new UploadImageAction());
        imageList.addMouseListener(new DoubleClickToShowImageAdapter());
    }

    // ----------查找img_table填充ListModel----------
    public void fillListModel() throws SQLException {
        try (ResultSet rs = queryAll.executeQuery()) {
            // 先清除所有元素
            imageModel.clear();
            // 把查询的全部记录添加到ListModel中
            while (rs.next()) {
                imageModel.addElement(
                        new ImageHolder(rs.getInt(1), rs.getString(2))
                );
            }
        }
    }

    // ---------将指定图片放入数据库---------
    public void upload(String imgPath) {
        String imageName = filenameUtils.getFileName(imgPath);
        File f = new File(imgPath);
        try (InputStream is = new FileInputStream(f)) {
            // 设置图片名参数
            insert.setString(1, imageName);
            // 原来就是insert数据流就可以了。。。
            insert.setBinaryStream(2, is, (int) f.length());
            int affect = insert.executeUpdate();
            if (affect == 1) {
                // 重新更新ListModel，将会让JList显示最新的图片列表
                fillListModel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class DoubleClickToShowImageAdapter extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            // 鼠标不是双击就返回
            if (e.getClickCount() < 2) {
                return;
            }
            // 取出选中的List项
            ImageHolder cur = imageList.getSelectedValue();
            try {
                // 显示选中项对应的Image
                showImageById(cur.getId());
            } catch (SQLException sqle) {
                sqle.printStackTrace();
            }
        }
    }

    public void showImageById(int id) throws SQLException {
        // 设置id参数为传进来的值
        query.setInt(1, id);
        try (ResultSet rs = query.executeQuery()) {
            if (rs.next()) {
                // 取出Blob列
                Blob imgBlob = rs.getBlob(1);
                //获取图片的bytes，起始位置居然是1
                byte[] imgByteArr = imgBlob.getBytes(1, (int) imgBlob.length());
                //用ImageIcon包装imgByteArr后显示图片
                imageLabel.setIcon(nii(imgByteArr));
            }
        }
    }

    //插入图片到数据库action
    class UploadImageAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 如果上传文件的文本框有内容
            if (tfImagePath.getText().trim().length() > 0) {
                // 将指定文件保存到数据库
                upload(tfImagePath.getText());
                // 清空文本框内容
                tfImagePath.setText("");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        CreateImgTableIfNotExists.main(args);
        FrameUtils.visibleAndExitOnClose(InsertImageByBlobDataType.class, true);
    }

}

