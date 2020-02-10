package com.tibco.cep.studio.ui.diagrams;

import java.util.Map;

import org.eclipse.swt.widgets.Button;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchSite;

import com.tibco.cep.diagramming.drawing.IGraphDrawing;
import com.tibco.cep.diagramming.tool.PALETTE;
import com.tibco.cep.studio.ui.AbstractDecisionTableEditor;
import com.tibco.cep.studio.ui.editors.EntityDiagramEditor;
import com.tibco.cep.studio.ui.editors.concepts.ConceptDiagramEditor;
import com.tibco.cep.studio.ui.editors.dependency.DependencyDiagramEditor;
import com.tibco.cep.studio.ui.editors.events.EventDiagramEditor;
import com.tibco.cep.studio.ui.editors.project.ProjectDiagramEditor;
import com.tibco.cep.studio.ui.editors.sequence.SequenceDiagramEditor;
import com.tibco.cep.studio.ui.palette.StudioPaletteUI;
import com.tibco.cep.studio.ui.palette.actions.AbstractEditorPartPaletteHandler;
import com.tibco.cep.studio.ui.palette.parts.Palette;
import com.tibco.cep.studio.ui.palette.parts.PaletteDrawer;
import com.tibco.cep.studio.ui.palette.views.PaletteView;

public class EntityDiagramEditorPartListener extends AbstractEditorPartPaletteHandler implements IPartListener {
	
	protected DependencyDiagramPaletteEntry dependencyPaletteEntry;
	protected ProjectPaletteEntry projectPaletteEntry;
	protected EventPaletteEntry eventPaletteEntry;
	protected ConceptPaletteEntry conceptPaletteEntry;
	
	public void partActivated(IWorkbenchPart part) {
		IWorkbenchPage activePage = part.getSite().getPage();
		if (activePage == null)
			return;
		if (part instanceof ConceptDiagramEditor) {
					doWhenDiagramEditorActive(part, activePage, (IEditorSite)((ConceptDiagramEditor) part).getSite(), PALETTE.CONCEPT);}
		else if (part instanceof EventDiagramEditor) {
					doWhenDiagramEditorActive(part, activePage, (IEditorSite)((EventDiagramEditor) part).getSite(), PALETTE.EVENT);
		} else if (part instanceof ProjectDiagramEditor) {
					doWhenDiagramEditorActive(part, activePage, (IEditorSite)((ProjectDiagramEditor) part).getSite(), PALETTE.PROJECT);
		} else if (part instanceof SequenceDiagramEditor) {
				    doWhenDiagramEditorActive(part, activePage, (IEditorSite)((SequenceDiagramEditor) part).getSite(), PALETTE.SEQUENCE);
		} else if (part instanceof DependencyDiagramEditor) {
				    doWhenDiagramEditorActive(part, activePage, (IEditorSite)((DependencyDiagramEditor) part).getSite(), PALETTE.DEPENDENCY);
		}
	}
	  
	public void partBroughtToTop(IWorkbenchPart part) {
		// TODO Auto-generated method stub
	}

	public void partClosed(IWorkbenchPart part) {
		try {
			if (part instanceof EntityDiagramEditor) {
				final EntityDiagramEditor editor = (EntityDiagramEditor) part;
				IEditorSite site = (IEditorSite) editor.getSite();
				if (site.getPage() != null	&& !(site.getPage().getActiveEditor() instanceof IGraphDrawing)
						&& !(site.getPage().getActiveEditor() instanceof AbstractDecisionTableEditor)) {
					doWhenDiagramEditorCloseOrDeactivate(part, site);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void partDeactivated(IWorkbenchPart part) {
		try {
			if (part instanceof ConceptDiagramEditor) {
				IEditorSite site = (IEditorSite) ((ConceptDiagramEditor) part).getSite();
				doWhenPartDeActivated(part, site, site.getPage() != null && (site.getPage().getActiveEditor() instanceof IGraphDrawing), 
						site.getPage().getActiveEditor() instanceof ConceptDiagramEditor);
			} else if (part instanceof EventDiagramEditor) {
				IEditorSite site = (IEditorSite) ((EventDiagramEditor) part).getSite();
				doWhenPartDeActivated(part, site, site.getPage() != null && (site.getPage().getActiveEditor() instanceof IGraphDrawing), 
						site.getPage().getActiveEditor() instanceof EventDiagramEditor);
			} else if (part instanceof ProjectDiagramEditor) {
				IEditorSite site = (IEditorSite) ((ProjectDiagramEditor) part).getSite();
				doWhenPartDeActivated(part, site, site.getPage() != null && (site.getPage().getActiveEditor() instanceof IGraphDrawing), 
						site.getPage().getActiveEditor() instanceof ProjectDiagramEditor);
			} else if (part instanceof SequenceDiagramEditor) {
				IEditorSite site = (IEditorSite) ((SequenceDiagramEditor) part).getSite();
				doWhenPartDeActivated(part, site, site.getPage() != null && (site.getPage().getActiveEditor() instanceof IGraphDrawing), 
						site.getPage().getActiveEditor() instanceof SequenceDiagramEditor);
			}else if (part instanceof DependencyDiagramEditor) {
				IEditorSite site = (IEditorSite) ((DependencyDiagramEditor) part).getSite();
				doWhenPartDeActivated(part, site, site.getPage() != null && (site.getPage().getActiveEditor() instanceof IGraphDrawing), 
						site.getPage().getActiveEditor() instanceof DependencyDiagramEditor);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void partOpened(IWorkbenchPart part) {
		// TODO Auto-generated method stub
	}

	/**
	 * 
	 * @param site
	 * @param isActivate
	 */
	public void updatePaletteView(IWorkbenchPart part, IEditorSite site,	boolean isActivate) {
		if (site.getPage() != null) {
			IViewPart view = site.getPage().findView(PaletteView.ID);
			if (view != null) {
				PaletteView paletteView = (PaletteView) view;
				Palette palette = paletteView.getPalette();
				if (isActivate) {
					if (palette.getPaletteDrawers().size() == 0) {
						if (part instanceof ConceptDiagramEditor) {
							if(paletteView.getType() !=  PALETTE.CONCEPT){
								updateConceptViewPalette(palette, paletteView, site);
							}
						} else if (part instanceof EventDiagramEditor) {
							if(paletteView.getType() !=  PALETTE.EVENT){
								updateEventViewPalette(palette, paletteView, site);
							}
						} else if (part instanceof ProjectDiagramEditor) {
							if(paletteView.getType() !=  PALETTE.PROJECT){
								updateProjectDiagramPalette(palette, paletteView, site);
							}
						} else if (part instanceof SequenceDiagramEditor) {
							//TODO: Add a palette later, for now it is empty
							if(paletteView.getType() !=  PALETTE.SEQUENCE){
								
								updateSequenceDiagramPalette(palette, paletteView, site);
							}
						} else if (part instanceof DependencyDiagramEditor) {
							if(paletteView.getType() !=  PALETTE.DEPENDENCY){
								updateDependencyDiagramPalette(palette, paletteView, site);
							}
						}
					}
				} else {
					StudioPaletteUI.resetPalette(paletteView);
				}
			}
		}
	}

	/**
	 * @param palette
	 * @param paletteView
	 * @param site
	 */
	private void updateConceptViewPalette(Palette palette, PaletteView paletteView, IWorkbenchSite site){
		ConceptPaletteEntry conceptPaletteEntry = new ConceptPaletteEntry();
		PaletteDrawer smNodesDrawer = conceptPaletteEntry.createNodeGroup(site.getPage(), palette);
		PaletteDrawer smLinksDrawer = conceptPaletteEntry.createLinkGroup(site.getPage(), palette);
		palette.addPaletteDrawer(smNodesDrawer);
		palette.addPaletteDrawer(smLinksDrawer);
		paletteView.setType(PALETTE.CONCEPT);
		conceptPaletteEntry.expandBar(site.getWorkbenchWindow(), paletteView,paletteView.getRootExpandBar(), palette,paletteView.getListener());
	}
	
	/**
	 * @param palette
	 * @param paletteView
	 * @param site
	 */
	private void updateEventViewPalette(Palette palette, PaletteView paletteView, IWorkbenchSite site){
		EventPaletteEntry eventPaletteEntry = new EventPaletteEntry();
//		PaletteDrawer genNodesDrawer = eventPaletteEntry.createGeneralGroup(site.getPage(), palette);
		PaletteDrawer eventNodesDrawer = eventPaletteEntry.createNodeGroup(site.getPage(), palette);
//		PaletteDrawer eventLinksDrawer = eventPaletteEntry.createLinkGroup(site.getPage(), palette);
//		palette.addPaletteDrawer(genNodesDrawer);
		palette.addPaletteDrawer(eventNodesDrawer);
//		palette.addPaletteDrawer(eventLinksDrawer);
		paletteView.setType(PALETTE.EVENT);
		eventPaletteEntry.expandBar(site.getWorkbenchWindow(), paletteView,paletteView.getRootExpandBar(), palette,	paletteView.getListener());
	}
	
	/**
	 * @param palette
	 * @param paletteView
	 * @param site
	 */
	private void updateProjectDiagramPalette(Palette palette, PaletteView paletteView, IWorkbenchSite site){
		projectPaletteEntry = new ProjectPaletteEntry();
//		PaletteDrawer projectNodesDrawer = projectPaletteEntry.createNodeGroup(site.getPage(), palette);
		PaletteDrawer viewFilterDrawer = projectPaletteEntry.createFilterGroup(site.getPage(), palette);
//		palette.addPaletteDrawer(projectNodesDrawer);
		paletteView.setType(PALETTE.PROJECT);
		projectPaletteEntry.expandBar(site.getWorkbenchWindow(), paletteView,paletteView.getRootExpandBar(), palette,paletteView.getListener());
		projectPaletteEntry.createCheckDrawer(site.getWorkbenchWindow(), paletteView.getRootExpandBar(), viewFilterDrawer, paletteView.getListener());
	}
	
	/**
	 * @param palette
	 * @param paletteView
	 * @param site
	 */
	private void updateSequenceDiagramPalette(Palette palette, PaletteView paletteView, IWorkbenchSite site){
		paletteView.setType(PALETTE.SEQUENCE);
		//TODO Sequence Palette yet to be updated
	}
	
	/**
	 * @param palette
	 * @param paletteView
	 * @param site
	 */
	private void updateDependencyDiagramPalette(Palette palette, PaletteView paletteView, IWorkbenchSite site){
		dependencyPaletteEntry = new DependencyDiagramPaletteEntry();
//		PaletteDrawer dependencyDrawer = dependencyPaletteEntry.createNodeGroup(site.getPage(), palette);
		PaletteDrawer viewFilterDrawer = dependencyPaletteEntry.createFilterGroup(site.getPage(), palette);
//		palette.addPaletteDrawer(dependencyDrawer);
		paletteView.setType(PALETTE.DEPENDENCY);
		dependencyPaletteEntry.expandBar(site.getWorkbenchWindow(), paletteView,paletteView.getRootExpandBar(), palette,paletteView.getListener());
		dependencyPaletteEntry.createRadioDrawer(site.getWorkbenchWindow(), paletteView.getRootExpandBar(), viewFilterDrawer, paletteView.getListener());
	}
	

	/* (non-Javadoc)
	 * @see com.tibco.cep.studio.ui.palette.actions.AbstractEditorPartPaletteHandler#resetPaletteSelection(org.eclipse.ui.IWorkbenchPart)
	 */
	public void resetPaletteSelection(IWorkbenchPart part){
		if (part instanceof ConceptDiagramEditor) {
			//TODO
		} else if (part instanceof EventDiagramEditor) {
			//TODO
		} else if (part instanceof ProjectDiagramEditor) {
			resetProjectDiagramPalatte(part);
		} else if (part instanceof SequenceDiagramEditor) {
			//TODO
		}
		else if (part instanceof DependencyDiagramEditor) {
			resetDependencyDiagramPalatte(part);
		}
	}
	
	/**
	 * @param part
	 */
	private void resetDependencyDiagramPalatte(IWorkbenchPart part){
		if(dependencyPaletteEntry != null){
			DependencyDiagramManager dependencyDiagramManager = 
				(DependencyDiagramManager)((DependencyDiagramEditor) part).getDiagramManager();
			int level = dependencyDiagramManager.getDependencyLevel();
			switch(level){
				case 1:
					dependencyPaletteEntry.getDependencyLevelButtonMap().get("One").setSelection(true);
					dependencyPaletteEntry.getDependencyLevelButtonMap().get("Two").setSelection(false);
					dependencyPaletteEntry.getDependencyLevelButtonMap().get("All").setSelection(false);
					break;
				case 2:
					dependencyPaletteEntry.getDependencyLevelButtonMap().get("One").setSelection(false);
					dependencyPaletteEntry.getDependencyLevelButtonMap().get("Two").setSelection(true);
					dependencyPaletteEntry.getDependencyLevelButtonMap().get("All").setSelection(false);
					break;
				default:
					dependencyPaletteEntry.getDependencyLevelButtonMap().get("One").setSelection(false);
					dependencyPaletteEntry.getDependencyLevelButtonMap().get("Two").setSelection(false);
					dependencyPaletteEntry.getDependencyLevelButtonMap().get("All").setSelection(true);
				break;
			}
		}
	}
	
	/**
	 * @param part
	 */
	private void resetProjectDiagramPalatte(IWorkbenchPart part){
		if(projectPaletteEntry != null){
			ProjectDiagramManager projectDiagramManager = 
				(ProjectDiagramManager)((ProjectDiagramEditor) part).getDiagramManager();
			Map<String, Boolean> projectDiagramMap = projectDiagramManager.getProjectDiagramMap();
			for(Button entry:projectPaletteEntry.getEntryButtonValueMap().keySet()){
				if(!entry.isDisposed()){
					if(projectDiagramMap.containsKey(entry.getText().intern())){
						entry.setSelection(projectDiagramMap.get(entry.getText().intern()));
						projectPaletteEntry.getPaletteMap().put(entry.getText().intern(), projectDiagramMap.get(entry.getText().intern()));
					}
					projectPaletteEntry.getEntryButtonMap().put(entry.getText().intern(), entry);
					projectPaletteEntry.getEntryButtonValueMap().put(entry, entry.getSelection());
				}
			}
		}
	}
}