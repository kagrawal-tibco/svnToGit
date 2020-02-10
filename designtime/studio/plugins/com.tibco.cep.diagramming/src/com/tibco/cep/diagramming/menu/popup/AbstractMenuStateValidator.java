package com.tibco.cep.diagramming.menu.popup;

import java.util.Map;

import javax.swing.AbstractButton;

import org.eclipse.emf.common.util.EList;

import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tibco.cep.diagramming.menu.Pattern;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractMenuStateValidator {

	protected DiagramManager manager;

	public abstract DiagramManager getManager();

	public abstract void setManager(DiagramManager manager);

	public abstract void validate(Map<AbstractButton, EList<Pattern>> componentPatterns);
	
	public abstract boolean isEditorEnabled();
	
}
