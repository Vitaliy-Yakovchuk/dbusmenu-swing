package com.ramussoftware.dbusmenu;

import java.util.List;

/**
 * Interface for menus and menu items
 * 
 * @author Vitaliy Yakovchuk
 * 
 */

public interface Menu {

	/**
	 * Menu or menu item id, should be unique. Root menu always has 0 - id.
	 */

	int getId();

	boolean isSeparator();

	/**
	 * -# two consecutive underscore characters "__" are displayed as a single
	 * underscore, -# any remaining underscore characters are not displayed at
	 * all, -# the first of those remaining underscore characters (unless it is
	 * the last character in the string) indicates that the following character
	 * is the access key.
	 */

	String getLabel();

	boolean isEnabled();

	boolean isVisible();

	/**
	 * 
	 * Icon name of the item, following the freedesktop.org icon spec.
	 * Deprecated as it is better to use getIconData instead
	 */

	@Deprecated
	String getIconName();

	/**
	 * PNG data of the icon.
	 */

	byte[] getIconData();

	/**
	 * The shortcut of the item. Each array represents the key press in the list
	 * of keypresses. Each list of strings contains a list of modifiers and then
	 * the key that is used. The modifier strings allowed are: "Control", "Alt",
	 * "Shift" and "Super".
	 * 
	 * - A simple shortcut like Ctrl+S is represented as: [["Control", "S"]] - A
	 * complex shortcut like Ctrl+Q, Alt+X is represented as: [["Control", "Q"],
	 * ["Alt", "X"]]
	 * 
	 */

	String[] getShortcut();

	/**
	 * If the item can be toggled, this property should be set to: -
	 * "checkmark": Item is an independent togglable item - "radio": Item is
	 * part of a group where only one item can be toggled at a time - "": Item
	 * cannot be toggled
	 * 
	 */

	String getToggleType();

	/**
	 * Describe the current state of a "togglable" item. Can be one of: - 0 =
	 * off - 1 = on - anything else = indeterminate
	 * 
	 * Note: The implementation does not itself handle ensuring that only one
	 * item in a radio group is set to "on", or that a group does not have "on"
	 * and "indeterminate" items simultaneously; maintaining this policy is up
	 * to the toolkit wrappers.
	 */
	int getToggleState();

	/**
	 * List of menu children. Can return <code>null</code> or empty list is
	 * there are no children
	 */
	List<Menu> getChildren();

	void onEvent();
}
