package com.ramussoftware.dbusmenu;

import java.util.List;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class SwingMenuHolder implements MenuHolder {

	private Menu root;

	public SwingMenuHolder(JMenuBar bar, String menuName) {
		int mc = bar.getMenuCount();
		JMenuItem[] items = new JMenuItem[mc];
		for (int i = 0; i < mc; i++)
			items[i] = bar.getMenu(i);

		root = new SwingRootMenu(items, menuName, this);
	}

	@Override
	public Menu find(int menuId) {
		// can be optimized for specific task
		if (menuId == 0)
			return root;

		return find(root, menuId);
	}

	private Menu find(Menu menu, int menuId) {
		List<Menu> children = menu.getChildren();
		if (children != null)
			for (Menu m : children) {
				if (m.getId() == menuId)
					return m;
				Menu menu2 = find(m, menuId);
				if (menu2 != null)
					return menu2;
			}
		return null;
	}

	public int getId(JMenuItem menuItem) {
		return System.identityHashCode(menuItem);
	}

}
