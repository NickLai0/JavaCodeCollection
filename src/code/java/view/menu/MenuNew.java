package code.java.view.menu;

//: MenuNew.java
// Menus in Java 1.1

import java.awt.*;
import java.awt.event.*;

/*
 * demonstrate the usages of Menu,MenuBar,
 * MenuItem,CheckboxMenuItem,Frame in a
 * listening events way.
 * <p>
 * This demo comes from Thinking in Java.
 */
public class MenuNew extends Frame {
    String flavors[] = {"Chocolate", "Strawberry",
            "Vanilla Fudge Swirl", "Mint Chip",
            "Mocha Almond Fudge", "Rum Raisin",
            "Praline Cream", "Mud Pie"};
    TextField t = new TextField("No flavor", 30);
    MenuBar mb1 = new MenuBar();
    Menu f = new Menu("File");
    Menu m = new Menu("Flavors");
    Menu s = new Menu("Safety");
    // Alternative approach:
    CheckboxMenuItem safety[] = {
            new CheckboxMenuItem("Guard"),
            new CheckboxMenuItem("Hide")
    };
    MenuItem file[] = {
            new MenuItem("Open"),
            new MenuItem("Exit")
    };
    // A second menu bar to swap to:
    MenuBar mb2 = new MenuBar();
    Menu fooBar = new Menu("fooBar");
    MenuItem other[] = {
            new MenuItem("Foo"),
            new MenuItem("Bar"),
            new MenuItem("Baz"),
    };

    // Initialization code:
    {
        ML ml = new ML();
        CMI cmi = new CMI();
        /*
 This seems a bit strange because in each case the
 * “action command” is exactly the same as the label on the menu component.
 * Why not just use the label, instead of this alternative string?
 * The problem is internationalization. If you retarget this program
 * to another language, you only want to change the label in the menu,
 * and not go through the code
 * changing all the logic which will no doubt introduce new errors. */
        safety[0].setActionCommand("Guard");
        safety[0].addItemListener(cmi);
        safety[1].setActionCommand("Hide");
        safety[1].addItemListener(cmi);
        file[0].setActionCommand("Open");
        file[0].addActionListener(ml);
        file[1].setActionCommand("Exit");
        file[1].addActionListener(ml);
        other[0].addActionListener(new FooL());
        other[1].addActionListener(new BarL());
        other[2].addActionListener(new BazL());
    }

    Button b = new Button("Swap Menus");

    public MenuNew() {
        FL fl = new FL();
        for (int i = 0; i < flavors.length; i++) {
            MenuItem mi = new MenuItem(flavors[i]);
            mi.addActionListener(fl);
            m.add(mi);
            // Add separators at intervals:
            if ((i + 1) % 3 == 0)

                m.addSeparator();
        }
        for (int i = 0; i < safety.length; i++)
            s.add(safety[i]);
        f.add(s);
        for (int i = 0; i < file.length; i++)
            f.add(file[i]);
        mb1.add(f);
        mb1.add(m);
        setMenuBar(mb1);
        t.setEditable(false);
        add(t, BorderLayout.CENTER);
        // Set up the system for swapping menus:
        b.addActionListener(new BL());
        add(b, BorderLayout.NORTH);
        for (int i = 0; i < other.length; i++)
            fooBar.add(other[i]);
        mb2.add(fooBar);
    }

    class BL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            MenuBar m = getMenuBar();
            if (m == mb1) setMenuBar(mb2);
            else if (m == mb2) setMenuBar(mb1);
        }
    }

    class ML implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            MenuItem target = (MenuItem) e.getSource();
            String actionCommand = target.getActionCommand();
            if (actionCommand.equals("Open")) {
                String s = t.getText();
                boolean chosen = false;
                for (int i = 0; i < flavors.length; i++)
                    if (s.equals(flavors[i])) chosen = true;
                if (!chosen)
                    t.setText("Choose a flavor first!");
                else
                    t.setText("Opening " + s + ". Mmm, mm!");
            } else if (actionCommand.equals("Exit")) {
                /*
                 * if “Exit” is chosen, a new WindowEvent is created,
                 * passing in the handle of the enclosing class object (MenuNew.this) and
                 * creating a WINDOW_CLOSING event. This is handed to the dispatchEvent( )
                 * method of the enclosing class object, which then ends up calling
                 * windowClosing( ) inside WL, just as if the message had been
                 * generated the “normal” way. Through this mechanism,
                 * you can dispatch any message you want in any
                 * circumstances, so it’s quite powerful.*/
                dispatchEvent(
                        new WindowEvent(MenuNew.this, WindowEvent.WINDOW_CLOSING)
                );
            }
        }
    }

    /*
     *   The FL listener is simple even though it’s handling all
     *   the different flavors in the flavor menu. This
     *   approach is useful if you have enough simplicity in your logic,
     *   but in general you’ll usually want to take
     *   the approach used with FooL, BarL and BazL,
     *   where they are each only attached to a single menu
     *   component, so no extra detection logic is necessary
     *   and you know exactly who called the listener.
     *   Even with the profusion(众多/many) of classes generated this way,
     *  the code inside tends to be smaller and the
     *   process is more foolproof(使用简便).
     */
    class FL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            MenuItem target = (MenuItem) e.getSource();
            t.setText(target.getLabel());
        }
    }

    // Alternatively, you can create a different
    // class for each different MenuItem. Then you
    // Don't have to figure out which one it is:
    class FooL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            t.setText("Foo selected");
        }
    }

    class BarL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            t.setText("Bar selected");
        }
    }

    class BazL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            t.setText("Baz selected");
        }
    }

    class CMI implements ItemListener {
        public void itemStateChanged(ItemEvent e) {
            CheckboxMenuItem target =
                    (CheckboxMenuItem) e.getSource();
            String actionCommand =
                    target.getActionCommand();
            if (actionCommand.equals("Guard"))
                t.setText("Guard the Ice Cream! " +
                        "Guarding is " + target.getState());
            else if (actionCommand.equals("Hide"))
                t.setText("Hide the Ice Cream! " +
                        "Is it cold? " + target.getState());
        }
    }

    static class WL extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }

    public static void main(String args[]) {
        MenuNew f = new MenuNew();
        f.setSize(300, 200);
        f.setVisible(true);
        f.addWindowListener(new WL());
    }
}


/**
 * the following description from Thinking in Java.
 * <p>
 * This code is essentially the same as the previous (Java 1.0) version,
 * until you get to the initialization section
 * (marked by the opening brace right after the comment “Initialization code:”).
 * Here you can see the ItemListeners and ActionListeners attached to the
 * various menu components.
 * You can also see the use of setActionCommand( ).
 * <p>
 * This seems a bit strange because in each case the
 * “action command” is exactly the same as the label on the menu component.
 * Why not just use the label, instead of this alternative string?
 * The problem is internationalization. If you retarget this program
 * to another language, you only want to change the label in the menu,
 * and not go through the code
 * changing all the logic which will no doubt introduce new errors.
 * So to make this easy for code that
 * checks the text string associated with a menu component,
 * the “action command” can be immutable(unchangeable) while
 * the menu label can change.
 * All the code works with the “action command,” so it’s unaffected by
 * changes to the menu labels. Notice that in this program,
 * not all the menu components are examined(checked)
 * for their action commands, so those that aren’t don’t
 * have their action command set.
 * <p>
 * Much of the constructor is the same as before,
 * with the exception of a couple of calls to add listeners.
 * The bulk(much) of the work happens in the listeners themselves.
 * In BL, the MenuBar swapping happens as in
 * the previous example. In ML,
 * the “figure out who rang” approach is taken by getting
 * the source of the ActionEvent and casting it to a MenuItem,
 * then getting the action command string to pass it through a
 * cascaded(连级的) if statement. Much of this is the same as before,
 * but notice that if “Exit” is chosen, a new WindowEvent is created,
 * passing in the handle of the enclosing class object (MenuNew.this) and
 * creating a WINDOW_CLOSING event. This is handed to the dispatchEvent( )
 * method of the enclosing class object, which then ends up calling
 * windowClosing( ) inside WL, just as if the message had been
 * generated the “normal” way. Through this mechanism,
 * you can dispatch any message you want in any
 * circumstances, so it’s quite powerful.
 * <p>
 * The FL listener is simple even though it’s handling all
 * the different flavors in the flavor menu. This
 * approach is useful if you have enough simplicity in your logic,
 * but in general you’ll usually want to take
 * the approach used with FooL, BarL and BazL,
 * where they are each only attached to a single menu
 * component, so no extra detection logic is necessary and
 * you know exactly who called the listener.
 * Even with the profusion of classes generated this way,
 * the code inside tends to be smaller and the
 * process is more foolproof.
 *
 */