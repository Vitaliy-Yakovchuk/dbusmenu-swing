package com.canonical;
import java.util.Map;
import org.freedesktop.dbus.Position;
import org.freedesktop.dbus.Struct;
import org.freedesktop.dbus.Variant;
public final class Struct2 extends Struct
{
   @Position(0)
   public final int a;
   @Position(1)
   public final Map<String,Variant> b;
  public Struct2(int a, Map<String,Variant> b)
  {
   this.a = a;
   this.b = b;
  }
}
