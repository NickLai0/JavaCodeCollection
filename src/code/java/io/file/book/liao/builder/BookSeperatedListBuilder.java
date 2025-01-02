package code.java.io.file.book.liao.builder;

import code.java.io.file.book.liao.data.BookTableOfContentAndBody;
import code.java.io.file.book.liao.factory.BookTableOfContentAndBodyFactory;
import code.java.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * 李敖书本目录和内容树构建者
 */
public class BookSeperatedListBuilder {

    private String bookSrcDir;

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
    public BookSeperatedListBuilder setBookSrcDir(String bookSrcDir) {
        this.bookSrcDir = bookSrcDir;
        return this;
    }

    public BookSeperatedList build() {
        checkParameters();
        List<BookTableOfContentAndBody> list = new ArrayList<>();
        realBuild(list);
        BookSeperatedList tableOfContentList = new BookSeperatedList();
        tableOfContentList.list = list;
        return tableOfContentList;
    }

    private void checkParameters() {
        if (bookSrcDir == null) {
            throw new NullPointerException("bookDir is null");
        }
        if (!FileUtils.makeDirIfDoesNotExist(bookSrcDir)) {
            throw new IllegalArgumentException("bookDir doesn't exist");
        }
    }

    private void realBuild(List<BookTableOfContentAndBody> bookList) {
        buildRecursively(new File(bookSrcDir), bookList);
    }

    private void buildRecursively(File f, List<BookTableOfContentAndBody> bookList) {
        if (f.isDirectory()) {
            for (File file : f.listFiles()) {
                if (file.isDirectory()) {
                    //目录则递归处理
                    buildRecursively(file, bookList);
                } else {
                    //文件则直接添加
                    addBookTableOfContentAndBody(bookList, file);
                }
            }
        } else {
            //文件则直接添加
            addBookTableOfContentAndBody(bookList, f);
        }

    }

    private static void addBookTableOfContentAndBody(List<BookTableOfContentAndBody> bookList, File bookFile) {
        BookTableOfContentAndBody book =
                BookTableOfContentAndBodyFactory.create(
                        bookFile.getAbsolutePath()
                );
        bookList.add(book);
    }

    public static class BookSeperatedList {
        private List<BookTableOfContentAndBody> list;

        public List<BookTableOfContentAndBody> getTableOfContentList() {
            //todo:xxx这里应该用原型设计模式，复制一份出来，以让外部无法修改内部的数据。
            //因为忘记原型设计模式原理，所以只能这样！
            return list;
        }
    }

}
