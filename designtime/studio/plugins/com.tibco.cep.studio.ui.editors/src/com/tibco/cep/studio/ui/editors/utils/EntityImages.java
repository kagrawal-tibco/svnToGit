package com.tibco.cep.studio.ui.editors.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.rt.AddonUtil;
import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;


public final class EntityImages {
	public static final String IMAGES_INIT = "com.tibco.cep.entity.diagram..utils.images.";

	public static final String CONCEPT_PALETTE_CONCEPT = IMAGES_INIT + "concept";
	public static final String CONCEPT_PALETTE_INHERITANCE = IMAGES_INIT + "inheritance";
	public static final String CONCEPT_PALETTE_CONTAINMENT = IMAGES_INIT + "containment";
	public static final String CONCEPT_PALETTE_REFERENCE = IMAGES_INIT	+ "reference";
	public static final String CONCEPT_PALETTE_LINK = IMAGES_INIT + "link";

	public static final String PALETTE_NODES = IMAGES_INIT + "nodes";
	
	public static final String PALETTE_DECISIONTABLE = IMAGES_INIT + "decisiontable";
	public static final String PALETTE_LINK = IMAGES_INIT + "scopelink";
	public static final String PALETTE_RULE = IMAGES_INIT + "rule";
	public static final String PALETTE_RULE_FUNCTION = IMAGES_INIT + "rule function";
	public static final String PALETTE_RF = IMAGES_INIT + "rf";
	public static final String PALETTE_ARCHIVE = IMAGES_INIT + "archive";
	public static final String PALETTE_TOOLTIP = IMAGES_INIT + "tooltip";
	public static final String PALETTE_DOMAINMODEL = IMAGES_INIT + "domainmodel";
	
	public static final String PALETTE_NOTE = IMAGES_INIT + "note";
	public static final String SCORECARD = IMAGES_INIT + "scorecard";
	public static final String METRIC = IMAGES_INIT + "metric";
	public static final String EVENT = IMAGES_INIT	+ "event";
	public static final String TIME_EVENT =  IMAGES_INIT	+ "timeevent";
	public static final String CONCEPT_DIAGRAM = IMAGES_INIT + "concept_diagram";
	public static final String EVENT_DIAGRAM = IMAGES_INIT + "event_diagram";
	public static final String CHANNEL = IMAGES_INIT	+ "channel";
	public static final String DESTINATION = CHANNEL	+ ".desetination";
	public static final String PROCESS = IMAGES_INIT	+ "PROCESS";

	public static final String ADD = IMAGES_INIT	+ ".add";
	public static final String REMOVE = IMAGES_INIT	+ ".remove";
	
	public static final String PALETTE_STATEMACHINE = IMAGES_INIT	+ ".statemachine";
	
	public static final String PROJECT_DIAGRAM_FILTER_DRAWER_GRP_TITLE =  IMAGES_INIT	+ ".project_filter_grp";
	
	private static final String ICON_PATH = "icons/";

	private static ImageRegistry ConceptImageRegistry;

	@SuppressWarnings("deprecation")
	public static boolean loadImage(String key, String name) {
		URL url = EditorsUIPlugin.getDefault().find(new Path(name));
		if (url == null)
			return false;
		ImageDescriptor descr = ImageDescriptor.createFromURL(url);
		ConceptImageRegistry.put(key, descr);
		return true;
	}

	public static Image getImage(String key) {
		if (ConceptImageRegistry == null)
			initialize();
		return ConceptImageRegistry.get(key);
	}

	public static ImageDescriptor getImageDescriptor(String key) {
		if (ConceptImageRegistry == null)
			initialize();
		return ConceptImageRegistry.getDescriptor(key);
	}

	private static void initialize() {
		ConceptImageRegistry = new ImageRegistry();

		loadImage(CONCEPT_PALETTE_CONCEPT, ICON_PATH + "concept.png");
		loadImage(CONCEPT_PALETTE_INHERITANCE, ICON_PATH + "inheritance.png");
		loadImage(CONCEPT_PALETTE_CONTAINMENT, ICON_PATH + "containment.gif");
		loadImage(CONCEPT_PALETTE_REFERENCE, ICON_PATH + "reference.gif");
		loadImage(CONCEPT_PALETTE_LINK, ICON_PATH + "link.gif");
		
		loadImage(PALETTE_NODES, ICON_PATH + "nodes.gif");
		loadImage(PALETTE_NOTE, ICON_PATH + "note.gif");
		
		loadImage(SCORECARD, ICON_PATH + "scorecard.png");
		if(AddonUtil.isViewsAddonInstalled()){
		loadImage(METRIC, ICON_PATH + "metric_16x16.gif");
		}
		loadImage(EVENT, ICON_PATH + "event.png");
		loadImage(TIME_EVENT, ICON_PATH + "time-event.gif");
		loadImage(CONCEPT_DIAGRAM, ICON_PATH + "conceptview.png");
		loadImage(EVENT_DIAGRAM, ICON_PATH + "eventview.png");
		
		loadImage(PALETTE_LINK, ICON_PATH + "link.gif");
		loadImage(PALETTE_DECISIONTABLE, ICON_PATH + "new_table_16x16.png");
		loadImage(PALETTE_RULE, ICON_PATH + "rules.png");
		loadImage(PALETTE_RULE_FUNCTION, ICON_PATH + "rule-function.png");
		loadImage(PALETTE_RF, ICON_PATH + "rules.png");
		//loadImage(PALETTE_ARCHIVE, ICON_PATH + "beArchive16x16.gif");
		loadImage(PALETTE_TOOLTIP, ICON_PATH + "sm.gif");

		loadImage(PALETTE_DOMAINMODEL, ICON_PATH + "domaineditor_16x16.png");
		
		loadImage(CHANNEL, ICON_PATH + "channel_16x16.png");
		loadImage(PROCESS, ICON_PATH + "sequence_diagram.png");
		loadImage(DESTINATION, ICON_PATH + "destination_16x16.png");
		
		loadImage(PALETTE_STATEMACHINE, ICON_PATH + "state_machine.png");
		loadImage(ADD, ICON_PATH + "add_16x16.png");
		loadImage(REMOVE, ICON_PATH + "delete.png");
		
		loadImage(PROJECT_DIAGRAM_FILTER_DRAWER_GRP_TITLE, ICON_PATH + "project_diagram_filter.png");

	}

	public static ImageIcon createIcon(String icon) {
		ImageIcon imageIcon = null;
		try{
			InputStream resource = EditorsUIPlugin.class.getClassLoader().getResourceAsStream(icon);
			if (resource == null) {
				System.err.println("Image file " + icon + " is missing");
			}
			return new ImageIcon(ImageIO.read(resource));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imageIcon;
	}
}