package code.java.pattern.creational.bulder;

import code.java.io.file.book.liao.SeparateAndSaveBooks;
import code.java.io.file.book.liao.builder.BookSeperatedListBuilder;

import java.io.IOException;

public class BookSeperatedListBuilderRouter {
    public static void main(String[] args) throws IOException {
        //内部用到BookSeperatedListBuilder
        SeparateAndSaveBooks.main(args);
    }
}
