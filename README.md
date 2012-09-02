dbusmenu-swing
==============

Unity global menu integration for Java swing applications.

This library create a dbus connection to Unity global menu. It was tested on gentoo system with Window Menubar plasmoid.
But it does not work properly on Ubuntu :).

If you are looking well working ubuntu library take a look at Java Swing Ayatana project:

http://code.google.com/p/java-swing-ayatana/

Java Swing Ayatana was not working properly on my gentoo system+KDE, that is why I created dbusmenu-swing library.

Usage
==============
Install native unix socket library in your system. Install libmatthew-java package or put libunix-java.so to /usr/lib.
You can copy it from lib directory or get here
https://launchpad.net/ubuntu/+source/libmatthew-java


Add dbusmenu.jar to Java project CLASSPATH, and call 
com.ramussoftware.dbusmenu.DBusMenuBuilder.builFromJMenuBar(your_jframe).