package com.canonical.AppMenu;
import com.trolltech.QtDBus.QtTypeName.Out0;
import java.util.List;
import org.freedesktop.dbus.DBusInterface;
import org.freedesktop.dbus.DBusSignal;
import org.freedesktop.dbus.UInt32;
import org.freedesktop.dbus.exceptions.DBusException;
public interface Registrar extends DBusInterface
{
   public static class WindowRegistered extends DBusSignal
   {
      public final UInt32 windowId;
      public final String service;
      public final DBusInterface menuObjectPath;
      public WindowRegistered(String path, UInt32 windowId, String service, DBusInterface menuObjectPath) throws DBusException
      {
         super(path, windowId, service, menuObjectPath);
         this.windowId = windowId;
         this.service = service;
         this.menuObjectPath = menuObjectPath;
      }
   }
   public static class WindowUnregistered extends DBusSignal
   {
      public final UInt32 windowId;
      public WindowUnregistered(String path, UInt32 windowId) throws DBusException
      {
         super(path, windowId);
         this.windowId = windowId;
      }
   }

  public void RegisterWindow(UInt32 windowId, DBusInterface menuObjectPath);
  public void UnregisterWindow(UInt32 windowId);
  public Pair<String, DBusInterface> GetMenuForWindow(UInt32 windowId);
  @Out0("MenuInfoList")
  public List<Struct1> GetMenus();

}
