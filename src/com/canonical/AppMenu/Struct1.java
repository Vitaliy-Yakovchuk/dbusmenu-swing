package com.canonical.AppMenu;
import org.freedesktop.dbus.DBusInterface;
import org.freedesktop.dbus.Position;
import org.freedesktop.dbus.Struct;
import org.freedesktop.dbus.UInt32;
public final class Struct1 extends Struct
{
   @Position(0)
   public final UInt32 a;
   @Position(1)
   public final String b;
   @Position(2)
   public final DBusInterface c;
  public Struct1(UInt32 a, String b, DBusInterface c)
  {
   this.a = a;
   this.b = b;
   this.c = c;
  }
}
