dbusmenu-swing
==============

Unity global menu integration for Java swing applications.

This library create a dbus connection to Unity global menu. It was tested on gentoo system with Window Menubar plasmoid.

Usage
==============
Install native unix socket library in your system. Install libmatthew-java package or put libunix-java.so to /usr/lib.
You can copy it from lib directory or get here
https://launchpad.net/ubuntu/+source/libmatthew-java


 Add dbusmenu.jar to Java project CLASSPATH, and call 
com.ramussoftware.dbusmenu.DBusMenuBuilder.builFromJMenuBar(your_jframe).
