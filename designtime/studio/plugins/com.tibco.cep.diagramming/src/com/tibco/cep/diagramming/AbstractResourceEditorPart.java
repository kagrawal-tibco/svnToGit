package com.tibco.cep.diagramming;

import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.part.MultiPageEditorPart;

import com.tibco.cep.studio.ui.palette.actions.AbstractEditorPartPaletteHandler;

public abstract class AbstractResourceEditorPart extends MultiPageEditorPart implements IResourceChangeListener {

	public boolean saving;
	/**
	 * @return
	 */
	public AbstractEditorPartPaletteHandler getPartListener(){
		return null;
	}
	public abstract boolean isCatalogFunctionDrag();

	public abstract void setCatalogFunctionDrag(boolean catalogFunctionDrag);
	
	//registering perspective id
	public abstract String getPerspectiveId();
	
	public abstract void openEditorPerspective(final IEditorSite site);
}
