package com.tibco.cep.studio.ui.actions;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionDelegate2;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowPulldownDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import com.tibco.cep.studio.ui.AbstractNavigatorNode;
import com.tibco.cep.studio.ui.StudioUIPlugin;
import com.tibco.cep.studio.ui.util.StudioResourceUtils;

public class GlobalVariablesAction extends AbstractHandler implements IWorkbenchWindowPulldownDelegate, IActionDelegate2 {

	private IWorkbenchWindow window;
	private IStructuredSelection selection;

	private IProject project;
	private Menu menu;
	
	@Override
	public void dispose() {
	}

	@Override
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	@Override
	public void run(IAction action) {
	}

	@Override
	public void selectionChanged(IAction action, ISelection _selection) {
		if (_selection == null) {
			return;
		}
		if (_selection instanceof IStructuredSelection) {
			this.selection = (IStructuredSelection)_selection;
			if (selection.size() == 1){
				try {
					action.setEnabled(true);
					if(selection.getFirstElement() instanceof AbstractNavigatorNode){
						action.setEnabled(false);
						return;
					}
					if( StudioResourceUtils.isStudioProject(selection)) {
						project = StudioResourceUtils.getCurrentProject(selection);
						if (project != null && project.isOpen()) {
							action.setEnabled(true);
						} else {
							action.setEnabled(false);
						}
					} else {
						action.setEnabled(false);
					}
				} catch (Exception e) {
					action.setEnabled(false);
				}
			} else {
				action.setEnabled(false);
			}	
		} else if(_selection.isEmpty()) {
			action.setEnabled(false);
		}
	}

	@Override
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
	@SuppressWarnings("static-access")
	private void populateMenu(Menu menu) {
		ShowGVViewAction gvViewAction = new ShowGVViewAction("Global Variables View", window, StudioUIPlugin.getDefault().getImageDescriptor("icons/global_var.png"));
		ShowGVEditorAction gvEditorAction = new ShowGVEditorAction(this, "Global Variables Editor", window, 
				StudioUIPlugin.getDefault().getImageDescriptor("icons/global_var_edit.png"));
		ActionContributionItem gvViewItem = new ActionContributionItem(gvViewAction);
		ActionContributionItem gvEditorItem = new ActionContributionItem(gvEditorAction);
		
		if (menu != null) {
			gvViewItem.fill(menu, -1);
			gvEditorItem.fill(menu, -1);
		}
	}

	@Override
	public void init(IAction action) {
		window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
	}

	@Override
	public void runWithEvent(IAction action, Event event) {
		try {
			if (selection != null) {
				if (StudioResourceUtils.isStudioProject(selection)) {
					Object firstElement = selection.getFirstElement();
					if (!(firstElement instanceof IProject)) {
						if (!StudioResourceUtils.isStudioProject(selection)) {
							return;
						}
						project = StudioResourceUtils
								.getCurrentProject(selection);
					} else {
						project = (IProject) firstElement;
					}
				}
			}
			if (project != null && project.isOpen()) {
				IFolder gvFolder = project.getFolder("defaultVars");
				if (!gvFolder.exists()) {
					gvFolder.create(true, true, null);
				}
				IFile gvFile = gvFolder.getFile("defaultVars.substvar");
				IEditorInput editorInput = new FileEditorInput(gvFile);
				IWorkbenchWindow window=PlatformUI.getWorkbench().getActiveWorkbenchWindow();
				IWorkbenchPage page = window.getActivePage();
				page.openEditor(editorInput, "com.tibco.cep.studio.ui.editor.GlobalVariablesEditor");
			}
		} catch (Exception e) {
		}
	}
	
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public IStructuredSelection getSelection() {
		return selection;
	}
}