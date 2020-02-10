package com.tibco.cep.studio.mapper.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class MapperActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.tibco.cep.studio.mapper.ui"; //$NON-NLS-1$

	// The shared instance
	private static MapperActivator plugin;
	
	/**
	 * The constructor
	 */
	public MapperActivator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
		
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static MapperActivator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
	
	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 * 
	 * @param pluginId -> The plugin id to load image from.
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String pluginId, String path) {
		pluginId = (pluginId != null) ? pluginId : PLUGIN_ID;
		return imageDescriptorFromPlugin(pluginId, path);
	}
	
	public Image getImage(String imageName) {
		Image image = getImageRegistry().get(imageName);
		if (image == null) {
			ImageDescriptor desc = getImageDescriptor(imageName);
			if (desc == null) {
				System.out.println("Could not find image "+imageName);
				return null;
			}
			image = desc.createImage();
			getImageRegistry().put(imageName, image);
		}
		return image;
	}
}
