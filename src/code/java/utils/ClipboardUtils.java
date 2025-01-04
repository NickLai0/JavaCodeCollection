package code.java.utils;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

/*
 剪切板工具

 JToolBarTest类也有Toolkit.getDefaultToolkit().getSystemClipboard();使用案例
 */
public class ClipboardUtils {

    private static final Clipboard sfClipboard =
            Toolkit.getDefaultToolkit().getSystemClipboard();

    public static void copyStrToClipboard(String str) {
        if (str == null) {
            return;
        }
        /*
        the creation of a
        StringSelection object from the String, and the call to
        setContents( ) with this StringSelection.
        That’s all there is to putting a String on the clipboard.
        */
        StringSelection clipString = new StringSelection(str);
        sfClipboard.setContents(clipString, clipString);
    }

    /**
     * @return null，之前没复制过字string，故返null
     */
    public static String getStrFromClipboard() {
        Transferable clipData = sfClipboard.getContents(ClipboardUtils.class);
        try {
            /*
            The DataFlavor representing a Java Unicode String class,
            where: representationClass = java. lang. String
            mimeType= "application/ x-java-serialized-object
             */
            return (String) clipData.getTransferData(DataFlavor.stringFlavor);
        } catch (Exception ex) {
            System.out.println("not String flavor");
            return null;
        }
    }


}
