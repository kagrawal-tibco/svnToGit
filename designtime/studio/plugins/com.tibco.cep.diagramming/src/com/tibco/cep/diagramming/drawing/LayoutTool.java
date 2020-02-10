package com.tibco.cep.diagramming.drawing;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchWindow;

import com.tibco.cep.diagramming.DiagrammingPlugin;
import com.tibco.cep.diagramming.utils.ICommandIds;

/**
 * 
 * @author ggrigore
 *
 */
public class LayoutTool extends Action implements IMenuCreator  {

	@SuppressWarnings("unused")
	private final IWorkbenchWindow window;
	private Action[] actions;
	Menu menu;
	@SuppressWarnings("static-access")
	public LayoutTool(String title, IWorkbenchWindow window,Action[] actions) {
		super(title, SWT.DROP_DOWN);
		this.window = window;
		this.actions =  actions;
		setId(ICommandIds.DIAGRAM_LAYOUT);
		setText("Layout");
		setImageDescriptor(DiagrammingPlugin.getDefault().getImageDescriptor("icons/globalLayout.png"));
		setActionDefinitionId(ICommandIds.DIAGRAM_LAYOUT);
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

