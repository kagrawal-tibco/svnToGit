package com.tibco.cep.diagramming.actions;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowPulldownDelegate;

import com.tibco.cep.diagramming.DiagrammingPlugin;

/**
 * 
 * @author sasahoo
 *
 */
public class SearchDiagramAction implements IWorkbenchWindowPulldownDelegate{

	@SuppressWarnings("unused")
	private IWorkbenchPage page;
	private Menu menu;
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWindowPulldownDelegate#getMenu(org.eclipse.swt.widgets.Control)
	 */
	public Menu getMenu(Control parent) {
		if(menu != null) 
		{
			menu.dispose();
		}
		menu = new Menu(parent);
		populateMenu(menu);
		return menu;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#dispose()
	 */
	public void dispose() {
		if (menu != null) {
			menu.dispose();
			menu = null;
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.IWorkbenchWindow)
	 */
	public void init(IWorkbenchWindow window) {
		page = window.getActivePage();
	}

	public void run(IAction action) {
		//TODO
	}
	
	/**
	 * @param menu
	 */
	@SuppressWarnings("static-access")
	private void populateMenu(Menu menu) {
		org.eclipse.jface.action.Action searchStringAction = new org.eclipse.jface.action.Action("Search Entity", org.eclipse.jface.action.Action.AS_PUSH_BUTTON) {
			/* (non-Javadoc)
			 * @see org.eclipse.jface.action.Action#run()
			 */
			public void run() {
				try{
//					new DiagramSearchDialog(page,"Search").open();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		};
		searchStringAction.setToolTipText("Search String");
		searchStringAction.setImageDescriptor(DiagrammingPlugin.getDefault().getImageDescriptor("icons/entity_search_tool.png"));

		ActionContributionItem item = new ActionContributionItem(searchStringAction);
		item.fill(menu, -1);
	}
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		//TODO
	}

}
