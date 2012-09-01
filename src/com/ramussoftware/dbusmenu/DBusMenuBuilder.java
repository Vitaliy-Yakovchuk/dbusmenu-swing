package com.ramussoftware.dbusmenu;

import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.peer.ComponentPeer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

import org.freedesktop.dbus.DBusConnection;
import org.freedesktop.dbus.UInt32;
import org.freedesktop.dbus.Variant;
import org.freedesktop.dbus.exceptions.DBusException;

import com.canonical.Struct2;
import com.canonical.Struct3;
import com.canonical.dbusmenu;
import com.canonical.AppMenu.Registrar;

public class DBusMenuBuilder {

	protected MenuHolder menuHolder;

	public DBusMenuBuilder(MenuHolder menuHolder) {
		this.menuHolder = menuHolder;
	}

	/**
	 * 
	 * Shows the menu trough the dbus. Can throw an exception if menu can not be
	 * shown
	 * 
	 * @param peer
	 *            Frame peer must be XWindow peer with method getWindow that
	 *            returns X window id
	 */
	public void show(ComponentPeer peer) {
		long id = getWindowId(peer);
		if (id < 0)
			throw new RuntimeException("Cannot get X window id");

		connect(id);
	}

	@SuppressWarnings("rawtypes")
	protected void connect(long id) {
		try {
			DBusConnection conn = DBusConnection
					.getConnection(DBusConnection.SESSION);
			Registrar registrar = (Registrar) conn.getRemoteObject(
					"com.canonical.AppMenu.Registrar",
					"/com/canonical/AppMenu/Registrar", Registrar.class);

			UInt32 uInt32 = new UInt32(id);

			dbusmenu menu;

			conn.exportObject("/com/canonical/dbusmenu", menu = new dbusmenu() {

				@Override
				public boolean isRemote() {
					return false;
				}

				@Override
				public com.canonical.Pair<UInt32, com.canonical.Struct1> GetLayout(
						int parentId, int recursionDepth,
						List<String> propertyNames) {
					Menu menu = menuHolder.find(parentId);

					HashMap<String, Variant> map = new HashMap<String, Variant>();
					ArrayList<Variant> c = new ArrayList<Variant>();
					com.canonical.Struct1 s1 = new com.canonical.Struct1(menu
							.getId(), map, c);

					List<Menu> children = menu.getChildren();
					if (children != null) {
						for (Menu sm : children) {

							c.add(new Variant<com.canonical.Struct1>(
									AddMenuItem(parentId, recursionDepth,
											propertyNames, sm)));
						}

						if (children.size() > 0)

							map.put("children-display", new Variant<String>(
									"submenu"));
					}

					fillMap(menu, map);

					com.canonical.Pair<UInt32, com.canonical.Struct1> p = new com.canonical.Pair<UInt32, com.canonical.Struct1>(
							new UInt32(parentId), s1);

					return p;

				}

				@SuppressWarnings("unchecked")
				private void fillMap(Menu menu, HashMap<String, Variant> map) {
					if (menu.isSeparator()) {
						map.put("type", new Variant<String>("separator"));
						return;
					} else
						map.put("type", new Variant<String>("standard"));
					map.put("label", new Variant<String>(menu.getLabel()));
					map.put("visible", new Variant<Boolean>(menu.isVisible()));
					map.put("enabled", new Variant<Boolean>(menu.isEnabled()));
					String[] shortcuts = menu.getShortcut();
					if (shortcuts != null && shortcuts.length > 0) {
						map.put("shortcut", new Variant(
								new String[][] { shortcuts }));
					}

					String toggleType = menu.getToggleType();
					if (toggleType != null && toggleType.length() > 0) {
						map.put("toggle-type", new Variant<String>(toggleType));

						map.put("toggle-state",
								new Variant<Integer>(menu.getToggleState()));
					}

					byte[] bs = menu.getIconData();
					if (bs != null && bs.length > 0)
						map.put("icon-data", new Variant<byte[]>(bs));

				}

				private com.canonical.Struct1 AddMenuItem(int parentId,
						int recursionDepth, List<String> propertyNames,
						Menu submenu) {
					HashMap<String, Variant> map = new HashMap<String, Variant>();
					ArrayList<Variant> c = new ArrayList<Variant>();
					com.canonical.Struct1 s1 = new com.canonical.Struct1(
							submenu.getId(), map, c);
					List<Menu> children = submenu.getChildren();
					if (children != null && children.size() > 0){

						map.put("children-display", new Variant<String>(
								"submenu"));
						for(Menu child:children)
							c.add(new Variant<com.canonical.Struct1>(AddMenuItem(submenu.getId(), recursionDepth, propertyNames, child)));
					}

					fillMap(submenu, map);
					return s1;
				}

				@SuppressWarnings("unchecked")
				@Override
				public List<Struct2> GetGroupProperties(List<Integer> ids,
						List<String> propertyNames) {
					List<Struct2> res = new ArrayList<Struct2>();
					for(Integer i:ids){
						Menu menu= menuHolder.find(i);
						if(menu==null)
							return null;
						HashMap map =new HashMap();
						fillMap(menu, map);
						Struct2 struct2 = new Struct2(i, map);
						res.add(struct2);
					}
					return res;
				}

				@SuppressWarnings("unchecked")
				@Override
				public Variant GetProperty(int id, String name) {
					Menu menu = menuHolder.find(id);
					if(menu!=null){
						HashMap map =new HashMap();
						fillMap(menu, map);
						return (Variant)map.get(name);
					}
					return null;
				}

				@Override
				public void Event(int id, String eventId, Variant data,
						UInt32 timestamp) {
					if ("clicked".endsWith(eventId)) {
						Menu menu = menuHolder.find(id);
						menu.onEvent();
					}
				}

				@Override
				public List<Integer> EventGroup(List<Struct3> events) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public boolean AboutToShow(int id) {
					// TODO Auto-generated method stub
					return true;
				}

				@Override
				public com.canonical.Pair<List<Integer>, List<Integer>> AboutToShowGroup(
						List<Integer> ids) {
					// TODO Auto-generated method stub
					return null;
				}

			});

			registrar.RegisterWindow(uInt32, menu);

		} catch (DBusException De) {
			De.printStackTrace();
			System.exit(1);
		}

	}

	/**
	 * 
	 * Shows the menu trough the dbus. Can throw an exception if menu can not be
	 * shown
	 * 
	 * @param window
	 * 
	 */
	@SuppressWarnings("deprecation")
	public void show(Window window) {
		show(window.getPeer());
	}

	/**
	 * Current implementation using restricted apy
	 */
	@Deprecated
	protected long getWindowId(ComponentPeer peer) {
		try {
			Method m = peer.getClass().getMethod("getWindow");
			return (Long) m.invoke(peer);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return -1l;
	}

	/**
	 * This method do not hide menu bar just create dbus menu copy
	 */
	public static boolean builFromJMenuBar(final JFrame frame) {
		try {
			final JMenuBar jMenuBar = frame.getJMenuBar();
			SwingMenuHolder holder = new SwingMenuHolder(jMenuBar,
					frame.getTitle());

			final DBusMenuBuilder builder = new DBusMenuBuilder(holder);

			boolean s;

			if (frame.isVisible()) {

				builder.show(frame);

				s = false;
				jMenuBar.setVisible(false);
			} else {
				s = true;
			}

			final boolean show = s;

			frame.addWindowListener(new WindowAdapter() {
				@Override
				public void windowOpened(WindowEvent e) {
					if (show) {
						builder.show(frame);
						jMenuBar.setVisible(false);
					}
				}
			});

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
