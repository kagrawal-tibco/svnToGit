package com.tibco.cep.sharedresource.ui.util;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.sharedresource.SharedResourcePlugin;

/*
@author ssailapp
@date Oct 2, 2009 3:43:18 PM
 */

public class SharedResourceImages {

	public static final String SHARED_RESOURCE_IMAGES_BASE = "com.tibco.cep.sharedresource.ui.util.";
	private static final String ICON_PATH = "icons/";
	private static final String WIZ_PATH = ICON_PATH + "/wizban/";
	private static ImageRegistry SharedResourceImageRegistry;
	
	//public static final String IMG_FORM_BG = SHARED_RESOURCE_IMAGES_BASE + "formBg";
	public static final String IMG_SHAREDRES_SSL_CONFIG = SHARED_RESOURCE_IMAGES_BASE + "sslConfig";
	public static final String IMG_SHAREDRES_HTTP = SHARED_RESOURCE_IMAGES_BASE + "http";
	
	public static final String IMG_SHAREDRES_WIZ_HTTP = SHARED_RESOURCE_IMAGES_BASE + "wizHttp";
	public static final String IMG_SHAREDRES_WIZ_IDENTITY = SHARED_RESOURCE_IMAGES_BASE + "wizIdentity";
	public static final String IMG_SHAREDRES_WIZ_SB = SHARED_RESOURCE_IMAGES_BASE + "wizStreamBase";
	public static final String IMG_SHAREDRES_WIZ_JDBC = SHARED_RESOURCE_IMAGES_BASE + "wizJdbc";
	public static final String IMG_SHAREDRES_WIZ_JMS_APP = SHARED_RESOURCE_IMAGES_BASE + "wizJmsApp";
	public static final String IMG_SHAREDRES_WIZ_JMS_CON = SHARED_RESOURCE_IMAGES_BASE + "wizJmsCon";
	public static final String IMG_SHAREDRES_WIZ_JNDI = SHARED_RESOURCE_IMAGES_BASE + "wizJndi";
	public static final String IMG_SHAREDRES_WIZ_RSP = SHARED_RESOURCE_IMAGES_BASE + "wizRsp";
	public static final String IMG_SHAREDRES_WIZ_RV = SHARED_RESOURCE_IMAGES_BASE + "wizRv";
	
	public static boolean loadImage(String key, String name) {
//		URL url = SharedResourcePlugin.getDefault().find(new Path(name));
		URL url = FileLocator.find(SharedResourcePlugin.getDefault().getBundle(), new Path(name), null);
		if (url == null)
			return false;
		ImageDescriptor descr = ImageDescriptor.createFromURL(url);
		SharedResourceImageRegistry.put(key, descr);
		return true;
	}

	public static Image getImage(String key) {
		if (SharedResourceImageRegistry == null)
			initialize();
		return SharedResourceImageRegistry.get(key);
	}

	public static ImageDescriptor getImageDescriptor(String key) {
		if (SharedResourceImageRegistry == null)
			initialize();
		return SharedResourceImageRegistry.getDescriptor(key);
	}

	private static void initialize() {
		SharedResourceImageRegistry = new ImageRegistry();
//		loadImage(IMG_FORM_BG, ICON_PATH + "form_banner.gif");

		loadImage(IMG_SHAREDRES_SSL_CONFIG, ICON_PATH + "sslconfiguration.gif");
		loadImage(IMG_SHAREDRES_HTTP, ICON_PATH + "httpconnection.gif");
		
		loadImage(IMG_SHAREDRES_WIZ_HTTP, WIZ_PATH + "httpconnection.gif");
		loadImage(IMG_SHAREDRES_WIZ_IDENTITY, WIZ_PATH + "identity.gif");
		loadImage(IMG_SHAREDRES_WIZ_SB, WIZ_PATH + "streambase_16x16.png");
		loadImage(IMG_SHAREDRES_WIZ_JDBC, WIZ_PATH + "jdbcconnection.gif");
		loadImage(IMG_SHAREDRES_WIZ_JMS_APP, WIZ_PATH + "jmsapplication.gif");
		loadImage(IMG_SHAREDRES_WIZ_JMS_CON, WIZ_PATH + "jmsconfiguration.gif");
		loadImage(IMG_SHAREDRES_WIZ_JNDI, WIZ_PATH + "jndi.gif");
		loadImage(IMG_SHAREDRES_WIZ_RSP, WIZ_PATH + "rule-serviceprovider.png");		
		loadImage(IMG_SHAREDRES_WIZ_RV, WIZ_PATH + "rvtransport.gif");
	}
}
