package code.java.io.file.book;

import code.java.io.file.utils.MyFileUtils;
import code.java.utils.FileUtils;
import code.java.view.container.dialog.JDialog.HintDialog;
import code.java.view.container.frame.Jframe.BaseDoubleInputAndTextAreaFrame;
import code.java.view.container.frame.Jframe.CommonBookSeparatorFrame;
import com.mysql.cj.util.StringUtils;
import org.apache.commons.io.IOUtils;

import javax.swing.*;
import java.io.*;

//通用书籍切割器
public class CommonBookSeparator {

    private CommonBookSeparatorFrame f;

    public static void main(String[] args) {
        new CommonBookSeparator().showTxtToDocxFrame();
    }

    private void showTxtToDocxFrame() {
        f = new CommonBookSeparatorFrame();
        f.setTitle("给.txt书籍按照设定的目录进行切割到目标目录");
        f.setOnButtonClickListener(new Handler());
        f.setVisible(true);
    }

    private class Handler implements BaseDoubleInputAndTextAreaFrame.OnButtonClickListener {
        private String[] tableOfContent;

        @Override
        public void onButtonClick(JTextField tfFrom, JTextField tfTo, JTextArea jTextArea) {
            String fromFile = tfFrom.getText().trim();
            String destDir = tfTo.getText().trim();
            if (!checkParameter(fromFile, destDir)) {
                return;
            }
            try {
                String text;
                if (f.isCheck()) {
                    String charsetName = f.getText();
                    text = FileUtils.fileToString(new File(fromFile), charsetName);
                } else {
                    try (FileReader fr = new FileReader(fromFile)) {
                        text = IOUtils.toString(fr);
                    }
                }
                tableOfContent = jTextArea.getText().split("\n");
                splitToDestByTableOfContent(text, tableOfContent, destDir);
            } catch (IOException e) {
                e.printStackTrace();
                new HintDialog(f, e.toString()).show();
            }
        }

        /**
         * 检查源目录、目标目录参数是否正确
         *
         * @param txtFromDirPath
         * @param docxToDirPath
         * @return
         */
        private boolean checkParameter(String txtFromDirPath, String docxToDirPath) {
            String errorMsg = null;

            if (StringUtils.isNullOrEmpty(txtFromDirPath)) {
                errorMsg = "source directory is empty";
            } else {
                File txtFromDir = new File(txtFromDirPath);
                if (!txtFromDir.exists()) {
                    errorMsg = "source directory does not exist";
                } else if (txtFromDir.isDirectory()) {
                    errorMsg = "source directory is not a file";
                }
            }

            if (errorMsg != null) {
                if (StringUtils.isNullOrEmpty(docxToDirPath)) {
                    errorMsg = "dest directory is empty";
                } else {
                    File docxToDir = new File(docxToDirPath);
                    if (!docxToDir.exists()) {
                        errorMsg = "dest directory does not exist";
                    } else if (!docxToDir.isDirectory()) {
                        errorMsg = "dest directory is not a directory";
                    }
                }
            }

            if (errorMsg != null) {
                //有错误消息，提示错误
                new HintDialog(f, errorMsg).show();
                return false;
            }

            //参数无错则返回。
            return true;
        }
    }

    private void splitToDestByTableOfContent(String text, String[] tableOfContent, String dir) throws IOException {
        String currentTitle = tableOfContent[0].trim();
        try (BufferedReader br = new BufferedReader(new StringReader(text))) {
            for (int i = 1; i < tableOfContent.length; i++) {
                String nextTitle = tableOfContent[i].trim();
                //构建当前文章章节
                String article = buildArticleByLastLine(br, nextTitle);
                //将当前文章章节写到目标目录
                writeToDest(currentTitle, article, dir);
               /* println("-----------------------------《" + currentTitle + "》已写入，全文如下-------------------------------------------------");
                println(article);*/
                currentTitle = nextTitle;
            }
            //写最后一章
            writeToDest(currentTitle, IOUtils.toString(br), dir);
        }
        MyFileUtils.addOrderNumbers4EachFile(dir);
    }

    private void writeToDest(String currentTitle, String article, String dir) throws IOException {
        File destFile = new File(dir, currentTitle + ".txt");
        try (FileOutputStream fos = new FileOutputStream(destFile)) {
            IOUtils.write(article, fos);
        }
    }

    private String buildArticleByLastLine(BufferedReader br, String nextTitle) throws IOException {
        StringBuffer articleBuilder = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.contains(nextTitle)) {
                //到达下一章节的第一句，直接中断
                break;
            }
            articleBuilder.append(line).append('\n');
        }
        //返回文章
        return articleBuilder.toString();
    }

}
