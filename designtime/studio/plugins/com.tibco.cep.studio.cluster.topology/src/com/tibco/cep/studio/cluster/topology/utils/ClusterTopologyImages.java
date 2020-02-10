package com.tibco.cep.studio.cluster.topology.utils;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.studio.cluster.topology.ClusterTopologyPlugin;

/**
 * 
 * @author ggrigore
 *
 */	
public final class ClusterTopologyImages {

	public static final String DEP_IMAGES_INIT = "com.tibco.cep.studio.cluster.topology.utils.images.";

	public static final String DEP_IMAGES_NEW_ACTION = DEP_IMAGES_INIT + "new";
	public static final String DEP_IMAGES_OPEN_ACTION = DEP_IMAGES_INIT + "open";
	public static final String DEP_IMAGES_SAVE_ACTION = DEP_IMAGES_INIT + "save";
	public static final String DEP_IMAGES_SAVE_AS_ACTION = DEP_IMAGES_INIT + "saveas";
	public static final String DEP_IMAGES_DEPLOY_ACTION = DEP_IMAGES_INIT	+ "deploy";
	
	public static final String GRAPH_PALETTE_NODES = DEP_IMAGES_INIT + "nodes";
	public static final String GRAPH_PALETTE_NOTE = DEP_IMAGES_INIT + "note";
	public static final String GRAPH_PALETTE_LINK = DEP_IMAGES_INIT + "link";
	public static final String GRAPH_PALETTE_INHERITANCE = DEP_IMAGES_INIT + "inheritance";

	public static final String GRAPH_PALETTE_HOST = DEP_IMAGES_INIT + "server";
	public static final String GRAPH_PALETTE_MU = DEP_IMAGES_INIT + "mu";
	public static final String GRAPH_PALETTE_AGENT = DEP_IMAGES_INIT + "agent";
	public static final String GRAPH_PALETTE_CLUSTER = DEP_IMAGES_INIT + "cluster";
	public static final String GRAPH_PALETTE_QUERY_AGENT = DEP_IMAGES_INIT + "queryagent";
	public static final String GRAPH_PALETTE_INFERENCE_AGENT = DEP_IMAGES_INIT + "inferenceagent";
	public static final String GRAPH_PALETTE_DASHBOARD_AGENT = DEP_IMAGES_INIT + "dashboardagent";
	
	private static final String ICON_PATH = "icons/";

	private static ImageRegistry depImageRegistry;

	public static boolean loadImage(String key, String name) {
		URL url = FileLocator.find(ClusterTopologyPlugin.getDefault().getBundle(), new Path(name), null);
		if (url == null)
			return false;
		ImageDescriptor descr = ImageDescriptor.createFromURL(url);
		depImageRegistry.put(key, descr);
		return true;
	}

	public static Image getImage(String key) {
		if (depImageRegistry == null)
			initialize();
		return depImageRegistry.get(key);
	}

	public static ImageDescriptor getImageDescriptor(String key) {
		if (depImageRegistry == null)
			initialize();
		return depImageRegistry.getDescriptor(key);
	}

	private static void initialize() {
		depImageRegistry = new ImageRegistry();
		
		loadImage(DEP_IMAGES_NEW_ACTION, ICON_PATH + "editfrag.gif");
		loadImage(DEP_IMAGES_OPEN_ACTION, ICON_PATH + "dt_16x16.gif");
		loadImage(DEP_IMAGES_SAVE_ACTION, ICON_PATH + "save.gif");
		loadImage(GRAPH_PALETTE_NODES, ICON_PATH + "nodes.gif");
		loadImage(GRAPH_PALETTE_NOTE, ICON_PATH + "note.gif");
		loadImage(DEP_IMAGES_SAVE_AS_ACTION, ICON_PATH + "saveAs.gif");
		loadImage(DEP_IMAGES_DEPLOY_ACTION, ICON_PATH + "greenarr.gif");
		loadImage(GRAPH_PALETTE_INHERITANCE,ICON_PATH + "inheritance.png");
		loadImage(GRAPH_PALETTE_LINK, ICON_PATH + "link.gif");
		
		loadImage(GRAPH_PALETTE_HOST, ICON_PATH + "host_16x16.png");
		//TODO: use another icon for DU palette
		loadImage(GRAPH_PALETTE_MU, ICON_PATH + "cpu.gif");
		loadImage(GRAPH_PALETTE_AGENT, ICON_PATH + "agents_16x16.png");
		loadImage(GRAPH_PALETTE_CLUSTER, ICON_PATH + "bus.gif");
		loadImage(GRAPH_PALETTE_QUERY_AGENT, ICON_PATH + "queryagent_16x16.png");
		loadImage(GRAPH_PALETTE_INFERENCE_AGENT, ICON_PATH + "interferenceagent_16x16.png");
		loadImage(GRAPH_PALETTE_DASHBOARD_AGENT, ICON_PATH + "dashboardagent_16x16.png");
	}
}
