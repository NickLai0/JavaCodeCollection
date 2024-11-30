package code.java.io.file.book.liao.builder;

import code.java.io.file.book.liao.data.TableOfContent;
import code.java.io.file.book.liao.data.TableOfContentItem;
import code.java.utils.FileUtils;
import code.java.utils.IOUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import static code.java.utils.LU.println;


/**
 * 李敖书本目录树构建者
 */
public class TableOfContentListBuilder {

    private String bookDir;
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
     */

    public TableOfContentListBuilder setBookDir(String bookDir) {
        this.bookDir = bookDir;
        return this;
    }

    private Pattern tablesOfContentsEndLinePattern = Pattern.compile(
            "(.*wjm_tcy.*制作.*)|(.*wjm_tcy.*编.*)"//一般目录的结束行
                    + "|(李庆元简介)"//《陈水扁的真面目》目录结束行
                    + "|(版权信息)"//《快意还乡——李敖神州文化之旅》目录结束行
                    + "|(《李敖大哥大》简介)"//《李敖大哥大》目录结束行
                    + "|(本书译名悉以李敖认定者为准，间与一般译名有异。)"//《阳痿美国》目录结束行
                    + "|(补充中间一段有被删除九页，由李敖影音书籍QQ群feel me提供！错字由孤笑看一线天校对。)"//《上山·上山·爱》目录结束行
    );


    private void realBuild2(BufferedReader brInput, TableOfContent tableOfContent) throws IOException {
        String temp = null;
        if ((temp = brInput.readLine()) != null) {
            tableOfContent.setBookName(temp.trim());
        }
        if ("我也李敖一下".equals(tableOfContent.getBookName())) {
            println();
        }
        while ((temp = brInput.readLine()) != null) {
            temp = temp.trim();
            if ("目录".equals(temp)) {
                //设置目录二字
                tableOfContent.setTableOfContentTitle(temp);
                break;
            } else {
                //加载书本名下面和目录二字上面的书本描述列表。
                //像《虚拟的十七岁》就是书本说明和一首诗歌
                if (tableOfContent.getBookDescriptionList() == null) {
                    tableOfContent.setBookDescriptionList(new ArrayList<>());
                }
                tableOfContent.getBookDescriptionList().add(temp);
            }
        }
        LinkedList<String> articleTitleAndDescriptionList = new LinkedList<>();
        while ((temp = brInput.readLine()) != null) {
            temp = temp.trim();
            if (!tablesOfContentsEndLinePattern.matcher(temp).matches()) {
                if (temp.length() > 0 /*过滤空行*/) {
                    articleTitleAndDescriptionList.add(temp);
                }
            } else {
                //正则匹配了目录结尾行，所以中断
                break;
            }
        }

        //先倒序处理所有文章的描述行
        int count = descriptionCount(tableOfContent.getBookName());
        if (count > 0) {
            //的目录描述表是一行或多行的，
            LinkedList<String> descriptionList = new LinkedList<>();
            for (int i = 0; i < count; i++) {
                String description = articleTitleAndDescriptionList.removeLast();
                descriptionList.addFirst(description);
            }
            tableOfContent.setTableOfContentDescriptionList(descriptionList);
        }

        if (articleTitleAndDescriptionList.size() > 0) {
            //有目录，所以顺序处理所有目录
            List<TableOfContentItem> tableOfContentItemList = new ArrayList<>();
            //最后处理目录列表行：切割出每个文章标题项，配上序号，封装保持列表
            while (articleTitleAndDescriptionList.size() > 0) {
                temp = articleTitleAndDescriptionList.removeFirst();
                String[] articleTitleArr = temp.split(" ");
                for (int i = 0; i < articleTitleArr.length; i++) {
                    String articleTitle = articleTitleArr[i];
                    TableOfContentItem item = new TableOfContentItem();
                    //有些目录名会被“ ”给切割成两句，下面将此种情况重新组合为一句。
                    if (articleTitle.contains("（") && !articleTitle.contains("）")) {
                        String nextStr = null;
                        if ((i + 1) < articleTitleArr.length && (nextStr = articleTitleArr[i + 1]) != null && !nextStr.contains("（") && nextStr.contains("）")) {
                            //重新组合为完整的目录标题
                            articleTitle += nextStr;
                        }
                    }
                    tableOfContentItemList.add(item);
                    item.setOrder(tableOfContentItemList.size());
                    item.setArticleTitle(articleTitle);
                }
            }
            tableOfContent.setTableOfContentItemList(tableOfContentItemList);
        }

    }

    private int descriptionCount(String bookName) {
        switch (bookName) {
            case "李敖快意恩仇录":
            case "为中国思想趋向求答案":
            case "李戡专访与脸书合集":
            case "我也李敖一下"://这本书的目录有点独特，带有“第x部——...”这样的目录名
            case "胡适与我":
            case "笑傲六十年·有话说李敖":
            case "给马戈的五十封信":
            case "李敖书信集":
            case "李敖秘藏日记":
            case "虽千万人，李敖往矣":
            case "千秋万岁乌鸦求是合集":
            case "李敖全集":
                return 1;
            case "李敖登陆记"://这本书的目录有点独特，带有“上篇：李敖登陆记” “下篇：出版背后的故事”这样的目录名
                return 2;
            default:
                return 0;
        }
    }

    public TableOfContentList build() {
        checkParameters();
        List<TableOfContent> list = new ArrayList<>();
        realBuild(list);
        TableOfContentList tableOfContentList = new TableOfContentList();
        tableOfContentList.list = list;
        return tableOfContentList;
    }

    private void checkParameters() {
        if (bookDir == null) {
            throw new NullPointerException("bookDir is null");
        }
        if (!FileUtils.makeDirIfDoesNotExist(bookDir)) {
            throw new IllegalArgumentException("bookDir doesn't exist");
        }
    }

    private void realBuild(List<TableOfContent> list) {
        for (File subDir : new File(bookDir).listFiles()) {
            for (File bookFile : subDir.listFiles()) {
                BufferedReader br = null;
                try {
                    TableOfContent tableOfContent = new TableOfContent();
                    tableOfContent.setBookParentName(subDir.getName());
                    tableOfContent.setBookFileName(bookFile.getName());
                    realBuild2(br = new BufferedReader(new FileReader(bookFile)), tableOfContent);
                    list.add(tableOfContent);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } finally {
                    IOUtils.closeQuietly(br);
                }
            }
        }
    }

    public static class TableOfContentList {
        private List<TableOfContent> list;

        public List<TableOfContent> getTableOfContentList() {
            //todo:xxx这里应该用原型设计模式，复制一份出来，以让外部无法修改内部的数据。
            //因为忘记原型设计模式原理，所以只能这样！
            return list;
        }
    }

}
