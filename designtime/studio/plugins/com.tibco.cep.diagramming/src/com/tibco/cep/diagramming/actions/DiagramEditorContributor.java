package com.tibco.cep.diagramming.actions;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.MultiPageEditorActionBarContributor;

/**
 * 
 * @author sasahoo
 *
 */
public class DiagramEditorContributor extends MultiPageEditorActionBarContributor {

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.MultiPageEditorActionBarContributor#setActivePage(org.eclipse.ui.IEditorPart)
	 */
	@Override
	public void setActivePage(IEditorPart activeEditor) {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorActionBarContributor#contributeToMenu(org.eclipse.jface.action.IMenuManager)
	 */
	public void contributeToMenu(IMenuManager menuManager) {
		super.contributeToMenu(menuManager);
//		MenuManager diagramMenu = new MenuManager("Diagram",IWorkbenchActionConstants.M_LAUNCH);
//		menuManager.appendToGroup(groupName, item);
//		MenuManager interactiveToolsMenu = new MenuManager("Interactive Tools","com.tibco.cep.diagramming.diagram.interacive.tools.submenu");
//		diagramMenu.add(interactiveToolsMenu);
//
//		interactiveToolsMenu.add(new SelectAction(getPage()));
//		interactiveToolsMenu.add(new PanAction(getPage()));
//		interactiveToolsMenu.add(new ZoomAction(getPage()));
//		interactiveToolsMenu.add(new InteractiveZoomAction(getPage()));
//		interactiveToolsMenu.add(new LinkNavigatorAction(getPage()));
//		IContributionItem[] contributionItems = menuManager.getItems();
//		List<IContributionItem> list =  new ArrayList<IContributionItem>();
//		for(IContributionItem item: contributionItems){
//			list.add(item);
//		}
//		menuManager.removeAll();
//		int index = 0;
//		for(IContributionItem item:list){
//			if(index+1 < list.size()){
//				IContributionItem itemNext = list.get(index+1);
//				if(itemNext instanceof MenuManager){
//					MenuManager manager = (MenuManager)itemNext;
//					if(manager.getMenuText().equalsIgnoreCase("Window")){
//						menuManager.add(diagramMenu);
//						continue;
//					}
//				}
//			}
//				menuManager.add(item);
//			
//			index++;
//		}
	}
	  
	 /* (non-Javadoc)
	 * @see org.eclipse.ui.part.EditorActionBarContributor#contributeToToolBar(org.eclipse.jface.action.IToolBarManager)
	 */
	public void contributeToToolBar(IToolBarManager toolBarManager) {
		
		contributeUtilitiesToolBar(toolBarManager);

		toolBarManager.add(new PrintSetupAction(getPage()));
		toolBarManager.add(new PrintPreviewAction(getPage()));
		toolBarManager.add(new PrintAction(getPage()));
		toolBarManager.add(new ExportToImageAction(getPage()));
		toolBarManager.add(new Separator());
		contributeInteractiveToolBar(toolBarManager);
		toolBarManager.add(new Separator());
		toolBarManager.add(new DiagramLayoutActions(getPage()));
		toolBarManager.add(new LabelingAction(getPage()));
		toolBarManager.add(new RoutingAction(getPage()));
		toolBarManager.add(new IncrementalLayoutAction(getPage()));
		toolBarManager.add(new FitWindowAction(getPage()));
		toolBarManager.add(new Separator());
		toolBarManager.add(new DiagramZoomComboContributionItem(getPage()));
		toolBarManager.add(new Separator());

		contributeSearchHandler(toolBarManager);
	}

	/**
	 * @param toolBarManager
	 */
	protected void contributeSearchHandler(IToolBarManager toolBarManager) {
		toolBarManager.add(new DiagramSearchAction(getPage()));
		toolBarManager.add(new Separator());
	}
	protected void contributeUtilitiesToolBar(IToolBarManager toolBarManager){}
	
	/**
	 * @param toolBarManager
	 */
	protected void contributeInteractiveToolBar(IToolBarManager toolBarManager){
		toolBarManager.add(new SelectAction(getPage()));
		toolBarManager.add(new PanAction(getPage()));
		toolBarManager.add(new MagnifyAction(getPage()));
		toolBarManager.add(new ZoomAction(getPage()));
		toolBarManager.add(new InteractiveZoomAction(getPage()));
		toolBarManager.add(new LinkNavigatorAction(getPage()));
	}
}
