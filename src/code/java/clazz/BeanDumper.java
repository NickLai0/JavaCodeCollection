package code.java.clazz;
/*extracting BeanInfo with the Introspector

One of the most critical parts of the Bean scheme
occurs when you drag a Bean off a palette（调色板） and plop（丢）
it down on a form. The application builder tool must be able
to create the Bean (which it can do if
there’s a default constructor) and then, without access to
the Bean’s source code, extract all the
necessary information to create the property
sheet and event handlers.
Part of the solution is already evident from the end of Chapter 11:
Java 1.1 reflection allows all the
methods of an anonymous class to be discovered.
This is perfect for solving the Bean problem without
requiring you to use any extra language keywords
like those required in other visual programming
languages. In fact, one of the prime reasons reflection
was added to Java 1.1 was to support Beans (as
well as object serialization and remote method invocation).
So you might expect that the creator of
the application builder tool would have to reflect each
Bean and hunt through its methods to find the
properties and events for that Bean.
This is certainly possible, but the Java designers
wanted to provide a standard interface for everyone to
use, not only to make Beans simpler to use but also
to provide a standard gateway to the creation of
more complex Beans. This interface is the Introspector class,
and the most important method in this
class is the static getBeanInfo( ).
You pass a Class handle to this method, and it fully interrogates（查询） that
class and returns a BeanInfo object that you can then dissect（解剖）
to find properties, methods and events.
Normally you won’t care about any of this – you’ll probably
get most of your Beans off the shelf from
vendors and you don’t need to know all the magic
that’s going on underneath. You’ll simply drag your
beans onto your form, then configure their
properties and write handlers for the events you’re
interested in. However, it’s an interesting and educational
exercise to use the Introspector to display
information about a Bean, so here’s a tool that does it:
*/
//: BeanDumper.java
// A method to introspect a bean

import java.beans.*;
import java.lang.reflect.*;

public class BeanDumper {
    public static void dump(Class bean) {
        BeanInfo bi = null;
        try {
            bi = Introspector.getBeanInfo(
                    bean, java.lang.Object.class);
        } catch (IntrospectionException ex) {
            //introspect:反省，于此该理解为反释
            System.out.println("Couldn't introspect " +
                    bean.getName());
            System.exit(1);
        }
        PropertyDescriptor properties[] =
                bi.getPropertyDescriptors();
        for (int i = 0; i < properties.length; i++) {
            Class p = properties[i].getPropertyType();
            System.out.println(
                    "Property type:\n " + p.getName());
            System.out.println(
                    "Property name:\n " +
                            properties[i].getName());
            Method readMethod =
                    properties[i].getReadMethod();
            if (readMethod != null)
                System.out.println(
                        "Read method:\n " +
                                readMethod.toString());
            Method writeMethod =
                    properties[i].getWriteMethod();
            if (writeMethod != null)
                System.out.println(
                        "Write method:\n " +
                                writeMethod.toString());
            System.out.println("====================");
        }
        System.out.println("Public methods:");
        MethodDescriptor methods[] =
                bi.getMethodDescriptors();
        for (int i = 0; i < methods.length; i++)
            System.out.println(
                    methods[i].getMethod().toString());
        System.out.println("======================");
        System.out.println("Event support:");
        EventSetDescriptor events[] =
                bi.getEventSetDescriptors();
        for (int i = 0; i < events.length; i++) {
            System.out.println("Listener type:\n " +
                    events[i].getListenerType().getName());
            Method lm[] =
                    events[i].getListenerMethods();
            for (int j = 0; j < lm.length; j++)
                System.out.println(
                        "Listener method:\n " +
                                lm[j].getName());
            MethodDescriptor lmd[] =
                    events[i].getListenerMethodDescriptors();
            for (int j = 0; j < lmd.length; j++)
                System.out.println(
                        "Method descriptor:\n " +
                                lmd[j].getMethod().toString());
            Method addListener =
                    events[i].getAddListenerMethod();
            System.out.println(
                    "Add Listener Method:\n " +
                            addListener.toString());
            Method removeListener =

                    events[i].getRemoveListenerMethod();
            System.out.println(
                    "Remove Listener Method:\n " +
                            removeListener.toString());
            System.out.println("====================");
        }
    }

    // Dump the class of your choice:
    public static void main(String args[]) {
        /*if(args.length < 1) {
            System.err.println("usage: \n" +
                    "BeanDumper fully.qualified.class");
            System.exit(0);
        }
        Class c = null;
        try {
            c = Class.forName(args[0]);
        } catch(ClassNotFoundException ex) {
            System.err.println(
                    "Couldn't find " + args[0]);
            System.exit(0);
        }
        dump(c);
        */
        //上面用命令行来操作不好演示，我改为下面的代码，用以演示。
        dump(BeanDumper.class);
    }
}

/*
BeanDumper.dump() is the method that does all the work.
First it tries to create a BeanInfo object,
and if successful calls the methods of BeanInfo
that produce information about properties, methods
and events. In Introspector.getBeanInfo( ),
you’ll see there is a second argument. This tells the
Introspector where to stop in the inheritance hierarchy.
Here, it stops before it parses all the methods
from Object, since we’re not interested in seeing those.
For properties, getPropertyDescriptors( )
returns an array of PropertyDescriptors. For each

PropertyDescriptor you can call getPropertyType( )
 to find the class of object that is passed in and
out via the property methods. Then for each property
 you can get its pseudonym (extracted from the
method names) with getName( ), the method
for reading with getReadMethod( ) and the method
for writing with getWriteMethod( ).
These last two methods return a Method object which can
actually be used to invoke the corresponding
method on the object (this is part of reflection).
For the public methods (including the property methods),
getMethodDescriptors( ) returns an array
of MethodDescriptors. For each one you can get
the associated Method object and print out its name.
For the events, getEventSetDescriptors( ) returns
an array of (what else) EventSetDescriptors. Each
of these can be queried to find out the class of
the listener, the methods of that listener class, and the
add- and remove-listener methods. The BeanDumper
program prints out all of this information.


* */