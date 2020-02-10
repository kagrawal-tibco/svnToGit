package com.tibco.cep.studio.ui.diagrams.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchPage;

import com.tibco.cep.studio.ui.editors.EditorsUIPlugin;
import com.tibco.cep.studio.ui.editors.ProjectDiagramTools;

/**
 * 
 * @author sasahoo
 *
 */
public class DiagramFilterAction extends Action implements IMenuCreator  {

	private IWorkbenchPage page;
	Menu menu;
	/**
	 * @param page
	 */
	@SuppressWarnings("static-access")
	public DiagramFilterAction( IWorkbenchPage page) {
		super("", SWT.DROP_DOWN);
		this.page = page;
		setImageDescriptor(EditorsUIPlugin.getDefault().getImageDescriptor("icons/project_diagram_filter.png"));
		setMenuCreator(this);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		//TODO for default search
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
		if (menu != null) {
			menu.dispose();
		}
		menu = new Menu(parent);
		populateMenu(menu);
		return menu;

	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.IMenuCreator#getMenu(org.eclipse.swt.widgets.Menu)
	 */
	public Menu getMenu(Menu parent) {
		return null;
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