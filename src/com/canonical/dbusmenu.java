package com.canonical;

import java.util.List;
import org.freedesktop.dbus.DBusInterface;
import org.freedesktop.dbus.DBusSignal;
import org.freedesktop.dbus.UInt32;
import org.freedesktop.dbus.Variant;
import org.freedesktop.dbus.exceptions.DBusException;

public interface dbusmenu extends DBusInterface {
	public static class ItemsPropertiesUpdated extends DBusSignal {
		public final List<Struct4> updatedProps;
		public final List<Struct5> removedProps;

		public ItemsPropertiesUpdated(String path, List<Struct4> updatedProps,
				List<Struct5> removedProps) throws DBusException {
			super(path, updatedProps, removedProps);
			this.updatedProps = updatedProps;
			this.removedProps = removedProps;
		}
	}

	public static class LayoutUpdated extends DBusSignal {

		public final UInt32 revision;
		public final int parent;

		public LayoutUpdated(String path, UInt32 revision, int parent)
				throws DBusException {
			super(path, revision, parent);

			this.revision = revision;
			this.parent = parent;
		}
	}

	public static class ItemActivationRequested extends DBusSignal {
		public final int id;
		public final UInt32 timestamp;

		public ItemActivationRequested(String path, int id, UInt32 timestamp)
				throws DBusException {
			super(path, id, timestamp);
			this.id = id;
			this.timestamp = timestamp;
		}
	}

	public Pair<UInt32, Struct1> GetLayout(int parentId, int recursionDepth,
			List<String> propertyNames);

	public List<Struct2> GetGroupProperties(List<Integer> ids,
			List<String> propertyNames);

	public Variant GetProperty(int id, String name);

	public void Event(int id, String eventId, Variant data, UInt32 timestamp);

	public List<Integer> EventGroup(List<Struct3> events);

	public boolean AboutToShow(int id);

	public Pair<List<Integer>, List<Integer>> AboutToShowGroup(List<Integer> ids);

}
