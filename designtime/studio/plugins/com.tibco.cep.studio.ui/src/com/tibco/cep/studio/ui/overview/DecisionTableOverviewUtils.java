package com.tibco.cep.studio.ui.overview;

import static com.tibco.cep.diagramming.utils.DiagramUtils.refreshDiagram;
import static com.tibco.cep.studio.ui.util.StudioUIUtils.invokeOnDisplayThread;

import java.awt.Frame;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;

import com.tibco.cep.diagramming.drawing.DiagramManager;
import com.tibco.cep.diagramming.drawing.IGraphDrawing;
import com.tibco.cep.studio.ui.AbstractDecisionTableEditor;
import com.tomsawyer.interactive.swing.overview.TSEOverviewComponent;

public class DecisionTableOverviewUtils {
	
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
				IViewReference overViewReference = page.findViewReference(CommonOverview.ID);
				if (overViewReference != null && (overViewReference.getView(false)) instanceof CommonOverview) {
					final CommonOverview overview = (CommonOverview) overViewReference.getView(false);
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
				IViewReference overViewReference = page.findViewReference(CommonOverview.ID);
				if (overViewReference != null && (overViewReference.getView(false)) instanceof CommonOverview) {
					final CommonOverview overview = (CommonOverview) overViewReference.getView(false);
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
	
	/**
	 * @param overview
	 * @param activeEditorPart
	 * @param isActivate
	 * @param isEditorSwitch
	 */
	public static void refreshOverview(CommonOverview overview, 
					                   IEditorPart activeEditorPart, 
					                   boolean isActivate, 
					                   boolean isEditorSwitch){
		Frame frame = overview.getFrame();
		if(frame != null){
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
			                          CommonOverview overview ) {
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
	 * @param activePage
	 * @param editor
	 */
	public static void updateOverview(final IWorkbenchPage activePage, 
			                          final AbstractDecisionTableEditor editor){
		try{
			final IViewPart view = activePage.findView(CommonOverview.ID);
			if (view instanceof CommonOverview) {
				final CommonOverview commonOverview = (CommonOverview)view;
//				SwingUtilities.invokeLater(new Runnable() {
//					public void run() {
//						JPanel decisionTablePane = editor.getDecisionTablePane();
//						commonOverview.updateOverview();
//						commonOverview.getOverview().setScrollPane(null);
//						if (decisionTablePane != null) {
//							commonOverview.getOverview().setScrollPane(
//									(JScrollPane) SwingUtilities.getAncestorOfClass(JScrollPane.class, decisionTablePane));
//						} else {
//							commonOverview.getOverview().setScrollPane(null);
//						}
//						commonOverview.getOverview().updateUI();
//						commonOverview.getOverview().validate();
//						//commonOverview.getFrame().setVisible(false);
//						commonOverview.getFrame().setVisible(true);
//					}
//				});
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * @param activePage
	 * @param editor
	 */
	public static void updateTableOverview(final IWorkbenchPage activePage, 
            							   final AbstractDecisionTableEditor editor){
		
		invokeOnDisplayThread(new Runnable() {
			public void run() {
				updateOverview(activePage, editor);
			}
		}, false);
	}
}
