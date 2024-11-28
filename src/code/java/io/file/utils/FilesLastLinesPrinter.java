package code.java.io.file.utils;

import code.java.utils.FileUtils;
import code.java.utils.IOUtils;
import code.java.utils.LU;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

import static code.java.utils.LU.println;

public class FilesLastLinesPrinter {

    private final String sourceDir;

    private final int printLastLinesNumber;

    public FilesLastLinesPrinter(String srcDir, int printLastLinesNumber) {
        if (!FileUtils.makeDirIfDoesNotExist(srcDir)) {
            throw new IllegalArgumentException("srcDir doesn't exist");
        }
        if (printLastLinesNumber < 1) {
            throw new IllegalArgumentException("printLastLinesNumber can not smaller than 1");
        }
        this.sourceDir = srcDir;
        this.printLastLinesNumber = printLastLinesNumber;
    }

    public void print() throws IOException {
        printRecursively(sourceDir, 0);
    }

    private void printRecursively(String srcDir, int i) throws IOException {
        if (srcDir == null) {
            return;
        }
        File dir = new File(srcDir);
        if (!dir.exists()) {
            return;
        }
        File[] fileList = dir.listFiles();
        if (fileList == null || fileList.length == 0) {
            return;
        }
        List<String> lastThreeLinesList = new LinkedList<>();
        for (File f : fileList) {
            if (f == null) {
                continue;
            }
            if (f.isFile()) {
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new FileReader(f));
                    String line = null;
                    //todo:有没有更高效的可以直接跳到最末尾读取的方式？而不是一行一行地读到最后。
                    while ((line = br.readLine()) != null) {
                        lastThreeLinesList.add(line);
                        if (lastThreeLinesList.size() > printLastLinesNumber) {
                            lastThreeLinesList.remove(0);
                        }
                    }
                } finally {
                    IOUtils.closeQuietly(br);
                }
                printTab(i - 1);
                println(f.getName());
                while (lastThreeLinesList.size() > 0) {
                    printTab(i);
                    println(lastThreeLinesList.remove(0));
                }
            } else {
                File subDir = f;
                printRecursively(subDir.getAbsolutePath(), i + 1);
            }
        }
    }

    private void printTab(int tabNum) {
        for (int i = 0; i < tabNum; i++) {
            LU.print("\t");
        }
    }
}
