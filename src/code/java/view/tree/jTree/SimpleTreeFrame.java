package code.java.view.tree.jTree;

import code.java.utils.FrameUtils;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.*;

/**
 * From Core Java 2.
 * <p>
 * Modify a lot.
 * <p>
 * This frame contains a simple tree that displays a
 * manually constructed tree model.
 */
public class SimpleTreeFrame extends JFrame {

    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;

    DefaultMutableTreeNode root = new DefaultMutableTreeNode("World");

    DefaultMutableTreeNode USA = new DefaultMutableTreeNode("USA");
    DefaultMutableTreeNode stateCalifornia = new DefaultMutableTreeNode("California");
    DefaultMutableTreeNode citySanJose = new DefaultMutableTreeNode("San Jose");
    DefaultMutableTreeNode cityCupertino = new DefaultMutableTreeNode("Cupertino");
    DefaultMutableTreeNode stateMichigan = new DefaultMutableTreeNode("Michigan");
    DefaultMutableTreeNode cityAnnArbor = new DefaultMutableTreeNode("Ann Arbor");

    DefaultMutableTreeNode germany = new DefaultMutableTreeNode("Germany");
    DefaultMutableTreeNode stateSchleswigHolstein = new DefaultMutableTreeNode("Schleswig-Holstein");
    DefaultMutableTreeNode cityKiel = new DefaultMutableTreeNode("Kiel");

    JTree tree;

    public SimpleTreeFrame() {
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setupUSANode();
        setupGermanyNode();
        // construct tree and put it in a scroll pane
        tree = new JTree(root);
        add(new JScrollPane(tree), BorderLayout.CENTER);
    }

    private void setupGermanyNode() {
        root.add(germany);
        germany.add(stateSchleswigHolstein);
        stateSchleswigHolstein.add(cityKiel);
    }

    private void setupUSANode() {
        // set up tree model data
        root.add(USA);
        //setup California state.
        USA.add(stateCalifornia);
        stateCalifornia.add(citySanJose);
        stateCalifornia.add(cityCupertino);
        //setup Michigan state.
        USA.add(stateMichigan);
        stateMichigan.add(cityAnnArbor);
    }

    public static void main(String[] args) {
        FrameUtils.visibleAndExitOnClose(SimpleTreeFrame.class);
    }

}
