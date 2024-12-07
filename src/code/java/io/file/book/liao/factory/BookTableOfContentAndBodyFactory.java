package code.java.io.file.book.liao.factory;

import code.java.io.file.book.liao.data.BookTableOfContentAndBody;
import code.java.io.file.book.liao.data.TableOfContentItem;
import code.java.utils.IOUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class BookTableOfContentAndBodyFactory {

    public static BookTableOfContentAndBody create(String bookSrcPath) {
        BufferedReader br = null;
        try {
            File bookFile = new File(bookSrcPath);
            br = new BufferedReader(new FileReader(bookSrcPath));
            BookTableOfContentAndBody book = new BookTableOfContentAndBody();
            book.setBookParentName(bookFile.getParentFile().getName());
            book.setBookFileName(bookFile.getName());
            separatingBookAndSave(br, book);
            return book;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(br);
        }
    }

    private static Pattern tablesOfContentsEndLinePattern = Pattern.compile(
            "(.*wjm_tcy.*制作.*)|(.*wjm_tcy.*编.*)"//一般目录的结束行
                    + "|(李庆元简介)"//《陈水扁的真面目》目录结束行
                    + "|(版权信息)"//《快意还乡——李敖神州文化之旅》目录结束行
                    + "|(《李敖大哥大》简介)"//《李敖大哥大》目录结束行
                    + "|(本书译名悉以李敖认定者为准，间与一般译名有异。)"//《阳痿美国》目录结束行
    );

    private static void separatingBookAndSave(BufferedReader brInput, BookTableOfContentAndBody book) throws IOException {
        String temp = null;
        if ((temp = brInput.readLine()) != null) {
            book.setBookName(temp.trim());
        }
        while ((temp = brInput.readLine()) != null) {
            temp = temp.trim();
            if ("目录".equals(temp)) {
                //设置目录二字
                book.setTableOfContentTitle(temp);
                break;
            } else {
                //加载书本名下面和目录二字上面的书本描述列表。
                //像《虚拟的十七岁》就是书本说明和一首诗歌
                if (book.getBookDescriptionList() == null) {
                    book.setBookDescriptionList(new ArrayList<>());
                }
                book.getBookDescriptionList().add(temp);
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

        //先倒序处理所有目录的描述行
        int count = descriptionCount(book.getBookName());
        if (count > 0) {
            //的目录描述表是一行或多行的，
            LinkedList<String> descriptionList = new LinkedList<>();
            for (int i = 0; i < count; i++) {
                String description = articleTitleAndDescriptionList.removeLast();
                descriptionList.addFirst(description);
            }
            book.setTableOfContentDescriptionList(descriptionList);
        }

        if (articleTitleAndDescriptionList.size() > 0) {
            //有目录，所以顺序处理所有目录
            List<TableOfContentItem> tableOfContentItemList = new ArrayList<>();
            if (needSplitOutTable(book.getBookName())) {
                //每行目录由多个标题组成，所以切割处理
                splitOutTableOfContent(articleTitleAndDescriptionList, tableOfContentItemList);
            } else {
                //每行是一个目录，不需切割。
                handleTableOfContent4EachLine(articleTitleAndDescriptionList, tableOfContentItemList);
            }
            book.setTableOfContentItemList(tableOfContentItemList);
        }

        //将书本的书体（所有文章含标题）转为一个字符串保存下来
        StringBuffer sb = new StringBuffer(8 * 1024);
        while ((temp = brInput.readLine()) != null) {
            sb.append(temp).append("\n");
        }
        book.setBookContentBody(sb.toString());
    }

    private static void handleTableOfContent4EachLine(LinkedList<String> articleTitleAndDescriptionList, List<TableOfContentItem> tableOfContentItemList) {
        String articleTitle;
        while (articleTitleAndDescriptionList.size() > 0) {
            articleTitle = articleTitleAndDescriptionList.removeFirst();
            if(articleTitle!=null && articleTitle.length() >0) {
                TableOfContentItem item = new TableOfContentItem();
                tableOfContentItemList.add(item);
                item.setOrder(tableOfContentItemList.size());
                item.setArticleTitle(articleTitle);
            }
        }
    }

    private static boolean needSplitOutTable(String bookName) {
        switch (bookName) {
            case "李敖有话说":
                return false;
            default://默认情况下都需要切割
                return true;
        }
    }

    //每一行都有很多目录，由“ ”隔开，所以切割出每个文章标题，
    //配上序号，封装成标题项后保持列表
    private static void splitOutTableOfContent(LinkedList<String> articleTitleAndDescriptionList, List<TableOfContentItem> tableOfContentItemList) {
        String temp;
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
    }

    //返回目录列表下面的描述行个数
    private static int descriptionCount(String bookName) {
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
            case "上山·上山·爱":
                return 1;
            case "李敖登陆记"://这本书的目录有点独特，带有“上篇：李敖登陆记” “下篇：出版背后的故事”这样的目录名
                return 2;
            default:
                return 0;
        }
    }

}