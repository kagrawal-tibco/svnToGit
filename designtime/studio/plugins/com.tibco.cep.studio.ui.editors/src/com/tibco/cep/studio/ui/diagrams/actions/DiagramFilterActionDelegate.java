package com.tibco.cep.studio.ui.diagrams.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;

import com.tibco.cep.studio.ui.editors.ProjectDiagramTools;

/**
 * 
 * @author sasahoo
 *
 */
public class DiagramFilterActionDelegate implements IEditorActionDelegate,IMenuCreator{

	private IWorkbenchPage page;
	Menu menu;
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IEditorActionDelegate#setActiveEditor(org.eclipse.jface.action.IAction, org.eclipse.ui.IEditorPart)
	 */
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		if (targetEditor != null) {
			page = targetEditor.getEditorSite().getPage();
		}
		action.setMenuCreator(this);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.IMenuCreator#dispose()
	 */
	public void dispose() {
		if (menu != null) {
			menu.dispose();
			menu = null;
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.IMenuCreator#getMenu(org.eclipse.swt.widgets.Control)
	 */
	public Menu getMenu(Control parent) {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.IMenuCreator#getMenu(org.eclipse.swt.widgets.Menu)
	 */
	public Menu getMenu(Menu parent) {
		if (menu != null) {
			menu.dispose();
		}
		menu = new Menu(parent);
		populateMenu(menu);
		return menu;
	}

	/**
	 * @param menu
	 */
	private void populateMenu(Menu menu) {
		for(Action action:new ProjectDiagramTools(page).getToolItems()){
			ActionContributionItem item = new ActionContributionItem(action);
			item.fill(menu, -1);
		}
	}

}
