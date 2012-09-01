package com.canonical;
import org.freedesktop.dbus.Position;
import org.freedesktop.dbus.Struct;
import org.freedesktop.dbus.UInt32;
import org.freedesktop.dbus.Variant;
public final class Struct3 extends Struct
{
   @Position(0)
   public final int a;
   @Position(1)
   public final String b;
   @Position(2)
   public final Variant c;
   @Position(3)
   public final UInt32 d;
  public Struct3(int a, String b, Variant c, UInt32 d)
  {
   this.a = a;
   this.b = b;
   this.c = c;
   this.d = d;
  }
}
