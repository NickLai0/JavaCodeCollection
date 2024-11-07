package code.java.clazz;
/*
From Thinking in Java.

what is a Bean?

After the dust settles（尘埃落定）, then,
a component is really just a block of code,
typically embodied in a class.
The key issue is the ability for the application builder tool
to discover the properties and events for that
component. To create a VB component, the programmer had to
write a fairly complicated piece of
code, following certain conventions（协定）
to expose the properties and events. Delphi was a second generation
visual programming tool and the language was actively designed around visual
programming so it is much easier to create a visual component.

However, Java has brought the
creation of visual components to its
most advanced state with JavaBeans,
because a Bean is just a class.
You don’t have to write any extra code
or use special language extensions in order to make
something a Bean. The only thing you need to do, in fact,
is slightly modify the way you name your
methods. It is the method name that tells the application
builder tool whether this is a property, an
event or just an ordinary method.

In the Java documentation, this naming convention
is mistakenly termed a “design pattern.” This is
unfortunate since design patterns (see Chapter 16)
are challenging enough without this sort of
confusion. It’s not a design pattern,
it’s just a naming convention and it’s fairly simple:
        1. For a property named xxx, you typically
        create two methods: getXxx( ) and setXxx( ).
Note that the first letter after get or set is
automatically decapitalized to produce the
property name. The type produced by the “get”
function is the same as the type of the
argument to the “set” function. The name of
the property and the type for the “get” and
“set” are not related.

2. For a boolean property, you can use
the “get” and “set” approach above, but
you may also
use “is” instead of “get.”

  3. Ordinary methods of the Bean don’t
conform to the above naming convention, but they’re
public.

4. For events, you use the “listener” approach.
It’s exactly the same as you’ve been seeing:

addFooBarListener(FooBarListener)
and removeFooBarListener(FooBarListener) to
handle a FooBarEvent.

 Most of the time the built-in events and
 listeners will satisfy your
needs, but you can also create your own events
and listener interfaces.

        Point 1 above answers a question about
        something you may have noticed in the
        change from Java 1.0

to Java 1.1: a number of method names have had small,
apparently meaningless name changes. Now
you can notice that most of those changes had
to do with adapting to the “get” and “set” naming
convention, in order to make that particular
component into a Bean.

We can use these guidelines to create a very simple Bean:

*/
//: Frog.java
// A trivial Java bean
        import java.awt.*;
        import java.awt.event.*;
class Spots {}
public class Frog {
    private int jumps;
    private Color color;
    private Spots spots;
    private boolean jmpr;
    public int getJumps() { return jumps; }
    public void setJumps(int newJumps) {
        jumps = newJumps;
    }
    public Color getColor() { return color; }
    public void setColor(Color newColor) {
        color = newColor;
    }
    public Spots getSpots() { return spots; }
    public void setSpots(Spots newSpots) {
        spots = newSpots;
    }
    public boolean isJumper() { return jmpr; }
    public void setJumper(boolean j) { jmpr = j; }
    public void addActionListener( ActionListener l) {
        //...
    }
    public void removeActionListener(  ActionListener l) {
        // ...
    }
    public void addKeyListener(KeyListener l) {
        // ...
    }
    public void removeKeyListener(KeyListener l) {
        // ...
    }
    // An "ordinary" public method:
    public void croak() {
        System.out.println("Ribbet!");
    }
}

/*
First, you can see that it’s just a class.
Normally, all your fields will be private,
and only accessible
through methods. Following the naming convention,
the properties are jumps, color, spots and

jumper (note the change in case of the first
letter in the property name). Although the name of the
internal identifier is the same as the name
of the property in the first three cases,
in Frog you can
see that the property name does not force
you to use any particular name for internal variables (or
The events this Bean handles are ActionEvent and KeyEvent,
based on the naming of the “add” and
“remove” methods for the associated listener.
Finally you can see the ordinary method croak( ) is still
part of the Bean simply because it’s a public method,
not because it conforms（遵守） to any naming sc
*/