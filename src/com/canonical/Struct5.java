package com.canonical;
import java.util.List;
import org.freedesktop.dbus.Position;
import org.freedesktop.dbus.Struct;
public final class Struct5 extends Struct
{
   @Position(0)
   public final int a;
   @Position(1)
   public final List<String> b;
  public Struct5(int a, List<String> b)
  {
   this.a = a;
   this.b = b;
  }
}
