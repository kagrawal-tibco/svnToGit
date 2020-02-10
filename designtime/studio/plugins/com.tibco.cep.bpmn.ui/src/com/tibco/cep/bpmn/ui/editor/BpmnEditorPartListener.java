package com.tibco.cep.bpmn.ui.editor;

import static com.tibco.cep.studio.ui.overview.OverviewUtils.refreshOverview;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;

import com.tibco.cep.bpmn.model.designtime.metamodel.BpmnMetaModelExtensionConstants;
import com.tibco.cep.bpmn.model.designtime.utils.EObjectWrapper;
import com.tibco.cep.bpmn.ui.BpmnUIPlugin;
import com.tibco.cep.bpmn.ui.graph.BpmnGraphUtils;
import com.tibco.cep.bpmn.ui.graph.palette.BpmnPaletteEntry;
import com.tibco.cep.bpmn.ui.graph.palette.PaletteReader;
import com.tibco.cep.diagramming.drawing.IGraphDrawing;
import com.tibco.cep.diagramming.tool.PALETTE;
import com.tibco.cep.studio.ui.AbstractDecisionTableEditor;
import com.tibco.cep.studio.ui.palette.StudioPaletteUI;
import com.tibco.cep.studio.ui.palette.actions.AbstractEditorPartPaletteHandler;
import com.tibco.cep.studio.ui.palette.parts.Palette;
import com.tibco.cep.studio.ui.palette.parts.PaletteDrawer;
import com.tibco.cep.studio.ui.palette.views.PaletteView;
import com.tomsawyer.graphicaldrawing.TSEGraph;

public class BpmnEditorPartListener extends AbstractEditorPartPaletteHandler implements IPartListener {
	
	private BpmnEditor editor;

	public BpmnEditorPartListener(BpmnEditor bpmnGraphEditor) {
		this.editor = bpmnGraphEditor;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IPartListener#partActivated(org.eclipse.ui.IWorkbenchPart)
	 */
	public void partActivated(IWorkbenchPart part) {
		IWorkbenchPage activePage = part.getSite().getPage();
		if (activePage == null)
			return;
		if (part instanceof BpmnEditor) {
			
			//Selection Input for Property Page Display
			final BpmnDiagramManager diagramManager =(BpmnDiagramManager) editor.getDiagramManager();
			diagramManager.waitForInitComplete();
			if (!diagramManager.isInitialized()) {
				return;
			}
			if(diagramManager.isFirstTimeCreatedGraph)
				diagramManager.refreshNodesForIconAndColor();
			if((diagramManager.getSelectedNodes().size() == 1 && diagramManager.getSelectedEdges().size() == 0 && diagramManager.getSelectedConnectors().size() == 0)
					|| (diagramManager.getSelectedEdges().size() == 1 && diagramManager.getSelectedNodes().size() == 0 && diagramManager.getSelectedConnectors().size() == 0)
							|| (diagramManager.getSelectedEdges().size() == 0 && diagramManager.getSelectedNodes().size() == 0 && diagramManager.getSelectedConnectors().size() == 1)){
				if(diagramManager.getSelectedNodes().size() == 1){
					BpmnGraphUtils.setWorkbenchSelection(diagramManager.getSelectedNodes().get(0), editor, true);
				}
				if(diagramManager.getSelectedEdges().size() == 1){
					BpmnGraphUtils.setWorkbenchSelection(diagramManager.getSelectedEdges().get(0), editor, true);
				}
				if(diagramManager.getSelectedConnectors().size() == 1){
					BpmnGraphUtils.setWorkbenchSelection(diagramManager.getSelectedConnectors().get(0), editor, true);
				}
			}
			//If no Node or Edges are selected, then Root State Machine selected by default. 
			else if(diagramManager.getSelectedNodes().size()==0){
				BpmnGraphUtils.setWorkbenchSelection((TSEGraph)diagramManager.getGraphManager().getMainDisplayGraph(), editor, true);
			}
			
			
			doWhenDiagramEditorActive(part, activePage, (IEditorSite)((BpmnEditor) part).getSite(), PALETTE.GRAPH);	
//			editor.getBpmnGraphDiagramManager().addZoomListener();
			editor.enableActions();
		}
		
		
	}

	/**
	 * 
	 * @param site
	 * @param descriptor
	 */
//	protected void showDiagramPerspective(IEditorSite site,IPerspectiveDescriptor descriptor){
//		String perspective = descriptor.getId();
//		if (!perspective.equals(DiagramPerspective.ID)) {
//			IWorkbenchWindow window = site.getWorkbenchWindow();
//			IWorkbench bench = window.getWorkbench();
//			try {
//				BpmnGraphUtils.removeDecisionTablePerspectiveViews(site); //removing unwanted views other than graph perspective 
//				bench.showPerspective(DiagramPerspective.ID, window);
//			} catch (WorkbenchException e) {
//				e.printStackTrace();
//			}
//		} else{
//			try {
//			BpmnGraphUtils.removeDecisionTablePerspectiveViews(site);//removing unwanted views other than graph perspective
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		}
//	}
	
	public void partBroughtToTop(IWorkbenchPart part) {
		// TODO Auto-generated method stub
	}


	/* (non-Javadoc)
	 * @see org.eclipse.ui.IPartListener#partClosed(org.eclipse.ui.IWorkbenchPart)
	 */
	public void partClosed(IWorkbenchPart part) {
		try {
			if (part instanceof BpmnEditor) {
				final BpmnEditor editor = (BpmnEditor) part;
				IEditorSite site = (IEditorSite) editor.getSite();
				if (site.getPage() != null	&& !(site.getPage().getActiveEditor() instanceof IGraphDrawing)
						&& !(site.getPage().getActiveEditor() instanceof AbstractDecisionTableEditor)) {
					doWhenPartDeActivated(part, site, site.getPage() != null && (site.getPage().getActiveEditor() instanceof IGraphDrawing), 
							site.getPage().getActiveEditor() instanceof BpmnEditor);
				}
			}
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IPartListener#partDeactivated(org.eclipse.ui.IWorkbenchPart)
	 */
	public void partDeactivated(IWorkbenchPart part) {
		try {
			if (part instanceof BpmnEditor) {
				IEditorSite site = (IEditorSite) ((BpmnEditor) part).getSite();
				doWhenPartDeActivated(part, site, site.getPage() != null && (site.getPage().getActiveEditor() instanceof IGraphDrawing), 
						site.getPage().getActiveEditor() instanceof BpmnEditor);	
			}
		} catch (Exception e) {
			BpmnUIPlugin.log(e);
		}
	}
	
	public void partOpened(IWorkbenchPart part) {
		
		if (part.equals(editor)) {
			// TODO Auto-generated method stub
			final BpmnDiagramManager diagramManager = (BpmnDiagramManager) editor
					.getDiagramManager();
			
			boolean isFirstTimeCreatedGraph = diagramManager.isFirstTimeCreatedGraph;
			if (isFirstTimeCreatedGraph) {
				Map<EObjectWrapper<EClass, EObject>, Map<String, Object>> updateMap = new HashMap<EObjectWrapper<EClass,EObject>, Map<String,Object>>();
				diagramManager.updateGraphElements(diagramManager
						.getGraphManager().getMainDisplayGraph(), updateMap);
				EObject userObject = (EObject)diagramManager.getGraphManager().getMainDisplayGraph().getGreatestAncestor().getUserObject();
				if(userObject!=null){
					EObjectWrapper<EClass, EObject> wrap = EObjectWrapper.wrap(userObject);
					HashMap<String, Object> updateList = new HashMap<String, Object>();
					updateList.put(BpmnMetaModelExtensionConstants.E_ATTR_ZOOM_LEVEL, 1.0);
					updateMap.put(wrap, updateList);
				}
				Set<EObjectWrapper<EClass, EObject>> keySet = updateMap.keySet();
				for (EObjectWrapper<EClass, EObject> eObjectWrapper : keySet) {
					Map<String, Object> map = updateMap.get(eObjectWrapper);
					diagramManager.getModelController().updateEmfModel(eObjectWrapper, map);
				}
				
				diagramManager.addAnnotationModelListener();
				Display.getCurrent().asyncExec(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						editor.doSave(new NullProgressMonitor());
					}
				});

			}
		}
		
	}


//	private List<String> getPaletteFiles(String paletteString) {
//		List<String> fileList = new LinkedList<String>();
//		File file = new File(paletteString);
//		if (file.isDirectory()) {
//			FilenameFilter filter = new FilenameFilter() {
//				public boolean accept(File dir, String name) {
//					return name.endsWith(".beprocesspalette");
//					}
//				}; 
//			String[] children = file.list(filter);
//			if (children != null) {
//				for (int i=0; i <children.length; i++) {
//					// Get filename of file or directory
//					fileList.add(children[i]);
//				}
//			} 
//		}
//		else {
//			fileList.add(paletteString);
//		}		
//		
//		return fileList;
//	}

	/**
	 * @param part
	 * @param site
	 * @param isActivate
	 */
	public void updatePaletteView(IWorkbenchPart part, IEditorSite site, boolean isActivate) {
		if (site.getPage() != null) {
			IViewPart view = site.getPage().findView(PaletteView.ID);
			if (view != null) {
				PaletteView paletteView = (PaletteView) view;
				Palette palette = paletteView.getPalette();
				if (isActivate) {
					if (palette.getPaletteDrawers().size() == 0) {
						if (part instanceof BpmnEditor ) {
							if(((BpmnEditor)part).isEditable()){
								if(paletteView.getType() !=  PALETTE.GRAPH){
									BpmnPaletteEntry graphPaletteEntry = new BpmnPaletteEntry();
									List<PaletteDrawer> drawers = new LinkedList<PaletteDrawer>();
									PaletteReader reader = new PaletteReader(graphPaletteEntry,palette,site);
									try {
										reader.processPalettes();
									} catch (Exception e) {
										BpmnUIPlugin.log(e);
									}
									drawers.addAll(reader.getPaletteSections());
									for (PaletteDrawer drawer : drawers) {
										// System.out.println("Adding drawer to palette with entries: " + drawer.getPaletteEntries().size());
										palette.addPaletteDrawer(drawer);
									}
									paletteView.setType(PALETTE.GRAPH);
									graphPaletteEntry.expandBar(site.getWorkbenchWindow(), paletteView,paletteView.getRootExpandBar(), palette,paletteView.getListener());
								} 
							}else
								paletteView.setType(PALETTE.NONE);
						} 
					}else if(paletteView.getType() ==  PALETTE.GRAPH){
						StudioPaletteUI.resetSwitchEditorPalette(site, true);
						updatePaletteView(part, site, true);
					}
				} else {
					StudioPaletteUI.resetPalette(paletteView);
				}
			}
		}
	}
	
//	private List createDefaultPalettes(BpmnPaletteEntry paletteEntry, Palette palette, IEditorSite site) {
//		//PaletteDrawer graphEventDrawer = graphPaletteEntry.createEventsGroup(site.getPage(), palette);
//		List drawers = new LinkedList();
//		drawers.add(paletteEntry.createGeneralGroup(site.getPage(), palette));
//		drawers.add(paletteEntry.createNodeGroup(site.getPage(), palette));
//		drawers.add(paletteEntry.createGatewaysGroup(site.getPage(), palette));
//		drawers.add(paletteEntry.createStartEventsGroup(site.getPage(), palette));
//		drawers.add(paletteEntry.createEndEventsGroup(site.getPage(), palette));
//		drawers.add(paletteEntry.createIntermediateEventsGroup(site.getPage(), palette));
//		drawers.add(paletteEntry.createSwimLanesGroup(site.getPage(), palette));
//		return drawers;
//	}


	/**
	 * 
	 * @param part
	 * @param site
	 * @param type
	 * @param isClosed
	 */
	protected void setPaletteType(IWorkbenchPart part,IEditorSite site, PALETTE type, boolean isClosed){
		String projName = part.getTitleToolTip();
		if(part instanceof BpmnEditor){
			BpmnEditor edi = (BpmnEditor)part;
			projName = edi.getProject().getName();
		}
		IViewPart view = site.getPage().findView(PaletteView.ID);
		if (view != null) {
			PaletteView paletteView = (PaletteView) view;
			if(isClosed){
				paletteView.setType(PALETTE.NONE);
			}
			else{
				if(paletteView.getType() !=  type ){
					paletteView.setProjectName(part.getTitleToolTip());
					StudioPaletteUI.resetSwitchEditorPalette(site, false);
					refreshOverview(site, true, true);
					updatePaletteView(part, site, true);
				}else{
					String paletteViewProj = paletteView.getProjectName() ;
				if (site.getPage().getActiveEditor() instanceof BpmnEditor ){
						if(paletteView!=null && paletteView.getType()==PALETTE.GRAPH && ( paletteViewProj!=null && ( !paletteViewProj.equals(projName)))){
							paletteView.setProjectName(projName);
							StudioPaletteUI.resetSwitchEditorPalette(site, true);
							refreshOverview(site, true, true);
							updatePaletteView(part, site, true);
						}
						else if(paletteView!=null &&  paletteView.getType()==PALETTE.GRAPH && ( paletteViewProj!=null &&  paletteViewProj.equals(projName))){
							refreshOverview(site, true, true);

						}
					}
					resetPaletteSelection(part);
				}
			}
		}
	}
	
	@Override
	public void resetPaletteSelection(IWorkbenchPart part) {
		// TODO Auto-generated method stub
		
	}
	

}
