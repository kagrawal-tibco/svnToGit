package com.tibco.cep.studio.dashboard.ui.utils;

import java.net.URL;
import java.util.Enumeration;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.osgi.framework.Bundle;

public class ImagesLoader {

	private ImageRegistry imageRegistry;

	private Bundle plugInBundle;

	public ImagesLoader(Bundle plugInBundle,ImageRegistry imageRegistry) {
		this.plugInBundle = plugInBundle;
		this.imageRegistry = imageRegistry;
	}

	@SuppressWarnings("rawtypes")
	public void load() {
		Enumeration entries = plugInBundle.findEntries("/icons", "*.*", true);
		while (entries.hasMoreElements()) {
			URL url = (URL) entries.nextElement();
			String path = url.getPath();
			path = path.replaceAll("/icons/", "");
			if (url.getPath().contains(".svn") == false){
				imageRegistry.put(path,ImageDescriptor.createFromURL(url));
			}
		}
	}

}
