package com.ramussoftware.dbusmenu;

/**
 * Class to assess to any menu item
 * 
 * @author Vitaliy Yakovchuk
 * 
 */

public interface MenuHolder {

	/**
	 * Finds menu or item by its id. Root menu always has 0 - id
	 */

	Menu find(int menuId);

}
