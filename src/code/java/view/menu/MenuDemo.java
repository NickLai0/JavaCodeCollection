package code.java.view.menu;

import java.awt.*;


/**
 * demonstrate the usages of Menu,MenuBar,
 * MenuItem,CheckboxMenuItem,Frame.
 * <p>
 * This demo comes from Thinking in Java.
 * I modify a lot. It is not that good
 * for this demo to use Frame, use JFrame
 * would be better, but I just keep to
 * use Frame here.
 */
public class MenuDemo extends Frame {

    String flavors[] = {
            "Chocolate",
            "Strawberry",
            "Vanilla Fudge Swirl",
            "Mint Chip",
            "Mocha Almond Fudge",
            "Rum Raisin",
            "Praline Cream",
            "Mud Pie"
    };

    TextField textField = new TextField("No flavor", 30);

    MenuBar menuBar1 = new MenuBar();

    Menu menuFile = new Menu("File");
    Menu menuSafety = new Menu("Safety");
    Menu menuFlavors = new Menu("Flavors");

    private final String LABEL_OPEN = "Open";
    private final String LABEL_EXIT = "Exit";

    //Menu Item belongs to a specific menu.
    MenuItem fileMenuItems[] = {new MenuItem(LABEL_OPEN), new MenuItem(LABEL_EXIT)};

    private final String LABEL_GUARD = "Guard";
    private final String LABEL_HIDE = "Hide";

    CheckboxMenuItem mCmiGuard = new CheckboxMenuItem(LABEL_GUARD);
    CheckboxMenuItem mCmiHide = new CheckboxMenuItem(LABEL_HIDE);

    // Alternative approach:
    CheckboxMenuItem safety[] = {mCmiGuard, mCmiHide};

    // A second menu bar to swap to:
    MenuBar fooMenuBar = new MenuBar();

    Menu fooMenu = new Menu("fooBar");

    MenuItem fooBarMenuItems[] = {
            new MenuItem("Foo"),
            new MenuItem("Bar"),
            new MenuItem("Baz"),
    };

    Button btnSwapMenu = new Button("Swap Menus");

    public MenuDemo() {
        /*
         *BorderLayout is the default layout manager
         * for the content pane. It implements a
         * layout style that defines five locations
         * to which a component can be added. The
         * first is the center. The other four are
         * the sides (i.e., borders), which are called
         * north, south, east, and west. By default,
         * when you add a component to the content pane,
         * you are adding the component to the center.
         * To add a component to one of the other regions,
         * specify its name.
         *
         * setLayout();
         */

        prepareSafetyMenu();
        prepareFileMenu();
        prepareFlavorMenu();
        addFileAndFlavorMenus2MenuBar1();

        //Set the menu bar(on top of the window).
        setMenuBar(menuBar1);

        /*
         *To add a component to one of the other regions,
         *specify its name, such as "North".
         *
         * Set up the system for swapping menus:
         */
        add("North", btnSwapMenu);

        /*
            By default,
         * when you add a component to the content pane,
         * you are adding the component to the center.
         *
         * Center component will fill all space.
         */
        add(textField);
        textField.setEditable(false);

        prepareFooMenuBar();
    }

    private void prepareFooMenuBar() {
        for (int i = 0; i < fooBarMenuItems.length; i++)
            fooMenu.add(fooBarMenuItems[i]);
        fooMenuBar.add(fooMenu);
    }

    private void addFileAndFlavorMenus2MenuBar1() {
        menuBar1.add(menuFile);
        menuBar1.add(menuFlavors);
    }

    private void prepareFileMenu() {
        menuFile.add(menuSafety);
        for (int i = 0; i < fileMenuItems.length; i++)
            menuFile.add(fileMenuItems[i]);
    }

    private void prepareSafetyMenu() {
        for (int i = 0; i < safety.length; i++)
            menuSafety.add(safety[i]);
    }

    private void prepareFlavorMenu() {
        for (int i = 0; i < flavors.length; i++) {
            menuFlavors.add(new MenuItem(flavors[i]));
            // Add separators at intervals:
            if ((i + 1) % 3 == 0) menuFlavors.addSeparator();
        }
    }

    public boolean handleEvent(Event evt) {
        if (evt.id == Event.WINDOW_DESTROY) {
            System.exit(0);
        } else {
            return super.handleEvent(evt);
        }
        //此写法甚好，若上加else if，代码结构不需改。
        return true;
    }

    /**
     * The worst problem for AWT is every action would be
     * handled in action method. This would cause a lot
     * of "if else" statements.
     * @param evt the event to handle
     * @param arg the object acted on
     * @return
     */
    public boolean action(Event evt, Object arg) {
        if (evt.target.equals(btnSwapMenu)) {
            swapMenu();
        } else if (evt.target instanceof CheckboxMenuItem) {
            switch (((CheckboxMenuItem) evt.target).getLabel()) {
                case LABEL_GUARD:
                    textField.setText("guard: " + mCmiGuard.getState());
                    break;
                case LABEL_HIDE:
                    textField.setText("Hide :" + mCmiHide.getState());
                    break;
            }
        } else if (evt.target instanceof MenuItem) {
            switch ((String) arg) {
                case LABEL_OPEN:
                    open();
                    break;
                case LABEL_EXIT:
                    System.exit(0);
                    break;
                default:
                    textField.setText(arg.toString());
                    break;
            }
        } else {
            return super.action(evt, arg);
        }
        return true;
    }

    private void open() {
        String s = textField.getText();
        boolean chosen = false;

        for (int i = 0; i < flavors.length; i++) {
            if (s.equals(flavors[i])) {
                chosen = true;
                break;
            }
        }

        if (!chosen) textField.setText("Choose a flavor first!");
        else textField.setText("Opening " + s + ". Mmm, mm!");
    }

    private void swapMenu() {
        MenuBar m = getMenuBar();
        //此写法较清晰，比三目运算写法好理解，再加个MenuBar，代码结构也无需改。
        if (m == menuBar1) {
            setMenuBar(fooMenuBar);
        } else if (m == fooMenuBar) {
            setMenuBar(menuBar1);
        }
    }

    public static void main(String args[]) {
        MenuDemo f = new MenuDemo();
        f.resize(300, 200);
        f.show();//== f.setVisible(true);
    }


    /*
    the following description comes from "Thinking In Java".

    In this program I avoided the typical long lists of add( ) calls
    for each menu, because that seemed like a lot of unnecessary typing.
    Instead I placed the menu items into arrays, and then simply stepped
    through each array calling add( ) in a for loop. This also means that
    adding or subtracting a menu item is less tedious(烦).

As an alternative approach (which I find less desirable since
it requires more typing) the CheckboxMenuItems are created
in an array of handles safety; this is true for the arrays
file and other as well.

This program creates not one but two MenuBars to demonstrate
that menu bars can be actively swapped while the program is
running. You can see how a MenuBar is made up of Menus, and
each Menu is itself made up of MenuItems, CheckboxMenuItems,
or even other Menus (which produce submenus). When a MenuBar
is assembled it can be installed into the current program with
the setMenuBar( ) method. Note that when the button is pressed,
it checks to see which menu is currently installed using getMenuBar( ),
then puts the other menu bar in it’s place. The rest of action( )
deals with the various menu items, testing for “Open,” “Guard” or
 “Hide” (note spelling and capitalization is critical and it is
 not checked, so this is a clear source of programming errors),
 and otherwise sending the string to the TextField.
 The checking and un-checking of the menu items is taken care of
 automatically, and the getState( ) method reveals the state.

   You might think that one menu could reasonably reside on
   more than one menu bar. This does seem to make sense
   because all you’re passing to the MenuBar add( ) method
   is a handle. However, if you try this the behavior will
   be strange, and not what you expect (it’s difficult to know
   if this is a bug or if they intended it to work this way).

This example also shows what you need to do to create an application
instead of an applet (again, because an application can support
menus and an applet cannot). Instead of inheriting from Applet,
you inherit from Frame. Instead of init( ) to set things up,
you make a constructor for your class.

Finally, you create a main() and in that you build an object
of your new type, resize it and then call show( ). It’s only
different from an applet in a few small places, but it’s now
a standalone windowed application and you’ve got menus

     */
}