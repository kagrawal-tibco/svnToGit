package com.tibco.cep.studio.ui.overview;

import static com.tibco.cep.diagramming.utils.DiagramUtils.refreshDiagram;

import java.awt.Frame;

import javax.swing.SwingUtilities;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.diagramming.AbstractOverview;
import com.tibco.cep.diagramming.drawing.BaseDiagramManager;
import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tibco.cep.diagramming.drawing.IGraphDrawing;
import com.tomsawyer.interactive.swing.overview.TSEOverviewComponent;

/**
 * This utility class handles all refresh activity of Common Overview (Drawing Overview/ Table Overview)
 * @author sasahoo
 *
 */
public class OverviewUtils {
	
	public static final String COMMON_OVERVIEW_ID = "com.tibco.cep.studio.ui.overview.commonoverview";
	
	/**
	 * @param site
	 * @param isActivate
	 * @param isEditorSwitch
	 */
	public static void refreshOverview(final IEditorSite site, 
			final boolean isActivate, 
			final boolean isEditorSwitch) {
		try{
			IWorkbenchPage page = site.getPage();
			if (page != null) {
				IViewReference overViewReference = page.findViewReference(COMMON_OVERVIEW_ID);
				if (overViewReference != null) {
					IViewPart view = overViewReference.getView(false);
					if (overViewReference != null && view instanceof AbstractOverview) {
						final AbstractOverview overview = (AbstractOverview) overViewReference.getView(false);
						final IEditorPart activeEditorPart = page.getActiveEditor();
						if(overview != null ){
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									refreshOverview(overview, activeEditorPart, isActivate, isEditorSwitch);
								}
							});
						}
					}
				}
			}	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	

	/**
	 * This method handles both Diagram and Overview refresh
	 * @param site
	 * @param isActivate
	 * @param isEditorSwitch
	 * @param diagramManager
	 * @param isRefreshDiagram
	 */
	public static void refreshOverviewAndDiagram(final IEditorSite site, 
												 final boolean isActivate, 
												 final boolean isEditorSwitch,
												 final DiagramManager diagramManager,
												 final boolean isRefreshDiagram) {
		try{
			IWorkbenchPage page = site.getPage();
			if (page != null) {
				IViewReference overViewReference = page.findViewReference(COMMON_OVERVIEW_ID);
				if (overViewReference != null && (overViewReference.getView(false)) instanceof AbstractOverview) {
					final AbstractOverview overview = (AbstractOverview) overViewReference.getView(false);
					final IEditorPart activeEditorPart = page.getActiveEditor();
					if(overview != null ){
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								//Calling here to avoid multiple async call for refresh diagram and overview refresh
								if(isRefreshDiagram){
									refreshDiagram(diagramManager);
								}
								refreshOverview(overview, activeEditorPart, isActivate, isEditorSwitch);
							}
						});
					}
				}
			}	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void refreshOverviewAndDiagram(final Object object, 
			 final boolean isActivate, 
			 final boolean isEditorSwitch,
			 final BaseDiagramManager diagramManager,
			 final boolean isRefreshDiagram) {
	
		if (object instanceof IEditorSite) {
			refreshOverviewAndDiagram((IEditorSite)object, isActivate, isEditorSwitch, diagramManager, isRefreshDiagram);
		}
	}
	
	/**
	 * @param overview
	 * @param activeEditorPart
	 * @param isActivate
	 * @param isEditorSwitch
	 */
	public static void refreshOverview(final AbstractOverview overview,
			final IEditorPart activeEditorPart, final boolean isActivate,
			final boolean isEditorSwitch) {

		Frame frame = overview.getFrame();
		if (frame != null) {// && !SWT.getPlatform().equals("cocoa")) {
			if (isActivate) {
				if (isEditorSwitch) {
					frame.removeAll();
					updateOverView(activeEditorPart, frame, overview);
				}
				frame.setVisible(true);
			} else {
				frame.removeAll();
				frame.setVisible(false);
			}
		}

	}
	
	/**
	 * @param activeEditorPart
	 * @param frame
	 */
	public static void updateOverView(final IEditorPart activeEditorPart, 
			                          final Frame frame,
			                          AbstractOverview overview ) {
		IGraphDrawing editor = null;
			if (activeEditorPart != null) {
				if (activeEditorPart instanceof IGraphDrawing) {
					editor = (IGraphDrawing) activeEditorPart;
				}
				if(editor == null) return;
				final Object diagramManager = editor.getDiagramManager();
				if (diagramManager != null){
					TSEOverviewComponent overviewComponent =  overview.getTSOverviewComponent();
					if (overviewComponent != null) {
						frame.remove(overviewComponent);
						if( overviewComponent.isVisible() ) {
							overviewComponent.setVisible(false);
						}
						overviewComponent = null;
					}
					overviewComponent = ((IGraphDrawing) diagramManager).getOverviewComponent();
					overviewComponent.setCanvas(((IGraphDrawing) diagramManager).getDrawingCanvas());
					overviewComponent.setVisible(true);
					overviewComponent.setSize(300, 200);
					overviewComponent.setLocation(600, 0);
					overviewComponent.updateUI();
				    frame.add(overviewComponent);
				}
			}
	}
	
	/**
	 * Utility method for invoking anything on Eclipse UI Thread
	 */
	public static void invokeOnDisplayThread(final Runnable runnable, boolean syncExec) {
		Display display = PlatformUI.getWorkbench().getDisplay();
		if(display == null){
			display = Display.getDefault();
		}
		if(display != null && !display.isDisposed()) {
			if (display.getThread() != Thread.currentThread()) {
				if (syncExec) {
					display.syncExec(runnable);
				} else {
					display.asyncExec(runnable);
				}
			} else	{
				runnable.run();
			}
		}
	}
}