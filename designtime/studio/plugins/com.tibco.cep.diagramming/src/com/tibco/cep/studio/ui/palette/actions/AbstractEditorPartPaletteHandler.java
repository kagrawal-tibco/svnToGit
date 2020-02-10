package com.tibco.cep.studio.ui.palette.actions;

import static com.tibco.cep.studio.ui.overview.OverviewUtils.refreshOverview;

import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.cep.diagramming.AbstractResourceEditorPart;
import com.tibco.cep.diagramming.tool.PALETTE;
import com.tibco.cep.studio.ui.palette.StudioPaletteUI;
import com.tibco.cep.studio.ui.palette.views.PaletteView;

/**
 * 
 * @author sasahoo
 *
 */
public abstract class AbstractEditorPartPaletteHandler {

	public abstract void updatePaletteView(IWorkbenchPart part,IEditorSite site, boolean type);
	public abstract void resetPaletteSelection(IWorkbenchPart part);
	/**
	 * 
	 * @param part
	 * @param site
	 * @param type
	 * @param isClosed
	 */
	protected void setPaletteType(IWorkbenchPart part,IEditorSite site, PALETTE type, boolean isClosed){
		IViewPart view = site.getPage().findView(PaletteView.ID);
		if (view != null) {
			PaletteView paletteView = (PaletteView) view;
			if(isClosed){
				paletteView.setType(PALETTE.NONE);
			}
			else{
				if(paletteView.getType() !=  type){
					StudioPaletteUI.resetSwitchEditorPalette(site, false);
					refreshOverview(site, true, true);
					updatePaletteView(part, site, true);
				}else{
					//Updating existing Palette 
					resetPaletteSelection(part);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param part
	 * @param site
	 */
	protected void doWhenDiagramEditorCloseOrDeactivate(IWorkbenchPart part,IEditorSite site){
		refreshOverview(site, false, false);
		updatePaletteView(part, site, false);
		setPaletteType(part, site, PALETTE.NONE , true);
	}
	
	/**
	 * 
	 * @param part
	 * @param activePage
	 * @param site
	 * @param type
	 */
	protected void doWhenDiagramEditorActive(IWorkbenchPart part,IWorkbenchPage activePage,IEditorSite site, PALETTE type){
		IPerspectiveDescriptor descriptor = activePage.getPerspective();
		if (descriptor != null) {
			if (part instanceof AbstractResourceEditorPart) {
				((AbstractResourceEditorPart)part).openEditorPerspective(site);
			}
			setPaletteType(part, site, type,false);
			refreshOverview(site, true, true);
		}
	}
	
	/**
	 * 
	 * @param isCurrentPage
	 * @param part
	 * @param site
	 */
	protected void doWhenDiagramEditorDeactivate(boolean isCurrentPage,IWorkbenchPart part,IEditorSite site){
		if(isCurrentPage)	{
			refreshOverview(site, true, true);
		} else {
			refreshOverview(site, false, false);
			updatePaletteView(part, site, false);
		}
	}
	
	/**
	 * 
	 * @param part
	 * @param site
	 * @param isDiagramEditor
	 * @param isCurrentPage
	 */
	protected void doWhenPartDeActivated(IWorkbenchPart part,IEditorSite site,boolean isDiagramEditor, boolean isCurrentPage){
		if (isDiagramEditor) {
			doWhenDiagramEditorDeactivate(isCurrentPage, part, site);
		}else{
			doWhenDiagramEditorCloseOrDeactivate(part, site);
		}	
	}
}
