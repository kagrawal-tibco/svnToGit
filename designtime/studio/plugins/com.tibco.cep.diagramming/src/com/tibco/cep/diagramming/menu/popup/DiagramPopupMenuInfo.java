package com.tibco.cep.diagramming.menu.popup;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.diagramming.menu.Item;
import com.tibco.cep.diagramming.menu.Menu;
import com.tibco.cep.diagramming.menu.MenuItem;
import com.tibco.cep.diagramming.menu.Pattern;
import com.tomsawyer.canvas.swing.TSEComponentLocalization;
import com.tomsawyer.graphicaldrawing.awt.TSEImage;

/**
 * 
 * @author sasahoo
 *
 */
public class DiagramPopupMenuInfo {

	private String menuResource;
	private String extension;
	private AbstractDiagramMenuHandler menuHandler;
	private AbstractDiagramMenuFilter menuFilter;
	private AbstractMenuStateValidator validator;
	private Menu menu;
	private Map<String, JPopupMenu> popupMenus = new HashMap<String, JPopupMenu>();
	private Map<String, AbstractButton> menuComponentMap = new HashMap<String, AbstractButton>();
	private Map<AbstractButton, EList<Pattern>> itemPatternMap = new HashMap<AbstractButton, EList<Pattern>>();
	private JPopupMenu popupMenu;
	private AbstractDiagramI18NTextGenerator generator;

	/**
	 * @param menuResource
	 * @param extension
	 * @param menu
	 * @param menuHandler
	 */
	public DiagramPopupMenuInfo(String menuResource, 
			                    String extension, 
			                    Menu menu,
			                    AbstractDiagramMenuHandler menuHandler,
			                    AbstractDiagramMenuFilter menuFilter,
			                    AbstractMenuStateValidator validator, 
			                    AbstractDiagramI18NTextGenerator generator) {
		this.menuResource = menuResource;
		this.extension = extension;
		this.menu = menu;
		this.menuHandler = menuHandler;
		this.generator = generator;
		this.popupMenu = createPopup(menu);
		this.menuFilter = menuFilter;
		this.validator = validator;
	}

	public JPopupMenu createPopup(Menu menu) {
		JPopupMenu popup;
		String name  = menu.getName();
		String id = menu.getId();
		popup = (JPopupMenu) popupMenus.get(id);
		if (popup == null) {
			popup = new JPopupMenu();
			popup.setName(name);
			//popup.setLightWeightPopupEnabled(false);
			this.populateMenu(popup, menu);
			this.popupMenus.put(id, popup);
		}
		TSEComponentLocalization.setComponentOrientation(popup);
		return (popup);
	}

	/**
	 * @param component
	 * @param menu
	 */
	public void populateMenu(JComponent component, Menu menu) {
		TSEComponentLocalization.setComponentOrientation(component);
		for (Item item : menu.getItems()) {
			String id = item.getId();
			String displayName = getName(item.getName());
			String iconPath = item.getIcon();
			String command = item.getCommand();
			AbstractButton abstractButton;
			boolean visible = item.isVisible();
			if (item instanceof MenuItem) {
				MenuItem menuItem = (MenuItem)item;
				boolean separator = menuItem.isSeparator(); 
				if (separator) {
					component.add(new JSeparator());
				} else {
					boolean checked= menuItem.isChecked();
					int keyCode = menuItem.getKeycode();
					int modifiers = menuItem.getModifiers();
					String mnemonic = menuItem.getMnemonic();
					if (checked) {
						abstractButton = new JCheckBoxMenuItem(displayName, checked);
						if (keyCode > 0 && modifiers > 0) {
							((JCheckBoxMenuItem)abstractButton).setAccelerator(KeyStroke.getKeyStroke(keyCode, modifiers));
						}
					} else {
						abstractButton = new JMenuItem(displayName);
						if (keyCode > 0 && modifiers > 0) {
							((JMenuItem)abstractButton).setAccelerator(KeyStroke.getKeyStroke(keyCode, modifiers));
						}
					}
					abstractButton.enableInputMethods(false);
					if (mnemonic != null) {
						abstractButton.setMnemonic(mnemonic.charAt(0));
					}
					if (command != null) {
						abstractButton.setActionCommand(command);
					}
					ImageIcon defaultIcon = this.getIconResource(iconPath, getClass());
					if (defaultIcon == null) {
						defaultIcon = this.getIconResource(iconPath, menuHandler.getClass());
					}
					if (defaultIcon != null) {
						abstractButton.setIcon(defaultIcon);
					}
					abstractButton.setVisible(visible);
					abstractButton.addActionListener(menuHandler);
					menuComponentMap.put(id, abstractButton);
					itemPatternMap.put(abstractButton, menuItem.getPattern());
					component.add(abstractButton);
				}
			} else if (item instanceof Menu) {
				Menu subMenuItem = (Menu)item;
				if (subMenuItem.isIsSubMenu()){
					abstractButton = new JMenu(displayName);
					abstractButton.enableInputMethods(false);
					component.add(new JSeparator());
					
					JMenu submenuComponent = (JMenu)abstractButton;
					
					component.add(submenuComponent);
					abstractButton.setVisible(visible);
					
					menuComponentMap.put(id, submenuComponent);
					itemPatternMap.put(abstractButton, subMenuItem.getPattern());
					
					populateMenu(submenuComponent, subMenuItem);
				}
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	private ImageIcon getIconResource(String imagePath, Class resourceClass) {
		ImageIcon icon = null;
		if (imagePath != null) {
			Image image = TSEImage.loadImage(resourceClass, imagePath);
			if (image != null) {
				icon = new ImageIcon(image);
			}
		}
		return (icon);
	}
	
	public String getName(String name) {
		if (generator != null) {
			return generator.getLocaleText(name);
		}
		return name;
	}
	
	public String getMenuResource() {
		return menuResource;
	}

	public String getExtension() {
		return extension;
	}

	public AbstractDiagramMenuHandler getMenuHandler() {
		return menuHandler;
	}

	public Menu getMenu() {
		return menu;
	}

	public Map<String, JPopupMenu> getPopupMenus() {
		return popupMenus;
	}

	public Map<String, AbstractButton> getMenuComponentMap() {
		return menuComponentMap;
	}

	public Map<AbstractButton, EList<Pattern>> getItemPatternMap() {
		return itemPatternMap;
	}

	public JPopupMenu getPopupMenu() {
		return popupMenu;
	}
	
	public AbstractDiagramMenuFilter getMenuFilter() {
		return menuFilter;
	}
	
	public AbstractMenuStateValidator getMenuStateValidator() {
		return validator;
	}

}