package code.java.view.menu;

import java.awt.*;

//This demo from Thinking in Java.
//I modify a little.
public class Menu1 extends Frame {
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

    TextField t = new TextField("No flavor", 30);

    MenuBar mb1 = new MenuBar();

    Menu menuFile = new Menu("File");
    Menu menuSafety = new Menu("Safety");
    Menu menuFlavors = new Menu("Flavors");

    //Menu Item belongs to a specific menu.
    MenuItem file[] = {
            new MenuItem("Open"),
            new MenuItem("Exit")
    };

    // Alternative approach:
    CheckboxMenuItem safety[] = {
            new CheckboxMenuItem("Guard"),
            new CheckboxMenuItem("Hide")
    };

    // A second menu bar to swap to:
    MenuBar mb2 = new MenuBar();

    Menu fooBar = new Menu("fooBar");

    MenuItem other[] = {
            new MenuItem("Foo"),
            new MenuItem("Bar"),
            new MenuItem("Baz"),
    };

    Button b = new Button("Swap Menus");

    public Menu1() {
        for (int i = 0; i < flavors.length; i++) {
            menuFlavors.add(new MenuItem(flavors[i]));
            // Add separators at intervals:
            if ((i + 1) % 3 == 0)
                menuFlavors.addSeparator();
        }
        for (int i = 0; i < safety.length; i++)
            menuSafety.add(safety[i]);
        menuFile.add(menuSafety);
        for (int i = 0; i < file.length; i++)
            menuFile.add(file[i]);
        mb1.add(menuFile);
        mb1.add(menuFlavors);
        setMenuBar(mb1);
        t.setEditable(false);
        add("Center", t);
        // Set up the system for swapping menus:
        add("North", b);
        for (int i = 0; i < other.length; i++)
            fooBar.add(other[i]);
        mb2.add(fooBar);
    }

    public boolean handleEvent(Event evt) {
        if (evt.id == Event.WINDOW_DESTROY)
            System.exit(0);
        else
            return super.handleEvent(evt);
        return true;
    }

    public boolean action(Event evt, Object arg) {
        if (evt.target.equals(b)) {
            MenuBar m = getMenuBar();
            if (m == mb1) setMenuBar(mb2);
            else if (m == mb2) setMenuBar(mb1);
        } else if (evt.target instanceof MenuItem) {
            if (arg.equals("Open")) {
                String s = t.getText();
                boolean chosen = false;
                for (int i = 0; i < flavors.length; i++)
                    if (s.equals(flavors[i])) chosen = true;
                if (!chosen)
                    t.setText("Choose a flavor first!");
                else
                    t.setText("Opening " + s + ". Mmm, mm!");
            } else if (arg.equals("Guard"))
                t.setText("Guard the Ice Cream! " +
                        "Guarding is " + safety[0].getState());
            else if (arg.equals("Hide"))
                t.setText("Hide the Ice Cream! " +
                        "Is it cold? " + safety[1].getState());
                // You can also use the other way of
                // matching to a string:
            else if (evt.target.equals(file[1]))
                System.exit(0);
            else
                t.setText(arg.toString());
        } else
            return super.action(evt, arg);
        return true;
    }

    public static void main(String args[]) {
        Menu1 f = new Menu1();
        f.resize(300, 200);
        f.show();
    }
}