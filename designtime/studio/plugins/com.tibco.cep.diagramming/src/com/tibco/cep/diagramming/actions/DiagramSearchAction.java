package com.tibco.cep.diagramming.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchPage;

import com.tibco.cep.diagramming.DiagrammingPlugin;
import com.tibco.cep.diagramming.drawing.DiagramSearchDialog;
import com.tibco.cep.diagramming.utils.ICommandIds;
import com.tibco.cep.diagramming.utils.Messages;

/**
 * 
 * @author sasahoo
 *
 */
public class DiagramSearchAction extends Action implements IMenuCreator  {

	protected IWorkbenchPage page;
	Menu menu;
	private boolean disposed = true;

	@SuppressWarnings("static-access")
	public DiagramSearchAction( IWorkbenchPage page) {
		super("", SWT.DROP_DOWN);
		this.page = page;
		setId(ICommandIds.DIAGRAM_SEARCH);
		setToolTipText(Messages.getString("search.diagram.entities"));
		setImageDescriptor(DiagrammingPlugin.getDefault().getImageDescriptor("icons/diagram_search_tool.png"));
		setActionDefinitionId(ICommandIds.DIAGRAM_SEARCH);
		setMenuCreator(this);
	}

	public void run() {
		//TODO for default search
	}

	public void dispose() {
		if (menu != null) {
			menu.dispose();
			menu = null;
		}
	}

	public Menu getMenu(Control parent) {
		if (menu != null) {
			menu.dispose();
		}
		menu = new Menu(parent);
		populateMenu(menu);
		return menu;

	}

	public Menu getMenu(Menu parent) {
		return null;
	}

	@SuppressWarnings("static-access")
	protected void populateMenu(Menu menu) {
		org.eclipse.jface.action.Action searchStringAction = new org.eclipse.jface.action.Action("Search Entity", org.eclipse.jface.action.Action.AS_PUSH_BUTTON) {
			/* (non-Javadoc)
			 * @see org.eclipse.jface.action.Action#run()
			 */
			public void run() {
				try{
					new DiagramSearchDialog(page,"Search", getSearchAction()).open();
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
	
	public boolean isDisposed() {
		return disposed;
	}
	
	public void setDisposed(boolean disposed) {
		this.disposed = disposed;
	}
	
	private DiagramSearchAction getSearchAction() {
		return this;
	}
}