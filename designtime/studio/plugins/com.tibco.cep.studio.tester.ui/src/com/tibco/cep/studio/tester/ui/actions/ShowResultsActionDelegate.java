package com.tibco.cep.studio.tester.ui.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionDelegate2;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowPulldownDelegate;
import org.eclipse.ui.PlatformUI;

import com.tibco.cep.studio.tester.ui.utils.TesterUIUtils;
import com.tibco.cep.studio.ui.StudioUIManager;

/**
 * 
 * @author sasahoo
 *
 */
public class ShowResultsActionDelegate extends AbstractHandler implements IWorkbenchWindowPulldownDelegate, IActionDelegate2  {

	@SuppressWarnings("unused")
	private IWorkbenchWindow window;
	private Menu menu;

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
	 * @see org.eclipse.ui.IWorkbenchWindowPulldownDelegate#getMenu(org.eclipse.swt.widgets.Control)
	 */
	public Menu getMenu(Control parent) {
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
		TesterUIUtils.populateTesterRuns(menu, null);
	}

	@Override
	public void init(IWorkbenchWindow window) {
		// TODO Auto-generated method stub
		window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
	}

	@Override
	public void run(IAction action) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init(IAction action) {
		StudioUIManager.getInstance().addAction("com.tibco.cep.studio.tester.showResultsAction", action);
		action.setEnabled(false);
	}

	@Override
	public void runWithEvent(IAction action, Event event) {
		// TODO Auto-generated method stub
		
	}
}