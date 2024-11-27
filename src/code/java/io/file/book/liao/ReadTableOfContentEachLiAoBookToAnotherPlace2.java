package code.java.io.file.book.liao;

import code.java.utils.FileUtils;
import code.java.utils.IOUtils;
import code.java.utils.ProjectFileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import static code.java.utils.LU.println;

/**
 * 将李敖大全集5.0的所有书本目录输出数来.
 */
public class ReadTableOfContentEachLiAoBookToAnotherPlace2 {

    public static void main(String[] args) throws IOException {
        handleAllTablesOfContent();
        showLastThreeLinesOfEveryBook();
    }

    private static void showLastThreeLinesOfEveryBook() throws IOException {
        File liaoBooksTablesOfContentsRootDir = getTablesOfContentDir();
        List<String> lastThreeLinesList = new LinkedList<>();
        for (File dir : liaoBooksTablesOfContentsRootDir.listFiles()) {
            for (File bookFile : dir.listFiles()) {
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new FileReader(bookFile));
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        lastThreeLinesList.add(line);
                        if (lastThreeLinesList.size() > 3) {
                            lastThreeLinesList.remove(0);
                        }
                    }
                } finally {
                    IOUtils.closeQuietly(br);
                }

                println(bookFile.getName());
                while (lastThreeLinesList.size() > 0) {
                    println("\t" + lastThreeLinesList.remove(0));
                }
            }
        }
    }

    private static void handleAllTablesOfContent() throws IOException {
        File liaoBooksRootDir = new File(
                ProjectFileUtils.getProjectRootDir(),
                "temp/李敖大全集5.0(书名按顺序排列)(去除html，仅有文字内容)"
        );
        File liaoBooksTablesOfContentsRootDir = getTablesOfContentDir();
        println("从李敖大全集目录下复制每本书前三行到目标目录对应文件，且每本书目录格式化后才保存");
        println("copy from directory: " + liaoBooksRootDir);
        println("copy to directory: " + liaoBooksTablesOfContentsRootDir);
        saveTablesOfContentsTo(liaoBooksRootDir, liaoBooksTablesOfContentsRootDir);
        println("Copy finished.");
    }

    private static File getTablesOfContentDir() {
        File liaoBooksTablesOfContentsRootDir
                = new File(LiAoBookStore.getLiAoBooksRootDir() + "(每本书的目录)");
        FileUtils.makeDirIfDoesNotExist(liaoBooksTablesOfContentsRootDir);
        return liaoBooksTablesOfContentsRootDir;
    }

    private static void saveTablesOfContentsTo(File fromDir, File toDir) throws IOException {
        for (File fromSubDir : fromDir.listFiles()) {
            File toSubDir = new File(toDir, fromSubDir.getName());
            FileUtils.makeDirIfDoesNotExist(toSubDir);

            for (File srcLiAoBookFile : fromSubDir.listFiles()) {
                File tableOfContentDestFile = new File(toSubDir, srcLiAoBookFile.getName());
                BufferedReader br = null;
                PrintWriter pw = null;
                try {
                    br = new BufferedReader(new FileReader(srcLiAoBookFile), 2048);
                    pw = new PrintWriter(new BufferedWriter(new FileWriter(tableOfContentDestFile)));
                    saveTablesOfContentsTo(br, pw);
                } finally {
                    IOUtils.closeQuietly(br, pw);
                }
            }
        }
    }

    private static void saveTablesOfContentsTo(BufferedReader br, PrintWriter pw) throws IOException {
        String bookName = br.readLine().trim();
        //其实只有目录二字
        String bookSubName = br.readLine().trim();

        List<String> tableOfContentList = new ArrayList<>();

        String oneLine = br.readLine();
        /*
         * 目录规则：
         *     一、正常情况下，书本文件目录二字的下一句就是所有目录合在一起，用“ ”间隔开的本书目录。
         *     二、特殊情况，如《我也李敖一下》，目录下面的整本书目录是好多行结合在一起，
         *      以“.*wjm_tcy.*制作.*”为结束行，如“不自由的自由（wjm_tcy）制作！”。
         *     三、目录结束标志句子还有不规则形式如“不自由的自由（wjm_tcy）根据群友raphael、Jeff Ao、太昊兄三种版本互补所编。”
                  四、“李敖快意恩仇录”这本书在目录下面和“.*wjm_tcy. * 制作. *”
                    上面，还有一句话，而这句话有空格，导致它被当目录切割处理。
                    这类型的书得特殊处理。
         * 得出：目录名是一行或多行，夹在“目录”行和“.*wjm_tcy.*制作.*”行的中间，
         * 另外还有特殊的目录下面和“.*wjm_tcy. * 制作. *”上面，还有一句话，
         * 得根据书名特殊处理
         *
         *
         */
        Pattern tablesOfContentsEndLinePattern = Pattern.compile(
                "(.*wjm_tcy.*制作.*)|(.*wjm_tcy.*编.*)"//一般目录的结束行
                        + "|(李庆元简介)"//《陈水扁的真面目》目录结束行
                        + "|(版权信息)"//《快意还乡——李敖神州文化之旅》目录结束行
                        + "|(《李敖大哥大》简介)"//《李敖大哥大》目录结束行
                        + "|(本书译名悉以李敖认定者为准，间与一般译名有异。)"//《阳痿美国》目录结束行
                        + "|补充中间一段有被删除九页，由李敖影音书籍QQ群feel me提供！错字由孤笑看一线天校对。"//《上山·上山·爱》目录结束行
        );
        while (oneLine != null) {
            oneLine = oneLine.trim();
            if (!tablesOfContentsEndLinePattern.matcher(oneLine).matches()) {
                if (oneLine.length() > 0 /*过滤空行*/) {
                    //不到目录结尾则统统认为是目录，添加到目录集合。
                    tableOfContentList.add(oneLine);
                }
            } else {
                break;
            }
            oneLine = br.readLine();
        }

        String description4TableOfContent = null;
        if (hasDescription4TableOfContent(bookName)) {
            description4TableOfContent = tableOfContentList.remove(tableOfContentList.size() - 1);
        }

        pw.println(bookName);//写入书名
        pw.println(bookSubName);//写入“目录”二字
        pw.println();
        //循环写入目录表
        boolean needSkipNextStr = false;
        int bookIndex = 0;
        String s = null;
        while (tableOfContentList.size() > 0) {
            s = tableOfContentList.remove(0);

            String[] tableOfContent = s.split(" ");
            for (int index = 0; index < tableOfContent.length; index++) {
                //目录一般是用空格隔开
                String titleOfArticle = tableOfContent[index].trim();
                //"他改变了台湾（feel 56me）"这样的目录名，
                // 会因英文名中间的空格而变成两行，所以修正一下。
                if (titleOfArticle.contains("（") && !titleOfArticle.contains("）")
                        && (index + 1) < tableOfContent.length) {
                    String nextStr = tableOfContent[index + 1].trim();
                    if (!nextStr.contains("（") && nextStr.contains("）")) {
                        titleOfArticle += nextStr;
                        needSkipNextStr = true;
                    }
                }

                if (++bookIndex < 10) {
                    pw.println("0" + bookIndex + titleOfArticle);
                } else {
                    pw.println(bookIndex + titleOfArticle);
                }

                if (needSkipNextStr) {
                    ++index;
                    needSkipNextStr = false;
                }

            }

        }

        if (description4TableOfContent != null) {
            pw.println();
            pw.println(description4TableOfContent);//写入“目录”的描述
        }

    }

    //这些书的目录下面都有一句描述的话，不是来源作者李敖，就是来源编者。
    static String[] BookNamesIncludingDescription4TableOfContent = new String[]{
            "为中国思想趋向求答案",
            "李戡专访与脸书合集",
            "李敖登陆记",
            "我也李敖一下",
            "胡适与我",
            "笑傲六十年·有话说李敖",
            "李敖快意恩仇录（十二）",
            "给马戈的五十封信",
            "李敖书信集",
            "李敖秘藏日记",
            "虽千万人，李敖往矣",
            "千秋万岁乌鸦求是合集",
            "李敖全集",
    };

    private static boolean hasDescription4TableOfContent(String bookName) {
        for (String anotherBookName : BookNamesIncludingDescription4TableOfContent) {
            if (anotherBookName.equals(bookName)) {
                return true;
            }
        }
        return false;
    }

}
