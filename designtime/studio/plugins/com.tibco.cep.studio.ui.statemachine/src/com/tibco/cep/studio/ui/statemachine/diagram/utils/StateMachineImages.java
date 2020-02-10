package com.tibco.cep.studio.ui.statemachine.diagram.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;

import com.tibco.cep.studio.ui.statemachine.StateMachinePlugin;

public class StateMachineImages {



	public static final String SM_IMAGES_INIT = "com.tibco.cep.statemachine.diagram.utils.images.";

	public static final String SM_PALETTE_START_STATE = SM_IMAGES_INIT
			+ "startstate";
	public static final String SM_PALETTE_END_STATE = SM_IMAGES_INIT
			+ "endstate";
	public static final String SM_PALETTE_SIMPLE_STATE = SM_IMAGES_INIT
			+ "simplestate";
	public static final String SM_PALETTE_COMPOSITE_STATE = SM_IMAGES_INIT
			+ "compositestate";
	public static final String SM_PALETTE_CONCURRENT_STATE = SM_IMAGES_INIT
			+ "concurrentstate";
	public static final String SM_PALETTE_REGION= SM_IMAGES_INIT
			+ "region";
	public static final String SM_PALETTE_CALL_STATE = SM_IMAGES_INIT
			+ "substate";
	public static final String SM_PALETTE_SELECTION_STATE = SM_IMAGES_INIT
			+ "selectionstate";
	public static final String SM_PALETTE_TRANSITION_STATE = SM_IMAGES_INIT
			+ "transitionstate";
	public static final String SM_PALETTE_NOTE = SM_IMAGES_INIT + "note";

	public static final String SM_NEW_ACTION = SM_IMAGES_INIT + "new";
	public static final String SM_OPEN_ACTION = SM_IMAGES_INIT + "open";
	public static final String SM_SAVE_ACTION = SM_IMAGES_INIT + "save";
	public static final String SM_SAVE_AS_ACTION = SM_IMAGES_INIT + "saveas";

	public static final String SM_PALETTE_LINK = SM_IMAGES_INIT + "link";

	public static final String SM_PALETTE_NODES = SM_IMAGES_INIT + "nodes";

	private static final String ICON_PATH = "icons/";

	private static ImageRegistry SMImageRegistry;

	@SuppressWarnings("deprecation")
	public static boolean loadImage(String key, String name) {
		URL url = StateMachinePlugin.getDefault().find(new Path(name));
		if (url == null)
			return false;
		ImageDescriptor descr = ImageDescriptor.createFromURL(url);
		SMImageRegistry.put(key, descr);
		return true;
	}

	public static Image getImage(String key) {
		if (SMImageRegistry == null)
			initialize();
		return SMImageRegistry.get(key);
	}

	public static ImageDescriptor getImageDescriptor(String key) {
		if (SMImageRegistry == null)
			initialize();
		return SMImageRegistry.getDescriptor(key);
	}

	private static void initialize() {
		SMImageRegistry = new ImageRegistry();

		loadImage(SM_PALETTE_START_STATE, ICON_PATH + "StartState.png");
		loadImage(SM_PALETTE_END_STATE, ICON_PATH + "EndState.png");
		loadImage(SM_PALETTE_SIMPLE_STATE, ICON_PATH + "SimpleState.png");
		loadImage(SM_PALETTE_COMPOSITE_STATE, ICON_PATH + "composite.png");
		loadImage(SM_PALETTE_CONCURRENT_STATE, ICON_PATH + "concurrent.png");
		loadImage(SM_PALETTE_REGION, ICON_PATH + "region.png");
		loadImage(SM_PALETTE_CALL_STATE, ICON_PATH + "sub_machinestate.png");
		loadImage(SM_PALETTE_SELECTION_STATE, ICON_PATH + "select.gif");
		loadImage(SM_PALETTE_TRANSITION_STATE, ICON_PATH + "transition.png");
		loadImage(SM_NEW_ACTION, ICON_PATH + "state_machine.png");
		loadImage(SM_OPEN_ACTION, ICON_PATH + "open_project_16x16.png");
		loadImage(SM_SAVE_ACTION, ICON_PATH + "save_project_16x16.png");
		loadImage(SM_SAVE_AS_ACTION, ICON_PATH + "saveas_16x16.png");
		loadImage(SM_PALETTE_LINK, ICON_PATH + "link.gif");
		loadImage(SM_PALETTE_NODES, ICON_PATH + "nodes.gif");
		loadImage(SM_PALETTE_NOTE, ICON_PATH + "note.gif");
	}
	
	/**
	 * @param icon
	 * @return
	 */
	public static ImageIcon  createIcon(String icon)  {
		ImageIcon imageIcon = null;
		try{
			InputStream resource = StateMachinePlugin.class.getClassLoader().getResourceAsStream(icon);
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
