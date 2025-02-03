package code.java.io.file.book.liao;

import code.java.io.file.book.liao.builder.BookSeperatedListBuilder;
import code.java.io.file.book.liao.data.BookTableOfContentAndBody;
import code.java.io.file.book.liao.data.TableOfContentItem;
import code.java.io.file.book.liao.utils.LiAoBookUtils;
import code.java.utils.FileUtils;
import code.java.utils.IOUtils;

import java.io.*;
import java.util.List;
import java.util.regex.Pattern;

import static code.java.utils.LU.println;

public class BookSeperatedListWriter {

    private static final String SUFFIX_TABLE_FILE = "-目录.txt";
    private static final String SUFFIX_BOOK_DESCRIPTION_FILE = "-书本描述.txt";
    private static final String SUFFIX_TABLE_DESCRIPTION_FILE = "-目录描述.txt";
    private static final String SUFFIX_ARTICLE_FILE = ".txt";

    private final BookSeperatedListBuilder.BookSeperatedList srcTOCList;

    private final String outputDir;

    public BookSeperatedListWriter(BookSeperatedListBuilder.BookSeperatedList srcTOCList, String outputDir) {
        this.srcTOCList = srcTOCList;
        this.outputDir = outputDir;
    }

    public void write() throws IOException {
        saveBookTo(srcTOCList, new File(outputDir));
    }

    private void saveBookTo(BookSeperatedListBuilder.BookSeperatedList srcTOCList, File toDir) throws IOException {
        for (BookTableOfContentAndBody bookData : srcTOCList.getTableOfContentList()) {
            File toSubDir = new File(
                    //子目录名，如：01自传回忆类
                    new File(toDir, bookData.getBookParentName()),
                    //守护本目录名，结果如：01李敖自传与回忆（因文件名有序号，这一写就不需要排序）
                    bookData.getBookFileName().replace(".txt", ""));
            FileUtils.makeDirIfDoesNotExist(toSubDir);
            try {
                saveBookDescriptionIfHad(bookData, toSubDir);
                saveTableOfContent(bookData, toSubDir);
                saveTableDescriptionIfHad(bookData, toSubDir);
                separateAndSaveBookBody(bookData, toSubDir);
            } catch (Exception e) {
                println("Met an exception：" + bookData);
                throw e;
            }
        }
    }

    private void saveBookDescriptionIfHad(BookTableOfContentAndBody toc, File toSubDir) throws IOException {
        List<String> bdList = toc.getBookDescriptionList();
        if (bdList == null || bdList.isEmpty()) {
            return;
        }
        File descriptionFile = new File(toSubDir, toc.getBookName() + SUFFIX_BOOK_DESCRIPTION_FILE);
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(descriptionFile)))) {
            pw.println(toc.getBookName());//写入书名
            pw.println();//写空行
            for (String articleDescription : bdList) {
                //打印文章描述
                pw.println(articleDescription);
            }
        }
    }

    //此方法和saveBookDescriptionIfHad高度类同
    private void saveTableDescriptionIfHad(BookTableOfContentAndBody toc, File toSubDir) throws IOException {
        List<String> descList = toc.getTableOfContentDescriptionList();
        if (descList == null || descList.isEmpty()) {
            return;
        }
        File descriptionFile = new File(toSubDir, toc.getBookName() + SUFFIX_TABLE_DESCRIPTION_FILE);
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(descriptionFile)))) {
            pw.println(toc.getBookName());//写入书名
            pw.println();//写空行
            for (String articleDescription : descList) {
                //打印文章描述
                pw.println(articleDescription);
            }
        }
    }

    /**
     * 保持书本的目录部分
     *
     * @param tableOfContent
     * @param toSubDir
     * @throws IOException
     */
    private void saveTableOfContent(BookTableOfContentAndBody tableOfContent, File toSubDir) throws IOException {
        File tableOfContentDestFile = new File(toSubDir, tableOfContent.getBookName() + SUFFIX_TABLE_FILE);
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new BufferedWriter(new FileWriter(tableOfContentDestFile)));
            saveTableOfContentStep2(tableOfContent, pw);
        } finally {
            IOUtils.closeQuietly(pw);
        }
    }

    /**
     * 保存书本体部分：切割出每篇文章，一篇文章存一个目录。
     *
     * @param book
     * @param toSubDir
     */
    private void separateAndSaveBookBody(BookTableOfContentAndBody book, File toSubDir) throws IOException {
        /*
        由下面书本文章结构的出：
            一、一般情况
                1、文章标题
                2、文章体
                3、文章结束行
            二、特殊情况
                第x部——xxx(或者是”上篇：...“这样的书部名)
                和“一般情况”一样

        WjmTcyStatistics得出文章的结尾行标志：.*wjm_tcy.*(制作！)$

        得出需求：
            1、如果是一般情况，则切割出来
            2、如果是非一般情况，则将“部名/篇名”
                加入到该部第一篇文章里。
            3、序号则用新的，不按照目录的来
        按：这会导致未来可能要手动的改一些目录、
        序号什么的，没法，因目前无能力全自动。


        李敖文章结构一般和特殊情况：

        一般情况：
            目录：
                01,瞻之在前，忽焉在后（何飞鹏）
            文章内容（规律）：
                瞻之在前，忽焉在后（何飞鹏）
                （...文字内容略）
                 wjm_tcy（不自由的自由）制作！
        特殊情况：
            《李敖登陆记》
                部分目录
                    12,下篇：出版背后的故事
                    13,“他妈的”是粗话吗？
                文章内容（规律）：
                    下篇：出版背后的故事
                    “他妈的”是粗话吗？
                     （...文字内容略）
                     不自由的自由（wjm_tcy）与“Jeff Ao”联合制作！
            《我也李敖一下》和《李敖登陆记》类似，
                部分目录：
                    44,第三部——我也李敖一下
                    45,速溶英雄
                文章内容（规律）：
                    第三部——我也李敖一下
                    速溶英雄
                    （...文字内容略）
                    不自由的自由（wjm_tcy）与“Jeff Ao”联合制作！
         */
        String bookContentBody = book.getBookContentBody();
        BufferedReader br = new BufferedReader(new StringReader(bookContentBody));
        List<TableOfContentItem> tocItemList = book.getTableOfContentItemList();
        String line;
        /*
         * 结束行：
         *      1、天花乱坠、wjm_tcy（不自由的自由）与Jeff Ao联合制作！
         *          对应正则：".*wjm_tcy.*(制作！)$"
         *      2、天花乱坠与Jeff Ao联合制作！
         *          对应正则：".*Jeff Ao.*(制作！)$"
         *
         * 得出最终正则：
         */
        Pattern articleEndLinePattern = Pattern.compile(
                "(.*wjm_tcy.*(制作！)$)"
                        + "|(.*Jeff Ao.*(制作！)$)"
        );
        int articleIndex = 0;
        for (int i = 0, size = tocItemList.size(); i < size; i++) {
            String articleTitle = tocItemList.get(i).getArticleTitle();
            mSbTemp.setLength(0);
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (articleTitle.equals(line)) {
                    mSbTemp.append(line).append("\n");
                    if (i + 1 < size) {
                        //handle second title if it exists.
                        TableOfContentItem tociTemp = tocItemList.get(i + 1);
                        if ((line = br.readLine()) != null) {
                            line = line.trim();
                            if (tociTemp.getArticleTitle().equals(line)) {
                                //If there is second title,which means it is the real article title.
                                articleTitle = tociTemp.getArticleTitle();
                                ++i;//Skip this handled item for next loop.
                            }
                            //Whatever it is, just add this line as the body of this article.
                            mSbTemp.append(line).append("\n");
                        }
                    }
                } else { //Add article body line.
                    mSbTemp.append(line).append("\n");
                    if (articleEndLinePattern.matcher(line).matches()) {
                        //Reach the end line of this article, so break.
                        break;
                    }
                }
            }
            if (needAddNumber(book.getBookName())) {
                saveArticle(
                        book.getBookName(),
                        LiAoBookUtils.addZerosDependsOnSize(++articleIndex, size),
                        correctTitle(articleTitle),
                        mSbTemp.toString(),
                        toSubDir
                );
            } else {
                saveArticle(
                        book.getBookName(),
                        correctTitle(articleTitle),
                        mSbTemp.toString(),
                        toSubDir
                );
            }
        }
    }

    private boolean needAddNumber(String bookName) {
        switch (bookName) {
            case "李敖有话说":
                return false;
        }
        return true;
    }

    //纠正文章标题如“李敖：不忘初心，砥砺前行。（散翎/千灯茶社）”中的“/”要替换为“-”，这样文件才能成功创建。
    private String correctTitle(String articleTitle) {
        return articleTitle.replace("/", "-");
    }

    //保存文章
    private void saveArticle(String bookName, String number, String title, String content, File toSubDir) throws FileNotFoundException {
        saveArticle(bookName, number + title, content, toSubDir);
    }

    //保存文章
    private void saveArticle(String bookName, String articleName, String content, File toSubDir) throws FileNotFoundException {
        PrintWriter pw = null;
        //文件名：序号+文章标题+后缀
        String filename = articleName + SUFFIX_ARTICLE_FILE;
        try {
            pw = new PrintWriter(new File(toSubDir, filename));
            pw.println("注：来源《" + bookName + "》");
            pw.println(content);
        } finally {
            IOUtils.closeQuietly(pw);
        }
    }

    public static final String TABLE_OF_CONTENT_SEPARATOR = ",";
    public static final String PREFIX_LESS_THAN_TEN = "0";
    public static final String PREFIX_LESS_THAN_ONE_HUNDRED = "00";

    private static final StringBuffer mSbTemp = new StringBuffer();

    private void saveTableOfContentStep2(BookTableOfContentAndBody toc, PrintWriter pw) throws IOException {
        pw.println(toc.getBookName());//写入书名
        pw.println(toc.getTableOfContentTitle());//写入“目录”二字
        pw.println();//写空行
        List<TableOfContentItem> tociList = toc.getTableOfContentItemList();
        if (tociList != null && tociList.size() > 0) {
            //printSerialTitle2方法的代码简化版，但运行效率会稍差些（没时间测试）
//            printSerialTitle(pw, tociList);
            printSerialTitle2(pw, tociList);
        }
      /*
      //目录描述单独存放一个文件
      List<String> descriptionList = toc.getTableOfContentDescriptionList();
        if (descriptionList != null) {
            pw.println();//打印空行
            for (String description : descriptionList) {
                pw.println(description);//打印目录描述
            }
        }
        */
    }

    //printSerialTitle2方法的代码简化版，但运行效率会稍差些
    private static void printSerialTitle(PrintWriter pw, List<TableOfContentItem> tociList) {
        for (int i = 0, size = tociList.size(); i < size; i++) {
            TableOfContentItem toci = tociList.get(i);
            //目录一般是用空格隔开
            mSbTemp.setLength(0);
            mSbTemp.append(LiAoBookUtils.addZerosDependsOnSize(toci.getOrder(), size));
            mSbTemp.append(toci.getArticleTitle());
            pw.println(mSbTemp);
        }
    }

    //printSerialTitle方法的代码增多版本，但运行效率会稍好些
    private static void printSerialTitle2(PrintWriter pw, List<TableOfContentItem> tociList) {
        TableOfContentItem toci;
        if (tociList.size() < 100) {
            for (int i = 0; i < tociList.size(); i++) {
                toci = tociList.get(i);
                int titleOrder = toci.getOrder();
                //目录一般是用空格隔开
                mSbTemp.setLength(0);
                if (titleOrder < 10) {
                    mSbTemp.append(PREFIX_LESS_THAN_TEN);
                }
                mSbTemp.append(titleOrder).append(TABLE_OF_CONTENT_SEPARATOR).append(toci.getArticleTitle());
                pw.println(mSbTemp);
            }
        } else {
            for (int i = 0; i < tociList.size(); i++) {
                toci = tociList.get(i);
                int titleOrder = toci.getOrder();
                //目录一般是用空格隔开
                mSbTemp.setLength(0);
                if (titleOrder < 10) {
                    mSbTemp.append(PREFIX_LESS_THAN_ONE_HUNDRED);
                } else if (titleOrder < 100) {
                    mSbTemp.append(PREFIX_LESS_THAN_TEN);
                }
                mSbTemp.append(titleOrder).append(TABLE_OF_CONTENT_SEPARATOR).append(toci.getArticleTitle());
                pw.println(mSbTemp);
            }
        }
    }

}
