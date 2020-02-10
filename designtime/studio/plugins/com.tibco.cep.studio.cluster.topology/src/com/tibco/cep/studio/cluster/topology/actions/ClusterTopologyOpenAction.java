package com.tibco.cep.studio.cluster.topology.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;

/**
 * 
 * @author ggrigore
 *
 */
public class ClusterTopologyOpenAction implements IWorkbenchWindowActionDelegate {

	private IWorkbenchWindow window;
	
	public void dispose() {

	}

	public void init(IWorkbenchWindow window) {
		this.window = window;

	}

	public void run(IAction action) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				FileDialog fileDialog = new FileDialog(
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
					SWT.OPEN);

				String[] extensions = { "*.deployment" };
				fileDialog.setText("Open a Cluster Deployment File");
				fileDialog.setFilterExtensions(extensions);
//				String fullFilename = fileDialog.open();
//				String filename = fileDialog.getFileName();

//				if (fullFilename != null) {
//					IWorkbenchPage page = window.getActivePage();
//					IEditorInput input = null;
//					try {
//						input = new ClusterTopologyEditorInput("deployment",
//								filename, fullFilename);
//						IEditorPart editor = page.openEditor(input,
//								ClusterTopologyEditor.ID);
//						if (editor instanceof ClusterTopologyEditor){
//							ClusterTopologyEditorInput dtInput = (ClusterTopologyEditorInput)((ClusterTopologyEditor)editor).getEditorInput();
//							dtInput.setextension1Name(filename);
//							((ClusterTopologyEditor)editor).inputChanged();
//						}
//
//					} catch (PartInitException e) {
//						e.printStackTrace();
//					}
//
//				}
			}
		});

	}

	public void selectionChanged(IAction action, ISelection selection) {
	}

	public IWorkbenchWindow getWindow() {
		return window;
	}
}
