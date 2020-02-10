package com.tibco.cep.decision.tree.ui.util;

import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.decision.tree.ui.DecisionTreeUIPlugin;

/*
@author ssailapp
@date Sep 14, 2011
 */

public class DecisionTreeImages {
	private static final String DTREE_IMAGES_PATH = "icons/";

	public static String ASSIGNMENT = DTREE_IMAGES_PATH + "assignment.png";
	public static String ASSOCIATION = DTREE_IMAGES_PATH + "association.png";
	public static String BOOLEAN_CONDITION = DTREE_IMAGES_PATH + "ExclusiveGateway.gif";
	public static String CALL_RF = DTREE_IMAGES_PATH + "SimpleState.png";
	public static String CALL_TABLE = DTREE_IMAGES_PATH + "callTable.png";
	public static String CALL_TREE = DTREE_IMAGES_PATH + "callTree.png";
	public static String DESCRIPTION = DTREE_IMAGES_PATH + "note.gif";
	public static String LINK = DTREE_IMAGES_PATH + "link.gif";
	public static String LOOP = DTREE_IMAGES_PATH + "nodes.gif";
	public static String NODES = DTREE_IMAGES_PATH + "nodes.gif";
	public static String REGION = DTREE_IMAGES_PATH + "region.png";
	public static String SELECTION = DTREE_IMAGES_PATH + "select.gif";
	public static String SIMPLE_NODE = DTREE_IMAGES_PATH + "SimpleState.png";
	public static String SWITCH_CONDITION = DTREE_IMAGES_PATH + "Events.gif";
	public static String TERMINAL_BREAK = DTREE_IMAGES_PATH + "sphere.gif";
	public static String TERMINAL_END = DTREE_IMAGES_PATH + "EndState.png";
	public static String TERMINAL_START = DTREE_IMAGES_PATH + "StartState.png";
	public static String TRANSITION = DTREE_IMAGES_PATH + "transition.png";
	public static String TREE = DTREE_IMAGES_PATH + "DecisionTree16x16.png";
	
	public static String INVALID = DTREE_IMAGES_PATH + "Invalid10x10.png";

	public static Image getImage(String imageId) {
		return (DecisionTreeUIPlugin.getDefault().getImage(imageId));
	}
	
	public static ImageDescriptor getImageDescriptor(String imageId) {
		return (DecisionTreeUIPlugin.getImageDescriptor(imageId));
	}
	
	public static ImageIcon createIcon(String icon)  {
		ImageIcon imageIcon = null;
		try{
			InputStream resource = DecisionTreeUIPlugin.class.getClassLoader().getResourceAsStream(icon);
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
