package com.ramussoftware.dbusmenu;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

public class SwingMenu implements Menu {

	private static com.ramussoftware.dbusmenu.SwingMenu.VKCollection vks;
	private final JMenuItem menuItem;
	private final int id;
	private SwingMenuHolder holder;

	public SwingMenu(JMenuItem menuItem, SwingMenuHolder holder) {
		this.menuItem = menuItem;
		this.id = holder.getId(menuItem);
		this.holder = holder;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public boolean isSeparator() {
		return menuItem == null;
	}

	@Override
	public String getLabel() {
		return menuItem.getText();
	}

	@Override
	public boolean isEnabled() {
		return menuItem.isEnabled();
	}

	@Override
	public boolean isVisible() {
		return menuItem.isVisible();
	}

	@Override
	public String getIconName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getIconData() {
		Icon icon = menuItem.getIcon();
		if (icon != null) {

			int width = icon.getIconWidth();
			int height = icon.getIconHeight();

			BufferedImage bufferedImage = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_ARGB);

			Graphics2D g2d = bufferedImage.createGraphics();
			icon.paintIcon(menuItem, g2d, 0, 0);

			g2d.dispose();

			ByteArrayOutputStream output = new ByteArrayOutputStream();

			try {
				ImageIO.write(bufferedImage, "png", output);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
			return output.toByteArray();
		}
		return null;
	}

	@Override
	public String[] getShortcut() {
		KeyStroke ks = menuItem.getAccelerator();
		if (ks != null) {
			String s = getModifiersText(ks.getModifiers());
			String vk = getVKText(ks.getKeyCode());

			List<String> l = new ArrayList<String>();

			StringTokenizer st = new StringTokenizer(s);

			while (st.hasMoreTokens())
				l.add(st.nextToken());

			l.add(vk);

			return l.toArray(new String[l.size()]);
		}
		// TODO Auto-generated method stub
		return null;
	}

	private static VKCollection getVKCollection() {
		if (vks == null) {
			vks = new VKCollection();
		}
		return vks;
	}

	static String getModifiersText(int modifiers) {
		StringBuffer buf = new StringBuffer();

		if ((modifiers & InputEvent.SHIFT_DOWN_MASK) != 0) {
			buf.append("Shift ");
		}
		if ((modifiers & InputEvent.CTRL_DOWN_MASK) != 0) {
			buf.append("Ctrl ");
		}
		if ((modifiers & InputEvent.META_DOWN_MASK) != 0) {
			buf.append("Meta ");
		}
		if ((modifiers & InputEvent.ALT_DOWN_MASK) != 0) {
			buf.append("Alt ");
		}
		if ((modifiers & InputEvent.ALT_GRAPH_DOWN_MASK) != 0) {
			buf.append("AltGraph ");
		}
		if ((modifiers & InputEvent.BUTTON1_DOWN_MASK) != 0) {
			buf.append("Button1 ");
		}
		if ((modifiers & InputEvent.BUTTON2_DOWN_MASK) != 0) {
			buf.append("Button2 ");
		}
		if ((modifiers & InputEvent.BUTTON3_DOWN_MASK) != 0) {
			buf.append("Button3 ");
		}

		return buf.toString();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	static class VKCollection {
		Map code2name;
		Map name2code;

		public VKCollection() {
			code2name = new HashMap();
			name2code = new HashMap();
		}

		public synchronized void put(String name, Integer code) {
			assert ((name != null) && (code != null));
			assert (findName(code) == null);
			assert (findCode(name) == null);
			code2name.put(code, name);
			name2code.put(name, code);
		}

		public synchronized Integer findCode(String name) {
			assert (name != null);
			return (Integer) name2code.get(name);
		}

		public synchronized String findName(Integer code) {
			assert (code != null);
			return (String) code2name.get(code);
		}
	}

	static String getVKText(int keyCode) {
		VKCollection vkCollect = getVKCollection();
		Integer key = Integer.valueOf(keyCode);
		String name = vkCollect.findName(key);
		if (name != null) {
			return name.substring(3);
		}
		int expected_modifiers = (Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL);

		Field[] fields = KeyEvent.class.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			try {
				if (fields[i].getModifiers() == expected_modifiers
						&& fields[i].getType() == Integer.TYPE
						&& fields[i].getName().startsWith("VK_")
						&& fields[i].getInt(KeyEvent.class) == keyCode) {
					name = fields[i].getName();
					vkCollect.put(name, key);
					return name.substring(3);
				}
			} catch (IllegalAccessException e) {
				assert (false);
			}
		}
		return "UNKNOWN";
	}

	@Override
	public String getToggleType() {
		if (menuItem instanceof JRadioButtonMenuItem)
			return "radio";
		if (menuItem instanceof JCheckBoxMenuItem)
			return "checkmark";
		return "";
	}

	@Override
	public int getToggleState() {
		if (getToggleType().length() > 0)
			return menuItem.isSelected() ? 1 : 0;
		return -1;
	}

	@Override
	public List<Menu> getChildren() {
		if (menuItem instanceof JMenu) {
			JMenu menu = (JMenu) menuItem;
			int ic = menu.getItemCount();
			List<Menu> res = new ArrayList<Menu>(ic);
			for (int i = 0; i < ic; i++)
				res.add(new SwingMenu(menu.getItem(i), holder));
			return res;
		}
		return null;
	}

	@Override
	public void onEvent() {
		// TODO Check if this is correct
		ActionEvent e = new ActionEvent(menuItem, ActionEvent.ACTION_PERFORMED,
				menuItem.getActionCommand());

		for (ActionListener l : menuItem.getActionListeners())
			l.actionPerformed(e);
		if (menuItem instanceof JRadioButtonMenuItem)
			menuItem.setSelected(!menuItem.isSelected());
		if (menuItem instanceof JCheckBoxMenuItem)
			menuItem.setSelected(!menuItem.isSelected());
	}
}
