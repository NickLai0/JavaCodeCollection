package code.java.database.dml;

import code.java.database.ddl.CreateImgTableIfNotExists;
import code.java.io.file.book.fkjjy.utils.FKJJYUtils;
import code.java.utils.FrameUtils;
import code.java.view.JFileChooser.filter.CommonImageExtensionFileFilter;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
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
    private JTextField filePath = new JTextField(26);
    private JButton browserBn = new JButton("...");
    private JButton uploadBn = new JButton("图片插入数据库");
    private JLabel imageLabel = new JLabel();
    // 以当前路径创建文件选择器
    JFileChooser chooser = new JFileChooser(".");
    // 创建文件过滤器
//    ExtensionFileFilter filter = new ExtensionFileFilter();
    FileFilter filter = new CommonImageExtensionFileFilter();

    {
        try {
            // 获取数据库连接
            conn = FKJJYUtils.getTestDBConnection();
            // 创建执行插入的PreparedStatement对象，
            // 该对象执行插入后可以返回自动生成的主键
            insert = conn.prepareStatement("insert into img_table" + " values(null,?,?)", Statement.RETURN_GENERATED_KEYS);
            // 创建两个PreparedStatement对象，用于查询指定图片，查询所有图片
            query = conn.prepareStatement("select img_data from img_table" + " where img_id=?");
            queryAll = conn.prepareStatement("select img_id, " + " img_name from img_table");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init() throws SQLException {
        setSize(620, 400);
        chooser.addChoosableFileFilter(filter);
        // 禁止“文件类型”下拉列表中显示“所有文件”选项。
        chooser.setAcceptAllFileFilterUsed(false);
        // ---------初始化程序界面---------
        fillListModel();
        filePath.setEditable(false);
        // 只能单选
        imageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JPanel jp = new JPanel();
        jp.add(filePath);
        jp.add(browserBn);
        browserBn.addActionListener(new BrowseImageAction());
        jp.add(uploadBn);
        uploadBn.addActionListener(new UploadImageAction());
        JPanel left = new JPanel();
        left.setLayout(new BorderLayout());
        left.add(new JScrollPane(imageLabel), BorderLayout.CENTER);
        left.add(jp, BorderLayout.SOUTH);
        add(left);
        imageList.setFixedCellWidth(160);
        add(new JScrollPane(imageList), BorderLayout.EAST);
        imageList.addMouseListener(new ShowImageAdapter());
    }

    // ----------查找img_table填充ListModel----------
    public void fillListModel() throws SQLException {
        try (ResultSet rs = queryAll.executeQuery()) {
            // 先清除所有元素
            imageModel.clear();
            // 把查询的全部记录添加到ListModel中
            while (rs.next()) {
                imageModel.addElement(new ImageHolder(rs.getInt(1), rs.getString(2)));
            }
        }
    }

    // ---------将指定图片放入数据库---------
    public void upload(String fileName) {
        // 截取文件名
        String imageName = fileName.substring(
                fileName.lastIndexOf('\\') + 1,
                fileName.lastIndexOf('.')
        );
        File f = new File(fileName);
        try (InputStream is = new FileInputStream(f)) {
            // 设置图片名参数
            insert.setString(1, imageName);
            // 设置二进制流参数
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

    class ShowImageAdapter extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            // 如果鼠标双击
            if (e.getClickCount() >= 2) {
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
    }

    // ---------根据图片ID来显示图片----------
    public void showImageById(int id) throws SQLException {
        // 设置参数
        query.setInt(1, id);
        try (ResultSet rs = query.executeQuery()) {
            if (rs.next()) {
                // 取出Blob列
                Blob imgBlob = rs.getBlob(1);
                //getBytes的起始位置居然是1
                byte[] imgByteArr = imgBlob.getBytes(1, (int) imgBlob.length());
                // 取出Blob列里的数据
                imageLabel.setIcon(nii(imgByteArr));
            }
        }
    }

    class BrowseImageAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 显示文件对话框
            int result = chooser.showDialog(InsertImageByBlobDataType.this, "选择图片");
            // 如果用户选择了APPROVE（赞同）按钮，即打开，保存等效按钮
            if (result == JFileChooser.APPROVE_OPTION) {
                filePath.setText(chooser.getSelectedFile().getPath());
            }
        }
    }

    class UploadImageAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 如果上传文件的文本框有内容
            if (filePath.getText().trim().length() > 0) {
                // 将指定文件保存到数据库
                upload(filePath.getText());
                // 清空文本框内容
                filePath.setText("");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        CreateImgTableIfNotExists.main(args);
        FrameUtils.visibleAndExitOnClose(InsertImageByBlobDataType.class, true);
    }

}

// 创建一个ImageHolder类，用于封装图片名、图片ID
class ImageHolder {
    // 封装图片的ID
    private int id;
    // 封装图片的图片名字
    private String name;

    public ImageHolder() {
    }

    public ImageHolder(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // id的setter和getter方法
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    // name的setter和getter方法
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    // 重写toString方法，返回图片名
    public String toString() {
        return name;
    }
}
