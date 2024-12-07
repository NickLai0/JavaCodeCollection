package code.java.store.preference;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import static code.java.utils.LU.*;
/*
演示Preferences的基本用法，可和ImageViewer.java配合，但需先运行它。

The Preferences class provides such a
central repository in a platform independent manner.
In Windows, the Preferences class uses the registry
for storage; on Linux, the information is stored
in the local ﬁle system instead. Of course, the
repository implementation is transparent to the
programmer using the Preferences class.
The Preferences repository has a tree structure,
with node path names such as /com/mycompany/myapp.

Each program user has one tree;
an additional tree, called the system tree,
is available for settings that are common to all users.
* */
public class PreferenceDemo {
    public static void main(String[] args) throws BackingStoreException {
        Preferences userRoot = Preferences.userRoot();
        Preferences userNode = userRoot.node("/com/horstmann/corejava/ImageViewer");
        println("userRoot = " + userRoot);
        println("userNode = " + userNode);
        userNode.put(PreferenceDemo.class.getName(), "a new string saved from this class.");
        for (String key : userNode.keys()) {
            println("\t" + key + " = " + userNode.get(key, ""));
        }

        printSeparateLine();
        Preferences systemRoot = Preferences.systemRoot();
        println("systemRoot = " + systemRoot);
       /*
        调用systemRoot.keys()抛异常：
            ...java.util.prefs.BackingStoreException: Could not open windows registry node Software\JavaSoft\Prefs at root 0xffffffff80000002.
       */
        Preferences systemNode = systemRoot.node("/com/horstmann/corejava/ImageViewer");
        println("systemNode = " + systemNode);
        systemNode.put(PreferenceDemo.class.getName(), "a string saved from this class.");
        /*
            调用systemNode.keys()抛异常：...BackingStoreException...
       */

        printSeparateLine();
        Preferences userNodeForPackage = Preferences.userNodeForPackage(PreferenceDemo.class.getClass());
        println("userNodeForPackage = " + userNodeForPackage);
        userNodeForPackage.put(PreferenceDemo.class.getName(), "a string saved from this class.");
        for (String key : userNodeForPackage.keys()) {
            println("\t" + key + " = " + userNode.get(key, ""));
        }

        printSeparateLine();
        Preferences systemNodeForPackage = Preferences.systemNodeForPackage(PreferenceDemo.class.getClass());
        println("systemNodeForPackage = " + systemNodeForPackage);
        systemNodeForPackage.put(PreferenceDemo.class.getName(), "a string saved from this class.");
          /*
            调用systemNodeForPackage.keys()抛异常：...BackingStoreException...
          */
    }

}
