package com.ramussoftware.dbusmenu;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenuItem;

public class SwingRootMenu implements Menu {

	private final JMenuItem[] menuItems;

	private final String name;

	private SwingMenuHolder holder;

	public SwingRootMenu(JMenuItem[] menuItems, String name,
			SwingMenuHolder holder) {
		this.menuItems = menuItems;
		this.name = name;
		this.holder = holder;
	}

	@Override
	public int getId() {
		return 0;
	}

	@Override
	public boolean isSeparator() {
		return menuItems == null;
	}

	@Override
	public String getLabel() {
		return name;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean isVisible() {
		return true;
	}

	@Override
	public String getIconName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getIconData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getShortcut() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getToggleType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getToggleState() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Menu> getChildren() {
		List<Menu> res = new ArrayList<Menu>(menuItems.length);
		for (JMenuItem item : menuItems)
			res.add(new SwingMenu(item, holder));
		return res;
	}

	@Override
	public void onEvent() {
		// TODO Auto-generated method stub

	}

}
