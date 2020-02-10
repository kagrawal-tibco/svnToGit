package com.tibco.cep.decision.table.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.decision.table.ui.DecisionTableUIPlugin;
/**
 * 
 * @author sasahoo
 *
 */
		
public final class DTImages {

	public static final String DT_IMAGES_INIT = "com.tibco.cep.decision.table.utils.images.";

	public static final String DT_IMAGES_GENERATE_DECISION_TREE_ACTION = DT_IMAGES_INIT 
	+ "create_tree";
	public static final String DT_IMAGES_RENAME_ACTION  = DT_IMAGES_INIT
	+ "rename";
	public static final String DT_IMAGES_NEW_ACTION = DT_IMAGES_INIT
	+ "new";
	public static final String DT_IMAGES_OPEN_ACTION = DT_IMAGES_INIT
	+ "open";
	public static final String DT_IMAGES_SAVE_ACTION = DT_IMAGES_INIT
	+ "save";
	public static final String DT_IMAGES_SAVE_AS_ACTION = DT_IMAGES_INIT
	+ "saveas";
	public static final String DT_IMAGES_SAVE_ALL_ACTION = DT_IMAGES_INIT
	+ "saveall";
	public static final String DT_IMAGES_ANALYZE_ACTION = DT_IMAGES_INIT
	+ "analyze";
	public static final String DT_IMAGES_VALIDATE_ACTION = DT_IMAGES_INIT
	+ "validate";
	public static final String DT_IMAGES_PROJ_VALIDATE_ACTION = DT_IMAGES_INIT
	+ "proj_validate";
	public static final String DT_IMAGES_DEPLOY_ACTION = DT_IMAGES_INIT
	+ "deploy";
	public static final String DT_IMAGES_GEN_CLASS_ACTION = DT_IMAGES_INIT
	+ "genClass";
	public static final String DT_IMAGES_DEPLOYALL_ACTION = DT_IMAGES_INIT
	+ "deployall";
	public static final String DT_IMAGES_UPDATE_ACTION = DT_IMAGES_INIT
	+ "update";
	public static final String DT_IMAGES_EXCEL_ACTION = DT_IMAGES_INIT
	+ "excel";
	public static final String DT_CONNECT_ENGINE_ACTION = DT_IMAGES_INIT
	+ "connect";
	public static final String DT_IMAGES_MERGE_ROWS = DT_IMAGES_INIT
	+ "merge";
	public static final String DT_IMAGES_CALENDAR = DT_IMAGES_INIT
	+ "calendar";
	public static final String DT_IMAGES_NEW_DT = DT_IMAGES_INIT
	+ "newdt";
	public static final String DT_IMAGES_RULE = DT_IMAGES_INIT
	+ "rule";
	public static final String DT_IMAGES_IMPORT_EXCEL = DT_IMAGES_INIT
	+ "importexcel";
	public static final String DT_IMAGES_IMPORT_WIZARD = DT_IMAGES_INIT
	+ "importwizard";
	public static final String DT_IMAGES_SAVE_AS_WIZARD = DT_IMAGES_INIT
	+ "saveaswizard";
	public static final String DT_IMAGES_AUTHENTICATION = DT_IMAGES_INIT
	+ "authentication";
	public static final String DT_IMAGES_DELETE_ACTION = DT_IMAGES_INIT
	+ "delete";
	public static final String DT_IMAGES_TIMER_EVENT = DT_IMAGES_INIT
	+ "time_event";
	public static final String DT_IMAGES_CALANDAR_DELETE = DT_IMAGES_INIT
	+ "calandar_delete";
	public static final String DT_CHECKOUT_ACTION = DT_IMAGES_INIT
	+ "checkout";
	public static final String DT_IMAGES_SHOW_TEXT = DT_IMAGES_INIT + "showtext";
	public static final String DT_IMAGES_SHOW_PROPERTY = DT_IMAGES_INIT + "showproperty";
	public static final String DT_RULE_FUNCTION = DT_IMAGES_INIT + "decisiontablerulefunctions";
	public static final String DT_DOMAIN_MODEL = DT_IMAGES_INIT + "domainmodel";
	public static final String  DT_RULE_DELETED = DT_IMAGES_INIT + "ruledeleted";
	
	public static final String  DT_IMAGES_START_TEST = DT_IMAGES_INIT + "starttest";
	public static final String  DT_IMAGES_STOP_TEST = DT_IMAGES_INIT + "stoptest";
	public static final String  DT_IMAGES_SHUTDOWN_ENGINE = DT_IMAGES_INIT + "shutdownengine";
	public static final String  DT_IMAGES_REFRESH = DT_IMAGES_INIT + "refresh";
	
	private static final String ICON_PATH = "icons/";
	
	private static ImageRegistry dtImageRegistry;
	

	public static boolean loadImage(String key, String name) {
//		URL url = DecisionTableUIPlugin.getDefault().find(new Path(name));
		URL url = FileLocator.find(DecisionTableUIPlugin.getDefault().getBundle(), new Path(name), null);
		if (url == null)
			return false;
		ImageDescriptor descr = ImageDescriptor.createFromURL(url);
		dtImageRegistry.put(key, descr);
		return true;
	}

	public static Image getImage(String key) {
		if (dtImageRegistry == null)
			initialize();
		return dtImageRegistry.get(key);
	}

	public static ImageDescriptor getImageDescriptor(String key) {
		if (dtImageRegistry == null)
			initialize();
		return dtImageRegistry.getDescriptor(key);
	}

	private static void initialize() {
		dtImageRegistry = new ImageRegistry();
		
		loadImage(DT_IMAGES_SAVE_AS_WIZARD, ICON_PATH + "saveas_wizard.gif");
		loadImage(DT_IMAGES_AUTHENTICATION, ICON_PATH + "authentication.gif");
		loadImage(DT_IMAGES_IMPORT_EXCEL, ICON_PATH + "import_16x16.png");
		loadImage(DT_IMAGES_IMPORT_WIZARD, ICON_PATH + "import_excel_wizard.gif");
		loadImage(DT_IMAGES_RULE, ICON_PATH + "rules.png");
		loadImage(DT_IMAGES_NEW_DT, ICON_PATH + "new_dt_wizard.gif");
		loadImage(DT_IMAGES_NEW_ACTION, ICON_PATH + "decisiontablerulefunctions_16x16.png");
		loadImage(DT_IMAGES_OPEN_ACTION, ICON_PATH + "dt_16x16.gif");
		loadImage(DT_IMAGES_SAVE_ACTION, ICON_PATH + "save_edit.gif");
		loadImage(DT_IMAGES_SAVE_AS_ACTION, ICON_PATH + "saveas_16x16.png");
		loadImage(DT_IMAGES_SAVE_ALL_ACTION, ICON_PATH + "saveall_edit.gif");
		loadImage(DT_IMAGES_VALIDATE_ACTION, ICON_PATH + "validation_16x16.png");
		loadImage(DT_IMAGES_ANALYZE_ACTION, ICON_PATH + "analyze_table16x16.gif");
		loadImage(DT_IMAGES_PROJ_VALIDATE_ACTION, ICON_PATH + "validation_proj16x16.png");
		loadImage(DT_IMAGES_GEN_CLASS_ACTION, ICON_PATH + "classfile.gif");
		loadImage(DT_IMAGES_DEPLOY_ACTION, ICON_PATH + "deploy_decision_table_16x16.png");
		loadImage(DT_IMAGES_DEPLOYALL_ACTION, ICON_PATH + "deploy_decision_table_16x16_all.png");
		loadImage(DT_IMAGES_EXCEL_ACTION, ICON_PATH + "export_16x16.png");
		loadImage(DT_CONNECT_ENGINE_ACTION, ICON_PATH + "connect_to_server_16x16.png");
		loadImage(DT_IMAGES_CALENDAR, ICON_PATH + "calendar.gif");
		loadImage(DT_IMAGES_UPDATE_ACTION, ICON_PATH + "update.gif");
		loadImage(DT_IMAGES_MERGE_ROWS, ICON_PATH + "merge_16x16.png");
		loadImage(DT_IMAGES_DELETE_ACTION,ICON_PATH + "delete.gif");
		loadImage(DT_IMAGES_TIMER_EVENT,ICON_PATH + "time-event.gif");
		loadImage(DT_IMAGES_CALANDAR_DELETE,ICON_PATH + "delete_calandar.gif");
		loadImage(DT_CHECKOUT_ACTION,ICON_PATH + "checkout.gif");
		loadImage(DT_IMAGES_SHOW_TEXT, ICON_PATH + "string_16x16.png");
		loadImage(DT_IMAGES_SHOW_PROPERTY, ICON_PATH + "property_entity.png");
		loadImage(DT_RULE_FUNCTION, ICON_PATH + "decisiontablerulefunctions_16x16.png");
		loadImage(DT_DOMAIN_MODEL, ICON_PATH + "domaineditor_16x16.png");
		loadImage(DT_RULE_DELETED, ICON_PATH + "deletedtable_16x16.png");
		
		loadImage(DT_IMAGES_START_TEST, ICON_PATH + "starttest.gif");
		loadImage(DT_IMAGES_STOP_TEST, ICON_PATH + "stop.gif");
		loadImage(DT_IMAGES_SHUTDOWN_ENGINE, ICON_PATH + "shutdownEngine.gif");
		loadImage(DT_IMAGES_REFRESH, ICON_PATH + "refresh.gif");
		
		loadImage(DT_IMAGES_GENERATE_DECISION_TREE_ACTION, ICON_PATH + "generateDecisionTree.png");
		loadImage(DT_IMAGES_RENAME_ACTION, ICON_PATH + "rename.gif");
	}
	
	  /**
     * 
     * @param icon
     * @return
     */
    public static ImageIcon  createIcon(String icon)  {
		 ImageIcon imageIcon = null;
		 	try{
			 	InputStream resource = DecisionTableUIPlugin.class.getClassLoader().getResourceAsStream(icon);
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
