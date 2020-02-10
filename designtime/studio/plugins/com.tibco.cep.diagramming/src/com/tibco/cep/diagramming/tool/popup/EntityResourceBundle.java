//
//	ResourceBundle.java
//
//	Copyright:
//		Tom Sawyer Software, 1992 - 2005
//		1625 Clay Street
//		Sixth Floor
//		Oakland, CA 94612
//		U.S.A.
//
//		Web: www.tomsawyer.com
//
//		All Rights Reserved.
//

package com.tibco.cep.diagramming.tool.popup;


import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.ListResourceBundle;
import java.util.MissingResourceException;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import com.tomsawyer.canvas.swing.TSEComponentLocalization;
import com.tomsawyer.graphicaldrawing.awt.TSEImage;


/**
 * This class defines a resource bundle that has the ability to build
 * various UI objects directly from resources. It offers methods for
 * building menu bars, toolbars, and popup menus. In addition, it
 * provides access to resources such as Objects, Strings and Icons, 
 * and does not throw exceptions if the resources are missing. It is 
 * used by both the Tomahawk class and the ExSelectState class to build
 * their user interfaces.
 */
@SuppressWarnings("all")
public abstract class EntityResourceBundle extends ListResourceBundle
{

// ---------------------------------------------------------------------
// Section: General methods
// ---------------------------------------------------------------------

	/**
	 * This method returns the number of resources held in this
	 * resource bundle.
	 */
	public int size()
	{
		return (this.getContents().length);
	}


// ---------------------------------------------------------------------
// Section: Accessing resources by key or location
// ---------------------------------------------------------------------

	/**
	 * This method redefines the standard behavior of the <code>
	 * getObject</code> method of the ListResourceBundle class.
	 * Instead of throwing an exception, it returns null if the given
	 * resource is not found.
	 */
	public Object getObjectResource(String key)
	{
		Object value = null;

		try
		{
			value = this.getObject(key);
		}
		catch (MissingResourceException resourceError)
		{
		}

		return (value);
	}
	

	/**
	 * This method redefines the standard behavior of the <code>
	 * getString</code> method of the ListResourceBundle class.
	 * Instead of throwing an exception, it returns null if the given
	 * resource is not found.
	 */
	public String getStringResource(String key)
	{
		String value = null;

		if (this.reader != null)
		{
			value = this.reader.getParameter(key);
		}

		if (value == null)
		{
			try
			{
				value = this.getString(key);
			}
			catch (MissingResourceException resourceError)
			{
			}
		}

		return (value);
	}


	/**
	 * This method returns an icon resource from the resource bundle
	 * associated with the specified class. It returns null if the
	 * icon resource could not be loaded.
	 */
	public ImageIcon getIconResource(String resourceName,
		Class resourceClass)
	{
		ImageIcon icon = null;

		String imagePath = this.getStringResource(resourceName);

		if (imagePath != null)
		{
			Image image = TSEImage.loadImage(resourceClass, imagePath);
			
			if (image != null)
			{
				icon = new ImageIcon(image);
			}
		}

		return (icon);
	}
	

	/**
	 * This method returns the first field(key) of the resource table 
	 * at the specified index.
	 */
	public Object getKeyAt(int index)
	{
		return (this.getContents()[index][0]);
	}
	
	
	/**
	 * This method returns the second field(value) of the resource
	 * table at the specified index.
	 */
	public Object getValueAt(int index)
	{
		return (this.getContents()[index][1]);
	}


	/**
	 * This method locates the first occurence of the resource 
	 * starting with the specific name in the resource table. It 
	 * returns the index of the resource in the table if it is found,
	 * and the length of the table plus 1 otherwise.
	 */
	public int locate(String name)
	{
		Object[][] resourceTable = this.getContents();

		// skip resources until the first item starting with the given
		// name is found

		for (int index = 0; index < resourceTable.length; index ++)
		{
			String resource = (String) resourceTable[index][0];

			if (resource.startsWith(name))
			{
				return (index);
			}
		}

		return (resourceTable.length + 1);
	}


// ---------------------------------------------------------------------
// Section: Command line and applet parameter reading
// ---------------------------------------------------------------------

	/**
	 * This method sets the parameter reader that provides access to 
	 * the parameters that were re-specified at the execution time, 
	 * rather than compile-time.
	 */
	public void setParameterReader(ParameterReader reader)
	{
		this.reader = reader;
	}


// ---------------------------------------------------------------------
// Section: GUI building
// ---------------------------------------------------------------------


	/**
	 * This method creates a named popup menu.
	 */
	public JPopupMenu createPopup(String name)
	{
		return this.createPopup(name, null);
	}


	/**
	 * This method creates a named popup menu and adds the specified
	 * action listener to it.
	 */
	public JPopupMenu createPopup(String name,
		ActionListener listener)
	{
		JPopupMenu popup;
		
		popup = (JPopupMenu) popupMenus.get(name);
		
		// if popup has not been created, create it
		
		if (popup == null)
		{
			int index = this.locate(name);

			if (index >= this.size())
			{
				return (null);
			}

			// OK, we found the resources; create a popup menu
			popup = new JPopupMenu();

			// see if it is a named popup
			String text = (String) this.getValueAt(index);

			if (name.length() > 0)
			{
				popup.setName(text);
			}

			//popup.setLightWeightPopupEnabled(false);
			this.populateMenu(popup, index + 1, listener, true);

			// storing the created popup menu
			this.popupMenus.put(name, popup);
		}
		
		TSEComponentLocalization.setComponentOrientation(popup);
		
		return (popup);
	}

	
	/**
	 * This method populates the given menu with items or submenus. It 
	 * returns the index of the last entry scanned in the resource
	 * table.
	 */
	public int populateMenu(JComponent menu,
		int index,
		ActionListener listener)
	{
		return this.populateMenu(menu,
			index,
			listener,
			true);
	}
	

	/**
	 * This method populates the given menu with items or submenus. It 
	 * returns the index of the last entry scanned in the resource
	 * table.
	 */
	public int populateMenu(JComponent menu,
		int index,
		ActionListener listener,
		boolean addAccelerators)
	{
		boolean needSeparator = false;

		TSEComponentLocalization.setComponentOrientation(menu);

		while (true)
		{
			String type = (String) this.getKeyAt(index);
			String name = (String) this.getValueAt(index);
			
			String text = this.getStringResource(name + ".text");

			if ("submenu".equals(type))
			{
				if (name == null || name.length() <= 0)
				{
					return (index + 1);
				}
				else
				{
					JMenu submenu = null;
					try {
						submenu = new JMenu(text);
					}
					catch (RuntimeException e) {
						
					}
					catch (java.lang.Error e2) {
						
					}
					catch (Exception e3) {
						
					}
					
					index = this.populateMenu(submenu,
						index + 1,
						listener,
						addAccelerators);
						
					if (submenu.getItemCount() > 0)
					{
						// This is added to work around a Swing bug
						// where clicking on items in menus sometimes 
						// throws a NullPointerException
						
						submenu.enableInputMethods(false);
						
						if (needSeparator)
						{
							menu.add(new JSeparator());
							needSeparator = false;
						}

						menu.add(submenu);
						
						// see if we have a mnemonic for the submenu
						String mnemonic = this.getStringResource(
							name + ".mnemonic");

						if (mnemonic != null)
						{
							submenu.setMnemonic(mnemonic.charAt(0));
						}
					}
				}
			}
			else if ("item".equals(type))
			{
			// skip "notInAppletMenu" items if we're an applet or
			// "notInJVMBelow13" items if the JVM is below 1.3.

				if ((name == null) || (name.length() <= 0))
				{
					needSeparator = true;
				}
				else
				{
					// menu item we are about to create
					JMenuItem item;

					// see if it is a radio or plain menu item
					String group = this.getStringResource(
						name + ".group");

					if (group != null)
					{
						item = new JMenuItem(text);
						this.getRadioController(group + ".menu").add(item);
					}
					else
					{
						// see if the item is to be checked
						String checked =
							this.getStringResource(name + ".checked");

						if (checked == null)
						{
							item = new JMenuItem(text);
						}
						else
						{
							item = new JCheckBoxMenuItem(text,
								"true".equals(checked));
						}
					}

					// This is added to work around a Swing bug
					// where clicking on items in menus sometimes
					// throws a NullPointerException

					item.enableInputMethods(false);

					ImageIcon icon = this.getIconResource(
						name + ".icon", ResourceBundleManager.class);

					if (icon != null)
					{
						item.setIcon(icon);
					}

					// check if there is a command associated with the
					// item. If there is, the item gets a command
					// listener. Otherwise disable it.

					String command =
						this.getStringResource(name + ".command");

					if (command != null)
					{
						item.setActionCommand(command);
						item.addActionListener(listener);

						// only buttons with commands are remembered
						this.appButtons.addElement(item);
					}
					else
					{
						item.setEnabled(false);
					}

					// see if we have an accelerator for the item

					if (addAccelerators)
					{
						Object accelerator = this.getObjectResource(
							name + ".accelerator");

						if (accelerator instanceof KeyStroke)
						{
							item.setAccelerator((KeyStroke) accelerator);
						}
					}

					// see if we have a mnemonic for the item
					String mnemonic = this.getStringResource(
						name + ".mnemonic");

					if (mnemonic != null)
					{
						item.setMnemonic(mnemonic.charAt(0));
					}

					if (needSeparator)
					{
						menu.add(new JSeparator());
						needSeparator = false;
					}

					// finally add the item to the menu
					menu.add(item);
				}

				++index;
			}
			else
			{
				return (index);
			}
		}
	}


	
	/**
	 * This method returns an enumeration of all buttons created by
	 * this resource.
	 */
	public Enumeration getAllButtons()
	{
		return (this.appButtons.elements());
	}


	/**
	 * This method returns the button group controller with a given
	 * name. It returns null if it cannot find one.
	 */
	public ButtonGroup getRadioController(String name)
	{
		ButtonGroup group =
			(ButtonGroup) this.radioControllers.get(name);

		if (group == null)
		{
			group = new ButtonGroup();
			this.radioControllers.put(name, group);
		}

		return (group);
	}


// ---------------------------------------------------------------------
// Section: Instance variables
// ---------------------------------------------------------------------

	/**
	 * This variable stores all buttons created by this application.
	 * It is used by enablers and radio buttons synchronizers.
	 */
	Vector appButtons = new Vector(90, 10);

	/**
	 * This variable stores the accessor to command line arguments.
	 */
	ParameterReader reader;

	/**
	 * This variable stores all button groups managed by this applet.
	 */
	Hashtable radioControllers = new Hashtable(11, 0.9f);
	
	/**
	 * This variable stores all the popup menus that are shared by canvases.
	 */
	Hashtable popupMenus = new Hashtable(10);
}
