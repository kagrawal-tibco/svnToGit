package com.tibco.cep.studio.ui.editors;

import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
/**
 * 
 * @author sasahoo
 *
 */
public class ProjectDiagramFilterMenu extends Action implements IMenuCreator  {

	private List<Action> actions;
	Menu menu;
	
	@SuppressWarnings("static-access")
	public ProjectDiagramFilterMenu(String title, List<Action> actions) {
		super(title, SWT.DROP_DOWN);
		this.actions =  actions;
		setId("com.tibco.cep.studio.project.diagram.diagram.filter");
		setText("Show/Hide");
		setImageDescriptor(EditorsUIPlugin.getDefault().getImageDescriptor("icons/project_diagram_filter.png"));
		setActionDefinitionId("com.tibco.cep.studio.project.diagram.diagram.filter");
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

	private void populateMenu(Menu menu) {
		for(Action action:actions){
			ActionContributionItem item = new ActionContributionItem(action);
			item.fill(menu, -1);
		}
	}
}

