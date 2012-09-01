package com.canonical;
import java.util.List;
import java.util.Map;
import org.freedesktop.dbus.Position;
import org.freedesktop.dbus.Struct;
import org.freedesktop.dbus.Variant;
public final class Struct1 extends Struct
{
   @Position(0)
   public final int a;
   @Position(1)
   public final Map<String,Variant> b;
   @Position(2)
   public final List<Variant> c;
  public Struct1(int a, Map<String,Variant> b, List<Variant> c)
  {
   this.a = a;
   this.b = b;
   this.c = c;
  }
}
