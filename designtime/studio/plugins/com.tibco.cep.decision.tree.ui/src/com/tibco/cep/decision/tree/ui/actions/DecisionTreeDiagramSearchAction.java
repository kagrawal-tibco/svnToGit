package com.tibco.cep.decision.tree.ui.actions;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchPage;

import com.tibco.cep.diagramming.DiagrammingPlugin;
import com.tibco.cep.diagramming.actions.DiagramSearchAction;

/*
@author ssailapp
@date Sep 14, 2011
 */

public class DecisionTreeDiagramSearchAction extends DiagramSearchAction {

	public DecisionTreeDiagramSearchAction(IWorkbenchPage page) {
		super(page);
	}
	
	/* (non-Javadoc)
	 * @see com.tibco.cep.diagramming.actions.DiagramSearchAction#populateMenu(org.eclipse.swt.widgets.Menu)
	 */
	@SuppressWarnings("static-access")
	protected void populateMenu(Menu menu) {
		org.eclipse.jface.action.Action searchStringAction = new org.eclipse.jface.action.Action("Search State Entity", org.eclipse.jface.action.Action.AS_PUSH_BUTTON) {
			/* (non-Javadoc)
			 * @see org.eclipse.jface.action.Action#run()
			 */
			public void run() {
				try{
					if (isDisposed()) {
						setDisposed(false);
						DecisionTreeDiagramSearchDialog dialog = new DecisionTreeDiagramSearchDialog(page,"Search", getSearchAction());
						dialog.open();
					}
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
	
	private DiagramSearchAction getSearchAction() {
		return this;
	}
}
