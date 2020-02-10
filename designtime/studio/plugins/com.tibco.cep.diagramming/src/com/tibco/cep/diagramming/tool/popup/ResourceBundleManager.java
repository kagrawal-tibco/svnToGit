package com.tibco.cep.diagramming.tool.popup;

import java.util.ResourceBundle;

public class ResourceBundleManager {
	/**
	 * This method returns the unique instance of the ResourceBundle.
	 * The ResourceBundle object is allocated on the first call to
	 * this method. This method will allocate the ResourceBundle
	 * object if it has not yet been created.
	 */
	public static EntityResourceBundle getResources()
	{
		if (ResourceBundleManager.resourceBundle == null)
		{
			ResourceBundleManager.resourceBundle =
				(EntityResourceBundle) ResourceBundle.getBundle(
					"com.tibco.cep.diagramming.tool.popup.SelectToolBundle");

			// Tomahawk.resourceBundle.setParameterReader(this);
		}

		return ResourceBundleManager.resourceBundle;
	}
	

	/**
	 * This method returns the value of the parameter whose name
	 * is specified by the given key. If there is no
	 * parameter with the given key, the method return null.
	 */
	public String getParameter(String key)
	{
		String [] args = {"", ""};
		
		for (int i = 0; i < args.length; i++)
		{
			String param = args[i];

			if (param.startsWith(key))
			{
				int delimiterIndex = param.indexOf('=');

				if ((delimiterIndex > 0) &&
					(delimiterIndex < param.length() - 1))
				{
					return (param.substring(delimiterIndex + 1));
				}
			}
		}

		return (null);
	}
	
	
	/**
	 * This variable holds the singleton resource bundle used to
	 * localize this applet.
	 */
	private static EntityResourceBundle resourceBundle = null;	
}
